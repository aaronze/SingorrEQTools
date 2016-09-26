/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools;

import eqtools.data.Bidder;
import eqtools.server.Magelo;
import java.util.ArrayList;

/**
 *
 * @author AaronServer
 */
public class Auction {
    private ArrayList<Bidder> bidders = new ArrayList<>();
    private String item;
    private int quantity = 1;
    private boolean isOpen = true;
    private String itemType;
    
    public Auction() {
        
    }
    
    public Auction(String item) {
        this.item = item;
    }
    
    public Auction(String item, int quantity) {
        this.item = item;
        this.quantity = quantity;
        
        itemType = Magelo.getItemType(item);
    }

    public void addBidder(Bidder bidder) {
        // Ensure person auctioning sending tells and using tell windows
        // does not accidentally add themselves to the bidding pool
        if (bidder.name.equalsIgnoreCase(Auctionator.logReader.getCharacterName())) {
            return;
        }
        
        // Ensure no bidder is added twice
        if (hasBidder(bidder.name)) {
            return;
        }
        
        // If auction is closed
        if (!isOpen) {
            return;
        }
        
        bidders.add(bidder);
    }
    
    public boolean hasBidder(String name) {
        for (Bidder b : bidders.toArray(new Bidder[0])) {
            if (b.name.equalsIgnoreCase(name)) {
                return true;
            }
        }
        
        return false;
    }
    
    public void clearBidders() {
        bidders.clear();
    }
    
    public Bidder[] getBidders() {
        return bidders.toArray(new Bidder[0]);
    }
    
    public void closeAuction() {
        isOpen = false;
    }

    public void removeBidder(Bidder bidder) {
        bidders.remove(bidder);
    }
    
    public void setItem(String s) {
        item = s;
    }
    
    public String getItem() {
        return item;
    }
    
    public void setQuantity(int n) {
        quantity = n;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public String getItemType() {
        return itemType;
    }
}
