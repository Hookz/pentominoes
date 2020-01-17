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

    boolean foundSolution = false;

    public void solveDriver() {
        //start timer
        start = Instant.now();

        if (exactCover) {
            exactCoverUpdated(new ArrayList<Integer>());
        } else {
            //Remove unfillable positions (mainly useful for testing)
            reduceInput();
            if(precise){
                //disable pruning and timer
                partialCoverUpdated(new ArrayList<Integer>(), false, 0);
            } else {
                //enable pruning and timer
                partialCoverUpdated(new ArrayList<Integer>(), true, 0);
            }
        }

        System.out.println("DONE");
    }

    public void partialCoverDriver(ArrayList<Integer> tmp_branch_solution, int layer) {
        run++;

        //Stop when you found a solution
        if (!precise) {
            if ((timeElapsed = Duration.between(start, Instant.now()).toSeconds()) < maxSeconds) {
                //enable pruning and timer
                partialCoverUpdated(tmp_branch_solution, true, layer);
            }
        } else {
            //disable pruning and timer
            partialCoverUpdated(tmp_branch_solution, false, layer);
        }

    }

    private void partialCoverUpdated(ArrayList<Integer> tmp_branch_solution, boolean pruning, int layer) {
        //System.out.println("TMP: " + Arrays.toString(tmp_branch_solution.toArray()));

        //Step one of AlgX isn't used for partialCover

        //Chose the next column (deterministically) (Step 2 of AlgX)
        ColumnObject nextColumnObject = getSmallestColumnObject();

        //How valuable is the partial solution?
        int solutionScore = getSolutionScore(tmp_branch_solution);

        if(nextColumnObject == null){
            //Found a leaf node
            System.out.println("LEAF" + tmp_branch_solution.toString());

            //Check if this is the best solution so far
            if(solutionScore > bestScore){
                bestSolution = tmp_branch_solution.toArray();
                bestScore = solutionScore;
            }

            bestSolution = tmp_branch_solution.toArray();
        } else {
            System.out.println(layer);

            //Go down one layer in the search tree
            layer++;

            //Prune every x layers
            if(layer%pruneWait == 0){
                if((layer%pruneWait)>=maxValuePerLayer.size()){
                    //first time this layer is reached
                    maxValuePerLayer.add(solutionScore);
                } else {
                    //layer already exists
                    //check if the score is good enough for the given layer
                    if(solutionScore > pruneCutoff * maxValuePerLayer.get(layer%pruneWait)){
                        //continue branch
                        if(solutionScore > maxValuePerLayer.get(layer%pruneWait)){
                            //update highscore
                            maxValuePerLayer.set(layer%pruneWait, solutionScore);
                        }

                    } else {
                        System.out.println("Abandon branch");
                        //abandon branch
                        return;
                    }
                }
            }

            nextColumnObject.unlink();

            //Choose a row r such that Ar,c=1 (Step 3 of AlgX)
            for (DataObject row = nextColumnObject.down; row != nextColumnObject; row = row.down){
                partialCoverUpdated(row, tmp_branch_solution, nextColumnObject, pruning, layer);
            }

            nextColumnObject.link();

        }
    }

    private void partialCoverUpdated(DataObject focusRow, ArrayList<Integer> tmp_branch_solution, ColumnObject nextColumnObject, boolean pruning, int layer) {
        //Include row r in the partial solution (Step 4 of AlgX)
        tmp_branch_solution.add(focusRow.inputRow);

        //Unlink row and column (Step 5 of AlgX)
        for (DataObject column = focusRow.right; column != focusRow; column = column.right) {
            column.header.unlink();
        }

        //The driver will keep track of the timer and will call the other partialCover function
        partialCoverDriver(tmp_branch_solution, layer);

        //Undo step (re-link)
        tmp_branch_solution.remove(tmp_branch_solution.size() - 1);

        nextColumnObject = focusRow.header;

        for (DataObject left = focusRow.left; left != focusRow; left = left.left) {
            left.header.link();
        }
    }

    private void exactCoverUpdated(ArrayList<Integer> tmp_branch_solution) {
        System.out.println(Arrays.toString(tmp_branch_solution.toArray()));

        if(!foundSolution){
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

    public int getSolutionScore(ArrayList<Integer> solution) {
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


    ColumnObject smallestColumn;

    public void reduceInput() {
        //remove/unlink columns that can't be filled
        while ((smallestColumn = getSmallestColumnObject()).size == 0) {
            smallestColumn.unlink();
        }
    }

}