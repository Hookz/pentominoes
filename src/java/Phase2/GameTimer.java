package Phase2;
import java.util.TimerTask;

/**
 * Class running the TimerTask if the game mode selected uses a bot
 */
public class GameTimer extends TimerTask {
    public void run() {
        if(!Tetris.enableBot)
            Tetris.movePieceDown(false);
    }
}