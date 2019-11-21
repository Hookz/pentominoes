package Phase2.Tetris;
import General.PentominoDatabase;
public class Qbot {
    //TODO check i and j
    //TODO get a correct score
    //TODO keep placed blocks better into account
    //TODO use fieldScores
    //Add AI class, with fieldScores variable and findBestPlaceToPlace and updateFieldScores method
    private static int[][] fieldScores;
    private static int[][] tempField = copyField(Tetris.field);

    //TODO after it works: save the results so far so they can be reused if a position doesn't have a route
    //TODO read advice: 1) Keep track of what positions have been tried and their results (if they were possible at all). This means that you need to save the best location and rotation for the 2 pentominoes that you are trying out.
    // 2) When you finished finding the optimal position to place the first pentomino, check the second pentomino using the new scores that you can get from genRewards. As the field input, use the actually field with the first pentomino
    // added in (as a tmp field).
     /*Qbot tip 3: don't try placing a pentomino in the sky or on a taken cell, find the lowest free cell of each column and try that one. 
    Keep going up until you tried every cell in that column that could possibly be used. Then continue to the next column.
    
    Tip 4: For each of the cells that you try, fit in the pentomino in all it's possible rotations (not mirrors). 
    If none work, save a false in a boolean array that stores the locations that are left to search trough. This is not strictly needed, but can really help you debugging.
    */

    /**
     * Finds best place to place curPiece and nextPiece
     * @return: best place to place current and next piece [xc,yc,rc,xn,yn,rn]
     */
    public static int[] findBestPlaceToPlace(){
        int[][][] pieceArr = PentominoDatabase.data[Tetris.curPiece];
        int[][][] nextPieceArr = PentominoDatabase.data[Tetris.nextPiece];
        //find the the placement that yields the highest score for the current and the nextPiece

        mainLoop(Tetris.curPiece,Tetris.curPieceRotation);
        genRewards(tempField, Tetris.fieldHeight, Tetris.fieldWidth);
        mainLoop(Tetris.nextPiece,Tetris.nextRot);

        //TODO: return best placement of the curPiece and nextPiece
        return null;
    }

    /***
     *
     * @param piece: currently considered piece (curPiece: 1 / nextPiece: 2)
     * @param pieceRotation:  rotation [curPieceRotation / nextRot](just to determine whether piece is flipped)
     */
    public static void mainLoop(int piece, int pieceRotation){
        //TODO: create the x-axis / y-axis loop to go through all starting positions
            //TODO: add a call to the pLoop to get score from the current piece
            //TODO: compare new score with current score
        //TODO: update the tempField to include the placement of the new block
    }

    /***
     * Loop for a point "P" from mainLoop
     * @param point: currently considered point P (in the form of an [x,y] array)
     * @return: score for current piece in current point P and the rotation yielding that score
     */
    public static int[] pLoop(int[] point, int[][][] pieceArr){
        //TODO: add a loop that goes through all possible rotations of a given piece
        //TODO: calculate points in each rotation for the given point
        //TODO: change return to be in form [score, rotationID]
        return null;
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
        System.out.println();
    }

    private static int[][] copyField(int[][] f0){
        int [][] f1=new int[Tetris.fieldWidth][Tetris.fieldHeight];
        for(int i=0;i<Tetris.fieldWidth;i++){
            for(int j=0;j<Tetris.fieldHeight;j++){
                f1[i][j]=f0[i][j];
            }
        }
        return f1;
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
