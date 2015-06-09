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
import client.base.BaseView;
import entity.Almacen;
import entity.Kardex;
import entity.Producto;
import entity.Ubicacion;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import util.EntityState;
import util.InstanceFactory;
import util.Strings;

/**
 *
 * @author prote_000
 */
public class KardexReport extends BaseView {
    WarehouseApplication warehouseApplication = InstanceFactory.Instance.getInstance("warehouseApplicaiton", WarehouseApplication.class);
    RackApplication rackApplication = InstanceFactory.Instance.getInstance("rackApplicaiton", RackApplication.class);
    SpotApplication spotApplication = InstanceFactory.Instance.getInstance("spotApplicaiton", SpotApplication.class);
    PalletApplication palletApplication = InstanceFactory.Instance.getInstance("palletApplicaiton", PalletApplication.class);
    ProductApplication productApplication = InstanceFactory.Instance.getInstance("productApplicaiton", ProductApplication.class);
    KardexApplication kardexApplication = InstanceFactory.Instance.getInstance("kardexApplicaiton", KardexApplication.class);
    public ArrayList<Almacen> warehouses;
    public ArrayList<Producto> products;
    public ArrayList<Kardex> kardex;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    JFileChooser fc = new JFileChooser();
    File file = null;
    /**
     * Creates new form KardexReport
     */
    public KardexReport() {
        initComponents();
        super.initialize();
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
        products = productApplication.queryByCondition(idType);
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
                        kardex.get(0).getStockInicial(),
                    };
            model.addRow(row);
            for(Kardex kardexItem : kardex){
                row = new Object[]{
                    sdf.format(kardexItem.getFecha()),
                    kardexItem.getTipoMovimiento(),
                    kardexItem.getCantidad(),
                    kardexItem.getStockFinal(),
                };
                model.addRow(row);
            }
            // Inventario final
            row = new Object[]{
                        sdf.format(kardex.get(kardex.size()-1).getFecha()),
                        "Inventario Final",
                        "-",
                        kardex.get(kardex.size()-1).getStockFinal(),
                    };
            model.addRow(row);
        }
    }
    
    public void createHeader(WritableSheet writableSheet, Date date){
        
        try{
            Label label0 = new Label(1, 1, "");
            Label label1 = new Label(4, 1, "Kardex");
            Label label2 = new Label(7, 1, "Fecha: "+ dateFormat.format(date));
            Label label3 = new Label(1, 2, "Almacen: "+ warehouses.get(comboWarehouseFrom.getSelectedIndex()).getDescripcion() );
            Label label4 = new Label(1, 3, "Condicion: "+warehouses.get(comboWarehouseFrom.getSelectedIndex()).getCondicion().getNombre());
            Label label5 = new Label(1, 4, "Producto: "+products.get(comboProductFrom.getSelectedIndex()).getNombre());
            Label label6 = new Label(1, 5, "Período: "+dateFormat.format(dtcInitDate.getDate())+" a "+dateFormat.format(dtcEndDate.getDate()));
            WritableFont tittleFont = new WritableFont(WritableFont.createFont("Calibri"),
             16,
             WritableFont.BOLD,  false,
             UnderlineStyle.NO_UNDERLINE);
             tittleFont.setColour(jxl.format.Colour.RED);
            
            WritableCellFormat tittleFormat = new WritableCellFormat(tittleFont);
             tittleFormat.setWrap(false);
             tittleFormat.setAlignment(jxl.format.Alignment.CENTRE);
             tittleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
             
            WritableFont headerRFont = new WritableFont(WritableFont.createFont("Calibri"),
             10,
             WritableFont.BOLD,  false,
             UnderlineStyle.NO_UNDERLINE);
             headerRFont.setColour(jxl.format.Colour.BLACK);
            
            WritableCellFormat headerRFormat = new WritableCellFormat(headerRFont);
             headerRFormat.setWrap(false);
             headerRFormat.setAlignment(jxl.format.Alignment.RIGHT);
             headerRFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
             
            WritableFont headerLFont = new WritableFont(WritableFont.createFont("Calibri"),
             11,
             WritableFont.BOLD,  false,
             UnderlineStyle.NO_UNDERLINE);
             headerLFont.setColour(jxl.format.Colour.BLACK);
            
            WritableCellFormat headerLFormat = new WritableCellFormat(headerLFont);
             headerLFormat.setWrap(false);
             headerLFormat.setAlignment(jxl.format.Alignment.LEFT);
             headerLFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            //Add the created Cells to the sheet
             
            WritableFont headerTFont = new WritableFont(WritableFont.createFont("Calibri"),
             WritableFont.DEFAULT_POINT_SIZE,
             WritableFont.BOLD,  false,
             UnderlineStyle.NO_UNDERLINE);
            headerTFont.setColour(jxl.format.Colour.WHITE);
            
            WritableCellFormat headerTFormat = new WritableCellFormat(headerTFont);
             headerTFormat.setWrap(true);
             headerTFormat.setAlignment(jxl.format.Alignment.CENTRE);
             headerTFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
             headerTFormat.setWrap(true);
             headerTFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,
             jxl.format.Colour.BLACK);
             headerTFormat.setBackground(Colour.GRAY_80);
             
            Label t1 = new Label(1, 7, "Fecha"); 
            Label t2 = new Label(2, 7, "Detalle"); 
            Label t3 = new Label(3, 7, "Stock inicial"); 
            Label t4 = new Label(4, 7, "Unidades"); 
            Label t5 = new Label(5, 7, "Stock final"); 
             
             t1.setCellFormat(headerTFormat);
             t2.setCellFormat(headerTFormat);
             t3.setCellFormat(headerTFormat);
             t4.setCellFormat(headerTFormat);
             t5.setCellFormat(headerTFormat);
             
             label0.setCellFormat(headerLFormat);
             label1.setCellFormat(tittleFormat);
             label2.setCellFormat(headerRFormat);
             label3.setCellFormat(headerLFormat);
             label4.setCellFormat(headerLFormat);
             label5.setCellFormat(headerLFormat);
             label6.setCellFormat(headerLFormat);
             
            writableSheet.addCell(label0);
            writableSheet.addCell(label1);
            writableSheet.addCell(label2);
            writableSheet.addCell(label3);
            writableSheet.addCell(label4);
            writableSheet.addCell(label5);
            writableSheet.addCell(label6);
            
            writableSheet.addCell(t1);
            writableSheet.addCell(t2);
            writableSheet.addCell(t3);
            writableSheet.addCell(t4);
            writableSheet.addCell(t5);
            
        }catch(Exception e){

        }
    }
    
    private void fillReport(WritableSheet writableSheet){
        try{
            //Definicion de formatos
            WritableFont rowFont = new WritableFont(WritableFont.createFont("Calibri"),
             11,
             WritableFont.NO_BOLD,  false,
             UnderlineStyle.NO_UNDERLINE);
             rowFont.setColour(jxl.format.Colour.BLACK);
            
            WritableCellFormat parFormat = new WritableCellFormat(rowFont);
             parFormat.setWrap(false);
             parFormat.setBackground(Colour.GREY_25_PERCENT);
             
             
             WritableCellFormat imparFormat = new WritableCellFormat(rowFont);
             parFormat.setWrap(false);
             int col=1;
             int fil=8;
             int i=0;
             Object[] row;
             for(Kardex k : kardex){
                Label l1 = new Label(1, fil+i, dateFormat.format(k.getFecha()));
                Label l2 = new Label(2, fil+i, k.getTipoMovimiento());
                jxl.write.Number l3 = new jxl.write.Number(3, fil+i, k.getStockInicial());
                jxl.write.Number l4 = new jxl.write.Number(4, fil+i, k.getCantidad());
                jxl.write.Number l5 = new jxl.write.Number(5, fil+i, k.getStockFinal());
                if (i%2==0){
                    l1.setCellFormat(imparFormat);
                    l2.setCellFormat(imparFormat);
                    l3.setCellFormat(imparFormat);
                    l4.setCellFormat(imparFormat);
                    l5.setCellFormat(imparFormat);
                }else{
                    l1.setCellFormat(parFormat);
                    l2.setCellFormat(parFormat);
                    l3.setCellFormat(parFormat);
                    l4.setCellFormat(parFormat);
                    l5.setCellFormat(parFormat);
                }
                writableSheet.addCell(l1);
                writableSheet.addCell(l2);
                writableSheet.addCell(l3);
                writableSheet.addCell(l4);
                writableSheet.addCell(l5);
                i++;
            }  
        }catch (Exception e){
            
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
        btnExport = new javax.swing.JButton();

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
        btnReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReportMouseClicked(evt);
            }
        });
        btnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportActionPerformed(evt);
            }
        });

        jLabel4.setText("Producto:");

        tblKardex.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Fecha", "Detalle", "Unidades", "Stock final"
            }
        ));
        jScrollPane1.setViewportView(tblKardex);

        btnExport.setText("Exportar XLS");
        btnExport.setEnabled(false);
        btnExport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnExportMousePressed(evt);
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dtcInitDate, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                            .addComponent(comboWarehouseFrom, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboProductFrom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtcEndDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnExport, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnReport, javax.swing.GroupLayout.Alignment.TRAILING))))
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
                            .addComponent(comboProductFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
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
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExport)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                //error_message += Strings.ERROR_DATE_INI+"\n";
            }
            if(dtcEndDate.getDate()==null){
                hasErrors=true;
                //error_message += Strings.ERROR_DATE_END+"\n";
            }
            if(dtcInitDate.getDate()!=null && dtcEndDate.getDate()!=null){
                if(dtcInitDate.getDate().getTime() > dtcEndDate.getDate().getTime()){
                    hasErrors=true;
                    //error_message += Strings.ERROR_DATE+"\n";
                }
            }
            
            if(hasErrors){
                //JOptionPane.showMessageDialog(this, error_message,Strings.ERROR_KARDEX_TITLE,JOptionPane.WARNING_MESSAGE);
                btnExport.setEnabled(false);
            }else{
                kardex = new ArrayList<Kardex>();
                kardex = kardexApplication.queryByParameters(warehouses.get(comboWarehouseFrom.getSelectedIndex()).getId(), products.get(comboProductFrom.getSelectedIndex()).getId(), dtcInitDate.getDate(),dtcEndDate.getDate());
                fillKardex();
                btnExport.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btnReportActionPerformed

    private void comboWarehouseFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboWarehouseFromItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED)
            fillProducts(warehouses.get(comboWarehouseFrom.getSelectedIndex()).getCondicion().getId());
    }//GEN-LAST:event_comboWarehouseFromItemStateChanged

    private void btnExportMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportMousePressed
        /*
        JOptionPane.setDefaultLocale(new Locale("es", "ES"));
        fc.setDialogTitle("Seleccione un archivo");
        fc.showOpenDialog(this);
        file = fc.getSelectedFile();
        System.out.println(file);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
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
            //JOptionPane.showMessageDialog(this, "Ocurrió un error al abrir el archivo",Strings.ERROR_KARDEX_TITLE,JOptionPane.ERROR_MESSAGE);
        }
        */
        JOptionPane.setDefaultLocale(new Locale("es", "ES"));
        fc.setDialogTitle("Seleccione un archivo");
        fc.showOpenDialog(this);
        file = fc.getSelectedFile();
        System.out.println(file);
        Date date = new Date();
        /* Export to XSL */
        try {
            File exlFile = file;
            WritableWorkbook writableWorkbook = Workbook.createWorkbook(exlFile);
 
            WritableSheet writableSheet = writableWorkbook.createSheet("Kardex", 0);
            URL url = getClass().getResource("../../images/warehouse-512-000000.png");
            java.io.File imageFile = new java.io.File(url.toURI());
            BufferedImage input = ImageIO.read(imageFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(input, "PNG", baos);
            
            writableSheet.addImage(new WritableImage(1,1,0.4,1,baos.toByteArray()));
            writableSheet.setColumnView(1, 26);
            writableSheet.setColumnView(2, 10);
            writableSheet.setColumnView(3, 10);
            writableSheet.setColumnView(4, 10);
            writableSheet.setColumnView(5, 10);
            writableSheet.setColumnView(6, 15);
            writableSheet.setColumnView(7, 15);
            writableSheet.setColumnView(8, 15);
            writableSheet.setColumnView(9, 15);
            writableSheet.setColumnView(10, 15);
            //Create Cells with contents of different data types.
            //Also specify the Cell coordinates in the constructor
            
            createHeader(writableSheet,date);
            
            fillReport(writableSheet);
 
            //Write and close the workbook
            writableWorkbook.write();
            writableWorkbook.close();
        } catch (Exception ex) {
            Logger.getLogger(AvailabilityReport.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(this, "Ocurrió un error al abrir el archivo",Strings.ERROR_KARDEX_TITLE,JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnExportMousePressed

    private void btnReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportMouseClicked
        // TODO add your handling code here:
        JOptionPane.setDefaultLocale(new Locale("es", "ES"));
        boolean hasErrors=false;
        String error_message = "Errores:\n";
        if(warehouses.size()>0 && products.size()>0){
            if(dtcInitDate.getDate()==null){
                hasErrors=true;
                //error_message += Strings.ERROR_DATE_INI+"\n";
            }
            if(dtcEndDate.getDate()==null){
                hasErrors=true;
                //error_message += Strings.ERROR_DATE_END+"\n";
            }
            if(dtcInitDate.getDate()!=null && dtcEndDate.getDate()!=null){
                if(dtcInitDate.getDate().getTime() > dtcEndDate.getDate().getTime()){
                    hasErrors=true;
                    //error_message += Strings.ERROR_DATE+"\n";
                }
            }
            
            if(hasErrors){
                //JOptionPane.showMessageDialog(this, error_message,Strings.ERROR_KARDEX_TITLE,JOptionPane.WARNING_MESSAGE);
                btnExport.setEnabled(false);
            }else{
                kardex = new ArrayList<Kardex>();
                kardex = kardexApplication.queryByParameters(warehouses.get(comboWarehouseFrom.getSelectedIndex()).getId(), products.get(comboProductFrom.getSelectedIndex()).getId(), dtcInitDate.getDate(),dtcEndDate.getDate());
                System.out.println(kardex.size());
                fillKardex();
                btnExport.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btnReportMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnReport;
    private javax.swing.JComboBox comboProductFrom;
    private javax.swing.JComboBox comboWarehouseFrom;
    private com.toedter.calendar.JDateChooser dtcEndDate;
    private com.toedter.calendar.JDateChooser dtcInitDate;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblKardex;
    // End of variables declaration//GEN-END:variables
}
