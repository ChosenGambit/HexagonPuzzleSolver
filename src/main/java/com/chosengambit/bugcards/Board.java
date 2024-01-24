package com.chosengambit.bugcards;

import java.util.ArrayList;
import java.util.List;

/**
 * board consists of 19 tiles
 *       
 *   # # #
 *  # # # # 
 * # # # # #
 *  # # # #
 *   # # #
 * 
 * tile numbers (minus 0)
 * 
 *   01 02 03
 *  04 05 06 07
 * 08 09 10 11 12
 *  13 14 15 16
 *   17 18 19
 *    
 * @author ChosenGambit
 */
public class Board {
    
    public static final int NUM_TILES = 19;
    public static final int NUM_CARDS = 19;
    private List<Tile> tiles = new ArrayList<>();
    
    public Board() {             
        addTiles();        
    }
    
    /**
     * print card nr from every tile
     */
    public void printTileCards() {
        for (Tile tile : this.tiles) {
            if (tile.getCard() != null) {
                System.out.println("Tile# "+tile.getTileNumber()+ " has card# "+tile.getCard().getCardnr()+" pic@side0: "+tile.getCard().getPictureAtCardSide(0).getPic());
            }
            else {
                System.out.println("Tile# "+tile.getTileNumber()+ " has no card!");
            }
        }
    }
    
    /**
     * Get last tile with a card on it
     * @return 
     */
    public Tile getLastNonEmptyTileAndResetHighest() {
        
        int index = 0;
        
        for (int i = 0; i < this.tiles.size(); i++) {
            if (this.tiles.get(i).getCard() == null) {
                
                // if i is not the first card
                if (i > 0) index = i-1;
                break;
            }
        }
        resetHighestNumberAfter(index);
        return this.tiles.get(index);
    }
    
    /**
     * reset HigestCardNumber on tile for all tiles after specified number
     * @param number 
     */
    private void resetHighestNumberAfter(int number) {
        for (int i = number+1; i < this.tiles.size(); i++) {
            this.tiles.get(i).setHighestCardNumber(0);
        }
    }
    
    /**
     * All cards to 0, highest number to 0
     */
    public void resetBoard() {
        for (Tile tile : this.tiles) {
            tile.removeCard();
            tile.setHighestCardNumber(0);
        }
    }
    
    /**
     * get all tiles
     * @return 
     */
    public List<Tile> getTiles() {
        return this.tiles;
    }
    
    /**
     * returns true if card nr is present on board
     * @param cardnr
     * @return 
     */
    public boolean isCardOnBoard(int cardnr) {
        for (Tile tile : this.tiles) {
            if (tile.getCard() != null) {
                if (tile.getCard().getCardnr() == cardnr) 
                    return true;
            }
        }
        return false;
    }
    
    /**
     * add all tiles to the board
     */
    public final void addTiles() {
        
        // create tiles
        for (int i = 1; i < NUM_TILES+1; i++) {
            Tile tile = new Tile(i);
            tiles.add(tile);
        }
        
        // create connection between tiles
        // the order in which the connections are added defined the side 
        // of he tile where the connection is.
        for(Tile tile : tiles) {
            switch(tile.getTileNumber()) {
                case 1:
                    tile.addConnections(null, t(2), t(5), t(4), null, null);
                break;
                case 2:
                    tile.addConnections(null, t(3), t(6), t(5), t(1), null);
                break;
                case 3:
                    tile.addConnections(null, null, t(7), t(6), t(2), null);
                break;
                case 4:
                    tile.addConnections(t(1), t(5), t(9), t(8), null, null);
                break;
                case 5:
                    tile.addConnections(t(2), t(6), t(10), t(9), t(4), t(1));
                break;
                case 6:
                    tile.addConnections(t(3), t(7), t(11), t(10), t(5), t(2));
                break;
                case 7:
                    tile.addConnections(null, null, t(12), t(11), t(6), t(3));
                break;
                case 8:
                    tile.addConnections(t(4), t(9), t(13), null, null, null);
                break;
                case 9:
                    tile.addConnections(t(5), t(10), t(14), t(13), t(8), t(4));
                break;
                case 10:
                    tile.addConnections(t(6), t(11), t(15), t(14), t(9), t(5));
                break;
                case 11:
                    tile.addConnections(t(7), t(12), t(16), t(15), t(10), t(6));
                break;
                case 12:
                    tile.addConnections(null, null, null, t(16), t(11), t(7));
                break;
                case 13:
                    tile.addConnections(t(9), t(14), t(17), null, null, t(8));
                break;
                case 14:
                    tile.addConnections(t(10), t(15), t(18), t(17), t(13), t(9));
                break;
                case 15:
                    tile.addConnections(t(11), t(16), t(19), t(18), t(14), t(10));
                break;
                case 16:
                    tile.addConnections(t(12), null, null, t(19), t(15), t(11));
                break;
                case 17:
                    tile.addConnections(t(14), t(18), null, null, null, t(13));
                break;
                case 18:
                    tile.addConnections(t(15), t(19), null, null, t(17), t(14));
                break;
                case 19:
                    tile.addConnections(t(16), null, null, null, t(18), t(15));
                break;
            }
        }
    }
    
