package Phase2.Tetris;

import javax.swing.*;
import java.awt.*;

public class GameWrapper extends JPanel{
    public JLabel score = new JLabel(number(Tetris.score), JLabel.CENTER);
    public JLabel[] gamePieces = new Phase1.ChoosePieces().pieces;
    public JFrame window;
    public static UI ui;
    public JLabel nextPiece;

    public GameWrapper(int x, int y, int _size){
        score.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        ui = new UI(x,y,_size);
        ui.setBounds(0,0,x*_size,y*_size);
        window = new JFrame("Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new GridLayout(1,2));
        window.add(ui);

        JPanel rightPanel = new JPanel(new GridLayout(3,1));
        JPanel scorePanel = new JPanel(new GridLayout(2,1));
        JPanel nextPiecePanel = new JPanel(new GridLayout(2,1));

        JLabel scoreLabel = new JLabel("Score",JLabel.CENTER);
        scoreLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 26));

        scorePanel.add(scoreLabel);
        scorePanel.add(score);

        JLabel nextPieceLabel = new JLabel("Next piece", JLabel.CENTER);
        nextPieceLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 22));

        for(int i=0;i<gamePieces.length;i++){
            gamePieces[i].setHorizontalAlignment(JLabel.CENTER);
            gamePieces[i].setBackground(Color.white);
        }

        // TODO: To be set to randomly generated ID number used for randomizing game pieces
        nextPiece = gamePieces[2];

        nextPiecePanel.add(nextPieceLabel);
        nextPiecePanel.add(nextPiece);

        rightPanel.add(scorePanel);
        rightPanel.add(nextPiecePanel);

        window.add(rightPanel);
        window.pack();
        window.setResizable(false);
        window.setVisible(true);
    }

    public static String number(int x){
        String sequence = Integer.toString(x);
        return sequence;
    }
}
