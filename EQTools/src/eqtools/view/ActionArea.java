/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqtools.view;

import java.awt.Rectangle;

/**
 *
 * @author AaronServer
 */
public abstract class ActionArea {
    public Rectangle area;
    protected boolean isDeleted = false;
    
    public void setArea(Rectangle rect) {
        area = rect;
    }
    
    public boolean isDeleted() {
        return isDeleted;
    }
    
    public abstract void mouseClicked();
    public abstract void mouseOver();
}
