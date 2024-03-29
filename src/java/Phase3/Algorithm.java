package Phase3;
import java.util.List;

/**
 * Class starting the chosen algorithm (selected in the Wrapper)
 */
public class Algorithm {
    /***
     * Main method of the class that starts execution of the algorithm that is chosen in the Wrapper class.
     */
    public static void startAlgorithm() {
        System.out.println("Starting algorithm");

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

            List<Integer> rowTypes = createDancingInput.rowTypes;

            DancingLinksProblem dancingLinksProblem = new DancingLinksProblem(inputMatrix, headerNames, rowTypes ,true, Wrapper.maxTime, Wrapper.precise);

            dancingLinksProblem.createDataStructure();
            dancingLinksProblem.solveDriver();

            dancingLinksProblem.answerToUI();

        } else if (Wrapper.problemType.equals("B")){
            if (Wrapper.algorithmType.equals("Algorithm X+ (Partial cover)")) {
                //Use dancing links with parcels and scores 3, 4, 5
                CreateDancingInput createDancingInput = new CreateDancingInput("Parcels");
                createDancingInput.selectInput();
                createDancingInput.createPlacements();
                createDancingInput.threeDToOneD();

                boolean[][] inputMatrix = createDancingInput.inputMatrix;

                String[] headerNames = new String[inputMatrix.length];
                for (int i = 0; i < inputMatrix.length; i++) {
                    headerNames[i] = Integer.toString(i);
                }

                List<Integer> rowTypes = createDancingInput.rowTypes;

                DancingLinksProblem dancingLinksProblem = new DancingLinksProblem(inputMatrix, headerNames, rowTypes, false, Wrapper.maxTime, Wrapper.precise);

                dancingLinksProblem.createDataStructure();
                dancingLinksProblem.solveDriver();

                dancingLinksProblem.answerToUI();
            }
            else if (Wrapper.algorithmType.equals("Genetic Algorithm")) {
                Gbot.Play();
            }
            else if (Wrapper.algorithmType.equals("Greedy Algorithm (Value)") || Wrapper.algorithmType.equals("Greedy Algorithm (Value/Volume)")) {
                FX3D.inputDetail1.amount = 1000;
                FX3D.inputDetail2.amount = 1000;
                FX3D.inputDetail3.amount = 1000;

                GreedyAlgorithm.runAlgorithm();
            }
        } else if (Wrapper.problemType.equals("C")){
            //Use dancing links exact cover with pentominoes
            CreateDancingInput createDancingInput = new CreateDancingInput("Pentominoes");
            createDancingInput.selectInput();
            createDancingInput.createPlacements();
            createDancingInput.threeDToOneD();

            boolean[][] inputMatrix = createDancingInput.inputMatrix;

            String[] headerNames = new String[inputMatrix.length];
            for(int i=0; i<inputMatrix.length; i++){
                headerNames[i] = Integer.toString(i);
            }

            List<Integer> rowTypes = createDancingInput.rowTypes;

            DancingLinksProblem dancingLinksProblem = new DancingLinksProblem(inputMatrix, headerNames, rowTypes, true, Wrapper.maxTime, Wrapper.precise);

            dancingLinksProblem.createDataStructure();
            dancingLinksProblem.solveDriver();

            dancingLinksProblem.answerToUI();

        } else if (Wrapper.problemType.equals("D")) {
            if (Wrapper.algorithmType.equals("Algorithm X+ (Partial cover)")) {
                //Use dancing links with pentominoes and scores 3, 4, 5
                CreateDancingInput createDancingInput = new CreateDancingInput("Pentominoes");
                createDancingInput.selectInput();
                createDancingInput.createPlacements();
                createDancingInput.threeDToOneD();

                boolean[][] inputMatrix = createDancingInput.inputMatrix;

                String[] headerNames = new String[inputMatrix.length];
                for (int i = 0; i < inputMatrix.length; i++) {
                    headerNames[i] = Integer.toString(i);
                }

                List<Integer> rowTypes = createDancingInput.rowTypes;

                DancingLinksProblem dancingLinksProblem = new DancingLinksProblem(inputMatrix, headerNames, rowTypes, false, Wrapper.maxTime, Wrapper.precise);

                dancingLinksProblem.createDataStructure();
                dancingLinksProblem.solveDriver();

                dancingLinksProblem.answerToUI();
            }
            else if (Wrapper.algorithmType.equals("Genetic Algorithm")) {
                Gbot.Play();
            }
        } else if (Wrapper.problemType.equals("General")){
            if (Wrapper.inputType.equals("Parcels")) {
                if (Wrapper.algorithmType.equals("Genetic Algorithm")) {
                    Gbot.Play();
                }
                else if (Wrapper.algorithmType.equals("Greedy Algorithm (Value)") || Wrapper.algorithmType.equals("Greedy Algorithm (Value/Volume)")) {
                    GreedyAlgorithm.runAlgorithm();
                }
            }
            else if (Wrapper.inputType.equals("Pentominoes")) {
                Gbot.Play();
            }
        }
    }

}