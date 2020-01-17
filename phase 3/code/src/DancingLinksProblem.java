//https://www.geeksforgeeks.org/exact-cover-problem-algorithm-x-set-2-implementation-dlx/ was used
//https://stackoverflow.com/questions/39939710/java-how-to-implement-dancing-links-algorithm-with-doublylinkedlists
//https://github.com/benfowler/dancing-links/tree/master/dlx/src
//https://github.com/Warren-Partridge/algorithm-x/blob/master/DancingLinks.java
//https://www.ocf.berkeley.edu/~jchu/publicportal/sudoku/sudoku.paper.html
//https://github.com/benfowler/dancing-links/blob/master/dlx/src/main/java/au/id/bjf/dlx/DLX.java
//http://sudopedia.enjoysudoku.com/Dancing_Links.html

//LOOK AT 4x4.dlx.64x64 (1) FOR AN SUDOKU EXAMPLE (phase 3 -> Research -> this)

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class DancingLinksProblem {
    boolean[][] inputMatrix;
    int[] rowValues;
    String[] headerNames;
    boolean exactCover;
    int maxSeconds;

    DataObject root;

    long timeElapsed = 0;
    Instant start;
    boolean precise;
    int bestScore = 0;
    Object[] bestSolution;
    long run = 0;
    List<Integer> maxValuePerLayer = new ArrayList<>();
    int pruneWait = 5;
    float pruneCutoff = .4F;

    //TODO remove after debugging
    List<Object[]> solutions = new ArrayList<Object[]>();
    int debug;

    public DancingLinksProblem(boolean[][] inputMatrix, String[] headerNames, int[] rowValues, boolean exactCover, int maxSeconds, boolean precise) {
        this.inputMatrix = inputMatrix;
        this.rowValues = rowValues;
        this.headerNames = headerNames;
        this.exactCover = exactCover;
        this.maxSeconds = maxSeconds;
        this.precise = precise;
    }

    public void createDataStructure() {
        //Create root
        ColumnObject root = new ColumnObject();
        root.left = root;
        root.right = root;
        root.up = root;
        root.down = root;

        //create the headers
        for (int x = 0; x < inputMatrix[0].length; x++) {
            ColumnObject header = new ColumnObject();

            header.up = header;
            header.down = header;

            header.right = root;
            header.left = root.left;

            root.left.right = header;
            root.left = header;

            header.size = 0;
            header.name = headerNames[x];
        }

        //Create a list for the next row that gets added to the row above, repeat for all rows
        List<DataObject> rowObjects = new LinkedList<DataObject>();
        for (int y = 0; y < inputMatrix.length; y++) {
            //remove data from last row
            rowObjects.clear();

            //Go to the first column
            ColumnObject currentColumn = (ColumnObject) root.right;

            for (int x = 0; x < inputMatrix[0].length; x++) {
                //if there's a value to add, add it to the row
                if (inputMatrix[y][x]) {
                    //start with new dataObject
                    DataObject dataObject = null;
                    //create one with inputRowIdentifier
                    dataObject = new DataObject(y);

                    dataObject.up = currentColumn.up;
                    dataObject.down = currentColumn;

                    dataObject.right = dataObject;
                    dataObject.left = dataObject;

                    dataObject.header = currentColumn;

                    currentColumn.up.down = dataObject;
                    currentColumn.up = dataObject;
                    currentColumn.size++;

                    rowObjects.add(dataObject);
                }

                //Go to next column
                currentColumn = (ColumnObject) currentColumn.right;
            }

            //Link all of the data objects in this row horizontally (if there are any)
            if (rowObjects.size() > 0) {
                Iterator<DataObject> iterator = rowObjects.iterator();
                DataObject first = iterator.next();

                //while there are objects left in the row, keep going trough them
                while (iterator.hasNext()) {
                    DataObject dataObject = iterator.next();
                    dataObject.left = first.left;
                    dataObject.right = first;
                    first.left.right = dataObject;
                    first.left = dataObject;
                }
            }
        }

        this.root = root;
    }

    List<DataObject> tmpSolution = new ArrayList<DataObject>();
    ArrayList<Integer> tmpSol = new ArrayList<Integer>();
    boolean foundSolution = false;

    //TODO update
    public void solveDriver(int K) { // Deterministic algorithm to find all exact covers
        //start timer
        start = Instant.now();

        if (exactCover) {
            exactCoverUpdated(new ArrayList<Integer>());
        } else {
            //Remove unfillable positions (mainly useful for testing)
            //TODO
//            reduceInput();
//            solvePartial(K, new ArrayList<Integer>());
        }

        System.out.println("DONE");
    }

    ColumnObject smallestColumn;

    public void reduceInput() {
        //remove/unlink columns that can't be filled
        while ((smallestColumn = getSmallestColumnObject()).size == 0) {
            smallestColumn.unlink();
        }
    }

    //TODO rewrite partial cover
//    public void solvePartial(int K, ArrayList<Integer> sols) {
//        //Stop when you found a solution
//        if (!precise) {
//            while ((timeElapsed = Duration.between(start, Instant.now()).toSeconds()) < maxSeconds) {
//                run++;
//
//                partialRun(K, sols);
//            }
//        } else {
//            partialRun(K, sols);
//        }
//
//    }

    private void exactCoverUpdated(ArrayList<Integer> tmp_branch_solution) {
        System.out.println(Arrays.toString(tmp_branch_solution.toArray()));

        //If you have a solution (Step 1 of algX)
        if (root.right == root) {
            System.out.println(tmp_branch_solution.toString());

            System.out.println("FULLY COVERED");
            foundSolution = true;
            bestSolution = tmp_branch_solution.toArray();
        } else {
            //Chose the next column (deterministically) (Step 2 of AlgX)
            ColumnObject nextColumnObject = getSmallestColumnObject();

            nextColumnObject.unlink();

            //Choose a row r such that Ar,c=1 (Step 3 of AlgX)
            for (DataObject row = nextColumnObject.down; row != nextColumnObject; row = row.down){
                System.out.println("E");
                exactCoverUpdated(row, tmp_branch_solution, nextColumnObject);
            }

            nextColumnObject.link();
        }
    }

    private void exactCoverUpdated(DataObject focusRow, ArrayList<Integer> tmp_branch_solution, ColumnObject nextColumnObject) {
        //Include row r in the partial solution (Step 4 of AlgX)
        tmp_branch_solution.add(focusRow.inputRow);

        //Unlink row and column (Step 5 of AlgX)
        for (DataObject column = focusRow.right; column != focusRow; column = column.right) {
            column.header.unlink();
        }

        exactCoverUpdated(tmp_branch_solution);

        //Undo step (re-link)
        tmp_branch_solution.remove(tmp_branch_solution.size() - 1);

        nextColumnObject = focusRow.header;

        for (DataObject left = focusRow.left; left != focusRow; left = left.left) {
            left.header.link();
        }
    }


//        solvePartial(++K,sols);
//
//        //Undo step (re-link)
//        row = tmpSolution.remove(tmpSolution.size() - 1);
//        nextColumnObject = row.header;
//
//        for (DataObject left = row.left; left != row; left = left.left) {
//            left.header.link();
//        }
//    }
//
//    }

//    private void partialRun(int K, ArrayList<Integer> sols){
//        //System.out.println(K);
//
//        //TODO finish pruning
//        /*
//        //Prune every x layers
//        if(layer%pruneWait == 0){
//            if(layer<maxValuePerLayer.size()){
//                //first time this layer is reached
//                maxValuePerLayer.add(score);
//            } else {
//                //layer already exists
//                //check if the score is good enough
//                if(score > pruneCutoff * maxValuePerLayer.get(layer)){
//                    //continue branch
//                    if(score > maxValuePerLayer.get(layer)){
//                        //update highscore
//                        maxValuePerLayer.set(layer, score);
//                    }
//                } else {
//                    //abandon branch
//                }
//            }
//        }*/
//
//
//        //Get the shape with the least filled cells
//        //TODO could this be the cause of the stackOverFlow?
//        //ColumnObject nextColumnObject = getRandomColumnObject();
//        ColumnObject nextColumnObject = getSmallestColumnObject();
//
//        //If this is a dead end
//        if(nextColumnObject == null){
//
//            System.out.println(sols.toString());
//
//            Object[] solutionArray = tmpSolution.toArray();
//
//            //TODO remove after debugging
//            solutions.add(tmpSolution.toArray());
//
//            for(Object solution : solutionArray){
//                DataObject sol = (DataObject) solution;
//                int colInputArray = sol.inputRow;
//                //System.out.println(colInputArray);
//            }
//
//
//            int solutionScore = getSolutionScore(solutionArray);
//
//            //Check if this is the best solution so far
//            if(solutionScore > bestScore){
//                bestSolution = tmpSolution.toArray();
//                bestScore = solutionScore;
//            }
//
//            //TODO backtrack
////            DataObject row = tmpSolution.remove(tmpSolution.size() - 1);
////            nextColumnObject = row.header;
////
////            for (DataObject left = row.left; left != row; left = left.left) {
////                left.header.link();
////            }
////
////            nextColumnObject.link();
////
////            solvePartial(--K);
//
//            return;
//
//        } else {
//
//            nextColumnObject.unlink();
//
//            for (DataObject row = nextColumnObject.down; row != nextColumnObject; row = row.down) {
//
//                sols.add(row.inputRow);
//                tmpSolution.add(row);
//
//                //Cover redundant elements
//                for (DataObject column = row.right; column != row; column = column.right) {
//
//                    column.header.unlink();
//                }
//
//                solvePartial(++K,sols);
//
//                //Undo step (re-link)
//                row = tmpSolution.remove(tmpSolution.size() - 1);
//                nextColumnObject = row.header;
//
//                for (DataObject left = row.left; left != row; left = left.left) {
//                    left.header.link();
//                }
//            }
//
//            nextColumnObject.link();
//        }
//        return;
//    }

    public void solveExact(int K) {
        while (!foundSolution) {
            //Check if you have covered all
            if (root.right == root) {
                //SOLVED IT!
                System.out.println("FULLY COVERED");
                foundSolution = true;
                bestSolution = tmpSolution.toArray();

                return;
            }

            //Get the shape with the least filled cells
            ColumnObject nextColumnObject = getSmallestColumnObject();
            nextColumnObject.unlink();

            //Remove covered elements
            for (DataObject row = nextColumnObject.down; row != nextColumnObject; row = row.down) {
                tmpSolution.add(row);

                for (DataObject column = row.right; column != row; column = column.right) {
                    column.header.unlink();
                }

                solveExact(++K);
                row = tmpSolution.remove(tmpSolution.size() - 1);
                nextColumnObject = row.header;

                for (DataObject column = row.left; column != row; column = column.left) {
                    column.header.link();
                }
            }

            nextColumnObject.link();
        }
    }

    private ColumnObject getSmallestColumnObject() {
        int min = Integer.MAX_VALUE;
        ColumnObject smallestColumnObject = null;

        // Search for the min size ColumnObject by iterating through all ColumnObjects by moving right until we end up back at the header
        for (ColumnObject col = (ColumnObject) root.right; col != root; col = (ColumnObject) col.right) {
            if (col.size < min) {
                min = col.size;
                smallestColumnObject = col;

                //If you found a column of size 0, don't bother looking trough the rest (it can't have a negative size)
                if (min == 0) {
                    return smallestColumnObject;
                }
            }
        }

        return smallestColumnObject;
    }

    public int getSolutionScore(int[] solution) {
        int score = 0;

        //For every chosen object in the solution
        for (int rowNumber : solution) {
            int objectScore = rowValues[rowNumber];
            score += objectScore;
        }

        return score;
    }

    //TODO rewrite
//    public int[][][] answerToArray() {
//        List<Integer> indexesOfUsedRows = new ArrayList<>();
//
//        for (Object row : bestSolution) {
//            DataObject rowObject = (DataObject) row;
//            int usedRowIndex = rowObject.inputRow;
//            indexesOfUsedRows.add(usedRowIndex);
//        }
//
//        List<boolean[]> inputRows = new ArrayList<>();
//
//        for (int index : indexesOfUsedRows) {
//            boolean[] inputRow = inputMatrix[index];
//            inputRows.add(inputRow);
//        }
//
//        //Start 1D to 3D conversion for UI
//        int[][][] finalUIOutput = new int[Wrapper.CONTAINER_WIDTH / Wrapper.cellSize][Wrapper.CONTAINER_HEIGHT / Wrapper.cellSize][Wrapper.CONTAINER_DEPTH / Wrapper.cellSize];
//
//        //Go trough each shape and add it to the 3D output
//        for (boolean[] shape : inputRows) {
//            int outputHeight = Wrapper.CONTAINER_HEIGHT / Wrapper.cellSize;
//            int outputWidth = Wrapper.CONTAINER_WIDTH / Wrapper.cellSize;
//            int outputDepth = Wrapper.CONTAINER_DEPTH / Wrapper.cellSize;
//
//            int[][][] shapeOutput = new int[outputWidth][outputHeight][outputDepth];
//            boolean[][][] booleanShapeOutput = new boolean[outputWidth][outputHeight][outputDepth];

            //boolean[][] twoD = new boolean[][];

//            //Create the rows
//            for(int i=0; i<outputHeight; i++){
//                boolean[] row = new boolean[outputWidth];
//
//                for(int j=0; j<outputWidth; j++){
//                    row[j] = inputRows.get(i*j + j);
//                }
//            }

            //TODO convert to int according to type
        //}





        /*
        static int[][][] input = new int[Wrapper.CONTAINER_WIDTH/Wrapper.cellSize][Wrapper.CONTAINER_HEIGHT/Wrapper.cellSize][Wrapper.CONTAINER_DEPTH/Wrapper.cellSize];

        public static void giveInput(){

            for(int x=0; x<input.length; x++){
                for(int y=0; y<input[x].length; y++){
                    for(int z=0; z<input[x][y].length; z++){
                        input[x][y][z] = (int) (Math.random()*4);
                    }
                }
            }

            Wrapper.UIInput = input;
            FX3D.updateUI();
        }
         */


//        int[][][] stopJava = {{{}}};
//        return stopJava;
//    }

}