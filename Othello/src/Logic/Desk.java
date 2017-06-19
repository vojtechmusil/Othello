
package Logic;

/**
 * Hraci pole
 * @author Musil
 */
public class Desk {
    
    private final char FREECELL = '.';
    private char[][] desk = null;
    
    /**
    * Inicializes game field
    *
    * @param  p1 char - character of first player
    * @param  p2 char - characted of second player
    */
    public Desk(char p1, char p2) {
        desk = new char[8][8];
        resetDesk(p1, p2);
    }
    
    /**
    * Resets game field
    *
    * @param  p1 char - character of first player
    * @param  p2 char - characted of second player
    */
    private void resetDesk(char p1, char p2) {
        for (int i = 0; i < desk.length; i++) {
            for (int j = 0; j < desk.length; j++) {
                desk[i][j] = FREECELL;
            }
        }
        
        fillCell(3, 3, p2);
        fillCell(4, 4, p2);
        fillCell(4, 3, p1);
        fillCell(3, 4, p1);
    }
    
    /**
    * Fills one cell
    *
    * @param  x  int - x coordinate
    * @param  y  int - y coordinate
    * @param  ch char - filled character
    */
    private void fillCell(int x, int y, char ch) {
        if(desk[x][y] == FREECELL) {
            desk[x][y] = ch;
        }
    }
    
    /**
    * Flips all cells in row between two points
    *
    * @param  sX  int - start x coordinate
    * @param  sY  int - start y coordinate
    * @param  eX  int - end x coordinate
    * @param  eY  int - end y coordinate
    * @param  changeTo char - filled character
    */
    private void flipRow(int sX, int sY, int eX, int eY, char changeTo) {
        int tmp;
        
        if(sY > eY) {
            tmp = sY;
            sY = eY;
            eY = tmp;
        }
            
        for (int i = sY; i < eY; i++) {
            desk[sX][i] = changeTo;
        } 
    }
    
    /**
    * Flips all cells in column between two points
    *
    * @param  sX  int - start x coordinate
    * @param  sY  int - start y coordinate
    * @param  eX  int - end x coordinate
    * @param  eY  int - end y coordinate
    * @param  changeTo char - filled character
    */
    private void flipCol(int sX, int sY, int eX, int eY, char changeTo) {
        int tmp;
        
        if(sX > eX) {
            tmp = sX;
            sX = eX;
            eX = tmp;
        }
            
        for (int i = sX; i < eX; i++) {
            desk[i][sY] = changeTo;
        }
    }
    
    /**
    * Flips all cells in diagonal from top right to bottom left between two points
    *
    * @param  sX  int - start x coordinate
    * @param  sY  int - start y coordinate
    * @param  eX  int - end x coordinate
    * @param  eY  int - end y coordinate
    * @param  changeTo char - filled character
    */
    private void flipDiagToRight(int sX, int sY, int eX, int eY, char changeTo) {
        int tmp;
        
        if(sX > eX) {
            tmp = sX;
            sX = eX;
            eX = tmp;
        }
        
        if(eY > sY) {
            tmp = sY;
            sY = eY;
            eY = tmp;
        }
        
        int j = sY;
        for (int i = sX; i < eX; i++) {
            if(j > eY) {
                desk[i][j] = changeTo;
            } else {
                break;
            }
            j--;
        }
    }
    
    /**
    * Flips all cells in diagonal from top leftto bottom rigth between two points
    *
    * @param  sX  int - start x coordinate
    * @param  sY  int - start y coordinate
    * @param  eX  int - end x coordinate
    * @param  eY  int - end y coordinate
    * @param  changeTo char - filled character
    */
    private void flipDiagToLeft(int sX, int sY, int eX, int eY, char changeTo) {
        int tmp;
        
        if(sX > eX) {
            tmp = sX;
            sX = eX;
            eX = tmp;
        }
        
        if(sY > eY) {
            tmp = sY;
            sY = eY;
            eY = tmp;
        }
        
        int j = sY;
        for (int i = sX; i < eX; i++) {
            if(j < eY) {
                desk[i][j] = changeTo;
            } else {
                break;
            }
            j++;
        }
    }
    
