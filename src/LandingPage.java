import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class LandingPage extends MouseAdapter{
    public static int rectWidth = 1;
    public static int rectHeight = 1;
    public static JFrame startWindow = new JFrame("Pentominoes: Settings");
    public static void createWindow(){
        // Window title
        startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Top text
        JLabel title = new JLabel();
        title.setText("<html><center>PENTOMINES <br> Settings</center></html>");
        title.setFont(new Font("Impact", Font.BOLD, 60));
        title.setForeground(new Color(255,206,40));
        title.setBounds(350,-10,960,200);
        title.setVisible(true);



        // Board width label
        JLabel bwLabel = new JLabel();
        bwLabel.setText("Board width:");
        bwLabel.setFont(bwLabel.getFont().deriveFont(36.0f));
        bwLabel.setForeground(Color.black);
        bwLabel.setBounds(150,350,400,75);
        bwLabel.setVisible(true);

        // Board width text field
        JTextField boardWidth = new JTextField(1);
        boardWidth.setBounds(500,350,75,75);
        boardWidth.setBackground(Color.white);
        boardWidth.setEditable(false);
        boardWidth.setFont(boardWidth.getFont().deriveFont(36.0f));
        boardWidth.setText("1");
        boardWidth.setVisible(true);

        // Board width minus button
        JButton bwMinus = new JButton();
        bwMinus.setBounds(425,350,75,75);
        bwMinus.setBackground(Color.white);
        bwMinus.setFont(bwMinus.getFont().deriveFont(36.0f));
        bwMinus.setVisible(true);
        bwMinus.setText("-");

        // Board width plus button
        JButton bwPlus = new JButton();
        bwPlus.setBounds(575,350,75,75);
        bwPlus.setBackground(Color.white);
        bwPlus.setFont(bwPlus.getFont().deriveFont(36.0f));
        bwPlus.setVisible(true);
        bwPlus.setText("+");

        // Board height label
        JLabel bhLabel = new JLabel();
        bhLabel.setText("Board height:");
        bhLabel.setFont(bhLabel.getFont().deriveFont(36.0f));
        bhLabel.setForeground(Color.black);
        bhLabel.setBounds(150,500,400,75);
        bhLabel.setVisible(true);

        // Board height text field
        JTextField boardHeight = new JTextField(1);
        boardHeight.setText("1");
        boardHeight.setBounds(500,500,75,75);
        boardHeight.setBackground(Color.white);
        boardHeight.setEditable(false);
        boardHeight.setFont(boardHeight.getFont().deriveFont(36.0f));
        boardWidth.setVisible(true);

        // Board height minus button
        JButton bhMinus = new JButton();
        bhMinus.setBounds(425,500,75,75);
        bhMinus.setBackground(Color.white);
        bhMinus.setFont(bhMinus.getFont().deriveFont(36.0f));
        bhMinus.setVisible(true);
        bhMinus.setText("-");

        // Board height plus button
        JButton bhPlus = new JButton();
        bhPlus.setBounds(575,500,75,75);
        bhPlus.setBackground(Color.white);
        bhPlus.setFont(bhPlus.getFont().deriveFont(36.0f));
        bhPlus.setVisible(true);
        bhPlus.setText("+");

        // Confirm button
        JButton go = new JButton();
        go.setText("GO!");
        go.setBounds(380,200,200,100);
        go.setVisible(true);
        go.setOpaque(true);
        go.setBorderPainted(false);
        go.setBackground(new Color(255,206,40));
        go.setFont(go.getFont().deriveFont(36.0f));

        // Background
        JLabel background = new JLabel();
        background.setVisible(true);
        background.setBounds(240,60,960,720);
        background.setLayout(new FlowLayout());


        // Mouse listeners
        bwMinus.addMouseListener(
                new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(rectWidth>1) {
                            rectWidth--;
                            boardWidth.setText(number(rectWidth));
                        }
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
                }
        );
        bwPlus.addMouseListener(
                new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(rectWidth<20) {
                            rectWidth++;
                            boardWidth.setText(number(rectWidth));
                        }
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
                }
        );

        bhMinus.addMouseListener(
                new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(rectHeight>1) {
                            rectHeight--;
                            boardHeight.setText(number(rectHeight));
                        }
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
                }
        );
        bhPlus.addMouseListener(
                new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(rectHeight<20) {
                            rectHeight++;
                            boardHeight.setText(number(rectHeight));
                        }
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
                }
        );

        go.addMouseListener(
                new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Search.area = rectHeight*rectWidth;
                        Search.verticalGridSize=rectWidth;
                        Search.horizontalGridSize=rectHeight;
                        ChoosePieces.createWindow();
                        startWindow.dispose();
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
                }
        );

        // Starting the window
        startWindow.setLayout(new BorderLayout());
        startWindow.setSize(960,720);
        startWindow.setBounds(240,60,960,720);
        startWindow.add(title);
        startWindow.add(bwLabel);
        startWindow.add(boardWidth);
        startWindow.add(bwMinus);
        startWindow.add(bwPlus);
        startWindow.add(bhLabel);
        startWindow.add(boardHeight);
        startWindow.add(bhMinus);
        startWindow.add(bhPlus);
        startWindow.add(go);
        startWindow.add(background);
        startWindow.setVisible(true);
    }
    public static String number(int x){
        String sequence = Integer.toString(x);
        return sequence;
    }
}
