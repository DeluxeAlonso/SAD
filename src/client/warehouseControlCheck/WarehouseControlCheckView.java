/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.warehouseControlCheck;

import application.kardex.KardexApplication;
import application.pallet.PalletApplication;
import application.product.ProductApplication;
import application.spot.SpotApplication;
import application.warehouse.WarehouseApplication;
import client.base.BaseView;
import static client.internment.InternmentSelectView.crearEAN128;
import entity.Almacen;
import entity.Kardex;
import entity.KardexId;
import entity.Pallet;
import entity.Producto;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import util.EntityState;
import util.InstanceFactory;
import util.Strings;

/**
 *
 * @author robert
 */
public class WarehouseControlCheckView extends BaseView {
    WarehouseApplication warehouseApplication = InstanceFactory.Instance.getInstance("warehouseApplicaiton", WarehouseApplication.class);
    SpotApplication spotApplication = InstanceFactory.Instance.getInstance("spotApplicaiton", SpotApplication.class);
    PalletApplication palletApplication = InstanceFactory.Instance.getInstance("palletApplicaiton", PalletApplication.class);
    KardexApplication kardexApplication = InstanceFactory.Instance.getInstance("kardexApplicaiton", KardexApplication.class);
    ProductApplication productApplication = InstanceFactory.Instance.getInstance("productApplicaiton", ProductApplication.class);
    public static WarehouseControlCheckView warehouseControlCheckView;
    public ArrayList<Almacen> warehousesFrom;
    public ArrayList<Ubicacion> spots;
    File file = null;
    HashMap<Integer,Integer> kardexCountIn = new HashMap<Integer,Integer>();
    HashMap<Integer,Integer> kardexCountOut = new HashMap<Integer,Integer>();
    
    /**
     * Creates new form WarehouseControlCheckView
     * @param mainPanel
     */
    public WarehouseControlCheckView(JDesktopPane mainPanel) {
        initComponents();
        super.initialize();
        warehouseControlCheckView = this;
        //Initialize comboWarehouseFrom
        warehousesFrom = warehouseApplication.queryAll();
        if(warehousesFrom.size()>0){
            String[] warehousesName = new String[warehousesFrom.size()];
            for(int i=0; i<warehousesFrom.size(); i++){
                warehousesName[i] = warehousesFrom.get(i).getDescripcion();
            }
            comboWarehouseFrom.setModel(new javax.swing.DefaultComboBoxModel(warehousesName));
            fillSpotsTable(warehousesFrom.get(0).getId());
            btnFile.setEnabled(true);
        }
    }
    
    public void clearSpotsTable(){
        DefaultTableModel model = (DefaultTableModel) tblControlCheck.getModel();
        model.setRowCount(0);
    }
    
    public void fillSpotsTable(int warehouseId){
        clearSpotsTable();
        ArrayList<Pallet> p;
        DefaultTableModel model = (DefaultTableModel) tblControlCheck.getModel();
        spots = (ArrayList<Ubicacion>) spotApplication.querySpotsByWarehouse(warehouseId);
        if(spots.size()>0){
            for(Ubicacion spot : spots){
                p = palletApplication.queryPalletsBySpot(spot.getId());
                System.out.println("Pallets en la ubicacion "+spot.getId()+": "+p.size());
                if(p.size()>0){
                    model.addRow(new Object[]{
                        spot.getRack().getId(),
                        spot.getLado(),
                        spot.getFila(),
                        spot.getColumna(),
                        EntityState.getSpotStateLiteral(spot.getEstado()),
                        p.get(0).getProducto().getNombre()
                    });
                }else{
                    model.addRow(new Object[]{
                        spot.getRack().getId(),
                        spot.getLado(),
                        spot.getFila(),
                        spot.getColumna(),
                        EntityState.getSpotStateLiteral(spot.getEstado()),
                        "-"
                    });
                }
            }
        }
    }
    
