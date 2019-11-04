package Phase1;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * This class takes care of all the graphics to display a certain state of the game field
 */
public class UI extends JPanel
{
  private JFrame window;
  private int[][] state;
  private int size;

  /**
   *
   * @param x: number of cells in a row (width)
   * @param y: number of cells in a column (height)
   * @param _size: the size of the side of each cell
   */
  public UI(int x, int y, int _size)
  {
    size = _size;
    setPreferredSize(new Dimension(x * size, y * size));

    window = new JFrame("Pentomino");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.add(this);
    window.pack();
    window.setVisible(true);

    state = new int[x][y];
    for (int i = 0; i < state.length; i++)
    {
      for (int j = 0; j < state[i].length; j++)
      {
        state[i][j] = -1;
      }
    }
  }

  // Paint function, called by the system if required for a new frame, uses the state stored by the Phase1.UI class
  public void paintComponent(Graphics g)
  {
    Graphics2D localGraphics2D = (Graphics2D) g;

    localGraphics2D.setColor(Color.LIGHT_GRAY);
    localGraphics2D.fill(getVisibleRect());

    // draw lines
    localGraphics2D.setColor(Color.GRAY);
    for (int i = 0; i <= state.length; i++)
    {
      localGraphics2D.drawLine(i * size, 0, i * size, state[0].length * size);
    }
    for (int i = 0; i <= state[0].length; i++)
    {
      localGraphics2D.drawLine(0, i * size, state.length * size, i * size);
    }

    // draw blocks
    for (int i = 0; i < state.length; i++)
    {
      for (int j = 0; j < state[0].length; j++)
      {
        localGraphics2D.setColor(GetColorOfID(state[i][j]));
        localGraphics2D.fill(new Rectangle2D.Double(i * size + 1, j * size + 1, size - 1, size - 1));
      }
    }
  }

  // Decodes the ID of a pentomino into a color
  private Color GetColorOfID(int i)
  {
    if(i==0) {return Color.BLUE;}
    else if(i==1) {return Color.ORANGE;}
    else if(i==2) {return Color.CYAN;}
    else if(i==3) {return Color.GREEN;}
    else if(i==4) {return Color.MAGENTA;}
    else if(i==5) {return Color.PINK;}
    else if(i==6) {return Color.RED;}
    else if(i==7) {return Color.YELLOW;}
    else if(i==8) {return new Color(0, 0, 0);}
    else if(i==9) {return new Color(0, 0, 100);}
    else if(i==10) {return new Color(100, 0,0);}
    else if(i==11) {return new Color(0, 100, 0);}
    else {return Color.LIGHT_GRAY;}
  }

  // This function should be called to update the displayed state (Makes a copy)
  public void setState(int[][] _state)
  {
    for (int i = 0; i < state.length; i++)
    {
      for (int j = 0; j < state[i].length; j++)
      {
        state[i][j] = _state[i][j];
      }
    }

    // Tells the system a frame update is required
    repaint();
  }
}
