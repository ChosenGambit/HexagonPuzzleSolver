package com.chosengambit.bugcards;

import java.math.BigInteger;
import java.util.List;

/**
 * A Tile is a placeholder to put a Card on
 * 
 * A tile consists of 6 sides
 * 
 * 5  / \ 0
 * 4 |   | 1
 * 3  \ / 2
 * 
 * 
 * @author ChosenGambit
 */
public class Tile {
    
    private int tilenr;
    private BigInteger countDown; // countDown per card on tile
    private List<Card> cards;
    private Tile[] connectedTiles = new Tile[6];
    private Card card = null;
    private int highestCardNumber = 0;
    public static final int TILE_SIDES = 6;
    
    public Tile(int tilenr) {
        this.tilenr = tilenr;
    }

    /**
     * Get highest card number on tile
     * @return 
     */
    public int getHighestCardNumber() {
        return highestCardNumber;
    }

    /**
     * Set highest card number on tile
     * @param highestCardNumber 
     */
    public void setHighestCardNumber(int highestCardNumber) {
       this.highestCardNumber = highestCardNumber;     
    }
        
    /**
     * set card on this tile
     * @param card
     */
    public void setCard(Card card) {
        this.card = card;
        this.setHighestCardNumber(card.getCardnr());
    }
    
    public void removeCard() {
        this.card = null;
    }
    
    public BigInteger getCountDown() {
        return countDown;
    }

    public void setCountDown(BigInteger countDown) {
        this.countDown = countDown;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
    
    /**
     * get card attached to this tile
     * @return 
     */
    public Card getCard() {
        return this.card;
    }
    
    /**
     * get unique number of this tile
     * @return 
     */
    public int getTileNumber() {
        return tilenr;
    }
        
    /**
     * get all connected tiles to this tile
     * @return 
     */
    public Tile[] getConnectedTiles() {
        return this.connectedTiles;
    }
    
    /**
     * get connected tile at specific side of this tile
     * @param side
     * @return 
     */
    public Tile getConnectedTileAtSide(int side) {
        return this.connectedTiles[side];
    }
    
    /**
     * get opposite side of param side of the tile
     * @param side
     * @return integer of opposite side
     */
    public int getOppositeSide(int side) {
        switch (side) {
            case 0: return 3;
            case 1: return 4;
            case 2: return 5;
            case 3: return 0;
            case 4: return 1;
            case 5: return 2;
        }
        return -1;
    }
    
    /**
     * add connections to other tiles
     * @param tiles 
     */
    public void addConnections(Tile... tiles) {
        
        int side = 0;
        for (Tile tile : tiles) {
            connectedTiles[side] = tile; // null value = no connection that side
            side++;
        }   
    }
    
    /** 
     * print tile number this tile is connected to
     */
    public void isConnectedTo() {
        String out = "Tile #"+getTileNumber()+" is connected to: ";
        for (Tile tile : this.connectedTiles) {
            out += ""+tile.getTileNumber()+",";
        }
        System.out.println(out);
    }
    
    public class NoConnectedTileException extends Exception {
        public NoConnectedTileException(String message) {
            super(message);
        }
    }
    
    public class NoCardException extends Exception {
        public NoCardException(String message) {
            super(message);
        }
    }
    
}
