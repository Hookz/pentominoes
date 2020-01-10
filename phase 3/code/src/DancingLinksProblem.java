//https://www.geeksforgeeks.org/exact-cover-problem-algorithm-x-set-2-implementation-dlx/ was used
//https://stackoverflow.com/questions/39939710/java-how-to-implement-dancing-links-algorithm-with-doublylinkedlists
//https://github.com/benfowler/dancing-links/tree/master/dlx/src
//https://github.com/Warren-Partridge/algorithm-x/blob/master/DancingLinks.java
//https://www.ocf.berkeley.edu/~jchu/publicportal/sudoku/sudoku.paper.html
//https://github.com/benfowler/dancing-links/blob/master/dlx/src/main/java/au/id/bjf/dlx/DLX.java


// Data structures implemented from pg. 5 of Dancing Links paper

// Ok, just so it makes sense to me:
// Data Object = a dancing link. It has 2 circularly linked lists and a list header.
// Column Object = a column (a set of 1s that we may want to have in our exact cover). It contains DOs and has a name and # of DOs.

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
        //Create first header (root)
        ColumnObject root = new ColumnObject();
        root.left = root;
        root.right = root;
        root.up = root;
        root.down = root;
        root.name = headerNames[0];

        //create the other headers
        for(int x=1; x<inputMatrix[0].length; x++){
            ColumnObject header = new ColumnObject();
            header.up = header;
            header.down = header;
            header.right = root;
            header.left = root.left;
            root.left.right = header;
            root.left = header;
            header.name = headerNames[x];
        }

        //Create a list for the next row that gets added to the row above, repeat for all rows
        List<DataObject> RowObjects = new LinkedList<DataObject>();
        for(int y=1; y<inputMatrix.length; y++){
            //remove data from last row
            RowObjects.clear();

            ColumnObject currentColumn = (ColumnObject) root.right;

            for(int x=0; x<inputMatrix[0].length; x++){
                //if there's a value to add, add it to the row
                if(inputMatrix[y][x]){
                    DataObject dataObject = new DataObject();
                    dataObject.up = currentColumn.up;
                    dataObject.down = currentColumn;
                    dataObject.left = dataObject;
                    dataObject.right = dataObject;
                    dataObject.header = currentColumn;
                    currentColumn.up.down = dataObject;
                    currentColumn.up = dataObject;
                    currentColumn.size++;

                    RowObjects.add(dataObject);
                }

                currentColumn = (ColumnObject) currentColumn.right;
            }

            //Link all of the data objects in this row horizontally
            if(RowObjects.size()>0){
                Iterator<DataObject> iterator = RowObjects.iterator();
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

    List<DataObject> solutions;
    boolean foundSolution = false;

    public void solve(int K) { // Deterministic algorithm to find all exact covers
        //Stop when you found a solution
        while(!foundSolution){
            if (root.right == root) {
                //SOLVED IT!
                System.out.println("FULLY COVERED");
                foundSolution = true;

                return;
            }

            ColumnObject nextColumnObject = getSmallestColumnObject();
            nextColumnObject.cover();

            for (DataObject r = nextColumnObject.down; r != nextColumnObject; r = r.down) {
                solutions.add(r);

                for (DataObject j = r.right; j != r; j = j.right) {
                    j.header.cover();
                }

                solve(K + 1);
                r = solutions.remove(solutions.size() - 1);
                nextColumnObject = r.header;

                for (DataObject j = r.left; j != r; j = j.left) {
                    j.header.uncover();
                }
            }

            nextColumnObject.uncover();

            return;
        }

    }

//    void cover(ColumnObject header, ColumnObject columnObject){
//        columnObject.right.left = columnObject.left;
//        columnObject.left.right = columnObject.right;
//        DataObject i = columnObject.down;
//        while (i != columnObject) {
//            DataObject j = i.right;
//            while (j != i) {
//                j.down.up = j.up;
//                j.up.down = j.down;
//                ((ColumnObject)j.header).size--;
//                j = j.right;
//            }
//
//            i = i.down;
//        }
//    }
//
//    void uncover(ColumnObject header, ColumnObject columnObject) {
//        DataObject i = columnObject.up;
//        while (i != columnObject) {
//            DataObject j = i.left;
//            while (j != i) {
//                ((ColumnObject)j.header).size++;
//                j.down.up = j;
//                j.up.down = j;
//                j = j.left;
//            }
//
//            i = i.up;
//        }
//        columnObject.right.left = columnObject;
//        columnObject.left.right = columnObject;
//    }

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