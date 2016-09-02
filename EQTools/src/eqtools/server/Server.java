/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools.server;

import eqtools.PlayerInfo;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 *
 * @author AaronServer
 */
public class Server {
    private final static String RESOURCE_SERVER = "http://meteorice.com/resources/";
    private final static String[] patchedFiles = {
        "data.txt",
        "guild.txt"
    };
    
    public static void patchFiles() {
        // Spawn a new thread to patch the files in the background
        new Thread(){
            @Override
            public void run() {
                for (String filename : patchedFiles) {
                    downloadFile(RESOURCE_SERVER + filename, new File(filename));
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
}
