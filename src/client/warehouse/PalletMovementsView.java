package client.warehouse;

import application.pallet.PalletApplication;
import application.rack.RackApplication;
import application.spot.SpotApplication;
import application.warehouse.WarehouseApplication;
import client.base.BaseView;
import entity.Almacen;
import entity.Pallet;
import entity.Rack;
import entity.Ubicacion;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import util.EntityState;
import util.EntityState.Spots;
import util.InstanceFactory;
import util.Strings;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KEVIN BROWN
 */
public class PalletMovementsView extends BaseView {
    WarehouseApplication warehouseApplication = InstanceFactory.Instance.getInstance("warehouseApplicaiton", WarehouseApplication.class);
    RackApplication rackApplication = InstanceFactory.Instance.getInstance("rackApplicaiton", RackApplication.class);
    SpotApplication spotApplication = InstanceFactory.Instance.getInstance("spotApplicaiton", SpotApplication.class);
    PalletApplication palletApplication = InstanceFactory.Instance.getInstance("palletApplicaiton", PalletApplication.class);
    public static PalletMovementsView palletMovementsView;
    public ArrayList<Almacen> warehousesFrom;
    public ArrayList<Almacen> warehousesTo;
    public ArrayList<Rack> racksFrom;
    public ArrayList<Rack> racksTo;
    public ArrayList<Ubicacion> spotsTo;
    public ArrayList<Pallet> palletsFrom;
    public int warehouseSelected;
    /**
     * Creates new form PalletMovementsView
     */
    public PalletMovementsView() {
        initComponents();
        super.initialize();
        //Initialize comboWarehouseFrom
        palletMovementsView = this;
        warehousesFrom = warehouseApplication.queryAll();
        if(warehousesFrom.size()>0){
            String[] warehousesName = new String[warehousesFrom.size()];
            for(int i=0; i<warehousesFrom.size(); i++){
                warehousesName[i] = warehousesFrom.get(i).getDescripcion();
            }
            comboWarehouseFrom.setModel(new javax.swing.DefaultComboBoxModel(warehousesName));
            fillWarehousesTo(warehousesFrom.get(0).getCondicion().getId());
            fillRacksFrom(warehousesFrom.get(0).getId());
            if(racksFrom.size()>0)
                fillTableFrom(racksFrom.get(0).getId());
            if(warehousesTo.size()>0){
                fillRacksTo(warehousesTo.get(0).getId());
                if(racksTo.size()>0)
                    fillTableTo(racksTo.get(0).getId());
            }
        }
    }
    
    public void clearComponents(){
        comboWarehouseTo.removeAllItems();
        comboRackTo.removeAllItems();
        comboRackFrom.removeAllItems();
        clearTableFrom();
        clearTableTo();
    }
    
    public void fillWarehousesTo(int type){
        comboWarehouseTo.removeAllItems();
        warehousesTo = warehouseApplication.queryWarehousesByType(type);
        if(warehousesTo.size()>0){
            String[] warehousesName = new String[warehousesTo.size()];
            for(int i=0; i<warehousesTo.size(); i++){
                warehousesName[i] = warehousesTo.get(i).getDescripcion();
            }
            comboWarehouseTo.setModel(new javax.swing.DefaultComboBoxModel(warehousesName));
        }
    }
    
    public void fillRacksFrom(int warehouseId){
        comboRackFrom.removeAllItems();
        racksFrom = rackApplication.queryRacksByWarehouse(warehouseId);
        if(racksFrom.size()>0){
            String[] rackIdentifier = new String[racksFrom.size()];
            for(int i=0; i<racksFrom.size(); i++){
                rackIdentifier[i] = racksFrom.get(i).getId().toString();
            }
            comboRackFrom.setModel(new javax.swing.DefaultComboBoxModel(rackIdentifier));
        }
    }
    
