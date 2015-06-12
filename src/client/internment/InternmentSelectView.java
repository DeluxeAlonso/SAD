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
import entity.Almacen;
import entity.Kardex;
import entity.KardexId;
import entity.OrdenInternamiento;
import entity.OrdenInternamientoXProducto;
import entity.OrdenInternamientoXProductoId;
import entity.Producto;
import entity.Pallet;
import entity.Rack;
import entity.Ubicacion;
import java.awt.Cursor;
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
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import util.EntityState;
import util.InstanceFactory;
import util.Strings;

/**
 *
 * @author KEVIN BROWN
 */
public class InternmentSelectView extends BaseView {

    WarehouseApplication warehouseApplication = InstanceFactory.Instance.getInstance("warehouseApplication", WarehouseApplication.class);
    InternmentApplication internmentApplication = InstanceFactory.Instance.getInstance("internmentApplication", InternmentApplication.class);
    ProductApplication productApplication = InstanceFactory.Instance.getInstance("productApplication", ProductApplication.class);
    PalletApplication palletApplication = InstanceFactory.Instance.getInstance("palletApplication", PalletApplication.class);
    RackApplication rackApplication = InstanceFactory.Instance.getInstance("rackApplication", RackApplication.class);
    SpotApplication spotApplication = InstanceFactory.Instance.getInstance("spotApplicaiton", SpotApplication.class);
    KardexApplication kardexApplication = InstanceFactory.Instance.getInstance("kardexApplication", KardexApplication.class);
    public static InternmentSelectView internmentSelectView;

    //JFileChooser fc = new JFileChooser();
    File file = null;

    /**
     * Creates new form InternmentSelectView
     */
    ArrayList<Buffer> ordenListada = new ArrayList<Buffer>();
    ArrayList<Almacen> almacenes = new ArrayList<Almacen>();

    public static class Buffer {

        public int id_item;
        public String fecha;
        public int cantidad;
    }
    JTable table = null;
    public int cantAInternar;
    public OrdenInternamiento ordenAInternar = null;
    public ArrayList<Ubicacion> ubicaciones = new ArrayList<Ubicacion>();
    public ArrayList<OrdenInternamiento> orders = new ArrayList<OrdenInternamiento>();

