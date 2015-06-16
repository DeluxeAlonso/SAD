/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.reports;

import application.kardex.KardexApplication;
import application.pallet.PalletApplication;
import application.product.ProductApplication;
import application.producttype.ProductTypeApplication;
import application.rack.RackApplication;
import application.spot.SpotApplication;
import application.warehouse.WarehouseApplication;
import client.base.BaseView;
import entity.Almacen;
import entity.Condicion;
import entity.Kardex;
import entity.Pallet;
import entity.Producto;
import entity.Rack;
import entity.TipoProducto;
import entity.Ubicacion;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Boolean;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import util.EntityState;
import util.EntityType;
import static util.EntityType.CONDITIONS;
import static util.EntityType.CONDITIONS_NAMES;
import util.InstanceFactory;
import util.Strings;

/**
 *
 * @author prote_000
 */
public class StockReport extends BaseView {
    WarehouseApplication warehouseApplication = InstanceFactory.Instance.getInstance("warehouseApplicaiton", WarehouseApplication.class);
    RackApplication rackApplication = InstanceFactory.Instance.getInstance("rackApplicaiton", RackApplication.class);
    SpotApplication spotApplication = InstanceFactory.Instance.getInstance("spotApplicaiton", SpotApplication.class);
    PalletApplication palletApplication = InstanceFactory.Instance.getInstance("palletApplicaiton", PalletApplication.class);
    ProductApplication productApplication = InstanceFactory.Instance.getInstance("productApplicaiton", ProductApplication.class);
    ProductTypeApplication productTypeApplication = InstanceFactory.Instance.getInstance("productTypeApplicaiton", ProductTypeApplication.class);
    KardexApplication kardexApplication = InstanceFactory.Instance.getInstance("kardexApplicaiton", KardexApplication.class);
    public ArrayList<Almacen> warehouses;
    public ArrayList<Condicion> conditions;
    public ArrayList<Kardex> kardex;
    public ArrayList<Rack> racks;
    public ArrayList<Ubicacion> spots;
    public ArrayList<Pallet> pallets;
    public ArrayList<TipoProducto> productTypes;
    private String [] warehousesNames;
    private String [] conditionNames;
    private String [] racksNames;
    private String [] typeNames;
    private List<Object[]> listReport;
    JFileChooser fc = new JFileChooser();
    File file = null;
    /**
     * Creates new form KardexReport
     */
    
    String alm;
    String tipo;
    String condi;
    int reporte;
    public StockReport() {
        initComponents();
        initialize();
        fillWarehouses();
        //condicionTxt.setText(EntityType.getCondition(warehouses.get(0).getCondicion().getId()).getNombre());
        warehouseCombo.setModel(new javax.swing.DefaultComboBoxModel(warehousesNames));
        fillRacksIni();
        //tipoCombo.setModel(new javax.swing.DefaultComboBoxModel(typeNames));
        fillConditionsIni();
        //condicionCombo.setModel(new javax.swing.DefaultComboBoxModel(conditionNames));
    }
    
    public void fillWarehouses(){
        warehouses = warehouseApplication.queryAll();
        warehousesNames = new String[warehouses.size()+1];
        warehousesNames[0]="Todos";
        for (int i = 0; i < warehouses.size(); i++) {
            warehousesNames[i+1] = warehouses.get(i).getDescripcion();
        } 
    }
    public void fillRacksIni(){
        productTypes = productTypeApplication.queryAll();
        
        if (productTypes!=null){
            typeNames = new String[productTypes.size()+1];
            typeNames[0] = "Todos";
            for (int i = 0; i < productTypes.size(); i++) {
                typeNames[i+1] = productTypes.get(i).getNombre();
            } 
        }
    }
    
