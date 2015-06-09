/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.internment;

import application.internment.InternmentApplication;
import application.kardex.KardexApplication;
import application.pallet.PalletApplication;
import application.product.ProductApplication;
import application.rack.RackApplication;
import application.spot.SpotApplication;
import application.warehouse.WarehouseApplication;
import client.base.BaseView;
import client.warehouse.EditWarehouseView;
import entity.Almacen;
import entity.Condicion;
import entity.Kardex;
import entity.KardexId;
import entity.OrdenInternamiento;
import entity.OrdenInternamientoXProducto;
import entity.OrdenInternamientoXProductoId;
import entity.Producto;
import entity.Pallet;
import entity.Rack;
import entity.Ubicacion;
import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Hibernate;
import util.EntityState;
import util.EntityType;
import util.InstanceFactory;
import util.Strings;

/**
 *
 * @author KEVIN BROWN
 */
public class InternmentSelectView extends BaseView {
    WarehouseApplication warehouseApplication=InstanceFactory.Instance.getInstance("warehouseApplication", WarehouseApplication.class);
    InternmentApplication internmentApplication=InstanceFactory.Instance.getInstance("internmentApplication", InternmentApplication.class);
    ProductApplication productApplication=InstanceFactory.Instance.getInstance("productApplication", ProductApplication.class);
    PalletApplication palletApplication=InstanceFactory.Instance.getInstance("palletApplication",PalletApplication.class);
    RackApplication rackApplication=InstanceFactory.Instance.getInstance("rackApplication", RackApplication.class);
    SpotApplication spotApplication = InstanceFactory.Instance.getInstance("spotApplicaiton", SpotApplication.class);
    KardexApplication kardexApplication = InstanceFactory.Instance.getInstance("kardexApplication",KardexApplication.class);
    public static InternmentSelectView internmentSelectView;
    
    JFileChooser fc = new JFileChooser();
    File file = null;
    
    /**
     * Creates new form InternmentSelectView
     */
    
    ArrayList<Buffer> ordenListada = new ArrayList<Buffer>();
    ArrayList<Almacen> almacenes = new ArrayList<Almacen>();
    public static class Buffer{
        public int id_item;
        public String fecha;
        public int cantidad;
    }
    JTable table = null;
    public int cantAInternar;
    public OrdenInternamiento ordenAInternar=null;
    public ArrayList<Ubicacion> ubicaciones = new ArrayList<Ubicacion>();
    public ArrayList<OrdenInternamiento> orders = new ArrayList<OrdenInternamiento>();
    
    public InternmentSelectView() {
        initComponents();
        super.initialize();
        internmentSelectView = this;
        setTitle("Internamiento");
        jButton1.setEnabled(false);
        jButton2.setEnabled(false);
        //jButton3.setEnabled(false);
        jComboBox1.removeAllItems();
        table = jTable2;
        jTextField3.setVisible(false);
        jLabel3.setVisible(false);
        /*table.getModel().addTableModelListener(new TableModelListener() {
        public void tableChanged(TableModelEvent e) {
        Boolean isChecked;
        int count=0;
        int aux = cantAInternar;
        if(table.getRowCount()>0){
            for (int i = 0; i < table.getRowCount(); i++) {
                aux=cantAInternar;
                isChecked = (Boolean)table.getValueAt(i, 4);
                if (isChecked != null && isChecked) {
                    count++;
                }
                aux=aux-count;
                jTextField3.setText(Integer.toString(aux));
            }
        } 
      }
    });
    */    
        
        fillTable();
    }

    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblFileChooser = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblFileChooser1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton3 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Crear Pedido");

        lblFileChooser.setText(" Ingrese la orden de internamiento desde un archivo:");

