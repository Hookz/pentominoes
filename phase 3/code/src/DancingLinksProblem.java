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

import java.util.List;

public class DancingLinksProblem {
    boolean[][] inputMatrix;
    DataObject[][] dataStructure;
    String[] headerNames;

    public DancingLinksProblem(boolean[][] inputMatrix, String[] headerNames) {
        this.inputMatrix = inputMatrix;
        this.dataStructure = new DataObject[inputMatrix.length][inputMatrix[0].length];
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
        for(int x=1; x<=inputMatrix.length; x++){
            ColumnObject header = new ColumnObject();
            header.up = header;
            header.down = header;
            header.right = header;
            header.left = root.left;
            root.left.right = root;
            root.left = header;
            header.name = headerNames[x];
        }

        //TODO continue on line 72

    }

    private ColumnObject root; // Special CO, labeled "h" in the paper
    private List<DataObject> solutions;
    private List<DataObject> answer;
    private int numSolutionsFound = 0;

    private void search(int K) { // Deterministic algorithm to find all exact covers
        //Stop when you found a solution
        while(numSolutionsFound == 0){
            if (root.right == root) {
                //numSolutionsFound++;

                System.out.println("FULLY COVERED");

                return;
            }

            ColumnObject c = getSmallestColumnObject();
            c.cover();

            for (DataObject r = c.down; r != c; r = r.down) {
                solutions.add(r);

                for (DataObject j = r.right; j != r; j = j.right) {
                    j.C.cover();
                }

                search(K + 1);
                r = solutions.remove(solutions.size() - 1);
                c = r.C;

                for (DataObject j = r.left; j != r; j = j.left) {
                    j.C.uncover();
                }
            }

            c.uncover();

            return;
        }

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