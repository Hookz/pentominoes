class DataObject {
    DataObject left, right, up, down;
    ColumnObject header;

    DataObject() {
        left = right = up = down = this;
    }

    DataObject addToRow(DataObject newDataObject) { // Append new DataObject to the right and return it
        this.right.left = newDataObject;
        newDataObject.left = this;
        newDataObject.right = this.right;
        this.right = newDataObject;

        return newDataObject;
    }

    DataObject addToColumn(DataObject newDataObject) { // Append new DataObject below and return it
        this.down.up = newDataObject;
        newDataObject.up = this;
        newDataObject.down = this.down;
        this.down = newDataObject;

        return newDataObject;
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