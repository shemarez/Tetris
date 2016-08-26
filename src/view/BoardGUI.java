/*
 * TCSS305 Tetris part b
 * winter 2016
 */
package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Block;
import model.Board;
/**
 * Creates new Board.java and shows it on GUI. 
 * Builds all tetris pieces, and starts timer. 
 * Presses the required keys for user.
 * 
 * @author Shema Rezanejad
 * @version 1
 *
 */
public class BoardGUI extends JPanel implements Observer {
    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = -6232946033965941866L;
    /**
     * Animation speed.
     */
    private static final int ANIMATION_SPEED = 500;

    /** Block width. */
    private static final int BLOCK_WIDTH = 20;

    /** Panel size. */
    private static final int PANEL_SIZE = 100;
    /**
     * Border Thickness.
     */
    private static final int B = 7;
    /** Arcade piece color. */
    private static final Color ARCADE_PIECE = new Color(139, 149, 109);
    /** Arcade piece color. */
    private static final Color ARCADE_COLOR = new  Color(196, 207, 161);
    /** Stroke. */
    private static final int STROKE = 3;
    /** Storing the board. */
    private final Board myBoard;

    /** Frame for mainGUI. */
    private final MainGUI myFrame;

    /** Storing graphics. */
    private Graphics2D myGraphics;

    /** Storing timer. */
    private Timer myTimer;

    /** Storing tetris piece x. */
    private int myX; 

    /** Storing tetris piece y. */
    private int myY;

    /** Arraylist of Block[] used to create shapes.*/
    private List<Block[]> myBlockList;
    /** New game button. */
    private JMenuItem myNewGame;
    /** End Game button. */
    private JMenuItem myEndGame;
    /** Label for game over. */
    private final JLabel myGameOverLabel;
    /** Checks if paused. */
    private boolean myPause;
    /** Checks if theme color is true. */
    private boolean myThemeColor;
    /** Checks if game is over. */
    private boolean myGameOver;
    /**Checks if seizure theme is chosen. */
    private boolean mySeizureTheme;
    /** Creates random colors. */
    private Color myRandomColors;
    /** Clip for arcade theme.*/
    private Clip myArcadeClip;
    private Clip myDarkClip;
    private Clip mySeizureClip;


    /**
     * Constructor, initializes frame. 
     * Creates new Board, calls private methods.
     * 
     * @param theFrame to add to 
     * @param theBoard the main board
     */
    public BoardGUI(final MainGUI theFrame, final Board theBoard) {
        super();
        this.myFrame = theFrame;
        myBoard = theBoard;
        myBlockList = new ArrayList<Block[]>();
        myX = -PANEL_SIZE;
        myY = -PANEL_SIZE;
        myGameOverLabel = new JLabel("Game Over!");
        myGameOverLabel.setFont(theFrame.createCustomFont());
        myPause = false;
        setup();
    }
    
    public void setClips(Clip dark, Clip arcade, Clip seizure) {
        this.myArcadeClip = arcade;
        this.myDarkClip = dark;
        this.mySeizureClip = seizure;
        
    }
    /** Checkes if new game is chosen. 
     * 
     * @param theNewGame new game button
     * @param theEndGame end game button
     */
    public void newGame(final JMenuItem theNewGame, final JMenuItem theEndGame) {
        myNewGame = theNewGame;
        myEndGame = theEndGame;
        myBoard.newGame();
        theNewGame.setEnabled(false);
        theEndGame.setEnabled(true);
        myGameOver = false;

        timer();
    }
    /** Sets up this panel. */
    private void setup() {
        setLayout(new BorderLayout());
        this.setBackground(ARCADE_COLOR);
        this.setBorder(BorderFactory.createMatteBorder(B, B, B, B, Color.DARK_GRAY));
        setPreferredSize(new Dimension(myBoard.getWidth() * BLOCK_WIDTH,
                                       myBoard.getHeight() * BLOCK_WIDTH));

        myFrame.pack();
    }
    /** Checks if game ended. 
     * 
     * @return a boolean 
     */
    public boolean endGame() {
        myTimer.stop();
        JOptionPane.showMessageDialog(myFrame, myGameOverLabel);
        myGameOver = true;
//        if(this.myArcadeClip.isRunning()) {
//            myArcadeClip.stop();
//        } else if(this.mySeizureClip.isRunning()) {
//            this.mySeizureClip.stop();
//        } else if(this.myDarkClip.isRunning()) {
//            this.myDarkClip.stop();
//        }

        return true;
        

    }
    /**
     * Sets up the panel for this class.
     */
    public void arcadeTheme() {
       
        this.setBackground(ARCADE_COLOR);
        this.setBorder(BorderFactory.createMatteBorder(B, B, B, B, Color.DARK_GRAY));
        setPreferredSize(new Dimension(myBoard.getWidth() * BLOCK_WIDTH,
                                       myBoard.getHeight() * BLOCK_WIDTH));
        myThemeColor = false;
        mySeizureTheme = false;

    }
    /** Checks if dark theme is chosen
     * Creates the theme.
     */
    public void darkTheme() {
        this.setBackground(Color.BLACK);
        this.setBorder(BorderFactory.createMatteBorder(B, B, B, B, Color.BLUE));
        setPreferredSize(new Dimension(myBoard.getWidth() * BLOCK_WIDTH,
                                       myBoard.getHeight() * BLOCK_WIDTH));
        myThemeColor = true;
        mySeizureTheme = false;
    }
    /**
     * For seizure theme. Sets background.
     */
    public void remixTheme() {
        randColors();
        this.setBorder(BorderFactory.createMatteBorder(B, B, B, B, Color.BLUE));  
        this.setBackground(myRandomColors);
        repaint();
        mySeizureTheme = true;
        
    }
    /**
     * For Observable. Updates the observable to what 
     * the new value is. Ends game, helps create tetris shape.
     */
    @Override
    public void update(final Observable theObservable, final Object theArg) {
        if (theObservable instanceof Board) {

            if (theArg instanceof ArrayList) { 
                myBlockList = (ArrayList<Block[]>) theArg;
                repaint();

            }
            if (theArg instanceof Boolean) {      
                final boolean gameOver = (Boolean) theArg;
                if (gameOver) {
                    endGame();
                    myNewGame.setEnabled(true);
                    myEndGame.setEnabled(false);
  
                }


            }

        }


    }
    /**
     * Stops timer.
     */
    public void stop() {
        myPause = true; 
        myTimer.stop();
        
    }
    /**
     * Starts timer.
     */
    public void start() {
        myPause = false;
        myTimer.start();
        
    }

