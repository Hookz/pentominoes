public class ParcelType {

    private String name;
    private double ratio;

    ParcelType(String n, double r) {
        this.name = n;
        this.ratio = r;
    }

    public String getName() {
        return this.name;
    }

    public double getRatio() {
        return this.ratio;
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setRatio(double r){
        this.ratio = r;
    }

}