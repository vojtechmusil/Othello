
package UI;

import Logic.Statistics;
import Logic.Game;
import java.io.File;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * Othello Game
 * @author Musil
 */
public class Othello {
    
    private static Statistics stats;
    private static Game game;
    private static Scanner sc = new Scanner(System.in);
    private static boolean sound = false;
    private static Thread soundThread = null;
    
    /**
    * Main program
    * 
    * @param args
    */
    public static void main(String[] args) {
        try { System.setOut(new PrintStream(System.out, true, "utf-8")); } 
        catch(UnsupportedEncodingException ex) { System.out.println("Unsupported encoding"); }
        
        try { stats = new Statistics(); }
        catch(IOException ex) { System.out.println("Chyba načtení statistik"); }
        
        int select = 1;
        
        while(select != 0) {
            menu();
            
            System.out.print("Volba: ");
            
            try { select = sc.nextInt(); }
            catch (InputMismatchException ex) { select = -1; sc.nextLine(); }
            
            switch(select) {
                case 1: 
                    initGame();
                    playGame();
                    break;
                    
                case 2:
                    showStatistics();
                    break;
                    
                case 3:
                    resetStatistics();
                    break;
                    
                case 4:
                    if(!sound) {
                        subSound();
                        soundThread.start();
                    } else {
                        System.out.println("Špatná volba, opakujte...");
                    }
                    break;
                    
                case 0:
                    saveStatistics();
                    System.out.println("Ukončuji...");
                    if(sound) {
                        try { soundThread.sleep(2000); soundThread.interrupt(); }
                        catch(InterruptedException ex) { System.out.println("Chyba vlákna"); }
                    }
                    return;
                    
                default:
                    System.out.println("Špatná volba, opakujte...");
            }
        }
    }
    
    /**
    * Shows main menu
    */
    public static void menu() { 
        System.out.println("");
        System.out.println("MENU");
        System.out.println("1 - nová hra");
        System.out.println("2 - statistiky");
        System.out.println("3 - resetovat statistiky");
        if(!sound) {
            System.out.println("4 - zapnout audio");
        }
        System.out.println("0 - konec");
        System.out.println("");
    }

    /**
    * Inicializes the game
    */
    public static void initGame() {
        game = new Game();
    }

    /**
    * Game handler, all game methods goes through it
    */
    public static void playGame() {
        int x;
        int y;
        boolean end = false;
        
        while(!end) {
            showDesk(game.getDesk());
            
            System.out.println("");
            System.out.println("Hraje - " + game.getPlayer());
            System.out.println("Možnosti - " + game.getPlayerMoves());
            
            if(game.getPlayerMoves() == 0) {
                System.out.println("Neni mozny tah, hraje druhy hrac");
                break;
            }
            
            if(game.getPossibleMoves() == 0 || game.getFreeCells() == 0) { break; }
            
            System.out.println("Zadejte - x y");
            
            try {
                x = sc.nextInt();
                y = sc.nextInt();
                
                if(!game.fillCell(x, y)) { System.out.println("Špatné souřadnice, opakujte..."); }
            }
            catch (InputMismatchException ex) { 
                x = -1;
                y = -1;
                sc.nextLine();
                System.out.println("Špatně zadáno, opakujte..."); 
            }
        }
        
        getFinalScores();
    }
    
    /**
    * Shows final score when game finishes
    */
    public static void getFinalScores() {
        System.out.println("");
        System.out.println("Konec hry!");
        
        if (game.getBlackScore() > game.getWhiteScore()) {
            System.out.println("Vyhrál černý");
            updateStatistics(1, game.getBlackScore(), game.getWhiteScore());
            
        } else if(game.getBlackScore() < game.getWhiteScore()) {
            System.out.println("Vyhrál bílý");
            updateStatistics(2, game.getBlackScore(), game.getWhiteScore());
            
        } else {
            System.out.println("Remíza!");
            updateStatistics(0, game.getBlackScore(), game.getWhiteScore());
        }
        
        System.out.println("Černých kamenů - " + game.getBlackScore());
        System.out.println("Bílých kamenů - " + game.getWhiteScore());
    }
    
    /**
    * Shows game field as it is in its current state
    *
    * @param  desk 2D char array
    */
    public static void showDesk(char[][] desk) {
        System.out.println("");
        
        for (int i = -1; i < desk.length; i++) {
            if(i > -1) {
                System.out.print(i + " ");
                
                for (int j = 0; j < desk.length; j++) {
                    System.out.print(desk[i][j] + " ");
                }
                
                System.out.println();
            } else {
                for (int j = -1; j < desk.length; j++) {
                    if(j > -1) {
                        System.out.print(j + " ");
                    } else {
                        System.out.print("  ");
                    }
                }
                System.out.println("");
            }
        }
    }
    
    /**
    * Shows game statistics since its last save
    */
    public static void showStatistics() {
        System.out.println("");
        
        System.out.println("Her celkem: " + stats.getTotalGames());
        System.out.println("Výher černé: " + stats.getBlackWins());
        System.out.println("Výher bílé: " + stats.getWhiteWins());
        System.out.println("Kamenů černé: " + stats.getBlackStones());
        System.out.println("Kamenů bílé: " + stats.getWhiteStones());
    }

    /**
    * Resets game statistics file
    */
    public static void resetStatistics() {
        stats.resetStatistics();
    }

    /**
    * Updates game statistics with values from game
    *
    * @param  winner  number of winning player, 1 = black, 2 = white, 3 = draw
    * @param  bStones final number of white stones on game field
    * @param  wStones final number of black stones on game field
    */
    public static void updateStatistics(int winner, int bStones, int wStones) {
        stats.updateStatistics(winner, bStones, wStones);
    }
    
    /**
    * Saves game statistics
    */
    public static void saveStatistics() {
        stats.saveStatistics();
    }
    
    /**
    * Plays background audio
    */
    public static void subSound() {
        sound = true;
        
        soundThread = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                try {
                    File file = new File("song.wav");
                    AudioInputStream stream = AudioSystem.getAudioInputStream(file);
                    Clip clip = AudioSystem.getClip();
                    clip.open(stream);
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(-24.0f);
                    clip.start();
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    Thread.sleep(clip.getMicrosecondLength());
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        });
    }
}
