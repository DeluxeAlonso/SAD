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
    public AvailabilityReport() {
        initComponents();
        initialize();
        fillWarehouses();
        //condicionTxt.setText(EntityType.getCondition(warehouses.get(0).getCondicion().getId()).getNombre());
        warehouseCombo.setModel(new javax.swing.DefaultComboBoxModel(warehousesNames));
        fillRacksIni();
        tipoCombo.setModel(new javax.swing.DefaultComboBoxModel(typeNames));
        fillConditionsIni();
        condicionCombo.setModel(new javax.swing.DefaultComboBoxModel(conditionNames));
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
        if(racks.size()>0){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
            Object[] row;
            // Inventario inicial
            /*
            row = new Object[]{
                        sdf.format(racks.get(0).getFechaRegistro()),
                        "Inventario Inicial",
                        "-",
                        0,
                    };
            model.addRow(row);*/
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
        tipoCombo = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        condicionCombo = new javax.swing.JComboBox();
        condicionCombo1 = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();

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
                true, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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

        tipoCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                tipoComboItemStateChanged(evt);
            }
        });

        jLabel2.setText("Tipo de producto:");

        condicionCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        condicionCombo1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Tipo:");

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
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 2, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnExport, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnReport, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tipoCombo, 0, 128, Short.MAX_VALUE)
                            .addComponent(warehouseCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(condicionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(condicionCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                    .addComponent(condicionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(condicionCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
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
                
                if (warehouseCombo.getSelectedIndex()==0){
                    idWare=0;
                }else{
                    idWare=warehouses.get(warehouseCombo.getSelectedIndex()-1).getId();
                }
                
                if (tipoCombo.getSelectedIndex()==0)
                    idTipo=-1;
                else
                    idTipo=racks.get(tipoCombo.getSelectedIndex()-1).getId();
                
                if (tipoCombo.getSelectedIndex()==0)
                    idCon=-1;
                else
                    idCon=racks.get(tipoCombo.getSelectedIndex()-1).getId();
                
                listReport = palletApplication.queryByReport(idWare,idCon,idTipo,0);
                //System.out.println(racks.size());
                fillKardex();
                btnExport.setEnabled(true);
            }
        
    }//GEN-LAST:event_btnReportActionPerformed

    private void warehouseComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_warehouseComboItemStateChanged
        

    }//GEN-LAST:event_warehouseComboItemStateChanged

    private void btnExportMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportMousePressed
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
        } catch (IOException ex) {
            Logger.getLogger(AvailabilityReport.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(this, "Ocurrió un error al abrir el archivo",Strings.ERROR_KARDEX_TITLE,JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnExportMousePressed

    private void tipoComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_tipoComboItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoComboItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnReport;
    private javax.swing.JComboBox condicionCombo;
    private javax.swing.JComboBox condicionCombo1;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblKardex;
    private javax.swing.JComboBox tipoCombo;
    private javax.swing.JComboBox warehouseCombo;
    // End of variables declaration//GEN-END:variables
}
