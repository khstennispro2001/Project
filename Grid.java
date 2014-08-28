package helicopter.simulator;

import java.awt.*;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Event;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Grid extends JFrame implements KeyListener
{
  private boolean
          wStat,
          aStat,
          sStat,
          dStat,
          qStat,
          eStat,
          shiftStat,
          controlStat;

    public Grid(int x, int y)
    {
        JFrame frameY = new JFrame();

        JPanel contentPane = new JPanel();
        JPanel HelicopterLabel = new JPanel();

        this.requestFocus();
        addKeyListener(this);

        try
        {
            JFrame.setDefaultLookAndFeelDecorated(true);
            contentPane.setLayout(null);

            JLabel imageLabel = new JLabel(new ImageIcon(this.getClass().getResource("blackhawk.gif")));
            JLabel joystickLabel = new JLabel(new ImageIcon(this.getClass().getResource("bullseye.jpg")));

            HelicopterLabel.add(imageLabel);
            HelicopterLabel.setLocation(x,y);
            HelicopterLabel.setSize(160,160);

            joystickLabel.setLocation(400, 900);
            joystickLabel.setSize(75, 75);

            JPanel bottomPanel = new JPanel();
            bottomPanel.setBackground(Color.GRAY);
            JLabel instructionBottom = new JLabel("DOWN");
            bottomPanel.add(instructionBottom);
            bottomPanel.setLocation(400, 975);
            bottomPanel.setSize(75, 25);

            JPanel topPanel = new JPanel();
            topPanel.setBackground(Color.GRAY);
            JLabel instructionTop = new JLabel("UP");
            topPanel.add(instructionTop);
            topPanel.setLocation(400, 875);
            topPanel.setSize(75, 25);

            JPanel leftPanel = new JPanel();
            leftPanel.setBackground(Color.LIGHT_GRAY);
            JLabel instructionLeft = new JLabel("LEFT");
            leftPanel.add(instructionLeft);
            leftPanel.setLocation(350, 875);
            leftPanel.setSize(50, 125);

            JPanel rightPanel = new JPanel();
            rightPanel.setBackground(Color.LIGHT_GRAY);
            JLabel instructionRight = new JLabel("RIGHT");
            rightPanel.add(instructionRight);
            rightPanel.setLocation(475, 875);
            rightPanel.setSize(50, 125);

            JPanel throttlePanel = new JPanel();
            JLabel throttleLabel = new JLabel(new ImageIcon(this.getClass().getResource("throttle.jpg")));
            JLabel instructionThrottle = new JLabel("Throttle");
            throttlePanel.add(throttleLabel);
            throttlePanel.add(instructionThrottle);
            throttlePanel.setLocation(525, 915);
            throttlePanel.setSize(50, 50);


            JPanel wPanel = new JPanel();
            JLabel wThrottle = new JLabel("W");
            wPanel.setBackground(Color.WHITE);
            wPanel.add(wThrottle);
            wPanel.setLocation(525, 875);
            wPanel.setSize(50, 40);

            JPanel sPanel = new JPanel();
            JLabel sThrottle = new JLabel("S");
            sPanel.setBackground(Color.WHITE);
            sPanel.add(sThrottle);
            sPanel.setLocation(525, 970);
            sPanel.setSize(50, 40);

            JPanel lSidePanel = new JPanel();
            lSidePanel.setBackground(Color.RED);
            lSidePanel.setLocation(0, 875);
            lSidePanel.setSize(5000, 150);
            
            contentPane.add(HelicopterLabel);
            contentPane.add(joystickLabel);
            contentPane.add(bottomPanel);
            contentPane.add(topPanel);
            contentPane.add(leftPanel);
            contentPane.add(rightPanel);
            contentPane.add(throttlePanel);
            contentPane.add(wPanel);
            contentPane.add(sPanel);
            contentPane.add(lSidePanel);
            contentPane.setOpaque(true);
            

            frameY.setContentPane(contentPane);
            frameY.setSize(900, 1050);
            frameY.setVisible(true);

            frameY.setDefaultCloseOperation(EXIT_ON_CLOSE);
            frameY.setVisible(true);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

 

    public static void main(String[] args)
    {
        new Grid(100, 100);
    }

  /**
   * Listener for a key being typed. Will not be used for the purposes of this
   * program.
   *
   * @param ke The key event being input.
   *
   */
  @Override
  public void keyTyped(KeyEvent ke) {
    // Unused
  }

  /**
   * Listens for a key being pressed.
   *
   * @param ke The key event being input.
   *
   */
  @Override
  public void keyPressed(KeyEvent ke) {
    int keyPressed = ke.getKeyCode();

    switch(keyPressed) {
      case 87: // W: Forward
        Game.wStat = true;
        System.out.println("W Pressed");
        break;
      case 65: // A: Rotate Counter Clockwise
        Game.aStat = true;
        break;
      case 83: // S: Rotate Clockwise
        Game.sStat = true;
        break;
      case 68: // D: Backward
        Game.dStat = true;
        break;
      case 81: // Q: Strafe Left
        Game.qStat = true;
        break;
      case 69: // E: Strafe Right
        Game.eStat = true;
        break;
      case 16: // Shift: Increase Altitude
        Game.shiftStat = true;
        break;
      case 17: // Control: Decrease Altitude
        Game.controlStat = true;
        break;
      case 27: // Escape: Terminate Program
        System.exit(0);
        break;
      default:
        break;
    }
  }

  /**
   * Listens for a key being released.
   *
   * @param ke The key event being input.
   *
   */
  @Override
  public void keyReleased(KeyEvent ke) {
    int keyReleased = ke.getKeyCode();

    switch(keyReleased) {
      case 87: // W: Forward
        Game.wStat = false;
        break;
      case 65: // A: Rotate Counter Clockwise
        Game.aStat = false;
        break;
      case 83: // S: Rotate Clockwise
        Game.sStat = false;
        break;
      case 68: // D: Backward
        Game.dStat = false;
        break;
      case 81: // Q: Strafe Left
        Game.qStat = false;
        break;
      case 69: // E: Strafe Right
        Game.eStat = false;
        break;
      case 16: // Shift: Increase Altitude
        Game.shiftStat = false;
        break;
      case 17: // Control: Decrease Altitude
        Game.controlStat = false;
        break;
      default:
        break;
    }
  }
}

