import javafx.geometry.Point3D;
import javafx.scene.shape.Box;

public interface Parcel {
    Point3D getOrigin();
    int getValue();
    <T extends Parcel> T copy();
    int getVolume();
    int getLength();
    int getWidth();
    int getHeight();
    Box[] toBox(double scale);
}
