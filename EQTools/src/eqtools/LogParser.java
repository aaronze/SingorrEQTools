/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools;

import eqtools.data.Bidder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author AaronServer
 */
public class LogParser extends Thread {
    private final BufferedReader logReader;
    private final int POLL_INTERVAL = 10;
    private boolean keepAlive = true;
    private final File logFile;
    private final String characterName;
   
    public LogParser(File logFile) throws FileNotFoundException {
        this.logFile = logFile;
        logReader = new BufferedReader(new FileReader(logFile));
        
        String name = logFile.getName();
        name = name.replace("eqlog_", "");
        name = name.replace(".txt", "");
        name = name.substring(0, name.indexOf("_"));
        characterName = name;
    }
    
    public String getCharacterName() {
        return characterName;
    }
    
    @Override
    public void run() {
        consumeFile();
        
        try {
            while (keepAlive) {
                String line;
                while ((line = logReader.readLine()) != null) {
                    parseLine(line);
                }
                
                try {
                    Thread.sleep(POLL_INTERVAL);
                } catch (Exception e) {
                    // Silently ignore
                }
            }
        } catch (Exception e) {
            System.err.println("Log Parser thread failed: " + e.getMessage());
        }
    }
    
    public void parseNext() throws IOException {
        String line;
        while ((line = logReader.readLine()) != null) {
            parseLine(line);
        }
    }
    
    public void stopParsing() {
        keepAlive = false;
    }
    
    public void consumeFile() {
        try {
            logReader.skip(logFile.length());
        } catch (IOException ex) {
            Logger.getLogger(LogParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void parseLine(String line) {
        if (Auctionator.auction != null) {
            parseAuction(line);
        }
    }
    
    private void parseAuction(String line) {
        // TODO Find a way to exclude outbound messages adding player by detecting player name by log file name
        Matcher tellWindowMatcher = Pattern.compile("\\[.*\\] (.*) \\-\\> .*: (.*)").matcher(line);
        if (tellWindowMatcher.find()) {
            String name = tellWindowMatcher.group(1);
            String message = tellWindowMatcher.group(2);
            Auctionator.auction.addBidder(new Bidder(name, message));
        }
        
        Matcher tellMatcher = Pattern.compile("\\[.*\\] (.*) tells you, '(.*)'").matcher(line);
        if (tellMatcher.find()) {
            String name = tellMatcher.group(1);
            String message = tellMatcher.group(2);
            Auctionator.auction.addBidder(new Bidder(name, message));
        }
    }
    
}
