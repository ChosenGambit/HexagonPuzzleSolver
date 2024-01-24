package com.chosengambit.bugcards;

import com.chosengambit.bugcards.gfx.CardManager;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Brute force a solution
 * @author ChosenGambit
 */
public class BruteForce {

    private Board board;
    private List<Tile> tiles;
    private static final boolean VERBOSE = false;
    private static final int PIC_FOUND = 1;
    private static final int PIC_NOT_FOUND = 2;
    private static final int PIC_NULL = 3;
    private static final int BACK_AT_TILE_ONE = 4;
    
    // throttle speed
    public static long GRAPHIC_STEP_PLACE = (long) 0.5;
    public static long GRAPHIC_STEP_BACK = (long) 0.8;
    public static long GRAPHIC_STEP_ROTATE = (long) 0.4;
    
    private List<Card> deck;
    private List<Card> unplacedCards = new ArrayList<>();
    private int rotations = 6;
    private int numSolutions = 0;
    private int tries = 0;
    private boolean paintStuff = true;
    
    private CardManager cardManager;
    
    public BruteForce(Board board, CardManager cardManager) {
        
        this.cardManager = cardManager;    
        this.cardManager.init();
        this.board = board;
        this.tiles = board.getTiles();    
        
        // try each card, with each possible rotation at the start of each try
        out: for (int i = 0; i < Board.NUM_TILES; i++) {             
            
            for (int r = 0; r < this.rotations; r++) {     
                            
                //System.out.println("######## Start with "+i+ " with rotation "+r);
                this.deck = board.createCardDeck();
                this.deck.add(0,this.deck.remove(i)); // set new startcard    
                this.board.resetBoard();            
                this.unplacedCards.clear();       
                Tile firstTile = dealTopCardOnEmptyTile();
                firstTile.getCard().rotateRight(r);            
                start();
            }
        }
        
        System.out.println("The number of solutions found are: "+this.numSolutions);
        System.out.println("The number of tries is: "+this.tries);

    }   
    
    /**
     * Start solving
     * @return int
     */
    public int start() {
        
        //this.printDeck()

        // if we have cards left to place, start placing
        while (deck.size() > 0) {
            
            Tile lastTile = dealTopCardOnEmptyTile(); 
            
            // could not deal card anymore
            if (lastTile == null) {
                stepBackAndRetry();
                return 0;
            }
            else {
                // if last placed card can make a connection with previous card
                if (checkConnectionsForPictureMatch(lastTile)) {
                    resetUnplacedCardsToDeck();
                }
                // card could not be placed, find new one, put this card in the deck again
                else {
                    //System.out.println("Remove_c -> tile#"+lastTile.getTileNumber()+ " card#"+lastTile.getCard().getCardnr());
                    unplaced(lastTile.getCard());                   
                    lastTile.removeCard();                    
                }  
            }            
        }      
        
        // check if algorithm finished, if tile #18 has a card
        if (!isSolution()) {            
            stepBackAndRetry();
        }
        else {
            // stop repainting board if we found a solution
            paintStuff = false;
        }
        return 1;
    }
    
    /**
     * remove card before the last card that did not fit
     * move on from there
     */
    private int stepBackAndRetry() {
        // find last card on tiles
        Tile lastTile = this.board.getLastNonEmptyTileAndResetHighest();

        // move card to "unplaced" array
        unplaced(lastTile.getCard());      
        
        // move all unplaced cards to deck
        resetUnplacedCardsToDeck();
        
        // remove card from tile
        lastTile.removeCard();         

        doGraphicStep(GRAPHIC_STEP_BACK);
        
        // if we are back at tile 1, reset deck and rotate card 1 
        if (lastTile.getTileNumber() == 1) {
            //System.out.println("Back at tile 1");      
            return BACK_AT_TILE_ONE;
        }
        // continue when the first tile is not changed
        else {
            start();    
        }
                
        return -1;
    }  
    
    /**
     * add card from tile to unplaced deck
     * @param tile 
     */
    private void unplaced(Card card) {
        //System.out.println("Move to unplaced: "+card.getCardnr());
        card.resetRotation();
        this.unplacedCards.add(card);                        
    }
    
    /**
     * Deal card on first empty tile we find
     * The last card on the tile may not be lower than the last try
     * @return Tile with placedCard
     */
    public Tile dealTopCardOnEmptyTile() {
        
        Tile tile = null;
        
        for (int i = 1; i < this.tiles.size()+1; i++) {
            tile = getTileByNumber(i);
            Card card = tile.getCard();
            
            // no card on tile yet, and card must be higher than highest try
            if (card == null) { 
                while (deck.size() > 0) {
                    
                    Card placeCard = deck.remove(0);                      
                    // only place card if number is higher that last card
                    
                    if (placeCard.getCardnr() > tile.getHighestCardNumber()) {
                        tile.setCard(placeCard);
                        doGraphicStep(GRAPHIC_STEP_PLACE);
                        return tile;
                    }       
                    
                    // card could not be placed
                    unplaced(placeCard);
                }
                
                return null;
            }
        }        
        return tile;
    }
    
    /**
     * Add unplaced cards to deck
     */
    private void resetUnplacedCardsToDeck() {
        this.deck.addAll(0, this.unplacedCards);
        this.unplacedCards.clear();
        Collections.sort(this.deck);
    }
    
