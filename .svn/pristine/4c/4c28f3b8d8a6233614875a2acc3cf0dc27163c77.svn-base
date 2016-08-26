/*
 * TCSS 305 W 16
 * Tetris B
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.Board;
/**
 * Creates the score panel for the east panel.
 * Include score, level, and lines. 
 * @author Shema Rezanejad
 * @version 1
 *
 */
public class Score extends JPanel implements Observer {

    /**
     * 
     */
    private static final long serialVersionUID = -4885469066941943955L;
    /** panel color. */
    private static final Color PANEL_COLOR = new Color(196, 207, 161);
    /** Font location. */
    private static final String FONT = "Fonts\\ARCADECLASSIC.ttf";
    /** Border width. */
    private static final int B = 3;
    /** Border 1. */ 
    private static final Border BORDER1 = BorderFactory.
                    createMatteBorder(B, B, B, B, Color.DARK_GRAY);
    /** Border 2 for compound corder. */
    private static final Border BORDER2 = BorderFactory.
                    createMatteBorder(B, B, B, B, PANEL_COLOR);
    /** Starting point for score. */
    private static final int START = -4;
    /** Panel sizes. */
    private static final Dimension SIZE = new Dimension(120, 50);
    /** Array for lines cleared. */
    private Integer[] myRowsComplete;
    /** Compound border. */
    private final Border myCompoundBorder;
    /** Panel for # lines. */
    private final JPanel myLinesPanel;
    /** Panel for # levels. */
    private final JPanel myLevelPanel;
    /** Label for levels. */
    private JLabel myLevelLabel;
    /** Label for lines. */
    private JLabel myLinesLabel;
    /** Label for score. */
    private JLabel myScoreLabel;
    /** stores gui. */
    private final BoardGUI myBoardGUI;
    /** the level. */
    private int myLevel;
    /** the score. */
    private int myScore;
    /** the individual piece score. */
    private int myPieceScore;
    /** the lines. */
    private int myLines;

