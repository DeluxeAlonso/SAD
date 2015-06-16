/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.reports;

import application.pallet.PalletApplication;
import application.producttype.ProductTypeApplication;
import application.warehouse.WarehouseApplication;
import client.base.BaseView;
import entity.Almacen;
import entity.GuiaRemision;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import util.InstanceFactory;
import util.Strings;

/**
 *
 * @author User
 */
public class InternmentReport extends BaseView {

    /**
     * Creates new form InternmentReport
     */
    PalletApplication palletApplication=InstanceFactory.Instance.getInstance("palletApplication", PalletApplication.class);
    WarehouseApplication warehouseApplication=InstanceFactory.Instance.getInstance("warehouseApplication", WarehouseApplication.class);
    ArrayList<Almacen> warehouses ;
    List<Object[]> listReport;
    File file = null;
    String warehouseName=null;
    public InternmentReport() {
        initComponents();
        initialize();
        setTitle("Reporte de Internamiento");
        fillWarehouses();
        
    }

    
    public void fillWarehouses(){
        warehouses = warehouseApplication.queryAll();
        
        String [] warehousesNames = new String[warehouses.size()+1];
        warehousesNames[0]="Todos";
        for (int i = 0; i < warehouses.size(); i++) {
            warehousesNames[i+1] = warehouses.get(i).getDescripcion();
        } 
        warehouseCombo.setModel(new javax.swing.DefaultComboBoxModel(warehousesNames));
    }
    
      
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        generateButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        reportTable = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        iniDate = new com.toedter.calendar.JDateChooser();
        endDate = new com.toedter.calendar.JDateChooser();
        warehouseCombo = new javax.swing.JComboBox();
        exportBtn = new javax.swing.JButton();

        setClosable(true);

        jLabel3.setText("Fecha Inicial:");

        jLabel4.setText("Fecha Final:");

        jLabel5.setText("Almacen:");

