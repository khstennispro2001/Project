package helicopter.simulator;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Game.
 * Main class that controls program flow and communicates between the different
 * classes.
 *
 */
class Game {

  // Used as "on-off" switches signaling whether a button is being pressed.
  public static boolean
          wStat,
          aStat,
          sStat,
          dStat,
          qStat,
          eStat,
          shiftStat,
          controlStat;

  // The size of the game window. Used for physics applications.
  public static int
          windowWidth,
          windowHeight;

  // Instances of each supporting class.
  private Renderer renderer;
  private Simulation simulation;

  // Timer used to poll for keyboard input.
  private Timer timer;

  /**
   * start().
   * Starts the game.
   *
   */
  public void start() {
      simulation = new Simulation();
      simulation.start();

      renderer = new Renderer();

      timer = new Timer();
      timer.schedule(new UpdatePositionTask(), 0, 10);

      System.out.println("Welcome to the helicopter simulator");
      System.out.println("To move forward press W...");
      System.out.println("To rotate counter clockwise press A...");
      System.out.println("To rotate clockwise press D...");
      System.out.println("To move backwards press S...");
      System.out.println("To move left press Q...");
      System.out.println("To move right press E...");
      System.out.println("To increase altitude press Shift...");
      System.out.println("To decrease altitude press CTRL...");
      System.out.println("To exit the simulation press ESC...");
  }

  /**
   * UpdatePositionTask.
   * Will send the button press data to the simulation class and send the
   * calculated coordinates from the simulation class to the rendering class.
   *
   */
  class UpdatePositionTask extends TimerTask {

    /**
     * run().
     * Called by the class timer object. Polls for keyboard button presses,
     * sends that data to the physics class to calculate movement, and then
     * passes the calculated data to the rendering class to draw the image.
     *
     */
    @Override
    public void run() {

      int accel = Simulation.ACCEL_NONE;
      int moveLR = Simulation.MOVE_NONE;
      int moveUD = Simulation.MOVE_NONE;
      int turn = Simulation.TURN_NONE;

      float xPos,yPos,height,direction;

      // Check for forward and backward movement.
      if (wStat && sStat) { // These cancel each other out.
        accel = Simulation.ACCEL_NONE;
      } else if (wStat) {
        accel = Simulation.ACCEL_FORWARD;
      } else if (sStat) {
        accel = Simulation.ACCEL_BACK;
      }

      // Check for left and right movement.
      if (qStat && eStat) {
        moveLR = Simulation.MOVE_NONE;
      } else if (qStat) {
        moveLR = Simulation.MOVE_LEFT;
      } else if (eStat) {
        moveLR = Simulation.MOVE_RIGHT;
      }

      // Check for up and down movement.
      if (controlStat && shiftStat) {
        moveUD = Simulation.MOVE_NONE;
      } else if (controlStat) {
        moveUD = Simulation.MOVE_DOWN;
      } else if (shiftStat) {
        moveUD = Simulation.MOVE_UP;
      }

      // Check for left and right turning.
      if (aStat && dStat) {
        turn = Simulation.TURN_NONE;
      } else if (aStat) {
        turn = Simulation.TURN_LEFT;
      } else if (dStat) {
        turn = Simulation.TURN_RIGHT;
      }

      // Send the data to the physics simulator.
      simulation.update(accel, moveLR, moveUD, turn);

      // Recieve the updated coordinates from the physics simulator.
      // Translate the coordinates to the window coordinate system
      // The window coordinate system has (0,0) at the top-left, and the Y-axis is downwards positive
      // The simulation coordinate system has (0,0) at the center of the window, and Y-axis is upwards positive
      xPos=simulation.getX()+Game.windowWidth/2;
      yPos=Game.windowHeight/2-simulation.getY();
      height=simulation.getHeight();
      direction=simulation.getDirection();

      // Send the calculated coordinates to the rendering class.
      renderer.drawHelicopterAt(new Position(xPos, yPos, height, direction));
    }
  }
}

/**
 * Position.
 * Holds helicopter position data.
 *
 */
class Position {
  private float xPos, yPos, height, direction;

  /**
   * Position().
   * No-arg constructor.
   *
   */
  public Position() {
    xPos = 0;
    yPos = 0;
    height = 0;
    direction = 0;
  }

  /**
   * Position().
   * Constructor.
   *
   * @param xPos The helicopters x-position.
   * @param yPos The helicopters y-position.
   * @param height The helicopters height (z-position).
   * @param direction The direction the helicopter is facing (in radians).
   *
   */
  public Position(float xPos, float yPos, float height, float direction) {
    this.xPos = xPos;
    this.yPos = yPos;
    this.height = height;
    this.direction = direction;
  }

  /**
   * setXPos().
   * Sets the helicopters x-position.
   *
   * @param xPos The helicopters x-position.
   *
   */
  public void setXPos(float xPos) {
    this.xPos = xPos;
  }

  /**
   * setYPos().
   * Sets the helicopters y-position.
   *
   * @param yPos The helicopters y-position.
   *
   */
  public void setYPos(float yPos) {
    this.yPos = yPos;
  }

  /**
   * setHeight().
   * Sets the helicopters height (z-position).
   *
   * @param height The helicopters height (z-position).
   *
   */
  public void setHeight(float height) {
    this.height = height;
  }

  /**
   * setDirection().
   * Sets the helicopters direction (in radians).
   *
   * @param direction The direction the helicopter is facing (in radians).
   *
   */
  public void setDirection(float direction) {
    this.direction = direction;
  }

  /**
   * getXPos().
   * Returns the helicopters x-position.
   *
   * @return The helicopters x-position.
   * 
   */
  public float getXPos() {
    return xPos;
  }

  /**
   * getYPos().
   * Returns the helicopters y-position.
   *
   * @return The helicopters y-position.
   *
   */
  public float getYPos() {
    return yPos;
  }

  /**
   * getHeight().
   * Returns the helicopters height (z-position).
   *
   * @return The helicopters height (z-position).
   *
   */
  public float getHeight() {
    return height;
  }

  /**
   * getDirection().
   * Returns the helicopters direction (in radians).
   *
   * @return The helicopters direction (in radians).
   *
   */
  public float getDirection() {
    return direction;
  }

  /**
   * toString().
   * Specifies a string representing the data in the class.
   *
   * @return A string representing the data in the class.
   *
   */
  public String toString() {
    return "X-Pos: " + xPos +
           "\nY-Pos: " + yPos +
           "\nZ-Pos: " + height +
           "\ndirection: " + direction;
  }
}
