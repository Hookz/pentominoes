import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Pentomino{
    final double xCenter;
    final double yCenter;
    final double zCenter;
    final double xStart;
    final double yStart;
    final double zStart;

    PhongMaterial item_material = new PhongMaterial();
    Color item_color;
    Group shape = new Group();
    Box[] cells = new Box[5];

    final int CEL_SIZE = 100;

    //ONLY L, P, T PENTOMINOES ARE REQUIRED
    //TODO add rotations
    //L
    public Pentomino(double xStart, double yStart, double zStart) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.zStart = zStart;

        this.xCenter = xStart + CEL_SIZE/2;
        this.yCenter = yStart + CEL_SIZE/2;
        this.zCenter = zStart + CEL_SIZE/2;

        item_color = Color.rgb((int) (Math.random()*255), (int) (Math.random()*255), (int) (Math.random()*255), 1);
        item_material.setDiffuseColor(item_color);

        //create cells and move them
        for(int i=0; i<5; i++){
            cells[i] = new Box(CEL_SIZE, CEL_SIZE, CEL_SIZE);

            cells[i].setTranslateX(xCenter);
            cells[i].setTranslateY(yCenter + i*CEL_SIZE);
            cells[i].setTranslateZ(zCenter);
        }

        //add cells to the group
        for(int i=0; i<5; i++){
            cells[i].setMaterial(item_material);
            shape.getChildren().add(cells[i]);
            FX3D.contentGroup.getChildren().add(cells[i]);
        }

        //create the outline
        //front
        FX3D.createLine(new Point3D(0, 0, 0), new Point3D(CEL_SIZE, 0, 0));
        FX3D.createLine(new Point3D(CEL_SIZE, 0, 0), new Point3D(CEL_SIZE, 5*CEL_SIZE, 0));
        FX3D.createLine(new Point3D(CEL_SIZE, 5*CEL_SIZE, 0), new Point3D(0, 5*CEL_SIZE, 0));
        FX3D.createLine(new Point3D(0, 5*CEL_SIZE, 0), new Point3D(0, 0, 0));

        //back
        FX3D.createLine(new Point3D(0, 0, CEL_SIZE), new Point3D(CEL_SIZE, 0, CEL_SIZE));
        FX3D.createLine(new Point3D(CEL_SIZE, 0, CEL_SIZE), new Point3D(CEL_SIZE, 5*CEL_SIZE, CEL_SIZE));
        FX3D.createLine(new Point3D(CEL_SIZE, 5*CEL_SIZE, CEL_SIZE), new Point3D(0, 5*CEL_SIZE, CEL_SIZE));
        FX3D.createLine(new Point3D(0, 5*CEL_SIZE, CEL_SIZE), new Point3D(0, 0, CEL_SIZE));

        //sides
        FX3D.createLine(new Point3D(0, 0, 0), new Point3D(0, 0, CEL_SIZE));
        FX3D.createLine(new Point3D(CEL_SIZE, 0, 0), new Point3D(CEL_SIZE, 0, CEL_SIZE));
        FX3D.createLine(new Point3D(CEL_SIZE, 5*CEL_SIZE, 0), new Point3D(CEL_SIZE, 5*CEL_SIZE, CEL_SIZE));
        FX3D.createLine(new Point3D(0, 5*CEL_SIZE, 0), new Point3D(0, 5*CEL_SIZE, CEL_SIZE));
    }


}
