//https://www.geeksforgeeks.org/exact-cover-problem-algorithm-x-set-2-implementation-dlx/ was used
//https://stackoverflow.com/questions/39939710/java-how-to-implement-dancing-links-algorithm-with-doublylinkedlists
//https://github.com/benfowler/dancing-links/tree/master/dlx/src
//https://github.com/Warren-Partridge/algorithm-x/blob/master/DancingLinks.java
//https://www.ocf.berkeley.edu/~jchu/publicportal/sudoku/sudoku.paper.html
//https://github.com/benfowler/dancing-links/blob/master/dlx/src/main/java/au/id/bjf/dlx/DLX.java
//http://sudopedia.enjoysudoku.com/Dancing_Links.html

//LOOK AT 4x4.dlx.64x64 (1) FOR AN SUDOKU EXAMPLE (phase 3 -> Research -> this)

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DancingLinksProblem {
    boolean[][] inputMatrix;
    DataObject root;
    String[] headerNames;

    public DancingLinksProblem(boolean[][] inputMatrix, String[] headerNames) {
        this.inputMatrix = inputMatrix;
        this.headerNames = headerNames;
    }

    public void createDataStructure(){
        //Create root
        ColumnObject root = new ColumnObject();
        root.left = root;
        root.right = root;
        root.up = root;
        root.down = root;

        //create the headers
        for(int x=0; x<inputMatrix[0].length; x++){
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
        for(int y=0; y<inputMatrix.length; y++){
            //remove data from last row
            rowObjects.clear();

            //Go to the first column
            ColumnObject currentColumn = (ColumnObject) root.right;

            for(int x=0; x<inputMatrix[0].length; x++){
                //if there's a value to add, add it to the row
                if(inputMatrix[y][x]){
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
            if(rowObjects.size()>0){
                Iterator<DataObject> iterator = rowObjects.iterator();
                DataObject first = iterator.next();

                //while there are objects left in the row, keep going trough them
                while (iterator.hasNext()){
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
    boolean foundSolution = false;

    List<DataObject> bestSolution = new ArrayList<DataObject>();
    int bestScore = 0;

    int maxRuns = 1000;
    int run = 0;

    public void solve(int K) { // Deterministic algorithm to find all exact covers
        //Stop when you found a solution
        while(!foundSolution && run<maxRuns){
            run++;

            //Check if you have covered all
            //TODO add partial and full-cover mode
            if (root.right == root) {
                //SOLVED IT!
                System.out.println("FULLY COVERED");
                foundSolution = true;

                /*
                //Check if the full cover also gives the highest score

                int score = calculateScore(tmpSolution);
                if(score > bestScore){
                    //Assign new best solution
                    bestSolution = new ArrayList<DataObject>(tmpSolution);
                    bestScore = score;
                }*/

                return;
            }

            System.out.println("a");
            System.out.println(bestScore);

            //Get the shape with the least filled cells
            ColumnObject nextColumnObject = getSmallestColumnObject();

            //If this is a dead end
            if(nextColumnObject.size == 0){

            }

            nextColumnObject.unlink();

            //Remove covered elements
            for (DataObject row = nextColumnObject.down; row != nextColumnObject; row = row.down) {
                tmpSolution.add(row);

                System.out.println("b");
                System.out.println(tmpSolution.size());

                //There hasn't been a full cover, but check if it's the best cover so far
                int score = calculateScore(tmpSolution);
                if(score > bestScore){
                    //Assign new best solution
                    bestSolution = new ArrayList<DataObject>(tmpSolution);
                    bestScore = score;
                }


                for (DataObject column = row.right; column != row; column = column.right) {
                    column.header.unlink();
                }

                solve(K + 1);
                row = tmpSolution.remove(tmpSolution.size() - 1);
                nextColumnObject = row.header;

                for (DataObject column = row.left; column != row; column = column.left) {
                    column.header.link();
                }
            }

            nextColumnObject.link();
        }

    }

    private int calculateScore(List<DataObject> tmpSolution){
        //TODO
        tmpSolution.size();

        return tmpSolution.size();
    }

    private ColumnObject getSmallestColumnObject() {
        int min = Integer.MAX_VALUE;
        ColumnObject smallestCO = null;

        // Search for the min size ColumnObject by iterating through all ColumnObjects by moving right until we end up back at the header
        //every node in headers
        for(ColumnObject col = (ColumnObject) root.right; col != root; col = (ColumnObject) col.right){
            if (col.size < min) {
                min = col.size;
                smallestCO = col;
            }
        }

        return smallestCO;
    }

}