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
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import jxl.Workbook;
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
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import util.EntityState;
import util.EntityType;
import static util.EntityType.CONDITIONS;
import static util.EntityType.CONDITIONS_NAMES;
import util.Icons;
import util.InstanceFactory;
import util.Strings;

/**
 *
 * @author prote_000
 */
public class AvailabilityReport extends BaseView {
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
    
    String almacenR;
    String rackR;
    String condicionR;
    public AvailabilityReport() {
        initComponents();
        initialize();
        fillWarehouses();
        //condicionTxt.setText(EntityType.getCondition(warehouses.get(0).getCondicion().getId()).getNombre());
        try{
        warehouseCombo.setModel(new javax.swing.DefaultComboBoxModel(warehousesNames));
        fillRacksIni();
        rackCombo.setModel(new javax.swing.DefaultComboBoxModel(racksNames));
        condicionTxt.setText(EntityType.getCondition(warehouses.get(0).getCondicion().getId()).getNombre());
        }catch (Exception e){
            
        }
    }
    
    public void fillWarehouses(){
        warehouses = warehouseApplication.queryAll();
        warehousesNames = new String[warehouses.size()];
        for (int i = 0; i < warehouses.size(); i++) {
            warehousesNames[i] = warehouses.get(i).getDescripcion();
        } 
    }
    public void fillRacksIni(){
        racks = rackApplication.queryRacksByWarehouse(warehouses.get(0).getId());
        
        if (racks!=null){
            racksNames = new String[racks.size()+1];
            racksNames[0] = "Todos";
            for (int i = 0; i < racks.size(); i++) {
                racksNames[i+1] = racks.get(i).getId().toString();
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
        if(spots.size()>0){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
            Object[] row;
            
            /*
            
            
            // Inventario inicial
            row = new Object[]{
                        sdf.format(racks.get(0).getFechaRegistro()),
                        "Inventario Inicial",
                        "-",
                        0,
                    };
            model.addRow(row);
            */
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
            /*
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
        rackCombo = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        condicionTxt = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Reporte de Disponibilidad");

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
                "Almacen", "Rack", "Fila/Columna/Lado", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKardex.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblKardex);
        if (tblKardex.getColumnModel().getColumnCount() > 0) {
            tblKardex.getColumnModel().getColumn(0).setResizable(false);
            tblKardex.getColumnModel().getColumn(1).setResizable(false);
            tblKardex.getColumnModel().getColumn(2).setResizable(false);
            tblKardex.getColumnModel().getColumn(3).setResizable(false);
        }

        btnExport.setText("Exportar XLS");
        btnExport.setEnabled(false);
        btnExport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnExportMousePressed(evt);
            }
        });
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        rackCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rackComboItemStateChanged(evt);
            }
        });

        jLabel2.setText("Rack:");

        condicionTxt.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(rackCombo, 0, 128, Short.MAX_VALUE)
                                    .addComponent(warehouseCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(44, 44, 44)
                                .addComponent(jLabel4)))
                        .addGap(0, 2, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(condicionTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnExport, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnReport, javax.swing.GroupLayout.Alignment.TRAILING)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(warehouseCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(condicionTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rackCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(btnReport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                int idTipo;
                int idCon;
                spots = new ArrayList<Ubicacion>();
                
                
                idWare=warehouses.get(warehouseCombo.getSelectedIndex()).getId();
                
                if (rackCombo.getSelectedIndex()==0)
                    idCon=0;
                else
                    idCon=racks.get(rackCombo.getSelectedIndex()-1).getId();
                almacenR  =warehouseCombo.getSelectedItem().toString();
                rackR  =rackCombo.getSelectedItem().toString();
                condicionR = condicionTxt.getText();
                spots = spotApplication.queryByParameters(idWare,idCon);
                //System.out.println(racks.size());
                fillKardex();
                btnExport.setEnabled(true);
            }
        
    }//GEN-LAST:event_btnReportActionPerformed

    private void warehouseComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_warehouseComboItemStateChanged

        
        fillRacks(warehouses.get(warehouseCombo.getSelectedIndex()).getId());
        rackCombo.setModel(new javax.swing.DefaultComboBoxModel(racksNames));
        condicionTxt.setText(EntityType.getCondition(
                warehouses.get(warehouseCombo.getSelectedIndex()).getCondicion().getId())
                .getNombre());
    }//GEN-LAST:event_warehouseComboItemStateChanged

    private void btnExportMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportMousePressed
        JOptionPane.setDefaultLocale(new Locale("es", "ES"));
        file = getReportSelectedFile();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        /* Export to XSL */
        try {
            /*
            FileWriter excel = new FileWriter(file);
            DefaultTableModel model = (DefaultTableModel) tblKardex.getModel();
            excel.write("\t\t\tKardex\n");
            excel.write("Emitido\t"+dateFormat.format(date)+"\n");
            excel.write("Almacen\t"+warehouses.get(warehouseCombo.getSelectedIndex()).getDescripcion()+"\n");
            excel.write("Unidad\tPallets\n");

            
            for(int i = 0; i < model.getColumnCount(); i++)
                excel.write(model.getColumnName(i) + "\t");
            excel.write("\n");
            for(int i=0; i< model.getRowCount(); i++) {
                for(int j=0; j < model.getColumnCount(); j++)
                    excel.write(model.getValueAt(i,j).toString()+"\t");
                excel.write("\n");
            }
            excel.close();
                    */
            File exlFile = file;
            WritableWorkbook writableWorkbook = Workbook.createWorkbook(exlFile);
 
            WritableSheet writableSheet = writableWorkbook.createSheet(
                    "Reporte de Disponibilidad", 0);
            URL url = getClass().getResource("../../images/warehouse-512-000000.png");
            java.io.File imageFile = new java.io.File(url.toURI());
            BufferedImage input = ImageIO.read(imageFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(input, "PNG", baos);
            //setbackgruound
            
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
            
            createHeader(writableSheet,date,dateFormat);
            
            fillReport(writableSheet);
 
            //Write and close the workbook
            writableWorkbook.write();
            writableWorkbook.close();
            
            
        } catch (Exception ex) {
            Logger.getLogger(AvailabilityReport.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(this, "Ocurrió un error al abrir el archivo",Strings.ERROR_KARDEX_TITLE,JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnExportMousePressed

    private void rackComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rackComboItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_rackComboItemStateChanged

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnExportActionPerformed

    public void createHeader(WritableSheet writableSheet, Date date, DateFormat dateFormat){
        
        try{
            writableSheet.getSettings().setShowGridLines(false);
            Label label0 = new Label(1, 1, "");
            Label label1 = new Label(4, 1, "Reporte de Disponibilidad");
            Label label2 = new Label(7, 1, "Fecha: "+ dateFormat.format(date));
            Label label3 = new Label(1, 2, "Almacen: "+ almacenR );
            Label label4 = new Label(1, 3, "Condicion: "+condicionR);
            Label label5 = new Label(1, 4, "Rack: "+ rackR);
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
             
            Label t1 = new Label(1, 6, "Almacen"); 
            Label t2 = new Label(2, 6, "Rack"); 
            Label t3 = new Label(3, 6, "Fila"); 
            Label t4 = new Label(4, 6, "Columna"); 
            Label t5 = new Label(5, 6, "Lado"); 
            Label t6 = new Label(6, 6, "Estado"); 
             
             t1.setCellFormat(headerTFormat);
             t2.setCellFormat(headerTFormat);
             t3.setCellFormat(headerTFormat);
             t4.setCellFormat(headerTFormat);
             t5.setCellFormat(headerTFormat);
             t6.setCellFormat(headerTFormat);
             
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
             for(Ubicacion ubi : spots){
                /*
                row = new Object[]{
                    arr[0].toString(),
                    arr[1].toString(),
                    arr[2].toString(),
                    arr[3].toString()
                };
                model.addRow(row);
                        */
                Label l1 = new Label(1, fil+i, almacenR);
                Number l2 = new Number(2, fil+i, ubi.getRack().getId());
                Number l3 = new Number(3, fil+i, ubi.getFila());
                Number l4 = new Number(4, fil+i, ubi.getColumna());
                Label l5 = new Label(5, fil+i, ubi.getLado());
                Label l6 = new Label(6, fil+i, EntityState.getSpotsState()[ubi.getEstado()]); 
                if (i%2==0){
                    l1.setCellFormat(imparFormat);
                    l2.setCellFormat(imparFormat);
                    l3.setCellFormat(imparFormat);
                    l4.setCellFormat(imparFormat);
                    l5.setCellFormat(imparFormat);
                    l6.setCellFormat(imparFormat);
                }else{
                    l1.setCellFormat(parFormat);
                    l2.setCellFormat(parFormat);
                    l3.setCellFormat(parFormat);
                    l4.setCellFormat(parFormat);
                    l5.setCellFormat(parFormat);
                    l6.setCellFormat(parFormat);
                    
                }
                writableSheet.addCell(l1);
                writableSheet.addCell(l2);
                writableSheet.addCell(l3);
                writableSheet.addCell(l4);
                writableSheet.addCell(l5);
                writableSheet.addCell(l6);
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JComboBox rackCombo;
    private javax.swing.JTable tblKardex;
    private javax.swing.JComboBox warehouseCombo;
    // End of variables declaration//GEN-END:variables
}
