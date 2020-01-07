public class Wrapper {
    //sizes are in cm
    final static int CONTAINER_WIDTH = 1650;
    final static int CONTAINER_HEIGHT = 250;
    final static int CONTAINER_DEPTH = 400;

    static float score = 0;
    static String inputType = ""; //can be parcel or pentomino
    static inputDetail[] inputDetails = new inputDetail[3]; //It will have the format [inputDetail1, inputDetail2, ..., inputDetailN] with inputDetail having type, amount and value as fields. To be used for the algorithm
    static int[][][] UIInput = new int[CONTAINER_WIDTH/FX3D.cellSize][CONTAINER_HEIGHT/FX3D.cellSize][CONTAINER_DEPTH/FX3D.cellSize];

}

//TODO
/*
Use input from UI to run the algorithm (for now it's enough to just get the input there)
Finish pentomino and parcel classes to contain all options
Write helper function for the algorithm (use the 2D ones from the last phase and make them 3D)
Write the algorithm itself
Update the UI according to the results
 */