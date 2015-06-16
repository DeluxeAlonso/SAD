/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.pallet;

import client.warehouse.*;
import application.condition.ConditionApplication;
import application.internment.InternmentApplication;
import application.pallet.PalletApplication;
import application.product.ProductApplication;
import application.rack.RackApplication;
import application.spot.SpotApplication;
import application.warehouse.WarehouseApplication;
import client.base.BaseView;
import entity.Almacen;
import entity.Condicion;
import entity.OrdenInternamiento;
import entity.Pallet;
import entity.Producto;
import entity.Rack;
import entity.OrdenInternamientoXProducto;
import entity.Ubicacion;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import util.EntityState;
import util.EntityType;
import util.Icons;
import util.InstanceFactory;

/**
 *
 * @author LUIS
 */
public class PalletView extends BaseView {
    WarehouseApplication warehouseApplication=InstanceFactory.Instance.getInstance("warehouseApplication", WarehouseApplication.class);
    ConditionApplication conditionApplication=InstanceFactory.Instance.getInstance("conditionApplication", ConditionApplication.class);
    RackApplication rackApplication=InstanceFactory.Instance.getInstance("rackApplication", RackApplication.class);
    SpotApplication spotApplication=InstanceFactory.Instance.getInstance("spotApplication", SpotApplication.class);
    ProductApplication productApplication=InstanceFactory.Instance.getInstance("productApplication", ProductApplication.class);
    PalletApplication palletApplication=InstanceFactory.Instance.getInstance("palletApplication", PalletApplication.class);
    InternmentApplication internmentApplication=InstanceFactory.Instance.getInstance("internmentApplication", InternmentApplication.class);
    
    ArrayList<Almacen> warehouses =null;
    ArrayList<Rack> racks =null;
    ArrayList<Ubicacion> spots =new ArrayList<Ubicacion>();
    ArrayList<Producto> product = new ArrayList<Producto>();
    ArrayList<Pallet> palletsGlob = new ArrayList<Pallet>();
    ArrayList<OrdenInternamiento> ordenInternamiento = new ArrayList<OrdenInternamiento>();
    
    ArrayList<String> state = new ArrayList<String>();
    ArrayList<Integer> stateN = new ArrayList<Integer>();
    
    public PalletView() {
        initComponents();
        super.initialize();
        String[] stateAux = EntityState.getPalletsState();
        for (String s : stateAux){
            if (s.compareTo("Eliminado")!=0){
                state.add(s);
                stateN.add(EntityState.Pallets.valueOf(s.toUpperCase()).ordinal());
            }
                
        }
        fillStateCombo();
        fillWarehouseCombo();
        fillProductCombo();
        fillIntermentCombo();
        clearGrid();
        //fillTable(warehouses.get(0).getId());
        Icons.setButton(newBtn, Icons.ICONOS.CREATE.ordinal());
        //Icons.setButton(deleteBtn, Icons.ICONOS.DELETE.ordinal());
        Icons.setButton(editBtn, Icons.ICONOS.MODIFY.ordinal());
        Icons.setButton(searchBtn, Icons.ICONOS.SEARCH.ordinal());

        
    }
    
    private void fillWarehouseCombo(){
        warehouses = warehouseApplication.queryAll();
        if (warehouses!=null && warehouses.size()!=0){
            
            String[] whNames = new String[warehouses.size()+1];
            whNames[0] = "";
            for (int i=0; i < warehouses.size();i++){
                whNames[i+1]=warehouses.get(i).getDescripcion();
            }
            warehouseCombo.setModel(new javax.swing.DefaultComboBoxModel(whNames));
        }
        
    }
    
