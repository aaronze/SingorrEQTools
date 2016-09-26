/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools.server;

import eqtools.data.MageloProfile;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author AaronServer
 */
public class Magelo {
    private static HashMap<String, MageloProfile> profiles = new HashMap<>();
    
    static {
        loadFromFile();
    }
    
    public static void addProfile(MageloProfile profile) {
        profiles.put(profile.name, profile);
    }
    
    public static void writeToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File("magelo.txt")))) {
            for (MageloProfile profile : profiles.values()) {
                writer.println(profile.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("magelo.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\t");
                MageloProfile profile = new MageloProfile();
                profile.name = tokens[0];
                profile.profile = tokens[1];
                profile.lastUpdated = new Date();
                
                int item = 0;
                while (item+3 < tokens.length) {
                    profile.equipment[item] = tokens[item+3];
                    item++;
                }
                
                addProfile(profile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String getItemType(String item) {
        try {
            URL url = new URL("http://eq.magelo.com/items?q=" + URLEncoder.encode(item, "UTF-8") + "&k=90");

            try (BufferedReader in = new BufferedReader(new InputStreamReader(new GZIPInputStream(url.openStream())))) {
                item = item.replaceAll("'", "\\\\'");
                
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.contains(item) && line.contains("var data=")) {
                        line = line.substring(line.indexOf(item));
                        line = line.substring(line.indexOf("["));
                        line = line.substring(line.indexOf("\"") + 1);
                        line = line.substring(0, line.indexOf("\""));
                        
                        return line;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }   
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "Unknown";
    }
    
    public static MageloProfile getProfile(String name) {
        return getProfile(name, false);
    }
    
    public static MageloProfile getProfile(String name, boolean forceLatest) {
        MageloProfile profile = new MageloProfile();
        profile.name = name;
        
        if (profiles.containsKey(name)) {
            if (!forceLatest) {
                return profiles.get(name);
            }
            profile = getProfile(name);
        }

        profile.downloadProfileFromMagelo();
        
        if (!profiles.containsKey(name)) {
            addProfile(profile);
        }
        
        writeToFile();
        
        return profile;
    }
}
