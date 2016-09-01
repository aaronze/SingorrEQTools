/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools.view;

import eqtools.Auctionator;
import eqtools.data.Bidder;

/**
 *
 * @author AaronServer
 */
public class RemoveBidderButton extends ActionArea {
    private Bidder bidder;
    
    public RemoveBidderButton(Bidder bidder) {
        this.bidder = bidder;
    }

    @Override
    public void mouseClicked() {
        Auctionator.auction.removeBidder(bidder);
    }

    @Override
    public void mouseEnter() {
        
    }

    @Override
    public void mouseExit() {
        
    }
    
}
