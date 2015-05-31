/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.order;

import application.local.LocalApplication;
import application.order.OrderApplication;
import application.product.ProductApplication;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import util.EntityType;
import client.order.OrderView;
import entity.Local;
import entity.Pedido;
import entity.PedidoParcial;
import entity.PedidoParcialXProducto;
import entity.PedidoParcialXProductoId;
import entity.Producto;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import util.Strings;

/**
 *
 * @author Alonso
 */
public class NewOrderProduct extends javax.swing.JDialog implements MouseListener,ItemListener{
    OrderApplication orderApplication = new OrderApplication();
    ProductApplication productApplication = new ProductApplication();
    LocalApplication localApplication = new LocalApplication();
    Border errorBorder = BorderFactory.createLineBorder(Color.RED, 1);
    Border regularBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
    ArrayList<Local> locals = new ArrayList<>();
    ArrayList<Producto> orderProducts;
    ArrayList<Producto> productsToAdd;
    ArrayList<Integer> productQuantities;
    String[] clientNames;
    String[] localNames;
    String error_message;
    /**
     * Creates new form NewOrderProduct
     */
    public NewOrderProduct(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setupElements();
    }

    public void setupElements(){
        initializeArrays();
        productApplication.refreshProducts();
        qtySpinner.setValue(1);
        productsToAdd = getAvailableProducts();
        refreshProductsToAddTable();
        fillCombos();
        setupListeners();
    }
    
    public void initializeArrays(){
        orderProducts = new ArrayList<>();
        productQuantities = new ArrayList<>();
    }
    
    /*
     * ComboBox Configuration
     */
    public void fillCombos(){
        fillClientNames();
        clientCombo.setModel(new javax.swing.DefaultComboBoxModel(clientNames));
    }
    
    public void fillClientNames(){
        clientNames =  new String[EntityType.CLIENTS.size() + 1];
        for (int i=0; i < EntityType.CLIENTS.size() + 1; i++){
            if (i == 0)
                clientNames[i] = "";
            else
                clientNames[i] = EntityType.CLIENTS.get(i-1).getNombre();
        }
    }
    
    public void fillLocalNames(){
        localNames =  new String[locals.size()];
        for (int i=0; i < locals.size(); i++)
                localNames[i] = locals.get(i).getNombre();
    } 
    
    public void fillLocalCombo(){
        locals = localApplication.queryLocalsByClient(EntityType.CLIENTS.get(clientCombo.getSelectedIndex()-1).getId());
        fillLocalNames();
        localCombo.setModel(new javax.swing.DefaultComboBoxModel(localNames));
    }

    /*
     * Listeners Configuration
     */   
    public void setupListeners(){
        productAddTable.addMouseListener(this);
        productTable.addMouseListener(this);
        clientCombo.addItemListener(this);
    }
    
