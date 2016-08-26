/*
 * TCSS 305 W 16
 * Tetris B
 */
package view;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.sun.glass.events.KeyEvent;
/**
 * Creates menu bar for program.
 * @author Shema Rezanejad
 * @version 1
 *
 */

public class TetrisMenu extends JMenuBar {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 6523545150132007076L;
    /**
     * Frame.
     */
    private final MainGUI myFrame;
    /** The main font. */
    private final Font myFont;
    /** the game mode menu. */
    private final JMenu myMode;
    /** Stores BoardGUI .*/
    private final BoardGUI myBoardGUI;
    /** Stores NextPiece. */
    private final NextPiece myNextPiece;
    /** Stores file JMENU.*/
    private JMenu myFile;
    /** Stores score panel. */
    private final Score myScorePanel;
    /** Stores music. */
    public Clip myArcadeClip;
    /** Stores music. */
    public Clip myDarkClip;
    /** Stores music. */
    public Clip mySeizureClip;
    /** Stores Button. */
    private JCheckBox myArcadeButton;
    /** Stores Button. */
    private JCheckBox myDarkButton;
    /** Stores Button. */
    private JCheckBox mySeizureButton;
    /** Stores Button. */
    private JMenu mySound;

    /**
     * Sets all fields. 
     * @param theFrame JFrame
     * @param theBoardGUI board on frame
     * @param theNextPiece nextpiece panel
     * @param theScorePanel score panel
     */
    public TetrisMenu(final MainGUI theFrame, final BoardGUI theBoardGUI, 
                      final NextPiece theNextPiece, final Score theScorePanel) {
        super();
        this.myFrame = theFrame;
        this.myFont = theFrame.createCustomFont();
        myMode = new JMenu("Mode");
        this.myBoardGUI = theBoardGUI;
        this.myNextPiece = theNextPiece;
        this.myScorePanel = theScorePanel;
        createMenuBar();

    }
    /** Creates the menu bar. 
     * 
     * @return a menuBar
     */
    private JMenuBar createMenuBar() {
        myArcadeButton = new JCheckBox("Arcade");
        myDarkButton = new JCheckBox("Dark");
        mySeizureButton = new JCheckBox("Seizure");

        myFile = new JMenu("File");
        myFile.setMnemonic(KeyEvent.VK_F);
        final JMenu options = new JMenu("Game");
        options.setMnemonic(KeyEvent.VK_G);


        final JMenu help = new JMenu("Help");
        final JMenuItem about = new JMenuItem("About...");
        help.setMnemonic(KeyEvent.VK_H);
        options.add(myMode);
        myMode.setEnabled(false);

        mySound = new JMenu("Sound");
        mySound.setMnemonic(KeyEvent.VK_S);
        options.addSeparator();
        options.add(mySound);


        myMode.setFont(myFont);
        myMode.setMnemonic(KeyEvent.VK_T);
        mySound.setFont(myFont);
        myFile.setFont(myFont);
        options.setFont(myFont);
        help.setFont(myFont);
        help.add(about);
        about.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                JOptionPane.showMessageDialog(null, "Score calculation"
                                + "+4 to every frozen piece"
                                + "Level(n): 1Line = 40 * n, 2Line = 100 * n,"
                                + "3Line = 300 * n,"
                                + "4Line = 1200 * n");

            }

        });

        onOff();
        mySound.setEnabled(false);
        add(myFile);
        add(options);
        add(help);
        fileMenu();
        createThemes();

        setVisible(true);

        return this;


    }
    /**
     * Turns sound on/off.
     */
    private void onOff() {
        final ButtonGroup soundGroup = new ButtonGroup();
        final JCheckBox on = new JCheckBox("on");
        on.setMnemonic(KeyEvent.VK_O);
        final JCheckBox off = new JCheckBox("off");
        off.setMnemonic(KeyEvent.VK_F);
        mySound.add(on);
        mySound.add(off);
        soundGroup.add(on);
        on.setSelected(true);
        soundGroup.add(off);
        on.setFont(myFont);
        off.setFont(myFont);

        off.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {

                if (myArcadeButton.isEnabled() && myArcadeClip.isRunning()) {
                    myArcadeClip.stop();
                } else if (myDarkButton.isEnabled() && myDarkClip.isRunning()) {
                    myDarkClip.stop();
                } else if (mySeizureButton.isEnabled() && mySeizureClip.isRunning()) {
                    mySeizureClip.stop();
                }

            }

        });
        on.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                if (myArcadeButton.isSelected()) {
                    myArcadeClip.start();
                } else if (myDarkButton.isSelected()) {
                    myDarkClip.start();
                } else if (mySeizureButton.isSelected()) {
                    mySeizureClip.start();
                }

            }

        });
    }
    /** Anonymous class for the themes. Calls all correct methods
     * adds all buttons to groups. 
     */
    private void createThemes() {
        final ButtonGroup group = new ButtonGroup();
        myArcadeButton.setSelected(true);

        myMode.setToolTipText("Pick your Game Mode");
        myMode.add(myArcadeButton);
        myMode.add(myDarkButton);
        myMode.add(mySeizureButton);
        group.add(myArcadeButton);
        group.add(myDarkButton);
        group.add(mySeizureButton);

        myDarkButton.setFont(myFont);
        myDarkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theE) {
                try {
                        darkEdge();
                     
                    if (myArcadeClip.isActive()) {
                        myArcadeClip.stop();
                    }
                    else if (mySeizureClip.isRunning()) {
                        mySeizureClip.stop();
                    }
                } catch (final UnsupportedAudioFileException | IOException 
                                | LineUnavailableException e1) {
                    e1.printStackTrace();
                }   
            }
        });
        myArcadeButton.setFont(myFont);
        myArcadeButton.setToolTipText("Classic Tetris");
        myDarkButton.setToolTipText("Do you dare?");
        myArcadeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theE) {
                try {
                        arcadeEdge();
                    if (myDarkClip.isRunning()) {
                        myDarkClip.stop();
                    }
                    if (mySeizureClip.isRunning()) {
                        mySeizureClip.stop();
                    }
                } catch (final IOException | UnsupportedAudioFileException 
                                | LineUnavailableException e1) {
                    e1.printStackTrace();
                } 
            }

        });
        mySeizureButton.setFont(myFont);
        mySeizureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                try {
                        edgeCase();   
                    
                    if (myDarkClip.isRunning()) {
                        myDarkClip.stop();
                    }
                    if (myArcadeClip.isRunning()) {
                        myArcadeClip.stop();
                    }
                } catch (final UnsupportedAudioFileException | IOException 
                                | LineUnavailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * Helper method for anon class.
     * @throws UnsupportedAudioFileException audio
     * @throws IOException file
     * @throws LineUnavailableException line
     */
    private void edgeCase() throws UnsupportedAudioFileException,
    IOException, LineUnavailableException {
        seizureSound();
        myFrame.seizureTheme();
        myScorePanel.darkMode();
        myBoardGUI.darkTheme();
        myNextPiece.darkTheme();
        myBoardGUI.remixTheme();

    }
    /**
     * Helper method for anon class.
     * @throws UnsupportedAudioFileException audio
     * @throws IOException file
     * @throws LineUnavailableException line
     */
    private void darkEdge() throws IOException, 
    UnsupportedAudioFileException, LineUnavailableException {
        myFrame.darkTheme();
        myScorePanel.darkMode();
        myBoardGUI.darkTheme();
        myNextPiece.darkTheme();
        remixSound();

    }
    /**
     * Helper method for anon class.
     * @throws UnsupportedAudioFileException audio
     * @throws IOException file
     * @throws LineUnavailableException line
     */
    private void arcadeEdge() throws IOException, 
    UnsupportedAudioFileException, LineUnavailableException {
        arcadeSound();
        myFrame.arcadeTheme();
        myScorePanel.levelPanel();
        myScorePanel.linesPanel();
        myScorePanel.score();
        myNextPiece.nextPanel();
        myBoardGUI.arcadeTheme();

    }
    /**
     * Creates file menu.
     */
    private void fileMenu() {
        final JMenuItem exit = new JMenuItem("Exit");
        final JMenuItem newGame = new JMenuItem("New Game");
        final JMenuItem endGame = new JMenuItem("End Game");
        myFile.add(newGame);
        myFile.add(endGame);
        myFile.addSeparator();
        myFile.add(exit);

        exit.setFont(myFont);
        newGame.setFont(myFont);
        newGame.setMnemonic(KeyEvent.VK_N);
        endGame.setFont(myFont);  
        endGame.setMnemonic(KeyEvent.VK_E);
        exit.setMnemonic(KeyEvent.VK_X);

        endGame.setEnabled(false);

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                myBoardGUI.newGame(newGame, endGame); 
                myScorePanel.reset();
                myMode.setEnabled(true);
                mySound.setEnabled(true);
                try {
                    if (myArcadeButton.isSelected()) {
                        arcadeSound(); 
                    } else if (myDarkButton.isSelected()) {
                        remixSound();
                    } else if (mySeizureButton.isSelected()) {
                        seizureSound();
                    } 
                } catch (final UnsupportedAudioFileException 
                                | IOException | LineUnavailableException e) {
                    e.printStackTrace();
                }
            }
        });
        endGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                if (myArcadeButton.isSelected() && myBoardGUI.endGame()) {
                    myArcadeClip.stop();
                } else if (myDarkButton.isSelected() && myBoardGUI.endGame()) {
                    myDarkClip.stop();

                } else if (mySeizureButton.isSelected() && myBoardGUI.endGame()) {
                    mySeizureClip.stop();
                }    
                myMode.setEnabled(false);
                newGame.setEnabled(true);
                endGame.setEnabled(false);


            }

        });
        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                myFrame.dispose();

            }

        });
    }
    /**
     * Creates sound.
     * @throws UnsupportedAudioFileException audio
     * @throws IOException file
     * @throws LineUnavailableException line
     */
    public void remixSound() throws UnsupportedAudioFileException, 
    IOException, LineUnavailableException {
        final File soundFile = new File("Audio/remixTheme.wav");
      //  AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource("remixTheme.wav"));
        final AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
      //  final AudioInputStream audioIn = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource("remixTheme.wav"));
        myDarkClip = AudioSystem.getClip();
        myDarkClip.open(audioIn);
        myDarkClip.loop(Clip.LOOP_CONTINUOUSLY);

    }
    /**
     * Creates sound.
     * @throws UnsupportedAudioFileException audio
     * @throws IOException file
     * @throws LineUnavailableException line
     */
    public void arcadeSound() throws UnsupportedAudioFileException, 
    IOException, LineUnavailableException {
        final File soundFile = new File("Audio/arcade.wav");
      //  AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource("arcade.wav"));
      //  final AudioInputStream audioIn = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource("arcade.wav"));

        final AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
        myArcadeClip = AudioSystem.getClip();
        myArcadeClip.open(audioIn);
        myArcadeClip.loop(Clip.LOOP_CONTINUOUSLY);

    }
    /**
     * Creates sound.
     * @throws UnsupportedAudioFileException audio
     * @throws IOException file
     * @throws LineUnavailableException line
     */
    public void seizureSound() throws UnsupportedAudioFileException, 
    IOException, LineUnavailableException {
        final File soundFile = new File("Audio/seizure.wav");
       // final AudioInputStream audioIn = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource("seizure.wav"));

        final AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
        mySeizureClip = AudioSystem.getClip();
        mySeizureClip.open(audioIn);
        mySeizureClip.loop(Clip.LOOP_CONTINUOUSLY);

    }


}
