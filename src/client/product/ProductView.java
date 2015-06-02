/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.product;

import client.client.*;
import application.client.ClientApplication;
import application.local.LocalApplication;
import application.product.ProductApplication;
import application.producttype.ProductTypeApplication;
import client.base.BaseView;
import client.personal.NewPersonalView;
import entity.Cliente;
import entity.Local;
import entity.Producto;
import entity.TipoProducto;
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
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import util.EntityState;
import util.EntityState.Clients;
import util.EntityState.Locals;
import util.EntityType;
import util.InstanceFactory;
import util.Strings;

/**
 *
 * @author Alonso
 */
public class ProductView extends BaseView implements MouseListener {
    ClientApplication clientApplication=InstanceFactory.Instance.getInstance("clientApplication", ClientApplication.class);
    LocalApplication localApplication=InstanceFactory.Instance.getInstance("localApplication", LocalApplication.class);
    ProductApplication productApplication=InstanceFactory.Instance.getInstance("productApplication", ProductApplication.class);
    ProductTypeApplication productTypeApplication=InstanceFactory.Instance.getInstance("productTypeApplication", ProductTypeApplication.class);
    ArrayList<Cliente> clients;
    ArrayList<Local> locals;
    ArrayList<TipoProducto> productTypes;
    ArrayList<Producto> products;    
    long aux=0;
    long EAN13=0;
    JFileChooser fc = new JFileChooser();
    File file = null;
    Border errorBorder = BorderFactory.createLineBorder(Color.RED, 1);
    Border regularBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
    public static ProductView clientView;
    /**
     * Creates new form ClientView
     */
    public ProductView() {
        initComponents();
        initialize();
        setupListeners();
        fillClientsTable();
        condicionCombo.setModel(new javax.swing.DefaultComboBoxModel(EntityType.CONDITIONS_NAMES));
        clientView = this;
        tblClients.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                btnDeleteClient.setEnabled(true);
                btnSaveLocal.setEnabled(true);
                fillProductsTable();
                changeNewLocalFormState(true);
                btnDeleteLocal.setEnabled(false);
            }
        });
        tblLocals.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                btnDeleteLocal.setEnabled(true);
            }
        });
        System.out.println(calcularEAN13(89041));
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
    
    public void fillProductsTable(){
        clearLocalsTable();
        DefaultTableModel model = (DefaultTableModel) tblLocals.getModel();
        products = (ArrayList<Producto>)productApplication.queryByType(productTypes.get(tblClients.getSelectedRow()).getId());
        if (products !=null){
        if(products.size()>0){
            for(Producto product : products){
                model.addRow(new Object[]{
                    product.getNombre(),
                    product.getDescripcion(),
                    EntityType.getCondition(product.getCondicion().getId()).getNombre(),
                    product.getEan13()
                });
            }
        }
        }
    }
    
    public void fillClientsTable(){
        clearClientsTable();
        DefaultTableModel model = (DefaultTableModel) tblClients.getModel();
        productTypes = productTypeApplication.queryAll();
        for(TipoProducto productType : productTypes){
            model.addRow(new Object[]{
                productType.getId(),
                productType.getNombre(),
                productType.getDescripcion()
            });
        }
    }
    
    public void changeNewLocalFormState(boolean state){
        productNameTxt.setEnabled(state);
        descProdTxt.setEnabled(state);
        cantidadTxt.setEnabled(state);
        cantidadXPalletTxt.setEnabled(state);
        btnSaveLocal.setEnabled(state);
        pesoTxt.setEnabled(state);
        condicionCombo.setEnabled(state);
        
    }
    
    public boolean isDouble( String str ){
        try{
            Double.parseDouble( str );
            return true;
        }catch( Exception e ){
            return false;
        }
    }
    
    public boolean isInteger( String str ){
        try{
            Integer.parseInt(str);
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
            TipoProducto productType;
            Producto product;
            br = new BufferedReader(new FileReader(csvFile));
            // Leo la cantidad de Tipo Productos que hay en el archivo
            line = br.readLine();
            String[] line_split = line.split(cvsSplitBy);
            int productsTypeNum = Integer.parseInt(line_split[0]);
            int productsNum;
            for(int i=0;i<productsTypeNum;i++){
                //Leo un cliente y lo inserto
                line = br.readLine();
                line_split = line.split(cvsSplitBy);
                
                productType = new TipoProducto();
                productType.setNombre(line_split[1]);
                productType.setDescripcion(line_split[2]);
                productTypeApplication.insert(productType);
                
                //System.out.println(line_split[0]+" - "+line_split[1]+" - "+line_split[2]);
                //Leo sus productos y los inserto
                productsNum = Integer.parseInt(line_split[3]);
                for(int j=0;j<productsNum;j++){
                    line = br.readLine();
                    line_split = line.split(cvsSplitBy);
                    
                    product = new Producto();
                    product.setNombre(line_split[0]);
                    product.setDescripcion(line_split[1]);
                    product.setStockTotal(Integer.parseInt(line_split[2]));
                    product.setCantidadProductosEnPallet(Integer.parseInt(line_split[3]));
                    product.setPeso(Double.parseDouble(line_split[4]));
                    product.setCondicion(EntityType.getCondition(Integer.parseInt(line_split[5])));
                    product.setTipoProducto(productType);
                    int idN = productApplication.insert(product);
                    product.setEan13(calcularEAN13(idN));
                    productApplication.update(product);
                    //System.out.println(line_split[0]+" - "+line_split[1]+" - "+line_split[2]+" - "+line_split[3]+" - "+line_split[4]);
                    
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
        productTypeName.setText("");
        productDesc.setText("");
    }
    
    public void clearNewClientFormBorders()
    {
        productTypeName.setBorder(regularBorder);
        productDesc.setBorder(regularBorder);
    }
    
    public void clearNewLocalForm()
    {
        productNameTxt.setText("");
        cantidadTxt.setText("");
        cantidadXPalletTxt.setText("");
        descProdTxt.setText("");
        condicionCombo.setSelectedIndex(0);
        pesoTxt.setText("");
        
    }
    
    public void clearNewLocalFormBorders()
    {
        productNameTxt.setBorder(regularBorder);
        cantidadTxt.setBorder(regularBorder);
        cantidadXPalletTxt.setBorder(regularBorder);
        descProdTxt.setBorder(regularBorder);
        condicionCombo.setBorder(regularBorder);
        pesoTxt.setBorder(regularBorder);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        fileTextField = new javax.swing.JTextField();
        btnFile = new javax.swing.JButton();
        btnDeleteClient = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClients = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnSaveClient = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        productTypeName = new javax.swing.JTextField();
        productDesc = new javax.swing.JTextField();
        btnFileUpload = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        productNameTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        descProdTxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cantidadTxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cantidadXPalletTxt = new javax.swing.JTextField();
        btnSaveLocal = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        pesoTxt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        condicionCombo = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLocals = new javax.swing.JTable();
        btnDeleteLocal = new javax.swing.JButton();

        setClosable(true);
        setTitle("Productos");

        jLabel1.setText("Ingresar Productos desde un archivo:");

        fileTextField.setEditable(false);

        btnFile.setText("...");
        btnFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFileActionPerformed(evt);
            }
        });

        btnDeleteClient.setText("Eliminar");
        btnDeleteClient.setEnabled(false);
        btnDeleteClient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnDeleteClientMousePressed(evt);
            }
        });
        btnDeleteClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteClientActionPerformed(evt);
            }
        });

        jScrollPane1.setToolTipText("");
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        tblClients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Descripcion"
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Nuevo Tipo de Producto"));

        btnSaveClient.setText("Guardar");
        btnSaveClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveClientActionPerformed(evt);
            }
        });

        jLabel2.setText("   *Nombre  de tipo:");

        jLabel3.setText("*Descripcion:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(productTypeName, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                            .addComponent(productDesc)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSaveClient)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(productTypeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(productDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSaveClient))
        );

        btnFileUpload.setText("Cargar");
        btnFileUpload.setEnabled(false);
        btnFileUpload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnFileUploadMousePressed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Nuevo Producto"));

        jLabel4.setText("*Nombre Producto:");

        productNameTxt.setEnabled(false);

        jLabel5.setText("*Descripcion:");

        descProdTxt.setEnabled(false);

        jLabel6.setText("*Stock:");

        cantidadTxt.setEnabled(false);

        jLabel7.setText("*Cantidad por Pallet:");

        cantidadXPalletTxt.setEnabled(false);

        btnSaveLocal.setText("Guardar");
        btnSaveLocal.setEnabled(false);
        btnSaveLocal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSaveLocalMousePressed(evt);
            }
        });
        btnSaveLocal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveLocalActionPerformed(evt);
            }
        });

        jLabel8.setText("*Peso (Kg):");

        pesoTxt.setEnabled(false);

        jLabel9.setText("*Condicion:");

        condicionCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        condicionCombo.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSaveLocal)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(productNameTxt)
                            .addComponent(descProdTxt)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(condicionCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cantidadTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(pesoTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                                        .addGap(2, 2, 2))
                                    .addComponent(cantidadXPalletTxt))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(productNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(descProdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cantidadTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(cantidadXPalletTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(pesoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(condicionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(btnSaveLocal))
        );

        tblLocals.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Descripcion", "Condicion", "EAN13"
            }
        ));
        jScrollPane2.setViewportView(tblLocals);

        btnDeleteLocal.setText("Eliminar");
        btnDeleteLocal.setEnabled(false);
        btnDeleteLocal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnDeleteLocalMousePressed(evt);
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
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fileTextField)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnFile, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnFileUpload))
                            .addComponent(btnDeleteClient)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnDeleteLocal)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFile, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(fileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnFileUpload)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        if(productTypeName.getText().length() < 2){
            error_message += Strings.ERROR_NAME_LESS_2+"\n";
            productTypeName.setBorder(errorBorder);
            hasErrors = true;
        }
        else if(productTypeName.getText().length() > 60){
            error_message += Strings.ERROR_NAME_MORE_60+"\n";
            productTypeName.setBorder(errorBorder);
            hasErrors = true;
        }
        
        if(hasErrors){
            JOptionPane.showMessageDialog(this, error_message,Strings.ERROR_NEW_CLIENT_TITLE,JOptionPane.WARNING_MESSAGE);
        }else{
            TipoProducto tproducto = new TipoProducto();
            tproducto.setNombre(productTypeName.getText());
            tproducto.setDescripcion(productDesc.getText());
            productTypeApplication.insert(tproducto);
            JOptionPane.showMessageDialog(this, Strings.MESSAGE_TPRODUCT_CREATED,Strings.MESSAGE_TPRODUCT_TITLE,JOptionPane.INFORMATION_MESSAGE);
            clearNewClientForm();
            clearLocalsTable();
            fillClientsTable();
            changeNewLocalFormState(false);
            btnDeleteLocal.setEnabled(false);
        }
    }//GEN-LAST:event_btnSaveClientActionPerformed

    private void btnDeleteClientMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteClientMousePressed
        JOptionPane.setDefaultLocale(new Locale("es", "ES"));
        int response = JOptionPane.showConfirmDialog(this, Strings.MESSAGE_DELETE_CLIENT,Strings.MESSAGE_DELETE_CLIENT_TITLE,JOptionPane.WARNING_MESSAGE);
        if(JOptionPane.OK_OPTION == response){
            productTypeApplication.delete(productTypes.get(tblClients.getSelectedRow()));            
            productTypeApplication.delete(productTypes.get(tblClients.getSelectedRow()));
            fillClientsTable();
            clearNewLocalForm();
            changeNewLocalFormState(false);
            btnDeleteClient.setEnabled(false);
            btnDeleteLocal.setEnabled(false);
        }
    }//GEN-LAST:event_btnDeleteClientMousePressed

    private void btnFileUploadMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFileUploadMousePressed
        loadFromFile(file.getAbsolutePath());
        file = null;
        btnFileUpload.setEnabled(false);
        fileTextField.setText("");
        
    }//GEN-LAST:event_btnFileUploadMousePressed

    private void btnSaveLocalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveLocalMousePressed
        clearNewLocalFormBorders();
        String error_message = "Errores:\n";
        JOptionPane.setDefaultLocale(new Locale("es", "ES"));
        boolean hasErrors = false;
        if(cantidadTxt.getText().isEmpty()){
            error_message += Strings.ERROR_CANTIDAD_PROD_REQUIRED+"\n";
            cantidadTxt.setBorder(errorBorder);
            hasErrors = true;
        }else if(!isInteger(cantidadTxt.getText())){
            error_message += Strings.ERROR_CANTIDAD_PROD_INT+"\n";
            cantidadTxt.setBorder(errorBorder);
            hasErrors = true;
        }
        if(cantidadXPalletTxt.getText().isEmpty()){
            error_message += Strings.ERROR_CANTIDADXPALLET_REQUIRED+"\n";
            cantidadXPalletTxt.setBorder(errorBorder);
            hasErrors = true;
        }else if(!isInteger(cantidadXPalletTxt.getText())){
            error_message += Strings.ERROR_CANTIDADXPALLET_INT+"\n";
            cantidadXPalletTxt.setBorder(errorBorder);
            hasErrors = true;
        }
        if(productNameTxt.getText().length()<1){
            error_message += Strings.ERROR_PRODUCT_NAME_LESS_2+"\n";
            productNameTxt.setBorder(errorBorder);
            hasErrors = true;
        }
        if(descProdTxt.getText().isEmpty()){
            error_message += Strings.ERROR_DESC_PROD_REQUIRED+"\n";
            descProdTxt.setBorder(errorBorder);
            hasErrors = true;
        }
        if (condicionCombo.getSelectedIndex()==0){
            error_message += Strings.ERROR_CONDITION_PROD_REQUIRED+"\n";
            condicionCombo.setBorder(errorBorder);
            hasErrors = true;
        }
        if (pesoTxt.getText().isEmpty()){
            error_message += Strings.ERROR_PESO_PROD_REQUIRED+"\n";
            pesoTxt.setBorder(errorBorder);
            hasErrors = true;
        }else if (!isDouble(pesoTxt.getText())){
            error_message += Strings.ERROR_PESO_PROD_DOUBLE+"\n";
            pesoTxt.setBorder(errorBorder);
            hasErrors = true;            
        }
        
        if(hasErrors){
            JOptionPane.showMessageDialog(this, error_message,Strings.ERROR_NEW_LOCAL_TITLE,JOptionPane.ERROR_MESSAGE);
        }else{
            Producto product = new Producto();
            product.setCantidadProductosEnPallet(Integer.parseInt(cantidadXPalletTxt.getText()));
            product.setCondicion(EntityType.CONDITIONS.get(condicionCombo.getSelectedIndex()-1));
            product.setDescripcion(descProdTxt.getText());
            product.setNombre(productNameTxt.getText());
            product.setPeso(Double.parseDouble(pesoTxt.getText()));
            product.setStockTotal(Integer.parseInt(cantidadTxt.getText()));
            product.setTipoProducto(productTypes.get(tblClients.getSelectedRow()));
            
            int aux=productApplication.insert(product);
            product.setEan13(calcularEAN13(aux));
            productApplication.update(product);
            JOptionPane.showMessageDialog(this, Strings.MESSAGE_NEW_PRODUCT_CREATED,Strings.MESSAGE_NEW_PRODUCT_TITLE,JOptionPane.INFORMATION_MESSAGE);
            clearNewLocalForm();
            fillProductsTable();
        }
    }//GEN-LAST:event_btnSaveLocalMousePressed

    private String calcularEAN13(int id){
        int iSum = 0;
        int iSumInpar = 0;
        int iDigit = 0;
        
        String ean12;
        /*
        String country= "775";
        String company="7061";
                */
        String country= "775";
        String company="7061";
        String idS = String.format("%05d", id);
        ean12 =  country+ company+idS;
 
        String EAN="0"+ean12;
 
        for (int i = 0; i <12; i++){
            iDigit = Integer.parseInt(ean12.charAt(i)+"");
            if (i % 2 != 0)
            {
                iSumInpar += iDigit;
            }
            else
            {
                iSum += iDigit;
            }
        }
 
        iDigit = (iSumInpar*3) + iSum ;
 
        int iCheckSum = (10 - (iDigit % 10)) % 10;
        return ean12 + iCheckSum;
    }
    
    private void btnDeleteLocalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteLocalMousePressed
        JOptionPane.setDefaultLocale(new Locale("es", "ES"));
        int response = JOptionPane.showConfirmDialog(this, Strings.MESSAGE_DELETE_LOCAL,Strings.MESSAGE_DELETE_LOCAL_TITLE,JOptionPane.WARNING_MESSAGE);
        if(JOptionPane.OK_OPTION == response){
            productApplication.delete(products.get(tblLocals.getSelectedRow()));
            fillProductsTable();
            btnDeleteLocal.setEnabled(false);
        }
    }//GEN-LAST:event_btnDeleteLocalMousePressed

    private void btnDeleteClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteClientActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteClientActionPerformed

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void btnSaveLocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveLocalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveLocalActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeleteClient;
    private javax.swing.JButton btnDeleteLocal;
    private javax.swing.JButton btnFile;
    private javax.swing.JButton btnFileUpload;
    private javax.swing.JButton btnSaveClient;
    private javax.swing.JButton btnSaveLocal;
    private javax.swing.JTextField cantidadTxt;
    private javax.swing.JTextField cantidadXPalletTxt;
    private javax.swing.JComboBox condicionCombo;
    private javax.swing.JTextField descProdTxt;
    private javax.swing.JTextField fileTextField;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField pesoTxt;
    private javax.swing.JTextField productDesc;
    private javax.swing.JTextField productNameTxt;
    private javax.swing.JTextField productTypeName;
    private javax.swing.JTable tblClients;
    private javax.swing.JTable tblLocals;
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
