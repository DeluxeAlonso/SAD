/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.general;

import client.product.ProductView;
import client.reports.RemissionGuideReport;
import client.reports.StockReport;
import client.report.ProductCaducityReport;
import client.transportunit.*;
import client.user.EditUserView;
import client.user.UserView;
import client.warehouse.PalletMovementsView;
import client.warehouse.WarehouseView;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Nevermade
 */
public class MainView extends javax.swing.JFrame {

    private UserView userView = null;
    private TransportUnitView transportUnitView = null;
    private WarehouseView warehouseView = null;
    private ProductView productView = null;
    private EditUserView editUserView = null;
    private PalletMovementsView palletMovementsView = null;
    private ProductCaducityReport productCaducity=null;
    private RemissionGuideReport remissionGuide=null;
    private StockReport stockReport = null;

    /**
     * Creates new form MainForm
     */
    public MainView() {
        initComponents();
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        WarehouseMenu = new javax.swing.JMenuItem();
        TUFrame = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        UserMenu = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Almacenes y Despacho");

        mainPanel.setDragMode(javax.swing.JDesktopPane.OUTLINE_DRAG_MODE);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 416, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 281, Short.MAX_VALUE)
        );

        jMenu2.setText("Movimientos");

        jMenuItem2.setText("Internamiento");
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("Pedido");
        jMenu2.add(jMenuItem3);

        jMenuItem6.setText("Movimientos Pallets");
        jMenuItem6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem6MousePressed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Operaciones");

        jMenuItem1.setText("Ajuste De Inventario");
        jMenu3.add(jMenuItem1);

        jMenuItem4.setText("Despacho");
        jMenu3.add(jMenuItem4);

        jMenuItem11.setText("Devoluciones");
        jMenu3.add(jMenuItem11);

        jMenuBar1.add(jMenu3);

        jMenu5.setText("Mantenimientos");
        jMenu5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu5MouseClicked(evt);
            }
        });

        WarehouseMenu.setText("Almacén");
        WarehouseMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                WarehouseMenuMousePressed(evt);
            }
        });
        jMenu5.add(WarehouseMenu);

        TUFrame.setText("Unidad de Transporte");
        TUFrame.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TUFrameMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TUFrameMousePressed(evt);
            }
        });
        jMenu5.add(TUFrame);

        jMenuBar1.add(jMenu5);

        jMenu1.setText("Reportes");

        jMenuItem12.setText("Kardex");
        jMenu1.add(jMenuItem12);

        jMenuItem13.setText("Stock");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem13);

        jMenuItem14.setText("Guías De Remisión");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem14);

        jMenuItem15.setText("Disponibilidad de Almacén");
        jMenu1.add(jMenuItem15);

        jMenuItem16.setText("Caducidad de Productos");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem16);

        jMenuBar1.add(jMenu1);

        UserMenu.setText("Usuarios");
        UserMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UserMenuMouseClicked(evt);
            }
        });
        jMenuBar1.add(UserMenu);

        jMenu6.setText("Interfaces");

        jMenuItem7.setText("Personal");
        jMenu6.add(jMenuItem7);

        jMenuItem8.setText("Clientes");
        jMenu6.add(jMenuItem8);

        jMenuItem10.setText("Productos");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem10);

        jMenuBar1.add(jMenu6);

        jMenu4.setText("Sesión");

        jMenuItem5.setText("Actualizar datos");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem5);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void UserMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UserMenuMouseClicked
        if (userView == null || !userView.isShowing()) {
            userView = new UserView();
            userView.setVisible(true);
            mainPanel.add(userView);
            try {
                // TODO add your handling code here:
                userView.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_UserMenuMouseClicked

    private void jMenu5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu5MouseClicked
    }//GEN-LAST:event_jMenu5MouseClicked

    private void TUFrameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TUFrameMouseClicked
        // TODO add your handling code here:
        // TODO add your handling code here:

    }//GEN-LAST:event_TUFrameMouseClicked

    private void TUFrameMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TUFrameMousePressed
        // TODO add your handling code here:
        if (transportUnitView == null || !transportUnitView.isShowing()) {
            transportUnitView = new TransportUnitView();
            transportUnitView.setVisible(true);
            mainPanel.add(transportUnitView);
            try {
                // TODO add your handling code here:
                transportUnitView.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_TUFrameMousePressed

    private void WarehouseMenuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_WarehouseMenuMousePressed
        // TODO add your handling code here:
        if (warehouseView == null || !warehouseView.isShowing()) {
            warehouseView = new WarehouseView();
            warehouseView.setVisible(true);
            mainPanel.add(warehouseView);
            try {
                // TODO add your handling code here:
                warehouseView.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_WarehouseMenuMousePressed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        if (editUserView == null || !editUserView.isShowing()) {
            editUserView = new EditUserView();
            editUserView.setVisible(true);
            mainPanel.add(editUserView);
            try {
                // TODO add your handling code here:
                editUserView.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        if (productView == null || !productView.isShowing()) {
            productView = new ProductView();
            productView.setVisible(true);
            mainPanel.add(productView);
            try {
                // TODO add your handling code here:
                productView.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem6MousePressed
                // TODO add your handling code here:
        if (palletMovementsView == null || !palletMovementsView.isShowing()) {
            palletMovementsView = new PalletMovementsView();
            palletMovementsView.setVisible(true);
            mainPanel.add(palletMovementsView);
            try {
                // TODO add your handling code here:
                palletMovementsView.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem6MousePressed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        // TODO add your handling code here:
        if (productCaducity == null || !productCaducity.isShowing()) {
            productCaducity = new ProductCaducityReport();
            productCaducity.setVisible(true);
            mainPanel.add(productCaducity);
            try {
                // TODO add your handling code here:
                productCaducity.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
        if (remissionGuide == null || !remissionGuide.isShowing()) {
            remissionGuide = new RemissionGuideReport();
            remissionGuide.setVisible(true);
            mainPanel.add(remissionGuide);
            try {
                // TODO add your handling code here:
                remissionGuide.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
           if (stockReport == null || !stockReport.isShowing()) {
            stockReport = new StockReport();
            stockReport.setVisible(true);
            mainPanel.add(stockReport);
            try {
                // TODO add your handling code here:
                stockReport.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem TUFrame;
    private javax.swing.JMenu UserMenu;
    private javax.swing.JMenuItem WarehouseMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JDesktopPane mainPanel;
    // End of variables declaration//GEN-END:variables
}