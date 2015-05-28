/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.order;

import application.local.LocalApplication;
import application.order.OrderApplication;
import entity.GuiaRemision;
import entity.Local;
import entity.Pedido;
import entity.PedidoParcial;
import entity.PedidoParcialXProducto;
import entity.Producto;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import util.EntityState;
import util.EntityType;
import util.Strings;

/**
 *
 * @author Alonso
 */
public class OrderView extends javax.swing.JInternalFrame implements MouseListener,ItemListener {
    OrderApplication orderApplication = new OrderApplication();
    LocalApplication localApplication = new LocalApplication();
    public static OrderView orderView;
    ArrayList<Local> locals = new ArrayList<>();
    ArrayList<Producto> orderProducts;
    ArrayList<Integer> productQuantities;
    String[] clientNames;
    String[] localNames;
    Integer selectedRowIndex = 0;
    Integer selectedProductRowIndex = 0;
    /**
     * Creates new form OrderView
     */
    public OrderView() {
        initComponents();
        orderView = this;
        setupElements();
        setupListeners();
    }

    public void setupElements(){
        initializeArrays();
        fillCombos();
        refreshTable();
    }

    public void initializeArrays(){
        orderProducts = new ArrayList<>();
        productQuantities = new ArrayList<>();
    }
    
    public void fillCombos(){
        fillClientNames();
        clientCombo.setModel(new javax.swing.DefaultComboBoxModel(clientNames));
    }
    
    public void fillClientNames(){
        clientNames =  new String[EntityType.CLIENTS.size() + 1];
        for (int i=0; i < EntityType.CLIENTS.size() + 1; i++){
            if (i == 0){
                clientNames[i] = "";
            }
            else{
                clientNames[i] = EntityType.CLIENTS.get(i-1).getNombre();
            }
        }
    }
    
    public void fillLocalNames(){
        localNames =  new String[locals.size()];
        for (int i=0; i < locals.size(); i++){
                localNames[i] = locals.get(i).getNombre();
        }
    } 
    
    public void fillLocalCombo(){
        locals = localApplication.queryLocalsByClient(EntityType.CLIENTS.get(clientCombo.getSelectedIndex()-1).getId());
        fillLocalNames();
        localCombo.setModel(new javax.swing.DefaultComboBoxModel(localNames));
    }
    
    public void refreshTable(){
        ArrayList<String> cols = new ArrayList<>();
        for (int i = 0; i<orderTable.getColumnCount(); i++)
            cols.add(orderTable.getColumnName(i));
        DefaultTableModel tableModel = new DefaultTableModel(cols.toArray(), 0);
        orderTable.setModel(tableModel);
        EntityType.ORDERS.stream().forEach((_order) -> {
            Object[] row = {_order.getId(), orderApplication.getOrderClient(_order.getId()).getNombre()
                    , _order.getLocal().getNombre(),EntityState.getOrdersState()[_order.getEstado()]};
            tableModel.addRow(row);
        });
    }
    
    public void setupListeners() {
        jScrollPane2.addMouseListener(this);
        orderTable.addMouseListener(this);
        productTable.addMouseListener(this);
        clientCombo.addItemListener(this);
    }
    
    /*ORDER METHODS*/
    
