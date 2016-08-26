package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

    /**
     * 
     */
    private static final long serialVersionUID = -4701904488271187964L;
    private boolean myTheme;
    
    private Image myImage;
    private Color myRandomColors;
    
    public ImagePanel(Image theImage, boolean theTheme) {
        this.myTheme = theTheme;
        this.myImage = theImage;
    }
    public void randColors() {
        final Random rand = new Random();
        // Java 'Color' class takes 3 floats, from 0 to 1.
        final int r = rand.nextInt(255);
        final int g = rand.nextInt(255);
        final int b = rand.nextInt(255);
        myRandomColors = new Color(r, g, b);
        this.setBackground(myRandomColors.brighter());
        myTheme = false;
    }
    @Override
    public void paintComponent(Graphics theGraphics) {
        super.paintComponent(theGraphics);
        Graphics2D g2d = (Graphics2D) theGraphics;
        if(myTheme) {
            g2d.drawImage(myImage, 0, 0, this);
        }
        else{
            randColors();
            repaint();
            
            
        }
 
        
        
    }
}
