import javafx.geometry.Point3D;
import javafx.scene.shape.Box;

public class Cube {

    private Point3D origin;
    private final int length;
    private final int width;
    private final int height;

    public Cube(int length, int width, int height, Point3D origin) {
        this.origin = origin;
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public Box toBox() {
        Box box = new Box(length, width, height);
        Point3D origin = getOrigin();
        box.setTranslateX(origin.getX());
        box.setTranslateY(origin.getY());
        box.setTranslateZ(origin.getZ());
        return box;
    }

    public int getVolume() {
        return length * width * height;
    }

    public Point3D getOrigin() {
        return origin;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isCube() {
        return length==width && width==height;
    }
}
