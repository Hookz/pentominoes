package Phase2;
import java.util.TimerTask;

public class GameTimer extends TimerTask {
    public void run() {
        if(!Tetris.enableBot)
            Tetris.movePieceDown(false);
    }
}