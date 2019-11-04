import java.util.TimerTask;

public class GameTimer extends TimerTask {
    int n=0;
    public void run() {
        /*int[] pos = {0, 5};
        Tetris.wipeField(Tetris.tempField);
        Tetris.curPos=pos;
        Tetris.curPiece=11;
        int[][] pieceToPlace = PentominoDatabase.data[Tetris.curPiece][Tetris.curPieceRotation];
        if(n>PentominoDatabase.data[Tetris.curPiece].length-1) n=0;
        System.out.println(n);
        System.out.println();
        Tetris.curPieceRotation=n;
        Tetris.addPiece();
        Tetris.ui.setState(Tetris.tempField);
        n++;*/
        Tetris.movePieceDown();
    }
}