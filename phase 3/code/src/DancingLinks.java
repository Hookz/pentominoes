// Data structures implemented from pg. 5 of Dancing Links paper

// Ok, just so it makes sense to me:
// Data Object = a dancing link. It has 2 circularly linked lists and a list header.
// Column Object = a column (a set of 1s that we may want to have in our exact cover). It contains DOs and has a name and # of DOs.

import java.util.List;

public class DancingLinks {
    class DataObject {
        DataObject left, right, up, down;
        ColumnObject C; // Knuth calls this property a "list header" in his paper but I am conflating the two objects into one for simplicity

        DataObject() {
            left = right = up = down = this;
        }

        DataObject(ColumnObject initC) {
            this();
            C = initC;
            // TODO: Stick on bottom of col by default?
        }

        DataObject appendToRow(DataObject newDO) { // Append new DO to the right and return it
            this.right.left = newDO;
            newDO.left = this;
            newDO.right = this.right;
            this.right = newDO;

            return newDO;
        }

        DataObject appendToColumn(DataObject newDO) { // Append new DO below and return it
            this.down.up = newDO;
            newDO.up = this;
            newDO.down = this.down;
            this.down = newDO;

            return newDO;
        }

        void unlinkFromRow() {
            this.left.right = this.right;
            this.right.left = this.left;
        }

        void relinkToRow() {
            this.left.right = this.right.left = this;
        }

        void unlinkFromColumn() {
            this.up.down = this.down;
            this.down.up = this.up;
            this.C.size--;
        }

        void relinkToColumn() {
            this.up.down = this.down.up = this;
            this.C.size++;
        }

    }

    class ColumnObject extends DataObject {
        //DataObject left, right, up, down;
        //ColumnObject C;
        String name;
        int size; // Number of 1s in the column

        ColumnObject(String initName) {
            super(); // Inherit left,right,up,down,C from dataobject
            C = this;
            name = initName;
            size = 0;
        }

        void cover() {
            this.unlinkFromRow();

            for (DataObject i = this.down; i != this.C; i = i.down) {
                for (DataObject j = i.right; j != i; j = j.right) {
                    j.unlinkFromColumn();
                }
            }
        }

        void uncover() {
            for (DataObject i = this.up; i != this.C; i = i.up) {
                for (DataObject j = i.left; j != i; j = j.left) {
                    j.relinkToColumn();
                }
            }

            this.relinkToRow();
        }

    }

    private ColumnObject root; // Special CO, labeled "h" in the paper
    private List<DataObject> solutions;
    private int numSolutionsFound = 0;

    private void search(int K) { // Deterministic algorithm to find all exact covers
        if (root.right == root) {
            numSolutionsFound++;

            // TODO: Print the current solution

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

        // Pseudocode from the paper:

//        If R[h] = h, print the current solution (see below) and return.
//        Otherwise choose a column object c (see below).
//        Cover column c (see below).
//        For each r ← D[c], D[D[c]], ... , while r != c,
//          set Ok ← r;
//          for each j ← R[r], R[R[r]], ... , while j != r,
//            cover column j (see below);
//          search(k + 1);
//          set r ← Ok and c ← C[r];
//          for each j ← L[r], L[L[r]], ... , while j != r,
//            uncover column j (see below).
//        Uncover column c (see below) and return.
    }

    private ColumnObject getSmallestColumnObject(ColumnObject root) {
        int min = Integer.MAX_VALUE;
        ColumnObject smallestCO = null;

        // Search for the min size CO by iterating through all COs by moving right until we end up back at the header
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