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
import util.Icons;

/**
 *
 * @author Alonso
 */
public abstract class BaseView extends javax.swing.JInternalFrame {
    protected Border errorBorder = BorderFactory.createLineBorder(Color.RED, 1);
    protected Border regularBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
    protected String error_message;

    
    protected void initialize(){
        Dimension desktopSize = MainView.desktopPane.getSize();
        Dimension jInternalFrameSize = this.getSize();
        this.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
        (desktopSize.height- jInternalFrameSize.height)/2 - 20);
        Icons.setMainIcon(this);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        
        
    }
    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt){
            this.dispose();
        }
    
}
