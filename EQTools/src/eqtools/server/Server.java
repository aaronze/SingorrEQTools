/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools.server;

import eqtools.PlayerInfo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author AaronServer
 */
public class Server {
    private final static String SERVER = "https://storms.000webhostapp.com/";
    private final static String[] patchedFiles = {
        "data.txt",
        "guild.txt",
        "version.txt"
    };
    public final static String SERVER_DATE;
    public static String lootedString = "";
    
    static {
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        SERVER_DATE = dateFormatGmt.format(new Date());
    }
    
    public static void patchFiles() {
        // Spawn a new thread to patch the files in the background
        new Thread(){
            @Override
            public void run() {
                lootedString = getLoot();
                
                for (String filename : patchedFiles) {
                    downloadFile(SERVER + "resources/" + filename, new File(filename));
                }
                
                // After we have downloaded the new files, reload the player info to reflect the new changes
                PlayerInfo.reload();
            }
        }.start();
    }
    
    /**
     * Downloads a file (Small files only, need to iterate transferFrom for bigger files)
     * And saves it to the given local path
     * 
     * @param url
     * @param path 
     */
    public static void downloadFile(String url, File path) {
        String tempPath = "tmp_" + path.getPath();
        
        try {
            // Attempt to download the file
            URL website = new URL(url);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            
            FileOutputStream fos = new FileOutputStream(tempPath);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            
            if (path.exists()) {
                path.delete();
            }
            
            File tempFile = new File(tempPath);
            tempFile.renameTo(path);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void sendLoot(String player, String item) {
        try {
            String param = URLEncoder.encode(player + "\t" + SERVER_DATE + "\t" + item, "UTF-8");
            System.out.println(ajax(SERVER + "add-loot.php?loot=" + param));
            
            lootedString = getLoot();
            PlayerInfo.reload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String getLoot() {
        try {
            return ajax(SERVER + "get-looted.php");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    /**
     * @param urlString
     * @return 
     * @throws MalformedURLException
     * @throws IOException 
     */
    public static String ajax(String urlString) throws MalformedURLException, IOException {
        final URL url = new URL(urlString);
        final URLConnection urlConnection = url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        urlConnection.connect();
        
        String output = "";
        final InputStream inputStream = urlConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while ((line = reader.readLine()) != null) {
            output += line + "\n";
        }
        reader.close();
        
        return output;
    }
}
