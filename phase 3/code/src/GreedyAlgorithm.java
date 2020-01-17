import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

public class GreedyAlgorithm {

    static ParcelType typeA; // Object that represents parcel type A
    static ParcelType typeB; // Object that represents parcel type B
    static ParcelType typeC; // Object that represents parcel type C
    static ArrayList<ParcelType> parcelTypes; // ArrayList that stores all ParcelType objects
    static double[] volumes = {2.0, 3.0, 3.375}; // Array that stores the volumes of parcel types A(volumes[0]), B(volumes[1]) and C(volumes[2])
    int[][][] container = new int[Wrapper.CONTAINER_WIDTH*2][Wrapper.CONTAINER_HEIGHT*2][Wrapper.CONTAINER_DEPTH*2];
    static int[][][][] parcelARotations;
    static int[][][][] parcelBRotations;
    static int[][][][] parcelCRotations;

    public static void initContainer(int [][][] container) {
        for (int i = 0; i < container.length; i++) {
            for (int j = 0; j < container[i].length; j++) {
                for (int k = 0; k < container[i][j].length; k++) {
                    container[i][j][k] = 0;
                }
            }
        }
    }

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
        //System.out.println("Most profitable parcel type: " + parcelTypes.get(0).getName());
        //System.out.println("Second most profitable parcel type: " + parcelTypes.get(1).getName());
        //System.out.println("Third most profitable parcel type: " + parcelTypes.get(2).getName());
        //System.out.println();

        parcelARotations = new int[][][][] {
                {{{1,1,1,1},{1,1,1,1},{1,1,1,1}},{{1,1,1,1},{1,1,1,1},{1,1,1,1}}},
                {{{1,1,1},{1,1,1},{1,1,1},{1,1,1}},{{1,1,1},{1,1,1},{1,1,1},{1,1,1}}},
                {{{1,1},{1,1},{1,1}},{{1,1},{1,1},{1,1}},{{1,1},{1,1},{1,1}},{{1,1},{1,1},{1,1}}},
                {{{1,1,1,1},{1,1,1,1}},{{1,1,1,1},{1,1,1,1}},{{1,1,1,1},{1,1,1,1}}},
                {{{1,1,1},{1,1,1}},{{1,1,1},{1,1,1}},{{1,1,1},{1,1,1}},{{1,1,1},{1,1,1}}},
                {{{1,1},{1,1},{1,1},{1,1}},{{1,1},{1,1},{1,1},{1,1}},{{1,1},{1,1},{1,1},{1,1}}}
        };
        parcelBRotations = new int[][][][]{
                {{{1,1,1,1},{1,1,1,1}},{{1,1,1,1},{1,1,1,1}}},
                {{{1,1},{1,1},{1,1},{1,1}},{{1,1},{1,1},{1,1},{1,1}}},
                {{{1,1},{1,1}},{{1,1},{1,1}},{{1,1},{1,1}},{{1,1},{1,1}}}
        };
        parcelCRotations = new int[][][][]
                {
                        {{{1,1,1},{1,1,1},{1,1,1}},{{1,1,1},{1,1,1},{1,1,1}},{{1,1,1},{1,1,1},{1,1,1}}}
                };
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

    public static boolean checkParcel(int[][][] solid, int[] coord){
        if (coord[0] < 0 || coord[1] < 0 || coord[2] < 0) {
            return true;
        } else {
            try {
                for (int i = 0; i < solid.length; i++) { // loop over x position of pentomino
                    for (int j = 0; j < solid[i].length; j++) { // loop over y position of pentomino
                        for (int k = 0; k < solid[i][j].length; k++) {
                            if (solid[i][j][k] == 1) {
                                if (FX3D.UIInput[coord[0] + i][coord[1] + j][coord[2] + k] != 0) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                return true;
            }
        }
        return false;
    }

    public static void addSolid(int[][][] solid, int[] coord, int color) { //add the solid to the container at the specified coordinates
        if (!checkParcel(solid, coord)) {
            for (int i = 0; i < solid.length; i++) {
                for (int j = 0; j < solid[i].length; j++) {
                    for (int k = 0; k < solid[i][j].length; k++) {
                        if (solid[i][j][k] == 1) {
                            FX3D.tmpUIInput[coord[0] + i][coord[1] + j][coord[2] + k] = color;
                        }
                    }
                }
            }
        } else {
            System.out.println("Can't place object");
        }
    }

}