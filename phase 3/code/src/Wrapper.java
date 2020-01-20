public class Wrapper {
    //sizes are in cm
    final static int CONTAINER_WIDTH = 1650;
    final static int CONTAINER_HEIGHT = 250;
    final static int CONTAINER_DEPTH = 400;
    final static double ACTUAL_CONTAINER_WIDTH = 16.5;
    final static double ACTUAL_CONTAINER_HEIGHT = 2.5;
    final static double ACTUAL_CONTAINER_DEPTH = 4;
    final static int cellSize = 50;

    static float score = 0;
    static String inputType = ""; //can be parcel or pentomino
    static String problemType = ""; //can be A, B, C, D or General
    static InputDetail[] inputDetails = new InputDetail[3]; //It will have the format [inputDetail1, inputDetail2, inputDetail3] with inputDetail having type, amount and value as fields. To be used for the algorithm

    static int[][][] UIInput = new int[CONTAINER_WIDTH/cellSize][CONTAINER_HEIGHT/cellSize][CONTAINER_DEPTH/cellSize];

    static boolean precise = false;
    static int maxTime = 30;

    static boolean printState = false;
}