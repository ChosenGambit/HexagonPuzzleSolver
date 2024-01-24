package com.chosengambit.bugcards.gfx;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import javax.swing.JComponent;

/**
 *
 * @author ChosenGambit
 */
public class CardFX extends JComponent {
    
    private int x,y,size,fxsize, rotation = 0;
    private Side[] sides = new Side[6];
    
    /**
     * set x and y coordinates
     * @param x
     * @param y 
     */
    private CardFX(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.fxsize = size / 5;
        setBounds(0, 0, size*2, size*2);
        setOpaque(false);
        setVisible(true);
    }
    
    public CardFX() {
        this(0,0,100);
    }
    
    
    /**
     * Add Graphic information to a side of this card
     * @param side
     * @param sideNumber 
     */
    public void addSide(Side side, int sideNumber) {
        sides[sideNumber] = side;        
    }
    
    /**
     * Print sides
     */
    public void print() {
        for(Side side : sides) {
            side.print();
        }
    }
    
    /**
     * Draw the card
     * @param g 
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        drawHexagon(g);
        
        // drag 'bug' image, where square is back, triangle is front
        Graphics2D g2d = (Graphics2D) g;
               
        for (int i = 0; i < sides.length; i++) {
            switch(sides[i].getType()) {
                case com.chosengambit.bugcards.Card.Picture.BIG_RED_BLACK_BUG: g2d.setColor(Color.RED);  break;
                case com.chosengambit.bugcards.Card.Picture.BLUE_BUG:g2d.setColor(Color.BLUE); break;
                case com.chosengambit.bugcards.Card.Picture.PURPLE:g2d.setColor(Color.PINK); break;
                case com.chosengambit.bugcards.Card.Picture.RED_BEETLE_SLIM:g2d.setColor(Color.ORANGE); break;
                case com.chosengambit.bugcards.Card.Picture.SLIM_YELLOW_BEETLE:g2d.setColor(Color.BLACK); break;
                case com.chosengambit.bugcards.Card.Picture.YELLOW_LADYBUG:g2d.setColor(Color.YELLOW); break;
            }
            
            switch(sides[i].getBack()) {
                case com.chosengambit.bugcards.Card.Picture._BACK:
                    //AffineTransform atf = g2d.getTransform();
                    //g2d.rotate(Math.toRadians(45));
                    // switch over sides of card to draw appropriate image
                    switch(i) {
                        case 0: g2d.fillRect(x + (size-size/3), y-fxsize, fxsize, fxsize); break;
                        case 1: break;
                        case 2: break;
                        case 3: break;
                        case 4: break;
                        case 5: break;
                    }
                    
                    //g2d.setTransform(atf);                  
                    
                    break;
                case com.chosengambit.bugcards.Card.Picture._FRONT: 
                    switch(i) {
                        //case 0: g2d.fillOval(x + (size-size/4), y, fxsize, fxsize); break;
                        case 1: break;
                        case 2: break;
                        case 3: break;
                        case 4: break;
                        case 5: break;
                    }
                    
                    break;
                        
            }
        }
            

    }
    
    /**
     * Draw hexagon outlines
     * @param g 
     */
    public void drawHexagon(Graphics g) {

        // Graphics g2d
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3.0f));
        
        AffineTransform atf = g2d.getTransform();
        g2d.rotate(Math.toRadians(30));
        
        GeneralPath hexagon = new GeneralPath();       

        hexagon.moveTo(
            (int) (size + size/3 * Math.cos(0 * 2 * Math.PI / 6)), 
            (int) (size + size/3 * Math.sin(0 * 2 * Math.PI / 6))
        );
        
        // create path
        for (int i = 0; i < 6; i++) {
            hexagon.lineTo(
                (int) (size + size/3 * Math.cos(i * 2 * Math.PI / 6)), 
                (int) (size + size/3 * Math.sin(i * 2 * Math.PI / 6))
            );
        }
        hexagon.closePath();
        
        
        g2d.setColor(Color.BLACK);
        g2d.draw(hexagon);
    }

    /**
     * CardFX sides, information about the image on the card
     */
    public static class Side {
        
        private int type; // bug type
        private int back; // 0 = front, 1 = back
        
        public Side(int type, int back) {
            this.type = type;
            this.back = back;
        }
        
        public void print() {
            System.out.println("type: "+type+", back: "+back);
        }

        public int getType() {
            return type;
        }

        public int getBack() {
            return back;
        }
        
        
        
    }
    
}
