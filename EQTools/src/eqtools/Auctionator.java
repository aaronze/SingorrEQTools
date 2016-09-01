/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools;

import eqtools.view.BiddersView;
import eqtools.data.Bidder;
import eqtools.data.Player;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JFileChooser;

/**
 *
 * @author AaronServer
 */
public class Auctionator extends javax.swing.JFrame {

    public static Auctionator instance;
    public static Auction auction;
    public static LogParser logReader;
    public static int DICE_WEIGHT = 0;
    
    /**
     * Creates new form Auctionator
     */
    public Auctionator() {
        instance = this;
        
        initComponents();
        
        if (Config.hasConfig(Config.DICE_WEIGHT)) {
            DICE_WEIGHT = Integer.parseInt(Config.getConfig(Config.DICE_WEIGHT));
        }
        
        // Automatically load the last loaded log file
        if (Config.hasConfig(Config.LAST_LOADED_LOG_FILE)) {
            try {
                File file = new File(Config.getConfig(Config.LAST_LOADED_LOG_FILE));
                logReader = new LogParser(file);
                logReader.consumeFile(); // Only start reading from end of log file
                
                lblStatus.setText("Log Loaded for " + logReader.getCharacterName() + "! Start a new Auction when ready.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void step() {
        if (logReader != null) {
            try {
                logReader.parseNext();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        validate();
        repaint();
        
        if (pnlBidders != null) {
            pnlBidders.repaint();
        }
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnStartNewAuction = new javax.swing.JButton();
        btnSendToEQ = new javax.swing.JButton();
        txtChannel = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        pnlBidders = new BiddersView();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnuLoadLogFile = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mnuAddBidders = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnStartNewAuction.setText("Start New Auction");
        btnStartNewAuction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartNewAuctionActionPerformed(evt);
            }
        });

        btnSendToEQ.setText("Send To EQ");
        btnSendToEQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendToEQActionPerformed(evt);
            }
        });

        txtChannel.setText("/2 ");
        txtChannel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtChannelActionPerformed(evt);
            }
        });

        jLabel1.setText("Use Chat Channel");

        lblStatus.setText("Waiting for Log File ... (File -> Load Log File)");
        lblStatus.setToolTipText("");

        javax.swing.GroupLayout pnlBiddersLayout = new javax.swing.GroupLayout(pnlBidders);
        pnlBidders.setLayout(pnlBiddersLayout);
        pnlBiddersLayout.setHorizontalGroup(
            pnlBiddersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlBiddersLayout.setVerticalGroup(
            pnlBiddersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 381, Short.MAX_VALUE)
        );

        jMenu1.setText("File");

        mnuLoadLogFile.setText("Load Log File");
        mnuLoadLogFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuLoadLogFileActionPerformed(evt);
            }
        });
        jMenu1.add(mnuLoadLogFile);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Debug");

        mnuAddBidders.setText("Add Bidders");
        mnuAddBidders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddBiddersActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAddBidders);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlBidders, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 521, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtChannel, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(63, 63, 63)
                        .addComponent(btnSendToEQ, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(btnStartNewAuction, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStartNewAuction, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBidders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtChannel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSendToEQ, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartNewAuctionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartNewAuctionActionPerformed
        // Must have loaded a log before starting auction
        if (logReader == null) {
            lblStatus.setText("Failed to start auction. Load a Log File first! (File -> Load Log)");
            return;
        }
        
        auction = new Auction();
        lblStatus.setText("Auction started. Waiting for tells ...");
    }//GEN-LAST:event_btnStartNewAuctionActionPerformed

    private void txtChannelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChannelActionPerformed
        
    }//GEN-LAST:event_txtChannelActionPerformed

    private void mnuLoadLogFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuLoadLogFileActionPerformed
        // If user has loaded a log before, start with that folder in File Chooser
        JFileChooser chooser;
        if (Config.hasConfig(Config.LAST_LOADED_LOG)) {
            chooser = new JFileChooser(new File(Config.getConfig(Config.LAST_LOADED_LOG)));
        } else {
            chooser = new JFileChooser();
        }

        int returnVal = chooser.showOpenDialog(this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();
                logReader = new LogParser(file);
                logReader.consumeFile(); // Only start reading from end of log file
                
                // Save this path for next time
                Config.setConfig(Config.LAST_LOADED_LOG, file.getParent());
                Config.setConfig(Config.LAST_LOADED_LOG_FILE, file.getPath());
                
                lblStatus.setText("Log Loaded for " + logReader.getCharacterName() + "! Start a new Auction when ready.");
            } catch (Exception e) {
                lblStatus.setText("Failed to Load Log File. Try Again?");
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_mnuLoadLogFileActionPerformed

    private void btnSendToEQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendToEQActionPerformed
        String text = txtChannel.getText() + " ";
        
        List<Bidder> list = Arrays.asList(auction.getBidders());
        Collections.sort(list, new Comparator<Bidder>() {
            @Override
            public int compare(Bidder left, Bidder right)  {
                return right.score(DICE_WEIGHT) - left.score(DICE_WEIGHT);
            }
        });

        // First pass for mains
        for (Bidder bidder : list) {
            Player player = PlayerInfo.getPlayer(bidder.name);
            if (!player.isAlt()) {
                if (player.isUnknown()) {
                    text += bidder.name + "[?] ";
                } else {
                    text += bidder.name + "[" + bidder.score() + "] ";
                }
            }
        }
 
        // Second pass for alts
        for (Bidder bidder : list) {
            Player player = PlayerInfo.getPlayer(bidder.name);
            if (player.isAlt()) {
                if (player.getMain().isUnknown()) {
                    text += bidder.name + "[? " + "- Alt of " + player.getMain().getName() + "] "; 
                } else {
                    text += bidder.name + "[" + player.getMain().score(DICE_WEIGHT) + " - Alt of " + player.getMain().getName() + "] "; 
                }
            }
        }

        StringSelection stringSelection = new StringSelection(text);
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, null);
        
        lblStatus.setText("Copied to Clipboard.");
        auction.closeAuction();
    }//GEN-LAST:event_btnSendToEQActionPerformed

    private void mnuAddBiddersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAddBiddersActionPerformed
        if (auction != null) {
            
            for (int n = 0; n < 5; n++) {
                String name = "";
                for (int i = 0; i < 8; i++) {
                    name += "abcdefghijklmnopqrstuvwxyz".charAt((int)(Math.random()*26));
                }
                
                auction.addBidder(new Bidder(name, "This is a test"));
            }
        }
    }//GEN-LAST:event_mnuAddBiddersActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Auctionator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Auctionator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Auctionator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Auctionator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Auctionator().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSendToEQ;
    private javax.swing.JButton btnStartNewAuction;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JMenuItem mnuAddBidders;
    private javax.swing.JMenuItem mnuLoadLogFile;
    private javax.swing.JPanel pnlBidders;
    private javax.swing.JTextField txtChannel;
    // End of variables declaration//GEN-END:variables
}
