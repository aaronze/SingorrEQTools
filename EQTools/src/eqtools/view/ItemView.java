/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools.view;

import javax.swing.JLabel;
import javax.swing.JTextPane;

/**
 *
 * @author AaronServer
 */
public class ItemView extends JLabel {
    public ItemView() {
        
    }
    
    public ItemView(String item) {
        viewItem(item);
    }
    
    public void viewItem(String item) {
        item = item.replaceAll("\\\\\\>", ">");
        item = item.replaceAll("\\\\\\'", "'");
        item = item.replaceAll("\\\\\\\"", "\"");
        
        System.out.println(item);

        String html = item.substring(item.indexOf("'<div") + 1);
        html = html.substring(0, html.indexOf("div>'"));
        
        System.out.println(html);

        this.setText("<html>" + html);
    }
}
