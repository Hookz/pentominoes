import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;

import java.awt.event.*;
public class ChoosePieces {
    public static boolean[] piecesUsed = new boolean[12];
    public static JFrame choosePieces = new JFrame("Pentominoes: Settings");
    public static void createWindow(){
        BufferedImage img;
        Image dimg;
        ImageIcon imageIcon;
        choosePieces.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel();
        title.setText("Choose exactly " + Search.area/5 +" piece(s)");
        title.setFont(new Font("Impact", Font.BOLD, 60));
        title.setForeground(new Color(255,206,40));
        title.setBounds(225,-10,960,200);
        title.setVisible(true);


        JLabel[] pieces = new JLabel[12];
        pieces[0] = new JLabel();
        pieces[0].setBounds(150,150,75,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/F.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[0].getWidth(), pieces[0].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[0].setIcon(imageIcon);
        pieces[0].setVisible(true);

        pieces[1] = new JLabel();
        pieces[1].setBounds(250,150,25,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/I.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[1].getWidth(), pieces[1].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[1].setIcon(imageIcon);
        pieces[1].setVisible(true);

        pieces[2] = new JLabel();
        pieces[2].setBounds(300,150,50,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/L.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[2].getWidth(), pieces[2].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[2].setIcon(imageIcon);
        pieces[2].setVisible(true);

        pieces[3] = new JLabel();
        pieces[3].setBounds(375,150,50,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/N.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[3].getWidth(), pieces[3].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[3].setIcon(imageIcon);
        pieces[3].setVisible(true);

        pieces[4] = new JLabel();
        pieces[4].setBounds(450,150,50,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/P.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[4].getWidth(), pieces[4].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[4].setIcon(imageIcon);
        pieces[4].setVisible(true);

        pieces[5] = new JLabel();
        pieces[5].setBounds(525,150,75,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/T.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[5].getWidth(), pieces[5].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[5].setIcon(imageIcon);
        pieces[5].setVisible(true);

        pieces[6] = new JLabel();
        pieces[6].setBounds(150,300,75,50);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/U.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[6].getWidth(), pieces[6].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[6].setIcon(imageIcon);
        pieces[6].setVisible(true);

        pieces[7] = new JLabel();
        pieces[7].setBounds(250,275,75,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/V.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[7].getWidth(), pieces[7].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[7].setIcon(imageIcon);
        pieces[7].setVisible(true);

        pieces[8] = new JLabel();
        pieces[8].setBounds(350,275,75,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/W.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[8].getWidth(), pieces[8].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[8].setIcon(imageIcon);
        pieces[8].setVisible(true);

        pieces[9] = new JLabel();
        pieces[9].setBounds(450,275,75,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/X.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[9].getWidth(), pieces[9].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[9].setIcon(imageIcon);
        pieces[9].setVisible(true);

        pieces[10] = new JLabel();
        pieces[10].setBounds(550,275,50,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/Y.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[10].getWidth(), pieces[10].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[10].setIcon(imageIcon);
        pieces[10].setVisible(true);

        pieces[11] = new JLabel();
        pieces[11].setBounds(550,375,75,75);
        img = null;
        try {
            img = ImageIO.read(new File("img/pieces/Z.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[11].getWidth(), pieces[11].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[11].setIcon(imageIcon);
        pieces[11].setVisible(true);

        JButton goWithChosen = new JButton("Go with chosen!");
        goWithChosen.setBounds(300,500,375,75);
        goWithChosen.setOpaque(true);
        goWithChosen.setBackground(new Color(255,206,40));
        goWithChosen.setBorderPainted(false);
        goWithChosen.setFont(goWithChosen.getFont().deriveFont(36.0f));
        goWithChosen.setVisible(true);

        JLabel background = new JLabel();
        background.setVisible(true);
        background.setBounds(240,60,960,720);
        background.setLayout(new FlowLayout());

        goWithChosen.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Search.input = returnPiecesUsed(piecesUsed);
                Search.lpdone = true;
                choosePieces.dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {

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
        for(int i=0;i<pieces.length;i++) {
            int j=i;
            pieces[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    piecesUsed[j] = !piecesUsed[j];
                    pieces[j].setVisible(false);
                }

                @Override
                public void mousePressed(MouseEvent e) {

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
        }

        choosePieces.setLayout(new BorderLayout());
        choosePieces.setSize(960,720);
        choosePieces.setBounds(240,60,960,720);
        choosePieces.add(title);
        choosePieces.add(pieces[0]);
        choosePieces.add(pieces[1]);
        choosePieces.add(pieces[2]);
        choosePieces.add(pieces[3]);
        choosePieces.add(pieces[4]);
        choosePieces.add(pieces[5]);
        choosePieces.add(pieces[6]);
        choosePieces.add(pieces[7]);
        choosePieces.add(pieces[8]);
        choosePieces.add(pieces[9]);
        choosePieces.add(pieces[10]);
        choosePieces.add(pieces[11]);
        choosePieces.add(goWithChosen);
        choosePieces.add(background);
        choosePieces.setVisible(true);
    }

    private static char intToCharacter(int pentID){
        char character=' ';
        if (pentID==0) {
            character = 'X';
        } else if (pentID == 1) {
            character = 'I';
        } else if (pentID == 2) {
            character = 'Z';

        } else if (pentID == 3) {
            character = 'T';
        } else if (pentID == 4) {
            character = 'U';
        } else if (pentID == 5) {
            character = 'V';
        } else if (pentID == 6) {
            character = 'W';
        } else if (pentID == 7) {
            character = 'Y';
        } else if (pentID == 8) {
            character = 'L';
        } else if (pentID == 9) {
            character = 'P';
        } else if (pentID == 10) {
            character = 'N';
        } else if (pentID == 11) {
            character = 'F';
        }
        return character;
    }
    private static char[] returnPiecesUsed(boolean[] piecesUsed){
        char[] pcsUsed;
        int noUsed=0, j=0;
        for(int i=0;i<piecesUsed.length;i++)
            if(piecesUsed[i]) noUsed++;
        pcsUsed = new char[noUsed];
        for(int i=0;i<piecesUsed.length;i++){
            if(piecesUsed[i]){
                pcsUsed[j]=intToCharacter(i);
                j++;
            }
        }
        return pcsUsed;
    }
}
