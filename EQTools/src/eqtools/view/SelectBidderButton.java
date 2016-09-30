/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools.view;

import eqtools.Auctionator;
import eqtools.data.MageloProfile;
import eqtools.server.Magelo;
import java.awt.Cursor;

/**
 *
 * @author AaronServer
 */
public class SelectBidderButton extends ActionArea {
    private int index;
    private String name;
    
    public SelectBidderButton(int index, String name) {
        this.index = index;
        this.name = name;
    }

    @Override
    public void mouseClicked() {
        ScraperView.selectedPlayer = index;
        
        MageloProfile profile = Magelo.getProfile(name);
        String[] items = profile.getItemsForSlot(Auctionator.instance.getItemType());
        Auctionator.instance.viewItems(items);
    }

    @Override
    public void mouseOver() {
        BiddersView.cursor = Cursor.HAND_CURSOR;
    }
   
}
