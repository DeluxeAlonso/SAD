/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.client;

import algorithm.Node;
import algorithm.Solution;
import application.client.ClientApplication;
import application.local.LocalApplication;
import application.order.OrderApplication;
import application.product.ProductApplication;
import client.base.BaseView;
import client.order.OrderView;
import entity.Cliente;
import entity.Local;
import entity.Pedido;
import entity.PedidoParcial;
import entity.PedidoParcialXProducto;
import entity.Producto;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import util.EntityState;
import util.EntityState.Clients;
import util.EntityState.Locals;
import util.InstanceFactory;
import util.Strings;

/**
 *
 * @author Alonso
 */
public class ClientView extends BaseView implements MouseListener {
    ClientApplication clientApplication=InstanceFactory.Instance.getInstance("clientApplication", ClientApplication.class);
    LocalApplication localApplication=InstanceFactory.Instance.getInstance("localApplication", LocalApplication.class);
    OrderApplication orderApplication= new OrderApplication();
    ProductApplication productApplication = new ProductApplication();
    ArrayList<Cliente> clients;
    ArrayList<Local> locals;
    File file = null;
    Border errorBorder = BorderFactory.createLineBorder(Color.RED, 1);
    Border regularBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
    public static ClientView clientView;
    private Object myFXPanel;
    /**
     * Creates new form ClientView
     */
    public ClientView() {
        initComponents();
        super.initialize();
                System.out.println("Buscar: "+ btnSaveClient.getWidth()+" "+btnSaveClient.getHeight());
        setupListeners();
        fillClientsTable();
        clientView = this;
        tblClients.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                btnDeleteClient.setEnabled(true);
                btnSaveLocal.setEnabled(true);
                fillLocalsTable();
                changeNewLocalFormState(true);
                btnDeleteLocal.setEnabled(false);
            }
        });
        tblLocals.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                btnDeleteLocal.setEnabled(true);
            }
        });
    }
    
    private void setupListeners() {
        addMouseListener(this);
        jScrollPane1.addMouseListener(this);
        tblClients.addMouseListener(this);
        tblLocals.addMouseListener(this);
    }
    
    private void clearClientsTable(){
        DefaultTableModel model = (DefaultTableModel) tblClients.getModel();
        model.setRowCount(0);
    }
    
    public void clearLocalsTable(){
        DefaultTableModel model = (DefaultTableModel) tblLocals.getModel();
        model.setRowCount(0);
    }
    
    public void fillLocalsTable(){
        clearLocalsTable();
        DefaultTableModel model = (DefaultTableModel) tblLocals.getModel();
        locals = (ArrayList<Local>)localApplication.queryLocalsByClient(clients.get(tblClients.getSelectedRow()).getId());
        if(locals.size()>0){
            for(Local local : locals){
                model.addRow(new Object[]{
                    local.getNombre(),
                    local.getDireccion(),
                    local.getLatitud(),
                    local.getLongitud(),
                });
            }
        }
    }
    
    public void fillClientsTable(){
        clearClientsTable();
        DefaultTableModel model = (DefaultTableModel) tblClients.getModel();
        clients = (ArrayList<Cliente>)clientApplication.queryAll();
        for(Cliente client : clients){
            model.addRow(new Object[]{
                client.getId(),
                client.getNombre(),
                client.getRuc(),
            });
        }
    }
    
    public void changeNewLocalFormState(boolean state){
        txtNewLocalName.setEnabled(state);
        txtNewLocalAddress.setEnabled(state);
        txtNewLocalLatitude.setEnabled(state);
        txtNewLocalLongitude.setEnabled(state);
        btnSaveLocal.setEnabled(state);
    }
    
    public boolean isDouble( String str ){
        try{
            Double.parseDouble( str );
            return true;
        }catch( Exception e ){
            return false;
        }
    }
    
    private void loadFromFile(String csvFile){
        BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
        try {
            Cliente client;
            Local local;
            br = new BufferedReader(new FileReader(csvFile));
            // Leo la cantidad de clientes que hay en el archivo
            line = br.readLine();
            String[] line_split = line.split(cvsSplitBy);
            int clientsNum = Integer.parseInt(line_split[0]);
            int localsNum;
            for(int i=0;i<clientsNum;i++){
                //Leo un cliente y lo inserto
                line = br.readLine();
                line_split = line.split(cvsSplitBy);
                client = new Cliente();
                client.setNombre(line_split[0]);
                client.setRuc(line_split[1]);
                client.setEstado(Clients.ACTIVO.ordinal());
                clientApplication.insert(client);
                //Leo sus locales y los inserto
                localsNum = Integer.parseInt(line_split[2]);
                for(int j=0;j<localsNum;j++){
                    line = br.readLine();
                    line_split = line.split(cvsSplitBy);
                    local = new Local();
                    local.setLatitud(Double.parseDouble(line_split[0]));
                    local.setLongitud(Double.parseDouble(line_split[1]));
                    local.setNombre(line_split[2]);
                    local.setDescripcion(line_split[3]);
                    local.setDireccion(line_split[4]);
                    local.setCliente(client);
                    local.setEstado(Locals.ACTIVO.ordinal());
                    localApplication.insert(local);
                }
            }
	} catch (FileNotFoundException e) {
            e.printStackTrace();
	} catch (IOException e) {
            e.printStackTrace();
	} finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally{
                    fillClientsTable();
                }
            }
	}
    }
    
    public void clearNewClientForm()
    {
        txtNewClientName.setText("");
        txtNewClientRuc.setText("");
    }
    
    public void clearNewClientFormBorders()
    {
        txtNewClientName.setBorder(regularBorder);
        txtNewClientRuc.setBorder(regularBorder);
    }
    
    public void clearNewLocalForm()
    {
        txtNewLocalName.setText("");
        txtNewLocalLatitude.setText("");
        txtNewLocalLongitude.setText("");
        txtNewLocalAddress.setText("");
    }
    
    public void clearNewLocalFormBorders()
    {
        txtNewLocalName.setBorder(regularBorder);
        txtNewLocalLatitude.setBorder(regularBorder);
        txtNewLocalLongitude.setBorder(regularBorder);
        txtNewLocalAddress.setBorder(regularBorder);
    }
    
    public void deleteOrder(Pedido currentOrder){
        ArrayList<PedidoParcial> partialOrders = orderApplication.getPendingPartialOrdersById(currentOrder.getId());
        currentOrder.setEstado(0);
        for(int i=0;i<partialOrders.size();i++){
            if(partialOrders.get(i).getEstado() == EntityState.PartialOrders.ATENDIDO.ordinal())
           for (Iterator<PedidoParcialXProducto> partialXProduct = partialOrders.get(i).getPedidoParcialXProductos().iterator(); partialXProduct.hasNext(); ) {
                PedidoParcialXProducto pxp = partialXProduct.next();
                Producto product = pxp.getProducto();
                product.setStockLogico(product.getStockLogico() + pxp.getCantidad());
                productApplication.update(product);
            }
        }
        orderApplication.updateOrder(currentOrder);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblClients = new javax.swing.JTable();
        btnDeleteClient = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnSaveClient = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNewClientName = new javax.swing.JTextField();
        txtNewClientRuc = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtNewLocalName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNewLocalAddress = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNewLocalLatitude = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNewLocalLongitude = new javax.swing.JTextField();
        btnSaveLocal = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLocals = new javax.swing.JTable();
        btnDeleteLocal = new javax.swing.JButton();
        btnMap = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        fileTextField = new javax.swing.JTextField();
        btnFile = new javax.swing.JButton();
        btnFileUpload = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Clientes");

        tblClients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Ruc"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblClients);
        if (tblClients.getColumnModel().getColumnCount() > 0) {
            tblClients.getColumnModel().getColumn(0).setResizable(false);
            tblClients.getColumnModel().getColumn(1).setResizable(false);
            tblClients.getColumnModel().getColumn(2).setResizable(false);
        }

        btnDeleteClient.setText("Eliminar");
        btnDeleteClient.setEnabled(false);
        btnDeleteClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteClientActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Nuevo Cliente"));

        btnSaveClient.setText("Guardar");
        btnSaveClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveClientActionPerformed(evt);
            }
        });

        jLabel2.setText("*Nombre del cliente:");

        jLabel3.setText("*RUC:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSaveClient)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNewClientName, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                            .addComponent(txtNewClientRuc))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNewClientName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNewClientRuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnSaveClient)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Nuevo Local"));

        jLabel4.setText("*Nombre de local:");

        txtNewLocalName.setEnabled(false);

        jLabel5.setText("*Dirección:");

        txtNewLocalAddress.setEnabled(false);

        jLabel6.setText("*Latitud:");

        txtNewLocalLatitude.setEnabled(false);

        jLabel7.setText("*Longitud:");

        txtNewLocalLongitude.setEnabled(false);

        btnSaveLocal.setText("Guardar");
        btnSaveLocal.setEnabled(false);
        btnSaveLocal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveLocalActionPerformed(evt);
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
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtNewLocalLatitude, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(txtNewLocalLongitude, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNewLocalAddress)
                            .addComponent(txtNewLocalName)))
                    .addComponent(btnSaveLocal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNewLocalName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNewLocalAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNewLocalLatitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtNewLocalLongitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveLocal)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblLocals.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Dirección", "Latitud", "Longitud"
            }
        ));
        jScrollPane2.setViewportView(tblLocals);

        btnDeleteLocal.setText("Eliminar");
        btnDeleteLocal.setEnabled(false);
        btnDeleteLocal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteLocalActionPerformed(evt);
            }
        });

        btnMap.setText("Visualizar locales");
        btnMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMapActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Carga masiva"));

        fileTextField.setEditable(false);

        btnFile.setText("...");
        btnFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFileActionPerformed(evt);
            }
        });

        btnFileUpload.setText("Cargar");
        btnFileUpload.setEnabled(false);
        btnFileUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFileUploadActionPerformed(evt);
            }
        });

        jLabel1.setText("Ingresar Clientes desde un archivo:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(fileTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFile, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFileUpload)
                        .addGap(88, 88, 88))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(220, 220, 220))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFile, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFileUpload))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnDeleteClient))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 54, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnDeleteLocal))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnMap)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(btnMap))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeleteClient)
                    .addComponent(btnDeleteLocal))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFileActionPerformed
        fc.setDialogTitle("Seleccione un archivo");
        fc.showOpenDialog(this);
        file = fc.getSelectedFile();
        if (!fc.getSelectedFile().getName().endsWith(".csv")) {
            JOptionPane.setDefaultLocale(new Locale("es", "ES"));
            JOptionPane.showMessageDialog(this, Strings.ERROR_NOT_CSV,Strings.ERROR_FILE_UPLOAD_TITLE,JOptionPane.WARNING_MESSAGE);
            fileTextField.setText("");
            btnFileUpload.setEnabled(false);
        }
        else{
            fileTextField.setText(file.getAbsolutePath());
            btnFileUpload.setEnabled(true);
        }
    }//GEN-LAST:event_btnFileActionPerformed

    private void btnSaveClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveClientActionPerformed
        clearNewClientFormBorders();
        String error_message = "Errores:\n";
        JOptionPane.setDefaultLocale(new Locale("es", "ES"));
        boolean hasErrors = false;
        if(txtNewClientName.getText().length() < 2){
            error_message += Strings.ERROR_NAME_LESS_2+"\n";
            txtNewClientName.setBorder(errorBorder);
            hasErrors = true;
        }
        else if(txtNewClientName.getText().length() > 60){
            error_message += Strings.ERROR_NAME_MORE_60+"\n";
            txtNewClientName.setBorder(errorBorder);
            hasErrors = true;
        }
        if(txtNewClientRuc.getText().length() != 11){
            error_message += Strings.ERROR_RUC_NOT_11+"\n";
            txtNewClientRuc.setBorder(errorBorder);
            hasErrors = true;
        }
        if(txtNewClientRuc.getText().length() > 0 && !txtNewClientRuc.getText().matches("[0-9]+")){
            error_message += Strings.ERROR_RUC_NOT_NUMERIC+"\n";
            txtNewClientRuc.setBorder(errorBorder);
            hasErrors = true;
        }
        
        if(hasErrors){
            JOptionPane.showMessageDialog(this, error_message,Strings.ERROR_NEW_CLIENT_TITLE,JOptionPane.WARNING_MESSAGE);
        }else{
            Cliente client = new Cliente();
            client.setNombre(txtNewClientName.getText());
            client.setRuc(txtNewClientRuc.getText());
            client.setEstado(Clients.ACTIVO.ordinal());
            clientApplication.insert(client);
            JOptionPane.showMessageDialog(this, Strings.MESSAGE_NEW_CLIENT_CREATED,Strings.MESSAGE_NEW_CLIENT_TITLE,JOptionPane.INFORMATION_MESSAGE);
            clearNewClientForm();
            clearLocalsTable();
            fillClientsTable();
            changeNewLocalFormState(false);
            btnDeleteLocal.setEnabled(false);
        }
    }//GEN-LAST:event_btnSaveClientActionPerformed

    private void btnFileUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFileUploadActionPerformed
        try {
            startLoader();
            loadFromFile(file.getAbsolutePath());
            file = null;
            btnFileUpload.setEnabled(false);
            fileTextField.setText("");
            JOptionPane.setDefaultLocale(new Locale("es", "ES"));
            JOptionPane.showMessageDialog(this, Strings.MESSAGE_LOAD_NEW_CLIENTS,Strings.MESSAGE_NEW_CLIENT_TITLE,JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            stopLoader();
        }
    }//GEN-LAST:event_btnFileUploadActionPerformed

    private void btnMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMapActionPerformed
        ArrayList<Local> locals = localApplication.queryAll();
        GoogleMapsLocals map = new GoogleMapsLocals(locals);
    }//GEN-LAST:event_btnMapActionPerformed

    private void btnSaveLocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveLocalActionPerformed
        clearNewLocalFormBorders();
        String error_message = "Errores:\n";
        JOptionPane.setDefaultLocale(new Locale("es", "ES"));
        boolean hasErrors = false;
        if(txtNewLocalLatitude.getText().isEmpty()){
            error_message += Strings.ERROR_LATITUDE_REQUIRED+"\n";
            txtNewLocalLatitude.setBorder(errorBorder);
            hasErrors = true;
        }else if(!isDouble(txtNewLocalLatitude.getText())){
            error_message += Strings.ERROR_LATITUDE_NOT_FLOAT+"\n";
            txtNewLocalLatitude.setBorder(errorBorder);
            hasErrors = true;
        }else if(isDouble(txtNewLocalLatitude.getText()) && Double.parseDouble(txtNewLocalLatitude.getText())>-11.5 || Double.parseDouble(txtNewLocalLatitude.getText())<-12.5 ){
            error_message += Strings.ERROR_LATITUDE_OUT_LIMITS+"\n";
            txtNewLocalLatitude.setBorder(errorBorder);
            hasErrors = true;
        }
        if(txtNewLocalLongitude.getText().isEmpty()){
            error_message += Strings.ERROR_LONGITUDE_REQUIRED+"\n";
            txtNewLocalLongitude.setBorder(errorBorder);
            hasErrors = true;
        }else if(!isDouble(txtNewLocalLongitude.getText())){
            error_message += Strings.ERROR_LONGITUDE_NOT_FLOAT+"\n";
            txtNewLocalLongitude.setBorder(errorBorder);
            hasErrors = true;
        }else if(isDouble(txtNewLocalLongitude.getText()) && Double.parseDouble(txtNewLocalLongitude.getText())>-76.8 || Double.parseDouble(txtNewLocalLongitude.getText())<-77.2){
            error_message += Strings.ERROR_LONGITUDE_OUT_LIMITS+"\n";
            txtNewLocalLongitude.setBorder(errorBorder);
            hasErrors = true;
        }
        if(txtNewLocalName.getText().length()<1){
            error_message += Strings.ERROR_NAME_LESS_2+"\n";
            txtNewLocalName.setBorder(errorBorder);
            hasErrors = true;
        }
        if(txtNewLocalAddress.getText().isEmpty()){
            error_message += Strings.ERROR_ADDRESS_REQUIRED+"\n";
            txtNewLocalAddress.setBorder(errorBorder);
            hasErrors = true;
        }
        
        if(hasErrors){
            JOptionPane.showMessageDialog(this, error_message,Strings.ERROR_NEW_LOCAL_TITLE,JOptionPane.ERROR_MESSAGE);
        }else{
            Local local = new Local();
            local.setCliente(clients.get(tblClients.getSelectedRow()));
            local.setDireccion(txtNewLocalAddress.getText());
            local.setLatitud(Double.parseDouble(txtNewLocalLatitude.getText()));
            local.setLongitud(Double.parseDouble(txtNewLocalLongitude.getText()));
            local.setEstado(Locals.ACTIVO.ordinal());
            local.setNombre(txtNewLocalName.getText());
            localApplication.insert(local);
            JOptionPane.showMessageDialog(this, Strings.MESSAGE_NEW_LOCAL_CREATED,Strings.MESSAGE_NEW_LOCAL_TITLE,JOptionPane.INFORMATION_MESSAGE);
            clearNewLocalForm();
            fillLocalsTable();
        }
    }//GEN-LAST:event_btnSaveLocalActionPerformed

    private void btnDeleteClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteClientActionPerformed
        JOptionPane.setDefaultLocale(new Locale("es", "ES"));
        int response = JOptionPane.showConfirmDialog(this, Strings.MESSAGE_DELETE_CLIENT,Strings.MESSAGE_DELETE_CLIENT_TITLE,JOptionPane.WARNING_MESSAGE);
        if(JOptionPane.OK_OPTION == response){
            clientApplication.delete(clients.get(tblClients.getSelectedRow()).getId());
            ArrayList<Pedido> ordersToDelete = orderApplication.getOrdersByClientId(clients.get(tblClients.getSelectedRow()).getId());
            for(int i=0;i<ordersToDelete.size();i++)
                deleteOrder(ordersToDelete.get(i));
            if(OrderView.orderView != null)
                OrderView.orderView.verifyOrders();
            fillClientsTable();
            clearNewLocalForm();
            changeNewLocalFormState(false);
            btnDeleteClient.setEnabled(false);
            btnDeleteLocal.setEnabled(false);
        }
    }//GEN-LAST:event_btnDeleteClientActionPerformed

    private void btnDeleteLocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteLocalActionPerformed
        JOptionPane.setDefaultLocale(new Locale("es", "ES"));
        int response = JOptionPane.showConfirmDialog(this, Strings.MESSAGE_DELETE_LOCAL,Strings.MESSAGE_DELETE_LOCAL_TITLE,JOptionPane.WARNING_MESSAGE);
        if(JOptionPane.OK_OPTION == response){
            localApplication.delete(locals.get(tblLocals.getSelectedRow()).getId());
            fillLocalsTable();
            btnDeleteLocal.setEnabled(false);
        }
    }//GEN-LAST:event_btnDeleteLocalActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeleteClient;
    private javax.swing.JButton btnDeleteLocal;
    private javax.swing.JButton btnFile;
    private javax.swing.JButton btnFileUpload;
    private javax.swing.JButton btnMap;
    private javax.swing.JButton btnSaveClient;
    private javax.swing.JButton btnSaveLocal;
    private javax.swing.JTextField fileTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblClients;
    private javax.swing.JTable tblLocals;
    private javax.swing.JTextField txtNewClientName;
    private javax.swing.JTextField txtNewClientRuc;
    private javax.swing.JTextField txtNewLocalAddress;
    private javax.swing.JTextField txtNewLocalLatitude;
    private javax.swing.JTextField txtNewLocalLongitude;
    private javax.swing.JTextField txtNewLocalName;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }
    
}
