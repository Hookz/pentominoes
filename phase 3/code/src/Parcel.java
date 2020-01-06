import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

/* TODO
Should there be separate subclasses for parcelA, parcelB and parcelC or should it stay the way it's now?
 */

public class Parcel extends Box{
    final double xCenter;
    final double yCenter;
    final double zCenter;
    final double width;
    final double height;
    final double depth;
    final double xStart;
    final double yStart;
    final double zStart;
    PhongMaterial item_material = new PhongMaterial();
    Color item_color;

    public Parcel(double xStart, double yStart, double zStart, double width, double height, double depth) {
        super(width, height, depth);

        this.xStart = xStart;
        this.yStart = yStart;
        this.zStart = zStart;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.xCenter = xStart + width/2;
        this.yCenter = yStart + height/2;
        this.zCenter = zStart + depth/2;

        this.setTranslateX(xCenter);
        this.setTranslateY(yCenter);
        this.setTranslateZ(zCenter);
        FX3D.createBoxLines(width, height, depth, xStart, yStart, zStart);

        item_color = Color.rgb((int) (Math.random()*255), (int) (Math.random()*255), (int) (Math.random()*255), 1);
        item_material.setDiffuseColor(item_color);
        this.setMaterial(item_material);

        this.setTranslateX(xCenter);
        this.setTranslateY(yCenter);
    }
}