    public void fillRacksTo(int warehouseId){
        comboRackTo.removeAllItems();
        racksTo = rackApplication.queryRacksByWarehouse(warehouseId);
        if(racksTo.size()>0){
            String[] rackIdentifier = new String[racksTo.size()];
            for(int i=0; i<racksTo.size(); i++){
                rackIdentifier[i] = racksTo.get(i).getId().toString();
            }
            comboRackTo.setModel(new javax.swing.DefaultComboBoxModel(rackIdentifier));
        }
    }
    
    public void clearTableFrom(){
        DefaultTableModel model = (DefaultTableModel) tblPalletFrom.getModel();
        model.setRowCount(0);
    }
    
    public void clearTableTo(){
        DefaultTableModel model = (DefaultTableModel) tblPalletTo.getModel();
        model.setRowCount(0);
    }
    
    public void fillTableFrom(int rackId){
        clearTableFrom();
        DefaultTableModel model = (DefaultTableModel) tblPalletFrom.getModel();
        palletsFrom = palletApplication.queryPalletsByRack(rackId);
        if(palletsFrom.size()>0){
            for(Pallet pallet : palletsFrom){
                model.addRow(new Object[]{
                    pallet.getEan128(),
                    pallet.getUbicacion().getLado(),
                    pallet.getUbicacion().getFila(),
                    pallet.getUbicacion().getColumna(),
                });
            }
        }
        
    }
    
    public void fillTableTo(int rackId){
        clearTableTo();
        DefaultTableModel model = (DefaultTableModel) tblPalletTo.getModel();
        spotsTo = (ArrayList<Ubicacion>) spotApplication.queryEmptySpotsByRack(rackId);
        if(spotsTo.size()>0){
            for(Ubicacion spot : spotsTo){
                model.addRow(new Object[]{
                    spot.getLado(),
                    spot.getFila(),
                    spot.getColumna(),
                });
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

        comboWarehouseFrom = new javax.swing.JComboBox();
        comboRackFrom = new javax.swing.JComboBox();
        comboWarehouseTo = new javax.swing.JComboBox();
        comboRackTo = new javax.swing.JComboBox();
        jScrollPaneFrom = new javax.swing.JScrollPane();
        tblPalletFrom = new javax.swing.JTable();
        jScrollPaneTo = new javax.swing.JScrollPane();
        tblPalletTo = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Movimiento de Pallets");

        comboWarehouseFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboWarehouseFromItemStateChanged(evt);
            }
        });

        comboRackFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboRackFromItemStateChanged(evt);
            }
        });

        comboWarehouseTo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboWarehouseToItemStateChanged(evt);
            }
        });

        comboRackTo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboRackToItemStateChanged(evt);
            }
        });

        tblPalletFrom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "EAN128", "Lado", "Fila", "Columna", "Seleccione"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPaneFrom.setViewportView(tblPalletFrom);

        tblPalletTo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Lado", "Fila", "Columna", "Seleccione"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPaneTo.setViewportView(tblPalletTo);

        jLabel1.setText("Seleccione almacén de origen");

        jLabel2.setText("Seleccione rack origen");

        jLabel3.setText("Seleccione almacén destino");

        jLabel4.setText("Seleccione rack destino");

        btnSave.setText("Guardar");
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSaveMousePressed(evt);
            }
        });

        jLabel5.setText("Pallets en rack de origen");

        jLabel6.setText("Ubicaciones libres en rack de destino");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(19, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboWarehouseFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboRackFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(133, 133, 133))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSave)
                            .addComponent(jScrollPaneFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPaneTo, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(comboRackTo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboWarehouseTo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel6))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboWarehouseFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboWarehouseTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboRackFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboRackTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPaneFrom, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                    .addComponent(jScrollPaneTo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(btnSave)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboWarehouseFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboWarehouseFromItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED){
            clearComponents();
            fillWarehousesTo(warehousesFrom.get(comboWarehouseFrom.getSelectedIndex()).getCondicion().getId());
            fillRacksFrom(warehousesFrom.get(comboWarehouseFrom.getSelectedIndex()).getId());
            if(racksFrom.size()>0)
                fillTableFrom(racksFrom.get(0).getId());
            if(warehousesTo.size()>0){
                fillRacksTo(warehousesTo.get(0).getId());
                if(racksTo.size()>0)
                    fillTableTo(racksTo.get(0).getId());
            }
        }
    }//GEN-LAST:event_comboWarehouseFromItemStateChanged

    private void comboWarehouseToItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboWarehouseToItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED){
            fillRacksTo(warehousesTo.get(comboWarehouseTo.getSelectedIndex()).getId());
            if(racksTo.size()>0)
                fillTableTo(racksTo.get(0).getId());
        }
    }//GEN-LAST:event_comboWarehouseToItemStateChanged

    private void comboRackFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboRackFromItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED){
            fillTableFrom(racksFrom.get(comboRackFrom.getSelectedIndex()).getId());
        }
    }//GEN-LAST:event_comboRackFromItemStateChanged

    private void comboRackToItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboRackToItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED){
            fillTableTo(racksTo.get(comboRackTo.getSelectedIndex()).getId());
        }
    }//GEN-LAST:event_comboRackToItemStateChanged

    private void btnSaveMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMousePressed
        ArrayList<Integer> selectedPallets = new ArrayList<Integer>();                            
        ArrayList<Integer> selectedSpots = new ArrayList<Integer>(); 
        Boolean isChecked;
        JOptionPane.setDefaultLocale(new Locale("es", "ES"));
        
        if(tblPalletFrom.getRowCount()>0){
            for (int i = 0; i < tblPalletFrom.getRowCount(); i++) {
                isChecked = (Boolean)tblPalletFrom.getValueAt(i, 4);
                if (isChecked != null && isChecked) {
                    selectedPallets.add(palletsFrom.get(i).getId());
                }
            }
        }
        
        if(tblPalletTo.getRowCount()>0){
            for (int i = 0; i < tblPalletTo.getRowCount(); i++) {
                isChecked = (Boolean)tblPalletTo.getValueAt(i, 3);
                if (isChecked != null && isChecked) {
                    selectedSpots.add(spotsTo.get(i).getId());
                }
            }
        }
        if(selectedPallets.size() == selectedSpots.size() && selectedPallets.size()>0){
            for(int i=0;i<selectedPallets.size();i++){
                spotApplication.updateSpotOccupancy(palletsFrom.get(i).getUbicacion().getId(), Spots.LIBRE.ordinal());
                palletApplication.updatePalletSpot(palletsFrom.get(i).getId(), selectedSpots.get(i));
                spotApplication.updateSpotOccupancy(selectedSpots.get(i), Spots.OCUPADO.ordinal());
            }
            fillTableFrom(racksFrom.get(comboRackFrom.getSelectedIndex()).getId());
            fillTableTo(racksTo.get(comboRackTo.getSelectedIndex()).getId());
        }else{
            String error_message = "Errores:\n\n";
            if(selectedPallets.size()<1)
                error_message += Strings.ERROR_NO_PALLETS_SELECTED+"\n";
            if(selectedSpots.size()<1)
                error_message += Strings.ERROR_NO_SPOTS_SELECTED+"\n";
            if(selectedPallets.size() != selectedSpots.size())
                error_message += Strings.ERROR_PALLETS_DONT_MATCH_SPOTS+"\n";
            JOptionPane.showMessageDialog(this, error_message,Strings.ERROR_PALLETS_MOVEMENT_TITLE,JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSaveMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox comboRackFrom;
    private javax.swing.JComboBox comboRackTo;
    private javax.swing.JComboBox comboWarehouseFrom;
    private javax.swing.JComboBox comboWarehouseTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPaneFrom;
    private javax.swing.JScrollPane jScrollPaneTo;
    private javax.swing.JTable tblPalletFrom;
    private javax.swing.JTable tblPalletTo;
    // End of variables declaration//GEN-END:variables
}
