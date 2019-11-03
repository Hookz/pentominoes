public class Game {
    //Create game class, with score, currentPiece and nextPiece variables and generateNextPiece, nextPiece, increaseScore and resetGame methods
    private int score;
    private int[][] currentPiece;
    private int[][] nextPiece;

    private int[][] generatePiece(){
        //get a random value and chose the corresponding piece
        int [][] a=new int[1][1];
        return a;
    }

    public void increaseScore(int increaseBy){
        score += increaseBy;
    }

    public void  nextPiece(){
        currentPiece = nextPiece;
        nextPiece = generatePiece();
    }

    public void resetGame(){
        score = 0;
        currentPiece = generatePiece();
        nextPiece = generatePiece();
    }
}