    public void createOrder(){
        Pedido p = new Pedido();
        p.setEstado(1);
        p.setCliente(EntityType.CLIENTS.get(clientCombo.getSelectedIndex() - 1));
        p.setLocal(locals.get(localCombo.getSelectedIndex()));
        
        PedidoParcial pp = new PedidoParcial();
        pp.setEstado(1);
        pp.setPedido(p);
        
        ArrayList<PedidoParcialXProducto> partialProducts = new ArrayList<>(); 
        for(int i=0;i<orderProducts.size();i++){
            PedidoParcialXProducto partialProduct = new PedidoParcialXProducto();
            partialProduct.setCantidad(productQuantities.get(i));
            partialProduct.setPedidoParcial(pp);
            partialProduct.setProducto(orderProducts.get(i));
            
            partialProducts.add(partialProduct);
        }
        
        if (orderApplication.CreateOrder(p, pp, partialProducts))
            JOptionPane.showMessageDialog(this, Strings.MESSAGE_CREATE_ORDER,
                    Strings.MESSAGE_CREATE_ORDER_TITLE,JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void deleteOrder(){
        EntityType.ORDERS.get(currentOrderIndex()).setEstado(0);
        if(orderApplication.updateOrder(EntityType.ORDERS.get(currentOrderIndex()))){
            orderApplication.refreshOrders();
            refreshTable();
            JOptionPane.showMessageDialog(this, Strings.MESSAGE_DELETE_ORDER,
                    Strings.MESSAGE_DELETE_ORDER_TITLE,JOptionPane.INFORMATION_MESSAGE);
            deleteBtn.setEnabled(false);
        }
    }
    
    /*PRODUCT METHODS*/
    
    public void addProduct(Producto p, Integer q){
        if(orderProducts.contains(p)){
            int index = orderProducts.indexOf(p);
            productQuantities.set(index, q + productQuantities.get(index));
            if (productQuantities.get(index) > p.getStockTotal())
                productQuantities.set(index, p.getStockTotal());
        }
        else{
            orderProducts.add(p);
            if (q > p.getStockTotal()){
                productQuantities.add(p.getStockTotal()); 
            }else{
                productQuantities.add(q); 
            }
        }
        refreshProductTable();
    }
    
    public void deleteProduct(int index){
        ArrayList<Producto> products = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();
        for(int i=0;i<orderProducts.size();i++){
            if(i != index){
                products.add(orderProducts.get(i));
                quantities.add(productQuantities.get(i));
            }
        }
        orderProducts = products;
        productQuantities = quantities;
    }
    
        public void refreshProductTable(){
        ArrayList<String> cols = new ArrayList<>();
        for (int i = 0; i<productTable.getColumnCount(); i++)
            cols.add(productTable.getColumnName(i));
        DefaultTableModel tableModel = new DefaultTableModel(cols.toArray(), 0);
        productTable.setModel(tableModel);
        orderProducts.stream().forEach((_product) -> {
            System.out.println(productQuantities.get(0));
            System.out.println("index" + orderProducts.indexOf(_product));
            Object[] row = {_product.getId(), _product.getNombre(), _product.getCondicion().getNombre(), productQuantities.get(orderProducts.indexOf(_product))};
            tableModel.addRow(row);
        });
    }
    
    public int currentOrderIndex(){
        return orderTable.getSelectedRow();
    }
    
    public int currentProductIndex(){
        System.out.println(productTable.getSelectedRow());
        return productTable.getSelectedRow();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        deleteBtn = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        fileTextField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        localCombo = new javax.swing.JComboBox();
        jScrollPanel = new javax.swing.JScrollPane();
        productTable = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        removeBtn = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        clientCombo = new javax.swing.JComboBox();

        setClosable(true);

        jScrollPane2.setEnabled(false);
        jScrollPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jScrollPane2MousePressed(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jScrollPane2MouseEntered(evt);
            }
        });

        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Codigo", "Cliente", "Local", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(orderTable);
        if (orderTable.getColumnModel().getColumnCount() > 0) {
            orderTable.getColumnModel().getColumn(0).setResizable(false);
            orderTable.getColumnModel().getColumn(1).setResizable(false);
            orderTable.getColumnModel().getColumn(2).setResizable(false);
            orderTable.getColumnModel().getColumn(3).setResizable(false);
        }

        deleteBtn.setText("Anular");
        deleteBtn.setEnabled(false);
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Carga Masiva"));

        jLabel1.setText("Ingresar Pedidos desde un archivo:");

        jButton1.setText("Seleccionar Archivo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(fileTextField)
                .addGap(24, 24, 24)
                .addComponent(jButton1))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(fileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(34, 34, 34)))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                    .addComponent(deleteBtn)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(deleteBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        jTabbedPane1.addTab("Carga Masiva", jPanel1);

        jLabel2.setText("Cliente:");

        jLabel3.setText("Local:");

        localCombo.setEnabled(false);

        productTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Producto", "Nombre", "Condición", "Cantidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPanel.setViewportView(productTable);

        jButton2.setText("<<Agregar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        removeBtn.setText("Quitar>>");
        removeBtn.setEnabled(false);
        removeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBtnActionPerformed(evt);
            }
        });

        jButton5.setText("Guardar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        clientCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clientCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(localCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(removeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(0, 3, Short.MAX_VALUE))
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(localCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clientCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(removeBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                        .addComponent(jButton5))
                    .addComponent(jScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Nuevo Pedido", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Nuevo Pedido");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        NewOrderProduct newOrderProduct = new NewOrderProduct((JFrame)SwingUtilities.getWindowAncestor(this),true);
        newOrderProduct.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Seleccione un archivo");
        fc.showOpenDialog(this);
        File file = fc.getSelectedFile();
        if (!fc.getSelectedFile().getName().endsWith(".csv")) {
          JOptionPane.showMessageDialog(this, "El archivo seleccionado no es un archivo CSV.");
        }
        else
            fileTextField.setText(file.getAbsolutePath());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        int response = JOptionPane.showConfirmDialog(this, Strings.MESSAGE_CONFIRM_DELETE_ORDER,Strings.MESSAGE_DELETE_ORDER_TITLE,JOptionPane.WARNING_MESSAGE);
        if(JOptionPane.OK_OPTION == response){
            deleteOrder();
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void removeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBtnActionPerformed
        deleteProduct(currentProductIndex());
        refreshProductTable();
    }//GEN-LAST:event_removeBtnActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        createOrder();
        orderApplication.refreshOrders();
        initializeArrays();
        refreshProductTable();
        refreshTable();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jScrollPane2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MousePressed

    }//GEN-LAST:event_jScrollPane2MousePressed

    private void jScrollPane2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MouseEntered

    }//GEN-LAST:event_jScrollPane2MouseEntered


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox clientCombo;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JTextField fileTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPanel;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox localCombo;
    private javax.swing.JTable orderTable;
    private javax.swing.JTable productTable;
    private javax.swing.JButton removeBtn;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("MouseCliced" + orderTable.getSelectedRow() + e.getSource().getClass().getName());
        JTable target = (JTable)e.getSource();
        if(target != null && orderTable.getSelectedRow() != -1){
            if(target.getColumnName(3).equals("Cantidad")){
                removeBtn.setEnabled(true);
            }
            else{
                if(EntityType.ORDERS.get(currentOrderIndex()).getEstado() != 0){
                    deleteBtn.setEnabled(true);
                }else
                    deleteBtn.setEnabled(false);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
          Object item = e.getItem();
          if(clientCombo.getSelectedIndex() != 0){
              localCombo.setEnabled(true);
              fillLocalCombo();
          }else
              localCombo.setEnabled(false);
        }
    }
    
}
