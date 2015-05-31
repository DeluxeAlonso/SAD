/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.order;

import application.local.LocalApplication;
import application.order.OrderApplication;
import entity.Cliente;
import entity.GuiaRemision;
import entity.Local;
import entity.Pedido;
import entity.PedidoParcial;
import entity.PedidoParcialXProducto;
import entity.PedidoParcialXProductoId;
import entity.Producto;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    ArrayList<Pedido> currentOrders;
    ArrayList<PedidoParcial> currentPartialOrders;
    ArrayList<Producto> partialProducts;
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
        /*ArrayList<PedidoParcial> partials = new ArrayList<>();
        partials = orderApplication.getPendingPartialOrders();
        for(int i=0;i<partials.size();i++){
            System.out.println(partials.get(i).getPedido().getId() + " " + partials.get(i).getPedido().getEstado());
            ArrayList<PedidoParcialXProducto> products = new ArrayList<>();
            products = orderApplication.queryAllPartialOrderProducts(partials.get(i).getId());
            for(int j=0; j<products.size(); j++){
                System.out.println(products.get(j).getProducto().getNombre());
            }
        }*/
        orderApplication.refreshOrders();
        currentOrders = EntityType.ORDERS;
        fillCombos();
        refreshTable();
    }

    public void initializeArrays(){
        orderProducts = new ArrayList<>();
        productQuantities = new ArrayList<>();
    }
    
    public void fillCombos(){
        fillClientNames();
        filterClientCombo.setModel(new javax.swing.DefaultComboBoxModel(clientNames));
        String []status = new String[5];
        status[0] = "";
        for(int i=1;i<=EntityState.getOrdersState().length;i++)
            status[i] = EntityState.getOrdersState()[i-1];
        statusCombo.setModel(new javax.swing.DefaultComboBoxModel(status)); 
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
        localNames =  new String[locals.size() + 1];
        for (int i=0; i < locals.size() + 1; i++){
            if(i == 0)
                localNames[i] = "";
            else
                localNames[i] = locals.get(i-1).getNombre();
        }
    } 
    
    public void fillLocalCombo(){
        locals = localApplication.queryLocalsByClient(EntityType.CLIENTS.get(filterClientCombo.getSelectedIndex()-1).getId());
        fillLocalNames();
        filterLocalCombo.setModel(new javax.swing.DefaultComboBoxModel(localNames));
    }
    
    public void refreshTable(){
        ArrayList<String> cols = new ArrayList<>();
        for (int i = 0; i<orderTable.getColumnCount(); i++)
            cols.add(orderTable.getColumnName(i));
        DefaultTableModel tableModel = new DefaultTableModel(cols.toArray(), 0);
        orderTable.setModel(tableModel);
        currentOrders.stream().forEach((_order) -> {
            Object[] row = {_order.getId(), _order.getCliente().getNombre()
                    , _order.getLocal().getNombre(),EntityState.getOrdersState()[_order.getEstado()]};
            tableModel.addRow(row);
        });
    }
    
    public void setupListeners() {
        jScrollPane2.addMouseListener(this);
        orderTable.addMouseListener(this);
        productTable.addMouseListener(this);
        filterClientCombo.addItemListener(this);
    }
    
    /*ORDER METHODS*/
    
    public void deleteOrder(){
        EntityType.ORDERS.get(currentOrderIndex()).setEstado(0);
        if(orderApplication.updateOrder(EntityType.ORDERS.get(currentOrderIndex()))){
            refreshOrders();
            JOptionPane.showMessageDialog(this, Strings.MESSAGE_DELETE_ORDER,
                    Strings.MESSAGE_DELETE_ORDER_TITLE,JOptionPane.INFORMATION_MESSAGE);
            deleteBtn.setEnabled(false);
        }
    }
    
    /*PRODUCT METHODS*/
    
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
    
    public void refreshProductTable(Integer partialId){
        ArrayList<String> cols = new ArrayList<>();
        for (int i = 0; i<productTable.getColumnCount(); i++)
            cols.add(productTable.getColumnName(i));
        DefaultTableModel tableModel = new DefaultTableModel(cols.toArray(), 0);
        productTable.setModel(tableModel);
        ArrayList<PedidoParcialXProducto> products = new ArrayList<>();
        products = orderApplication.queryAllPartialOrderProducts(partialId);
        products.stream().forEach((_product) -> {
            Object[] row = {_product.getProducto().getId(), _product.getProducto().getNombre(),
                _product.getProducto().getCondicion().getNombre(), _product.getCantidad()};
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
    
    public void refreshOrders(){
        orderApplication.refreshOrders();
        currentOrders = EntityType.ORDERS;
        refreshTable();
    }
    
    public void fillDetailFields(){
        clearDetailFields();
        Pedido order = currentOrders.get(orderTable.getSelectedRow());
        detailClientTxt.setText(order.getCliente().getNombre());
        rucTxt.setText(order.getCliente().getRuc());
        codLocalTxt.setText(order.getLocal().getId().toString());
        localTxt.setText(order.getLocal().getNombre());
        direccionTxt.setText(order.getLocal().getDireccion());
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateTxt.setText(dateFormat.format(order.getFecha()));
        
        detailStatusCombo.setModel(new javax.swing.DefaultComboBoxModel(EntityState.getOrdersState()));
        detailStatusCombo.setSelectedIndex(order.getEstado());
        
        currentPartialOrders = orderApplication.getPendingPartialOrdersById(currentOrders.get(orderTable.getSelectedRow()).getId());
        String[]partialOrders = new String[currentPartialOrders.size() + 1];
        
        for(int i=0;i<currentPartialOrders.size()+1;i++){
            if(i == 0)
                partialOrders[i] = "Todos";
            else
                partialOrders[i] = "Pedido " + i;
        }
        partialCombo.setModel(new javax.swing.DefaultComboBoxModel(partialOrders));
        
        refreshProductTable(currentPartialOrders.get(0).getId());
        
        for(int i=0;i<currentPartialOrders.size();i++){
            ArrayList<PedidoParcialXProducto> products = new ArrayList<>();
            products = orderApplication.queryAllPartialOrderProducts(currentPartialOrders.get(i).getId());
            for(int j=0; j<products.size(); j++){
                System.out.println(products.get(j).getProducto().getNombre());
            }
        }
        
    }
    
    public void clearDetailFields(){
        detailClientTxt.setText("");
        rucTxt.setText("");
        localTxt.setText("");
        codLocalTxt.setText("");
        direccionTxt.setText("");
        dateTxt.setText("");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        deleteBtn = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        fileTextField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        filterIdTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        statusCombo = new javax.swing.JComboBox();
        filterClientCombo = new javax.swing.JComboBox();
        filterLocalCombo = new javax.swing.JComboBox();
        searchBtn = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        productTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        detailClientTxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        rucTxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        codLocalTxt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        localTxt = new javax.swing.JTextField();
        direccionTxt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        dateTxt = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        detailStatusCombo = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        partialCombo = new javax.swing.JComboBox();
        jButton5 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Pedidos");

        deleteBtn.setText("Anular");
        deleteBtn.setEnabled(false);
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Carga Masiva"));

        jLabel1.setText("Ingresar Pedidos desde un archivo:");

        jButton1.setEnabled(false);
        jButton1.setLabel("Cargar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("...");
        jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(0, 186, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(fileTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(fileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(34, 34, 34)))
                .addGap(0, 11, Short.MAX_VALUE))
        );

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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros"));

        jLabel2.setText("Codigo:");

        jLabel3.setText("Cliente:");

        jLabel4.setText("Local:");

        jLabel5.setText("Estado:");

        statusCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusComboActionPerformed(evt);
            }
        });

        filterClientCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        filterLocalCombo.setEnabled(false);

        searchBtn.setText("Buscar");
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filterIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(statusCombo, 0, 176, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(filterLocalCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filterClientCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(searchBtn)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(filterIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(statusCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(filterClientCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(filterLocalCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(searchBtn))
        );

        jButton3.setText("Nuevo");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        productTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "Condicion", "Cantidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(productTable);
        if (productTable.getColumnModel().getColumnCount() > 0) {
            productTable.getColumnModel().getColumn(0).setResizable(false);
            productTable.getColumnModel().getColumn(1).setResizable(false);
            productTable.getColumnModel().getColumn(2).setResizable(false);
            productTable.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle Pedido"));

        jLabel6.setText("Cliente:");

        detailClientTxt.setEnabled(false);

        jLabel7.setText("RUC:");

        rucTxt.setEnabled(false);

        jLabel8.setText("Cod. Local:");

        codLocalTxt.setEnabled(false);

        jLabel9.setText("Direccion:");

        jLabel10.setText("Local:");

        localTxt.setEnabled(false);

        direccionTxt.setEnabled(false);

        jLabel11.setText("Emision:");

        dateTxt.setEnabled(false);

        jLabel12.setText("Estado:");

        detailStatusCombo.setEnabled(false);

        jLabel13.setText("P. Parcial:");

        jButton5.setText("Ver productos");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel6))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(direccionTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(detailClientTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                                    .addComponent(codLocalTxt))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(22, 22, 22)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rucTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(localTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(partialCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(dateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(detailStatusCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(detailClientTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(rucTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(codLocalTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(localTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(direccionTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(detailStatusCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(partialCombo, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteBtn)
                        .addGap(453, 453, 453))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(deleteBtn))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            clearDetailFields();
            detailStatusCombo.setSelectedIndex(1);
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void jScrollPane2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MousePressed

    }//GEN-LAST:event_jScrollPane2MousePressed

    private void jScrollPane2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MouseEntered

    }//GEN-LAST:event_jScrollPane2MouseEntered

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        NewOrderProduct newOrderProductView = new NewOrderProduct((JFrame) SwingUtilities.getWindowAncestor(this), true);
        newOrderProductView.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void statusComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusComboActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        Pedido order = new Pedido();
        if(filterIdTxt.getText().length() == 0)
            order.setId(null);
        else
            order.setId(Integer.parseInt(filterIdTxt.getText()));
        Cliente client = new Cliente();
        if(filterClientCombo.getSelectedIndex() == 0)
            client.setId(null);
        else
            client.setId(EntityType.CLIENTS.get(filterClientCombo.getSelectedIndex() - 1).getId());
        order.setCliente(client);
        Local local = new Local();
        if(filterLocalCombo.isEnabled() && filterLocalCombo.getSelectedIndex() != 0)
            local.setId(locals.get(filterLocalCombo.getSelectedIndex() - 1).getId());
        else
            local.setId(null);
        order.setLocal(local);
        if(statusCombo.getSelectedIndex() == 0)
            order.setEstado(null);
        else
            order.setEstado(statusCombo.getSelectedIndex()-1);
        currentOrders = orderApplication.searchOrders(order);
        refreshTable();
        clearDetailFields();
    }//GEN-LAST:event_searchBtnActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if(partialCombo.getSelectedIndex()==0){
            
        }
        else{
            refreshProductTable(currentPartialOrders.get(partialCombo.getSelectedIndex()-1).getId());
        }
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jScrollPane2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MousePressed

    }//GEN-LAST:event_jScrollPane2MousePressed

    private void jScrollPane2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MouseEntered

    }//GEN-LAST:event_jScrollPane2MouseEntered


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField codLocalTxt;
    private javax.swing.JFormattedTextField dateTxt;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JTextField detailClientTxt;
    private javax.swing.JComboBox detailStatusCombo;
    private javax.swing.JTextField direccionTxt;
    private javax.swing.JTextField fileTextField;
    private javax.swing.JComboBox filterClientCombo;
    private javax.swing.JTextField filterIdTxt;
    private javax.swing.JComboBox filterLocalCombo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField localTxt;
    private javax.swing.JTable orderTable;
    private javax.swing.JComboBox partialCombo;
    private javax.swing.JTable productTable;
    private javax.swing.JTextField rucTxt;
    private javax.swing.JButton searchBtn;
    private javax.swing.JComboBox statusCombo;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("MouseCliced" + orderTable.getSelectedRow() + e.getSource().getClass().getName());
        JTable target = (JTable)e.getSource();
        if(target != null && orderTable.getSelectedRow() != -1){
            if(target.getColumnName(3).equals("Cantidad")){
                //removeBtn.setEnabled(true);
            }
            else{
                fillDetailFields();
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
          if(filterClientCombo.getSelectedIndex() != 0){
              filterLocalCombo.setEnabled(true);
              fillLocalCombo();
          }else
              filterLocalCombo.setEnabled(false);
        }
    }
    
}
