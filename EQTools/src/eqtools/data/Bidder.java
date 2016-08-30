/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools.data;

import eqtools.Auctionator;
import eqtools.PlayerInfo;

/**
 *
 * @author AaronServer
 */
public class Bidder implements Comparable<Bidder> {
    public String name;
    public String message;
    public int dice;
    
    public Bidder(String name, String message) {
        this.name = name;
        this.message = message;
    }
    
    /**
     * Gives a weighted score based on players raid stats and items looted.
     * 
     * @return 
     */
    public int score() {
        return score(0);
    }
    
    /**
     * Gives a weighted score based on players raid stats and items looted.
     * A weighted dice can be added by including a diceWeight more then 0.
     * 
     * Note: A diceWeight of more than 1000 is effectively a completely random winner
     * This can actually be used to simply randomly award loot (With a small weighted bonus to loyal raiders)
     * Possibly a neat solution for open raids.
     * 
     * @param diceWeight
     * @return 
     */
    public int score(int diceWeight) {
        if (diceWeight != 0 && dice == 0) {
            dice = (int)(Math.random()*diceWeight);
        }
        
        return PlayerInfo.getPlayer(name).score(dice);
    }

    @Override
    public int compareTo(Bidder b) {
        int aScore = score(Auctionator.DICE_WEIGHT);
        int bScore = b.score(Auctionator.DICE_WEIGHT);
        
        if (bScore > aScore) {
            return 1;
        }
        if (bScore < aScore) {
            return -1;
        }
        return 0;
    }
}
