/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helicopter.simulator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author jmdarling
 */
public class Renderer extends JFrame implements KeyListener {
  private final int PLAY_SQUARE_LENGTH = 800;
  private int xPos = 50;
  private int yPos = 50;
  private int zPos = 10;
  private float direction = 0;
  private BufferedImage background;
  public static int bladeOrientation = 0;
  private Thread controller;

  public Renderer() {
    setTitle("RC Helicopter Simulator");
    setSize(PLAY_SQUARE_LENGTH, PLAY_SQUARE_LENGTH);
    setVisible(true);
    addKeyListener(this);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    RendererPanel rendererPanel = new RendererPanel(PLAY_SQUARE_LENGTH,PLAY_SQUARE_LENGTH);
    Game.windowWidth=PLAY_SQUARE_LENGTH;
    Game.windowHeight=PLAY_SQUARE_LENGTH;
    add(rendererPanel);

    BladeOrientationTask bladeOrientationTask = new BladeOrientationTask();
    controller = new Thread(bladeOrientationTask);
    controller.start();
    System.out.println(System.getProperty("user.dir"));
    try {
      background = ImageIO.read(new File("desert_background.jpg"));
    } catch (IOException ex) {
      Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  public void drawHelicopterAt(Position position) {
    if ((position.getXPos() >= 0) && (position.getXPos() <= PLAY_SQUARE_LENGTH)) {
       xPos = (int)position.getXPos();
    }

    if ((position.getYPos() >= 0) && (position.getYPos() <= PLAY_SQUARE_LENGTH)) {
      yPos = (int)position.getYPos();
    }

    if ((position.getHeight() >= 8) && (position.getHeight() <= 73)) {
      zPos = (int)position.getHeight();
    }
    
    direction = position.getDirection();

    repaint();
  }

  
    

  class RendererPanel extends JPanel implements ActionListener {
      public RendererPanel(int width,int height) {
            setSize(width, height);
            //Add buttons to control Helicopter
            JButton forward = new JButton("Forward");
            forward.setSize(20,20);
            JButton counterClockWise = new JButton("Counter Clockwise");
            JButton clockwise = new JButton ("Clockwise");
            JButton backward = new JButton ("Backwards");
            JButton strafeLeft = new JButton ("Left");
            JButton strafeRight = new JButton("Right");
            JButton increaseAltitude = new JButton("Increase Altitude");
            JButton decreaseAltitude = new JButton("Decrease Altitude");
            JButton exit = new JButton("Exit");
            GridLayout grid = new GridLayout(3,3);
            JPanel buttons = new JPanel();
            buttons.setLayout(grid);
            buttons.add(forward);
            buttons.add(counterClockWise);
            buttons.add(clockwise);
            buttons.add(backward);
            buttons.add(strafeLeft);
            buttons.add(strafeRight);
            buttons.add(increaseAltitude);
            buttons.add(decreaseAltitude);
            buttons.add(exit);
            forward.addActionListener(this);
            counterClockWise.addActionListener(this);
            clockwise.addActionListener(this);
            backward.addActionListener(this);
            strafeLeft.addActionListener(this);
            strafeRight.addActionListener(this);
            increaseAltitude.addActionListener(this);
            decreaseAltitude.addActionListener(this);
            exit.addActionListener(this);
            add("North",buttons);
      }
      @Override
      protected void paintComponent(Graphics g) {
          // Draw the background.
          g.drawImage(background, 0, 0, this);
          // Draw the body and rudder.
          g.setColor(Color.RED);
          // The body is a square of zPos by zPos dimensions, centered on xPos and yPos, rotated by direction
          float cos_dir = (float) Math.cos(direction);
          float sin_dir = (float) Math.sin(direction);
          int[] xPoints={-zPos/2,-zPos/2,zPos/2,zPos/2};
          int[] yPoints={-zPos/2,zPos/2,zPos/2,-zPos/2};
          int x,y,n;
          for(n=0;n<4;n++) {
              // Rotate by direction
              x=xPoints[n];
              y=yPoints[n];
              xPoints[n]=(int) (cos_dir*x - sin_dir*y);
              yPoints[n]=(int) (cos_dir*y + sin_dir*x);
              // Center at xPos,yPos
              xPoints[n]+=xPos;
              yPoints[n]+=yPos;
          }
          g.fillPolygon(xPoints, yPoints, 4);
          // The tail is zPos/5 wide and zPos long, positioned at the bottom-midpoint of the body
          xPoints=new int[]{-zPos/10,-zPos/10,zPos/10,zPos/10};
          yPoints=new int[]{-zPos/2+zPos/2,zPos/2+zPos/2,zPos/2+zPos/2,-zPos/2+zPos/2};
          for(n=0;n<4;n++) {
              // Rotate by direction
              x=xPoints[n];
              y=yPoints[n];
              xPoints[n]=(int) (cos_dir*x - sin_dir*y);
              yPoints[n]=(int) (cos_dir*y + sin_dir*x);
              // Center at xPos,yPos
              xPoints[n]+=xPos;
              yPoints[n]+=yPos;
          }
          g.fillPolygon(xPoints, yPoints, 4);

          // Draw the blades.
          g.setColor(Color.BLACK);
          g.fillArc(xPos - zPos, yPos - zPos, (2 * zPos), (2 * zPos), 0 + bladeOrientation, 30);
          g.fillArc(xPos - zPos, yPos - zPos, (2 * zPos), (2 * zPos), 90 + bladeOrientation, 30);
          g.fillArc(xPos - zPos, yPos - zPos, (2 * zPos), (2 * zPos), 180 + bladeOrientation, 30);
          g.fillArc(xPos - zPos, yPos - zPos, (2 * zPos), (2 * zPos), 270 + bladeOrientation, 30);

      }

    @Override
    public void actionPerformed(ActionEvent e) {
    String label = e.getActionCommand();
    if(e.getSource() instanceof JButton)
    {
        if ("Forward".equals(label))
        {
            Game.wStat = true;
            System.out.println("Helocopter is moving forward");
        }
        else if("Counter Clockwise".equals(label))
        {
            Game.aStat = true;
            System.out.println("Helicopter is rotating counterclockwise");
        }
        else if ("clockwise".equals(label))
        {
            Game.sStat = true;
            System.out.println("Helicopter is rotating clockwise");
        }
        else if("Backwards".equals(label))
        {
            Game.dStat = true;
            System.out.println("Helicopter is moving backwards");
        }
        else if ("Left".equals(label))
        {
            Game.qStat = true;
            System.out.println("Helicopter is moving left");
        }
        else if ("Right".equals(label))
        {
            Game.eStat = true;
            System.out.println("Helicopter is moving right");
        }
        else if ("Increase Altitude".equals(label))
        {
            Game.shiftStat = true;
            System.out.println("Altitude has increased");
        }
        else if ("Decrease Altitude".equals(label))
        {
            Game.controlStat = true;
            System.out.println("Altitude has decreased");
        }
        else if ("Exit".equals(label))
        {
            System.exit(0);
        }
    }
           }
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
class BladeOrientationTask implements Runnable {
  @Override
  public void run() {
    while(true) {
      Renderer.bladeOrientation += 1;
      try {
        Thread.sleep(3);
      } catch (InterruptedException ex) {
        Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

  }
}