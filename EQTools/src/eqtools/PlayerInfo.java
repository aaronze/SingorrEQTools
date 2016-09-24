/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools;

import eqtools.data.Player;
import eqtools.server.Server;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/**
 *
 * @author AaronServer
 */
public class PlayerInfo {
    public final static HashMap<String, Player> players = new HashMap();
    
    public static String CLIENT_VERSION = "1.6";
    public static String SERVER_VERSION = null;
    
    static {
        reload();
    }
    
    public static void reload() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Player player = new Player(line);
                players.put(player.getName(), player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("guild.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\t");
                String name = tokens[0];
                String rank = tokens[3];
                String altFlag = tokens[4];
                String comment = tokens[7];
                
                getPlayer(name).setRank(rank);
                
                if (name.equalsIgnoreCase("Siouxe")) continue; // Sigh.
                
                if (altFlag.equalsIgnoreCase("A")) {
                    comment = comment.replace("Alt", "");
                    comment = comment.replace("-", "");
                    comment = comment.replace("=", "");
                    comment = comment.trim();
                    
                    if (comment.length() == 0) {
                        comment = "Unknown";
                    } else {
                        comment = comment.toLowerCase();
                        comment = comment.substring(0, 1).toUpperCase() + comment.substring(1);
                    }
                    
                    Player main = getPlayer(comment);
                    getPlayer(name).setAltOf(main);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader("version.txt"))) {
            SERVER_VERSION = reader.readLine();
            
            if (SERVER_VERSION.equalsIgnoreCase(CLIENT_VERSION)) {
                Auctionator.instance.setVersionText("You are using the latest version.");
            } else {
                Auctionator.instance.setVersionText("A new version of Singorr's Toolkit is available! Go to meteorice.com to download.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Auctionator.instance.setVersionText("Runtime Error! Windows might be protecting this folder from me creating files. Try moving the jar somewhere else.");
        }
        
        try {
            String[] loots = Server.lootedString.split("\n");
            
            for (String loot : loots) {
                String[] tokens = loot.split("\t");
                
                String name = tokens[0];
                String date = tokens[1];
                String item = tokens[2];
                
                getPlayer(name).setLastLootedItem(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds the information for the given player's name.
     *
     * If the player could not be found, a skeleton player with that name
     * will be created and returned instead. It is very unlikely this person
     * will win any loot, but this is great for cases where an Alt/Box bids on
     * an item that would otherwise rot.
     *
     * ALWAYS returns a valid Player - even if a Player with that name is not found!
     *
     * @param name
     * @return
     */
    public static Player getPlayer(String name) {
        if (players.containsKey(name)) {
            return players.get(name);
        }

        Player player = new Player();
        player.setName(name);
        players.put(name, player);
        return player;
    }
}
