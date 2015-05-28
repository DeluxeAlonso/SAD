/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.reports;

import application.kardex.KardexApplication;
import application.pallet.PalletApplication;
import application.product.ProductApplication;
import application.rack.RackApplication;
import application.spot.SpotApplication;
import application.warehouse.WarehouseApplication;
import entity.Almacen;
import entity.Kardex;
import entity.Producto;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import util.InstanceFactory;
import util.Strings;

/**
 *
 * @author prote_000
 */
public class KardexReport extends javax.swing.JInternalFrame {
    WarehouseApplication warehouseApplication = InstanceFactory.Instance.getInstance("warehouseApplicaiton", WarehouseApplication.class);
    RackApplication rackApplication = InstanceFactory.Instance.getInstance("rackApplicaiton", RackApplication.class);
    SpotApplication spotApplication = InstanceFactory.Instance.getInstance("spotApplicaiton", SpotApplication.class);
    PalletApplication palletApplication = InstanceFactory.Instance.getInstance("palletApplicaiton", PalletApplication.class);
    ProductApplication productApplication = InstanceFactory.Instance.getInstance("productApplicaiton", ProductApplication.class);
    KardexApplication kardexApplication = InstanceFactory.Instance.getInstance("kardexApplicaiton", KardexApplication.class);
    public ArrayList<Almacen> warehouses;
    public ArrayList<Producto> products;
    public ArrayList<Kardex> kardex;
    JFileChooser fc = new JFileChooser();
    File file = null;
    /**
     * Creates new form KardexReport
     */
    public KardexReport() {
        initComponents();
        fillWarehouses();
        if(warehouses.size()>0)
            fillProducts(warehouses.get(0).getCondicion().getId());
    }
    
    public void fillWarehouses(){
        warehouses = warehouseApplication.queryAll();
        if(warehouses.size()>0){
            String[] warehousesName = new String[warehouses.size()];
            for(int i=0; i<warehouses.size(); i++)
                warehousesName[i] = warehouses.get(i).getDescripcion();
            comboWarehouseFrom.setModel(new javax.swing.DefaultComboBoxModel(warehousesName));
        }
    }
    
    public void fillProducts(int idType){
        comboProductFrom.removeAllItems();
        products = productApplication.queryByType(idType);
        if(products.size()>0){
            String[] productName = new String[products.size()];
            for(int i=0; i<products.size(); i++)
                productName[i] = products.get(i).getNombre();
            comboProductFrom.setModel(new javax.swing.DefaultComboBoxModel(productName));
        }
    }
    
    public void clearKardex(){
        DefaultTableModel model = (DefaultTableModel) tblKardex.getModel();
        model.setRowCount(0);
    }
    
