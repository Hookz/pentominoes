package Phase2.Tetris;
public class Qbot {
    //Add AI class, with fieldScores variable and findBestPlaceToPlace and updateFieldScores method
    private static int[][] fieldScores;

    public static int findBestPlaceToPlace(int[][] piece, int[][] nextPiece){
        //find the the placement that yields the highest score for the current and the nextPiece
        return 0;
    }

    public static void genRewards(int[][] field, int fieldHeight, int fieldWidth) {
        //give scores
        for (int i = 0; i < fieldHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                int amountOfBlocksSurrounding = 0;
                int amountOfBlocksInRow = 0;

                //only check field that aren't filled (you don't want to put a score in a cell that has already been taken)
                if (field[i][j] != -1) {
                    //check surroundings
                    //check same layer, but exclude the cell itself
                    if (j > 1) {
                        if (field[i][j - 1] == -1) amountOfBlocksSurrounding++;
                    }
                    if (j < fieldWidth - 1) {
                        if (field[i][j + 1] == -1) amountOfBlocksSurrounding++;
                    }

                    //check layer below
                    if (i < fieldHeight - 1) {

                        if (j > 1) {
                            if (field[i + 1][j - 1] == -1) amountOfBlocksSurrounding++;
                        }

                        if (field[i + 1][j] == -1) amountOfBlocksSurrounding++;

                        if (j < fieldWidth - 1) {
                            if (field[i + 1][j + 1] == -1) amountOfBlocksSurrounding++;
                        }
                    }

                    //get the amount of blocks in this row
                    for (int k = 0; k < fieldWidth; k++) {
                        if (field[i][k] == -1) {
                            amountOfBlocksInRow++;
                        }
                    }

                    //update score
                    //multiply the surrounding blocks by the height and add a default 'bonus' based on the layer
                    field[i][j] = amountOfBlocksSurrounding * (5 * (i + 1)) + 25 * (i) + amountOfBlocksInRow * (3 * (i) + 3) + 1;
                }

            }
        }

        //TODO remove after debugging
        //print field
        for (int i = 0; i < fieldHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                System.out.print(String.format("%4d", field[i][j]));
            }
            System.out.println();
        }
    }

    //TODO remove after debugging
    /*
    static static public int[][] addBlock(int[][] field, int fieldWidth, int fieldHeight){
        int randomX = (int)(Math.random()*fieldWidth);
        int randomY = (int)(Math.random()*fieldHeight);

        if(field[randomY][randomX] != -1){
            field[randomY][randomX] = -1;
        } else {
            addBlock(field, fieldWidth, fieldHeight);
        }

        return field;
    }*/
}
