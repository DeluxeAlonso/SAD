/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.order;

import algorithm.AlgorithmExecution;
import application.client.ClientApplication;
import application.local.LocalApplication;
import application.order.OrderApplication;
import application.pallet.PalletApplication;
import application.product.ProductApplication;
import client.base.BaseView;
import entity.Cliente;
import entity.GuiaRemision;
import entity.Local;
import entity.Pallet;
import entity.Pedido;
import entity.PedidoParcial;
import entity.PedidoParcialXProducto;
import entity.PedidoParcialXProductoId;
import entity.Producto;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import util.EntityState;
import util.EntityType;
import util.Icons;
import util.Strings;

/**
 *
 * @author Alonso
 */
public class OrderView extends BaseView implements MouseListener,ItemListener {
    OrderApplication orderApplication = new OrderApplication();
    LocalApplication localApplication = new LocalApplication();
    ProductApplication productApplication = new ProductApplication();
    ClientApplication clientApplication = new ClientApplication();
    AlgorithmExecution algorithmExecution = new AlgorithmExecution();
    PalletApplication palletApplication = new PalletApplication();
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
        super.initialize();
        orderView = this;
        setupElements();
        setupListeners();
    }

    public void setupElements(){
        currentOrders = orderApplication.getAllOrders();
        fillCombos();
        refreshTable();
        Icons.setButton(newBtn, Icons.ICONOS.CREATE.ordinal());
        Icons.setButton(searchBtn, Icons.ICONOS.SEARCH.ordinal());
        Icons.setButton(deleteBtn, Icons.ICONOS.DELETE.ordinal());
        Icons.setButton(deletePartialBtn, Icons.ICONOS.DELETE.ordinal());
        Icons.setButton(deliverBtn, Icons.ICONOS.DELIVERY.ordinal());
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
        filterClientCombo.setModel(new javax.swing.DefaultComboBoxModel(clientNames));
        String []status = new String[5];
        status[0] = "";
        for(int i=1;i<=EntityState.getOrdersState().length;i++)
            status[i] = EntityState.getOrdersState()[i-1];
        statusCombo.setModel(new javax.swing.DefaultComboBoxModel(status)); 
    }
    
    public void fillClientNames(){
        clientApplication.refreshClients();
        clientNames =  new String[EntityType.CLIENTS.size() + 1];
        for (int i=0; i < EntityType.CLIENTS.size() + 1; i++){
            if (i == 0)
                clientNames[i] = "";
            else
                clientNames[i] = EntityType.CLIENTS.get(i-1).getNombre();
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
    
    /*
     * Listeners Configuration
     */
    public void setupListeners() {
        jScrollPane2.addMouseListener(this);
        orderTable.addMouseListener(this);
        productTable.addMouseListener(this);
        filterClientCombo.addItemListener(this);
        partialCombo.addItemListener(this);
    } 
    
    /*
     * Table Methods
     */
    public void refreshTable(){
        DefaultTableModel tableModel = generateTableModel(orderTable);
        currentOrders.stream().forEach((_order) -> {
            Object[] row = {_order.getId(), _order.getCliente().getNombre()
                    , _order.getLocal().getNombre(),EntityState.getOrdersState()[_order.getEstado()]};
            tableModel.addRow(row);
        });
    }
    
    public void refreshProductTable(Integer partialId){
        DefaultTableModel tableModel = generateTableModel(productTable);
        ArrayList<PedidoParcialXProducto> products = new ArrayList<>();
        products = orderApplication.queryAllPartialOrderProducts(partialId);
        products.stream().forEach((_product) -> {
            Object[] row = {_product.getProducto().getId(), _product.getProducto().getNombre(),
                _product.getProducto().getCondicion().getNombre(), _product.getCantidad()};
            tableModel.addRow(row);
        });
    }
    
    public void refreshAllProductsTable(Integer orderId){
        DefaultTableModel tableModel = generateTableModel(productTable);
        ArrayList<PedidoParcialXProducto> products = new ArrayList<>();
        products = orderApplication.queryAllProductsByOrderId(orderId);
        products.stream().forEach((_product) -> {
            Object[] row = {_product.getProducto().getId(), _product.getProducto().getNombre(),
                _product.getProducto().getCondicion().getNombre(), _product.getCantidad()};
            tableModel.addRow(row);
        });
    }
    
    public DefaultTableModel generateTableModel(JTable table){
        ArrayList<String> cols = new ArrayList<>();
        for (int i = 0; i<table.getColumnCount(); i++)
            cols.add(table.getColumnName(i));
        DefaultTableModel model = new DefaultTableModel(cols.toArray(), 0);
        table.setModel(model);
        return model;
    }
    
    /*
     * Order Methods
     */
    public void deleteOrder(){
        Pedido currentOrder = currentOrders.get(currentOrderIndex());
        currentOrder.setEstado(0);
        if(orderApplication.updateOrder(currentOrder)){
            refreshOrders();
            JOptionPane.showMessageDialog(this, Strings.MESSAGE_DELETE_ORDER,
                    Strings.MESSAGE_DELETE_ORDER_TITLE,JOptionPane.INFORMATION_MESSAGE);
            deleteBtn.setEnabled(false);
        }
    }
    
    /*
     * Product Methods
     */
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
    
    /*
     * Input Fields Methods
     */
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
        
        partialCombo.setEnabled(true);
        
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
        partialStatusTxt.setText("");
        partialCombo.setEditable(false);
        reasonCombo.setEnabled(false);
        deletePartialBtn.setEnabled(false);
        refreshAllProductsTable(0);
    }
    
    /*
     * JTable Handling Methods
     */   
    
    public int currentOrderIndex(){
        return orderTable.getSelectedRow();
    }
    
    public int currentProductIndex(){
        System.out.println(productTable.getSelectedRow());
        return productTable.getSelectedRow();
    }
    
    public void refreshOrders(){
        currentOrders = orderApplication.getAllOrders();
        refreshTable();
    }
    
    /*
     * File Methods
     */
    public void loadFile(String route){
        BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
        Boolean has_errors = false;
        try {
            Pedido order;
            br = new BufferedReader(new FileReader(route));
            // Leo la cantidad de clientes que hay en el archivo
            line = br.readLine();
            String[] line_split = line.split(cvsSplitBy);
            int ordersNum = Integer.parseInt(line_split[0]);
            int productsNum;
            for(int i=0;i<ordersNum;i++){
                //Leo un pedido y lo inserto
                line = br.readLine();
                line_split = line.split(cvsSplitBy);
                
                ArrayList<PedidoParcialXProducto> partialProducts = new ArrayList<>();
                
                order = new Pedido();
                Cliente client = new Cliente();
                client.setId(Integer.parseInt(line_split[0]));
                order.setCliente(client);
                Local local = new Local();
                local.setId(Integer.parseInt(line_split[1]));
                order.setLocal(local);
                order.setEstado(Integer.parseInt(line_split[2]));
                Date date = new Date();
                order.setFecha(date);
                
                PedidoParcial pp = new PedidoParcial();
                pp.setEstado(1);
                pp.setPedido(order);
                
                productsNum = Integer.parseInt(line_split[3]);
                for(int j=0;j<productsNum;j++){
                    line = br.readLine();
                    line_split = line.split(cvsSplitBy);
                    PedidoParcialXProducto partial = new PedidoParcialXProducto();
                    PedidoParcialXProductoId id = new PedidoParcialXProductoId();
                    
                    Producto product = new Producto();
                    product.setId(Integer.parseInt(line_split[0]));
                    product.setNombre("");
                   
                    id.setIdProducto(product.getId());
                    
                    partial.setId(id);
                    partial.setPedidoParcial(pp);

                    partial.setProducto(productApplication.searchProduct(product).get(0));
                    partial.setCantidad(Integer.parseInt(line_split[1]));
                    partialProducts.add(partial);
                }
                if (!orderApplication.CreateOrder(order, pp, partialProducts)){
                    JOptionPane.showMessageDialog(this, Strings.LOAD_ORDER_ERROR,Strings.LOAD_ORDER_TITLE,JOptionPane.ERROR_MESSAGE);
                    has_errors = true;
                    break;
                }
            }
	} catch (Exception e) {
            has_errors = true;
            e.printStackTrace();
	} finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally{
                    if(!has_errors){
                        currentOrders = orderApplication.getAllOrders();
                        refreshTable();
                        JOptionPane.showMessageDialog(this, Strings.LOAD_ORDER_SUCCESS,
                        Strings.LOAD_ORDER_TITLE,JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                        JOptionPane.showMessageDialog(this, Strings.LOAD_ORDER_ERROR,Strings.LOAD_ORDER_TITLE,JOptionPane.ERROR_MESSAGE);
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

        deleteBtn = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        fileTextField = new javax.swing.JTextField();
        loadBtn = new javax.swing.JButton();
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
        newBtn = new javax.swing.JButton();
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
        partialStatusTxt = new javax.swing.JLabel();
        deletePartialBtn = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        reasonCombo = new javax.swing.JComboBox();
        deliverBtn = new javax.swing.JButton();

        setClosable(true);
        setTitle("Pedidos");

        deleteBtn.setEnabled(false);
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Carga Masiva"));

        jLabel1.setText("Ingresar Pedidos desde un archivo:");

        fileTextField.setEditable(false);

        loadBtn.setEnabled(false);
        loadBtn.setLabel("Cargar");
        loadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadBtnActionPerformed(evt);
            }
        });

        jButton2.setText("...");
        jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

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
                        .addComponent(loadBtn)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(loadBtn)
                        .addComponent(fileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(34, 34, 34)))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jScrollPane2.setEnabled(false);

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
                        .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        newBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBtnActionPerformed(evt);
            }
        });

        jScrollPane1.setEnabled(false);

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
        productTable.setEnabled(false);
        jScrollPane1.setViewportView(productTable);
        if (productTable.getColumnModel().getColumnCount() > 0) {
            productTable.getColumnModel().getColumn(0).setResizable(false);
            productTable.getColumnModel().getColumn(1).setResizable(false);
            productTable.getColumnModel().getColumn(2).setResizable(false);
            productTable.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle Pedido"));

        jLabel6.setText("Cliente:");

        detailClientTxt.setEditable(false);

        jLabel7.setText("RUC:");

        rucTxt.setEditable(false);

        jLabel8.setText("Cod. Local:");

        codLocalTxt.setEditable(false);

        jLabel9.setText("Direccion:");

        jLabel10.setText("Local:");

        localTxt.setEditable(false);

        direccionTxt.setEditable(false);

        jLabel11.setText("Emision:");

        dateTxt.setEditable(false);

        jLabel12.setText("Estado:");

        detailStatusCombo.setEnabled(false);

        jLabel13.setText("P. Parcial:");

        partialCombo.setEnabled(false);

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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
                                .addComponent(dateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(detailStatusCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(partialCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(partialStatusTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(partialCombo)
                    .addComponent(partialStatusTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        deletePartialBtn.setEnabled(false);
        deletePartialBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePartialBtnActionPerformed(evt);
            }
        });

        jLabel14.setText("Razon:");

        reasonCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar Razon", "Productos Vencidos", "Disconformidad" }));
        reasonCombo.setEnabled(false);

        deliverBtn.setText("Despachar");
        deliverBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deliverBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(deliverBtn)
                                .addGap(350, 350, 350)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(reasonCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deletePartialBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(newBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newBtn)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(deleteBtn)
                        .addComponent(deletePartialBtn)
                        .addComponent(jLabel14)
                        .addComponent(reasonCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(deliverBtn))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadBtnActionPerformed
        loadFile(fileTextField.getText());
    }//GEN-LAST:event_loadBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        int response = JOptionPane.showConfirmDialog(this, Strings.MESSAGE_CONFIRM_DELETE_ORDER,Strings.MESSAGE_DELETE_ORDER_TITLE,JOptionPane.WARNING_MESSAGE);
        if(JOptionPane.OK_OPTION == response){
            deleteOrder();
            clearDetailFields();
            detailStatusCombo.setSelectedIndex(1);
        }
    }//GEN-LAST:event_deleteBtnActionPerformed
                                     
    private void newBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtnActionPerformed
        NewOrderProduct newOrderProductView = new NewOrderProduct((JFrame) SwingUtilities.getWindowAncestor(this), true);
        newOrderProductView.setVisible(true);
    }//GEN-LAST:event_newBtnActionPerformed

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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Seleccione un archivo");
        fc.showOpenDialog(this);
        File file = fc.getSelectedFile();
        if (!fc.getSelectedFile().getName().endsWith(".csv")) {
          JOptionPane.setDefaultLocale(new Locale("es", "ES"));
          JOptionPane.showMessageDialog(this, "El archivo seleccionado no es un archivo CSV.");
        }
        else{
            fileTextField.setText(file.getAbsolutePath());
            loadBtn.setEnabled(true);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void deliverBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deliverBtnActionPerformed
        // TODO add your handling code here:
        //algorithmExecution.start();
    }//GEN-LAST:event_deliverBtnActionPerformed

    private void deletePartialBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletePartialBtnActionPerformed
    if(reasonCombo.getSelectedIndex() != 0){
            PedidoParcial p = currentPartialOrders.get(partialCombo.getSelectedIndex() -1);
            ArrayList<Pallet> pallets = palletApplication.getPalletsByPartialOrder(p.getId());
            for(int i=0;i<pallets.size();i++){
                pallets.get(i).setEstado(EntityState.Pallets.UBICADO.ordinal());
                pallets.get(i).setPedidoParcial(null);
            }
            if(reasonCombo.getSelectedIndex() == 1)//PRODUCTOS VENCIDOS 
                p.setEstado(EntityState.PartialOrders.NO_ATENDIDO.ordinal());
            else{//CLIENTE INSATISFECHO
                p.setEstado(EntityState.PartialOrders.ANULADO.ordinal());
            }
            if(orderApplication.updatePartialOrder(p, pallets)){
                JOptionPane.showMessageDialog(this, Strings.DEVOLUTION_ORDER_SUCCESS,Strings.DEVOLUTION_ORDER_TITLE,JOptionPane.INFORMATION_MESSAGE);
                ArrayList<PedidoParcial> availablePartialOrders = orderApplication.getPendingPartialOrdersById(p.getPedido().getId());
                System.out.println("AVAIALBLE ORDERS " + availablePartialOrders.size());
                if(availablePartialOrders.isEmpty()){
                    p.getPedido().setEstado(EntityState.Orders.ANULADO.ordinal());
                    orderApplication.updateOrder(p.getPedido());
                }
                currentOrders = orderApplication.getAllOrders();
                refreshTable();
                clearDetailFields();
            }
            else
                JOptionPane.showMessageDialog(this, Strings.DEVOLUTION_ORDER_ERROR,Strings.DEVOLUTION_ORDER_TITLE,JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_deletePartialBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField codLocalTxt;
    private javax.swing.JFormattedTextField dateTxt;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton deletePartialBtn;
    private javax.swing.JButton deliverBtn;
    private javax.swing.JTextField detailClientTxt;
    private javax.swing.JComboBox detailStatusCombo;
    private javax.swing.JTextField direccionTxt;
    private javax.swing.JTextField fileTextField;
    private javax.swing.JComboBox filterClientCombo;
    private javax.swing.JTextField filterIdTxt;
    private javax.swing.JComboBox filterLocalCombo;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JButton loadBtn;
    private javax.swing.JTextField localTxt;
    private javax.swing.JButton newBtn;
    private javax.swing.JTable orderTable;
    private javax.swing.JComboBox partialCombo;
    private javax.swing.JLabel partialStatusTxt;
    private javax.swing.JTable productTable;
    private javax.swing.JComboBox reasonCombo;
    private javax.swing.JTextField rucTxt;
    private javax.swing.JButton searchBtn;
    private javax.swing.JComboBox statusCombo;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        JTable target = (JTable)e.getSource();
        if(orderTable.getSelectedRow() != -1){
            fileTextField.setText("");
            fillDetailFields();
            if(currentOrders.get(currentOrderIndex()).getEstado() == 1){
                deleteBtn.setEnabled(true);
            }else
                deleteBtn.setEnabled(false);
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
          String item = (String)e.getItem();
          String arr[] = item.split(" ", 2);
          if(arr[0].equals("Cliente"))
            if(filterClientCombo.getSelectedIndex() != 0){
                filterLocalCombo.setEnabled(true);
                fillLocalCombo();
            }else
                filterLocalCombo.setEnabled(false);
          else
            if(partialCombo.getSelectedIndex()==0){
                refreshAllProductsTable(currentOrders.get(orderTable.getSelectedRow()).getId());
                partialStatusTxt.setText("");
            }
            else{
                if(currentPartialOrders.get(partialCombo.getSelectedIndex()-1).getEstado() == 0){
                    reasonCombo.setEnabled(true);
                    deletePartialBtn.setEnabled(true);
                }   
                refreshProductTable(currentPartialOrders.get(partialCombo.getSelectedIndex()-1).getId());
                partialStatusTxt.setText(EntityState.getPartialOrdersState()[currentPartialOrders.get(partialCombo.getSelectedIndex()-1).getEstado()]);
            }
        }
    }
    
}
