/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.pallet;

import application.condition.ConditionApplication;
import application.internment.InternmentApplication;
import application.kardex.KardexApplication;
import application.pallet.PalletApplication;
import application.product.ProductApplication;
import application.rack.RackApplication;
import application.spot.SpotApplication;
import application.warehouse.WarehouseApplication;
import client.base.BaseDialogView;
import entity.Almacen;
import entity.Kardex;
import entity.KardexId;
import entity.OrdenInternamiento;
import entity.OrdenInternamientoXProducto;
import entity.Pallet;
import entity.Producto;
import entity.Rack;
import entity.Ubicacion;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import util.EntityState;
import util.EntityType;
import util.Icons;
import util.InstanceFactory;
import util.Strings;

/**
 *
 * @author nivek_000
 */
public class EditPalletView extends BaseDialogView {
    WarehouseApplication warehouseApplication=InstanceFactory.Instance.getInstance("warehouseApplication", WarehouseApplication.class);
    ConditionApplication conditionApplication=InstanceFactory.Instance.getInstance("conditionApplication", ConditionApplication.class);
    RackApplication rackApplication=InstanceFactory.Instance.getInstance("rackApplication", RackApplication.class);
    SpotApplication spotApplication=InstanceFactory.Instance.getInstance("spotApplication", SpotApplication.class);
    ProductApplication productApplication=InstanceFactory.Instance.getInstance("productApplication", ProductApplication.class);
    PalletApplication palletApplication=InstanceFactory.Instance.getInstance("palletApplication", PalletApplication.class);
    KardexApplication kardexApplication = InstanceFactory.Instance.getInstance("kardexApplication",KardexApplication.class);
    InternmentApplication internmentApplication=InstanceFactory.Instance.getInstance("internmentApplication", InternmentApplication.class);
    
    ArrayList<Almacen> warehouses =new ArrayList<Almacen>();
    Pallet p =null;
    ArrayList<Producto> product = new ArrayList<Producto>();
    Pallet pNuevo = new Pallet();
    ArrayList<Ubicacion> ubicaciones = new ArrayList<Ubicacion>();
    String[] stateAux = EntityState.getPalletsState();
    ArrayList<String> state = new ArrayList<String>();
    ArrayList<Integer> stateN = new ArrayList<Integer>();
    
    
    /**
     * Creates new form EditPalletView
     */
    public EditPalletView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public EditPalletView(java.awt.Frame parent, boolean modal,Pallet p) {
        super(parent, modal);
        initComponents();
        initialize();
        this.setTitle("Editar Pallet");
        this.p = p;
        Producto prod = p.getProducto();

        fillProductCombo();
        
        dtcInitDate.setEnabled(false);
        jCheckBox1.setEnabled(false);
        productCombo.setEnabled(false);
        if (p.getEstado() == EntityState.Pallets.CREADO.ordinal()){
            setearCreado();
        }

        if (p.getEstado() == EntityState.Pallets.UBICADO.ordinal()){
            setearUbicado();
        }
        
        if (p.getEstado() == EntityState.Pallets.DESPACHADO.ordinal()){
            setearDespachado();
            saveBtn.setEnabled(false);
        }

        if (p.getEstado() == EntityState.Pallets.ROTO.ordinal()){
            setearRoto();
        }

        if (p.getEstado() == EntityState.Pallets.VENCIDO.ordinal()){
            setearVencido();
        }
        
        fillEstadoCombo();
        comboWarehouse.setEnabled(false);
        
        if (p.getUbicacion()!= null){
            DefaultTableModel model = (DefaultTableModel) tableFreeSpots.getModel();
            Ubicacion ub = p.getUbicacion();
            //Object[] colIdent = {"Almacén","Rack","Lado","Fila","Columna"};
            //model.setColumnIdentifiers(colIdent);
            model.setColumnCount(5);
            model.addRow(new Object[]{
                   p.getUbicacion().getRack().getAlmacen().getId(),
                   p.getUbicacion().getRack().getId(),
                   p.getUbicacion().getLado(),
                   p.getUbicacion().getFila(),
                   p.getUbicacion().getColumna()
                });
        }
        
        
        this.productCombo.setSelectedItem(prod.getNombre());
        this.dtcInitDate.setDate(p.getFechaVencimiento());
        int x = p.getEstado();
        int y = stateN.indexOf(x);
        this.jComboBox2.setSelectedItem(state.get(y));
        this.jTextField1.setText(p.getEan128());
        
        
        Icons.setButton(saveBtn, Icons.ICONOS.SAVE.ordinal());
        Icons.setButton(cancelBtn, Icons.ICONOS.CANCEL.ordinal());
    }
    
