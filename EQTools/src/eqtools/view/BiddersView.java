/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools.view;

import eqtools.Auctionator;
import eqtools.PlayerInfo;
import static eqtools.Auctionator.DICE_WEIGHT;
import static eqtools.Auctionator.auction;
import eqtools.data.Bidder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author AaronServer
 */
public class BiddersView extends JPanel {

    private BufferedImage buffer = null;
    private Graphics graphics = null;
    
    public BiddersView() {
        super();
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        
        if (width == 0 || height == 0) {
            // Bail out, it's not fully created and positioned yet
            return;
        }
        
        // If no buffer, or mismatching sized buffer - reset buffer
        if (buffer == null || buffer.getWidth() != width || buffer.getHeight() != height) {
            buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            graphics = buffer.getGraphics();
        }
        
        if (graphics != null) {
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height);
            
            if (Auctionator.auction != null) {
                List<Bidder> list = Arrays.asList(Auctionator.auction.getBidders());
                Collections.sort(list, new Comparator<Bidder>() {
                    public int compare(Bidder left, Bidder right)  {
                        return right.score(DICE_WEIGHT) - left.score(DICE_WEIGHT);
                    }
                });

                graphics.setColor(Color.BLACK);
                graphics.setFont(new Font("Arial", Font.PLAIN, 12));

                for (int i = 0; i < list.size(); i++) {
                    Bidder bidder = list.get(i);

                    String text;
                    if (PlayerInfo.getPlayer(bidder.name).isUnknown()) {
                        text = bidder.name + ": ? \"" + bidder.message + "\" \n";
                    } else {
                        text = bidder.name + ": " + bidder.score(DICE_WEIGHT) + " \"" + bidder.message + "\" \n";
                    }

                    graphics.drawString(text, 10, i * 20 + 20);
                }
            }
            
            g.drawImage(buffer, 0, 0, null);
        }
    }
    
    public void drawBidder(Graphics g, Bidder bidder, int x, int y, int width, int height) {
        g.setColor(Color.BLACK);
        
    }
}