        jTextField1.setEditable(false);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("Cargar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Num. Orden", "Producto", "Fecha de Vcto.", "Cantidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        lblFileChooser1.setText("Órdenes pendientes:");

        jButton2.setText("Internar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rack", "Lado", "Fila", "Columna", "Seleccione"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
            jTable2.getColumnModel().getColumn(2).setResizable(false);
            jTable2.getColumnModel().getColumn(3).setResizable(false);
            jTable2.getColumnModel().getColumn(4).setResizable(false);
        }

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Seleccione almacén destino:");

        jLabel2.setText("Ubicaciones Disponibles:");

        jTextField2.setEditable(false);

        jTextField3.setEditable(false);

        jLabel3.setText("Cantidad Pendiente por internar:");

        jCheckBox1.setText("Seleccionar automático");
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });

        jButton3.setText("...");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFileChooser)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jTextField1)
                                    .addGap(28, 28, 28)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblFileChooser1))
                        .addGap(81, 81, 81)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jCheckBox1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblFileChooser)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton3))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFileChooser1)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(jButton2))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    public static String crearEAN128(Pallet pallet){
        String ean = "";
        //ean += pallet.getId();
        ean += "(02)0" + pallet.getProducto().getEan13();
        ean += "37" + pallet.getProducto().getCantidadProductosEnPallet();
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        ean += "(17)" + dateFormat.format(pallet.getFechaVencimiento());
        return ean;
    }
    
    public void fillTable(){
        clearGrid(jTable1);
        int x = 0;
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        orders.clear();
        orders.addAll(internmentApplication.queryByType(EntityState.InternmentOrders.REGISTRADA.ordinal()));
        orders.addAll(internmentApplication.queryByType(EntityState.InternmentOrders.PENDIENTE.ordinal()));
        for (OrdenInternamiento or : orders){
            Producto prod = internmentApplication.getProdOrder(or).getProducto();
            ArrayList<Pallet> pal = palletApplication.getPalletsFromOrder(or.getId());
            x = internmentApplication.getProdOrder(or).getCantidad() - internmentApplication.getProdOrder(or).getCantidadIngresada();
            model.addRow(new Object[]{
               or.getId(),
               prod.getNombre(),
               pal.get(0).getFechaVencimiento(),
               x
            }); 
        }
        
    }
    
        public void fillComboWarehouse(int type){
        jComboBox1.removeAllItems();
        almacenes = warehouseApplication.queryWarehousesByType(type);
        if(almacenes.size()>0){
            String[] nombresAlmacen = new String[almacenes.size()];
            for(int i=0; i<almacenes.size(); i++){
                nombresAlmacen[i] = almacenes.get(i).getDescripcion();
            }
            jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(nombresAlmacen));
        }
    }
    
    
    public void clearGrid(JTable tabla) {
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0);
    }

    private void loadFromFile(String csvFile){
        ordenListada.clear();
        //JFileChooser fc = new JFileChooser();
        BufferedReader br = null;        
        String line = "";
        String cvsSplitBy = ",";
        
            try {
                jTextField1.setText(file.getAbsolutePath());
                br = new BufferedReader(new FileReader(csvFile));
                
                while ((line = br.readLine()) != null){
                    String[] lectura = line.split(cvsSplitBy);
                    Buffer buff = new Buffer();
                    buff.id_item =  Integer.parseInt(lectura[0]);
                    buff.fecha = lectura[1];
                    buff.cantidad = Integer.parseInt(lectura[2]);
                    ordenListada.add(buff);
                }
                
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(InternmentSelectView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(InternmentSelectView.class.getName()).log(Level.SEVERE, null, ex);
            }
           
             
        
        createInternmentOrders(ordenListada);
        fillTable(); 
    }
    
    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {                                           

    } 
    
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        loadFromFile(file.getAbsolutePath());
        file = null;
        jButton1.setEnabled(false);
        jTextField1.setText("");
        
    }//GEN-LAST:event_jButton1ActionPerformed

    public void createInternmentOrders(ArrayList<Buffer> listaBuff){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for (Buffer b : listaBuff){
            //OrdenInternamiento
            OrdenInternamiento orden = new OrdenInternamiento();
            orden.setFecha(cal.getTime());
            orden.setEstado(EntityState.InternmentOrders.REGISTRADA.ordinal());//REGISTRADA
            int x = internmentApplication.insert(orden);
            //OrdenInternamientoXProducto
            OrdenInternamientoXProductoId id = new OrdenInternamientoXProductoId();
            id.setIdOrdenInternamiento(x);
            id.setIdProducto(b.id_item);
            OrdenInternamientoXProducto ordenXProducto = new OrdenInternamientoXProducto();
            ordenXProducto.setOrdenInternamiento(internmentApplication.queryById(x));
            ordenXProducto.setProducto(productApplication.queryById(b.id_item));
            ordenXProducto.setCantidad(b.cantidad);
            ordenXProducto.setCantidadIngresada(0);
            ordenXProducto.setId(id);
            internmentApplication.insertOrdenXProducto(ordenXProducto);
            //stocks en producto
            Producto prod = internmentApplication.getProdOrder(orden).getProducto();
            prod.setPalletsRegistrados(prod.getPalletsRegistrados()+b.cantidad);
            prod.setStockLogico(prod.getStockLogico()+b.cantidad);
            productApplication.update(prod);
            //Pallets
            for (int i=0;i<b.cantidad;i++){
                Pallet pallet = new Pallet();
                pallet.setEstado(EntityState.Pallets.CREADO.ordinal());//CREADO
                pallet.setFechaRegistro(orden.getFecha());
                try {
                    pallet.setFechaVencimiento( formatter.parse(b.fecha) );
                } catch (ParseException ex) {
                    Logger.getLogger(InternmentSelectView.class.getName()).log(Level.SEVERE, null, ex);
                }
                pallet.setOrdenInternamiento(internmentApplication.queryById(x));
                pallet.setProducto(productApplication.queryById(b.id_item));
                pallet.setEan128(crearEAN128(pallet));
                int eanAux=palletApplication.insert(pallet);
                
                Pallet palletAux = palletApplication.queryById(eanAux);
                String ean = palletAux.getEan128();
                ean+=eanAux;
                palletAux.setEan128(ean);
                palletApplication.update(palletAux);
                
            }
        }
        
    }
    
 
    
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (Integer.parseInt(jTextField3.getText()) >= 0){
            internarPallets();
        }
        else{
            JOptionPane.setDefaultLocale(new Locale("es", "ES"));
            JOptionPane.showMessageDialog(this, "Seleccione una cantidad de ubicaciones menor o igual a la cantidad de pallets","Error al Internar pallets",JOptionPane.ERROR_MESSAGE);
        }
          

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        // TODO add your handling code here:
            jButton2.setEnabled(true);
            //jButton3.setEnabled(true);
        
        
        int row = jTable1.getSelectedRow();
        int cod = Integer.parseInt(jTable1.getModel().getValueAt(row,0).toString());
        OrdenInternamiento orden = internmentApplication.queryById(cod);
        Producto prod = internmentApplication.getProdOrder(orden).getProducto();
        cantAInternar = internmentApplication.getProdOrder(orden).getCantidad() - internmentApplication.getProdOrder(orden).getCantidadIngresada();
        jTextField3.setText(Integer.toString(cantAInternar));
        ordenAInternar = orden;
        fillComboWarehouse(prod.getCondicion().getId());
        fillFreeSpots();
        jCheckBox1.setSelected(false);
        
    }//GEN-LAST:event_jTable1MousePressed

    
    
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBox1ActionPerformed

    
    private void internarPallets(){
        JTable table = jTable2;    
        int cant = 0;
        int aux = 0;
        Boolean isChecked=null;
        if (ordenAInternar != null){
            ArrayList<Pallet> pallets = palletApplication.getPalletsFromOrder(ordenAInternar.getId());
            ArrayList<Pallet> palletsInter = new ArrayList<Pallet>();
            for (int i = 0;i< pallets.size();i++){
                if (pallets.get(i).getUbicacion() == null)
                    palletsInter.add(pallets.get(i));
            }
            if(table.getRowCount()>0){
            for (int i = 0; i < table.getRowCount(); i++) {
                if (internmentApplication.getProdOrder(ordenAInternar).getCantidadIngresada() == internmentApplication.getProdOrder(ordenAInternar).getCantidad()){
                    //ordenAInternar.setEstado(EntityState.InternmentOrders.INTERNADA.ordinal());
                    OrdenInternamiento ord = internmentApplication.queryById(ordenAInternar.getId());
                    ord.setEstado(EntityState.InternmentOrders.INTERNADA.ordinal());
                    internmentApplication.update(ord);
                    aux = cant-1;
                    break;
                }
                
                isChecked = (Boolean)table.getValueAt(i, 4);
                if (isChecked != null && isChecked) {
                    //meter ubicacion al pallet  
                    
                    //palletApplication.updateSpot(palletsInter.get(cant).getId(),ubicaciones.get(i).getId());
                    Pallet pall = palletsInter.get(cant);
                    pall.setEstado(EntityState.Pallets.UBICADO.ordinal());
                    pall.setUbicacion(ubicaciones.get(i));
                    palletApplication.update(pall);
                    
                    // cambiar estado de ubicacion a ocupada

                    spotApplication.updateSpotOccupancy(ubicaciones.get(i).getId(), EntityState.Spots.OCUPADO.ordinal());
                                    
                    //actualizar cant a internar en ordeninteramientoXproducto
                    OrdenInternamientoXProducto ordenXProd = internmentApplication.getProdOrder(ordenAInternar);
                    ordenXProd.setCantidadIngresada(ordenXProd.getCantidadIngresada()+1);
                    internmentApplication.incCantOrderXProd(ordenXProd);
                    
                    
                    
                    // actualizar stock total del producto
                    /*Producto prod = internmentApplication.getProdOrder(ordenAInternar).getProducto();
                    prod.setStockTotal(prod.getStockTotal()+1);
                    productApplication.update(prod);
                    */
                    
                    //disminuir ubicaciones libres en racks y almacen
                    /*Almacen alm = almacenes.get(jComboBox1.getSelectedIndex());
                    alm.setUbicLibres(alm.getUbicLibres()-1);
                    warehouseApplication.update(alm);
                    */
                    
                    cant++;
                    aux = cant;
                } 
                if (internmentApplication.getProdOrder(ordenAInternar).getCantidadIngresada() == internmentApplication.getProdOrder(ordenAInternar).getCantidad()){
                    //ordenAInternar.setEstado(EntityState.InternmentOrders.INTERNADA.ordinal());
                    OrdenInternamiento ord = internmentApplication.queryById(ordenAInternar.getId());
                    ord.setEstado(EntityState.InternmentOrders.INTERNADA.ordinal());
                    internmentApplication.update(ord);
                    aux = cant;
                    break;
                }
            }
            
            // actualizar pallets registrados y ubicados del producto
            Producto prod = internmentApplication.getProdOrder(ordenAInternar).getProducto();
            prod.setPalletsUbicados(prod.getPalletsUbicados()+aux);
            prod.setPalletsRegistrados(prod.getPalletsRegistrados()-aux);
            productApplication.update(prod);
            
            //actualizar cant a internar en ordeninteramientoXproducto
            //OrdenInternamientoXProducto ordenXProd = internmentApplication.getProdOrder(ordenAInternar);
            //ordenXProd.setCantidadIngresada(ordenXProd.getCantidadIngresada()+aux);
            //internmentApplication.incCantOrderXProd(ordenXProd);
            
            //disminuir ubicaciones libres en racks y almacen
            Almacen alm = almacenes.get(jComboBox1.getSelectedIndex());
            alm.setUbicLibres(alm.getUbicLibres()-aux);
            warehouseApplication.update(alm);
            
            
            
            //ingresar entrada en kardex
            ArrayList<Kardex> kardex = kardexApplication.queryByParameters(almacenes.get(jComboBox1.getSelectedIndex()).getId(), internmentApplication.getProdOrder(ordenAInternar).getProducto().getId());
            Kardex internmentKardex = new Kardex();
            internmentKardex.setAlmacen(almacenes.get(jComboBox1.getSelectedIndex()));
            internmentKardex.setProducto(internmentApplication.getProdOrder(ordenAInternar).getProducto());
            internmentKardex.setTipoMovimiento("Ingreso");
            internmentKardex.setCantidad(aux);
            
            internmentKardex.setFecha(Calendar.getInstance().getTime());
            if(kardex.size()==0){
                internmentKardex.setStockInicial(0);
            }else{
                internmentKardex.setStockInicial(kardex.get(0).getStockFinal());
            }
            internmentKardex.setStockFinal(internmentKardex.getStockInicial() + aux);
            
            KardexId kId = new KardexId();
            kId.setIdAlmacen(almacenes.get(jComboBox1.getSelectedIndex()).getId());
            kId.setIdProducto(internmentApplication.getProdOrder(ordenAInternar).getProducto().getId());
            
            internmentKardex.setId(kId);
            
            kardexApplication.insert(internmentKardex);
            //kardexApplication.insertKardexID(kId);
            
        }
        fillTable();
        clearGrid(jTable2);
        jButton2.setEnabled(false);
        //jButton3.setEnabled(false);    
        jComboBox1.removeAllItems();
        jTextField2.setText("");
        jTextField3.setText("");
        }
    }
    
    
    private void fillFreeSpots(){
            
            clearGrid(jTable2);
            ubicaciones.clear();
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            ArrayList<Ubicacion> ubi = new ArrayList<Ubicacion>();
            Almacen alm = almacenes.get(jComboBox1.getSelectedIndex());
            jTextField2.setText(alm.getUbicLibres().toString());
            System.out.println("Almacen id  " + alm.getId());
            ArrayList<Rack> racks = rackApplication.queryRacksByWarehouse(alm.getId());
            System.out.println("Racks " + racks.size());
            for (Rack r : racks){
                //r.getUbicacions().
                ubi = (ArrayList<Ubicacion>) spotApplication.queryEmptySpotsByRack(r.getId());
                System.out.println("Ubicaciones " + ubi.size());
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
    
    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
        if(evt.getStateChange() == ItemEvent.SELECTED){
             fillFreeSpots();
        }
        
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
        DefaultTableModel table = (DefaultTableModel) jTable2.getModel();
        int cant = 0;
        if (jCheckBox1.isSelected()){
            fillFreeSpots();
            for (int i=0;i<table.getRowCount();i++){
                table.setValueAt(true, i, 4);
                cant++;
                if (cant==cantAInternar)
                    break;
            }
        }
        else
            fillFreeSpots();
    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        fc.setDialogTitle("Seleccione un archivo");
        fc.showOpenDialog(this);
        file = fc.getSelectedFile();
        if (!fc.getSelectedFile().getName().endsWith(".csv")) {
            JOptionPane.setDefaultLocale(new Locale("es", "ES"));
            JOptionPane.showMessageDialog(this, Strings.ERROR_NOT_CSV,Strings.ERROR_FILE_UPLOAD_TITLE,JOptionPane.WARNING_MESSAGE);
            jTextField1.setText("");
            jButton1.setEnabled(false);
        }
        else{
            jTextField1.setText(file.getAbsolutePath());
            jButton1.setEnabled(true);
        }
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel lblFileChooser;
    private javax.swing.JLabel lblFileChooser1;
    // End of variables declaration//GEN-END:variables
}