    public void fillConditionsIni(){
        conditions = EntityType.CONDITIONS;
        if (conditions!=null){
            conditionNames = new String[conditions.size()+1];
            conditionNames[0] = "Todos";
            for (int i = 0; i < conditions.size(); i++) {
                conditionNames[i+1] = conditions.get(i).getNombre();
            } 
        }
    }    
    public void fillRacks(int id){
        racks = rackApplication.queryRacksByWarehouse(id);
        
        if (racks!=null){
            racksNames = new String[racks.size()+1];
            racksNames[0] = "Todos";
            for (int i = 0; i < racks.size(); i++) {
                racksNames[i+1] = racks.get(i).getId().toString();
            } 
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
        if(listReport.size()>0){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
            Object[] row;
            
            
            for(Object[] arr : listReport){
                
                row = new Object[]{
                    arr[0].toString(),
                    arr[1].toString(),
                    arr[2].toString(),
                    arr[3].toString()
                };
                model.addRow(row);
            }
            /*
            
            
            // Inventario inicial
            row = new Object[]{
                        sdf.format(racks.get(0).getFechaRegistro()),
                        "Inventario Inicial",
                        "-",
                        0,
                    };
            model.addRow(row);
            for(Ubicacion spotItem : spots){
                String position = spotItem.getFila()+"/" +spotItem.getColumna()+"/"+ spotItem.getLado();
                row = new Object[]{
                    warehouses.get(warehouseCombo.getSelectedIndex()).getDescripcion(),
                    spotItem.getRack().getId(),
                    position,
                    EntityState.getSpotsState()[spotItem.getEstado()]
                };
                model.addRow(row);
            }
            // Inventario final
            
            row = new Object[]{
                        sdf.format(kardex.get(.size()-1).getFecha()),
                        "Inventario Final",
                        "-",
                        "Hola 2",
                    };
            model.addRow(row);
            */
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
        warehouseCombo = new javax.swing.JComboBox();
        btnReport = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKardex = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        btnExport = new javax.swing.JButton();
        reporteCombo = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        condicionTxt = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Reporte de Stock");

        jLabel1.setText("Almacén:");

        warehouseCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                warehouseComboItemStateChanged(evt);
            }
        });