    public void setearCreado(){
            state.clear();
            stateN.clear();
            dtcInitDate.setEnabled(true);
            jCheckBox1.setEnabled(true);
            if (p.getOrdenInternamiento() == null)
                productCombo.setEnabled(false);
            
            for (String s : stateAux){
                if ((s.compareTo("Eliminado")!=0) && (s.compareTo("Ubicado")!=0) && (s.compareTo("Despachado")!=0) && (s.compareTo("Vencido")!=0) ){
                    state.add(s);
                    stateN.add(EntityState.Pallets.valueOf(s.toUpperCase()).ordinal());
                }        
            }
    }
    
    public void setearUbicado(){
            state.clear();
            stateN.clear();
            dtcInitDate.setEnabled(true);
            for (String s : stateAux){
                if ((s.compareTo("Eliminado")!=0) && (s.compareTo("Despachado")!=0) && (s.compareTo("Vencido")!=0) ){
                    state.add(s);
                    stateN.add(EntityState.Pallets.valueOf(s.toUpperCase()).ordinal());
                }        
            }        
    }
    
    public void setearDespachado(){
            state.clear();
            stateN.clear();            
            for (String s : stateAux){
                if ((s.compareTo("Creado")!=0) && (s.compareTo("Ubicado")!=0) &&  (s.compareTo("Roto")!=0) &&  (s.compareTo("Vencido")!=0) && (s.compareTo("Eliminado")!=0) ){
                    state.add(s);
                    stateN.add(EntityState.Pallets.valueOf(s.toUpperCase()).ordinal());
                }        
            }        
    }

    public void setearRoto(){
            state.clear();
            stateN.clear();
            dtcInitDate.setEnabled(true);
            for (String s : stateAux){
                if ((s.compareTo("Ubicado")!=0)&&(s.compareTo("Despachado")!=0)  &&  (s.compareTo("Vencido")!=0)&& (s.compareTo("Eliminado")!=0) ){
                    state.add(s);
                    stateN.add(EntityState.Pallets.valueOf(s.toUpperCase()).ordinal());
                }        
            }        
    }
    
    public void setearVencido(){
            state.clear();
            stateN.clear();            
            dtcInitDate.setEnabled(true);
            for (String s : stateAux){
                if ((s.compareTo("Ubicado")!=0)&& (s.compareTo("Creado")!=0) &&(s.compareTo("Despachado")!=0)  && (s.compareTo("Eliminado")!=0) ){
                    state.add(s);
                    stateN.add(EntityState.Pallets.valueOf(s.toUpperCase()).ordinal());
                }        
            }
        
    }
    
    public void clearBorders(){
        productCombo.setBorder(regularBorder);
        jComboBox2.setBorder(regularBorder);
        dtcInitDate.setBorder(regularBorder);
    }
    
    private void fillWarehouseCombo(int type){
        warehouses.clear();
        warehouses.addAll(warehouseApplication.queryWarehousesByType(type));
        if (warehouses!=null && warehouses.size()!=0){
            
            String[] whNames = new String[warehouses.size()];
            for (int i=0; i < warehouses.size();i++){
                whNames[i]=warehouses.get(i).getDescripcion();
            }
            comboWarehouse.setModel(new javax.swing.DefaultComboBoxModel(whNames));
        }
        
    }
    
    private void fillProductCombo(){
        product = productApplication.getAllProducts();
        if (product!=null && product.size()!=0){
            
            String[] prNames = new String[product.size()];
            for (int i=0; i < product.size();i++){
                prNames[i]=product.get(i).getNombre();
            }
            productCombo.setModel(new javax.swing.DefaultComboBoxModel(prNames));
        }
        
    }
    
