package Phase2.Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameModeSelector extends MouseAdapter{
    public static JFrame selectionWindow = new JFrame("Pentris: Game Options");
    public static void createWindow(){
        selectionWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        selectionWindow.setLayout(new GridLayout(4,1));
        //Top text
        JLabel title = new JLabel();
        title.setText("PENTRIS Settings");
        title.setFont(new Font("Impact", Font.BOLD, 60));
        title.setForeground(new Color(255,206,40));
        title.setHorizontalAlignment(JLabel.CENTER);

        //Creating buttons
        JButton humanPlayer = new JButton("Human Player");
        JButton geneticBot = new JButton("G-Bot (Genetic)");
        JButton scoreBot = new JButton("Q-Bot");

        // Mouse listeners
        humanPlayer.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Tetris.enableBot = false;
                Tetris.gmsdone = true;
                selectionWindow.dispose();
                synchronized (Tetris.waitingObject){
                    Tetris.waitingObject.notifyAll();
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {
                Tetris.enableBot = false;
                Tetris.gmsdone = true;
                selectionWindow.dispose();
                synchronized (Tetris.waitingObject){
                    Tetris.waitingObject.notifyAll();
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        geneticBot.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Tetris.enableBot = true;
                Tetris.gmsdone = true;
                Tetris.botType = "G";
                selectionWindow.dispose();
                synchronized (Tetris.waitingObject){
                    Tetris.waitingObject.notifyAll();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Tetris.enableBot = true;
                Tetris.gmsdone = true;
                Tetris.botType = "G";
                selectionWindow.dispose();
                synchronized (Tetris.waitingObject){
                    Tetris.waitingObject.notifyAll();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        scoreBot.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Tetris.enableBot = true;
                Tetris.gmsdone = true;
                Tetris.botType = "Q";
                selectionWindow.dispose();
                synchronized (Tetris.waitingObject){
                    Tetris.waitingObject.notifyAll();
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {
                Tetris.enableBot = true;
                Tetris.gmsdone = true;
                Tetris.botType = "Q";
                selectionWindow.dispose();
                synchronized (Tetris.waitingObject){
                    Tetris.waitingObject.notifyAll();
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        //Adding buttons to window
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(title,BorderLayout.CENTER);
        selectionWindow.add(titlePanel);
        selectionWindow.add(humanPlayer);
        selectionWindow.add(geneticBot);
        selectionWindow.add(scoreBot);

        //Displaying window
        selectionWindow.setSize(960,720);
        //selectionWindow.pack();
        selectionWindow.setResizable(false);
        selectionWindow.setVisible(true);
    }
}
