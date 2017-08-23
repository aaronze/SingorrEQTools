/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools;

import eqtools.data.Bidder;
import eqtools.server.Server;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
    private static File logFile;
    private static String characterName;
   
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
        parseScraper(line);
        
        if (Auctionator.isRaidBot) {
            parseRaidbot(line);
        }
        
        parseCLI(line);
    }
    
    private void parseAuction(String line) {
        Matcher tellWindowMatcher = Pattern.compile("\\[.*\\] (.*) \\-\\> .*: (.*)").matcher(line);
        if (tellWindowMatcher.find()) {
            String name = tellWindowMatcher.group(1);
            String message = tellWindowMatcher.group(2);
            
            // Ensure person auctioning sending tells and using tell windows
            // does not accidentally add themselves to the bidding pool
            if (!name.equalsIgnoreCase(Auctionator.logReader.getCharacterName())) {
                Auctionator.auction.addBidder(new Bidder(name, message));
            }
        }
        
        Matcher tellMatcher = Pattern.compile("\\[.*\\] (.*) tells you, '(.*)'").matcher(line);
        if (tellMatcher.find()) {
            String name = tellMatcher.group(1);
            String message = tellMatcher.group(2);
            
            // Ensure person auctioning sending tells and using tell windows
            // does not accidentally add themselves to the bidding pool
            if (!name.equalsIgnoreCase(Auctionator.logReader.getCharacterName())) {
                Auctionator.auction.addBidder(new Bidder(name, message));
            }
        }
        
        Matcher lootMatcher = Pattern.compile("] --(.*) has looted a (.*).--").matcher(line);
        if (lootMatcher.find()) {
            String name = lootMatcher.group(1);
            String item = lootMatcher.group(2);
            Server.sendLoot(name, item);
        }
    }
    
    private void parseScraper(String line) {
        Matcher scraperMatcher = Pattern.compile(".*tells colv2.*, '(.*)--\\s*(.*)'").matcher(line);
        if (scraperMatcher.find()) {
            String item = scraperMatcher.group(1);
            String bidders = scraperMatcher.group(2);
            
            int quantity = 1;
            
            Matcher quantityMatcher = Pattern.compile("(\\d+x)").matcher(item);
            if (quantityMatcher.find()) {
                String quantityString = quantityMatcher.group(1);
                
                item = item.replaceAll(quantityString, "");
                
                quantityString = quantityString.replaceAll("x", "");
                quantityString = quantityString.replaceAll(" ", "");
                
                quantity = Integer.parseInt(quantityString);
            }
            
            quantityMatcher = Pattern.compile("(x?\\s*\\d+)").matcher(item);
            if (quantityMatcher.find()) {
                String quantityString = quantityMatcher.group(1);
                
                item = item.replaceAll(quantityString, "");
                
                quantityString = quantityString.replaceAll("x", "");
                quantityString = quantityString.replaceAll(" ", "");
                
                quantity = Integer.parseInt(quantityString);
            }
            
            Auction auction = new Auction(item.trim(), quantity);
            
            bidders = bidders.replaceAll("[^a-zA-Z ]", "");
            for (String bidder : bidders.split(" ")) {
                auction.addBidder(new Bidder(Utils.ucfirst(bidder), "Scraper Added"));
            }
            
            Scraper.auctions.add(auction);
        }
        
        scraperMatcher = Pattern.compile(".*tell colv2.*, '(.*)--\\s*(.*)'").matcher(line);
        if (scraperMatcher.find()) {
            String item = scraperMatcher.group(1);
            String bidders = scraperMatcher.group(2);
            
            int quantity = 1;
            
            Matcher quantityMatcher = Pattern.compile("(\\d+x)").matcher(item);
            if (quantityMatcher.find()) {
                String quantityString = quantityMatcher.group(1);
                
                item = item.replaceAll(quantityString, "");
                
                quantityString = quantityString.replaceAll("x", "");
                quantityString = quantityString.replaceAll(" ", "");
                
                quantity = Integer.parseInt(quantityString);
            }
            
            quantityMatcher = Pattern.compile("(x?\\s*\\d+)").matcher(item);
            if (quantityMatcher.find()) {
                String quantityString = quantityMatcher.group(1);
                
                item = item.replaceAll(quantityString, "");
                
                quantityString = quantityString.replaceAll("x", "");
                quantityString = quantityString.replaceAll(" ", "");
                
                quantity = Integer.parseInt(quantityString);
            }
            
            Auction auction = new Auction(item.trim(), quantity);
            
            bidders = bidders.replaceAll("[^a-zA-Z ]", "");
            for (String bidder : bidders.split(" ")) {
                auction.addBidder(new Bidder(Utils.ucfirst(bidder), "Scraper Added"));
            }
            
            Scraper.auctions.add(auction);
        }
    }
    
    private void parseRaidbot(String line) {
        Matcher tellWindowMatcher = Pattern.compile("\\[.*\\] (.*) \\-\\> .*: (.*)").matcher(line);
        if (tellWindowMatcher.find()) {
            String name = tellWindowMatcher.group(1);
            String message = tellWindowMatcher.group(2);
            
            if (!name.equalsIgnoreCase(Auctionator.logReader.getCharacterName())) {
                raidinvite(name);
            }
        }
        
        Matcher tellMatcher = Pattern.compile("\\[.*\\] (.*) tells you, '(.*)'").matcher(line);
        if (tellMatcher.find()) {
            String name = tellMatcher.group(1);
            String message = tellMatcher.group(2);

            if (!name.equalsIgnoreCase(Auctionator.logReader.getCharacterName())) {
                raidinvite(name);
            }
        }
    }
    
    private void parseCLI(String line) {
        Matcher sayMatcher = Pattern.compile("\\[.*\\] You say, '\\{Raidbot\\:(.*)\\}'").matcher(line);
        if (sayMatcher.find()) {
            String message = sayMatcher.group(1);
            
            if (message.equalsIgnoreCase("start")) {
                Auctionator.instance.getRaidbotAutoInviteCheckbox().setSelected(true);
                Auctionator.isRaidBot = true;
            }
            if (message.equalsIgnoreCase("stop")) {
                Auctionator.instance.getRaidbotAutoInviteCheckbox().setSelected(false);
                Auctionator.isRaidBot = false;
            }
        }
    }
    
    ArrayList<String> responders = new ArrayList<>();
    
    private void raidinvite(String name) {
        eqsay("/raidinvite " + name);
        
        String message = Auctionator.instance.getAutoRaidInviteCustomMessage();
        
        if (!responders.contains(name) && message != null) {
            eqsay("/tell " + name + " " + message);
        }
        
        responders.add(name);
    }
    
    private void eqsay(String s) {
        StringSelection selection = new StringSelection(s);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        
        try {
            Robot robot = new Robot();
            
            robot.delay(10);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(2);
            robot.keyRelease(KeyEvent.VK_ENTER);

            robot.delay(10);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.delay(10);
            robot.keyPress(KeyEvent.VK_V);
            robot.delay(1);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);
            
            robot.delay(10);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(2);
            robot.keyRelease(KeyEvent.VK_ENTER);
            
            robot.delay(20);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String findConversationsFor(String convName) {
        String conversation = "";
        
        try (BufferedReader logReader = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = logReader.readLine()) != null) {
                Matcher tellWindowMatcher = Pattern.compile("\\[.*\\] (.*) \\-\\> (.*): (.*)").matcher(line);
                if (tellWindowMatcher.find()) {
                    String name = tellWindowMatcher.group(1);
                    String otherName = tellWindowMatcher.group(2);
                    String message = tellWindowMatcher.group(3);
                    
                    if (name.equalsIgnoreCase(convName)) {
                        conversation += name + ": " + message + "\n";
                    }
                    if (otherName.equalsIgnoreCase(convName)) {
                        conversation += characterName + ": " + message + "\n";
                    }
                }

                Matcher tellMatcher = Pattern.compile("\\[.*\\] (.*) tells you, '(.*)'").matcher(line);
                if (tellMatcher.find()) {
                    String name = tellMatcher.group(1);
                    String message = tellMatcher.group(2);

                    if (name.equalsIgnoreCase(convName)) {
                        conversation += name + ": " + message + "\n";
                    }
                }
                
                Matcher tellOtherMatcher = Pattern.compile("\\[.*\\] You told (.*), '(.*)'").matcher(line);
                if (tellOtherMatcher.find()) {
                    String name = tellOtherMatcher.group(1);
                    System.out.println(name);
                    String message = tellOtherMatcher.group(2);

                    if (name.equalsIgnoreCase(convName)) {
                        conversation += characterName + ": " + message + "\n";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return conversation;
    }
}
