class DataObject {
    DataObject left, right, up, down;
    ColumnObject header;
    int inputRow = -1;

    DataObject() {
        left = right = up = down = this;
    }

    DataObject(int inputRow) {
        left = right = up = down = this;
        this.inputRow = inputRow;
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
        this.header.size--;
    }

    void relinkToColumn() {
        this.up.down = this.down.up = this;
        this.header.size++;
    }

}