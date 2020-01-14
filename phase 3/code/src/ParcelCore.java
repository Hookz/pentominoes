import javafx.geometry.Point3D;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;


public abstract class ParcelCore implements Parcel {
    private int value;
    private Point3D origin;


    protected ParcelCore(int value, Point3D origin){
        this.value = value;
        this.origin = origin;
    }
    public int getValue(){
        return value;
    }
    @Override
    public Point3D getOrigin() {
        return origin;
    }

    @Override
    public Box[] toBox(double scale) {
        Cube[] cubes = toCubes();
        Box[] boxes = new Box[cubes.length];
        Point3D origin = getOrigin();
        for (int i=0; i < boxes.length; i++) {
            boxes[i] = cubes[i].toBox();
            boxes[i].setWidth(scale * cubes[i].getWidth());
            boxes[i].setHeight(scale * cubes[i].getHeight());
            boxes[i].setDepth(scale * cubes[i].getLength());
            boxes[i].setDrawMode(DrawMode.FILL);
            boxes[i].setTranslateX(scale * (cubes[i].getOrigin().getY()));
            boxes[i].setTranslateY(-scale * (cubes[i].getOrigin().getZ()));
            boxes[i].setTranslateZ(scale * (cubes[i].getOrigin().getX()));
        }
        return boxes;
    }

    protected abstract Cube[] toCubes();

    public abstract void rotateLength();
    public abstract void rotateWidth();
    public abstract void rotateHeight();

}
