import javafx.geometry.Point3D;

public class SimpleParcel extends ParcelCore {
    private Cube shape;

    public SimpleParcel(int length, int width, int height, int value, Point3D origin) {
        super(value,origin);
        shape = new Cube(length, width, height, origin);
    }

    @Override
    public <T extends Parcel> T copy() {
        SimpleParcel copy =  new SimpleParcel(shape.getLength(), shape.getWidth(), shape.getHeight(), getValue(), this.getOrigin());
        return (T) copy;
    }

    @Override
    public int getVolume() {
        return shape.getVolume();
    }

    @Override
    public void rotateLength() {
        if (shape.isCube()) return;
        // rotating axis stays the same, other two axis swap
        shape = new Cube(shape.getLength(), shape.getHeight(), shape.getWidth(), shape.getOrigin());
    }
    @Override
    public void rotateWidth() {
        if (shape.isCube()) return;
        // rotating axis stays the same, other two axis swap
        shape = new Cube(shape.getHeight(), shape.getWidth(), shape.getLength(), shape.getOrigin());
    }
    @Override
    public void rotateHeight() {
        if (shape.isCube()) return;
        // rotating axis stays the same, other two axis swap
        shape = new Cube(shape.getWidth(), shape.getLength(), shape.getHeight(), shape.getOrigin());
    }

    public int getLength() {
        return shape.getLength();
    }
    public int getWidth() {
        return shape.getWidth();
    }
    public int getHeight() {
        return shape.getHeight();
    }
    @Override
    protected Cube[] toCubes() {
        return new Cube[]{shape};
    }

}
