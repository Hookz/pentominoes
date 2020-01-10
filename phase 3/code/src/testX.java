public class testX {
    public static void main(String[] args){
        //TODO upgrade to actual data

        final int WIDTH = 4;
        final int HEIGHT = 4;

        //Use some simple input data
        boolean[][] inputMatrix = new boolean[HEIGHT][WIDTH];
        String[] headerNames = {"A", "B", "C", "D"};

        int[][] tmpInput = {{1, 0, 0, 1}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 1, 1, 1}};

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
