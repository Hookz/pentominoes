public class ParcelType {

    private String name;
    private double ratio;
    private double value;
    private int rotations;
    private int color;
    private int placed;
    private int maximum;

    ParcelType(String n, double r, double v, int ro, int c, int p, int m) {
        this.name = n;
        this.ratio = r;
        this.value = v;
        this.rotations = ro;
        this.color = c;
        this.placed = p;
        this.maximum = m;
    }

    public String getName() {
        return this.name;
    }

    public double getRatio() {
        return this.ratio;
    }

    public double getValue() {
        return this.value;
    }

    public int getRotations() {
        return this.rotations;
    }

    public int getColor() {
        return this.color;
    }

    public int getPlaced() {
        return this.placed;
    }

    public int getMaximum() {
        return this.maximum;
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setRatio(double r){
        this.ratio = r;
    }

    public void setValue(double v) {
        this.value = v;
    }

    public void setRotations(int ro) {
        this.rotations = ro;
    }

    public void setColor(int c) {
        this.color = c;
    }

    public void setPlaced(int p) {
        this.placed = p;
    }

    public void setMaximum(int m) {
        this.maximum = m;
    }

}