    private void fillEstadoCombo(){
        if (state!=null && state.size()!=0){   
            String[] prNames = new String[state.size()];
            for (int i=0; i < state.size();i++){
                prNames[i]=state.get(i);
            }
            jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(prNames));
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

        saveBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableFreeSpots = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        comboWarehouse = new javax.swing.JComboBox();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        productCombo = new javax.swing.JComboBox();
        dtcInitDate = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        tableFreeSpots.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Almacén", "Rack", "Lado", "Fila", "Columna", "Seleccione"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableFreeSpots);
        if (tableFreeSpots.getColumnModel().getColumnCount() > 0) {
            tableFreeSpots.getColumnModel().getColumn(0).setResizable(false);
            tableFreeSpots.getColumnModel().getColumn(1).setResizable(false);
            tableFreeSpots.getColumnModel().getColumn(2).setResizable(false);
            tableFreeSpots.getColumnModel().getColumn(3).setResizable(false);
            tableFreeSpots.getColumnModel().getColumn(4).setResizable(false);
            tableFreeSpots.getColumnModel().getColumn(5).setResizable(false);
        }

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Pallet"));
        jPanel1.setName("asd"); // NOI18N

        comboWarehouse.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboWarehouse.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboWarehouseItemStateChanged(evt);
            }
        });

        jCheckBox1.setText("Internar");
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });

        jLabel5.setText("Almacén:");

        jTextField1.setEditable(false);

        productCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        productCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                productComboItemStateChanged(evt);
            }
        });

        jLabel2.setText("Producto:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Estado:");

        jLabel3.setText("Fecha Venc.");

        jLabel1.setText("EAN128:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(productCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(75, 75, 75)
                                .addComponent(jCheckBox1))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(dtcInitDate, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(77, 77, 77)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(comboWarehouse, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jCheckBox1)
                            .addComponent(productCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dtcInitDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 5, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(comboWarehouse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addComponent(saveBtn)
                        .addGap(125, 125, 125)
                        .addComponent(cancelBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private boolean hasErrors(){
        boolean errorFlag = false;
        Date date = new Date();
        String error_message = "Errores:\n";
        /*if (jComboBox1.getSelectedIndex()==0){
            error_message += "Debe seleccionar un producto\n";
            jComboBox1.setBorder(errorBorder);
            errorFlag=true;
        }
        if (jComboBox2.getSelectedIndex()==0){
            error_message += "Debe seleccionar un Almacén\n";
            jComboBox2.setBorder(errorBorder);
            errorFlag=true;
        }
        if (dtcInitDate.getDate() == null ){
            error_message += "Debe seleccionar una Fecha\n";
            dtcInitDate.setBorder(errorBorder);
            errorFlag=true;
        }

        if (dtcInitDate.getDate().compareTo(date) <0){
            error_message += "Debe seleccionar una Fecha superior\n";
            dtcInitDate.setBorder(errorBorder);
            errorFlag=true;
        }*/
        
        if (errorFlag==true)
            JOptionPane.showMessageDialog(this, error_message,"Mensaje de edición de pallet",JOptionPane.WARNING_MESSAGE);
        
        return errorFlag;
    }
    
    private Ubicacion recorrerGrid (){
        Ubicacion ubi = null;
        int cant = 0;
        Boolean isChecked = null;
        for (int i = 0; i < tableFreeSpots.getRowCount() ;i++){
            if (cant > 1){
                ubi = null;
                break;
            } 
            isChecked = (Boolean) tableFreeSpots.getValueAt(i, 5);
            if (isChecked!=null && isChecked){
                cant++;
                ubi = ubicaciones.get(i);
            }
        }
        return ubi;
    }
    
    
    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        clearBorders();
        Date date = new Date();
        ArrayList<Pallet> palletsInternar = new ArrayList<Pallet>();
        Ubicacion ub = null;
        Pallet palletEditado = new Pallet();
        palletEditado = p;        
        
        // SE INTERNARA EL PALLET
        if ((jCheckBox1.isSelected()) && (palletEditado.getEstado()== EntityState.Pallets.CREADO.ordinal())){ // El estado original es CREADO
            palletEditado.setEstado(EntityState.Pallets.UBICADO.ordinal());
            ub = recorrerGrid();
            if (ub == null){ //selecciono 0 o mas de 1 ubicacion
                JOptionPane.showMessageDialog(this, "Debe seleccionar sólo una ubicación", "Error para internar pallet", JOptionPane.INFORMATION_MESSAGE);
                return;
            } 
            else { // selecciono solo 1 ubicacion
               palletEditado.setFechaInternamiento(date);
               palletEditado.setFechaVencimiento(dtcInitDate.getDate());
               palletEditado.setUbicacion(ub);
               
               ArrayList<Kardex> kardex = kardexApplication.queryByParameters(palletEditado.getUbicacion().getRack().getAlmacen().getId(), palletEditado.getProducto().getId());
               Kardex internmentKardex = new Kardex();
               internmentKardex.setAlmacen(palletEditado.getUbicacion().getRack().getAlmacen());
               internmentKardex.setProducto(palletEditado.getProducto());
               internmentKardex.setTipoMovimiento("Ingreso");
               internmentKardex.setCantidad(1);
               
                internmentKardex.setFecha(Calendar.getInstance().getTime());
                if (kardex.size() == 0) {
                    internmentKardex.setStockInicial(0);
                } else {
                    internmentKardex.setStockInicial(kardex.get(0).getStockFinal());
                }
                internmentKardex.setStockFinal(internmentKardex.getStockInicial() + 1);

                KardexId kId = new KardexId();
                kId.setIdAlmacen(palletEditado.getUbicacion().getRack().getAlmacen().getId());
                kId.setIdProducto(palletEditado.getProducto().getId());
                internmentKardex.setId(kId);
                
                palletsInternar.add(palletEditado);
                int intern = 0;
                if (palletEditado.getOrdenInternamiento() == null){
                    intern = palletApplication.internNPalletsNoOrder(palletsInternar , internmentKardex);
                }
                else{
                    OrdenInternamientoXProducto op=internmentApplication.getProdOrder(palletEditado.getOrdenInternamiento());
                    intern = palletApplication.internNPallets(palletsInternar ,op , internmentKardex);
                }
                if (intern == 1){

                }
                   

            }    
            
        }
        
        else if (!(jCheckBox1.isSelected()) && (palletEditado.getEstado()== EntityState.Pallets.CREADO.ordinal())){ // NO se interna pero cambia estado
            palletEditado.setFechaVencimiento(dtcInitDate.getDate());
            int x = 0;
            Producto prod = null;
            prod = palletEditado.getProducto();
            if (dtcInitDate.getDate().before(date)){
                palletEditado.setEstado(EntityState.Pallets.VENCIDO.ordinal());
                x = 1;
            }
                
            if (stateN.get(jComboBox2.getSelectedIndex()) == EntityState.Pallets.ROTO.ordinal()){
                palletEditado.setEstado(EntityState.Pallets.ROTO.ordinal());
                x = 1;
            }
                
            if (x == 1){
                prod.setPalletsRegistrados(prod.getPalletsRegistrados()- 1);
                prod.setStockLogico(prod.getStockLogico()-1);
            }
            
            productApplication.update(prod);
            palletApplication.update(palletEditado);

        }
        
        // SE LIBERARA EL PALLET
        else if (palletEditado.getEstado()== EntityState.Pallets.UBICADO.ordinal()){
               OrdenInternamientoXProducto op = null; 
            
               ArrayList<Kardex> kardex = kardexApplication.queryByParameters(palletEditado.getUbicacion().getRack().getAlmacen().getId(), palletEditado.getProducto().getId());
               Kardex internmentKardex = new Kardex();
               internmentKardex.setAlmacen(palletEditado.getUbicacion().getRack().getAlmacen());
               internmentKardex.setProducto(palletEditado.getProducto());
               internmentKardex.setTipoMovimiento("Salida");
               internmentKardex.setCantidad(1);
               
                internmentKardex.setFecha(Calendar.getInstance().getTime());
                if (kardex.size() == 0) {
                    internmentKardex.setStockInicial(0);
                } else {
                    internmentKardex.setStockInicial(kardex.get(0).getStockFinal());
                }
                internmentKardex.setStockFinal(internmentKardex.getStockInicial()  -1);

                KardexId kId = new KardexId();
                kId.setIdAlmacen(palletEditado.getUbicacion().getRack().getAlmacen().getId());
                kId.setIdProducto(palletEditado.getProducto().getId());
                internmentKardex.setId(kId);
        
             if (stateN.get(jComboBox2.getSelectedIndex()) == EntityState.Pallets.CREADO.ordinal()){
                 palletEditado.setEstado(EntityState.Pallets.CREADO.ordinal());
                 palletEditado.setFechaVencimiento(dtcInitDate.getDate());
                 
                 if (dtcInitDate.getDate().before(date))
                    palletEditado.setEstado(EntityState.Pallets.VENCIDO.ordinal());
                 
                 if (palletEditado.getOrdenInternamiento() != null)
                    op=internmentApplication.getProdOrder(palletEditado.getOrdenInternamiento());
                 
                 palletsInternar.add(palletEditado);
                 
                 if (palletEditado.getEstado() == EntityState.Pallets.CREADO.ordinal())
                     palletApplication.liberarPorCreado(palletsInternar, op, internmentKardex);
                 else
                     palletApplication.liberarPorRotoOVencido(palletsInternar, op, internmentKardex);
             }
             else if (stateN.get(jComboBox2.getSelectedIndex()) == EntityState.Pallets.ROTO.ordinal()){
                 palletEditado.setEstado(EntityState.Pallets.ROTO.ordinal());
                 palletEditado.setFechaVencimiento(dtcInitDate.getDate());
                 if (palletEditado.getOrdenInternamiento() != null)
                    op=internmentApplication.getProdOrder(palletEditado.getOrdenInternamiento());
                 
                 palletsInternar.add(palletEditado);
                 palletApplication.liberarPorRotoOVencido(palletsInternar, op, internmentKardex);
                 
             }
             else if (stateN.get(jComboBox2.getSelectedIndex()) == EntityState.Pallets.UBICADO.ordinal()){
                 palletEditado.setFechaVencimiento(dtcInitDate.getDate());
                 if (dtcInitDate.getDate().before(date))
                    palletEditado.setEstado(EntityState.Pallets.VENCIDO.ordinal());
                 if (palletEditado.getOrdenInternamiento() != null)
                    op=internmentApplication.getProdOrder(palletEditado.getOrdenInternamiento());
                 
                 palletsInternar.add(palletEditado);
                 palletApplication.liberarPorRotoOVencido(palletsInternar, op, internmentKardex);
             }
                
                
        }
        else if (palletEditado.getEstado()== EntityState.Pallets.ROTO.ordinal()){
            palletEditado.setFechaVencimiento(dtcInitDate.getDate());
            int x = 0;
            Producto prod = null;
            prod = palletEditado.getProducto();
            if (stateN.get(jComboBox2.getSelectedIndex())==EntityState.Pallets.CREADO.ordinal()){
                palletEditado.setEstado(EntityState.Pallets.CREADO.ordinal());
                if (dtcInitDate.getDate().before(date)){
                    palletEditado.setEstado(EntityState.Pallets.VENCIDO.ordinal());
                }
                else{
                    prod.setPalletsRegistrados(prod.getPalletsRegistrados()+ 1);
                    prod.setStockLogico(prod.getStockLogico()+1);
                }
            }
            else if (stateN.get(jComboBox2.getSelectedIndex()) == EntityState.Pallets.ROTO.ordinal()){
                palletEditado.setEstado(EntityState.Pallets.ROTO.ordinal());
            }
            productApplication.update(prod);
            palletApplication.update(palletEditado);
        }
        else if (palletEditado.getEstado()== EntityState.Pallets.VENCIDO.ordinal()){
            palletEditado.setFechaVencimiento(dtcInitDate.getDate());
            int x = 0;
            Producto prod = null;
            prod = palletEditado.getProducto();
            if (stateN.get(jComboBox2.getSelectedIndex())==EntityState.Pallets.ROTO.ordinal()){
                palletEditado.setEstado(EntityState.Pallets.ROTO.ordinal());
            }
            else if (stateN.get(jComboBox2.getSelectedIndex()) == EntityState.Pallets.VENCIDO.ordinal()){
                if (dtcInitDate.getDate().after(date)){
                    palletEditado.setEstado(EntityState.Pallets.CREADO.ordinal());
                    prod.setPalletsRegistrados(prod.getPalletsRegistrados()+ 1);
                    prod.setStockLogico(prod.getStockLogico()+1);
                }

            }
            productApplication.update(prod);
            palletApplication.update(palletEditado);
        }        
        else if (palletEditado.getEstado()== EntityState.Pallets.DESPACHADO.ordinal()){
        }
        
        JOptionPane.showMessageDialog(this, "Pallet editado correctamente","Mensaje de edición de pallet",JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
    }//GEN-LAST:event_saveBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    public void clearGrid(JTable tabla) {
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0);
    }
    
    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
    Date date = new Date(); 
    if ((dtcInitDate.getDate().before(date)) || (stateN.get(jComboBox2.getSelectedIndex())!= EntityState.Pallets.CREADO.ordinal()))
        JOptionPane.showMessageDialog(this, "No puede internar un producto si no está registrado o que presente una fecha vencida", "Error para internar pallet", JOptionPane.INFORMATION_MESSAGE);
    else{
        if (jCheckBox1.isSelected()){
            comboWarehouse.setEnabled(true);
            jComboBox2.setEnabled(false);
            dtcInitDate.setEnabled(false);
            if (comboWarehouse.getItemCount() > 0 ){
                comboWarehouse.setSelectedIndex(0);
                fillFreeSpots();
            }
        }
            else{
                clearGrid(tableFreeSpots);
                comboWarehouse.setEnabled(false);
                jComboBox2.setEnabled(true);
                dtcInitDate.setEnabled(true);
            }
    }

        
    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    private void productComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_productComboItemStateChanged
        Producto p = product.get(productCombo.getSelectedIndex());
        comboWarehouse.removeAllItems();
        fillWarehouseCombo(p.getCondicion().getId());
        
    }//GEN-LAST:event_productComboItemStateChanged

    private void fillFreeSpots(){        
            clearGrid(tableFreeSpots);
            ubicaciones.clear();
            if (comboWarehouse.getItemCount() > 0) {
                ubicaciones.clear();
                DefaultTableModel model = (DefaultTableModel) tableFreeSpots.getModel();
                ArrayList<Ubicacion> ubi = new ArrayList<Ubicacion>();
                Almacen alm = warehouses.get(comboWarehouse.getSelectedIndex());
                ubi = spotApplication.querySpotsByWarehouse2(alm.getId());
                ubicaciones.addAll(ubi);
                    for (Ubicacion ub : ubi) {
                        model.addRow(new Object[]{
                            ub.getRack().getAlmacen().getId(),
                            ub.getRack().getId(),
                            ub.getLado(),
                            ub.getFila(),
                            ub.getColumna()
                        });
                    }

            }else
            JOptionPane.showMessageDialog(this, "No existe ningún almacén creado que cumpla con la condición del producto.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
    
    }
    
    private void comboWarehouseItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboWarehouseItemStateChanged
         if (evt.getStateChange() == ItemEvent.SELECTED){
          if (jCheckBox1.isSelected()){
             fillFreeSpots();
          }
         }
        
         
    }//GEN-LAST:event_comboWarehouseItemStateChanged

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JComboBox comboWarehouse;
    private com.toedter.calendar.JDateChooser dtcInitDate;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JComboBox productCombo;
    private javax.swing.JButton saveBtn;
    private javax.swing.JTable tableFreeSpots;
    // End of variables declaration//GEN-END:variables
}
