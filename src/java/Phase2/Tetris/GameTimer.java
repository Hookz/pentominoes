package Phase2.Tetris;
import java.util.TimerTask;
import General.PentominoDatabase;

public class GameTimer extends TimerTask {
    int n=0;
    int m=0;
    public void run() {
        /*if(m>11) m=0;
        if(n>PentominoDatabase.data[Tetris.curPiece].length-1){
            n=0;
            m++;
        }
        int[] pos = {0, 5};
        Tetris.wipeField(Tetris.tempField);
        Tetris.curPos=pos;
        Tetris.curPiece=m;
        Tetris.curPieceRotation=n;
        int[][] pieceToPlace = PentominoDatabase.data[Tetris.curPiece][Tetris.curPieceRotation];
        Tetris.addPiece();
        Tetris.gameWrapper.ui.setState(Tetris.tempField);
        n++;*/
        //TODO fix blocks becoming invisible
        Phase2.Tetris.Tetris.runBot();
        Tetris.movePieceDown();
    }
}