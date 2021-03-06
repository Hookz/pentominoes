package Phase1;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;

import java.awt.event.*;

/**
 * Class with the part of the GUI responsible for allowing the user to choose desired pentomino pieces
 */
public class ChoosePieces {
    public static JLabel[] pieces;
    /**
     * The array to keep track of used pieces
     */
    private boolean[] piecesUsed;
    /**
     * The frame that fires to enable the user to choose settings before running the main program from Search class
     */
    private JFrame choosePieces;

    /**
    * Default class constructor
    * <br>
    * Whenever used, it makes sure that a new class instance is fired and that all settings are reset to default values
    */

    public ChoosePieces(){
        piecesUsed = new boolean[12];
        choosePieces = new JFrame("Pentominoes: Settings");
        createPieces();
    }
    /**
     * Creates a window of the class to make it possible for user to input desired values
     * <br>
     * It adds the interactive layer with all clickable and adjustable elements to the GUI.
     */

    public void createPieces(){
        BufferedImage img;
        Image dimg;
        ImageIcon imageIcon;
        pieces = new JLabel[24];

        pieces[0] = new JLabel();
        pieces[0].setBounds(450,275,75,75);
        img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/img/pieces/X.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error with X");
        }
        dimg = img.getScaledInstance(pieces[0].getWidth(), pieces[0].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[0].setIcon(imageIcon);
        pieces[0].setVisible(true);

        pieces[1] = new JLabel();
        pieces[1].setBounds(250,150,25,100);
        img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/img/pieces/I.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[1].getWidth(), pieces[1].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[1].setIcon(imageIcon);

        pieces[2] = new JLabel();
        pieces[2].setBounds(550,400,75,75);
        img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/img/pieces/Z.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[2].getWidth(), pieces[2].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[2].setIcon(imageIcon);

        pieces[3] = new JLabel();
        pieces[3].setBounds(525,150,75,75);
        img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/img/pieces/T.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[3].getWidth(), pieces[3].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[3].setIcon(imageIcon);

        pieces[4] = new JLabel();
        pieces[4].setBounds(150,300,75,50);
        img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/img/pieces/U.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[4].getWidth(), pieces[4].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[4].setIcon(imageIcon);

        pieces[5] = new JLabel();
        pieces[5].setBounds(250,275,75,75);
        img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/img/pieces/V.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[5].getWidth(), pieces[5].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[5].setIcon(imageIcon);

        pieces[6] = new JLabel();
        pieces[6].setBounds(350,275,75,75);
        img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/img/pieces/W.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[6].getWidth(), pieces[6].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[6].setIcon(imageIcon);

        pieces[7] = new JLabel();
        pieces[7].setBounds(550,275,50,100);
        img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/img/pieces/Y.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[7].getWidth(), pieces[7].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[7].setIcon(imageIcon);

        pieces[8] = new JLabel();
        pieces[8].setBounds(300,150,50,100);
        img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/img/pieces/L.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[8].getWidth(), pieces[8].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[8].setIcon(imageIcon);

        pieces[9] = new JLabel();
        pieces[9].setBounds(450,150,50,75);
        img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/img/pieces/P.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[9].getWidth(), pieces[9].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[9].setIcon(imageIcon);

        pieces[10] = new JLabel();
        pieces[10].setBounds(375,150,50,100);
        img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/img/pieces/N.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[10].getWidth(), pieces[10].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[10].setIcon(imageIcon);

        pieces[11] = new JLabel();
        pieces[11].setBounds(150,150,75,75);
        img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/img/pieces/F.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[11].getWidth(), pieces[11].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[11].setIcon(imageIcon);

        //--------------------------------------REFLECTIONS---------------------------------------

        pieces[12] = new JLabel();
        pieces[13] = new JLabel();
        pieces[15] = new JLabel();
        pieces[16] = new JLabel();
        pieces[17] = new JLabel();
        pieces[18] = new JLabel();


        pieces[14] = new JLabel();
        pieces[14].setBounds(550,400,75,75);
        img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/img/pieces/Z1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[14].getWidth(), pieces[14].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[14].setIcon(imageIcon);

        pieces[19] = new JLabel();
        pieces[19].setBounds(550,275,50,100);
        img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/img/pieces/Y1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[19].getWidth(), pieces[19].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[19].setIcon(imageIcon);

        pieces[20] = new JLabel();
        pieces[20].setBounds(300,150,50,100);
        img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/img/pieces/L1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[20].getWidth(), pieces[20].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[20].setIcon(imageIcon);

        pieces[21] = new JLabel();
        pieces[21].setBounds(450,150,50,75);
        img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/img/pieces/P1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[21].getWidth(), pieces[21].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[21].setIcon(imageIcon);

        pieces[22] = new JLabel();
        pieces[22].setBounds(375,150,50,100);
        img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/img/pieces/N1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[22].getWidth(), pieces[22].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[22].setIcon(imageIcon);

        pieces[23] = new JLabel();
        pieces[23].setBounds(150,150,75,75);
        img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/img/pieces/F1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(pieces[23].getWidth(), pieces[23].getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(dimg);
        pieces[23].setIcon(imageIcon);
    }


    public void createWindow(){
        choosePieces.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel();
        title.setText("Choose exactly " + Search.area/5 +" piece(s)");
        title.setFont(new Font("Impact", Font.BOLD, 60));
        title.setForeground(new Color(255,206,40));
        title.setBounds(225,-10,960,200);
        title.setVisible(true);

        for(int i=0;i<12;i++)
            pieces[i].setVisible(true);
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
                Search.input = returnPiecesUsed();
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

    /**
     *
     * @param pentID: numeric representation of the pentomino to be converted to character
     * @return character representation of the pentomino to be used in the char array Search.input
     */
    private char intToCharacter(int pentID){
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

    /**
     * A function mapping the boolean array piecesUsed to a char array with letters corresponding to the used pieces.
     * @return returns the char array to be assigned to Search.input
     */
    private char[] returnPiecesUsed(){
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