    public InternmentSelectView() {
        initComponents();
        super.initialize();
        setTitle("Internamiento");
        btnLoadFile.setEnabled(false);
        btnIntern.setEnabled(false);
        //jButton3.setEnabled(false);
        comboWarehouse.removeAllItems();
        table = tableFreeSpots;
        //txtPendingInterns.setVisible(false);
        //jLabel3.setVisible(false);
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
        btnLoadFile = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableInternOrders = new javax.swing.JTable();
        lblFileChooser1 = new javax.swing.JLabel();
        btnIntern = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableFreeSpots = new javax.swing.JTable();
        comboWarehouse = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtAvUbication = new javax.swing.JTextField();
        txtPendingInterns = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        checkboxSelectAll = new javax.swing.JCheckBox();
        btnChooseFile = new javax.swing.JButton();

        setClosable(true);
        setTitle("Crear Pedido");

        lblFileChooser.setText(" Ingrese la orden de internamiento desde un archivo:");

        jTextField1.setEditable(false);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        btnLoadFile.setText("Cargar");
        btnLoadFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadFileActionPerformed(evt);
            }
        });

        tableInternOrders.setModel(new javax.swing.table.DefaultTableModel(
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
        tableInternOrders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableInternOrdersMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tableInternOrders);
        if (tableInternOrders.getColumnModel().getColumnCount() > 0) {
            tableInternOrders.getColumnModel().getColumn(0).setResizable(false);
            tableInternOrders.getColumnModel().getColumn(1).setResizable(false);
            tableInternOrders.getColumnModel().getColumn(2).setResizable(false);
            tableInternOrders.getColumnModel().getColumn(3).setResizable(false);
        }

        lblFileChooser1.setText("Órdenes pendientes:");

        btnIntern.setText("Internar");
        btnIntern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInternActionPerformed(evt);
            }
        });

        tableFreeSpots.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tableFreeSpots);
        if (tableFreeSpots.getColumnModel().getColumnCount() > 0) {
            tableFreeSpots.getColumnModel().getColumn(0).setResizable(false);
            tableFreeSpots.getColumnModel().getColumn(1).setResizable(false);
            tableFreeSpots.getColumnModel().getColumn(2).setResizable(false);
            tableFreeSpots.getColumnModel().getColumn(3).setResizable(false);
            tableFreeSpots.getColumnModel().getColumn(4).setResizable(false);
        }

        comboWarehouse.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboWarehouse.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboWarehouseItemStateChanged(evt);
            }
        });
        comboWarehouse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboWarehouseActionPerformed(evt);
            }
        });

        jLabel1.setText("Seleccione almacén destino:");

        jLabel2.setText("Ubicaciones Disponibles:");

        txtAvUbication.setEditable(false);

        txtPendingInterns.setEditable(false);

        jLabel3.setText("Cantidad Pendiente por internar:");

        checkboxSelectAll.setText("Seleccionar automático");
        checkboxSelectAll.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkboxSelectAllItemStateChanged(evt);
            }
        });

        btnChooseFile.setText("...");
        btnChooseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseFileActionPerformed(evt);
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
                                    .addComponent(btnChooseFile, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnLoadFile, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblFileChooser1))
                        .addGap(81, 81, 81)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAvUbication, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPendingInterns))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(checkboxSelectAll)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnIntern, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(comboWarehouse, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                    .addComponent(btnLoadFile)
                    .addComponent(comboWarehouse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnChooseFile))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFileChooser1)
                    .addComponent(jLabel2)
                    .addComponent(txtAvUbication, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtPendingInterns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkboxSelectAll)
                    .addComponent(btnIntern))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    public static String crearEAN128(Producto prod, Date fechaV) {
        String ean = "";
        //ean += pallet.getId();
        ean += "(02)0" + prod.getEan13();
        ean += "37" + prod.getCantidadProductosEnPallet();
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        ean += "(17)" + dateFormat.format(fechaV);
        return ean;
    }

    public void fillTable() {
        clearGrid(tableInternOrders);
        int x = 0;
        DefaultTableModel model = (DefaultTableModel) tableInternOrders.getModel();
        orders.clear();
        orders.addAll(internmentApplication.queryByType(EntityState.InternmentOrders.REGISTRADA.ordinal()));
        orders.addAll(internmentApplication.queryByType(EntityState.InternmentOrders.PENDIENTE.ordinal()));
        for (OrdenInternamiento or : orders) {
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

    public void fillComboWarehouse(int type) {
        comboWarehouse.removeAllItems();
        almacenes = warehouseApplication.queryWarehousesByType(type);
        if (almacenes.size() > 0) {
            String[] nombresAlmacen = new String[almacenes.size()];
            for (int i = 0; i < almacenes.size(); i++) {
                nombresAlmacen[i] = almacenes.get(i).getDescripcion();
            }
            comboWarehouse.setModel(new javax.swing.DefaultComboBoxModel(nombresAlmacen));
        }
    }

    public void clearGrid(JTable tabla) {
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0);
    }

    private void loadFromFile(String csvFile) {
        ordenListada.clear();
        //JFileChooser fc = new JFileChooser();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            jTextField1.setText(file.getAbsolutePath());
            br = new BufferedReader(new FileReader(csvFile));

            while ((line = br.readLine()) != null) {
                String[] lectura = line.split(cvsSplitBy);
                if (lectura.length == 3) {
                    Buffer buff = new Buffer();
                    buff.id_item = Integer.parseInt(lectura[0]);
                    buff.fecha = lectura[1];
                    buff.cantidad = Integer.parseInt(lectura[2]);
                    ordenListada.add(buff);
                } else {
                    break;
                }
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


    private void btnLoadFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadFileActionPerformed
        loadFromFile(file.getAbsolutePath());
        file = null;
        btnLoadFile.setEnabled(false);
        jTextField1.setText("");

    }//GEN-LAST:event_btnLoadFileActionPerformed

    public void createInternmentOrders(ArrayList<Buffer> listaBuff) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for (Buffer b : listaBuff) {
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
            ordenXProducto.setOrdenInternamiento(orden);
            Producto prod = productApplication.queryById(b.id_item);
            ordenXProducto.setProducto(prod);
            ordenXProducto.setCantidad(b.cantidad);
            ordenXProducto.setCantidadIngresada(0);
            ordenXProducto.setId(id);
            internmentApplication.insertOrdenXProducto(ordenXProducto);

            //stocks en producto
            //Producto prod = internmentApplication.getProdOrder(orden).getProducto();
            prod.setPalletsRegistrados(prod.getPalletsRegistrados() + b.cantidad);
            prod.setStockLogico(prod.getStockLogico() + b.cantidad);
            productApplication.update(prod);
            String ean = "";
            Date fechaV = null;
            try {
                //Pallets
                fechaV = formatter.parse(b.fecha);
            } catch (ParseException ex) {
                Logger.getLogger(InternmentSelectView.class.getName()).log(Level.SEVERE, null, ex);
            }
            ean = crearEAN128(prod, fechaV);

            ArrayList<Pallet> pallets = new ArrayList();

            for (int i = 0; i < b.cantidad; i++) {
                Pallet pallet = new Pallet();
                pallet.setEstado(EntityState.Pallets.CREADO.ordinal());//CREADO
                pallet.setFechaRegistro(orden.getFecha());
                pallet.setFechaVencimiento(fechaV);
                pallet.setOrdenInternamiento(orden);
                pallet.setProducto(prod);
                pallet.setEan128(ean);
                pallets.add(pallet);
            }

            palletApplication.insertNPallet(pallets);
            /*for (int i = 0; i < b.cantidad; i++) {
             Pallet pallet = new Pallet();
             pallet.setEstado(EntityState.Pallets.CREADO.ordinal());//CREADO
             pallet.setFechaRegistro(orden.getFecha());
             try {
             pallet.setFechaVencimiento(formatter.parse(b.fecha));
             } catch (ParseException ex) {
             Logger.getLogger(InternmentSelectView.class.getName()).log(Level.SEVERE, null, ex);
             }
             pallet.setOrdenInternamiento(orden);
             pallet.setProducto(prod);
             String ean=crearEAN128(pallet);
             //pallet.setEan128();
             int eanAux = palletApplication.insert(pallet);
                
             /*Pallet palletAux = palletApplication.queryById(eanAux);
             String ean = palletAux.getEan128();*/
            /*ean += eanAux;
             pallet.setEan128(ean);
             palletApplication.update(pallet);*/

            //}
        }

    }


    private void btnInternActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInternActionPerformed
        if (Integer.parseInt(txtPendingInterns.getText()) >= 0) {
            Cursor cur1 = new Cursor(Cursor.WAIT_CURSOR);
            setCursor(cur1);
            internarPallets();
            Cursor cur2 = new Cursor(Cursor.DEFAULT_CURSOR);
            setCursor(cur2);
            
        } else {
            JOptionPane.setDefaultLocale(new Locale("es", "ES"));
            JOptionPane.showMessageDialog(this, "Seleccione una cantidad de ubicaciones menor o igual a la cantidad de pallets", "Error al Internar pallets", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_btnInternActionPerformed

    private void tableInternOrdersMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableInternOrdersMousePressed
        // TODO add your handling code here:
        btnIntern.setEnabled(true);
        //jButton3.setEnabled(true);

        int row = tableInternOrders.getSelectedRow();
        int cod = Integer.parseInt(tableInternOrders.getModel().getValueAt(row, 0).toString());
        OrdenInternamiento orden = internmentApplication.queryById(cod);
        Producto prod = internmentApplication.getProdOrder(orden).getProducto();
        cantAInternar = internmentApplication.getProdOrder(orden).getCantidad() - internmentApplication.getProdOrder(orden).getCantidadIngresada();
        txtPendingInterns.setText(Integer.toString(cantAInternar));
        ordenAInternar = orden;
        fillComboWarehouse(prod.getCondicion().getId());
        fillFreeSpots();
        checkboxSelectAll.setSelected(false);

    }//GEN-LAST:event_tableInternOrdersMousePressed


    private void comboWarehouseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboWarehouseActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_comboWarehouseActionPerformed

    private void internarPallets() {
        JTable table = tableFreeSpots;
        int cant = 0;
        int aux = 0;
        Boolean isChecked = null;
        if (ordenAInternar != null) {
            ArrayList<Pallet> pallets = palletApplication.getPalletsFromOrder(ordenAInternar.getId());
            ArrayList<Pallet> palletsInter = new ArrayList<Pallet>();
            ArrayList<Pallet> palletsInterAFuncion = new ArrayList<Pallet>();
            for (int i = 0; i < pallets.size(); i++) {
                if (pallets.get(i).getUbicacion() == null) {
                    palletsInter.add(pallets.get(i));
                }
            }
            
            if (table.getRowCount() > 0) {
                OrdenInternamientoXProducto op=internmentApplication.getProdOrder(ordenAInternar);
                aux = op.getCantidadIngresada();
                for (int i = 0; i < table.getRowCount(); i++) {                    
                    if (aux==op.getCantidad()) {
                        break;
                    }

                    isChecked = (Boolean) table.getValueAt(i, 4);
                    if (isChecked != null && isChecked) {
                        Pallet pall = palletsInter.get(cant);
                        pall.setEstado(EntityState.Pallets.UBICADO.ordinal());
                        pall.setUbicacion(ubicaciones.get(i));
                        palletsInterAFuncion.add(pall);
                        cant++;
                        aux++;
                    }
                   
                }
                
                //ingresar entrada en kardex
                ArrayList<Kardex> kardex = kardexApplication.queryByParameters(almacenes.get(comboWarehouse.getSelectedIndex()).getId(), op.getProducto().getId());
                Kardex internmentKardex = new Kardex();
                internmentKardex.setAlmacen(almacenes.get(comboWarehouse.getSelectedIndex()));
                internmentKardex.setProducto(op.getProducto());
                internmentKardex.setTipoMovimiento("Ingreso");
                internmentKardex.setCantidad(palletsInterAFuncion.size());

                internmentKardex.setFecha(Calendar.getInstance().getTime());
                if (kardex.size() == 0) {
                    internmentKardex.setStockInicial(0);
                } else {
                    internmentKardex.setStockInicial(kardex.get(0).getStockFinal());
                }
                internmentKardex.setStockFinal(internmentKardex.getStockInicial() + palletsInterAFuncion.size());

                KardexId kId = new KardexId();
                kId.setIdAlmacen(almacenes.get(comboWarehouse.getSelectedIndex()).getId());
                kId.setIdProducto(op.getProducto().getId());

                internmentKardex.setId(kId);

                //kardexApplication.insert(internmentKardex);
                //kardexApplication.insertKardexID(kId);
                if (palletsInterAFuncion.size()>0){
                    int intern = palletApplication.internNPallets(palletsInterAFuncion, op, internmentKardex);
                    if (intern == 1)
                        JOptionPane.showMessageDialog(this, "Pallets internados correctamente","Mensaje de internado de pallet",JOptionPane.INFORMATION_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(this, "Por favor seleccione al menos 1 ubicación","Mensaje de internado de pallet",JOptionPane.WARNING_MESSAGE);
                    
            }
            fillTable();
            clearGrid(tableFreeSpots);
            btnIntern.setEnabled(false);
            //jButton3.setEnabled(false);    
            comboWarehouse.removeAllItems();
            txtAvUbication.setText("");
            txtPendingInterns.setText("");
        }
    }


    private void fillFreeSpots() {

        clearGrid(tableFreeSpots);
        if (comboWarehouse.getItemCount() > 0) {
            ubicaciones.clear();
            DefaultTableModel model = (DefaultTableModel) tableFreeSpots.getModel();
            ArrayList<Ubicacion> ubi = new ArrayList<Ubicacion>();
            Almacen alm = almacenes.get(comboWarehouse.getSelectedIndex());
            ubi = spotApplication.querySpotsByWarehouse2(alm.getId());
            txtAvUbication.setText(String.valueOf(ubi.size()));
            ubicaciones.addAll(ubi);
                for (Ubicacion ub : ubi) {
                    model.addRow(new Object[]{
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
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            fillFreeSpots();
        }

    }//GEN-LAST:event_comboWarehouseItemStateChanged

    private void checkboxSelectAllItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkboxSelectAllItemStateChanged
        DefaultTableModel table = (DefaultTableModel) tableFreeSpots.getModel();
        int cant = 0;
        if (checkboxSelectAll.isSelected()) {
            fillFreeSpots();
            for (int i = 0; i < table.getRowCount(); i++) {
                table.setValueAt(true, i, 4);
                cant++;
                if (cant == cantAInternar) {
                    break;
                }
            }
        } else {
            fillFreeSpots();
        }
    }//GEN-LAST:event_checkboxSelectAllItemStateChanged

    private void btnChooseFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseFileActionPerformed
        fc.setDialogTitle("Seleccione un archivo");
        fc.showOpenDialog(this);
        file = fc.getSelectedFile();
        if (!fc.getSelectedFile().getName().endsWith(".csv")) {
            JOptionPane.setDefaultLocale(new Locale("es", "ES"));
            JOptionPane.showMessageDialog(this, Strings.ERROR_NOT_CSV, Strings.ERROR_FILE_UPLOAD_TITLE, JOptionPane.WARNING_MESSAGE);
            jTextField1.setText("");
            btnLoadFile.setEnabled(false);
        } else {
            jTextField1.setText(file.getAbsolutePath());
            btnLoadFile.setEnabled(true);
        }
    }//GEN-LAST:event_btnChooseFileActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChooseFile;
    private javax.swing.JButton btnIntern;
    private javax.swing.JButton btnLoadFile;
    private javax.swing.JCheckBox checkboxSelectAll;
    private javax.swing.JComboBox comboWarehouse;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblFileChooser;
    private javax.swing.JLabel lblFileChooser1;
    private javax.swing.JTable tableFreeSpots;
    private javax.swing.JTable tableInternOrders;
    private javax.swing.JTextField txtAvUbication;
    private javax.swing.JTextField txtPendingInterns;
    // End of variables declaration//GEN-END:variables
}
