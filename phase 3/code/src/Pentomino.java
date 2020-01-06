import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

/* TODO
Should there be separate subclasses for pentL, pentP and pentT?
 */

public class Pentomino{
    final double xCenter;
    final double yCenter;
    final double zCenter;
    final double xStart;
    final double yStart;
    final double zStart;

    final char type;
    final int rotation;

    PhongMaterial item_material = new PhongMaterial();
    Color item_color;
    Group shape = new Group();
    Box[] cells = new Box[5];

    final int CEL_SIZE = 50;

    //ONLY L, P, T PENTOMINOES ARE REQUIRED
    //TODO add rotations
    //L
    public Pentomino(double xStart, double yStart, double zStart, char type, int rotation) {
        //TODO finish rotations


        this.xStart = xStart;
        this.yStart = yStart;
        this.zStart = zStart;

        this.xCenter = xStart + CEL_SIZE/2;
        this.yCenter = yStart + CEL_SIZE/2;
        this.zCenter = zStart + CEL_SIZE/2;

        this.type = type;
        this.rotation = rotation;

        item_color = Color.rgb((int) (Math.random()*255), (int) (Math.random()*255), (int) (Math.random()*255), 1);
        item_material.setDiffuseColor(item_color);

        /*Rotations are as follows
        1: No rotation
        2: Turn clockwise 90
        3: Turn clockwise 180
        4: Turn clockwise 270
        5: Make it orthogonal to the back
        6: Make it orthogonal to the front
        7:
        8:
         */

        switch (type){
            case 'L':
                switch (rotation) {
                    case 1:
                        //create cells and move them
                        for(int i=0; i<5; i++){
                            cells[i] = new Box(CEL_SIZE, CEL_SIZE, CEL_SIZE);

                            cells[i].setTranslateX(xCenter);
                            cells[i].setTranslateY(yCenter + i*CEL_SIZE);
                            cells[i].setTranslateZ(zCenter);
                            FX3D.createBoxLines(CEL_SIZE, CEL_SIZE, CEL_SIZE, xStart, yStart+i*CEL_SIZE, zStart);
                        }

                        break;
                }

                break;

            case 'P':
                break;
            case 'T':
                break;


        }

        //add cells to the group
        for(int i=0; i<5; i++){
            cells[i].setMaterial(item_material);
            shape.getChildren().add(cells[i]);
            FX3D.contentGroup.getChildren().add(cells[i]);
        }
    }
}
