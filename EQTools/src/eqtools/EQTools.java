/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools;

import eqtools.data.Player;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JFrame;

/**
 *
 * @author AaronServer
 */
public class EQTools {

    private Auctionator auctionator;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //testScores();
        new EQTools();
    }
    
    public static void testScores() {
        try {
            File f = new File("data.txt");
            BufferedReader reader = new BufferedReader(new FileReader(f));
            
            String line;
            while ((line = reader.readLine()) != null) {
                Player player = new Player(line);
                System.out.println(player.getName() + ": " + player.score(0));
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public EQTools() {
        auctionator = new Auctionator();
        auctionator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        auctionator.setTitle("Singorr's Toolkit");
        auctionator.setVisible(true);
        
        while (true) {
            auctionator.step();
            try {Thread.sleep(40);}catch (Exception e) {}
        }
    }
}
