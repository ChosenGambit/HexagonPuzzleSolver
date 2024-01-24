package com.chosengambit.bugcards.gfx;

import com.chosengambit.bugcards.Card;
import com.chosengambit.bugcards.Tile;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author ChosenGambit
 */
public class CardManager extends JPanel {
    
    private CardFX c;
    private CardFX[] cardsFX = new CardFX[19];
    private List<Card> bugCards;    
    private CardScan[] cards = new CardScan[19];
    private CardScan[] unplaced = new CardScan[19];
    
    /**
     * Instantiate Panel
     */
    public CardManager() {
 
    }   
    
    public void init() {
               
        // remove layout
        setLayout(null);
        
        for (int i = 0; i < 19; i++) {
            unplaced[i] = new CardScan(i+1);
            cards[i] = new CardScan(i+1);
        }                
        revalidate();
        repaint();
    }
   
     /**
     * this.cards get drawn
     * @param tiles 
     */
    public void setBoardInformation(List<Tile> tiles) {              
        
        // translate tile grid info to grapx info
        for (int i = 0; i < tiles.size(); i++) {
            this.cards[i] = null;
            if (tiles.get(i).getCard() != null) {
                int cardNr = tiles.get(i).getCard().getCardnr();
                int rotation = tiles.get(i).getCard().getRotation();
                int tileNr = tiles.get(i).getTileNumber()-1;
                
                this.cards[i] = unplaced[cardNr-1];
                this.cards[i].setLocation(tileNr);
                this.cards[i].setRotation(rotation); 
                //System.out.println("Rotation: "+rotation);
            }
        }
        
        repaint();
    }
    
    public void printCards() {
        for (int i = 0; i < this.cards.length; i++) {
            if (this.cards[i] != null) {
                System.out.println("Card# "+i+ " ok ");
            }
            else {
                System.out.println("Card# "+i+" is null");
            }
        }
    }
    
    /**
     * 
     * @param g 
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        removeAll();
        updateUI();
        
        try {
            for (int i = 0; i < cards.length; i++) {
              if (cards[i] != null && cards[i].getImage() != null)
                  g2d.drawImage(cards[i].getImage(), cards[i].getTransform(), null);
            }  
        }
        catch(Exception e) {
            
        }
    }
    
    public void p(int var) {
        System.out.println(""+var);
    }
}
