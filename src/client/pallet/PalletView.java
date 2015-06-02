/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.pallet;

import client.warehouse.*;
import application.condition.ConditionApplication;
import application.internment.InternmentApplication;
import application.rack.RackApplication;
import application.spot.SpotApplication;
import application.warehouse.WarehouseApplication;
import client.base.BaseView;
import entity.Almacen;
import entity.Condicion;
import entity.Pallet;
import entity.Producto;
import entity.Rack;
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
    Image img;
    Image img2;
    ArrayList<Almacen> warehouses =null;
    ArrayList<Rack> racks =null;
    ArrayList<Ubicacion> spots =new ArrayList<Ubicacion>();
    /**
     * Creates new form WarehouseForm
     */
    
    public PalletView() {
        initComponents();
        super.initialize();
        fillWarehouseCombo();
               
        clearGrid();
        fillTable(warehouses.get(0).getId());
        Icons.setButton(newBtn, Icons.ICONOS.CREATE.ordinal());
        Icons.setButton(deleteBtn, Icons.ICONOS.DELETE.ordinal());
        Icons.setButton(editBtn, Icons.ICONOS.MODIFY.ordinal());
        Icons.setButton(searchBtn, Icons.ICONOS.SEARCH.ordinal());
        
    }
    
    private void fillWarehouseCombo(){
        warehouses = warehouseApplication.queryAll();
        if (warehouses!=null){
            String[] whNames = new String[warehouses.size()];
            for (int i=0; i < warehouses.size();i++){
                whNames[i]=warehouses.get(i).getDescripcion();
            }
            warehouseCombo.setModel(new javax.swing.DefaultComboBoxModel(whNames));
        }
        
    }
    
    
    public void calculartam(JButton searchBtn){
        int w = searchBtn.getWidth();
        int h = searchBtn.getHeight();
        System.out.println("W:"+w+"");
        System.out.println("H:"+h+"");
        
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    public void fillTable(int id) {
            clearGrid();
            DefaultTableModel model = (DefaultTableModel) spotTable.getModel();
            ArrayList<Ubicacion> ubi = new ArrayList<Ubicacion>();
            Almacen alm = warehouses.get(warehouseCombo.getSelectedIndex());
            spotLbl.setText(alm.getUbicLibres().toString());
            ArrayList<Rack> racks = rackApplication.queryRacksByWarehouse(alm.getId());
            for (Rack r : racks){
                //r.getUbicacions().
                ubi = (ArrayList<Ubicacion>) spotApplication.queryEmptySpotsByRack(r.getId());
                spots.addAll(ubi);
                for (Ubicacion ub : ubi){
                    model.addRow(new Object[]{
                    r.getId(),
                    ub.getLado(),
                    ub.getFila(),
                    ub.getColumna()
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
        deleteBtn = new javax.swing.JButton();
        dtcInitDate = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        warehouseCombo = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        spotLbl = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        spotTable = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        productTxt = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Pallet");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar Pallet"));

        jLabel1.setText("Código:");

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

        deleteBtn.setEnabled(false);
        deleteBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteBtnMouseClicked(evt);
            }
        });

        jLabel5.setText("Fecha caducidad:");

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

        jLabel2.setText("Ubicaciones Disponibles:");

        spotLbl.setEditable(false);

        spotTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rack", "Lado", "Fila", "Columna"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(spotTable);
        if (spotTable.getColumnModel().getColumnCount() > 0) {
            spotTable.getColumnModel().getColumn(0).setResizable(false);
            spotTable.getColumnModel().getColumn(1).setResizable(false);
            spotTable.getColumnModel().getColumn(2).setResizable(false);
            spotTable.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel7.setText("Producto:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(33, 33, 33)
                        .addComponent(spotLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(idTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(searchBtn)
                                .addGap(181, 181, 181))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dtcInitDate, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(productTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(warehouseCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(newBtn)
                        .addGap(18, 18, 18)
                        .addComponent(editBtn)
                        .addGap(18, 18, 18)
                        .addComponent(deleteBtn)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(idTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dtcInitDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(productTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(warehouseCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(spotLbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtnActionPerformed
        // TODO add your handling code here:
        Pallet pa = new Pallet();
        Producto pro = new Producto();
        Ubicacion  ub = new Ubicacion ();
        
        Calendar cal = Calendar.getInstance();
        pro.setId(Integer.parseInt(productTxt.getText()));
        pa.setEstado(EntityState.Pallets.CREADO.ordinal());
        pa.setFechaRegistro(cal.getTime());
        pa.setProducto(pro);
        pa.setUbicacion(spots.get(spotTable.getSelectedRow()));
        //InternmentApplication
    }//GEN-LAST:event_newBtnActionPerformed

    private void editBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editBtnMouseClicked
        // TODO add your handling code here:
        /*
        int sr = usersGrid.getSelectedRow();
        String idString = usersGrid.getModel().getValueAt(sr, 0).toString();
        Almacen a = warehouseApplication.queryById(Integer.parseInt(idString));
        EditWarehouseView editWarehouse=new EditWarehouseView((JFrame)SwingUtilities.getWindowAncestor(this),true,a);
        editWarehouse.setVisible(true);
        clearGrid();
        fillTable();
        editBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
                */
    }//GEN-LAST:event_editBtnMouseClicked

    private void deleteBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteBtnMouseClicked
        // TODO add your handling code here:
        /*
        if (deleteBtn.isEnabled()){
        int sr = usersGrid.getSelectedRow();
        String idString = usersGrid.getModel().getValueAt(sr, 0).toString();
        Almacen a = warehouseApplication.queryById(Integer.parseInt(idString));
        a.setEstado(EntityState.Warehouses.INACTIVO.ordinal());
        warehouseApplication.update(a);
        clearGrid();
        fillTable();
        editBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        }
                */
    }//GEN-LAST:event_deleteBtnMouseClicked

    private void searchBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchBtnMouseClicked
        // TODO add your handling code here:
        /*
        int w = searchBtn.getWidth();
        int h = searchBtn.getHeight();
        System.out.println("W:"+w+"");
        System.out.println("H:"+h+"");
        //searchBtn.setSize(64, 64);
        clearGrid();
        int idS;
        int condicionS;
        int state;
        if (idTxt.getText().equals(""))
            idS=0;
        else idS=Integer.parseInt(idTxt.getText());
        
        if (condicionCombo.getSelectedItem().toString().equals(""))
            condicionS = 0;
        else 
            condicionS=conditionApplication.getConditionInstance(condicionCombo.getSelectedItem().toString()).getId();
        if (EstadoCombo.getSelectedIndex()==0)
            state = -1;
        else
            state = EstadoCombo.getSelectedIndex()-1;
        
        fillTable(idS,condicionS,state);
        editBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
                */
    }//GEN-LAST:event_searchBtnMouseClicked

    private void warehouseComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_warehouseComboItemStateChanged
        // TODO add your handling code here:
        
        fillTable(warehouses.get(warehouseCombo.getSelectedIndex()).getId());
                

    }//GEN-LAST:event_warehouseComboItemStateChanged

    private void warehouseComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_warehouseComboActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_warehouseComboActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteBtn;
    private com.toedter.calendar.JDateChooser dtcInitDate;
    private javax.swing.JButton editBtn;
    private javax.swing.JTextField idTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton newBtn;
    private javax.swing.JTextField productTxt;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTextField spotLbl;
    private javax.swing.JTable spotTable;
    private javax.swing.JComboBox warehouseCombo;
    // End of variables declaration//GEN-END:variables
}