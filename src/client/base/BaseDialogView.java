/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.base;

import client.general.MainView;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 *
 * @author Alonso
 */
public abstract class BaseDialogView extends javax.swing.JDialog{
    protected Border errorBorder = BorderFactory.createLineBorder(Color.RED, 1);
    protected Border regularBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
    protected String error_message;
    
    public BaseDialogView(java.awt.Frame parent, boolean modal){
        super(parent, modal);
    }
    
    protected void initialize(){
        Dimension desktopSize = MainView.desktopPane.getSize();
        Dimension jInternalFrameSize = this.getSize();
        this.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
        (desktopSize.height- jInternalFrameSize.height)/2);
    }
    
}
