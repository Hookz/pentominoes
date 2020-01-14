import javafx.geometry.Point3D;

import java.util.ArrayList;

public class PentominoParcel extends ParcelCore {
    private ArrayList<Point3D> relative_origin_points;
    private int length;
    private int width;
    private int height;

    private Cube[] cubes;
    private boolean cubes_calculated = false;

    protected PentominoParcel(int length, int width, int height, ArrayList<Point3D> points_to_copy, int value, Point3D origin) {
        super(value, origin);
        this.length = length;
        this.width = width;
        this.height = height;
        relative_origin_points = new ArrayList<Point3D>(points_to_copy.size());
        for (Point3D p : points_to_copy) relative_origin_points.add(p.add(0,0,0));
    }


    private void calculateCubes() {
        if (cubes==null) cubes = new Cube[relative_origin_points.size()];
        for (int i=0; i < cubes.length; i++)
            cubes[i] = new Cube(1, relative_origin_points.get(i));
        cubes_calculated = true;
    }


    public <T extends Parcel> T copy() {
        return (T) new PentominoParcel(length, width, height, relative_origin_points, getValue(), getOrigin());
    }
    @Override
    public int getVolume() {
        return relative_origin_points.size();
    }

    @Override
    protected Cube[] toCubes() {
        if (!cubes_calculated) calculateCubes();
        return cubes;
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

}