    /*
     * Product Methods
     */   
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
        refreshOrderProductsTable();
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
        removeBtn.setEnabled(false);
    }
    
    public ArrayList<Producto> getAvailableProducts(){
        ArrayList<Producto> products = new ArrayList<>();
        for(int i=0;i<EntityType.PRODUCTS.size();i++)
            if(EntityType.PRODUCTS.get(i).getStockTotal()>0)
                products.add(EntityType.PRODUCTS.get(i));
        return products;
    }
    
    /*
     * Table Methods
     */ 
    public void refreshOrderProductsTable(){
        ArrayList<String> cols = new ArrayList<>();
        for (int i = 0; i<productTable.getColumnCount(); i++)
            cols.add(productTable.getColumnName(i));
        DefaultTableModel tableModel = new DefaultTableModel(cols.toArray(), 0);
        productTable.setModel(tableModel);
        orderProducts.stream().forEach((_product) -> {
            Object[] row = {_product.getId(), _product.getNombre(), _product.getCondicion().getNombre(), productQuantities.get(orderProducts.indexOf(_product))};
            tableModel.addRow(row);
        });
    }
    
    public void refreshProductsToAddTable(){
        ArrayList<String> cols = new ArrayList<>();
        for (int i = 0; i<productAddTable.getColumnCount(); i++)
            cols.add(productAddTable.getColumnName(i));
        DefaultTableModel tableModel = new DefaultTableModel(cols.toArray(), 0);
        productAddTable.setModel(tableModel);
        productsToAdd.stream().forEach((_product) -> {
            Object[] row = {_product.getId(), _product.getNombre(), _product.getCondicion().getNombre(), _product.getStockTotal()};
            tableModel.addRow(row);
        });
    }
    
    public int currentProductIndex(){
        return productTable.getSelectedRow();
    }
    
    /*
     * Order Methods
     */ 
    public Boolean createOrder(){
        Pedido p = new Pedido();
        p.setEstado(1);
        p.setCliente(EntityType.CLIENTS.get(clientCombo.getSelectedIndex() - 1));
        p.setLocal(locals.get(localCombo.getSelectedIndex()));
        
        Date date = new Date();
        p.setFecha(date);
        
        PedidoParcial pp = new PedidoParcial();
        pp.setEstado(1);
        pp.setPedido(p);
        
        ArrayList<PedidoParcialXProducto> partialProducts = new ArrayList<>(); 
        for(int i=0;i<orderProducts.size();i++){
            PedidoParcialXProducto partialProduct = new PedidoParcialXProducto();
            PedidoParcialXProductoId id = new PedidoParcialXProductoId();
            
            id.setIdProducto(orderProducts.get(i).getId());
            
            partialProduct.setId(id);
            partialProduct.setCantidad(productQuantities.get(i));
            partialProduct.setPedidoParcial(pp);
            partialProduct.setProducto(orderProducts.get(i));
            
            partialProducts.add(partialProduct);
        }
        
        return orderApplication.CreateOrder(p, pp, partialProducts);
    }
    
    /*
     * Input Fields Methods
     */
    
    public Boolean validFields(){  
        productContainer.setBorder(regularBorder);
        error_message = "Errores:\n";
        JOptionPane.setDefaultLocale(new Locale("es", "ES"));
        boolean valid = true;
        if(clientCombo.getSelectedIndex() == 0){
            error_message += Strings.ERROR_CREATE_ORDER_NO_CLIENTE+"\n";
            valid = false; 
        }
        if(orderProducts.size() == 0){
            error_message += Strings.ERROR_CREATE_ORDER_NO_PRODUCTS+"\n";
            productContainer.setBorder(errorBorder);
            valid = false;
        }
        return valid;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        codPorductTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        productTxt = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        productAddTable = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        qtySpinner = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        localCombo = new javax.swing.JComboBox();
        productContainer = new javax.swing.JScrollPane();
        productTable = new javax.swing.JTable();
        addBtn = new javax.swing.JButton();
        removeBtn = new javax.swing.JButton();
        clientCombo = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        codPorductTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codPorductTxtActionPerformed(evt);
            }
        });

        jLabel2.setText("Producto:");

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        productAddTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Código", "Nombre", "Condición", "Stock Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        productAddTable.setName("productAddTable"); // NOI18N
        productAddTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productAddTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(productAddTable);
        if (productAddTable.getColumnModel().getColumnCount() > 0) {
            productAddTable.getColumnModel().getColumn(0).setResizable(false);
            productAddTable.getColumnModel().getColumn(1).setResizable(false);
            productAddTable.getColumnModel().getColumn(2).setResizable(false);
            productAddTable.getColumnModel().getColumn(3).setResizable(false);
        }

        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Cantidad de Pallets:");

        qtySpinner.setEnabled(false);

        jLabel3.setText("*Cliente:");

        jLabel5.setText("*Local:");

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
        productTable.setName("productTable"); // NOI18N
        productTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productTableMouseClicked(evt);
            }
        });
        productContainer.setViewportView(productTable);
        if (productTable.getColumnModel().getColumnCount() > 0) {
            productTable.getColumnModel().getColumn(0).setResizable(false);
            productTable.getColumnModel().getColumn(1).setResizable(false);
            productTable.getColumnModel().getColumn(2).setResizable(false);
            productTable.getColumnModel().getColumn(3).setResizable(false);
        }

        addBtn.setText("<<Agregar");
        addBtn.setEnabled(false);
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        removeBtn.setText("Quitar>>");
        removeBtn.setEnabled(false);
        removeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBtnActionPerformed(evt);
            }
        });

        clientCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(productContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(removeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clientCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(localCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(localCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clientCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(addBtn)
                        .addGap(8, 8, 8)
                        .addComponent(removeBtn)
                        .addGap(6, 286, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(productContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 31, Short.MAX_VALUE))))
        );

        jLabel1.setText("Codigo:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(codPorductTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(productTxt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(qtySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(codPorductTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(productTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)
                            .addComponent(jLabel1))
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(qtySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(validFields()){   
            if(createOrder()){
                JOptionPane.showMessageDialog(this, Strings.MESSAGE_CREATE_ORDER,
                    Strings.MESSAGE_CREATE_ORDER_TITLE,JOptionPane.INFORMATION_MESSAGE);
                initializeArrays();
                refreshOrderProductsTable();
                refreshProductsToAddTable();
                OrderView.orderView.refreshOrders();
                this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(this, Strings.ERROR_CREATE_ORDER,Strings.MESSAGE_CREATE_ORDER_TITLE,JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
            JOptionPane.showMessageDialog(this, error_message,Strings.MESSAGE_CREATE_ORDER_TITLE,JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void productAddTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productAddTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_productAddTableMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Producto product = new Producto();
        if(codPorductTxt.getText().length() == 0)
            product.setId(null);
        else
            product.setId(Integer.parseInt(codPorductTxt.getText()));
        product.setNombre(productTxt.getText());
        productsToAdd = productApplication.searchProduct(product);
        refreshProductsToAddTable();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        addProduct(productsToAdd.get(productAddTable.getSelectedRow()), (Integer)qtySpinner.getValue());
        productContainer.setBorder(regularBorder);
        refreshOrderProductsTable();
    }//GEN-LAST:event_addBtnActionPerformed

    private void removeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBtnActionPerformed
        deleteProduct(currentProductIndex());
        refreshOrderProductsTable();
    }//GEN-LAST:event_removeBtnActionPerformed

    private void codPorductTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codPorductTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codPorductTxtActionPerformed

    private void productTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_productTableMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewOrderProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewOrderProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewOrderProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewOrderProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NewOrderProduct dialog = new NewOrderProduct(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JComboBox clientCombo;
    private javax.swing.JTextField codPorductTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox localCombo;
    private javax.swing.JTable productAddTable;
    private javax.swing.JScrollPane productContainer;
    private javax.swing.JTable productTable;
    private javax.swing.JTextField productTxt;
    private javax.swing.JSpinner qtySpinner;
    private javax.swing.JButton removeBtn;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        JTable target = (JTable)e.getSource();
        if(productAddTable.getSelectedRow() != -1){
            qtySpinner.setEnabled(true);
            addBtn.setEnabled(true); 
        }
        System.out.println(productTable.getSelectedRow());
        if(productTable.getSelectedRow() != -1){
            removeBtn.setEnabled(true); 
        }
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
