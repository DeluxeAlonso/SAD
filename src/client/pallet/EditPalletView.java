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
    ArrayList<Integer> estados = new ArrayList<Integer>();
    ArrayList<String> estadosStr = new ArrayList<String>();
    ArrayList<Almacen> warehouses =new ArrayList<Almacen>();
    Pallet p =null;
    ArrayList<Producto> product = new ArrayList<Producto>();
    Pallet pNuevo = new Pallet();
    ArrayList<Ubicacion> ubicaciones = new ArrayList<Ubicacion>();
    
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
        fillEstadoCombo();
        //fillWarehouseCombo(product.get(0).getId());
        jComboBox3.setVisible(false);
        jLabel5.setVisible(false);
        
        if (p.getUbicacion()!= null){
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            Ubicacion ub = p.getUbicacion();
            //Object[] colIdent = {"Almacén","Rack","Lado","Fila","Columna"};
            //model.setColumnIdentifiers(colIdent);
            model.setColumnCount(4);
            model.addRow(new Object[]{
                   p.getUbicacion().getRack().getId(),
                   p.getUbicacion().getLado(),
                   p.getUbicacion().getFila(),
                   p.getUbicacion().getColumna()
                });
        jComboBox1.setEnabled(false);
        jTable1.setEnabled(false);
        jCheckBox1.setVisible(false);
        }
        
        
        this.jComboBox1.setSelectedItem(prod.getNombre());
        this.dtcInitDate.setDate(p.getFechaVencimiento());
        int x = p.getEstado();
        int y = estados.indexOf(x);
        this.jComboBox2.setSelectedItem(estadosStr.get(y));
        this.jTextField1.setText(p.getEan128());
        Icons.setButton(saveBtn, Icons.ICONOS.SAVE.ordinal());
        Icons.setButton(cancelBtn, Icons.ICONOS.CANCEL.ordinal());
    }
    
    public void clearBorders(){
        jComboBox1.setBorder(regularBorder);
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
            jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(whNames));
        }
        
    }
    
    private void fillProductCombo(){
        product = productApplication.getAllProducts();
        if (product!=null && product.size()!=0){
            
            String[] prNames = new String[product.size()];
            for (int i=0; i < product.size();i++){
                prNames[i]=product.get(i).getNombre();
            }
            jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(prNames));
        }
        
    }
    
    private void fillEstadoCombo(){
            estados.add(EntityState.Pallets.CREADO.ordinal());
            //estados.add(EntityState.Pallets.DESPACHADO.ordinal());
            estados.add(EntityState.Pallets.UBICADO.ordinal());
            estados.add(EntityState.Pallets.ELIMINADO.ordinal());

            estadosStr.add("CREADO");
            estadosStr.add("DESPACHADO");
            estadosStr.add("UBICADO");
            estadosStr.add("ELIMINADO");
            EntityState.getPalletsState();
            
        
        if (estados!=null && estados.size()!=0){
            
            String[] prNames = new String[estados.size()];
            for (int i=0; i < estados.size();i++){
                prNames[i]= estadosStr.get(i);
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
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jComboBox3 = new javax.swing.JComboBox();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
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

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rack", "Lado", "Fila", "Columna", "Seleccione"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Pallet"));
        jPanel1.setName("asd"); // NOI18N

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
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

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
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
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(84, 84, 84)
                                .addComponent(jCheckBox1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(21, Short.MAX_VALUE))))
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
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(0, 11, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(dtcInitDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47))
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
        }*/
        if ((estados.get(jComboBox2.getSelectedIndex())==EntityState.Pallets.UBICADO.ordinal())&&(!jCheckBox1.isSelected())){
           if (p.getEstado()!= EntityState.Pallets.UBICADO.ordinal()){
                error_message += "Debe internar primero\n";
                jCheckBox1.setBorder(errorBorder);
                errorFlag=true;
           }
        }
        if (dtcInitDate.getDate().compareTo(date) <0){
            error_message += "Debe seleccionar una Fecha superior\n";
            dtcInitDate.setBorder(errorBorder);
            errorFlag=true;
        }
        
        if (errorFlag==true)
            JOptionPane.showMessageDialog(this, error_message,"Mensaje de edición de pallet",JOptionPane.WARNING_MESSAGE);
        
        return errorFlag;
    }
    
    
    
    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        clearBorders();
        int cont = 0;
        Boolean isChecked = null;
        Ubicacion u = new Ubicacion();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        pNuevo.setFechaVencimiento(dtcInitDate.getDate());
        pNuevo.setProducto(product.get(jComboBox1.getSelectedIndex()));
        pNuevo.setEstado(estados.get(jComboBox2.getSelectedIndex()));
        
        if (!hasErrors()){
            p.setFechaVencimiento(pNuevo.getFechaVencimiento());

            if (p.getUbicacion() == null){ //no tiene ubicacion y se podria internar
                /*if (p.getProducto() != pNuevo.getProducto()){
                    Producto prod1 = product.get(p.getProducto().getId());
                    Producto prod2 = product.get(pNuevo.getProducto().getId());
                    
                    
                }*/
                
                if (jCheckBox1.isSelected()){ // si se interna
                    for (int i = 0;i<model.getRowCount();i++){
                        isChecked = (Boolean)model.getValueAt(i, 4); 
                        if (isChecked != null && isChecked){
                            cont++;
                            u = ubicaciones.get(i);
                        }
                    }
                    if (cont != 1){
                        jTable1.setBorder(errorBorder);
                        JOptionPane.showMessageDialog(this, "Debe seleccionar solo 1 ubicación","Mensaje de creación de pallet",JOptionPane.WARNING_MESSAGE);
                    }
                    else if (cont == 1){
                        spotApplication.updateSpotOccupancy(u.getId(), EntityState.Spots.OCUPADO.ordinal());
                        Producto prod = product.get(p.getProducto().getId());
                        prod.setPalletsUbicados(prod.getPalletsUbicados()+1);
                        prod.setPalletsRegistrados(prod.getPalletsRegistrados()-1);
                        productApplication.update(prod);
                        
                        Almacen alm = warehouses.get(jComboBox3.getSelectedIndex());
                        alm.setUbicLibres(alm.getUbicLibres()-1);
                        warehouseApplication.update(alm);
                        
                        if (p.getOrdenInternamiento() != null){
                            OrdenInternamientoXProducto ordenXProd = internmentApplication.getProdOrder(p.getOrdenInternamiento());
                            ordenXProd.setCantidadIngresada(ordenXProd.getCantidadIngresada()+1);
                            internmentApplication.incCantOrderXProd(ordenXProd);

                        }
                        
                        ArrayList<Kardex> kardex = kardexApplication.queryByParameters(warehouses.get(jComboBox3.getSelectedIndex()).getId(), prod.getId());
                        Kardex internmentKardex = new Kardex();
                        internmentKardex.setAlmacen(warehouses.get(jComboBox3.getSelectedIndex()));
                        internmentKardex.setProducto(prod);
                        internmentKardex.setTipoMovimiento("Ingreso");
                        internmentKardex.setCantidad(1);

                        internmentKardex.setFecha(Calendar.getInstance().getTime());
                        if(kardex.size()==0){
                            internmentKardex.setStockInicial(0);
                        }else{
                            internmentKardex.setStockInicial(kardex.get(0).getStockFinal());
                        }
                        internmentKardex.setStockFinal(internmentKardex.getStockInicial() + 1);
                        KardexId kId = new KardexId();
                        kId.setIdAlmacen(warehouses.get(jComboBox3.getSelectedIndex()).getId());
                        kId.setIdProducto(prod.getId());
                        internmentKardex.setId(kId);
                        kardexApplication.insert(internmentKardex);
                        //kardexApplication.insertKardexID(kId);

                        
                        pNuevo.setUbicacion(u);
                        
                        p.setEstado(pNuevo.getEstado());
                        p.setProducto(pNuevo.getProducto());
                        p.setUbicacion(pNuevo.getUbicacion());
                        palletApplication.update(p);
                        
                    }
              }        
                else{ //no se internara
                     p.setEstado(pNuevo.getEstado());
                     p.setProducto(pNuevo.getProducto());
                     palletApplication.update(p);
                }
            }
            else{ // tiene ubicacion
                if ((pNuevo.getEstado() == EntityState.Pallets.CREADO.ordinal()) ||(pNuevo.getEstado() == EntityState.Pallets.ELIMINADO.ordinal())){
                    spotApplication.updateSpotOccupancy(p.getUbicacion().getId(), EntityState.Spots.LIBRE.ordinal());
                    Producto prod = product.get(p.getProducto().getId());
                    prod.setPalletsUbicados(prod.getPalletsUbicados()-1);
                    prod.setPalletsRegistrados(prod.getPalletsRegistrados()+1);

                    ArrayList<Kardex> kardex = kardexApplication.queryByParameters(warehouses.get(jComboBox3.getSelectedIndex()).getId(), prod.getId());
                        Kardex internmentKardex = new Kardex();
                        internmentKardex.setAlmacen(warehouses.get(jComboBox3.getSelectedIndex()));
                        internmentKardex.setProducto(prod);
                        internmentKardex.setTipoMovimiento("Salida");
                        internmentKardex.setCantidad(1);

                        internmentKardex.setFecha(Calendar.getInstance().getTime());
                        if(kardex.size()==0){
                            internmentKardex.setStockInicial(0);
                        }else{
                            internmentKardex.setStockInicial(kardex.get(0).getStockFinal());
                        }
                        internmentKardex.setStockFinal(internmentKardex.getStockInicial() - 1);
                        KardexId kId = new KardexId();
                        kId.setIdAlmacen(warehouses.get(jComboBox3.getSelectedIndex()).getId());
                        kId.setIdProducto(prod.getId());
                        internmentKardex.setId(kId);
                        kardexApplication.insert(internmentKardex);

                    
                    
                    if (pNuevo.getEstado() == EntityState.Pallets.ELIMINADO.ordinal()){
                        prod.setStockLogico(prod.getStockLogico()-1);    
                    }
                    
                    productApplication.update(prod);
                    pNuevo.setUbicacion(null);
                    
                    if (p.getOrdenInternamiento() != null && pNuevo.getEstado()==EntityState.Pallets.CREADO.ordinal()){
                        OrdenInternamientoXProducto ordenXProd = internmentApplication.getProdOrder(p.getOrdenInternamiento());
                        ordenXProd.setCantidadIngresada(ordenXProd.getCantidadIngresada()-1);
                        internmentApplication.incCantOrderXProd(ordenXProd);
                        OrdenInternamiento ord = internmentApplication.queryById(p.getOrdenInternamiento().getId());
                        ord.setEstado(EntityState.InternmentOrders.REGISTRADA.ordinal());
                        internmentApplication.update(ord);
                        
                    }
                    
                    p.setEstado(pNuevo.getEstado());
                    p.setProducto(pNuevo.getProducto());
                    p.setUbicacion(pNuevo.getUbicacion());
                    palletApplication.update(p);
                    
                }
                
                
                

            }
            
        JOptionPane.showMessageDialog(this, "Se ha editado el pallet correctamente");
        this.dispose();
        }
        
        
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
     if (jCheckBox1.isSelected()){
        
        jLabel5.setVisible(true);
        jComboBox1.setSelectedIndex(1);
        jComboBox3.setVisible(true);
        jComboBox2.setEnabled(false);
     }
     else{
        jComboBox3.setVisible(false);
        jLabel5.setVisible(false);
        clearGrid(jTable1);
        jComboBox2.setEnabled(true);
     }
        
    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        Producto p = product.get(jComboBox1.getSelectedIndex());
        jComboBox3.removeAllItems();
        fillWarehouseCombo(p.getCondicion().getId());
        
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void fillFreeSpots(Almacen alm){        
            clearGrid(jTable1);
            ubicaciones.clear();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            ArrayList<Ubicacion> ubi = new ArrayList<Ubicacion>();
            ArrayList<Rack> racks = rackApplication.queryRacksByWarehouse(alm.getId());
            for (Rack r : racks){
                //r.getUbicacions().
                ubi = (ArrayList<Ubicacion>) spotApplication.queryEmptySpotsByRack(r.getId());
                ubicaciones.addAll(ubi);
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
    
    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
         if (evt.getStateChange() == ItemEvent.SELECTED){
          if (jCheckBox1.isSelected()){
             fillFreeSpots(warehouses.get(jComboBox3.getSelectedIndex()));
          }
         }
        
         
    }//GEN-LAST:event_jComboBox3ItemStateChanged

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private com.toedter.calendar.JDateChooser dtcInitDate;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton saveBtn;
    // End of variables declaration//GEN-END:variables
}
