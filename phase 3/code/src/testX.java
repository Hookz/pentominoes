public class testX {
    public static void main(String[] args){
        //TODO upgrade to actual data
        boolean exactCover = false;
        int[][] tmpInput;

        //Use some simple input data
        String[] headerNames = {"A", "B", "C", "D", "E", "F", "G", "H"};
        //partial cover

        if(exactCover){
            //exact cover (0, 1, 2, 4)

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
        /*
        {0, 0, 0, 0, 1, 1, 0, 0}
        {1, 0, 1, 0, 0, 0, 0, 0}
        {0, 0, 0, 0, 0, 0, 0, 1}
        {0, 1, 0, 1, 0, 0, 1, 0}
        */
        } else {
            tmpInput = new int[][]{ {0, 0, 0, 0, 1, 1, 0, 0},
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
                                    {0, 0, 0, 0, 0, 0, 0, 1},
                                    {0, 1, 1, 1, 0, 0, 0, 0}};
        }

        boolean[][] inputMatrix = new boolean[tmpInput.length][tmpInput[0].length];

        for(int i=0; i<tmpInput.length; i++){
            for(int j=0; j<tmpInput[i].length; j++){
                if(tmpInput[i][j] == 1){
                    inputMatrix[i][j] = true;
                }
            }
        }

        DancingLinksProblem dancingLinksProblem = new DancingLinksProblem(inputMatrix, headerNames);

        dancingLinksProblem.createDataStructure();
        dancingLinksProblem.solve(0);
    }
}