    /**
     * Creates timer, adds action to it. 
     */
    private void timer() {
        myTimer = new Timer(ANIMATION_SPEED, new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                myBoard.step();
                System.out.println(myBoard.toString());

            }

        });
        start();


    }
    /**
     * Draws the shape of each tetris piece. 
     * @param theGraphics graphics
     */
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        myGraphics = (Graphics2D) theGraphics;
        myGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
        drawSquares();
        if (mySeizureTheme) {
            remixTheme();
        }

    }
    /** Random color generator. */
    public void randColors() {
        final Random rand = new Random();
        final int r = rand.nextInt(255);
        final int g = rand.nextInt(255);
        final int b = rand.nextInt(255);
        myRandomColors = new Color(r, g, b);
    }
    /**
     * Creates the shape of each tetris piece.
     */
    private void drawSquares() {
        
        
        for (int i = myBlockList.size() - 1; i >= 0; i--) {
            myY = (BLOCK_WIDTH - i - 1) * BLOCK_WIDTH;

            final Block[] list =   myBlockList.get(i); 
            for (int j = 0; j <= list.length - 1; j++) {
                myX = j * BLOCK_WIDTH;
                if (list[j] != null && !myThemeColor) { 
                   // randColors();
                    myGraphics.setPaint(ARCADE_PIECE);
                    myGraphics.fillRect(myX  , myY, BLOCK_WIDTH, BLOCK_WIDTH); 
                    myGraphics.setPaint(Color.DARK_GRAY);
                    myGraphics.setStroke(new BasicStroke(STROKE));
                    myGraphics.drawRect(myX  , myY, BLOCK_WIDTH, BLOCK_WIDTH);
                }
                if (list[j] != null && myThemeColor) {
                    myGraphics.setPaint(Color.BLUE);
                    myGraphics.drawRect(myX  , myY, BLOCK_WIDTH, BLOCK_WIDTH);
                    myGraphics.setPaint(Color.BLACK);
                    myGraphics.fillRect(myX  , myY, BLOCK_WIDTH, BLOCK_WIDTH);
                }
                if (list[j] != null && mySeizureTheme) {
                    
                    myGraphics.setPaint(myRandomColors.darker());
                    myGraphics.fillRect(myX  , myY, BLOCK_WIDTH, BLOCK_WIDTH); 
                    myGraphics.setPaint(myRandomColors.brighter());
                    myGraphics.setStroke(new BasicStroke(STROKE));
                    myGraphics.drawRect(myX  , myY, BLOCK_WIDTH, BLOCK_WIDTH);
                    repaint();
                    
                    
                }
            }
        }

    }
    /**
     * Returns timer.
     * @return myTimer
     */
    public Timer getTime() {
        return myTimer;
    }
    @Override
    public boolean isFocusTraversable() {
        return true;
    }
    /**
     * Checks to see what key was pressed, 
     * calls that action.
     * @param theE the event
     */
    public void keyPressed(final KeyEvent theE) {

        final int key = theE.getKeyCode();
        if (myPause || !myGameOver) {
            if (key == KeyEvent.VK_LEFT) {
                myBoard.left();
            } else if (key == KeyEvent.VK_RIGHT) {
                myBoard.right();
            } else if (key == KeyEvent.VK_X) {
                
                myBoard.rotateCW();
            } else if (key == KeyEvent.VK_Z) {
                myBoard.rotateCCW();
            } else if (key == KeyEvent.VK_DOWN) {
                myBoard.step();
            } else if (key == KeyEvent.VK_SPACE) {
                myBoard.drop();
            }      
            
        }

        if (key == KeyEvent.VK_P) {            
            if (myPause) {
                this.start();
            } else {
                this.stop();
            }
            myPause = !myPause;


        }


    }








}
