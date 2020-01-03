public class Wrapper {
    final static int CONTAINER_WIDTH = 200;
    final static int CONTAINER_HEIGHT = 200;
    final static int CONTAINER_DEPTH = 200;

    static float score = 0;
    static String inputType = ""; //can be parcel or pentomino
    static inputDetail[] inputDetails; //It will have the format [inputDetail1, inputDetail2, ..., inputDetailN] with inputDetail having type, amount and value as fields.
}

//TODO
/*
Use input from UI to run the algorithm (for now it's enough to just get the input there)
Finish pentomino and parcel classes to contain all options
Write helper function for the algorithm (use the 2D ones from the last phase and make them 3D)
Write the algorithm itself
Update the UI according to the results
 */