/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try {
            LogParser lp = new LogParser(new File("C:\\Users\\Public\\Daybreak Game Company\\Installed Games\\EverQuest\\Logs\\eqlog_Letmeintotacvi_bristle.txt"));
            lp.start();
            
            new EQTools();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EQTools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public EQTools() {
        auctionator = new Auctionator();
        auctionator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        auctionator.setTitle("Auctionator");
        auctionator.setVisible(true);
    }
}
