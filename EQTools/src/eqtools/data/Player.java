/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools.data;

import java.text.NumberFormat;

/**
 *
 * @author AaronServer
 */
public class Player {
    private String name;
    private int dkp;
    private int attendance60;
    private int lastLooted;
    private int gearTotal60;
    private int gearTotalAllTime;
    private String looted;
    private boolean isCorrupted;
    private boolean isUnknown = false;
    
    public Player() {
        name = "noname";
        dkp = 0;
        attendance60 = 5;
        lastLooted = 1;
        gearTotal60 = 0;
        gearTotalAllTime = 0;
        looted = "00000000";
        isUnknown = true;
    }
    
    public Player(String line) {
        String[] tokens = line.split("\t");
        
        name = tokens[0];
        dkp = Integer.parseInt(tokens[1]);
        
        try {
            attendance60 = ((Number)NumberFormat.getInstance().parse(tokens[2])).intValue();
        } catch (Exception e) {
            // Parsing attendance failed. Set to worst attendance and flag corrupted
            attendance60 = 5;
            isCorrupted = true;
        }
        
        try {
            lastLooted = ((Number)NumberFormat.getInstance().parse(tokens[6])).intValue();
        } catch (Exception e) {
            // Parsing last looted failed. Set to worst value and flag corrupted
            lastLooted = 1;
            isCorrupted = true;
        }
        
        gearTotal60 = Integer.parseInt(tokens[7]);
        gearTotalAllTime = Integer.parseInt(tokens[8]);
        looted = tokens[10];
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    /**
     * Gives a weighted score based on players raid stats and items looted.
     * A weighted dice can be added by including a diceWeight more then 0.
     * 
     * @param diceWeight
     * @return 
     */
    public int score(int diceWeight) {
        int score = 0;
        
        score += 103 - ((attendance60 - 1) * 23); // Give a large boost to high raid attendance
        score += 19 * lastLooted; // Give a large boost to players who haven't won anything recently
        score -= 5 * gearTotal60; // Give a small defecit per item won in the last 60 days
        score += dkp / 200; // Give a tiny bonus to long term loyal raiders
        
        score += diceWeight; 
        
        return score;
    }
    
    public boolean isUnknown() {
        return isUnknown;
    }
}