    /**
    * Check for validity of move. Find all possible fills and fills and fills them.
    *
    * @param  x  int - x coordinate
    * @param  y  int - y coordinate
    * @param  changeTo char - filled character
    * @return true if move is valid, fase if not
    */
    public boolean checkMove(int x, int y, char changeTo) {
        boolean legal = false;

        if(x > -1 && x < desk.length && y > -1 && y < desk.length && desk[x][y] == FREECELL) {
            int modX = 2;
            int modY = 2;
            
            // check row to left
            for (int i = y-modY; i > -1; i--) {
                if(desk[x][i+1] == FREECELL || desk[x][i+1] == changeTo) { break; }
                
                if(desk[x][i] == changeTo) {
                    fillCell(x, y, changeTo);
                    flipRow(x, y, x, i, changeTo);
                    legal = true;
                    break;
                }
            }
            
            // check row to right
            for (int i = y+modY; i < desk.length; i++) {
                if(desk[x][i-1] == FREECELL || desk[x][i-1] == changeTo) { break; }
                
                if(desk[x][i] == changeTo) {
                    fillCell(x, y, changeTo);
                    flipRow(x, y, x, i, changeTo);
                    legal = true;
                    break;
                }
            }
            
            // check col to bottom
            for (int i = x+modX; i < desk.length; i++) {
                if(desk[i-1][y] == FREECELL || desk[i-1][y] == changeTo) { break; }
                
                if(desk[i][y] == changeTo) {
                    fillCell(x, y, changeTo);
                    flipCol(x, y, i, y, changeTo);
                    legal = true;
                    break;
                }
            }
            
            // check col to top
            for (int i = x-modX; i > -1; i--) {
                if(desk[i+1][y] == FREECELL || desk[i+1][y] == changeTo) { break; }
                
                if(desk[i][y] == changeTo) {
                    fillCell(x, y, changeTo);
                    flipCol(x, y, i, y, changeTo);
                    legal = true;
                    break;
                }
            }
            
            // check to bottom right
            int j = y+modY;
            for (int i = x+modX ;i < desk.length; i++) {
                if(j < desk.length) {
                    if(desk[i-1][j-1] == FREECELL || desk[i-1][j-1] == changeTo) { break; }
                    
                    if(desk[i][j] == changeTo) {
                        fillCell(x, y, changeTo);
                        flipDiagToLeft(x, y, i, j, changeTo);
                        legal = true;
                        break;
                    }
                    
                    j++;
                } else {
                    break;
                }
            }
            
            // check to bottom left
            j = y-modY;
            for (int i = x+modX; i < desk.length; i++) {
                if(j > -1) {
                    if(desk[i-1][j+1] == FREECELL || desk[i-1][j+1] == changeTo) { break; }
                    
                    if(desk[i][j] == changeTo) {
                        fillCell(x, y, changeTo);
                        flipDiagToRight(x, y, i, j, changeTo);
                        legal = true;
                        break;
                    }
                    
                    j--;
                } else {
                    break;
                }
            }
            
            // check to top left
            j = y-modY;
            for (int i = x-modX; i > -1; i--) {
                if(j > -1) {
                    if(desk[i+1][j+1] == FREECELL || desk[i+1][j+1] == changeTo) { break; }
                    
                    if(desk[i][j] == changeTo) {
                        fillCell(x, y, changeTo);
                        flipDiagToLeft(x, y, i, j, changeTo);
                        legal = true;
                        break;
                    }
                    
                    j--;
                } else {
                    break;
                }
            }
            
            // check to top right
            j = y+modY;
            for (int i = x-modX; i > -1; i--) {
                if(j < desk.length) {
                    if(desk[i+1][j-1] == FREECELL || desk[i+1][j-1] == changeTo) { break; }
                    
                    if(desk[i][j] == changeTo) {
                        fillCell(x, y, changeTo);
                        flipDiagToRight(x, y, i, j, changeTo);
                        legal = true;
                        break;
                    }
                    
                    j++;
                } else {
                    break;
                }
            }
        }
        
        return legal;        
    }
    
