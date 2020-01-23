package Phase3;
class DataObject {
    DataObject left, right, up, down;
    ColumnObject header;
    int inputRow;

    DataObject() {
        left = right = up = down = this;
        inputRow = -1;
    }

    DataObject(int inputRow) {
        left = right = up = down = this;
        this.inputRow = inputRow;
    }

    /**
     * Method that unlinks a DataObject from a row
     */
    void unlinkFromRow() {
        this.left.right = this.right;
        this.right.left = this.left;
    }

    /**
     * Method that links a DataObject back to a row
     */
    void relinkToRow() {
        this.left.right = this.right.left = this;
    }


    /**
     * Method that unlinks a DataObject from a column
     */
    void unlinkFromColumn() {
        this.up.down = this.down;
        this.down.up = this.up;
        this.header.size--;
    }

    /**
     * Method that links a DataObject back to a column
     */
    void relinkToColumn() {
        this.up.down = this.down.up = this;
        this.header.size++;
    }

}