/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools;

import eqtools.data.Bidder;
import eqtools.data.BidderView;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author AaronServer
 */
public class BidderScrollPane extends JPanel {
    private ArrayList<Bidder> bidders = new ArrayList<>();

    public void addBidder(Bidder bidder) {
        bidders.add(bidder);
        
        updateBidders();
    }
    
    public void clearBidders() {
        bidders.clear();
        
        updateBidders();
    }
    
    public void updateBidders() {
        this.removeAll();
        
        for (Bidder bidder : bidders) {
            BidderView view = new BidderView(bidder);
            this.add(view);
            
            view.setSize(180, 90);
            view.setVisible(true);
        }
        
        validate();
        repaint();
    }
}
