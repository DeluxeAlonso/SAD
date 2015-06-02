/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.general;

import application.action.ActionApplication;
import application.profile.ProfileApplication;
import client.internment.InternmentSelectView;

import client.devolution.DevolutionView;
import client.reports.AvailabilityReport;

import client.product.ProductView;
import client.personal.PersonalView;
import client.client.ClientView;
import client.delivery.DeliveryView;
import client.order.OrderView;
import client.pallet.PalletView;
import client.rack.RackView;
import client.reports.KardexReport;
import client.reports.RemissionGuideReport;
import client.reports.StockReport;
import client.reports.ProductCaducityReport;
import client.reports.SecurityLogView;

import client.transportunit.*;
import client.user.EditUserView;
import client.user.LoginView;
import client.user.UserView;
import client.warehouse.PalletMovementsView;
import client.warehouse.WarehouseView;
import client.warehouseControlCheck.WarehouseControlCheckView;
import entity.Accion;
import entity.Usuario;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;
import util.Icons;
import util.InstanceFactory;

/**
 *
 * @author Nevermade
 */
public class MainView extends javax.swing.JFrame {

    ProfileApplication profileApplication = InstanceFactory.Instance.getInstance("profileApplication", ProfileApplication.class);
    ActionApplication actionApplication = InstanceFactory.Instance.getInstance("actionApplication", ActionApplication.class);

    private UserView userView = null;
    private TransportUnitView transportUnitView = null;
    private WarehouseView warehouseView = null;
    private DevolutionView devolutionView = null;
    private ProductView productView = null;
    private PersonalView personalView = null;
    private ClientView clientView = null;
    private OrderView orderView = null;
    private DeliveryView deliveryView = null;
    private EditUserView editUserView = null;
    private PalletMovementsView palletMovementsView = null;
    private ProductCaducityReport productCaducity = null;
    private RemissionGuideReport remissionGuide = null;
    private StockReport stockReport = null;
    private WarehouseControlCheckView warehouseControlCheckView = null;
    private InternmentSelectView internmentSelectView = null;
    private PalletView palletView = null;
    private AvailabilityReport availabilityReport = null;
    private RackView rackView = null;
    private KardexReport kardexReport = null;
    public static Usuario user = null;
    public static JDesktopPane desktopPane = null;
    public static Icons icons = new Icons();
    private BufferedImage img = null;
    private Image icon = null;
    private SecurityLogView securiryLog=null;
    /**
     * Creates new form MainForm
     */
    public MainView(Usuario user) {
        loadImageToDesktopPane();
        initComponents();
        this.user = user;
        System.out.println(mainPanel);
        desktopPane = mainPanel;
        renderUserMenu();
        Icons.setMainIcon(this);
    }

    public MainView() {
        loadImageToDesktopPane();
        initComponents();
        desktopPane = mainPanel;
        Icons.setMainIcon(this);
    }

