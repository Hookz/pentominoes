package Phase2.Tetris;
import java.util.TimerTask;
import General.PentominoDatabase;

public class GameTimer extends TimerTask {
    int n = 0;
    int m = 0;

    public void run() {
        Tetris.movePieceDown();
    }
}