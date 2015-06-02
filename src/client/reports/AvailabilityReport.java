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
import entity.Condicion;
import entity.Kardex;
import entity.Producto;
import entity.Rack;
import entity.Ubicacion;
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
    KardexApplication kardexApplication = InstanceFactory.Instance.getInstance("kardexApplicaiton", KardexApplication.class);
    public ArrayList<Almacen> warehouses;
    public ArrayList<Condicion> conditions;
    public ArrayList<Kardex> kardex;
    public ArrayList<Rack> racks;
    public ArrayList<Ubicacion> spots;
    private String [] warehousesNames;
    private String [] racksNames;
    JFileChooser fc = new JFileChooser();
    File file = null;
    /**
     * Creates new form KardexReport
     */
    public AvailabilityReport() {
        initComponents();
        initialize();
        fillWarehouses();
        condicionTxt.setText(EntityType.getCondition(warehouses.get(0).getCondicion().getId()).getNombre());
        warehouseCombo.setModel(new javax.swing.DefaultComboBoxModel(warehousesNames));
        fillRacksIni();
        rackCombo.setModel(new javax.swing.DefaultComboBoxModel(racksNames));
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
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnExport))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rackCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(warehouseCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(32, 32, 32)
                                        .addComponent(jLabel4)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(condicionTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnReport)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 8, Short.MAX_VALUE)))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnReport))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rackCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExport)
                .addContainerGap(15, Short.MAX_VALUE))
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
                int idCon;
                spots = new ArrayList<Ubicacion>();
                
                idWare=warehouses.get(warehouseCombo.getSelectedIndex()).getId();
                if (rackCombo.getSelectedIndex()==0)
                    idCon=0;
                else
                    idCon=racks.get(rackCombo.getSelectedIndex()-1).getId();                
                spots = spotApplication.queryByParameters(idWare,idCon);
                System.out.println(racks.size());
                fillKardex();
                btnExport.setEnabled(true);
            }
        
    }//GEN-LAST:event_btnReportActionPerformed

    private void warehouseComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_warehouseComboItemStateChanged
        
        boolean aux = evt.getStateChange() == ItemEvent.SELECTED;
        //if(evt.getStateChange() == ItemEvent.SELECTED)
        fillRacks(warehouses.get(warehouseCombo.getSelectedIndex()).getId());
        condicionTxt.setText(EntityType.getCondition(warehouses.get(warehouseCombo.getSelectedIndex()).getCondicion().getId()).getNombre());
        rackCombo.setModel(new javax.swing.DefaultComboBoxModel(racksNames));
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

    private void rackComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rackComboItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_rackComboItemStateChanged


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
