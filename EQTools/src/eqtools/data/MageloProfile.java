/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author AaronServer
 */
public class MageloProfile {

    public static enum Slot {
        Charm, Left_Ear, Head, Face, Right_Ear, Neck, Shoulders, Arms,
        Back, Left_Wrist, Right_Wrist, Range, Hands, Primary, Secondary,
        Left_Ring, Right_Ring, Chest, Legs, Feet, Waist, Power_Source, Ammo
    };
    
    public String name;
    public String profile;
    public Date lastUpdated;
    public String[] equipment = new String[Slot.values().length];
    
    public String[] getItemsForSlot(String type) {
        type = type.toLowerCase();
        ArrayList<String> items = new ArrayList<>();
        
        if (type.contains("charm")) items.add(equipment[Slot.Charm.ordinal()]);
        if (type.contains("ear")) {items.add(equipment[Slot.Left_Ear.ordinal()]); items.add(equipment[Slot.Right_Ear.ordinal()]);};
        if (type.contains("head")) items.add(equipment[Slot.Head.ordinal()]);
        if (type.contains("face")) items.add(equipment[Slot.Face.ordinal()]);
        if (type.contains("neck")) items.add(equipment[Slot.Neck.ordinal()]);
        if (type.contains("shoulders")) items.add(equipment[Slot.Shoulders.ordinal()]);
        if (type.contains("arms")) items.add(equipment[Slot.Arms.ordinal()]);
        if (type.contains("back")) items.add(equipment[Slot.Back.ordinal()]);
        if (type.contains("wrist")) {items.add(equipment[Slot.Left_Wrist.ordinal()]); items.add(equipment[Slot.Right_Wrist.ordinal()]);};
        if (type.contains("range")) items.add(equipment[Slot.Range.ordinal()]);
        if (type.contains("hands")) items.add(equipment[Slot.Hands.ordinal()]);
        if (type.contains("primary")) items.add(equipment[Slot.Primary.ordinal()]);
        if (type.contains("secondary")) items.add(equipment[Slot.Secondary.ordinal()]);
        if (type.contains("fingers")) {items.add(equipment[Slot.Left_Ring.ordinal()]); items.add(equipment[Slot.Right_Ring.ordinal()]);};
        if (type.contains("chest")) items.add(equipment[Slot.Chest.ordinal()]);
        if (type.contains("legs")) items.add(equipment[Slot.Legs.ordinal()]);
        if (type.contains("feet")) items.add(equipment[Slot.Feet.ordinal()]);
        if (type.contains("waist")) items.add(equipment[Slot.Waist.ordinal()]);
        if (type.contains("power source")) items.add(equipment[Slot.Power_Source.ordinal()]);
        if (type.contains("ammo")) items.add(equipment[Slot.Ammo.ordinal()]);
        
        return items.toArray(new String[0]);
    }
    
    public void downloadProfileFromMagelo() {
        URL url;
        if (profile == null) {
            try {
                url = new URL("http://eq.magelo.com/ranking?gn=Circle%20of%20Legends&s=24&f=h73_0_0");
                
                try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        if (line.contains(name)) {
                            line = line.substring(line.indexOf(name) - 40);
                            
                            line = line.substring(line.indexOf("eq:profile:") + "eq:profile:".length());
                            line = line.substring(0, line.indexOf("\""));
                            
                            profile = line;
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        try {
            url = new URL("http://eq.magelo.com/profile/" + profile);
            
            try (BufferedReader in = new BufferedReader(new InputStreamReader(new GZIPInputStream(url.openStream())))) {
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.contains("items[")) {
                        String[] items = line.split("items\\[");
                        for (String item : items) {
                            if (item.contains("]")) {
                                int position = Integer.parseInt(item.substring(0, item.indexOf("]")));

                                if (position < 22) {
                                    equipment[position] = item.substring(item.indexOf("Item("));
                                }
                            }
                        }
                    }
                }
                
                lastUpdated = new Date();
            } catch (Exception e) {
                e.printStackTrace();
            }   
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String toString() {
        String s = "";
        s += name + "\t";
        s += profile + "\t";
        s += lastUpdated.toString() + "\t";
        for (String item : equipment) {
            s += item + "\t";
        }
        return s;
    }
}
