package Phase2.Tetris;
import General.PentominoDatabase;
public class Qbot {
    //TODO check i and j
    //TODO get a correct score
    //TODO keep placed blocks better into account
    //TODO use fieldScores
    //Add AI class, with fieldScores variable and findBestPlaceToPlace and updateFieldScores method
    private static int[][] fieldScores;

    //TODO after it works: save the results so far so they can be reused if a position doesn't have a route
    //TODO read advice: 1) Keep track of what positions have been tried and their results (if they were possible at all). This means that you need to save the best location and rotation for the 2 pentominoes that you are trying out.
    // 2) When you finished finding the optimal position to place the first pentomino, check the second pentomino using the new scores that you can get from genRewards. As the field input, use the actually field with the first pentomino
    // added in (as a tmp field).
    public static int findBestPlaceToPlace(int piece, int pieceRotation, int nextPiece, int nextPieceRotation){
        int[][] pieceArr = PentominoDatabase.data[piece][pieceRotation];
        int[][] nextPieceArr = PentominoDatabase.data[nextPiece][nextPieceRotation];
        //find the the placement that yields the highest score for the current and the nextPiece
        return 0;
    }

    public static void genRewards(int[][] field, int fieldHeight, int fieldWidth) {
        fieldScores = new int[fieldWidth][fieldHeight];

        //give scores
        for (int x = 0; x < fieldWidth; x++) {
            for (int y = 0; y < fieldHeight; y++) {
                int amountOfBlocksSurrounding = 0;
                int amountOfBlocksInRow = 0;

                //only check field that aren't filled (you don't want to put a score in a cell that has already been taken)
                if (field[x][y] == -1) {
                    //check surroundings
                    //check same layer, but exclude the cell itself
                    if (x > 1) {
                        if (field[x-1][y] == -1) amountOfBlocksSurrounding++;
                    }
                    if (x < fieldWidth - 1) {
                        if (field[x+1][y] == -1) amountOfBlocksSurrounding++;
                    }

                    //check layer below
                    if (y < fieldHeight - 1) {

                        if (x > 1) {
                            if (field[x-1][y+1] == -1) amountOfBlocksSurrounding++;
                        }

                        if (field[x][y+1] == -1) amountOfBlocksSurrounding++;

                        if (x < fieldWidth - 1) {
                            if (field[x+1][y+1] == -1) amountOfBlocksSurrounding++;
                        }
                    }

                    //get the amount of blocks in this row
                    for (int k = 0; k < fieldHeight; k++) {
                        if (field[x][k] == -1) {
                            amountOfBlocksInRow++;
                        }
                    }

                    //update score
                    //multiply the surrounding blocks by the height and add a default 'bonus' based on the layer
                    fieldScores[x][y] = amountOfBlocksSurrounding * (5 * (y + 1)) + 25 * (y) + amountOfBlocksInRow * (3 * (y) + 3) + 1;
                }

            }
        }

        //TODO remove after debugging
        //print field
        for (int y = 0; y < fieldHeight; y++) {
            for (int i = 0; i < fieldWidth; i++) {
                System.out.print(String.format("%6d", fieldScores[i][y]));
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