    /**
     * Constructor. Initializes fields. 
     * @param theBoard GUI
     */
    public Score(final BoardGUI theBoard) {
        super();
        myBoardGUI = theBoard;
        myCompoundBorder = BorderFactory.createCompoundBorder(BORDER2 , BORDER1);
        myLevel = 1;
        myScore = START;
        myLines = 0;
        myPieceScore = 0;
        myLevelPanel = new JPanel();
        myLinesPanel = new JPanel();

        setUpComponents(); 

    }
    /**
     * Creating the panel for scoring.
     */
    private void setUpComponents() {
        myLevelLabel = new JLabel("Level" + myLevel);
        myLinesLabel = new JLabel("Lines " + myLines);
        myScoreLabel = new JLabel("Score" + 0);
        setPreferredSize(SIZE);
        add(myScoreLabel);
        score();

    }
    /** Creates score panel.*/
    public void score() {

        myScoreLabel.setForeground(Color.DARK_GRAY);
        myScoreLabel.setFont(createCustomFont()); 
        this.setBackground(PANEL_COLOR);
        this.setBorder(myCompoundBorder);

    }
    /**
     * Creates level panel.
     * @return a jpanel
     */
    public JPanel levelPanel() {
        myLevelLabel.setForeground(Color.DARK_GRAY);
        myLevelLabel.setFont(createCustomFont());
        myLevelPanel.setBorder(myCompoundBorder);
        myLevelPanel.add(myLevelLabel);
        myLevelPanel.setPreferredSize(SIZE);
        myLevelPanel.setBackground(PANEL_COLOR);

        return myLevelPanel;

    }
    /**
     * Creates lines panel.
     * @return a jpanel
     */
    public JPanel linesPanel() {

        myLinesPanel.setBorder(myCompoundBorder);
        myLinesPanel.setPreferredSize(SIZE);
        myLinesPanel.setBackground(PANEL_COLOR); 
        myLinesLabel.setForeground(Color.DARK_GRAY);
        myLinesLabel.setFont(createCustomFont());
        myLinesPanel.add(myLinesLabel);


        return myLinesPanel;


    }
    /**
     * Changes theme and colors for this mode.
     */
    public void darkMode() {
        final Border border = BorderFactory.createMatteBorder(B, B, B, B, Color.BLUE);
        final Border compound =  BorderFactory.createCompoundBorder(border, BORDER1);
        myLinesPanel.setBackground(Color.BLACK);
        myLevelPanel.setBackground(Color.BLACK);
        this.setBackground(Color.BLACK);
        myLevelLabel.setForeground(Color.WHITE);
        myLinesLabel.setForeground(Color.WHITE);
        myScoreLabel.setForeground(Color.WHITE);

        myLinesPanel.setBorder(compound);
        myLevelPanel.setBorder(compound);
        this.setBorder(compound);



    }
    @Override
    public void update(final Observable theObs, final Object theObject) {
        if (theObs instanceof Board && theObject instanceof Integer[]) {
            myRowsComplete = (Integer[]) theObject;
            int linesCleared = 0;
            for (int i = 0; i <= myRowsComplete.length; i++) {
                linesCleared++;  
            }
            clearedLines(linesCleared - 1);

        }

    }
    /**
     * resets labels at new game.
     */
    public void reset() {
        myScore = 0;
        myLevel = 1;
        myLines = 0;
        this.myScoreLabel.setText("Score " + myScore);
        this.myLevelLabel.setText("Level " + myLevel);
        this.myLinesLabel.setText("Lines " + myLines);
    }
    /** 
     * Sets score for each piece.
     * @param theScore
     */
    public void setScore(int theScore) {
        this.myPieceScore = theScore;

    }
    /**
     * Scores each individual piece.
     */
    public void scorePieces() {
        myScore += myPieceScore;
        this.myScoreLabel.setText("Score " + myScore);
    }
    /**
     * Clears the lines.
     * @param theLines current line
     */
    private void clearedLines(final int theLines) {
        scoreLines(theLines);
        myLines += theLines;
        myLinesLabel.setText("Lines " + myLines);
        nextLevel();   

    }
    /**
     * Level up.
     */
    private void nextLevel() {
        final int mod = 5;
        final int delayBy = 100;
        final int delay = myBoardGUI.getTime().getDelay();
        if (myLines % mod == 0) {
            myLevel++;
            this.myLevelLabel.setText("Level " + myLevel);
            setDelay(delay - delayBy);
        }
    }
    /**
     * Sets timer delay.
     * @param theDelay amount
     */
    private void setDelay(final int theDelay) {
        myBoardGUI.getTime().setDelay(theDelay);
    }
    /**
     * Scores each line.
     * @param theLine current line
     */
    private void scoreLines(final int theLine) {
        final int three = 3;
        final int four = 4;
        if (myLevel == 1) {
            calcLines(theLine);  
        }
        if (myLevel == 2) {
            calcLines(theLine);
        }
        if (myLevel == three) {
            calcLines(theLine);
        }
        if (myLevel == four) {
            calcLines(theLine);
        }
        this.myScoreLabel.setText("Score " + myScore);
    }
    /**
     * Calculates the line.
     * @param theLine current line
     */
    private void calcLines(final int theLine) {
        final int three = 3;
        final int four = 4;
        final int delayBy = 100;
        final int threeH = 300;
        final int forty = 40;
        final int tweleve = 1200;
        if (theLine == 1) {
            myScore += myLevel * forty;
        }
        if (theLine == 2) {
            myScore += myLevel * delayBy;
        }
        if (theLine == three) {
            myScore += myLevel * threeH;
        }
        if (theLine == four) {
            myScore += myLevel * tweleve;
        }

    }
    /**
     * Creates a custom font.
     * @return Font
     */
    private Font createCustomFont() {
        Font font = null;
        
        try {
            font = Font.createFont(Font.TRUETYPE_FONT,
                                   new File(FONT)).deriveFont(20f);
            final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, 
                                            new File(FONT)));

        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final FontFormatException e) {
            e.printStackTrace();
        }
        return font;



    }


}