        generateButton.setText("GenerarReporte");
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });

        reportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "# Internamiento", "Almacén", "Producto", "Tipo", "Condicion", "Cantidad", "Fecha Internado"
            }
        ));
        jScrollPane1.setViewportView(reportTable);
        if (reportTable.getColumnModel().getColumnCount() > 0) {
            reportTable.getColumnModel().getColumn(0).setResizable(false);
        }

        warehouseCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        exportBtn.setText("Exportar XLS");
        exportBtn.setEnabled(false);
        exportBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(warehouseCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(iniDate, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(134, 134, 134)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 489, Short.MAX_VALUE)
                        .addComponent(generateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27))
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exportBtn)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(warehouseCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4))
                    .addComponent(iniDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(generateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(exportBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) reportTable.getModel();
        model.setRowCount(0);
    }
    
    public void fillTable(){
        DefaultTableModel model = (DefaultTableModel) reportTable.getModel();
        if(listReport.size()>0){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
            Object[] row;
            for(Object[] arr : listReport){
                row = new Object[]{
                    arr[0].toString(),
                    arr[1].toString(),
                    arr[2].toString(),
                    arr[3].toString(),
                    arr[4].toString(),
                    arr[5].toString(),
                    arr[6].toString()
                };
                model.addRow(row);
            }
        }
    }
    
    
    
    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        // TODO add your handling code here:
        boolean hasErrors=false;
        int almacen=0;
        if (warehouseCombo.getSelectedIndex()==0){
            almacen=0;
        }else
                almacen = warehouses.get(warehouseCombo.getSelectedIndex()-1).getId();
        if(iniDate.getDate()==null){
            hasErrors=true;
            error_message += Strings.ERROR_DATE_INI+"\n";
        }
        if(endDate.getDate()==null){
            hasErrors=true;
            error_message += Strings.ERROR_DATE_END+"\n";
        }
        if(iniDate.getDate()!=null && iniDate!=null){
            if(iniDate.getDate().getTime() > endDate.getDate().getTime()){
                hasErrors=true;
                error_message += Strings.ERROR_DATE+"\n";
            }
        }
        
        
        if (hasErrors){
            JOptionPane.showMessageDialog(this, "Los campos Fecha inicial y Fecha final son Obligatorios.","Error reporte de internamiento",JOptionPane.WARNING_MESSAGE);
            exportBtn.setEnabled(false);
        }else{
            warehouseName=warehouseCombo.getSelectedItem().toString();
            Calendar c = Calendar.getInstance(); 
            c.setTime(endDate.getDate()); 
            c.add(Calendar.DATE, 1);
            Date newEndDate = c.getTime();
            Calendar c1 = Calendar.getInstance(); 
            c1.setTime(iniDate.getDate()); 
            c1.set(Calendar.HOUR_OF_DAY, 0);
            c1.set( Calendar.MINUTE, 0);
            c1.set( Calendar.SECOND, 0);
            c1.set( Calendar.MILLISECOND, 0);
            Date newIniDate = c1.getTime();
            listReport = palletApplication.queryByReportInter(almacen,newIniDate , newEndDate, 0);
            clearTable();
            fillTable();
            exportBtn.setEnabled(true);
        }
        
        
        
    }//GEN-LAST:event_generateButtonActionPerformed

    private void exportBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportBtnActionPerformed
        // TODO add your handling code here:
        
        JOptionPane.setDefaultLocale(new Locale("es", "ES"));
        file = getReportSelectedFile();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        /* Export to XSL */
        try {

            File exlFile = file;
            
            WorkbookSettings ws = new WorkbookSettings();
            ws.setEncoding("UTF8");
            WritableWorkbook writableWorkbook = Workbook.createWorkbook(exlFile,ws);
 
            WritableSheet writableSheet = writableWorkbook.createSheet(
                    "Reporte de internamiento", 0);
            URL url = getClass().getResource("../../images/warehouse-512-000000.png");
            java.io.File imageFile = new java.io.File(url.toURI());
            BufferedImage input = ImageIO.read(imageFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(input, "PNG", baos);
            
            writableSheet.addImage(new WritableImage(1,1,0.4,1,baos.toByteArray()));
            writableSheet.setColumnView(1, 14);
            writableSheet.setColumnView(2, 12);
            writableSheet.setColumnView(3, 25);
            writableSheet.setColumnView(4, 18);
            writableSheet.setColumnView(5, 12);
            writableSheet.setColumnView(6, 12);
            writableSheet.setColumnView(7, 14);
            //Create Cells with contents of different data types.
            //Also specify the Cell coordinates in the constructor
            
            createHeader(writableSheet,date,dateFormat);
            
            fillReport(writableSheet,dateFormat);
 
            //Write and close the workbook
            writableWorkbook.write();
            writableWorkbook.close();
            
            
        } catch (Exception ex) {
            Logger.getLogger(AvailabilityReport.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(this, "Ocurrió un error al abrir el archivo",Strings.ERROR_KARDEX_TITLE,JOptionPane.ERROR_MESSAGE);
        }
        
        
    }//GEN-LAST:event_exportBtnActionPerformed

    public void createHeader(WritableSheet writableSheet, Date date, DateFormat dateFormat){
        
        try{
            Label label0 = new Label(1, 1, "");
            Label label1 = new Label(4, 1, "Reporte de Internamientos");
            Label label2 = new Label(7, 1, "Fecha: "+ dateFormat.format(date));
            Label label3 = new Label(1, 2, "Almacen: "+ warehouseName);
            Label label4 = new Label(1, 3, "Desde: "+ dateFormat.format(iniDate.getDate()));
            Label label5 = new Label(1, 4, "Hasta: "+ dateFormat.format(endDate.getDate()));
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
             
             
             
             //normalFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,
             //jxl.format.Colour.BLACK);
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
             
            Label t1 = new Label(1, 7, "#Internamiento"); 
            Label t2 = new Label(2, 7, "Almacen"); 
            Label t3 = new Label(3, 7, "Producto"); 
            Label t4 = new Label(4, 7, "Tipo"); 
            Label t5 = new Label(5, 7, "Condicion"); 
            Label t6 = new Label(6, 7, "Cantidad"); 
            Label t7 = new Label(7, 7, "Fecha de Internamiento");
             
             t1.setCellFormat(headerTFormat);
             t2.setCellFormat(headerTFormat);
             t3.setCellFormat(headerTFormat);
             t4.setCellFormat(headerTFormat);
             t5.setCellFormat(headerTFormat);
             t6.setCellFormat(headerTFormat);
             t7.setCellFormat(headerTFormat);
             
             label0.setCellFormat(headerLFormat);
             label1.setCellFormat(tittleFormat);
             label2.setCellFormat(headerRFormat);
             label3.setCellFormat(headerLFormat);
             label4.setCellFormat(headerLFormat);
             label5.setCellFormat(headerLFormat);
            writableSheet.addCell(label0);
            writableSheet.addCell(label1);
            writableSheet.addCell(label2);
            writableSheet.addCell(label3);
            writableSheet.addCell(label4);
            writableSheet.addCell(label5);
            writableSheet.addCell(t1);
            writableSheet.addCell(t2);
            writableSheet.addCell(t3);
            writableSheet.addCell(t4);
            writableSheet.addCell(t5);
            writableSheet.addCell(t6);
            writableSheet.addCell(t7);
            
        }
            catch(Exception e){
                
            }
    }
    
    
    private void fillReport(WritableSheet writableSheet, DateFormat dateFormat){
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
             parFormat.setAlignment(Alignment.CENTRE);
             DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = new Date();
             
             WritableCellFormat imparFormat = new WritableCellFormat(rowFont);
             imparFormat.setAlignment(Alignment.CENTRE);
             imparFormat.setWrap(false);
             int col=1;
             int fil=8;
             int i=0;
             Object[] row;
             for(Object[] arr : listReport){
                
                jxl.write.Number l1 = new jxl.write.Number(1, fil+i, Integer.parseInt(arr[0].toString()));
                Label l2 = new Label(2, fil+i, arr[1].toString());
                Label l3 = new Label(3, fil+i,arr[2].toString());
                Label l4 = new Label(4, fil+i, arr[3].toString());
                Label l5 = new Label(5, fil+i, arr[4].toString());
                jxl.write.Number l6 = new jxl.write.Number(6, fil+i, Integer.parseInt(arr[5].toString())); 
                Label l7 = new Label(7, fil+i, dateFormat.format((Date)arr[6]));
                if (i%2==0){
                    l1.setCellFormat(imparFormat);
                    l2.setCellFormat(imparFormat);
                    l3.setCellFormat(imparFormat);
                    l4.setCellFormat(imparFormat);
                    l5.setCellFormat(imparFormat);
                    l6.setCellFormat(imparFormat);
                    l7.setCellFormat(imparFormat);
                }else{
                    l1.setCellFormat(parFormat);
                    l2.setCellFormat(parFormat);
                    l3.setCellFormat(parFormat);
                    l4.setCellFormat(parFormat);
                    l5.setCellFormat(parFormat);
                    l6.setCellFormat(parFormat);
                    l7.setCellFormat(parFormat);
                }
                writableSheet.addCell(l1);
                writableSheet.addCell(l2);
                writableSheet.addCell(l3);
                writableSheet.addCell(l4);
                writableSheet.addCell(l5);
                writableSheet.addCell(l6);
                writableSheet.addCell(l7);
                i++;
            }
                     
                     
                     
            
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser endDate;
    private javax.swing.JButton exportBtn;
    private javax.swing.JButton generateButton;
    private com.toedter.calendar.JDateChooser iniDate;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable reportTable;
    private javax.swing.JComboBox warehouseCombo;
    // End of variables declaration//GEN-END:variables
}
