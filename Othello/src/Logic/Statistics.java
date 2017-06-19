
package Logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Statistiky
 * @author Musil
 */
public class Statistics {
    
    private PrintWriter wr;
    private Scanner sc;
    private File statistics;
    private int totalGames;
    private int blackWins;
    private int whiteWins;
    private int blackStones;
    private int whiteStones;
    
    /**
    * Inicializes statistics object file, loads values. 
    * If file does not exist, it is created and filled with values.
    * 
    * @throws IOException Trhows if tehre is error with loadiung the file
    */
    public Statistics() throws IOException {
        try {
            statistics = new File("statistics.txt");
            
            if(statistics.createNewFile()) {
                totalGames = 0;
                blackWins = 0;
                whiteWins = 0;
                blackStones = 0;
                whiteStones = 0;
            } else {
                sc = new Scanner(statistics);

                totalGames = sc.nextInt();
                blackWins = sc.nextInt();
                whiteWins = sc.nextInt();
                blackStones = sc.nextInt();
                whiteStones = sc.nextInt();

                sc.close();
            }
        } 
        catch(FileNotFoundException ex) {
            System.out.println("Chyba načtení - " + ex.getMessage());
        }
    }
    
    /**
    * Resets all statistics currently loaded
    */
    public void resetStatistics() {
        totalGames = 0;
        blackWins = 0;
        whiteWins = 0;
        blackStones = 0;
        whiteStones = 0;
    }
    
    /**
    * Updates statistics currently loaded with values from game
    * 
    * @param winner  int incremented winner, 1 - black, 2 - white, other - draw
    * @param bStones int count of black stones
    * @param wStones int count of white stones
    */
    public void updateStatistics(int winner, int bStones, int wStones) {
        totalGames++;
        
        if(winner == 1) {
            blackWins++;
        } else if(winner == 2) {
            whiteWins++;
        }
        
        blackStones += bStones;
        whiteStones += wStones;
    }
    
    /**
    * Saves statistics into file
    */
    public void saveStatistics() {
        try {
            wr = new PrintWriter(new BufferedWriter(new FileWriter(statistics)));
            
            wr.println(toString());
            
            wr.close();
        }
        catch(IOException ex) {
            System.out.println("Chyba zápisu - " + ex.getMessage());
        }
    }

    /**
    * Returns number of all games played since last update
    * 
    * @return int count of all games
    */
    public int getTotalGames() {
        return totalGames;
    }

    /**
    * Returns number of all games winned by blacks since last update
    * 
    * @return int count of black wins
    */
    public int getBlackWins() {
        return blackWins;
    }

    /**
    * Returns number of all games winned by whites since last update
    * 
    * @return int count of white wins
    */
    public int getWhiteWins() {
        return whiteWins;
    }

    /**
    * Returns number of all black stones successfuly placed onto game field since last update
    *
    * @return int count of black stones
    */
    public int getBlackStones() {
        return blackStones;
    }

    /**
    * Returns number of all white stones successfuly placed onto game field since last update
    * 
    * @return int count of white stones
    */
    public int getWhiteStones() {
        return whiteStones;
    }
    
    @Override
    public String toString() {
        return getTotalGames() + " " + getBlackWins() + " " + getWhiteWins() + " " + getBlackStones() + " " + getWhiteStones();
    }
}