    private void renderUserMenu() {
        Set actions = profileApplication.getProfileByName(user.getPerfil().getNombrePerfil()).getAccions();
        int numActions = actionApplication.getParents().size();

        MenuElement[] topLevelElements = menuBar.getSubElements();
        for (int i = 0; i < numActions; i++) {
            
            if (!(((JMenu) topLevelElements[i]).getText().equals("Sesión"))) {
                
                int numMenuItem = 0;
                MenuElement popItem= topLevelElements[i].getSubElements()[0];
                MenuElement[] lowLevelElements = popItem.getSubElements();
                for (int j = 0; j < lowLevelElements.length; j++) {
                    boolean found = false;
                    for (Accion a : (Set<Accion>) actions) {
                        String menuApp=((JMenuItem) lowLevelElements[j]).getText().toLowerCase();
                        String menuBD=a.getNombre().toLowerCase();
                        if (menuApp.equals(menuBD)) {
                            found = true;
                            actions.remove(a);
                            numMenuItem++;
                            break;
                        }
                    }
                    if (!found) {
                        ((JPopupMenu)popItem).remove(((JMenuItem) lowLevelElements[j]));
                    }
                }
                if (numMenuItem == 0) {
                    menuBar.remove(((JMenu) topLevelElements[i]));
                }
            }
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

        mainPanel = new javax.swing.JDesktopPane(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(img, 0, 0,  getWidth(),getHeight(), null);
            }

        };
        menuBar = new javax.swing.JMenuBar();
        menuMov = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        Pedidos = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        menuOp = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        menuMaint = new javax.swing.JMenu();
        WarehouseMenu = new javax.swing.JMenuItem();
        TUFrame = new javax.swing.JMenuItem();
        RackItem = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        menuReport = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        menuSec = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        menuInter = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        menuSession = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Almacenes y Despacho");
        setExtendedState(this.getExtendedState()| JFrame.MAXIMIZED_BOTH);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        mainPanel.setDragMode(javax.swing.JDesktopPane.OUTLINE_DRAG_MODE);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 459, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 283, Short.MAX_VALUE)
        );

        menuMov.setText("Movimientos");

        jMenuItem2.setText("Internamiento");
        jMenuItem2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem2MousePressed(evt);
            }
        });
        menuMov.add(jMenuItem2);

        Pedidos.setText("Pedidos");
        Pedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PedidosActionPerformed(evt);
            }
        });
        menuMov.add(Pedidos);

        jMenuItem6.setText("Movimientos Pallets");
        jMenuItem6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem6MousePressed(evt);
            }
        });
        menuMov.add(jMenuItem6);

        menuBar.add(menuMov);

        menuOp.setText("Operaciones");

        jMenuItem1.setText("Toma y Ajuste de Inventario");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menuOp.add(jMenuItem1);

        jMenuItem4.setText("Despacho");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        menuOp.add(jMenuItem4);

        jMenuItem11.setText("Devoluciones");
        jMenuItem11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem11MousePressed(evt);
            }
        });
        menuOp.add(jMenuItem11);

        menuBar.add(menuOp);

        menuMaint.setText("Mantenimientos");
        menuMaint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuMaintMouseClicked(evt);
            }
        });

        WarehouseMenu.setText("Almacén");
        WarehouseMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                WarehouseMenuMousePressed(evt);
            }
        });
        menuMaint.add(WarehouseMenu);

        TUFrame.setText("Unidad de Transporte");
        TUFrame.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TUFrameMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TUFrameMousePressed(evt);
            }
        });
        menuMaint.add(TUFrame);

        RackItem.setText("Rack");
        RackItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                RackItemMousePressed(evt);
            }
        });
        menuMaint.add(RackItem);

        jMenuItem3.setText("Pallet");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        menuMaint.add(jMenuItem3);

        menuBar.add(menuMaint);

        menuReport.setText("Reportes");

        jMenuItem12.setText("Kardex");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        menuReport.add(jMenuItem12);

        jMenuItem13.setText("Stock");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        menuReport.add(jMenuItem13);

        jMenuItem14.setText("Guías De Remisión");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        menuReport.add(jMenuItem14);

        jMenuItem15.setText("Disponibilidad de Almacén");
        jMenuItem15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem15MousePressed(evt);
            }
        });
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        menuReport.add(jMenuItem15);

        jMenuItem16.setText("Caducidad de Productos");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        menuReport.add(jMenuItem16);

        menuBar.add(menuReport);

        menuSec.setText("Seguridad");

        jMenuItem9.setText("Usuarios y Perfiles");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        menuSec.add(jMenuItem9);

        jMenuItem17.setText("Log");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        menuSec.add(jMenuItem17);

        menuBar.add(menuSec);

        menuInter.setText("Interfaces");

        jMenuItem7.setText("Personal");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        menuInter.add(jMenuItem7);

        jMenuItem8.setText("Clientes");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        menuInter.add(jMenuItem8);

        jMenuItem10.setText("Productos");
        jMenuItem10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem10MousePressed(evt);
            }
        });
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        menuInter.add(jMenuItem10);

        menuBar.add(menuInter);

        menuSession.setText("Sesión");

        jMenuItem5.setText("Actualizar datos");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        menuSession.add(jMenuItem5);

        jMenuItem18.setText("Cerrar Sesión");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        menuSession.add(jMenuItem18);

        menuBar.add(menuSession);

        setJMenuBar(menuBar);

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

    private void loadImageToDesktopPane() {
        try {
            img = ImageIO.read(getClass().getResource("/images/desktop_background.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void menuMaintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuMaintMouseClicked
    }//GEN-LAST:event_menuMaintMouseClicked

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
                Logger.getLogger(MainView.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_TUFrameMousePressed

    private void WarehouseMenuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_WarehouseMenuMousePressed
        // TODO add your handling code here:
        try{
        if (warehouseView == null || !warehouseView.isShowing()) {
            warehouseView = new WarehouseView();
            warehouseView.setVisible(true);
            mainPanel.add(warehouseView);
            try {
                // TODO add your handling code here:
                warehouseView.setSelected(true);

            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        }catch(Exception e){}

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
                Logger.getLogger(MainView.class
                        .getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(MainView.class
                        .getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(MainView.class
                        .getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(MainView.class
                        .getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(MainView.class
                        .getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(MainView.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        if (personalView == null || !personalView.isShowing()) {
            personalView = new PersonalView();
            personalView.setVisible(true);
            mainPanel.add(personalView);
            try {
                // TODO add your handling code here:
                personalView.setSelected(true);

            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        if (clientView == null || !clientView.isShowing()) {
            clientView = new ClientView();
            clientView.setVisible(true);
            mainPanel.add(clientView);
            try {

                // TODO add your handling code here:
                clientView.setSelected(true);

            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }           // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        if (warehouseControlCheckView == null || !warehouseControlCheckView.isShowing()) {
            warehouseControlCheckView = new WarehouseControlCheckView(mainPanel);
            warehouseControlCheckView.setVisible(true);
            mainPanel.add(warehouseControlCheckView);
            try {
                // TODO add your handling code here:
                warehouseControlCheckView.setSelected(true);

            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem2MousePressed
        // TODO add your handling code here:
        if (internmentSelectView == null || !internmentSelectView.isShowing()) {
            internmentSelectView = new InternmentSelectView();
            internmentSelectView.setVisible(true);
            mainPanel.add(internmentSelectView);
            try {
                // TODO add your handling code here:
                internmentSelectView.setSelected(true);

            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_jMenuItem2MousePressed

    private void jMenuItem11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem11MousePressed
        // TODO add your handling code here:
        if (devolutionView == null || !devolutionView.isShowing()) {
            devolutionView = new DevolutionView();
            devolutionView.setVisible(true);
            mainPanel.add(devolutionView);
            try {
                // TODO add your handling code here:
                devolutionView.setSelected(true);

            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem11MousePressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        //Tools.closeSession();
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        if (kardexReport == null || !kardexReport.isShowing()) {
            kardexReport = new KardexReport();
            kardexReport.setVisible(true);
            mainPanel.add(kardexReport);
            try {
                // TODO add your handling code here:
                kardexReport.setSelected(true);

            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        if (userView == null || !userView.isShowing()) {
            userView = new UserView(0);
            userView.setVisible(true);
            mainPanel.add(userView);
            try {
                // TODO add your handling code here:
                userView.setSelected(true);

            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        // TODO add your handling code here:
        new LoginView().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void RackItemMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RackItemMousePressed
        // TODO add your handling code here:
        if (rackView == null || !rackView.isShowing()) {
            rackView = new RackView();
            rackView.setVisible(true);
            mainPanel.add(rackView);
            try {
                // TODO add your handling code here:
                rackView.setSelected(true);

            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_RackItemMousePressed

    private void PedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PedidosActionPerformed
        if (orderView == null || !orderView.isShowing()) {
            orderView = new OrderView();
            orderView.setVisible(true);
            mainPanel.add(orderView);
            try {
                // TODO add your handling code here:
                orderView.setSelected(true);

            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_PedidosActionPerformed

    private void jMenuItem10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem10MousePressed
        // TODO add your handling code here:
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
        
    }//GEN-LAST:event_jMenuItem10MousePressed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        // TODO add your handling code here:
        if (securiryLog == null || !securiryLog.isShowing()) {
            securiryLog = new SecurityLogView();
            securiryLog.setVisible(true);
            mainPanel.add(securiryLog);
            try {
                // TODO add your handling code here:
                securiryLog.setSelected(true);

            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        if (deliveryView == null || !deliveryView.isShowing()) {
            deliveryView = new DeliveryView();
            deliveryView.setVisible(true);
            mainPanel.add(deliveryView);
            try {
                // TODO add your handling code here:
                deliveryView.setSelected(true);

            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        if (palletView == null || !palletView.isShowing()) {
            palletView = new PalletView();
            palletView.setVisible(true);
            mainPanel.add(palletView);
            try {
                // TODO add your handling code here:
                palletView.setSelected(true);

            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem15MousePressed(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        if (availabilityReport == null || !availabilityReport.isShowing()) {
            availabilityReport = new AvailabilityReport();
            availabilityReport.setVisible(true);
            mainPanel.add(availabilityReport);
            try {
                // TODO add your handling code here:
                availabilityReport.setSelected(true);

            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainView.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Pedidos;
    private javax.swing.JMenuItem RackItem;
    private javax.swing.JMenuItem TUFrame;
    private javax.swing.JMenuItem WarehouseMenu;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JDesktopPane mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuInter;
    private javax.swing.JMenu menuMaint;
    private javax.swing.JMenu menuMov;
    private javax.swing.JMenu menuOp;
    private javax.swing.JMenu menuReport;
    private javax.swing.JMenu menuSec;
    private javax.swing.JMenu menuSession;
    // End of variables declaration//GEN-END:variables

}
