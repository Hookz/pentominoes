package Phase2.Tetris;
public class Board {
    //Create board class, with addPiece, moveNewPiece, rotateNewPiece, checkRows and removeRow methods

    //you can reference curPiece with Tetris.curPiece
    //you can reference curPiece with Tetris.curPos

    private void addPiece(int x, int y, int[][] piece){
        //edit field when a piece is actually placed
    }

    public static void rotatePiece(boolean cw){
        //give the piece itself and whether you want to turn it to the right (or left)
        //call moveNewPiece when it has been rotated
    }

    public static void movePiece(boolean right){
        //give the piece itself and whether you want to move it to the right (or left)
        //call moveNewPiece when it has been rotated
    }

    public static void dropPiece(){
        //drop the piece to the lowest possible position
    }

    private void checkRows(){
        //check if there's a full row, if there is use removeRows
    }

    private void removeRows(int row){
        //remove this row, update the score and move all above rows down by the amount of full rows
    }

}