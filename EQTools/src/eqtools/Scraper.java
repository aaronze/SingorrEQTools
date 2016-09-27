/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools;

import java.util.ArrayList;

/**
 *
 * @author AaronServer
 */
public class Scraper {
    public static ArrayList<Auction> auctions = new ArrayList<>();
    private static Auction selectedAuction = null;
    
    public static Auction getSelectedAuction() {
        return selectedAuction;
    }

    public static void selectAuction(int index) {
        if (index < 0 || index > auctions.size()) {
            selectedAuction = null;
            return;
        }
        
        selectedAuction = auctions.get(index);
    }
}
