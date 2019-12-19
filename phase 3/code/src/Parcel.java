import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Parcel extends Box {
    final double xCenter;
    final double yCenter;
    final double zCenter;
    final double width;
    final double height;
    final double depth;
    PhongMaterial item_material = new PhongMaterial();
    Color item_color;

    public Parcel(double xCenter, double yCenter, double zCenter, double width, double height, double depth) {
        super(width, height, depth);
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.zCenter = zCenter;
        this.width = width;
        this.height = height;
        this.depth = depth;

        this.setTranslateX(xCenter);
        this.setTranslateY(yCenter);
        FX3D.createBoxLines(width, height, depth, xCenter-width/2, yCenter-height/2, -depth/2);

        item_color = Color.rgb((int) (Math.random()*255), (int) (Math.random()*255), (int) (Math.random()*255), 1);
        item_material.setDiffuseColor(item_color);
        this.setMaterial(item_material);

        this.setTranslateX(xCenter);
        this.setTranslateY(yCenter);
    }


}
