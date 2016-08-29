/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public void stopParsing() {
        keepAlive = false;
    }
    
    private void consumeFile() {
        try {
            logReader.skip(logFile.length());
        } catch (IOException ex) {
            Logger.getLogger(LogParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void parseLine(String line) {
        parseDeaths(line);
    }
    
    
    
    private void parseDeaths(String line) {
        if (line.contains("You have been slain by ")) {
            System.out.println(characterName + " died.");
        }
    }
}
