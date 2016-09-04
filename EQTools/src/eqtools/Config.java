/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author AaronServer
 */
public class Config {
    public final static String LAST_LOADED_LOG = "last_loaded_log";
    public final static String LAST_LOADED_LOG_FILE = "last_loaded_log_file";
    public final static String DICE_WEIGHT = "dice_weight";
    public final static String LAST_CHANNEL_USED = "last_channel_used";
    
    private final static HashMap<String, String> properties = new HashMap<>();
    
    static {
        File configFile = new File("config.txt");
        if (configFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
                Pattern configPattern = Pattern.compile("([^:]*)\\:(.*)");

                String line;
                while ((line = reader.readLine()) != null) {
                    Matcher matcher = configPattern.matcher(line);
                    if (matcher.find()) {
                        String name = matcher.group(1);
                        String value = matcher.group(2);
                        properties.put(name, value);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void saveConfig() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("config.txt"))) {
            String[] keys = properties.keySet().toArray(new String[0]);
            
            for (String key : keys) {
                String value = properties.get(key);
                writer.println(key + ":" + value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void setConfig(String key, String value) {
        properties.put(key, value);
        saveConfig();
    }
    
    public static String getConfig(String key) {
        return properties.get(key);
    }
    
    public static boolean hasConfig(String key) {
        return properties.containsKey(key);
    }
}
