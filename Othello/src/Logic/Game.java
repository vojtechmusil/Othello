
package Logic;

/**
 * Hra
 * @author Musil
 */
public class Game {

    private final int BLACK = 0;
    private final int WHITE = 1;
    private Desk desk;
    private int plays;
    private int freeCells;
    private int whiteScore;
    private int blackScore;
    
    /**
    * Inicializes game object
    */
    public Game() {
        desk = new Desk('x', 'o');
        plays = BLACK;
        freeCells = desk.countChars('.');
        whiteScore = 0;
        blackScore = 0;
    }
    
    /**
    * Check possible selecte move, check for fill to others and fill into 
    * selected cells current player's character.
    * Updates scores and swicth current player.
    *
    * @param  x  x coordinate
    * @param  y  y coordinate
    * @return      boolean true if successfully filled, false if move is not possible
    */
    public boolean fillCell(int x, int y) {
        if(plays == BLACK) {
            if(desk.checkMove(x, y, 'o')) {
                blackScore = desk.countChars('o');
                whiteScore = desk.countChars('x');
                freeCells--;
                plays = WHITE;
                return true;
            }
        } else {
            if(desk.checkMove(x, y, 'x')) {
                blackScore = desk.countChars('o');
                whiteScore = desk.countChars('x');
                freeCells--;
                plays = BLACK;
                return true;
            }
        }
        
        return false;
    }
    
    /**
    * Returns current player
    * 
    * @return      String current player
    */
    public String getPlayer() {
        if(plays == BLACK) {
            return "Černý (o)";
        } else {
            return "Bílý (x)";
        }
    }
    
    /**
    * Returns counted possible moves and fills of both players
    * 
    * @return      int counted possible moves for both players
    */
    public int getPossibleMoves() {
        return desk.countPossibleMovesFor('o') + desk.countPossibleMovesFor('x');
    }
    
    /**
    * Returns counted possible moves and fill for current player
    * 
    * @return      int counted possible moves of current player
    */
    public int getPlayerMoves() {
        if(plays == BLACK) {
            return desk.countPossibleMovesFor('o');
        } else {
            return desk.countPossibleMovesFor('x');
        }
    }
    
    /**
    * Returns counted free cells
    * 
    * @return      int the image at the specified URL
    */
    public int getFreeCells() {
        return freeCells;
    }
    
    /**
    * Returns count of white stones
    *
    * @return      int count of white stones
    */
    public int getWhiteScore() {
        return whiteScore;
    }
    
    /**
    * Returns count of black stones
    * 
    * @return      int count of black stones
    */
    public int getBlackScore() {
        return blackScore;
    }
    
    /**
    * Returns current game field
    * 
    * @return      2D char array
    */
    public char[][] getDesk() {
        return desk.getDesk();
    }
    
}
