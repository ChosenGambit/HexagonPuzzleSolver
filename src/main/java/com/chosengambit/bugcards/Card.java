package com.chosengambit.bugcards;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * The data that a card holds
 * @author ChosenGambit
 */
public class Card implements Comparable<Card>{

    private int cardnr = -1;
    private int rotation = 0;
    private List<Picture> pictures = new ArrayList<Picture>();
    public static final int MAX_ROTATIONS = 5;
    private BigInteger count = new BigInteger("0"); // count per card on tile

    /**
     * add picture to card a picture is attached to a sidenr
     *
     * sidenr is defined as follows:
     *
     * 5 / \ 0
     * 4 | | 1
     * 3 \ / 2
     * 
     * These numbers do not change
     *
     * @param sidenr
     * @param pic a picture identified by an integer
     * @param picside 0 = front, 1 = back (0 matches with 1)
     */
    public void addPicture(int sidenr, int pic, int picside) {
        this.pictures.add(new Picture(sidenr, pic, picside));
    }

    /**
     * get list with pictures
     *
     * @return
     */
    public List<Picture> getPictures() {
        return this.pictures;
    }
    
    /**
     * returns Picture at side of the card, rotation considered
     * @param cardSide
     * @return 
     */
    public Picture getPictureAtCardSide(int cardSide) {
        for (Picture pic : getPictures()) {
            if (pic.getSide() == cardSide) {
                return pic;
            }
        }
        return null;
    }

    /*
     GETTERS AND SETTERS
     */
    public int getRotation() {
        return rotation;
    }

    public void rotateRight() {
        for (Picture picture : this.pictures) {
            picture.rotateRight();            
        }
        this.rotation++;
        if (this.rotation > 5) {
           this.rotation = 0;
        }
    }
    
    public void rotateRight(int numberOfRotations) {
        for (int r = 0; r < numberOfRotations; r++) {
            rotateRight();
        }
    }

    public void resetRotation() {
        this.rotation = 0;
        for (Picture picture : this.pictures) {
            picture.resetRotation();
        }
    }

    @Override
    public int compareTo(Card otherCard) {
        if (this.cardnr < otherCard.getCardnr()) return -1;
        else if (this.cardnr > otherCard.getCardnr()) return 1;
        return 0;
    }
    
    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        this.count = count;
    }

    public Card(int cardnr) {
        this.cardnr = cardnr;
    }

    public int getCardnr() {
        return this.cardnr;
    }
    
    public void addCount() {
        this.count = this.count.add(new BigInteger("1"));
    }
    
    public void resetCount() {
        count = new BigInteger("0");
    }

    /**
     * Inner class picture represents a picture on a side of the card side can
     * be 0 - 5 pic is defined by an integer picside can be front or back
     * 
     * side is the side of the card (0-5) this picture is on
     * the sides of the card do not change. 
     */
    public class Picture {

        private int startside = -1; // for resetting purpose
        private int side = -1; // side of the card this picture is
        private int pic = -1; 
        private int picFrontBack = -1;

        public static final int PURPLE = 1;
        public static final int YELLOW_LADYBUG = 2;
        public static final int SLIM_YELLOW_BEETLE = 3;
        public static final int BLUE_BUG = 4;
        public static final int RED_BEETLE_SLIM = 5;
        public static final int BIG_RED_BLACK_BUG = 6;

        public static final int _FRONT = 0;
        public static final int _BACK = 1;

        /**
         * Set picture at card location (side)
         * @param side (0-6)
         * @param pic 
         * @param picFrontBack 
         */
        public Picture(int side, int pic, int picFrontBack) {
            this.startside = side;
            this.side = side;
            this.pic = pic;
            this.picFrontBack = picFrontBack;
        }

        public int getSide() {
            return side;
        }

        public void rotateRight() {
            int side = this.getSide() + 1;
            this.setSide(side);
        }

        public void resetRotation() {
            setSide(this.startside);
        }

        /**
         * side of card where the picture is
         *
         * @param side
         */
        public void setSide(int side) {
            if (side > 5) {
                side = 0;
            }
            if (side < 0) {
                side = 5;
            }
            this.side = side;
        }

        public int getPic() {
            return pic;
        }

        public int getPicFrontBack() {
            return picFrontBack;
        }

        public boolean setPic(int pic) throws Exception {
            if (pic > 6 || pic < 1) {
                throw new Exception("Picture must be in range 1-6");
            }
            this.pic = pic;
            return true;
        }
        
        public void printPicture() {
            System.out.println("Side: "+side+", Picture: "+pic+ " ("+picFrontBack+")");
        }
        
        

    }
}