    /**
     * check if last tile has card
     * @return 
     */
    public boolean isSolution() {
        int index = this.tiles.size()-1;
        if (this.tiles.get(index).getCard() != null) {
            System.out.println("Solution found");
            this.board.printTileCards();
            this.numSolutions++;
            return true;
        }
        return false;
    }
    
    /**
     * get Tile by Number, Number 1-18 lie on a fixed position
     * @param number
     * @return 
     */
    public Tile getTileByNumber(int number) {
        
        for (Tile tile : this.tiles) {
            if (tile.getTileNumber() == number) {
                return tile;
            }
        }
        return null;
    }
    
    /**
     * Check if pictures on all sides of the card on the tile match with adjacents
     * if one side matches, stop rotating the card, all other sides must match as well
     * 
     * @param card
     * @return 
     */
    public boolean checkConnectionsForPictureMatch(Tile tile) {
               
        boolean rotate = true;
               
        // check if there is null or a match on each side        
        for (int side = 0; side < Tile.TILE_SIDES; side++) {
            Tile connection = tile.getConnectedTileAtSide(side);
            
            // if theres no connection: skip, assume border
            if (connection == null) continue; 
            
            // check each connection for matching pictures
            int rotation = 6;
            boolean match = false;

            // rotate intil we find a match
            while (rotation > 0) {
                
                doGraphicStep(GRAPHIC_STEP_ROTATE);
                
                int matchType = comparePictures(tile, connection, side);

                if (matchType != PIC_NOT_FOUND) {
                    match = true;
                    
                    // if we have a matching pic, stop rotating
                    // all other sides must fit from this point
                    if (matchType == PIC_FOUND) {
                        rotate = false;
                    }
                    
                    break;
                }
                
                rotation--;        
                
                // if we found a match at one side, stop rotation while checking other sides
                if (rotate) {
                    tile.getCard().rotateRight();
                }                                
            }
 
            // if card found at opposite tile but no match? abort immediately
            if (!match) return false;
        }
        
        //System.out.println("_Match! -> tile: "+tile.getTileNumber()+ " card: "+tile.getCard().getCardnr());
        return true;
    }
    
    /**
     * Compare pictures of opposite sides of two cards from two tiles
     * @param tile
     * @param connection
     * @return 1 if match, 0 if no match, -1 if no card
     */
    public int comparePictures(Tile tile, Tile connection, int side) {
        
        this.tries++;
        // compare picture at each side of own card 
        // with 
        // other side of picture of adjacent connection
        Card card = tile.getCard();
        Card connectedCard = connection.getCard();
 
        if (connectedCard != null) {
            Card.Picture pic = card.getPictureAtCardSide(side);      
            Card.Picture connectedPic = connectedCard.getPictureAtCardSide(tile.getOppositeSide(side));      

            //if (VERBOSE) System.out.println("-> side"+side+ " ("+pic.getPic()+"|"+pic.getPicFrontBack()+ ") with ("+connectedPic.getPic()+"|"+connectedPic.getPicFrontBack()+")");
            if (pic.getPic() == connectedPic.getPic()) {
                if (pic.getPicFrontBack() != connectedPic.getPicFrontBack()) {     
                    //System.out.println("-> side"+side+ " cardnr "+card.getCardnr()+ " ("+pic.getPic()+"|"+pic.getPicFrontBack()+ ") with cardnr "+connectedCard.getCardnr()+" ("+connectedPic.getPic()+"|"+connectedPic.getPicFrontBack()+")");
                    // its a match                    
                    return PIC_FOUND;
                }
            }
        }
        else {
            return PIC_NULL;
        }
        
        return PIC_NOT_FOUND;
    }
    
    /**
     * print current cards in deck
     */
    public void printDeck() {
        System.out.println(" **** ");
        for (Card card : this.deck) {            
            System.out.println("card in deck: #"+card.getCardnr());            
        }
        System.out.println(" **** ");
    }
    
    /**
     * 
     */
    public void removeAllCardsFromTiles() {
        //Main.p("remove all cards");
        for (Tile tile : this.tiles) {
            if (tile.getTileNumber() != 1)
                tile.removeCard();
        } 
    }
    
    /**
     * copy deck of cards
     *
     * @param list
     * @return
     */
    public List<Card> copyList(List<Card> list) {
        List<Card> copy = new ArrayList<Card>();
        for (Card item : list) {
            copy.add(item);
        }
        return copy;
    }

    /**
     * calculate factorial of nr
     *
     * @param nr
     * @return
     */
    public BigInteger factorial(int nr) {

        if (VERBOSE) {
            System.out.print("" + nr + "");
        }
        BigInteger result = new BigInteger(String.valueOf(nr));

        for (int i = nr; i > 0; i--) {
            if ((i - 1) > 0) {
                if (VERBOSE) {
                    System.out.print(" * ");
                }
                return result.multiply((factorial(i - 1)));
            }
        }
        if (VERBOSE) {
            System.out.print(" = ");
        }
        return result;
    }

    /**
     * calculate how many times a card should lie on a tile
     *
     *
     * @param nr
     * @return
     */
    public BigInteger triesPerCard(int nr) {

        BigInteger original_nr = new BigInteger(String.valueOf(nr));
        BigInteger tries = factorial(nr);
        tries = tries.divide(original_nr);

        if (VERBOSE)System.out.print("Tries per number is " + tries.toString());
        
        return tries;
    }
    
    public void doGraphicStep(long delay) {
        if (paintStuff) {
            this.cardManager.setBoardInformation(this.tiles);
            try {Thread.sleep(delay);}catch(Exception e){}
        }
        
    }

}
