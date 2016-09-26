/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools.server;

import eqtools.data.MageloProfile;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author AaronServer
 */
public class Magelo {
    private static ArrayList<MageloProfile> profiles = new ArrayList<>();
    
    public static void addProfile(MageloProfile profile) {
        profiles.add(profile);
    }
    
    public static void writeToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File("magelo.txt")))) {
            for (MageloProfile profile : profiles) {
                writer.println(profile.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String getItemType(String item) {
        try {
            URL url = new URL("http://eq.magelo.com/items?q=" + URLEncoder.encode(item, "UTF-8"));

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
        MageloProfile profile = new MageloProfile();
        profile.name = name;
        
        profile.downloadProfileFromMagelo();
        
        return profile;
    }
}
