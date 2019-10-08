import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ChoosePieces {
    public static JFrame choosePieces = new JFrame("Pentominoes: Settings");
    public static void createWindow(){
        choosePieces.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel();
        title.setText("Choose game pieces");
        title.setFont(new Font("Impact", Font.BOLD, 60));
        title.setForeground(new Color(255,206,40));
        title.setBounds(350,-10,960,200);
        title.setVisible(true);

        JLabel pieceF = new JLabel(new ImageIcon("img/pieces/F.png"));
        pieceF.setBounds(150,50,400,75);
        pieceF.setVisible(true);

        JLabel pieceI = new JLabel(new ImageIcon("img/pieces/I.png"));
        pieceI.setBounds(150,100,400,75);
        pieceI.setVisible(true);

        JLabel pieceL = new JLabel(new ImageIcon("img/pieces/L.png"));
        pieceL.setBounds(150,150,400,75);
        pieceL.setVisible(true);

        JLabel pieceN = new JLabel(new ImageIcon("img/pieces/N.png"));
        pieceN.setBounds(150,200,400,75);
        pieceN.setVisible(true);

        JLabel pieceP = new JLabel(new ImageIcon("img/pieces/P.png"));
        pieceP.setBounds(150,250,400,75);
        pieceP.setVisible(true);

        JLabel pieceT = new JLabel(new ImageIcon("img/pieces/T.png"));
        pieceT.setBounds(150,300,400,75);
        pieceT.setVisible(true);

        JLabel pieceU = new JLabel(new ImageIcon("img/pieces/U.png"));
        pieceU.setBounds(150,350,400,75);
        pieceU.setVisible(true);

        JLabel pieceV = new JLabel(new ImageIcon("img/pieces/V.png"));
        pieceV.setBounds(150,400,400,75);
        pieceV.setVisible(true);

        JLabel pieceW = new JLabel(new ImageIcon("img/pieces/W.png"));
        pieceW.setBounds(150,450,400,75);
        pieceW.setVisible(true);

        JLabel pieceX = new JLabel(new ImageIcon("img/pieces/X.png"));
        pieceX.setBounds(150,500,400,75);
        pieceX.setVisible(true);

        JLabel pieceY = new JLabel(new ImageIcon("img/pieces/Y.png"));
        pieceY.setBounds(150,550,400,75);
        pieceY.setVisible(true);

        JLabel pieceZ = new JLabel(new ImageIcon("img/pieces/Z.png"));
        pieceZ.setBounds(150,600,400,75);
        pieceZ.setVisible(true);

        choosePieces.setLayout(new BorderLayout());
        choosePieces.setSize(960,720);
        choosePieces.setBounds(240,60,960,720);
        choosePieces.add(title);
        choosePieces.add(pieceF);
        choosePieces.add(pieceI);
        choosePieces.add(pieceL);
        choosePieces.add(pieceN);
        choosePieces.add(pieceP);
        choosePieces.add(pieceT);
        choosePieces.add(pieceU);
        choosePieces.add(pieceV);
        choosePieces.add(pieceW);
        choosePieces.add(pieceX);
        choosePieces.add(pieceY);
        choosePieces.add(pieceZ);
        choosePieces.setVisible(true);
    }
}
