/*
 * TCSS305 Tetris part a
 * winter 2016
 */
package view;

import java.awt.EventQueue;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * Runs the application.
 * @author Shema Rezanejad
 * @version 1
 *
 */
public final class RunTetris {
    /**
     * 
     */
    private RunTetris() {
        
    }
    /**
     * Main driver.
     * @param theArgs the args
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainGUI main;
                try {
                    main = new MainGUI();
                    try {
                        main.start();
                    }
                    catch (UnsupportedAudioFileException | LineUnavailableException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                catch (IOException | UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });

    }

}