        btnReport.setText("Generar Reporte");
        btnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportActionPerformed(evt);
            }
        });

        jLabel4.setText("Condicion:");

        tblKardex.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Producto", "Tipo", "Condicion", "Stock"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblKardex);
        if (tblKardex.getColumnModel().getColumnCount() > 0) {
            tblKardex.getColumnModel().getColumn(1).setResizable(false);
        }

        btnExport.setText("Exportar XLS");
        btnExport.setEnabled(false);
        btnExport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnExportMousePressed(evt);
            }
        });

        reporteCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Productos internados", "Stock Total", "Stock Logico" }));
        reporteCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                reporteComboItemStateChanged(evt);
            }
        });

        jLabel5.setText("Reporte:");

        condicionTxt.setEditable(false);
        condicionTxt.setText("Todos");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnExport))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnReport)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(reporteCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(18, 18, 18)
                                        .addComponent(warehouseCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(condicionTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 13, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reporteCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(warehouseCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(condicionTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(btnExport)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed
        JOptionPane.setDefaultLocale(new Locale("es", "ES"));
        boolean hasErrors=false;
        String error_message = "Errores:\n";
            
            if(hasErrors){
                //JOptionPane.showMessageDialog(this, error_message,Strings.ERROR_KARDEX_TITLE,JOptionPane.WARNING_MESSAGE);
                btnExport.setEnabled(false);
            }else{
                int idWare;
                int idReport;
                int idCon;
                spots = new ArrayList<Ubicacion>();
                
                if (warehouseCombo.getSelectedIndex()==0){
                    idWare=0;
                }else{
                    idWare=warehouses.get(warehouseCombo.getSelectedIndex()-1).getId();
                }
                if (warehouseCombo.getSelectedIndex()==0){
                    idCon=0;
                }else{
                    idCon=warehouses.get(warehouseCombo.getSelectedIndex()-1).getCondicion().getId();
                }
                idReport= reporteCombo.getSelectedIndex();
                
                if (warehouseCombo.getSelectedIndex()==0){
                    alm = "Todos";
                    condi = "Todos";
                }else {
                    alm = warehouseCombo.getSelectedItem().toString();
                    condi = condicionTxt.getText();
                }
                listReport = palletApplication.queryByReport(idWare,idCon,0,idReport);
                //System.out.println(racks.size());
                fillKardex();
                btnExport.setEnabled(true);
            }
        
    }//GEN-LAST:event_btnReportActionPerformed

    private void warehouseComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_warehouseComboItemStateChanged
        if (warehouseCombo.getSelectedIndex()==0){
            condicionTxt.setText("Todos");
        }else{
            condicionTxt.setText(EntityType.CONDITIONS_NAMES[warehouses.get
            (warehouseCombo.getSelectedIndex()-1).getCondicion().getId()]);
        }
            

    }//GEN-LAST:event_warehouseComboItemStateChanged

    private void btnExportMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportMousePressed
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
                    "Reporte de Stock", 0);
            writableSheet.getSettings().setShowGridLines(false);
            writableSheet.getSettings().setPrintGridLines(false);
            writableSheet.setColumnView(1, 26);
            writableSheet.setColumnView(2, 15);
            writableSheet.setColumnView(3, 15);
            writableSheet.setColumnView(4, 15);
            writableSheet.setColumnView(5, 15);
            writableSheet.setColumnView(6, 15);
            writableSheet.setColumnView(7, 15);
            writableSheet.setColumnView(8, 15);
            writableSheet.setColumnView(9, 15);
            writableSheet.setColumnView(10, 15);
            //Create Cells with contents of different data types.
            //Also specify the Cell coordinates in the constructor
            
            createHeader(writableSheet,date,dateFormat);
            
            fillReport(writableSheet);
 
            //Write and close the workbook
            
            writableWorkbook.write();
            
            writableWorkbook.close();
            
            
        } catch (Exception ex) {
            Logger.getLogger(StockReport.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Ocurrió un error al abrir el archivo","Error al exportar",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnExportMousePressed

    private void reporteComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_reporteComboItemStateChanged
        // TODO add your handling code here:
        if (reporteCombo.getSelectedIndex()==0){
            warehouseCombo.setEnabled(true);
        }else if (reporteCombo.getSelectedIndex()==1)
        {
            warehouseCombo.setEnabled(false);
            warehouseCombo.setSelectedIndex(0);
            condicionTxt.setText("Todos");
        }else if (reporteCombo.getSelectedIndex()==2){
            warehouseCombo.setEnabled(false);
            warehouseCombo.setSelectedIndex(0);
            condicionTxt.setText("Todos");
        }
    }//GEN-LAST:event_reporteComboItemStateChanged

    public void createHeader(WritableSheet writableSheet, Date date, DateFormat dateFormat){
        
        try{
            Label label0 = new Label(1, 1, "SAD");
            Label label1 = new Label(3, 1, "Reporte de Stock Total");;
            if (reporte==0){
                label1 = new Label(3, 1, "Reporte de productos internados");
            }else if (reporte==1){
                label1 = new Label(3, 1, "Reporte de Stock Total");
            }
            else if (reporte==2){
                label1 = new Label(3, 1, "Reporte de Stock Logico");
            }
            Label label2 = new Label(5, 1, "Fecha: "+ dateFormat.format(date));
            Label label3 = new Label(1, 2, "Almacen: "+ alm );
            //Label label4 = new Label(1, 3, "Tipo de producto: "+tipo);
            Label label5 = new Label(1, 3, "Condicion: "+condi);
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
            
            Label t1 = new Label(1, 6, "Producto"); 
            Label t2 = new Label(2, 6, "Tipo"); 
            Label t3 = new Label(3, 6, "Condicion"); 
            Label t4 = new Label(4, 6, "Stock"); 
             
             t1.setCellFormat(headerTFormat);
             t2.setCellFormat(headerTFormat);
             t3.setCellFormat(headerTFormat);
             t4.setCellFormat(headerTFormat);
             
             label0.setCellFormat(headerLFormat);
             label1.setCellFormat(tittleFormat);
             label2.setCellFormat(headerRFormat);
             label3.setCellFormat(headerLFormat);
             //label4.setCellFormat(headerLFormat);
             label5.setCellFormat(headerLFormat);
            writableSheet.addCell(label0);
            writableSheet.addCell(label1);
            writableSheet.addCell(label2);
            writableSheet.addCell(label3);
            //writableSheet.addCell(label4);
            writableSheet.addCell(label5);
            writableSheet.addCell(t1);
            writableSheet.addCell(t2);
            writableSheet.addCell(t3);
            writableSheet.addCell(t4);
            
        }
            catch(Exception e){
                
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
             int fil=7;
             int i=0;
             Object[] row;
             for(Object[] arr : listReport){
                /*
                row = new Object[]{
                    arr[0].toString(),
                    arr[1].toString(),
                    arr[2].toString(),
                    arr[3].toString()
                };
                model.addRow(row);
                        */
                Label l1 = new Label(1, fil+i, arr[0].toString());
                Label l2 = new Label(2, fil+i, arr[1].toString());
                Label l3 = new Label(3, fil+i, arr[2].toString());
                Number num = new Number(4, fil+i, Integer.parseInt(arr[3].toString()));    
                if (i%2==0){
                    l1.setCellFormat(imparFormat);
                    l2.setCellFormat(imparFormat);
                    l3.setCellFormat(imparFormat);
                    num.setCellFormat(imparFormat);
                }else{
                    l1.setCellFormat(parFormat);
                    l2.setCellFormat(parFormat);
                    l3.setCellFormat(parFormat);
                    num.setCellFormat(parFormat);
                }
                writableSheet.addCell(l1);
                writableSheet.addCell(l2);
                writableSheet.addCell(l3);
                writableSheet.addCell(num);
                i++;
            }
                     
                     
                     
            
        }catch (Exception e){
            
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnReport;
    private javax.swing.JTextField condicionTxt;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JComboBox reporteCombo;
    private javax.swing.JTable tblKardex;
    private javax.swing.JComboBox warehouseCombo;
    // End of variables declaration//GEN-END:variables
}
