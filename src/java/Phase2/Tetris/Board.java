package Phase2.Tetris;
public class Board {
    //Create board class, with addPiece, moveNewPiece, rotateNewPiece, checkRows and removeRow methods
    private int[][] board;

    public Board(){
        int fieldWidth = 5;
        int fieldHeight = 15;
        this.board = new int[fieldWidth][fieldHeight];
    }

    public Board(int fieldWidth, int fieldHeight){
        this.board = new int[fieldWidth][fieldHeight];
    }

    private void addPiece(int x, int y, int[][] piece){
        //edit field when a piece is actually placed
    }

    public void moveNewPiece(int x, int y, int[][] piece){
        //give the x and y coordinates along with the piece itself
        //show the new state, but keep it as a temporal state
        //if the piece hits an other piece, save the state
    }

    public void rotatePiece(int[][] piece, boolean right){
        //give the piece itself and whether you want to turn it to the right (or left)
        //call moveNewPiece when it has been rotated
    }

    private void checkRows(){
        //check if there's a full row, if there is use removeRows
    }

    private void removeRows(int row){
        //remove this row, update the score and move all above rows down by the amount of full rows
    }

}