    public void fillKardex(){
        clearKardex();
        DefaultTableModel model = (DefaultTableModel) tblKardex.getModel();
        double precio_unit = 13.19;
        if(kardex.size()>0){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
            Object[] row;
            // Inventario inicial
            row = new Object[]{
                        sdf.format(kardex.get(0).getFecha()),
                        "Inventario Inicial",
                        "-",
                        "-",
                        "-",
                        "-",
                        "-",
                        "-",
                        kardex.get(0).getStockInicial(),
                        precio_unit,
                        kardex.get(0).getStockInicial()*precio_unit,
                    };
            model.addRow(row);
            for(Kardex kardexItem : kardex){
                if(kardexItem.getTipoMovimiento().equals("Ingreso")){
                    row = new Object[]{
                        sdf.format(kardexItem.getFecha()),
                        kardexItem.getTipoMovimiento(),
                        kardexItem.getCantidad(),
                        precio_unit,
                        kardexItem.getCantidad()*precio_unit,
                        "-",
                        "-",
                        "-",
                        kardexItem.getStockFinal(),
                        precio_unit,
                        kardexItem.getStockFinal()*precio_unit,
                    };
                }
                else{
                    row = new Object[]{
                        sdf.format(kardexItem.getFecha()),
                        kardexItem.getTipoMovimiento(),
                        "-",
                        "-",
                        "-",
                        kardexItem.getCantidad(),
                        precio_unit,
                        kardexItem.getCantidad()*precio_unit,
                        kardexItem.getStockFinal(),
                        precio_unit,
                        kardexItem.getStockFinal()*precio_unit,
                    };
                }
                model.addRow(row);
            }
            // Inventario final
            row = new Object[]{
                        sdf.format(kardex.get(kardex.size()-1).getFecha()),
                        "Inventario Final",
                        "-",
                        "-",
                        "-",
                        "-",
                        "-",
                        "-",
                        kardex.get(kardex.size()-1).getStockFinal(),
                        precio_unit,
                        kardex.get(kardex.size()-1).getStockFinal()*precio_unit,
                    };
            model.addRow(row);
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

        jCalendar1 = new com.toedter.calendar.JCalendar();
        jLabel1 = new javax.swing.JLabel();
        comboWarehouseFrom = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnReport = new javax.swing.JButton();
        dtcInitDate = new com.toedter.calendar.JDateChooser();
        dtcEndDate = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        comboProductFrom = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKardex = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Kardex");

        jLabel1.setText("Almacén:");

        comboWarehouseFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboWarehouseFromItemStateChanged(evt);
            }
        });

        jLabel2.setText("Fecha inicial:");

        jLabel3.setText("Fecha final:");

        btnReport.setText("Generar Reporte");
        btnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportActionPerformed(evt);
            }
        });

        jLabel4.setText("Producto:");

        tblKardex.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Fecha", "Detalle", "Unidades", "V. Unit.", "V. Total", "Unidades", "V.Unit.", "V. Total", "Unidades", "V. Unit", "V. Total"
            }
        ));
        jScrollPane1.setViewportView(tblKardex);

        jLabel5.setText("Ingreso");

        jLabel6.setText("Salida");

        jLabel7.setText("Existencias");

        jButton1.setText("Exportar XLS");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnReport)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dtcInitDate, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                                    .addComponent(comboWarehouseFrom, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dtcEndDate, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                                    .addComponent(comboProductFrom, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(293, 293, 293)
                        .addComponent(jLabel5)
                        .addGap(238, 238, 238)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(116, 116, 116))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(comboWarehouseFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(comboProductFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(dtcInitDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(dtcEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed
        JOptionPane.setDefaultLocale(new Locale("es", "ES"));
        boolean hasErrors=false;
        String error_message = "Errores:\n";
        if(warehouses.size()>0 && products.size()>0){
            if(dtcInitDate.getDate()==null){
                hasErrors=true;
                error_message += Strings.ERROR_DATE_INI+"\n";
            }
            if(dtcEndDate.getDate()==null){
                hasErrors=true;
                error_message += Strings.ERROR_DATE_END+"\n";
            }
            if(dtcInitDate.getDate()!=null && dtcEndDate.getDate()!=null){
                if(dtcInitDate.getDate().getTime() > dtcEndDate.getDate().getTime()){
                    hasErrors=true;
                    error_message += Strings.ERROR_DATE+"\n";
                }
            }
            
            if(hasErrors){
                JOptionPane.showMessageDialog(this, error_message,Strings.ERROR_KARDEX_TITLE,JOptionPane.WARNING_MESSAGE);
            }else{
                kardex = new ArrayList<Kardex>();
                kardex = kardexApplication.queryByParameters(warehouses.get(comboWarehouseFrom.getSelectedIndex()).getId(), products.get(comboProductFrom.getSelectedIndex()).getId(), dtcInitDate.getDate(),dtcEndDate.getDate());
                System.out.println(kardex.size());
                fillKardex();
            }
        }
    }//GEN-LAST:event_btnReportActionPerformed

    private void comboWarehouseFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboWarehouseFromItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED)
            fillProducts(warehouses.get(comboWarehouseFrom.getSelectedIndex()).getCondicion().getId());
    }//GEN-LAST:event_comboWarehouseFromItemStateChanged

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
        JOptionPane.setDefaultLocale(new Locale("es", "ES"));
        fc.setDialogTitle("Seleccione un archivo");
        fc.showOpenDialog(this);
        file = fc.getSelectedFile();
        System.out.println(file);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        /* Export to XSL */
        try {
            FileWriter excel = new FileWriter(file);
            DefaultTableModel model = (DefaultTableModel) tblKardex.getModel();
            excel.write("\t\t\tKardex\n");
            excel.write("Emitido\t"+dateFormat.format(date)+"\n");
            excel.write("Almacen\t"+warehouses.get(comboWarehouseFrom.getSelectedIndex()).getDescripcion()+"\n");
            excel.write("Producto\t"+products.get(comboProductFrom.getSelectedIndex()).getNombre()+"\n");
            excel.write("Unidad\tPallets\n");
            excel.write("Fecha Inicial\t"+dateFormat.format(dtcInitDate.getDate())+"\tFecha Final\t"+dateFormat.format(dtcEndDate.getDate())+"\n\n");
            
            for(int i = 0; i < model.getColumnCount(); i++)
                excel.write(model.getColumnName(i) + "\t");
            excel.write("\n");
            for(int i=0; i< model.getRowCount(); i++) {
                for(int j=0; j < model.getColumnCount(); j++)
                    excel.write(model.getValueAt(i,j).toString()+"\t");
                excel.write("\n");
            }
            excel.close();
        } catch (IOException ex) {
            Logger.getLogger(KardexReport.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Ocurrió un error al abrir el archivo",Strings.ERROR_KARDEX_TITLE,JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1MousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReport;
    private javax.swing.JComboBox comboProductFrom;
    private javax.swing.JComboBox comboWarehouseFrom;
    private com.toedter.calendar.JDateChooser dtcEndDate;
    private com.toedter.calendar.JDateChooser dtcInitDate;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblKardex;
    // End of variables declaration//GEN-END:variables
}
