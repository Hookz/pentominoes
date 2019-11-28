package Phase2.Tetris;
import General.PentominoDatabase;

import java.io.IOException;

public class Qbot {

    //Add AI class, with fieldScores variable and findBestPlaceToPlace and updateFieldScores method
    private static int[][] fieldScores;
    //TODO to vague
    //TODO Is this really the right way to use tempField?
    private static int[][] tempField = copyField(Phase2.Tetris.Tetris.field);
    private static int bestX = -2;
    private static int bestY = -2;
    private static int bestRotation = -1;
    private static int curPiece = 0;
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
//    public static int[] findBestPlaceToPlaceV2(){
//        int xCurrent = -1;
//        int yCurrent = -1;
//        int rotationCurrent = -1;
//        int xNext = -1;
//        int yNext = -1;
//        int rotationNext = -1;
//        //find the the placement that yields the highest score for the current and the nextPiece
//        int[] temp = mainLoopV2(Phase2.Tetris.Tetris.curPiece, Phase2.Tetris.Tetris.curPieceRotation);
//        xCurrent = temp[0];
//        yCurrent = temp[1];
//        rotationCurrent = temp[2];
//        genRewards(tempField, Phase2.Tetris.Tetris.fieldHeight, Phase2.Tetris.Tetris.fieldWidth);
//        temp = mainLoopV2(Phase2.Tetris.Tetris.nextPiece, Phase2.Tetris.Tetris.nextRot);
//        xNext = temp[0];
//        yNext = temp[1];
//        rotationNext = temp[2];
//        return new int[]{xCurrent,yCurrent,rotationCurrent,xNext,yNext,rotationNext};
//    }

    //TODO What is it still doing here?
    public static void findBestPlaceToPlace() throws IOException {
        System.out.println("PRINT MATRIX");
        Phase2.Tetris.Tetris.printMatrix(Phase2.Tetris.Tetris.field);

        int cr = Phase2.Tetris.Tetris.movePieceDown(false);
        if(cr==-2) return;
        bestX = -1;
        bestY = -1;
        bestRotation = -1;
        curPiece = 0;
        curPiece = Phase2.Tetris.Tetris.curPiece;
        bestRotation = Phase2.Tetris.Tetris.curPieceRotation;
        genRewards(Phase2.Tetris.Tetris.field, Phase2.Tetris.Tetris.fieldHeight, Phase2.Tetris.Tetris.fieldWidth, Phase2.Tetris.Tetris.fieldPadding);
        mainLoop(curPiece,bestRotation);
        if(bestX==-1||bestRotation==-1) {
            return;
        }
        System.out.println(curPiece+" "+bestRotation);
        for(int i=0;i<bestX;i++){
            Phase2.Tetris.Tetris.movePiece(true);
        }
        while(Phase2.Tetris.Tetris.curPieceRotation!=bestRotation){
            Phase2.Tetris.Tetris.rotatePiece(true);
        }
        while(cr==-1){
            cr= Phase2.Tetris.Tetris.movePieceDown(false);
            try {
                Thread.sleep(200);
            }
            catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }
        }
        // genRewards(tempField, Tetris.fieldHeight, Tetris.fieldWidth);
        // mainLoop(Tetris.nextPiece,Tetris.nextRot);
        findBestPlaceToPlace();
    }
    /***
     *
     * @param piece: currently considered piece (curPiece: 1 / nextPiece: 2)
     * @param pieceRotation:  rotation [curPieceRotation / nextRot](just to determine whether piece is flipped)
     */
//    private static int[] mainLoopV2(int piece, int pieceRotation) {
//        int highestScore = 0;
//        int highestRotation = -1;
//        int highestX = -1;
//        int highestY = -1;
//        boolean isMirrored = false;
//        if (pieceRotation > 3) isMirrored = true;
//        int[][][] pieceArr = PentominoDatabase.data[piece];
//        for (int x = 0; x < Phase2.Tetris.Tetris.fieldWidth; x++) {
//            for (int y = 0; y < Phase2.Tetris.Tetris.fieldHeight; y++) {
//                int[] temp = pLoop(x, y, pieceArr, isMirrored);
//                if (temp[0] > highestScore) {
//                    highestScore = temp[0];
//                    highestRotation = temp[1];
//                    highestX = x;
//                    highestY = y;
//                }
//            }
//        }
//        int[][] pieceToPlace = PentominoDatabase.data[piece][highestRotation];
//        for (int i = 0; i < pieceToPlace.length; i++) { // loop over x position of pentomino
//            for (int j = 0; j < pieceToPlace[i].length; j++) { // loop over y position of pentomino
//                if (pieceToPlace[i][j] == 1) {
//                    // Add the ID of the pentomino to the board if the pentomino occupies this square
//                    tempField[highestX + j][highestY + i] = piece;
//                }
//            }
//        }
//        return new int[]{highestX, highestY, highestRotation};
//    }

    //TODO what is this still doing here?
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
        int[][] pieceToPlace = PentominoDatabase.data[piece][bestRotation];
        for(int i = 0; i < pieceToPlace.length; i++){ // loop over x position of pentomino
            for (int j = 0; j < pieceToPlace[i].length; j++){ // loop over y position of pentomino
                if (pieceToPlace[i][j] == 1){
                    // Add the ID of the pentomino to the board if the pentomino occupies this square
                    tempField[bestX + i][bestY + j] = piece;
                }
            }
        }
    }

    /***
     * Loop for a point "P" from mainLoop
     * @param xCoord: x coordinate of point P
     * @param yCoord: y coordinate of point P
     * @param pieceArr
     * @param isMirrored: boolean value, checking if piece is mirrored
     * @return: score for current piece in current point P and the rotation yielding that score
     */
    //TODO what does "Loop for a point "P" from mainLoop" mean?
    private static int[] pLoop(int xCoord, int yCoord, int[][][] pieceArr, boolean isMirrored){
        int[] iterator;
        int highestScoreRotation = -1;
        int highestScore = 0;
        if(isMirrored){
            iterator = new int[]{4,5,6,7};
        }
        else{
            iterator = new int[]{0,1,2,3};
        }
        for(int rotationIndex=0; rotationIndex<iterator.length; rotationIndex++){
            int[][] pieceArr2D = pieceArr[iterator[rotationIndex]];
            int curScore = 0;
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

        Phase2.Tetris.Tetris.printMatrix(flipped);

        //flip the field so i is y, j is x and a higher x means something is on the right and not on the left
        //alternative comment: prevent headache
        fieldScores = new int[fieldHeight][fieldWidth];

        //give scores
        //skip the part of the field that isn't in the playable area
        for (int i = 0; i < fieldHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
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

        //TODO remove after debugging
        //print flipped
        //TODO this print functions doesn't work, it increases all values by one and mixes some up (I have no idea why, it seems to be on drugs)
//        Phase2.Tetris.Tetris.printMatrix(fieldScores);
        for (int i = 0; i < fieldHeight; i++) {
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
