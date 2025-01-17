/*
 * TCSS305 Tetris part b
 * winter 2016
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.Board;
/**
 * Puts all panels together, creates and shows
 * GUI.
 * @author Shema Rezanejad
 * @version 1
 *
 */
public class MainGUI extends JFrame {
    /**
     * Generated serial version ID.
     */
    private static final long serialVersionUID = -5056050661369885282L;
    /**
     * Image for arcade.
     */
    private static final String ARCADE_THEME = "images/tetris_tile1.png";
    /**
     * Image for dark theme. 
     */
    private static final String DARK_THEME = "images/dark_theme.jpg";
    /**
     * Font location.
     */
    private static final String FONT = "Fonts\\ARCADECLASSIC.ttf";
    /**
     * Color for the arcade.
     */
    private static final Color ARCADE_COLOR = new Color(196, 207, 161);
    /** Frame size. */
    private static final Dimension SIZE = new Dimension(450, 475);
    /** East Panel size. */
    private static final Dimension EAST = new Dimension(150, 400);
    /** Stores a BoardGUI. */
    private final BoardGUI myBoardGUI;
    /** Stores east panel. */
    private final JPanel myEastPanel;
    /** The stored image.
     */
    private BufferedImage myImage;
    /** The main board. */
    private final Board myMainBoard;
    /** The custom font. */
    private Font myCustomFont;
    /** Storing the nextPanel. */
    private final NextPiece myNextPanel;
    /** Storing the score panel. */
    private final Score myScorePanel;
    /** Storing the menu. */
    private final TetrisMenu myMenu;
    /** Storing the control panel. */
    private JPanel myControlPanel;
    /** Storing a label . */
    private JLabel myKeyLabel;
    /** Storing the file. */
    private final ImagePanel myFile;
    /** Random colors. */
    private Color myRandomColors;
    
    /**
     * Constructor, initializes.
     * @throws IOException e
     * @throws UnsupportedAudioFileException 
     */
    public MainGUI() throws IOException, UnsupportedAudioFileException {
        super();
        myEastPanel = new JPanel(new FlowLayout());
       
        myMainBoard = new Board();
        myBoardGUI = new BoardGUI(this, myMainBoard);
        myScorePanel = new Score(myBoardGUI);
        myNextPanel = new NextPiece(myScorePanel);
        myMenu = new TetrisMenu(this, myBoardGUI, myNextPanel, myScorePanel);
        
        myBoardGUI.setClips(myMenu.myDarkClip, myMenu.myArcadeClip, myMenu.mySeizureClip);
        
        myImage = ImageIO.read(new File(ARCADE_THEME));
        myFile = new ImagePanel(myImage, true);   
        
    }

    /**
     * Starts the GUI. Puts all the components
     * together.
     * @throws IOException 
     * @throws LineUnavailableException 
     * @throws UnsupportedAudioFileException 
     */
    public void start() throws IOException, 
    UnsupportedAudioFileException, LineUnavailableException {
        setTitle("Tetris");
        setJMenuBar(myMenu);
        this.setContentPane(myFile);
        this.setPreferredSize(SIZE);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        myMainBoard.addObserver(myNextPanel);
        myMainBoard.addObserver(myBoardGUI);
        myMainBoard.addObserver(myScorePanel);

        myBoardGUI.setFocusTraversalKeysEnabled(true);
        myBoardGUI.requestFocus();
        myBoardGUI.addKeyListener(new KeyPress());

        
        add(myBoardGUI, BorderLayout.CENTER);
        
        setUpEastPanel();
        add(myEastPanel, BorderLayout.EAST);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
    
    /** Creates and shows the east panel. */
    private void setUpEastPanel() {
        myEastPanel.setPreferredSize(EAST);
        myEastPanel.add(myScorePanel);
        myEastPanel.add(myScorePanel.levelPanel());
        myEastPanel.add(myScorePanel.linesPanel());
        keyLabel();
        myEastPanel.add(myNextPanel);
        myEastPanel.setBackground(Color.DARK_GRAY);
        
    }

    /** Label for the game controls. */
    private void keyLabel() {
        final int one = 120;
        myControlPanel = new JPanel();
        myControlPanel.setPreferredSize(new Dimension(one, one));
        myControlPanel.setBackground(ARCADE_COLOR);
        final Border blackline = BorderFactory.createLineBorder(Color.black);
        myControlPanel.setBorder(blackline);
        final StringBuilder controls = new StringBuilder(200);

        controls.append("<html><u>Controls</u> "
                        + "<br>RotateCW- X<br>RotateCCW- Z <br> Left- left<br> Right- right "
                        + "<br>Down- down<br>Drop- space<br>Pause- p</html");

        myKeyLabel = new JLabel(controls.toString(), JLabel.CENTER);

        myKeyLabel.setFont(createCustomFont());
        myKeyLabel.setForeground(Color.darkGray);
        myControlPanel.add(myKeyLabel);

        myEastPanel.add(myControlPanel);


    }
    /** 
     * Changes everything back to default theme.
     * @throws IOException e
     */
    public void arcadeTheme() throws IOException {
        myImage = ImageIO.read(new File(ARCADE_THEME));
        this.setContentPane(new ImagePanel(myImage, true));

        add(myBoardGUI, BorderLayout.CENTER);
        myKeyLabel.setForeground(Color.DARK_GRAY);
        add(myEastPanel, BorderLayout.EAST);
        myEastPanel.setBackground(Color.DARK_GRAY);
        myControlPanel.setBackground(ARCADE_COLOR);
        myKeyLabel.setBackground(ARCADE_COLOR);
        
    }
    /**
     * Changes everything to dark theme.
     * @throws IOException e
     */
    public void darkTheme() throws IOException {
        myImage = ImageIO.read(new File(DARK_THEME));
        this.setContentPane(new ImagePanel(myImage, true));

        add(myBoardGUI, BorderLayout.CENTER);
        add(myEastPanel, BorderLayout.EAST);
        myEastPanel.setBackground(Color.BLACK);
        myControlPanel.setBackground(Color.BLACK);
        myKeyLabel.setForeground(Color.WHITE);
    }
    /**
     *  Creates seizure theme.
     */
    public void seizureTheme() {
        this.setContentPane(new ImagePanel(null, false));
        add(myBoardGUI, BorderLayout.CENTER);
        add(myEastPanel, BorderLayout.EAST);
        myEastPanel.setBackground(Color.BLACK);
        myControlPanel.setBackground(Color.BLACK);
        myKeyLabel.setForeground(Color.WHITE);
    }
    /** 
     * Creates custom font.
     * @return a font
     */
    public Font createCustomFont() {
        Font font = null;
        try {
            //create the font to use. Specify the size!
            font = Font.createFont(Font.TRUETYPE_FONT,
                                         new File(FONT)).deriveFont(13f);
            final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,
                                            new File(FONT)));
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final FontFormatException e) {
            e.printStackTrace();
        }
        return font;
        
        
    }
 
    /**
     * Private class for KeyListeners. If key
     * is pressed certain action occurs.
     * @author Shema Rezanejad
     * @version 1
     *
     */
    private class KeyPress extends KeyAdapter {
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            myBoardGUI.keyPressed(theEvent);

        }


    }



}