    /**
    * Finds and counts all possible moves and filles of selected character
    *
    * @param  ch char - filled character
    * @return int count of all possible moves
    */
    public int countPossibleMovesFor(char ch) {
        int count = 0;

        for(int x = 0; x < desk.length; x++) {
            for (int y = 0; y < desk.length; y++) {
                if(desk[x][y] == FREECELL) {
                int modX = 2;
                int modY = 2;

                // check row to left
                for (int i = y-modY; i > -1; i--) {
                    if(desk[x][i+1] == FREECELL || desk[x][i+1] == ch) { break; }

                    if(desk[x][i] == ch) {
                        count++;
                        break;
                    }
                }

                // check row to right
                for (int i = y+modY; i < desk.length; i++) {
                    if(desk[x][i-1] == FREECELL || desk[x][i-1] == ch) { break; }

                    if(desk[x][i] == ch) {
                        count++;
                        break;
                    }
                }

                // check col to bottom
                for (int i = x+modX; i < desk.length; i++) {
                    if(desk[i-1][y] == FREECELL || desk[i-1][y] == ch) { break; }

                    if(desk[i][y] == ch) {
                        count++;
                        break;
                    }
                }

                // check col to top
                for (int i = x-modX; i > -1; i--) {
                    if(desk[i+1][y] == FREECELL || desk[i+1][y] == ch) { break; }

                    if(desk[i][y] == ch) {
                        count++;
                        break;
                    }
                }

                // check to bottom right
                int j = y+modY;
                for (int i = x+modX ;i < desk.length; i++) {
                    if(j < desk.length) {
                        if(desk[i-1][j-1] == FREECELL || desk[i-1][j-1] == ch) { break; }

                        if(desk[i][j] == ch) {
                            count++;
                            break;
                        }

                        j++;
                    } else {
                        break;
                    }
                }

                // check to bottom left
                j = y-modY;
                for (int i = x+modX; i < desk.length; i++) {
                    if(j > -1) {
                        if(desk[i-1][j+1] == FREECELL || desk[i-1][j+1] == ch) { break; }

                        if(desk[i][j] == ch) {
                            count++;
                            break;
                        }

                        j--;
                    } else {
                        break;
                    }
                }

                // check to top left
                j = y-modY;
                for (int i = x-modX; i > -1; i--) {
                    if(j > -1) {
                        if(desk[i+1][j+1] == FREECELL || desk[i+1][j+1] == ch) { break; }

                        if(desk[i][j] == ch) {
                            count++;
                            break;
                        }

                        j--;
                    } else {
                        break;
                    }
                }

                // check to top right
                j = y+modY;
                for (int i = x-modX; i > -1; i--) {
                    if(j < desk.length) {
                        if(desk[i+1][j-1] == FREECELL || desk[i+1][j-1] == ch) { break; }

                        if(desk[i][j] == ch) {
                            count++;
                            break;
                        }

                        j++;
                    } else {
                        break;
                    }
                }
                }
            }
        }
        
        return count;   
    }
            
    /**
    * Counts all selected characters in field
    *
    * @param  counted char - counted character
    * @return int count of all selected characters in game field
    */
    public int countChars(char counted) {
        if(desk != null) {
            int count = 0;
            
            for (int i = 0; i < desk[0].length; i++) {
                for (int j = 0; j < desk.length; j++) {
                    if(desk[i][j] == counted) {
                        count++;
                    }
                }
            }
            
            return count;
        } else {
            return 0;
        }
    }
    
    /**
    * Returns game field
    *
    * @return 2D char array
    */
    public char[][] getDesk() {
        return desk;
    }
    
}
