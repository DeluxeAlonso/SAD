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
import entity.TipoProducto;
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
public class ProductCaducityReport extends BaseView {

    /**
     * Creates new form InternmentReport
     */
    PalletApplication palletApplication=InstanceFactory.Instance.getInstance("palletApplication", PalletApplication.class);
    WarehouseApplication warehouseApplication=InstanceFactory.Instance.getInstance("warehouseApplication", WarehouseApplication.class);
    ProductTypeApplication productTypeApplication=InstanceFactory.Instance.getInstance("productTypeApplication", ProductTypeApplication.class);
    ArrayList<TipoProducto> productTypes ;
    List<Object[]> listReport;
    File file = null;
    String warehouseName=null;
    String typeReport="";
    String plazoReport="";
    
    public ProductCaducityReport() {
        initComponents();
        initialize();
        setTitle("Reporte de Fecha de Caducidad");
        fillWarehouses();
        
    }

    
    public void fillWarehouses(){
        productTypes = productTypeApplication.queryAll();
        
        String [] typeNames = new String[productTypes.size()+1];
        typeNames[0]="Todos";
        for (int i = 0; i < productTypes.size(); i++) {
            typeNames[i+1] = productTypes.get(i).getNombre();
        } 
        typeCombo.setModel(new javax.swing.DefaultComboBoxModel(typeNames));
    }
    
      
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        generateButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        reportTable = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        typeCombo = new javax.swing.JComboBox();
        exportBtn = new javax.swing.JButton();
        fechaCombo = new javax.swing.JComboBox();

        setClosable(true);

        jLabel4.setText("Plazo de vencimiento:");

        jLabel5.setText("Tipo de Producto:");

        generateButton.setText("GenerarReporte");
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });

        reportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Tipo", "Condicion", "Cantidad", "Fecha de Caducidad"
            }
        ));
        jScrollPane1.setViewportView(reportTable);

        typeCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        exportBtn.setText("Exportar XLS");
        exportBtn.setEnabled(false);
        exportBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportBtnActionPerformed(evt);
            }
        });

        fechaCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "3 dias", "1 semana", "2 semanas", "1 mes", "2 meses", "Historico" }));
        fechaCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fechaComboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(typeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(generateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(fechaCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(exportBtn)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(typeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(fechaCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(generateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
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
                };
                model.addRow(row);
            }
        }
    }
    
    
    
    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        // TODO add your handling code here:
        boolean hasErrors=false;
        int almacen=0;
        
        
        
        if (hasErrors){
            JOptionPane.showMessageDialog(this, "Los campos Fecha inicial y Fecha final son Obligatorios.","Error reporte de internamiento",JOptionPane.WARNING_MESSAGE);
            exportBtn.setEnabled(false);
        }else{
            int idType=0;
            if (typeCombo.getSelectedIndex()==0) idType=0;
            else idType=productTypes.get(typeCombo.getSelectedIndex()-1).getId();
            typeReport = typeCombo.getSelectedItem().toString();
            plazoReport = fechaCombo.getSelectedItem().toString();
            listReport = palletApplication.queryByReportCaducity(idType,fechaCombo.getSelectedIndex());
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
                    "Reporte de Caducidad", 0);
            writableSheet.getSettings().setShowGridLines(false);
            URL url = getClass().getResource("../../images/warehouse-512-000000.png");
            java.io.File imageFile = new java.io.File(url.toURI());
            BufferedImage input = ImageIO.read(imageFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(input, "PNG", baos);
            
            writableSheet.addImage(new WritableImage(1,1,0.1,1,baos.toByteArray()));
            writableSheet.setColumnView(1, 40);
            writableSheet.setColumnView(2, 18);
            writableSheet.setColumnView(3, 14);
            writableSheet.setColumnView(4, 10);
            writableSheet.setColumnView(5, 12);
            writableSheet.setColumnView(6, 12);
            writableSheet.setColumnView(7, 12);
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

    private void fechaComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fechaComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaComboActionPerformed

    public void createHeader(WritableSheet writableSheet, Date date, DateFormat dateFormat){
        
        try{
            Label label0 = new Label(1, 1, "");
            Label label1 = new Label(2, 1, "Reporte de Caducidad");
            Label label2 = new Label(5, 1, "Fecha: "+ dateFormat.format(date));
            Label label3 = new Label(1, 2, "Tipo de Producto: "+ typeReport);
            Label label4 = new Label(1, 3, "Plazo de vencimiento: "+ plazoReport);
            
            WritableFont tittleFont = new WritableFont(WritableFont.createFont("Calibri"),
             16,
             WritableFont.BOLD,  false,
             UnderlineStyle.NO_UNDERLINE);
             tittleFont.setColour(jxl.format.Colour.RED);
            
            WritableCellFormat tittleFormat = new WritableCellFormat(tittleFont);
             tittleFormat.setWrap(false);
             tittleFormat.setAlignment(jxl.format.Alignment.LEFT);
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
             
            
            Label t1 = new Label(1, 6, "Producto"); 
            Label t2 = new Label(2, 6, "Tipo"); 
            Label t3 = new Label(3, 6, "Condicion"); 
            Label t4 = new Label(4, 6, "Cantidad"); 
            Label t5 = new Label(5, 6, "Fecha de Caducidad");
             
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
            writableSheet.addCell(label0);
            writableSheet.addCell(label1);
            writableSheet.addCell(label2);
            writableSheet.addCell(label3);
            writableSheet.addCell(label4);
            writableSheet.addCell(t1);
            writableSheet.addCell(t2);
            writableSheet.addCell(t3);
            writableSheet.addCell(t4);
            writableSheet.addCell(t5);
            
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
             
            WritableCellFormat parFormat1 = new WritableCellFormat(rowFont);
             parFormat1.setWrap(false);
             parFormat1.setBackground(Colour.GREY_25_PERCENT);
             
             
             WritableCellFormat imparFormat1 = new WritableCellFormat(rowFont);
             imparFormat1.setWrap(false); 
             
             
             int col=1;
             int fil=7;
             int i=0;
             Object[] row;
             for(Object[] arr : listReport){
                
                
                Label l1 = new Label(1, fil+i, arr[0].toString());
                Label l2 = new Label(2, fil+i,arr[1].toString());
                Label l3 = new Label(3, fil+i, arr[2].toString());
                jxl.write.Number l4 = new jxl.write.Number(4, fil+i, Integer.parseInt(arr[3].toString())); 
                Label l5 = new Label(5, fil+i, dateFormat.format((Date)arr[4]));
                if (i%2==0){
                    l1.setCellFormat(imparFormat1);
                    l2.setCellFormat(imparFormat);
                    l3.setCellFormat(imparFormat);
                    l4.setCellFormat(imparFormat);
                    l5.setCellFormat(imparFormat);
                }else{
                    l1.setCellFormat(parFormat1);
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
            System.out.println(e.toString());
        }
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exportBtn;
    private javax.swing.JComboBox fechaCombo;
    private javax.swing.JButton generateButton;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable reportTable;
    private javax.swing.JComboBox typeCombo;
    // End of variables declaration//GEN-END:variables
}