    /**
     * Create a new deck of cards
     * @return 
     */
    public List<Card> createCardDeck() {
        
        List<Card> cards = new ArrayList<Card>();
        
        // add cards

        Card card = new Card(1);
        card.addPicture(0, Card.Picture.SLIM_YELLOW_BEETLE, Card.Picture._BACK);
        card.addPicture(1, Card.Picture.BIG_RED_BLACK_BUG, Card.Picture._FRONT);
        card.addPicture(2, Card.Picture.BLUE_BUG, Card.Picture._BACK);
        card.addPicture(3, Card.Picture.RED_BEETLE_SLIM, Card.Picture._BACK);
        card.addPicture(4, Card.Picture.PURPLE, Card.Picture._FRONT);
        card.addPicture(5, Card.Picture.YELLOW_LADYBUG, Card.Picture._FRONT);
        cards.add(card);
        
        card = new Card(2);
        card.addPicture(0, Card.Picture.SLIM_YELLOW_BEETLE, Card.Picture._BACK);
        card.addPicture(1, Card.Picture.RED_BEETLE_SLIM, Card.Picture._BACK);
        card.addPicture(2, Card.Picture.BLUE_BUG, Card.Picture._BACK);
        card.addPicture(3, Card.Picture.PURPLE, Card.Picture._BACK);
        card.addPicture(4, Card.Picture.YELLOW_LADYBUG, Card.Picture._FRONT);
        card.addPicture(5, Card.Picture.BIG_RED_BLACK_BUG, Card.Picture._FRONT);
        cards.add(card);
        
        card = new Card(3);
        card.addPicture(0, Card.Picture.SLIM_YELLOW_BEETLE, Card.Picture._BACK);
        card.addPicture(1, Card.Picture.PURPLE, Card.Picture._FRONT);
        card.addPicture(2, Card.Picture.BIG_RED_BLACK_BUG, Card.Picture._FRONT);
        card.addPicture(3, Card.Picture.YELLOW_LADYBUG, Card.Picture._FRONT);
        card.addPicture(4, Card.Picture.RED_BEETLE_SLIM, Card.Picture._FRONT);
        card.addPicture(5, Card.Picture.BLUE_BUG, Card.Picture._BACK);
        cards.add(card);
        
        card = new Card(4);
        card.addPicture(0, Card.Picture.SLIM_YELLOW_BEETLE, Card.Picture._BACK);
        card.addPicture(1, Card.Picture.BLUE_BUG, Card.Picture._FRONT);
        card.addPicture(2, Card.Picture.PURPLE, Card.Picture._BACK);
        card.addPicture(3, Card.Picture.RED_BEETLE_SLIM, Card.Picture._BACK);
        card.addPicture(4, Card.Picture.BIG_RED_BLACK_BUG, Card.Picture._FRONT);
        card.addPicture(5, Card.Picture.YELLOW_LADYBUG, Card.Picture._FRONT);
        cards.add(card);
        
        card = new Card(5);
        card.addPicture(0, Card.Picture.SLIM_YELLOW_BEETLE, Card.Picture._FRONT);
        card.addPicture(1, Card.Picture.PURPLE, Card.Picture._BACK);
        card.addPicture(2, Card.Picture.YELLOW_LADYBUG, Card.Picture._BACK);
        card.addPicture(3, Card.Picture.RED_BEETLE_SLIM, Card.Picture._FRONT);
        card.addPicture(4, Card.Picture.BIG_RED_BLACK_BUG, Card.Picture._BACK);
        card.addPicture(5, Card.Picture.BLUE_BUG, Card.Picture._FRONT);
        cards.add(card);
        
        card = new Card(6);
        card.addPicture(0, Card.Picture.SLIM_YELLOW_BEETLE, Card.Picture._FRONT);
        card.addPicture(1, Card.Picture.PURPLE, Card.Picture._BACK);
        card.addPicture(2, Card.Picture.YELLOW_LADYBUG, Card.Picture._BACK);
        card.addPicture(3, Card.Picture.BIG_RED_BLACK_BUG, Card.Picture._BACK);
        card.addPicture(4, Card.Picture.BLUE_BUG, Card.Picture._BACK);
        card.addPicture(5, Card.Picture.RED_BEETLE_SLIM, Card.Picture._BACK);
        cards.add(card);
        
        card = new Card(7);
        card.addPicture(0, Card.Picture.SLIM_YELLOW_BEETLE, Card.Picture._FRONT);
        card.addPicture(1, Card.Picture.PURPLE, Card.Picture._FRONT);
        card.addPicture(2, Card.Picture.YELLOW_LADYBUG, Card.Picture._BACK);
        card.addPicture(3, Card.Picture.BIG_RED_BLACK_BUG, Card.Picture._BACK);
        card.addPicture(4, Card.Picture.RED_BEETLE_SLIM, Card.Picture._BACK);
        card.addPicture(5, Card.Picture.BLUE_BUG, Card.Picture._BACK);
        cards.add(card);
        
        card = new Card(8);
        card.addPicture(0, Card.Picture.SLIM_YELLOW_BEETLE, Card.Picture._FRONT);
        card.addPicture(1, Card.Picture.PURPLE, Card.Picture._BACK);
        card.addPicture(2, Card.Picture.RED_BEETLE_SLIM, Card.Picture._FRONT);
        card.addPicture(3, Card.Picture.BIG_RED_BLACK_BUG, Card.Picture._FRONT);
        card.addPicture(4, Card.Picture.YELLOW_LADYBUG, Card.Picture._BACK);
        card.addPicture(5, Card.Picture.BLUE_BUG, Card.Picture._FRONT);
        cards.add(card);
        
        card = new Card(9);
        card.addPicture(0, Card.Picture.SLIM_YELLOW_BEETLE, Card.Picture._BACK);
        card.addPicture(1, Card.Picture.BIG_RED_BLACK_BUG, Card.Picture._BACK);
        card.addPicture(2, Card.Picture.BLUE_BUG, Card.Picture._BACK);
        card.addPicture(3, Card.Picture.RED_BEETLE_SLIM, Card.Picture._FRONT);
        card.addPicture(4, Card.Picture.PURPLE, Card.Picture._FRONT);
        card.addPicture(5, Card.Picture.YELLOW_LADYBUG, Card.Picture._BACK);
        cards.add(card);
        
        card = new Card(10);
        card.addPicture(0, Card.Picture.SLIM_YELLOW_BEETLE, Card.Picture._FRONT);
        card.addPicture(1, Card.Picture.YELLOW_LADYBUG, Card.Picture._BACK);
        card.addPicture(2, Card.Picture.BIG_RED_BLACK_BUG, Card.Picture._FRONT);
        card.addPicture(3, Card.Picture.PURPLE, Card.Picture._BACK);
        card.addPicture(4, Card.Picture.BLUE_BUG, Card.Picture._BACK);
        card.addPicture(5, Card.Picture.RED_BEETLE_SLIM, Card.Picture._FRONT);
        cards.add(card);
        
        card = new Card(11);
        card.addPicture(0, Card.Picture.SLIM_YELLOW_BEETLE, Card.Picture._BACK);
        card.addPicture(1, Card.Picture.BLUE_BUG, Card.Picture._FRONT);
        card.addPicture(2, Card.Picture.PURPLE, Card.Picture._BACK);
        card.addPicture(3, Card.Picture.BIG_RED_BLACK_BUG, Card.Picture._BACK);
        card.addPicture(4, Card.Picture.RED_BEETLE_SLIM, Card.Picture._FRONT);
        card.addPicture(5, Card.Picture.YELLOW_LADYBUG, Card.Picture._FRONT);
        cards.add(card);
        
        card = new Card(12);
        card.addPicture(0, Card.Picture.SLIM_YELLOW_BEETLE, Card.Picture._BACK);
        card.addPicture(1, Card.Picture.BIG_RED_BLACK_BUG, Card.Picture._BACK);
        card.addPicture(2, Card.Picture.RED_BEETLE_SLIM, Card.Picture._FRONT);
        card.addPicture(3, Card.Picture.BLUE_BUG, Card.Picture._BACK);
        card.addPicture(4, Card.Picture.PURPLE, Card.Picture._FRONT);
        card.addPicture(5, Card.Picture.YELLOW_LADYBUG, Card.Picture._BACK);
        cards.add(card);
        
        card = new Card(13);
        card.addPicture(0, Card.Picture.SLIM_YELLOW_BEETLE, Card.Picture._FRONT);
        card.addPicture(1, Card.Picture.PURPLE, Card.Picture._BACK);
        card.addPicture(2, Card.Picture.BIG_RED_BLACK_BUG, Card.Picture._FRONT);
        card.addPicture(3, Card.Picture.BLUE_BUG, Card.Picture._FRONT);
        card.addPicture(4, Card.Picture.YELLOW_LADYBUG, Card.Picture._FRONT);
        card.addPicture(5, Card.Picture.RED_BEETLE_SLIM, Card.Picture._BACK);
        cards.add(card);
        
        card = new Card(14);
        card.addPicture(0, Card.Picture.SLIM_YELLOW_BEETLE, Card.Picture._FRONT);
        card.addPicture(1, Card.Picture.BIG_RED_BLACK_BUG, Card.Picture._BACK);
        card.addPicture(2, Card.Picture.BLUE_BUG, Card.Picture._FRONT);
        card.addPicture(3, Card.Picture.YELLOW_LADYBUG, Card.Picture._BACK);
        card.addPicture(4, Card.Picture.RED_BEETLE_SLIM, Card.Picture._FRONT);
        card.addPicture(5, Card.Picture.PURPLE, Card.Picture._FRONT);
        cards.add(card);
        
        card = new Card(15);
        card.addPicture(0, Card.Picture.SLIM_YELLOW_BEETLE, Card.Picture._FRONT);
        card.addPicture(1, Card.Picture.PURPLE, Card.Picture._BACK);
        card.addPicture(2, Card.Picture.BLUE_BUG, Card.Picture._BACK);
        card.addPicture(3, Card.Picture.BIG_RED_BLACK_BUG, Card.Picture._FRONT);
        card.addPicture(4, Card.Picture.RED_BEETLE_SLIM, Card.Picture._FRONT);
        card.addPicture(5, Card.Picture.YELLOW_LADYBUG, Card.Picture._BACK);
        cards.add(card);
        
        card = new Card(16);
        card.addPicture(0, Card.Picture.SLIM_YELLOW_BEETLE, Card.Picture._BACK);
        card.addPicture(1, Card.Picture.YELLOW_LADYBUG, Card.Picture._FRONT);
        card.addPicture(2, Card.Picture.BLUE_BUG, Card.Picture._FRONT);
        card.addPicture(3, Card.Picture.PURPLE, Card.Picture._FRONT);
        card.addPicture(4, Card.Picture.BIG_RED_BLACK_BUG, Card.Picture._BACK);
        card.addPicture(5, Card.Picture.RED_BEETLE_SLIM, Card.Picture._BACK);
        cards.add(card);
        
        card = new Card(17);
        card.addPicture(0, Card.Picture.SLIM_YELLOW_BEETLE, Card.Picture._BACK);
        card.addPicture(1, Card.Picture.BLUE_BUG, Card.Picture._FRONT);
        card.addPicture(2, Card.Picture.BIG_RED_BLACK_BUG, Card.Picture._FRONT);
        card.addPicture(3, Card.Picture.PURPLE, Card.Picture._BACK);
        card.addPicture(4, Card.Picture.RED_BEETLE_SLIM, Card.Picture._BACK);
        card.addPicture(5, Card.Picture.YELLOW_LADYBUG, Card.Picture._BACK);
        cards.add(card);
        
        card = new Card(18);
        card.addPicture(0, Card.Picture.SLIM_YELLOW_BEETLE, Card.Picture._BACK);
        card.addPicture(1, Card.Picture.BIG_RED_BLACK_BUG, Card.Picture._FRONT);
        card.addPicture(2, Card.Picture.BLUE_BUG, Card.Picture._BACK);
        card.addPicture(3, Card.Picture.RED_BEETLE_SLIM, Card.Picture._BACK);
        card.addPicture(4, Card.Picture.PURPLE, Card.Picture._FRONT);
        card.addPicture(5, Card.Picture.YELLOW_LADYBUG, Card.Picture._FRONT);
        cards.add(card);
        
        card = new Card(19);
        card.addPicture(0, Card.Picture.SLIM_YELLOW_BEETLE, Card.Picture._FRONT);
        card.addPicture(1, Card.Picture.BIG_RED_BLACK_BUG, Card.Picture._FRONT);
        card.addPicture(2, Card.Picture.PURPLE, Card.Picture._BACK);
        card.addPicture(3, Card.Picture.YELLOW_LADYBUG, Card.Picture._FRONT);
        card.addPicture(4, Card.Picture.BLUE_BUG, Card.Picture._BACK);
        card.addPicture(5, Card.Picture.RED_BEETLE_SLIM, Card.Picture._FRONT);
        cards.add(card);
        
        // remove card if NUM_CARDS is lower
        for (int i = cards.size()-1; i > NUM_CARDS-1; i--) {
            cards.remove(i);           
        }
        
        return cards;
        
    }
    
    /**
     * function alias
     * @param nr
     * @return 
     */
    public Tile t(int nr) {
        return getTileByNumber(nr);
    }
    
    /**
     * get tile by nr
     * @param nr
     * @return 
     */
    public Tile getTileByNumber(int nr) {
        for(Tile tile : tiles) {
            if (tile.getTileNumber() == nr) {
                return tile;
            }
        }
        return null;
    }
}
