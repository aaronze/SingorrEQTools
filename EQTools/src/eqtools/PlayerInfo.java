/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools;

import eqtools.data.Player;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/**
 *
 * @author AaronServer
 */
public class PlayerInfo {
    public final static HashMap<String, Player> players = new HashMap();
    
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
                String altFlag = tokens[4];
                String comment = tokens[7];
                
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
