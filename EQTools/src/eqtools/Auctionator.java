/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools;

import eqtools.view.BiddersView;
import eqtools.data.Bidder;
import eqtools.data.Player;
import eqtools.server.Magelo;
import eqtools.server.Server;
import eqtools.view.ItemView;
import eqtools.view.ScraperView;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

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
        
        Server.patchFiles();
        
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
        
        if (Config.hasConfig(Config.LAST_CHANNEL_USED)) {
            txtChannel.setText(Config.getConfig(Config.LAST_CHANNEL_USED));
        }

        new Thread() {
            @Override
            public void run() {
                for (Player player : PlayerInfo.players.values()) {
                    if (player.isAlt()) continue;
                    if (player.isUnknown()) continue;

                    Magelo.getProfile(player.getName());
                }
                
                for (Player player : PlayerInfo.players.values()) {
                    if (player.isAlt()) continue;
                    if (player.isUnknown()) continue;
                    
                    Magelo.getProfile(player.getName(), true);
                }
            }
        }.start();
        
        Auction auction = new Auction("Parogressio", 1);
        auction.addBidder(new Bidder("Anza", "Test"));
        Scraper.auctions.add(auction);
        
        Auction auction2 = new Auction("Parogressio", 1);
        auction2.addBidder(new Bidder("Thalas", "Test"));
        Scraper.auctions.add(auction2);
        
        Auction auction3 = new Auction("Sharpened Scale of Emollious", 1);
        auction3.addBidder(new Bidder("Thalas", "Test"));
        Scraper.auctions.add(auction3);
        
        Auction auction4 = new Auction("Parogressio", 1);
        auction4.addBidder(new Bidder("Thalas", "Test"));
        Scraper.auctions.add(auction4);
    }
    
    public void step() {
        if (logReader != null) {
            try {
                logReader.parseNext();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        int selectedIndex = lstAuctions.getSelectedIndex();
        DefaultListModel listModel = new DefaultListModel();
        for (int i = 0; i < Scraper.auctions.size(); i++) {
            String item = Scraper.auctions.get(i).getItem();
            if (Scraper.auctions.get(i).getQuantity() > 1) {
                item += " x" + Scraper.auctions.get(i).getQuantity();
            }
            
            listModel.addElement(item);
        }
        lstAuctions.setModel(listModel);
        lstAuctions.setSelectedIndex(selectedIndex);
        
        if (Scraper.getSelectedAuction() != null) {
            lblItemType.setText(Scraper.getSelectedAuction().getItemType());
        }
        
        
        validate();
        repaint();
        
        if (pnlBidders != null) {
            pnlBidders.repaint();
        }
    }
    
    public void setVersionText(String text) {
        txtVersion.setText(text);
    }
    
    public String getItemType() {
        return lblItemType.getText();
    }
    
    public void viewItems(String[] items) {
        ((ItemView)pnlItemViewer).viewItem(items[0]);
        if (items.length > 1) {
            ((ItemView)pnlItemViewer2).viewItem(items[1]);
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        btnStartNewAuction = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();
        pnlBidders = new BiddersView();
        btnSendToEQ = new javax.swing.JButton();
        txtVersion = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtChannel = new javax.swing.JTextField();
        boxSortBy = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstAuctions = new javax.swing.JList();
        pnlScraper = new ScraperView();
        lblItemType = new javax.swing.JLabel();
        pnlItemViewer2 = new ItemView();
        pnlItemViewer = new ItemView();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnuLoadLogFile = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mnuAddBidder = new javax.swing.JMenuItem();
        mnuAddBidders = new javax.swing.JMenuItem();
        mnuAddEveryone = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mnuAddScraper = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnStartNewAuction.setText("Start New Auction");
        btnStartNewAuction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartNewAuctionActionPerformed(evt);
            }
        });

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
            .addGap(0, 692, Short.MAX_VALUE)
        );

        btnSendToEQ.setText("Send To EQ");
        btnSendToEQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendToEQActionPerformed(evt);
            }
        });

        txtVersion.setText("Checking Version ...");

        jLabel1.setText("Use Chat Channel");

        txtChannel.setText("/2 ");
        txtChannel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtChannelActionPerformed(evt);
            }
        });

        boxSortBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Score", "Tells Received" }));

        jLabel2.setText("Sort By:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnStartNewAuction, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(13, 13, 13))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pnlBidders, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(boxSortBy, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtVersion))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtChannel, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(63, 63, 63)
                        .addComponent(btnSendToEQ, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStartNewAuction, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlBidders, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(boxSortBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtChannel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtVersion)))
                    .addComponent(btnSendToEQ, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Bidders", jPanel1);

        lstAuctions.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstAuctions.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstAuctionsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstAuctions);

        javax.swing.GroupLayout pnlScraperLayout = new javax.swing.GroupLayout(pnlScraper);
        pnlScraper.setLayout(pnlScraperLayout);
        pnlScraperLayout.setHorizontalGroup(
            pnlScraperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlScraperLayout.setVerticalGroup(
            pnlScraperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblItemType.setText("Select an Auction");

        pnlItemViewer2.setText(" ");

        pnlItemViewer.setText(" ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblItemType, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 54, Short.MAX_VALUE))
                    .addComponent(pnlScraper, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(pnlItemViewer, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addComponent(pnlItemViewer2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(2, 2, 2))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblItemType)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlScraper, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(pnlItemViewer, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                        .addGap(3, 3, 3)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlItemViewer2, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Scraper", jPanel2);

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

        mnuAddBidder.setText("Add Bidder ...");
        mnuAddBidder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddBidderActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAddBidder);

        mnuAddBidders.setText("Add Bidders");
        mnuAddBidders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddBiddersActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAddBidders);

        mnuAddEveryone.setText("Add Everyone");
        mnuAddEveryone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddEveryoneActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAddEveryone);
        jMenu2.add(jSeparator3);

        mnuAddScraper.setText("Add Scraper");
        mnuAddScraper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddScraperActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAddScraper);
        jMenu2.add(jSeparator2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartNewAuctionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartNewAuctionActionPerformed
        // Must have loaded a log before starting auction
        if (logReader == null) {
            lblStatus.setText("Failed to start auction. Load a Log File first! (File -> Load Log)");
            return;
        }
        
        Server.patchFiles();
        
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
            File possibleEQ = new File("C:\\Users\\Public\\Daybreak Game Company\\Installed Games\\EverQuest\\Logs");
            if (possibleEQ.exists()) {
                chooser = new JFileChooser(possibleEQ);
            } else {
                chooser = new JFileChooser();
            }
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
                if (player.getName().equalsIgnoreCase("Fadoram")) {
                    text += bidder.name + "[#] ";
                    continue;
                }
                if (player.isUnknown()) {
                    text += bidder.name + "[?] ";
                } else {
                    text += bidder.name + "[" + bidder.score() + player.printLastLootedMarker() + "] ";
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

        if (text.length() > 400) {
            text = text.substring(0, 400);
        }
        
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, null);
        
        lblStatus.setText("Copied to Clipboard.");
        auction.closeAuction();
        
        // Update config to use the given channel next time app loads
        Config.setConfig(Config.LAST_CHANNEL_USED, txtChannel.getText());
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

    private void mnuAddBidderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAddBidderActionPerformed
        String s = (String)JOptionPane.showInputDialog(
                    this,
                    "What is the name of the bidder?",
                    "Add Bidder",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "NoName");
        
        if (s != null) {
            // User didn't click cancel
            auction.addBidder(new Bidder(Utils.ucfirst(s), "Added Bidder"));
        }
    }//GEN-LAST:event_mnuAddBidderActionPerformed

    private void lstAuctionsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstAuctionsValueChanged
        Scraper.selectAuction(lstAuctions.getSelectedIndex());
        
        ScraperView.selectedIndex = -1;
        ((ItemView)pnlItemViewer2).setText("");
    }//GEN-LAST:event_lstAuctionsValueChanged

    private void mnuAddEveryoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAddEveryoneActionPerformed
        auction.addBidder(new Bidder("Bashhum", "Test"));
        auction.addBidder(new Bidder("Koramor", "Test"));
        auction.addBidder(new Bidder("Balthier", "Test"));
        auction.addBidder(new Bidder("Fadoram", "Test"));
        auction.addBidder(new Bidder("Action", "Test"));
        auction.addBidder(new Bidder("Skars", "Test"));
        auction.addBidder(new Bidder("Keltiklen", "Test"));
        auction.addBidder(new Bidder("Ruarik", "Test"));
        auction.addBidder(new Bidder("Olarien", "Test"));
        auction.addBidder(new Bidder("Madstomper", "Test"));
        auction.addBidder(new Bidder("Arukus", "Test"));
        auction.addBidder(new Bidder("Catness", "Test"));
        auction.addBidder(new Bidder("Salilye", "Test"));
        auction.addBidder(new Bidder("Shootmx", "Test"));
        auction.addBidder(new Bidder("Wahzoo", "Test"));
        auction.addBidder(new Bidder("Velos", "Test"));
        auction.addBidder(new Bidder("Zerowaphex", "Test"));
        auction.addBidder(new Bidder("Keldivar", "Test"));
        auction.addBidder(new Bidder("Singorr", "Test"));
        auction.addBidder(new Bidder("Ilyanir", "Test"));
        auction.addBidder(new Bidder("Cazzin", "Test"));
        auction.addBidder(new Bidder("Liosis", "Test"));
        auction.addBidder(new Bidder("Batevil", "Test"));
        auction.addBidder(new Bidder("Zzot", "Test"));
        auction.addBidder(new Bidder("Minx", "Test"));
        auction.addBidder(new Bidder("Kalyca", "Test"));
        auction.addBidder(new Bidder("Sjofn", "Test"));
        auction.addBidder(new Bidder("Calloneous", "Test"));
        auction.addBidder(new Bidder("Nataheln", "Test"));
        auction.addBidder(new Bidder("Rengurdar", "Test"));
        auction.addBidder(new Bidder("Syreena", "Test"));
        auction.addBidder(new Bidder("Idaen", "Test"));
        auction.addBidder(new Bidder("Quay", "Test"));
        auction.addBidder(new Bidder("Malvolio", "Test"));
        auction.addBidder(new Bidder("Eldros", "Test"));
        auction.addBidder(new Bidder("Shawnteria", "Test"));
        auction.addBidder(new Bidder("Osirys", "Test"));
        auction.addBidder(new Bidder("Kanjin", "Test"));
        auction.addBidder(new Bidder("Vitriol", "Test"));
        auction.addBidder(new Bidder("Kurrie", "Test"));
        auction.addBidder(new Bidder("Wenya", "Test"));
        auction.addBidder(new Bidder("Anza", "Test"));
        auction.addBidder(new Bidder("Starchild", "Test"));
        auction.addBidder(new Bidder("Methauros", "Test"));
        auction.addBidder(new Bidder("Darom", "Test"));
        auction.addBidder(new Bidder("Shierahall", "Test"));
        auction.addBidder(new Bidder("Chewi", "Test"));
        auction.addBidder(new Bidder("Shaymous", "Test"));
        auction.addBidder(new Bidder("Ninn", "Test"));
        auction.addBidder(new Bidder("Moak", "Test"));
        auction.addBidder(new Bidder("Bigganz", "Test"));
        auction.addBidder(new Bidder("Daigeh", "Test"));
        auction.addBidder(new Bidder("Litlex", "Test"));
        auction.addBidder(new Bidder("Tseuq", "Test"));
        auction.addBidder(new Bidder("Hhrolf", "Test"));
        auction.addBidder(new Bidder("Arillia", "Test"));
        auction.addBidder(new Bidder("Uaneldar", "Test"));
        auction.addBidder(new Bidder("Oshu", "Test"));
        auction.addBidder(new Bidder("Squashbettle", "Test"));
        auction.addBidder(new Bidder("Maggotmonger", "Test"));
        auction.addBidder(new Bidder("Ilsidri", "Test"));
        auction.addBidder(new Bidder("Georgeo", "Test"));
        auction.addBidder(new Bidder("Latrezis", "Test"));
        auction.addBidder(new Bidder("Tiogajoe", "Test"));
        auction.addBidder(new Bidder("Rorszale", "Test"));
        auction.addBidder(new Bidder("Veanda", "Test"));
        auction.addBidder(new Bidder("Acruxx", "Test"));
        auction.addBidder(new Bidder("Thalas", "Test"));
        auction.addBidder(new Bidder("Sargtlin", "Test"));
        auction.addBidder(new Bidder("Siouxe", "Test"));
        auction.addBidder(new Bidder("Gotjja", "Test"));
        auction.addBidder(new Bidder("Atlans", "Test"));
        auction.addBidder(new Bidder("Lorewood", "Test"));
        auction.addBidder(new Bidder("Dunkn", "Test"));
        auction.addBidder(new Bidder("Foresst", "Test"));
        auction.addBidder(new Bidder("Furryface", "Test"));
        auction.addBidder(new Bidder("Alore", "Test"));
        auction.addBidder(new Bidder("Zaibez", "Test"));
        auction.addBidder(new Bidder("Lekri", "Test"));
    }//GEN-LAST:event_mnuAddEveryoneActionPerformed

    private void mnuAddScraperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAddScraperActionPerformed
        Auction auction = new Auction("Parogressio", 1);
        auction.addBidder(new Bidder("Bashhum", "Test"));
        auction.addBidder(new Bidder("Koramor", "Test"));
        auction.addBidder(new Bidder("Balthier", "Test"));
        auction.addBidder(new Bidder("Fadoram", "Test"));
        auction.addBidder(new Bidder("Action", "Test"));
        auction.addBidder(new Bidder("Skars", "Test"));
        auction.addBidder(new Bidder("Keltiklen", "Test"));
        auction.addBidder(new Bidder("Ruarik", "Test"));
        auction.addBidder(new Bidder("Olarien", "Test"));
        auction.addBidder(new Bidder("Madstomper", "Test"));
        auction.addBidder(new Bidder("Arukus", "Test"));
        auction.addBidder(new Bidder("Catness", "Test"));
        auction.addBidder(new Bidder("Salilye", "Test"));
        auction.addBidder(new Bidder("Shootmx", "Test"));
        auction.addBidder(new Bidder("Wahzoo", "Test"));
        auction.addBidder(new Bidder("Velos", "Test"));
        auction.addBidder(new Bidder("Zerowaphex", "Test"));
        auction.addBidder(new Bidder("Keldivar", "Test"));
        auction.addBidder(new Bidder("Singorr", "Test"));
        auction.addBidder(new Bidder("Ilyanir", "Test"));
        auction.addBidder(new Bidder("Cazzin", "Test"));
        auction.addBidder(new Bidder("Liosis", "Test"));
        auction.addBidder(new Bidder("Batevil", "Test"));
        auction.addBidder(new Bidder("Zzot", "Test"));
        auction.addBidder(new Bidder("Minx", "Test"));
        auction.addBidder(new Bidder("Kalyca", "Test"));
        auction.addBidder(new Bidder("Sjofn", "Test"));
        auction.addBidder(new Bidder("Calloneous", "Test"));
        auction.addBidder(new Bidder("Nataheln", "Test"));
        auction.addBidder(new Bidder("Rengurdar", "Test"));
        auction.addBidder(new Bidder("Syreena", "Test"));
        auction.addBidder(new Bidder("Idaen", "Test"));
        auction.addBidder(new Bidder("Quay", "Test"));
        auction.addBidder(new Bidder("Malvolio", "Test"));
        auction.addBidder(new Bidder("Eldros", "Test"));
        auction.addBidder(new Bidder("Shawnteria", "Test"));
        auction.addBidder(new Bidder("Osirys", "Test"));
        auction.addBidder(new Bidder("Kanjin", "Test"));
        auction.addBidder(new Bidder("Vitriol", "Test"));
        auction.addBidder(new Bidder("Kurrie", "Test"));
        auction.addBidder(new Bidder("Wenya", "Test"));
        auction.addBidder(new Bidder("Anza", "Test"));
        auction.addBidder(new Bidder("Starchild", "Test"));
        auction.addBidder(new Bidder("Methauros", "Test"));
        auction.addBidder(new Bidder("Darom", "Test"));
        auction.addBidder(new Bidder("Shierahall", "Test"));
        auction.addBidder(new Bidder("Chewi", "Test"));
        auction.addBidder(new Bidder("Shaymous", "Test"));
        auction.addBidder(new Bidder("Ninn", "Test"));
        auction.addBidder(new Bidder("Moak", "Test"));
        auction.addBidder(new Bidder("Bigganz", "Test"));
        auction.addBidder(new Bidder("Daigeh", "Test"));
        auction.addBidder(new Bidder("Litlex", "Test"));
        auction.addBidder(new Bidder("Tseuq", "Test"));
        auction.addBidder(new Bidder("Hhrolf", "Test"));
        auction.addBidder(new Bidder("Arillia", "Test"));
        auction.addBidder(new Bidder("Uaneldar", "Test"));
        auction.addBidder(new Bidder("Oshu", "Test"));
        auction.addBidder(new Bidder("Squashbettle", "Test"));
        auction.addBidder(new Bidder("Maggotmonger", "Test"));
        auction.addBidder(new Bidder("Ilsidri", "Test"));
        auction.addBidder(new Bidder("Georgeo", "Test"));
        auction.addBidder(new Bidder("Latrezis", "Test"));
        auction.addBidder(new Bidder("Tiogajoe", "Test"));
        auction.addBidder(new Bidder("Rorszale", "Test"));
        auction.addBidder(new Bidder("Veanda", "Test"));
        auction.addBidder(new Bidder("Acruxx", "Test"));
        auction.addBidder(new Bidder("Thalas", "Test"));
        auction.addBidder(new Bidder("Sargtlin", "Test"));
        auction.addBidder(new Bidder("Siouxe", "Test"));
        auction.addBidder(new Bidder("Gotjja", "Test"));
        auction.addBidder(new Bidder("Atlans", "Test"));
        auction.addBidder(new Bidder("Lorewood", "Test"));
        auction.addBidder(new Bidder("Dunkn", "Test"));
        auction.addBidder(new Bidder("Foresst", "Test"));
        auction.addBidder(new Bidder("Furryface", "Test"));
        auction.addBidder(new Bidder("Alore", "Test"));
        auction.addBidder(new Bidder("Zaibez", "Test"));
        auction.addBidder(new Bidder("Lekri", "Test"));
        Scraper.auctions.add(auction);
    }//GEN-LAST:event_mnuAddScraperActionPerformed

    public String getSortBy() {
        return (String)boxSortBy.getSelectedItem();
    }
    
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
    private javax.swing.JComboBox boxSortBy;
    private javax.swing.JButton btnSendToEQ;
    private javax.swing.JButton btnStartNewAuction;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblItemType;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JList lstAuctions;
    private javax.swing.JMenuItem mnuAddBidder;
    private javax.swing.JMenuItem mnuAddBidders;
    private javax.swing.JMenuItem mnuAddEveryone;
    private javax.swing.JMenuItem mnuAddScraper;
    private javax.swing.JMenuItem mnuLoadLogFile;
    private javax.swing.JPanel pnlBidders;
    private javax.swing.JLabel pnlItemViewer;
    private javax.swing.JLabel pnlItemViewer2;
    private javax.swing.JPanel pnlScraper;
    private javax.swing.JTextField txtChannel;
    private javax.swing.JLabel txtVersion;
    // End of variables declaration//GEN-END:variables
}
