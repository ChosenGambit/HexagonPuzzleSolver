package com.chosengambit.bugcards;

import com.chosengambit.bugcards.gfx.CardManager;
import com.chosengambit.bugcards.gfx.GraphicsMain;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingWorker;

/**
 * @author ChosenGambit
 */
public class Main {
    
    private JMenu menu = new JMenu("Start");
    private static GraphicsMain gfx;
    private static CardManager cm;
    private static JFrame frame;

    public static void main(String[] args) {          
        
        gfx = new GraphicsMain();
        frame = gfx.createFrame("Tile Puzzle - Bug Cards Window");
        cm = gfx.createJPanel(frame);
        
        addMenu();       
    }
    
    public static void startSolving() {        
        Board board = new Board();  
        BruteForce bf = new BruteForce(board, cm);           
    }
        
    /**
     * Add menu
     */
    private static void addMenu() {
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Start");
        menu.add(addMenuItem("Very Slow", 500, 500, 500));
        menu.add(addMenuItem("Slow", (long)100, (long)100, (long)80));
        menu.add(addMenuItem("Normal",  (long)20, (long)10, (long)5));
        menu.add(addMenuItem("Fast",  (long)1, (long)1, (long)0.9));
        menu.add(addMenuItem("Maximum",  (long)0, (long)0, (long)0));
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
    }
    
    private static final JMenuItem addMenuItem(String label, long stepBack, long stepPlace, long stepRotate) {
        
        final long fstepBack = stepBack;
        final long fstepPlace = stepPlace;
        final long fstepRotate = stepRotate;
        
        JMenuItem menuItem = new JMenuItem(label);
        
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                //frame.remove(cm);
                BruteForce.GRAPHIC_STEP_BACK = fstepBack;
                BruteForce.GRAPHIC_STEP_PLACE = fstepPlace;
                BruteForce.GRAPHIC_STEP_ROTATE = fstepRotate;                
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        startSolving();
                        return null;
                    }
                };
                worker.execute();
            }
        }); 
        
        return menuItem;
    }
    
    public static void p(String str) {
        System.out.println(str);
    }
    
}
