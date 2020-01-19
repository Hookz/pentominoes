public class Algorithm {
    //TODO create the algorithm that actually solves the problem, make a pentomino and parcel option

    //create a container (as ints) for which the problem can be solved, not for the UI (this is done separately)
    //TODO save items as follows: typeId + uniqueId, this would allow you to seperate all of the elements.
    //times 2 so we can use the .5 values as well, this will be scaled later on
    int[][][] container = new int[Wrapper.CONTAINER_WIDTH*2][Wrapper.CONTAINER_HEIGHT*2][Wrapper.CONTAINER_DEPTH*2];

    public static void startAlgorithm(){
        System.out.println("Starting algorithm");

        runAlgorithm();
    }

    public static void runAlgorithm(){

        //Check type of problem
        if(Wrapper.problemType.equals("A")){
            //Use dancing links exact cover with parcels
            CreateDancingInput createDancingInput = new CreateDancingInput("Parcels");
            createDancingInput.selectInput();
            createDancingInput.createPlacements();
            createDancingInput.threeDToOneD();

            boolean[][] inputMatrix = createDancingInput.inputMatrix;

            String[] headerNames = new String[inputMatrix.length];
            for(int i=0; i<inputMatrix.length; i++){
                headerNames[i] = Integer.toString(i);
            }

            float[] rowValues = createDancingInput.rowValues;

            int[] rowTypes = createDancingInput.rowType;

            DancingLinksProblem dancingLinksProblem = new DancingLinksProblem(inputMatrix, headerNames, rowValues, rowTypes ,true, 0, Wrapper.precise);

            dancingLinksProblem.createDataStructure();
            dancingLinksProblem.solveDriver();

            dancingLinksProblem.answerToArray();

        } else if (Wrapper.problemType.equals("B")){
            //Use dancing links with parcels and scores 3, 4, 5
            CreateDancingInput createDancingInput = new CreateDancingInput("Parcels");
            createDancingInput.selectInput();
            createDancingInput.createPlacements();
            createDancingInput.threeDToOneD();

            boolean[][] inputMatrix = (boolean[][]) createDancingInput.placements.toArray();

            String[] headerNames = new String[inputMatrix.length];
            for(int i=0; i<inputMatrix.length; i++){
                headerNames[i] = Integer.toString(i);
            }

            float[] rowValues = createDancingInput.rowValues;

            int[] rowTypes = createDancingInput.rowType;

            DancingLinksProblem dancingLinksProblem = new DancingLinksProblem(inputMatrix, headerNames, rowValues, rowTypes,false, 0, Wrapper.precise);

            dancingLinksProblem.createDataStructure();
            dancingLinksProblem.solveDriver();

            dancingLinksProblem.answerToArray();

        } else if (Wrapper.problemType.equals("C")){
            //Use dancing links exact cover with pentominoes
            CreateDancingInput createDancingInput = new CreateDancingInput("Pentominoes");
            createDancingInput.selectInput();
            createDancingInput.createPlacements();
            createDancingInput.threeDToOneD();

            boolean[][] inputMatrix = (boolean[][]) createDancingInput.placements.toArray();

            String[] headerNames = new String[inputMatrix.length];
            for(int i=0; i<inputMatrix.length; i++){
                headerNames[i] = Integer.toString(i);
            }

            float[] rowValues = createDancingInput.rowValues;

            int[] rowTypes = createDancingInput.rowType;

            DancingLinksProblem dancingLinksProblem = new DancingLinksProblem(inputMatrix, headerNames, rowValues, rowTypes, true, 0, Wrapper.precise);

            dancingLinksProblem.createDataStructure();
            dancingLinksProblem.solveDriver();

            dancingLinksProblem.answerToArray();

        } else if (Wrapper.problemType.equals("D")){
            //Use dancing links with pentominoes and scores 3, 4, 5
            CreateDancingInput createDancingInput = new CreateDancingInput("Pentominoes");
            createDancingInput.selectInput();
            createDancingInput.createPlacements();
            createDancingInput.threeDToOneD();

            boolean[][] inputMatrix = (boolean[][]) createDancingInput.placements.toArray();

            String[] headerNames = new String[inputMatrix.length];
            for(int i=0; i<inputMatrix.length; i++){
                headerNames[i] = Integer.toString(i);
            }

            float[] rowValues = createDancingInput.rowValues;

            int[] rowTypes = createDancingInput.rowType;

            DancingLinksProblem dancingLinksProblem = new DancingLinksProblem(inputMatrix, headerNames, rowValues, rowTypes,false, 0, Wrapper.precise);

            dancingLinksProblem.createDataStructure();
            dancingLinksProblem.solveDriver();

            dancingLinksProblem.answerToArray();

        } else if (Wrapper.problemType.equals("General")){
            //Use greedy

        }

        updateUI();

    }

    public static void updateUI(){
        //TODO update UI
    }

}