    private void loadFromFile(String csvFile) throws ParseException{
        BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
        Producto prod;
        try {
            br = new BufferedReader(new FileReader(csvFile));
            // Leo la cantidad de clientes que hay en el archivo
            line = br.readLine();
            String[] line_split = line.split(cvsSplitBy);
            int spotsInFile = Integer.parseInt(line_split[0]);
            for(int i=0;i<spotsInFile;i++){
                // Leo una ubicacion
                line = br.readLine();
                line_split = line.split(cvsSplitBy);
                // 
                DefaultTableModel model = (DefaultTableModel) tblControlCheck.getModel();
                if(line_split[5].equals("-")){
                    // En fisico esta libre
                    model.setValueAt(line_split[4], i, 6);
                    model.setValueAt("-", i, 7);
                    if(model.getValueAt(i, 4).toString().equals("Ocupado")){
                        // Si en fisico esta libre y en el sistema ocupado, tengo que eliminar el pallet
                        model.setValueAt("Remover pallet del sistema", i, 8);
                        // Agrupo los pallets que se moveran por Producto para ser ingresados al kardex
                        ArrayList<Pallet> pallets = palletApplication.queryPalletsBySpot(spots.get(i).getId());
                        if(!kardexCountOut.isEmpty() && kardexCountOut.containsKey(pallets.get(0).getProducto().getId())){
                            System.out.println("kardex de salida contiene: "+pallets.get(0).getProducto());
                            kardexCountOut.put(pallets.get(0).getProducto().getId(), kardexCountOut.get(pallets.get(0).getProducto().getId())+1);
                        }else{
                            System.out.println("kardex de salida NO contiene: "+pallets.get(0).getProducto());
                            kardexCountOut.put(pallets.get(0).getProducto().getId(), 1);
                        }
                        // Eliminar el pallet de la ubicacion
                        Boolean bol = palletApplication.deletePalletBySpot(spots.get(i).getId());
                        System.out.println("Elimino el pallet de la ubicacion "+spots.get(i).getId()+": "+bol.booleanValue());
                        // Actualizo el estado de la ubicacion a libre
                        spotApplication.updateSpotOccupancy(spots.get(i).getId(), EntityState.Spots.LIBRE.ordinal());
                    }else{
                        // Si en fisico esta libre y en el sistema libre, todo OK
                        model.setValueAt("-", i, 8);
                    }
                }else{
                    // En fisico esta ocupado
                    prod = productApplication.queryById(Integer.parseInt(line_split[5]));
                    model.setValueAt(line_split[4], i, 6);
                    model.setValueAt(prod.getNombre(), i, 7);
                    if(model.getValueAt(i, 4).toString().equals("Libre")){
                        // Si en fisico esta ocupado y en el sistema libre, tengo que internar el pallet
                        model.setValueAt("Internar Pallet", i, 8);
                        // Agrupo los pallets que se moveran por Producto para ser ingresados al kardex
                        if(!kardexCountIn.isEmpty() && kardexCountIn.containsKey(prod.getId())){
                            System.out.println("kardex de salida contiene: "+prod);
                            kardexCountIn.put(prod.getId(), kardexCountIn.get(prod.getId())+1);
                        }else{
                            System.out.println("kardex de salida NO contiene: "+prod);
                            kardexCountIn.put(prod.getId(), 1);
                        }
                        // Inserto el pallet
                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        Date fechaVenc = formatter.parse(line_split[6]);
                        Pallet pallet = new Pallet();
                        pallet.setEstado(EntityState.Pallets.UBICADO.ordinal());
                        pallet.setFechaRegistro(new Date());
                        pallet.setFechaVencimiento(fechaVenc);
                        pallet.setProducto(prod);
                        pallet.setUbicacion(spots.get(i));
                        pallet.setEan128(crearEAN128(pallet.getProducto(),pallet.getFechaVencimiento()));
                        int eanAux=palletApplication.insert(pallet);

                        Pallet palletAux = palletApplication.queryById(eanAux);
                        String ean = palletAux.getEan128();
                        ean+=eanAux;
                        palletAux.setEan128(ean);
                        palletApplication.update(palletAux);
                        System.out.println("Inserto el pallet de la ubicacion "+spots.get(i).getId()+": "+eanAux);
                        // Actualizo el estado de la ubicacion a ocupado
                        spotApplication.updateSpotOccupancy(spots.get(i).getId(), EntityState.Spots.OCUPADO.ordinal());
                    
                    }else{
                        // Si en fisico esta ocupado y en el sistema ocupado, tengo que verificar si es el mismo producto
                        if(prod.getNombre().equals(model.getValueAt(i, 5).toString())){
                            // Si en fisico y en el sistema tienen el mismo producto
                            model.setValueAt("-", i, 8);
                        }else{
                            model.setValueAt("Actualizar pallet", i, 8);
                            // Si en fisico y en el sistema tienen productos diferentes
                            /* FALTA */
                            // Agrupo los pallets que se moveran por Producto para ser ingresados al kardex
                            ArrayList<Pallet> pallets = palletApplication.queryPalletsBySpot(spots.get(i).getId());
                            if(!kardexCountOut.isEmpty() && kardexCountOut.containsKey(pallets.get(0).getProducto().getId())){
                                System.out.println("kardex de salida contiene: "+pallets.get(0).getProducto());
                                kardexCountOut.put(pallets.get(0).getProducto().getId(), kardexCountOut.get(pallets.get(0).getProducto().getId()) +1);
                            }else{
                                System.out.println("kardex de salida NO contiene: "+pallets.get(0).getProducto());
                                kardexCountOut.put(pallets.get(0).getProducto().getId(), 1);
                            }
                            // Eliminar el pallet de la ubicacion
                            palletApplication.deletePalletBySpot(spots.get(i).getId());
                            // Agrupo los pallets que se moveran por Producto para ser ingresados al kardex
                            if(!kardexCountIn.isEmpty() && kardexCountIn.containsKey(prod.getId())){
                                //int productCount = kardexCountIn.get(prod).intValue();
                                //productCount++;
                                System.out.println("kardex de salida contiene: "+prod);
                                kardexCountIn.put(prod.getId(), kardexCountIn.get(prod.getId())+1);
                            }else{
                                System.out.println("kardex de salida NO contiene: "+prod);
                                kardexCountIn.put(prod.getId(), 1);
                            }
                            // Inserto el pallet
                            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date fechaVenc = formatter.parse(line_split[6]);
                            Pallet pallet = new Pallet();
                            pallet.setEstado(EntityState.Pallets.UBICADO.ordinal());
                            pallet.setFechaRegistro(new Date());
                            pallet.setFechaVencimiento(fechaVenc);
                            pallet.setProducto(prod);
                            pallet.setUbicacion(spots.get(i));
                            pallet.setEan128(crearEAN128(pallet.getProducto(),pallet.getFechaVencimiento()));
                            int eanAux=palletApplication.insert(pallet);

                            Pallet palletAux = palletApplication.queryById(eanAux);
                            String ean = palletAux.getEan128();
                            ean+=eanAux;
                            palletAux.setEan128(ean);
                            palletApplication.update(palletAux);
                        }
                    }
                }
            }
            // Ingreso los registros de kardex
            ArrayList<Kardex> previousKardex;
            Integer key;
            Kardex kardex;
            KardexId kardexId;
            Date date = new Date();
            // Para el kardex de salida
            Iterator<Integer> keySetIterator = kardexCountOut.keySet().iterator();
            System.out.println("Kardex de Salida:");
            while(keySetIterator.hasNext()){
                key = keySetIterator.next();
                Producto prodIn = productApplication.queryById(key);
                System.out.println("Producto: "+prodIn.getNombre()+", Cantidad: "+kardexCountOut.get(key));
                previousKardex = kardexApplication.queryByParameters(warehousesFrom.get(comboWarehouseFrom.getSelectedIndex()).getId(), prodIn.getId());
                kardexId = new KardexId();
                kardexId.setIdAlmacen(warehousesFrom.get(comboWarehouseFrom.getSelectedIndex()).getId());
                kardexId.setIdProducto(prodIn.getId());
                kardex = new Kardex();
                kardex.setId(kardexId);
                kardex.setAlmacen(warehousesFrom.get(comboWarehouseFrom.getSelectedIndex()));
                kardex.setProducto(prodIn);
                kardex.setFecha(date);
                kardex.setCantidad(kardexCountOut.get(key).intValue());
                kardex.setTipoMovimiento(Strings.MESSAGE_KARDEX_OUT_ADJUST);
                if(previousKardex.size()==0){
                    kardex.setStockInicial(0);
                }else{
                    kardex.setStockInicial(previousKardex.get(0).getStockFinal());
                }
                kardex.setStockFinal(kardex.getStockInicial() - kardex.getCantidad());
                kardexApplication.insert(kardex);
            }
            // Para el kardex de ingreso
            keySetIterator = kardexCountIn.keySet().iterator();
            System.out.println("Kardex de Ingreso:");
            while(keySetIterator.hasNext()){
                key = keySetIterator.next();
                Producto prodOut = productApplication.queryById(key);
                System.out.println("Producto: "+prodOut.getNombre()+", Cantidad: "+kardexCountIn.get(key));
                previousKardex = kardexApplication.queryByParameters(warehousesFrom.get(comboWarehouseFrom.getSelectedIndex()).getId(), prodOut.getId());
                kardexId = new KardexId();
                kardexId.setIdAlmacen(warehousesFrom.get(comboWarehouseFrom.getSelectedIndex()).getId());
                kardexId.setIdProducto(prodOut.getId());
                kardex = new Kardex();
                kardex.setId(kardexId);
                kardex.setAlmacen(warehousesFrom.get(comboWarehouseFrom.getSelectedIndex()));
                kardex.setProducto(prodOut);
                kardex.setFecha(date);
                kardex.setCantidad(kardexCountIn.get(key).intValue());
                kardex.setTipoMovimiento(Strings.MESSAGE_KARDEX_IN_ADJUST);
                if(previousKardex.size()==0){
                    kardex.setStockInicial(0);
                }else{
                    kardex.setStockInicial(previousKardex.get(0).getStockFinal());
                }
                kardex.setStockFinal(kardex.getStockInicial() + kardex.getCantidad());
                kardexApplication.insert(kardex);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblControlCheck = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        comboWarehouseFrom = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        fileTextField = new javax.swing.JTextField();
        btnFile = new javax.swing.JButton();
        btnFileUpload = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Ajuste de inventario");

        tblControlCheck.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Rack", "Lado", "Fila", "Columna", "En sistema", "Producto", "En físico", "Producto", "Tipo de ajuste"
            }
        ));
        jScrollPane1.setViewportView(tblControlCheck);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Informacion de ajuste"));

        comboWarehouseFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Almacén 1", "Almacén 2", "Almacén 3", "Almacén 4" }));
        comboWarehouseFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboWarehouseFromItemStateChanged(evt);
            }
        });

        jLabel5.setText("Almacén:");

        lblDate.setText("18/05/2015");

        jLabel2.setText("Fecha:");

        lblName.setText("Juan Carlos Pérez Salas");

        jLabel1.setText("Responsable:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 35, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDate)
                    .addComponent(lblName)
                    .addComponent(comboWarehouseFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblName))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblDate))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(comboWarehouseFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Toma de inventario"));

        jLabel3.setText("Cargar toma de inventario:");

        fileTextField.setEditable(false);

        btnFile.setText("...");
        btnFile.setEnabled(false);
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

        jLabel4.setText("* Debe escoger primero un almacén para contrastar con la toma de inventario");

        jLabel6.setText("** Al cargar la toma de inventario, se realiza el ajuste automaticamente");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(fileTextField)
                        .addGap(18, 18, 18)
                        .addComponent(btnFile, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFileUpload)
                        .addGap(41, 41, 41))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4))
                        .addGap(0, 48, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFile)
                    .addComponent(btnFileUpload))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 917, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFileUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFileUploadActionPerformed
        try {
            startLoader();
            loadFromFile(file.getAbsolutePath());
            file = null;
            btnFileUpload.setEnabled(false);
            fileTextField.setText("");
        } catch (ParseException ex) {
            Logger.getLogger(WarehouseControlCheckView.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            stopLoader();
        }
    }//GEN-LAST:event_btnFileUploadActionPerformed

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

    private void comboWarehouseFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboWarehouseFromItemStateChanged
        try {
            startLoader();    
            if(evt.getStateChange() == ItemEvent.SELECTED){
                fillSpotsTable(warehousesFrom.get(comboWarehouseFrom.getSelectedIndex()).getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            stopLoader();
        }
    }//GEN-LAST:event_comboWarehouseFromItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFile;
    private javax.swing.JButton btnFileUpload;
    private javax.swing.JComboBox comboWarehouseFrom;
    private javax.swing.JTextField fileTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblName;
    private javax.swing.JTable tblControlCheck;
    // End of variables declaration//GEN-END:variables
}
