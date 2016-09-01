/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools.view;

import eqtools.Auctionator;
import eqtools.PlayerInfo;
import static eqtools.Auctionator.DICE_WEIGHT;
import eqtools.data.Bidder;
import eqtools.data.Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
    
    private ArrayList<ActionArea> actions = new ArrayList<>();
    
    public BiddersView() {
        super();
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                
                for (ActionArea action : actions) {
                    if (x >= action.area.x && x <= action.area.x + action.area.width && 
                        y >= action.area.y && y <= action.area.y + action.area.height) {
                        
                        action.mouseClicked();
                    }
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        // Clear all action areas
        actions.clear();
        
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

                    drawBidder(graphics, bidder, 10, i*20 + 20, 100, 20);
                }
            }
            
            g.drawImage(buffer, 0, 0, null);
        }
    }
    
    public void drawBidder(Graphics g, final Bidder bidder, int x, int y, int width, int height) {
        g.setColor(Color.BLACK);
        
        String text;
        
        Player player = PlayerInfo.getPlayer(bidder.name);
        
        if (player.isAlt()) {
            if (player.getMain().isUnknown()) {
                text = bidder.name + ": ? [Alt of " + player.getMain().getName() + "] \"" + bidder.message + "\" \n"; 
            } else {
                text = bidder.name + ": " + player.getMain().score(DICE_WEIGHT) + " [Alt of " + player.getMain().getName() + "] \"" + bidder.message + "\" \n"; 
            }
        } else {
            if (player.isUnknown()) {
                text = bidder.name + ": ? \"" + bidder.message + "\" \n";
            } else {
                text = bidder.name + ": " + bidder.score(DICE_WEIGHT) + " \"" + bidder.message + "\" \n";
            }
        }

        graphics.drawString(text, x + 10, y);
        
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRoundRect(x-5, y-10, 10, 10, 2, 2);
        
        graphics.setColor(Color.GRAY);
        graphics.drawRoundRect(x-5, y-10, 10, 10, 2, 2);
        
        graphics.setColor(Color.BLACK);
        graphics.drawLine(x-2, y-7, x+2, y-3);
        graphics.drawLine(x-2, y-3, x+2, y-7);
        
        ActionArea removeBidderButton = new RemoveBidderButton(bidder);
        removeBidderButton.setArea(new Rectangle(x-5, y-10, 10, 10));
        actions.add(removeBidderButton);
    }
}
