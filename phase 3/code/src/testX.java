public class testX {
    public static void main(String[] args){
        //TODO upgrade to actual data
        boolean exactCover = false;
        int maxSeconds = 10;
        int[][] tmpInput;
        int[] rowValues = new int[0];

        //Use some simple input data
        String[] headerNames = {"A", "B", "C", "D", "E", "F", "G", "H"};
        //partial cover

        if(exactCover){
            //exact cover (0, 1, 2, 4)

                                //   A1 B6 C5 D4 E1 F1 G3 H2
            tmpInput = new int[][]{ {0, 0, 0, 0, 1, 1, 0, 0},
                                    {1, 0, 1, 0, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0, 0, 0, 1},
                                    {0, 1, 0, 0, 0, 0, 0, 0},
                                    {0, 1, 0, 1, 0, 0, 1, 0},
                                    {0, 1, 1, 1, 0, 0, 0, 0},
                                    {0, 1, 1, 1, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0, 0, 1, 0},
                                    {0, 1, 0, 0, 0, 0, 0, 0},
                                    {0, 0, 1, 0, 0, 0, 1, 0},
                                    {0, 0, 0, 0, 0, 0, 0, 1},
                                    {0, 1, 1, 1, 0, 0, 0, 0}};



        } else {
            tmpInput = new int[][]{ {1, 0, 0, 0, 1, 1, 0, 0},
                                    {0, 1, 0, 0, 0, 0, 0, 0},
                                    {0, 0, 1, 0, 0, 0, 0, 0},
                                    {0, 0, 0, 1, 0, 0, 0, 0},
                                    {0, 1, 0, 0, 0, 0, 0, 0},
                                    {0, 0, 1, 0, 0, 0, 0, 0},
                                    {0, 1, 1, 1, 0, 0, 0, 0},
                                    {0, 1, 1, 1, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0, 0, 1, 0},
                                    {0, 1, 0, 0, 0, 0, 0, 0},
                                    {0, 0, 1, 0, 0, 0, 1, 0},
                                    {0, 0, 0, 0, 0, 0, 1, 0},
                                    {0, 1, 1, 1, 0, 0, 0, 0}};

            rowValues = new int[] {  1,
                                    2,
                                    1,
                                    2,
                                    4,
                                    4,
                                    1,
                                    1,
                                    1,
                                    2,
                                    2,
                                    1,
                                    2};

            //{Type, Size, Score
            /*String[][] rowDetails = new String[][]{

            }*/

            /*
             tmpInput = new int[][]{ {1, 0, 0, 0, 1, 1, 0, 0},
                                    {0, 1, 0, 0, 0, 0, 0, 0},
                                    {0, 0, 1, 0, 0, 0, 0, 0},
                                    {0, 0, 0, 1, 0, 0, 0, 0},
                                    {0, 1, 0, 0, 0, 0, 0, 0},
                                    {0, 0, 1, 0, 0, 0, 0, 0},
                                    {0, 1, 1, 1, 0, 0, 0, 0},
                                    {0, 1, 1, 1, 0, 0, 0, 0},
                                    {0, 0, 0, 0, 0, 0, 1, 0},
                                    {0, 1, 0, 0, 0, 0, 0, 0},
                                    {0, 0, 1, 0, 0, 0, 1, 0},
                                    {0, 0, 0, 0, 0, 0, 1, 0},
                                    {0, 1, 1, 1, 0, 0, 0, 0}};

            0, 8, 3, 2, 1
            =
            0 1 2 3 8

            {1, 0, 0, 0, 1, 1, 0, 0}
            {0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0},
             */
        }

        boolean[][] inputMatrix = new boolean[tmpInput.length][tmpInput[0].length];

        for(int i=0; i<tmpInput.length; i++){
            for(int j=0; j<tmpInput[i].length; j++){
                if(tmpInput[i][j] == 1){
                    inputMatrix[i][j] = true;
                }
            }
        }

        DancingLinksProblem dancingLinksProblem = new DancingLinksProblem(inputMatrix, headerNames, rowValues, exactCover, maxSeconds, false);

        dancingLinksProblem.createDataStructure();
        dancingLinksProblem.solveDriver(0);

        if(!exactCover) {
            dancingLinksProblem.determineBestSolution();
            System.out.println(dancingLinksProblem.bestScore);
        }


    }
}
