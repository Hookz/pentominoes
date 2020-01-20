import java.text.DecimalFormat;
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

        // Initialize ParcelType objects
        typeA = new ParcelType("A", 0.0, 0.0, parcelARotations.length, 1, 0, Wrapper.inputDetails[0].amount);
        typeB = new ParcelType("B", 0.0, 0.0, parcelBRotations.length, 2, 0, Wrapper.inputDetails[1].amount);
        typeC = new ParcelType("C", 0.0, 0.0, parcelCRotations.length, 3, 0, Wrapper.inputDetails[2].amount);

        // Empty container
        emptyContainer();

        // Add ParcelType objects to parcelTypes ArrayList
        parcelTypes.add(typeA);
        parcelTypes.add(typeB);
        parcelTypes.add(typeC);

        // Check which version to use
        if (Wrapper.algorithmType.equals("Greedy Algorithm (Value)")) {
            // Set values
            typeA.setValue(Wrapper.inputDetails[0].value);
            typeB.setValue(Wrapper.inputDetails[1].value);
            typeC.setValue(Wrapper.inputDetails[2].value);

            // Determine the proper order of the parcel types based on decreasing value
            parcelTypes.sort(new ValueSorter());
        }
        else if (Wrapper.algorithmType.equals("Greedy Algorithm (Value/Volume)")) {
            // Set ratios
            typeA.setRatio(Wrapper.inputDetails[0].value / volumes[0]);
            typeB.setRatio(Wrapper.inputDetails[1].value / volumes[1]);
            typeC.setRatio(Wrapper.inputDetails[2].value / volumes[2]);

            // Determine the proper order of the parcel types based on decreasing ratio
            parcelTypes.sort(new RatioSorter());
        }

        // Fill the container
        fillContainer();

        // Print info
        printInfo();

        // Update the UI
        FX3D.updateUI();
    }

    // Comparator for values
    static class ValueSorter implements Comparator<ParcelType> {
        @Override
        public int compare(ParcelType o1, ParcelType o2) {
            return Double.compare(o2.getValue(), o1.getValue());
        }
    }

    // Comparator for ratios
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
            for(int r = 0; r < parcelTypes.get(p).getRotations(); r++) {
                // For every x-position
                for(int x = 0; x < Wrapper.UIInput.length; x++) {
                    // For every y-position
                    for(int y = 0; y < Wrapper.UIInput[0].length; y++) {
                        // For every z-position
                        for(int z = 0; z < Wrapper.UIInput[0][0].length; z++) {
                            // Check whether or not the maximum amount of parcels has been reached
                            if (parcelTypes.get(p).getPlaced() >= parcelTypes.get(p).getMaximum()) {
                                if (p < 2) {
                                    p++;
                                }
                            }
                            else {
                                // Create array that holds the current coordinates
                                int[] coord = {x, y, z};

                                // Create array that holds current piece index and current rotation index
                                int[] indexes = {p, r};

                                // Check whether or not the current piece can be placed and take the appropriate action
                                addSolid(fetchArray(p, r), coord, parcelTypes.get(p).getColor(), indexes);
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean checkParcel(int[][][] solid, int[] coord) {
        if (coord[0] < 0 || coord[1] < 0 || coord[2] < 0) {
            return true;
        } else {
            try {
                for (int i = 0; i < solid.length; i++) { // loop over x position of pentomino
                    for (int j = 0; j < solid[i].length; j++) { // loop over y position of pentomino
                        for (int k = 0; k < solid[i][j].length; k++) {
                            if (solid[i][j][k] == 1) {
                                if (Wrapper.UIInput[coord[0] + i][coord[1] + j][coord[2] + k] != 0) {
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

    public static void addSolid(int[][][] solid, int[] coord, int color, int[] indexes) { //add the solid to the container at the specified coordinates
        if (!checkParcel(solid, coord)) {
            for (int i = 0; i < solid.length; i++) {
                for (int j = 0; j < solid[i].length; j++) {
                    for (int k = 0; k < solid[i][j].length; k++) {
                        if (solid[i][j][k] == 1) {
                            Wrapper.UIInput[coord[0] + i][coord[1] + j][coord[2] + k] = color;
                        }
                    }
                }
            }
            parcelTypes.get(indexes[0]).setPlaced(parcelTypes.get(indexes[0]).getPlaced() + 1);
            System.out.println("Parcel #" + (typeA.getPlaced() + typeB.getPlaced() + typeC.getPlaced()) + ": [" + indexes[0] + ", " + indexes[1] + "] placed at (" + coord[0] + ", " + coord[1] + ", " + coord[2] + ")");
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

    public static void emptyContainer() {
        for(int x = 0; x < Wrapper.UIInput.length; x++) {
            for(int y = 0; y < Wrapper.UIInput[0].length; y++) {
                for(int z = 0; z < Wrapper.UIInput[0][0].length; z++) {
                    Wrapper.UIInput[x][y][z] = 0;
                }
            }
        }
    }

    public static void printInfo() {
        System.out.println("---------- Statistics ----------");
        System.out.println("- Amount of parcels of type A placed: " + typeA.getPlaced());
        System.out.println("- Amount of parcels of type B placed: " + typeB.getPlaced());
        System.out.println("- Amount of parcels of type C placed: " + typeC.getPlaced());

        float value = (typeA.getPlaced() * Wrapper.inputDetails[0].value) + (typeB.getPlaced() * Wrapper.inputDetails[1].value) + (typeC.getPlaced() * Wrapper.inputDetails[2].value);

        Wrapper.score = value;

        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("- Value of all placed parcels: " + df.format(value));
    }

}