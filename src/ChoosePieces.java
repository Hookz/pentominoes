import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;

import java.awt.event.*;
public class ChoosePieces {
    public static JFrame choosePieces = new JFrame("Pentominoes: Settings");
    public static void createWindow(){
        BufferedImage img;
        Image dimg;
        ImageIcon imageIcon;
        choosePieces.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel();
        title.setText("Choose game pieces");
        title.setFont(new Font("Impact", Font.BOLD, 60));
        title.setForeground(new Color(255,206,40));
        title.setBounds(225,-10,960,200);
        title.setVisible(true);


        JLabel pieceF = new JLabel();
        pieceF.setBounds(150,150,75,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/F.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieceF.getWidth(), pieceF.getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieceF.setIcon(imageIcon);
        pieceF.setVisible(true);

        JLabel pieceI = new JLabel();
        pieceI.setBounds(250,150,25,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/I.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieceI.getWidth(), pieceI.getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieceI.setIcon(imageIcon);
        pieceI.setVisible(true);

        JLabel pieceL = new JLabel();
        pieceL.setBounds(300,150,50,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/L.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieceL.getWidth(), pieceL.getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieceL.setIcon(imageIcon);
        pieceL.setVisible(true);

        JLabel pieceN = new JLabel();
        pieceN.setBounds(375,150,50,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/N.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieceN.getWidth(), pieceN.getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieceN.setIcon(imageIcon);
        pieceN.setVisible(true);

        JLabel pieceP = new JLabel();
        pieceP.setBounds(450,150,50,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/P.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieceP.getWidth(), pieceP.getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieceP.setIcon(imageIcon);
        pieceP.setVisible(true);

        JLabel pieceT = new JLabel();
        pieceT.setBounds(525,150,75,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/T.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieceT.getWidth(), pieceT.getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieceT.setIcon(imageIcon);
        pieceT.setVisible(true);

        JLabel pieceU = new JLabel();
        pieceU.setBounds(150,300,75,50);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/U.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieceU.getWidth(), pieceU.getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieceU.setIcon(imageIcon);
        pieceU.setVisible(true);

        JLabel pieceV = new JLabel();
        pieceV.setBounds(250,275,75,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/V.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieceV.getWidth(), pieceV.getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieceV.setIcon(imageIcon);
        pieceV.setVisible(true);

        JLabel pieceW = new JLabel();
        pieceW.setBounds(350,275,75,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/W.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieceW.getWidth(), pieceW.getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieceW.setIcon(imageIcon);
        pieceW.setVisible(true);

        JLabel pieceX = new JLabel();
        pieceX.setBounds(450,275,75,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/X.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieceX.getWidth(), pieceX.getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieceX.setIcon(imageIcon);
        pieceX.setVisible(true);

        JLabel pieceY = new JLabel();
        pieceY.setBounds(550,275,50,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/Y.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieceY.getWidth(), pieceY.getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieceY.setIcon(imageIcon);
        pieceY.setVisible(true);

        JLabel pieceZ = new JLabel();
        pieceZ.setBounds(550,375,75,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/Z.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieceZ.getWidth(), pieceZ.getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieceZ.setIcon(imageIcon);
        pieceZ.setVisible(true);

        JLabel background = new JLabel();
        background.setVisible(true);
        background.setBounds(240,60,960,720);
        background.setLayout(new FlowLayout());

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
        choosePieces.add(background);
        choosePieces.setVisible(true);
    }
}
