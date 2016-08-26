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
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.Board;
import model.Point;
import model.TetrisPiece;
/**
 * Creates next piece panel for the GUI, and
 * draws the next piece on the panel.
 * @author Shema Rezanejad
 * @version 1
 *
 */
public class NextPiece extends JPanel implements Observer {

    /**
     * Generated serial version ID.
     */
    private static final long serialVersionUID = -7943822836795937944L;
    /** Tetris color.*/
    private static final Color ARCADE_COLOR = new Color(139, 149, 109);
    /** Tetris color.*/
    private static final Color ARCADE_COLOR2 = new Color(196, 207, 161);
    /** Block width. */
    private static final int BLOCK_WIDTH = 20;
    /** adjusting y. */
    private static final int ADJUST_Y = 3;
    

    /** Storing tetris piece. */
    private TetrisPiece myTetrisPiece;
    /** Border 1. */
    private final Border myBorder1;
    /** Border2 .*/
    private final Border myBorder2;
    /** Checking if theme color. */
    private boolean myThemeColor;
    /** the score. */
    private final Score myScore;

    /**
     * Constructor. Calls private methods.
     * @param theScore the score
     */
    public NextPiece(final Score theScore) {
        super();
        myBorder1 = BorderFactory.createMatteBorder(5, 5, 5, 5, Color.DARK_GRAY);
        myBorder2 = BorderFactory.createMatteBorder(5, 5, 5, 5, ARCADE_COLOR2);
        this.myScore = theScore;
        setupComponents();


    }
    /**
     * Sets up panel for this class.
     */
    private void setupComponents() {
        add(new JLabel("Next Piece"));
        setPreferredSize(new Dimension(120, 90));
        nextPanel();
        setLayout(new BorderLayout());
    }
    /** Sets up the next shape panel. */
    public void nextPanel() {
        setBorder(BorderFactory.createCompoundBorder(myBorder2, myBorder1));
        this.setBackground(ARCADE_COLOR2);
        this.setBackground(ARCADE_COLOR2);
        myThemeColor = false;
    }
    /** Changes colors for the other mode. */
    public void darkTheme() {
        final Border darkBorder = BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLUE);
        setBorder(BorderFactory.createCompoundBorder(darkBorder, myBorder1));
        this.setBackground(Color.BLACK);
        myThemeColor = true;
    }
    @Override
    public void update(final java.util.Observable theObs, final Object theObject) {
        if (theObs instanceof Board && theObject instanceof TetrisPiece) {
            final int four = 4;
            myTetrisPiece = (TetrisPiece) theObject;
            myScore.setScore(four);
            myScore.scorePieces();
            repaint();

        }

    }
    /**
     * Draws the next piece shape. 
     * @param theGraphics 
     */
    public void paintComponent(final Graphics theGraphics) {

        super.paintComponent(theGraphics);
        Graphics2D g2d = (Graphics2D) theGraphics;
        final int centerX = 24;
        final int centerY = 2;

        if (myTetrisPiece != null) {

            for (final Point p: myTetrisPiece.getPoints()) {
                if (myThemeColor) {
                    g2d.setPaint(Color.BLUE);
                    g2d.fillRect(p.x() * BLOCK_WIDTH + centerX, 
                                        p.y() * BLOCK_WIDTH + centerY,
                                        BLOCK_WIDTH ,  BLOCK_WIDTH);
                    g2d.setStroke(new BasicStroke(ADJUST_Y));
                    g2d.setPaint(Color.BLACK);
                    g2d.drawRect(p.x() * BLOCK_WIDTH + centerX, 
                                        p.y() * BLOCK_WIDTH + centerY,
                                        BLOCK_WIDTH ,  BLOCK_WIDTH);


                } else {
                    g2d.setPaint(Color.DARK_GRAY);

                    g2d.fillRect(p.x() * BLOCK_WIDTH + centerX, 
                                        (p.y() * -1 + ADJUST_Y) * BLOCK_WIDTH + centerY,
                                        BLOCK_WIDTH ,  BLOCK_WIDTH);
                    g2d.setPaint(ARCADE_COLOR);
                    g2d.setStroke(new BasicStroke(ADJUST_Y));
                    g2d.drawRect(p.x() * BLOCK_WIDTH + centerX, 
                                        (p.y() * -1 + ADJUST_Y) * BLOCK_WIDTH + centerY,
                                        BLOCK_WIDTH ,  BLOCK_WIDTH);


                }


            }

        } 



    }



}
