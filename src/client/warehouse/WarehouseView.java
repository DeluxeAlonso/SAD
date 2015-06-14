/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.warehouse;

import application.condition.ConditionApplication;
import application.rack.RackApplication;
import application.spot.SpotApplication;
import application.warehouse.WarehouseApplication;
import client.base.BaseView;
import entity.Almacen;
import entity.Condicion;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
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
public class WarehouseView extends BaseView {
    WarehouseApplication warehouseApplication=InstanceFactory.Instance.getInstance("warehouseApplication", WarehouseApplication.class);
    ConditionApplication conditionApplication=InstanceFactory.Instance.getInstance("conditionApplication", ConditionApplication.class);
    RackApplication rackApplication=InstanceFactory.Instance.getInstance("rackApplication", RackApplication.class);
    SpotApplication spotApplication=InstanceFactory.Instance.getInstance("spotApplication", SpotApplication.class);
    Image img;
    Image img2;
    /**
     * Creates new form WarehouseForm
     */
    public WarehouseView() {
        initComponents();
        super.initialize();
        this.condicionCombo.setModel(new javax.swing.DefaultComboBoxModel(EntityType.CONDITIONS_NAMES));
        fillEstadoCombo();
               
        clearGrid();
        fillTable();
        Icons.setButton(newBtn, Icons.ICONOS.CREATE.ordinal());
        Icons.setButton(deleteBtn, Icons.ICONOS.DELETE.ordinal());
        Icons.setButton(editBtn, Icons.ICONOS.MODIFY.ordinal());
        Icons.setButton(searchBtn, Icons.ICONOS.SEARCH.ordinal());
        
    }

    public void calculartam(JButton searchBtn){
        int w = searchBtn.getWidth();
        int h = searchBtn.getHeight();
        System.out.println("W:"+w+"");
        System.out.println("H:"+h+"");
        
        
    }
    public void fillEstadoCombo(){
        String [] stateNames = new String[1+EntityState.getWarehousesState().length];
        stateNames[0]="";
        for (int i=0;i<EntityState.getWarehousesState().length;i++)
            stateNames[i+1]=EntityState.getWarehousesState()[i];
        this.EstadoCombo.setModel(new javax.swing.DefaultComboBoxModel(stateNames));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) usersGrid.getModel();
        ArrayList<Almacen> warehouses = warehouseApplication.queryAll();
        for (Almacen a : warehouses) {
            String estado = EntityState.getWarehousesState()[a.getEstado()];
            Condicion con = EntityType.getCondition(a.getCondicion().getId());
            model.addRow(new Object[]{
                a.getId().toString(),
                a.getDescripcion(),
                a.getCapacidad().toString(),
                con.getNombre(),
                estado
            });
        }

    }
    public void fillTable(int id,int condicion,int  state) {
        DefaultTableModel model = (DefaultTableModel) usersGrid.getModel();
        ArrayList<Almacen> warehouses = warehouseApplication.queryByParameters(id,condicion,state);
        for (Almacen a : warehouses) {

            Condicion con = EntityType.getCondition(a.getCondicion().getId());
            String estado = EntityState.getWarehousesState()[a.getEstado()];
            model.addRow(new Object[]{
                a.getId().toString(),
                a.getDescripcion(),
                a.getCapacidad().toString(),
                con.getNombre(),
                estado
            });
        }
        

    }
    
    public void clearGrid() {
        DefaultTableModel model = (DefaultTableModel) usersGrid.getModel();
        model.setRowCount(0);
    }
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        WarehouseGrid = new javax.swing.JScrollPane();
        usersGrid = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        EstadoCombo = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        idTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        condicionCombo = new javax.swing.JComboBox();
        searchBtn = new javax.swing.JButton();
        newBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();

        setClosable(true);
        setTitle("Almacen");

        usersGrid.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Descripcion", "Capacidad", "Tipo", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        usersGrid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                usersGridMousePressed(evt);
            }
        });
        WarehouseGrid.setViewportView(usersGrid);
        if (usersGrid.getColumnModel().getColumnCount() > 0) {
            usersGrid.getColumnModel().getColumn(0).setResizable(false);
            usersGrid.getColumnModel().getColumn(0).setPreferredWidth(10);
            usersGrid.getColumnModel().getColumn(1).setResizable(false);
            usersGrid.getColumnModel().getColumn(1).setPreferredWidth(120);
            usersGrid.getColumnModel().getColumn(2).setResizable(false);
            usersGrid.getColumnModel().getColumn(3).setResizable(false);
            usersGrid.getColumnModel().getColumn(4).setResizable(false);
            usersGrid.getColumnModel().getColumn(4).setPreferredWidth(20);
        }

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar Almacen"));

        jLabel3.setText("Estado:");

        EstadoCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Id:");

        jLabel2.setText("Condicion:");

        condicionCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });

        newBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBtnActionPerformed(evt);
            }
        });

        editBtn.setEnabled(false);
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });

        deleteBtn.setEnabled(false);
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(18, 18, 18)
                            .addComponent(EstadoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(searchBtn))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(idTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addComponent(jLabel2)
                            .addGap(18, 18, 18)
                            .addComponent(condicionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(newBtn)
                        .addGap(18, 18, 18)
                        .addComponent(editBtn)
                        .addGap(18, 18, 18)
                        .addComponent(deleteBtn)))
                .addGap(23, 94, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(idTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(condicionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(EstadoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(newBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(WarehouseGrid, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(WarehouseGrid, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtnActionPerformed
        // TODO add your handling code here:

        NewWarehouseView newWarehouse=new NewWarehouseView((JFrame)SwingUtilities.getWindowAncestor(this),true);
        newWarehouse.setVisible(true);
        clearGrid();
        fillTable();        
        
    }//GEN-LAST:event_newBtnActionPerformed

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        // TODO add your handling code here:
        int sr = usersGrid.getSelectedRow();
        String idString = usersGrid.getModel().getValueAt(sr, 0).toString();
        Almacen a = warehouseApplication.queryById(Integer.parseInt(idString));
        EditWarehouseView editWarehouse=new EditWarehouseView((JFrame)SwingUtilities.getWindowAncestor(this),true,a);
        editWarehouse.setVisible(true);
        clearGrid();
        fillTable();
        editBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
    }//GEN-LAST:event_editBtnActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        // TODO add your handling code here:
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
    }//GEN-LAST:event_searchBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        // TODO add your handling code here:
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
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void usersGridMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usersGridMousePressed
        // TODO add your handling code here:
        editBtn.setEnabled(true);
        deleteBtn.setEnabled(true);
    }//GEN-LAST:event_usersGridMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox EstadoCombo;
    private javax.swing.JScrollPane WarehouseGrid;
    private javax.swing.JComboBox condicionCombo;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton editBtn;
    private javax.swing.JTextField idTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton newBtn;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTable usersGrid;
    // End of variables declaration//GEN-END:variables
}
