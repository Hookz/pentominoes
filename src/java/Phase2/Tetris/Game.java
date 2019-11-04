package Phase2.Tetris;
public class Game {
    //Create game class, with score, currentPiece and nextPiece variables and generateNextPiece, nextPiece, increaseScore and resetGame methods
    private int score;
    private int[][] currentPiece;
    private int[][] nextPiece;

    private int[][] generatePiece(){
        int random = (int) (12 * Math.random() + 1);
        int[][] randomPiece;

        switch(random) {
            case 1:
                // P
                randomPiece = new int[][]
                        {
                                {1, 1},
                                {1, 1},
                                {1, 0}
                        };
                break;
            case 2:
                // X
                randomPiece = new int[][]
                        {
                                {0,1,0},
                                {1,1,1},
                                {0,1,0}
                        };
                break;
            case 3:
                // F
                randomPiece = new int[][]
                        {
                                {0,1,1},
                                {1,1,0},
                                {0,1,0}
                        };
                break;
            case 4:
                // V
                randomPiece = new int[][]
                        {
                                {1,0,0},
                                {1,0,0},
                                {1,1,1}
                        };
                break;
            case 5:
                // W
                randomPiece = new int[][]
                        {
                                {1,0,0},
                                {1,1,0},
                                {0,1,1}
                        };
                break;
            case 6:
                // Y
                randomPiece = new int[][]
                        {
                                {0,1},
                                {1,1},
                                {0,1},
                                {0,1}
                        };
                break;
            case 7:
                // I
                randomPiece = new int[][]
                        {
                                {1},
                                {1},
                                {1},
                                {1},
                                {1}
                        };
                break;
            case 8:
                // L
                randomPiece = new int[][]
                        {
                                {0,0,0,1},
                                {1,1,1,1},
                        };
                break;
            case 9:
                // N
                randomPiece = new int[][]
                        {
                                {1,1,0,0},
                                {0,1,1,1}
                        };
                break;
            case 10:
                // U
                randomPiece = new int[][]
                        {
                                {1,0,1},
                                {1,1,1},
                        };
                break;
            case 11:
                // Z
                randomPiece = new int[][]
                        {
                                {1,1,0},
                                {0,1,0},
                                {0,1,1}
                        };
                break;
            case 12:
                // T
                randomPiece = new int[][]
                        {
                                {1,1,1},
                                {0,1,0},
                                {0,1,0}
                        };
                break;
            default:
                // null
                randomPiece = new int[][]
                        {
                                {0,0,0},
                                {0,0,0},
                                {0,0,0}
                        };
                break;
        }

        return randomPiece;
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
