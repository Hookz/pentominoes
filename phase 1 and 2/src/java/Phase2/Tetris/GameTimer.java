package Phase2.Tetris;
import java.util.TimerTask;
import General.PentominoDatabase;

public class GameTimer extends TimerTask {
    public void run() {
        if(!Tetris.enableBot)
            Tetris.movePieceDown(false);
    }
}