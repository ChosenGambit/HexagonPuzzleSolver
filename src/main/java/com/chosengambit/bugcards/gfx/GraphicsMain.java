package com.chosengambit.bugcards.gfx;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * @author ChosenGambit
 */
public class GraphicsMain {
    
    private JFrame frame;
    private CardManager cManager;
            
    /**
     * Create window with title and return CardManager instance
     * @param title
     * @return 
     */
    public JFrame createFrame(String title) {
        
        // create frame
        frame = new JFrame();    
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         
        
        return frame;
    }
    
    public CardManager createJPanel(JFrame frame) {                
        
        frame.getContentPane().removeAll();
        
        this.cManager = new CardManager();
        this.cManager.setPreferredSize(new Dimension(1280,720));        
        this.cManager.repaint();        
        
        frame.getContentPane().add(cManager);
        frame.pack();
        frame.setVisible(true);
        
        return this.cManager;
    }


         
}
