import java.util.ArrayList;
import java.util.Comparator;

public class GreedyAlgorithm {

    static ParcelType typeA; // Object that represents parcel type A
    static ParcelType typeB; // Object that represents parcel type B
    static ParcelType typeC; // Object that represents parcel type C
    static ArrayList<ParcelType> parcelTypes; // ArrayList that stores all ParcelType objects
    static double[] volumes = {2.0, 3.0, 3.375}; // Array that stores the volumes of parcel types A(volumes[0]), B(volumes[1]) and C(volumes[2])

    public static void runAlgorithm() {
        // Initialize ParcelType objects
        typeA = new ParcelType("A", 0.0);
        typeB = new ParcelType("B", 0.0);
        typeC = new ParcelType("C", 0.0);

        // Initialize parcelTypes ArrayList
        parcelTypes = new ArrayList<ParcelType>();

        // Add ParcelType objects to parcelTypes ArrayList
        parcelTypes.add(typeA);
        parcelTypes.add(typeB);
        parcelTypes.add(typeC);

        // Calculate the ratios
        calcRatio();

        // Determine the proper order of the parcel types based on decreasing ratio
        deterOrder();

        // Debug
        System.out.println("Most profitable parcel type: " + parcelTypes.get(0).getName());
        System.out.println("Second most profitable parcel type: " + parcelTypes.get(1).getName());
        System.out.println("Third most profitable parcel type: " + parcelTypes.get(2).getName());
        System.out.println();
    }

    public static void calcRatio() {
        // Parcel type A
        double valueA = Wrapper.inputDetails[0].value;
        typeA.setRatio(valueA / volumes[0]);

        // Parcel type B
        double valueB = Wrapper.inputDetails[1].value;
        typeB.setRatio(valueB / volumes[1]);

        // Parcel type C
        double valueC = Wrapper.inputDetails[2].value;
        typeC.setRatio(valueC / volumes[2]);
    }

    public static void deterOrder() {
        // Sort the ParcelType objects based on decreasing ratio
        parcelTypes.sort(new RatioSorter());
    }

    // A nested class which acts as a comparator
    static class RatioSorter implements Comparator<ParcelType> {
        @Override
        public int compare(ParcelType o1, ParcelType o2) {
            return Double.compare(o2.getRatio(), o1.getRatio());
        }
    }

}