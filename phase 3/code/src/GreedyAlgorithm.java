import java.util.ArrayList;
import java.util.Comparator;

public class GreedyAlgorithm {

    static ParcelType typeA; // Object that represents parcel type A
    static ParcelType typeB; // Object that represents parcel type B
    static ParcelType typeC; // Object that represents parcel type C
    static ArrayList<ParcelType> parcelTypes; // ArrayList that stores all ParcelType objects
    static double[] volumes = {2.0, 3.0, 3.375}; // Array that stores the volumes of parcel types A(volumes[0]), B(volumes[1]) and C(volumes[2])
    static int[][][][] parcelARotations; // Four-dimensional array that holds all possible rotations for parcel type A
    static int[][][][] parcelBRotations; // Four-dimensional array that holds all possible rotations for parcel type B
    static int[][][][] parcelCRotations; // Four-dimensional array that holds all possible rotations for parcel type C

    public static void runAlgorithm() {
        // Initialize ParcelType objects
        typeA = new ParcelType("A", 0.0);
        typeB = new ParcelType("B", 0.0);
        typeC = new ParcelType("C", 0.0);

        // Initialize parcelTypes ArrayList
        parcelTypes = new ArrayList<ParcelType>();

        // Initialize parcelARotations
        parcelARotations = new int[][][][] {
                {{{1,1,1,1},{1,1,1,1},{1,1,1,1}},{{1,1,1,1},{1,1,1,1},{1,1,1,1}}},
                {{{1,1,1},{1,1,1},{1,1,1},{1,1,1}},{{1,1,1},{1,1,1},{1,1,1},{1,1,1}}},
                {{{1,1},{1,1},{1,1}},{{1,1},{1,1},{1,1}},{{1,1},{1,1},{1,1}},{{1,1},{1,1},{1,1}}},
                {{{1,1,1,1},{1,1,1,1}},{{1,1,1,1},{1,1,1,1}},{{1,1,1,1},{1,1,1,1}}},
                {{{1,1,1},{1,1,1}},{{1,1,1},{1,1,1}},{{1,1,1},{1,1,1}},{{1,1,1},{1,1,1}}},
                {{{1,1},{1,1},{1,1},{1,1}},{{1,1},{1,1},{1,1},{1,1}},{{1,1},{1,1},{1,1},{1,1}}}
        };

        // Initialize parcelBRotations
        parcelBRotations = new int[][][][] {
                {{{1,1,1,1},{1,1,1,1}},{{1,1,1,1},{1,1,1,1}}},
                {{{1,1},{1,1},{1,1},{1,1}},{{1,1},{1,1},{1,1},{1,1}}},
                {{{1,1},{1,1}},{{1,1},{1,1}},{{1,1},{1,1}},{{1,1},{1,1}}}
        };

        // Initialize parcelCRotations
        parcelCRotations = new int[][][][] {
                        {{{1,1,1},{1,1,1},{1,1,1}},{{1,1,1},{1,1,1},{1,1,1}},{{1,1,1},{1,1,1},{1,1,1}}}
        };

        // Add ParcelType objects to parcelTypes ArrayList
        parcelTypes.add(typeA);
        parcelTypes.add(typeB);
        parcelTypes.add(typeC);

        // Calculate the ratios
        calcRatio();

        // Determine the proper order of the parcel types based on decreasing ratio
        deterOrder();

        // Fill the container
        fillContainer();
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

    public static void fillContainer() {
        // For every parcel type
        for(int p = 0; p < parcelTypes.size(); p++) {
            // For every rotation of this parcel type
            for(int r = 0; r < fetchAmountOfRotations(p); r++) {
                // For every x-position
                for(int x = 0; x < Wrapper.UIInput.length; x++) {
                    // For every y-position
                    for(int y = 0; y < Wrapper.UIInput[0].length; y++) {
                        // For every z-position
                        for(int z = 0; z < Wrapper.UIInput[0][0].length; z++) {
                            // Fetch array that represents the current piece
                            int[][][] solid = fetchArray(p, r);

                            // Create array that holds the current coordinates
                            int[] coord = {x, y, z};

                            // Check whether or not the current piece can be placed and take the appropriate action
                            addSolid(solid, coord, 1);
                        }
                    }
                }
            }
        }
    }

    public static boolean checkParcel(int[][][] solid, int[] coord) {
        //TODO Find out what is going wrong within this try-catch
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
                System.out.println(e.fillInStackTrace());
                return true;
            }
        }
        return false;
    }

    static int amount = 0;
    public static void addSolid(int[][][] solid, int[] coord, int color) { //add the solid to the container at the specified coordinates
        amount++;
        if (!checkParcel(solid, coord)) {
            for (int i = 0; i < solid.length; i++) {
                for (int j = 0; j < solid[i].length; j++) {
                    for (int k = 0; k < solid[i][j].length; k++) {
                        if (solid[i][j][k] == 1) {
                            FX3D.tmpUIInput[coord[0] + i][coord[1] + j][coord[2] + k] = color;
                            System.out.println("Item #" + amount + "placed");
                        }
                    }
                }
            }
        } else {
            System.out.println("Can't place object #" + amount);
        }
    }

    public static int[][][] fetchArray(int p, int r) {
        String name = parcelTypes.get(p).getName();

        int[][][] solid = new int[0][0][0];

        if (name.equals("A"))
            solid = parcelARotations[r];
        else if (name.equals("B"))
            solid = parcelBRotations[r];
        else if (name.equals("C"))
            solid = parcelCRotations[r];

        return solid;
    }

    public static int fetchAmountOfRotations(int p) {
        String name = parcelTypes.get(p).getName();

        int amount = 0;

        if(name.equals("A"))
            amount = parcelARotations.length;
        else if (name.equals("B"))
            amount = parcelBRotations.length;
        else if (name.equals("C"))
            amount = parcelCRotations.length;

        return amount;
    }

}