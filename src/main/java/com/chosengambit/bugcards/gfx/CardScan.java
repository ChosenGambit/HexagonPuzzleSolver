package com.chosengambit.bugcards.gfx;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author ChosenGambit
 */
public class CardScan {
    
    private BufferedImage image;
    private int rotation = 0;
    private int location = 0;
    private float scale = 0.2f;
    private int cardNumber = 0;
    
    public CardScan(int cardNumber) {
        this.cardNumber = cardNumber;   
        
        try {            
            image = ImageIO.read(new File("src/main/resources/card"+this.cardNumber+".png"));            
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }    
    
    
    /**
     * Get loaded image
     * @return 
     */
    public BufferedImage getImage() {
        return this.image;
    }

    // get Rotation
    public int getRotation() {
        return rotation * 60;
    }

    public Point getLocation() {
        
        if (image == null) return null;
        
        int width = (int) (image.getWidth() * this.scale);
        int height = (int) (image.getHeight() * this.scale);
        int offset_x = 200;
        int offset_y = 50;
        int row2 = offset_y + height - height / 4;
        int row3 = row2 + height - height / 4;
        int row4 = row3 + height - height / 4;
        int row5 = row4 + height - height / 4;
        
        Point point = null;
        switch (this.location) {
            // row 1
            case 0: point = new Point(offset_x, offset_y); break; 
            case 1: point = new Point(offset_x + width, offset_y); break;
            case 2: point = new Point(offset_x + width*2, offset_y); break;
            // row 2
            case 3: point = new Point(offset_x - width/2, row2 ); break;
            case 4: point = new Point(offset_x - width/2 + width, row2); break;
            case 5: point = new Point(offset_x - width/2 + width * 2, row2); break;
            case 6: point = new Point(offset_x - width/2 + width * 3, row2); break;
            // row 3 (middle row)
            case 7: point = new Point(offset_x - width, row3); break;
            case 8: point = new Point(offset_x, row3); break;
            case 9: point = new Point(offset_x + width, row3); break;
            case 10: point = new Point(offset_x + width*2, row3); break;
            case 11: point = new Point(offset_x + width*3, row3); break;
            // row 4
            case 12: point = new Point(offset_x - width/2, row4); break;
            case 13: point = new Point(offset_x - width/2 + width, row4); break;
            case 14: point = new Point(offset_x - width/2 + width * 2, row4); break;
            case 15: point = new Point(offset_x - width/2 + width * 3, row4); break;
            // row 5
            case 16: point = new Point(offset_x, row5); break;
            case 17: point = new Point(offset_x + width, row5); break;
            case 18: point = new Point(offset_x + width *2, row5); break;                                 
        }
        return point;
    }
    
    public AffineTransform getTransform() {
        AffineTransform at = new AffineTransform();
        at.translate(getLocation().x, getLocation().y);
        at.scale(getScale(), getScale());
        at.rotate(Math.toRadians(getRotation()), getImage().getWidth()/2, getImage().getHeight()/2);
        return at;
    }

    
    public float getScale() {
        return scale;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    
}
