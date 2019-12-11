package Phase2.Tetris;
import General.PentominoDatabase;

import java.io.IOException;

public class Qbot {

    private static int[][] fieldScores;
    private static int bestX = -2;
    private static int bestY = -2;
    private static int bestRotation = -1;
    private static int curPiece = 0;

    /**
     * Finds best place to place curPiece and nextPiece
     * @return: best place to place current and next piece [xc,yc,rc,xn,yn,rn]
     */
    public static void findBestPlaceToPlace() throws IOException {
        // System.out.println("PRINT MATRIX");
        // Tetris.printMatrix(Tetris.field);

        int clearedRows = Phase2.Tetris.Tetris.movePieceDown(false);
        if(clearedRows==-2) return;
        bestX = -1;
        bestY = -1;
        bestRotation = -1;
        curPiece = 0;
        curPiece = Phase2.Tetris.Tetris.curPiece;
        bestRotation = Phase2.Tetris.Tetris.curPieceRotation;
        genRewards(Phase2.Tetris.Tetris.field, Phase2.Tetris.Tetris.fieldHeight, Phase2.Tetris.Tetris.fieldWidth, Phase2.Tetris.Tetris.hiddenRows);
        mainLoop(curPiece,bestRotation);
        if(bestX==-1||bestRotation==-1) {
            return;
        }
        return;
    }

    /***
     *
     * @param piece: currently considered piece (curPiece: 1 / nextPiece: 2)
     * @param pieceRotation:  rotation [curPieceRotation / nextRot](just to determine whether piece is flipped)
     */
    private static void mainLoop(int piece, int pieceRotation){
        int highestScore = 0;
        boolean isMirrored = false;
        if(pieceRotation>3) isMirrored = true;
        int[][][] pieceArr = PentominoDatabase.data[piece];
        for (int x = 0; x < Phase2.Tetris.Tetris.fieldWidth; x++) {
            for (int y = 0; y < Phase2.Tetris.Tetris.fieldHeight; y++) {
                int[] temp = pLoop(x,y,pieceArr,isMirrored);
                if(temp[0]>highestScore){
                    highestScore=temp[0];
                    bestRotation=temp[1];
                    bestX = x;
                    bestY = y;
                }
            }
        }
//        //TODO what is this doing?
//        int[][] pieceToPlace = PentominoDatabase.data[piece][bestRotation];
//        for(int i = 0; i < pieceToPlace.length; i++){ // loop over x position of pentomino
//            for (int j = 0; j < pieceToPlace[i].length; j++){ // loop over y position of pentomino
//                if (pieceToPlace[i][j] == 1){
//                    // Add the ID of the pentomino to the board if the pentomino occupies this square
//                    //tempField[bestX + i][bestY + j] = piece;
//                }
//            }
//        }
    }

    /***
     * Loop for a point "P" from mainLoop
     * @param xCoord: x coordinate of point P
     * @param yCoord: y coordinate of point P
     * @param pieceArr
     * @param isMirrored: boolean value, checking if piece is mirrored
     * @return: score for current piece in current point P and the rotation yielding that score
     */
    private static int[] pLoop(int xCoord, int yCoord, int[][][] pieceArr, boolean isMirrored){
        int[] iterator;
        int highestScoreRotation = -1;
        int highestScore = 0;
        //TODO why is there even the possibility for it to be mirrored?
        if(isMirrored){
            iterator = new int[]{4,5,6,7};
        }
        else{
            iterator = new int[]{0,1,2,3};
        }
        for(int rotationIndex=0; rotationIndex<iterator.length; rotationIndex++){
            int[][] pieceArr2D = pieceArr[iterator[rotationIndex]];
            int curScore = 0;
            //TODO indent if statement and add documentation
            if(xCoord+pieceArr2D.length<fieldScores.length && yCoord+pieceArr2D[0].length < fieldScores[0].length)
            for(int i=0; i<pieceArr2D.length;i++) {
                for (int j = 0; j < pieceArr2D[i].length; j++) {
                    if (pieceArr2D[i][j] != 0) {
                        curScore+=fieldScores[i+xCoord][j+yCoord];
                    }
                }
            }
            if(curScore>highestScore){
                highestScore=curScore;
                highestScoreRotation=iterator[rotationIndex];
            }
        }
        return new int[]{highestScore,highestScoreRotation};
    }

    private static int[][] flipMatrix(int[][] field){
        int[][] flipped = new int[field[0].length][field.length];

        //flip x and y
        for(int i=0; i<field.length; i++){
            for(int j=0; j<field[0].length; j++){
                flipped[j][i] = field[i][j];
            }
        }

        return flipped;
    }


