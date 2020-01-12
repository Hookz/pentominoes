public class rowDetailNode extends DataObject {
    int size; // Number of 1s in the row
    int inputRow;
    String type;
    int value;
    float density;
    int x;
    int y;
    int z;
    int rotation;

    rowDetailNode() {
        super(); // Inherit left, right, up, down, C from dataObject
        this.header = null;
        this.size = 0;
        this.inputRow = 0;
        this.type = "";
        this.value = 0;
        this.density = value/size;
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.rotation = 0;
    }

    rowDetailNode(int size, int inputRow, String type, int value, int x, int y, int z, int rotation) {
        super(); // Inherit left, right, up, down, C from dataObject
        this.header = null;
        this.size = size;
        this.inputRow = inputRow;
        this.type = type;
        this.value = value;
        this.density = value/size;
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotation = rotation;
    }

    //TODO rewrite
    void unlink() {
        this.unlinkFromRow();

        for (DataObject i = this.down; i != this.header; i = i.down) {
            for (DataObject j = i.right; j != i; j = j.right) {
                j.unlinkFromColumn();
            }
        }
    }

    void link() {
        for (DataObject i = this.up; i != this.header; i = i.up) {
            for (DataObject j = i.left; j != i; j = j.left) {
                j.relinkToColumn();
            }
        }

        this.relinkToRow();
    }
}