    private void fillProductCombo(){
        product = productApplication.getAllProducts();
        if (product!=null && product.size()!=0){
            
            String[] prNames = new String[product.size()+1];
            prNames[0] = "";
            for (int i=0; i < product.size();i++){
                prNames[i+1]=product.get(i).getNombre();
            }
            jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(prNames));
        }
        
    }
    
       private void fillIntermentCombo(){
        ordenInternamiento = internmentApplication.queryAll();
        if (ordenInternamiento!=null && ordenInternamiento.size()!=0){
            
            String[] prNames = new String[ordenInternamiento.size()+1];
            prNames[0] = "";
            for (int i=0; i < ordenInternamiento.size();i++){
                prNames[i+1]=ordenInternamiento.get(i).getId().toString();
            }
            jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(prNames));
        }
        
    }
   
       
    private void fillStateCombo(){
        if (state!=null && state.size()!=0){   
            String[] prNames = new String[state.size()+1];
            prNames[0] = "";
            for (int i=0; i < state.size();i++){
                prNames[i+1]=state.get(i);
            }
            jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(prNames));
        }
        
    }
       
       
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    public void fillTable(ArrayList<Pallet> pallets) {
            clearGrid();
            String auxOrdenInternamiento = null;
            DefaultTableModel model = (DefaultTableModel) spotTable.getModel();
            
                for (Pallet p : pallets){
                    if (p.getOrdenInternamiento()==null)
                        auxOrdenInternamiento = "-";
                    else
                        auxOrdenInternamiento = p.getOrdenInternamiento().getId().toString();
                    
                    if (p.getUbicacion() != null){
                        model.addRow(new Object[]{
                        p.getEan128(),
                        p.getProducto().getNombre(),
                        state.get(stateN.indexOf(p.getEstado())),
                        p.getFechaVencimiento(),
                        auxOrdenInternamiento,
                        p.getUbicacion().getRack().getAlmacen().getId(),
                        p.getUbicacion().getRack().getId(),
                        p.getUbicacion().getLado(),
                        p.getUbicacion().getFila(),
                        p.getUbicacion().getColumna()
                        });    
                    }
                    else if (p.getUbicacion() == null){
                        model.addRow(new Object[]{
                        p.getEan128(),
                        p.getProducto().getNombre(),
                        state.get(stateN.indexOf(p.getEstado())),
                        p.getFechaVencimiento(),
                        auxOrdenInternamiento,
                        "No ubicado",
                        "-",
                        "-",
                        "-",
                        "-"
                        });    
                    }
                     
                }                
            
    }
    
    public void clearGrid() {
        DefaultTableModel model = (DefaultTableModel) spotTable.getModel();
        model.setRowCount(0);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        idTxt = new javax.swing.JTextField();
        searchBtn = new javax.swing.JButton();
        newBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        warehouseCombo = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        spotTable = new javax.swing.JTable();

        setClosable(true);
        setTitle("Pallet");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar Pallet"));

        jLabel1.setText("EAN128:");

        searchBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchBtnMouseClicked(evt);
            }
        });

        newBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBtnActionPerformed(evt);
            }
        });

        editBtn.setEnabled(false);
        editBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editBtnMouseClicked(evt);
            }
        });

        jLabel6.setText("Almacen:");

        warehouseCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        warehouseCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                warehouseComboItemStateChanged(evt);
            }
        });
        warehouseCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                warehouseComboActionPerformed(evt);
            }
        });

        jLabel7.setText("Producto:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.setName(""); // NOI18N

        jLabel2.setText("Orden Internamiento:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Estado:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6))
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(newBtn)
                        .addGap(141, 141, 141)
                        .addComponent(editBtn))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(warehouseCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(idTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(56, 56, 56)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(41, 41, 41)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(58, 58, 58)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(searchBtn)
                .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel7)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(warehouseCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(editBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(newBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        spotTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "EAN128", "Producto", "Estado", "F. Venc.", "Orden Int.", "Almacén", "Rack", "Lado", "Fila", "Columna"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spotTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                spotTableMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(spotTable);
        if (spotTable.getColumnModel().getColumnCount() > 0) {
            spotTable.getColumnModel().getColumn(1).setResizable(false);
            spotTable.getColumnModel().getColumn(2).setResizable(false);
            spotTable.getColumnModel().getColumn(3).setResizable(false);
            spotTable.getColumnModel().getColumn(4).setResizable(false);
            spotTable.getColumnModel().getColumn(5).setResizable(false);
            spotTable.getColumnModel().getColumn(6).setResizable(false);
            spotTable.getColumnModel().getColumn(7).setResizable(false);
            spotTable.getColumnModel().getColumn(8).setResizable(false);
            spotTable.getColumnModel().getColumn(9).setResizable(false);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 941, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtnActionPerformed
        // TODO add your handling code here:
        NewPalletView newPallet=new NewPalletView((JFrame)SwingUtilities.getWindowAncestor(this),true);
        newPallet.setVisible(true);
        clearGrid();
        
    }//GEN-LAST:event_newBtnActionPerformed

    private void editBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editBtnMouseClicked
        if (spotTable.getRowCount()>0){
            EditPalletView editPallet=new EditPalletView((JFrame)SwingUtilities.getWindowAncestor(this),true,palletsGlob.get(spotTable.getSelectedRow()));
            editPallet.setVisible(true);
            clearGrid();
            editBtn.setEnabled(false);
        }
       
    }//GEN-LAST:event_editBtnMouseClicked

    private void searchBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchBtnMouseClicked
     
        clearGrid();
        int warehouseId;
        int productId;
        int internmentOr;
        int estado;
        ArrayList<Pallet> pallets= new ArrayList<Pallet>();
        if (warehouseCombo.getSelectedIndex()>0)
            warehouseId = warehouses.get(warehouseCombo.getSelectedIndex()-1).getId();
        else
            warehouseId = 0;
        
        if (jComboBox1.getSelectedIndex()>0)
            productId = product.get(jComboBox1.getSelectedIndex()-1).getId();
        else
            productId = 0;
        
        if (jComboBox2.getSelectedIndex()>0)
            internmentOr = ordenInternamiento.get(jComboBox2.getSelectedIndex()-1).getId();
        else
            internmentOr = 0;
        
        if (jComboBox3.getSelectedIndex()>0)
            estado = (stateN.get(jComboBox3.getSelectedIndex()-1));
        else
            estado = -1;
        
        pallets.addAll(palletApplication.queryByParameters(idTxt.getText(),warehouseId,productId,internmentOr,estado));
        palletsGlob.clear();
        palletsGlob.addAll(pallets);
        
        
        
            try {
                startLoader();
                fillTable(pallets);
            }
            finally{
                stopLoader();
            }
        
        
        editBtn.setEnabled(false);
        //deleteBtn.setEnabled(true);
        
    }//GEN-LAST:event_searchBtnMouseClicked

    private void warehouseComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_warehouseComboItemStateChanged
        // TODO add your handling code here:
        
  
    }//GEN-LAST:event_warehouseComboItemStateChanged

    private void warehouseComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_warehouseComboActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_warehouseComboActionPerformed

    private void spotTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spotTableMousePressed
        // TODO add your handling code here:
        editBtn.setEnabled(true);
    }//GEN-LAST:event_spotTableMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton editBtn;
    private javax.swing.JTextField idTxt;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton newBtn;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTable spotTable;
    private javax.swing.JComboBox warehouseCombo;
    // End of variables declaration//GEN-END:variables
}