    //TODO let row elimination come before placing the next block
    private static void genRewards(int[][] field, int fieldHeight, int fieldWidth, int fieldPadding) {
        int[][] flipped = flipMatrix(field);

        // Tetris.printMatrix(flipped);

        //remove unplayable part of the field
        int[][] tmp = new int[fieldHeight-fieldPadding][fieldWidth];
        for(int i=0; i<fieldHeight-fieldPadding; i++){
            tmp[i] = flipped[i+fieldPadding];
        }

        flipped = tmp;

        // Tetris.printMatrix(flipped);

        //flip the field so i is y, j is x and a higher x means something is on the right and not on the left
        //alternative comment: prevent headache
        fieldScores = new int[fieldHeight-fieldPadding][fieldWidth];

        //give scores
        //skip the part of the field that isn't in the playable area
        for (int i = 0; i < fieldScores.length; i++) {
            for (int j = 0; j < fieldScores[0].length; j++) {
                int amountOfBlocksSurrounding = 0;
                int amountOfBlocksInRow = 0;

                //only check field that aren't filled (you don't want to put a score in a cell that has already been taken)
                if (flipped[i][j] == -1) {
                    //check surroundings
                    //check same layer, but exclude the cell itself
                    if (i > 1) {
                        //left
                        if (flipped[i-1][j] != -1) amountOfBlocksSurrounding++;
                    }
                    if (i < fieldWidth - 1) {
                        //right
                        if (flipped[i+1][j] != -1) amountOfBlocksSurrounding++;
                    }

                    //check layer below
                    if (j>1) {

                        //left below
                        if (i > 1) {
                            if (flipped[i-1][j-1] != -1) amountOfBlocksSurrounding++;
                        }

                        //below
                        if (flipped[i][j-1] != -1) amountOfBlocksSurrounding++;

                        //below right
                        if (i < fieldWidth - 1) {
                            if (flipped[i+1][j-1] != -1) amountOfBlocksSurrounding++;
                        }
                    }

                    //get the amount of blocks in this row
                    for (int k = 0; k < fieldWidth; k++) {
                        if (flipped[k][j] != -1) {
                            amountOfBlocksInRow++;
                        }
                    }

                    //update score
                    //multiply the surrounding blocks by the height and add a default 'bonus' based on the layer
                    fieldScores[i][j] = amountOfBlocksSurrounding * (5 * (i + 1)) + 25 * (i) + amountOfBlocksInRow * (3 * (i) + 3) + 1;
                } else {
                    //if the cell is already taken, set it's score to 0
                    fieldScores[i][j] = 0;
                }
            }
        }

//        //debugging
        //print flipped
        for (int i = 0; i < fieldHeight-fieldPadding; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                System.out.print(String.format("%6d", fieldScores[i][j]));
            }
            System.out.println();
        }
        System.out.println();

        //flip back because others chose to suffer
        fieldScores = flipMatrix(fieldScores);
    }

    private static int[][] copyField(int[][] f0){
        int [][] f1=new int[Phase2.Tetris.Tetris.fieldWidth][Phase2.Tetris.Tetris.fieldHeight];
        for(int i = 0; i< Phase2.Tetris.Tetris.fieldWidth; i++){
            for(int j = 0; j< Phase2.Tetris.Tetris.fieldHeight; j++){
                f1[i][j]=f0[i][j];
            }
        }
        return f1;
    }

    public static void run() throws IOException {
        // Fetch best x-coordinate, best y-coordinate and best rotation for the current piece
        findBestPlaceToPlace();

        System.out.println("P. ID: " + curPiece); // P = Pentomino
        System.out.println("R. ID: " + bestRotation); // R = Rotation
        System.out.println("X: " + bestX);
        System.out.println("Y: " + bestY);

        // Update rotation
        while(Phase2.Tetris.Tetris.curPieceRotation != bestRotation) {
            Phase2.Tetris.Tetris.rotatePiece(true);
        }

        // Update x-coordinate
        for(int i = 0; i < bestX; i++) {
            Phase2.Tetris.Tetris.movePiece(true);
        }

        // Check for collision when dropping piece
        if(!checkCollision()) {
            // No collision detected; drop down possible
            System.out.println("Success");
            System.out.println();
            // Drop piece
            Phase2.Tetris.Tetris.dropPiece(false);
        }
        else {
            // Collision detected; drop down not possible
            System.out.println("Fail");
            System.out.println();
            // Set fieldscores for this position to zero
            fieldScores[bestX][bestY] = 0;
            // Try again
            run();
        }
    }

    private static boolean checkCollision() {
        for(int y = 0; y < bestY; y++) {
            if(Phase2.Tetris.Tetris.field[bestX][y] != -1)
                return true;
        }

        return false;
    }

}
