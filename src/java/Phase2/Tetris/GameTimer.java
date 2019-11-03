package Phase2.Tetris;
import java.util.TimerTask;

public class GameTimer extends TimerTask {
    public void run() {
        Tetris.step();
    }
}
