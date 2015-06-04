/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.warehouseControlCheck;

import application.pallet.PalletApplication;
import application.spot.SpotApplication;
import application.warehouse.WarehouseApplication;
import client.base.BaseView;
import entity.Almacen;
import entity.Ubicacion;
import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
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
    public static WarehouseControlCheckView warehouseControlCheckView;
    public ArrayList<Almacen> warehousesFrom;
    public ArrayList<Ubicacion> spots;
    //private JDesktopPane mainPanel;
    JFileChooser fc = new JFileChooser();
    File file = null;
    
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
        }
    }
    
    public void clearSpotsTable(){
        DefaultTableModel model = (DefaultTableModel) tblControlCheck.getModel();
        model.setRowCount(0);
    }
    
    public void fillSpotsTable(int warehouseId){
        clearSpotsTable();
        DefaultTableModel model = (DefaultTableModel) tblControlCheck.getModel();
        spots = (ArrayList<Ubicacion>) spotApplication.querySpotsByWarehouse(warehouseId);
        if(spots.size()>0){
            for(Ubicacion spot : spots){
                model.addRow(new Object[]{
                    spot.getRack().getId(),
                    spot.getLado(),
                    spot.getFila(),
                    spot.getColumna(),
                    EntityState.getSpotStateLiteral(spot.getEstado())
                });
            }
        }
    }
    
    private void loadFromFile(String csvFile){
        BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
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
                model.setValueAt(line_split[4], i, 5);
                if(line_split[4].equals(model.getValueAt(i, 4).toString())){
                    model.setValueAt("-", i, 6);
                }else{
                    if(line_split[4].equals("Ocupado")){
                        // En sistema libre y en fisico ocupado, debo insertar el pallet
                        
                    }else{
                        // En sistema ocupado y en fisico libre, debo eliminar el pallet
                        // Eliminar el pallet de la ubicacion
                        palletApplication.deletePalletBySpot(spots.get(i).getId());
                        // Actualizo el estado de la ubicacion a libre
                        spotApplication.updateSpotOccupancy(spots.get(i).getId(), EntityState.Spots.LIBRE.ordinal());
                        // Registro el kardex
                        
                    }
                }
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

        jLabel1 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        comboWarehouseFrom = new javax.swing.JComboBox();
        btnFileUpload = new javax.swing.JButton();
        fileTextField = new javax.swing.JTextField();
        btnFile = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblControlCheck = new javax.swing.JTable();

        setClosable(true);
        setTitle("Ajuste de inventario");

        jLabel1.setText("Responsable:");

        lblName.setText("Juan Carlos Pérez Salas");

        jLabel2.setText("Fecha:");

        lblDate.setText("18/05/2015");

        jLabel5.setText("Almacén");

        comboWarehouseFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Almacén 1", "Almacén 2", "Almacén 3", "Almacén 4" }));
        comboWarehouseFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboWarehouseFromItemStateChanged(evt);
            }
        });

        btnFileUpload.setText("Cargar");
        btnFileUpload.setEnabled(false);
        btnFileUpload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnFileUploadMousePressed(evt);
            }
        });
        btnFileUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFileUploadActionPerformed(evt);
            }
        });

        fileTextField.setEditable(false);

        btnFile.setText("...");
        btnFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFileActionPerformed(evt);
            }
        });

        tblControlCheck.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Rack", "Lado", "Fila", "Columna", "En sistema", "En físico", "Tipo de ajuste"
            }
        ));
        jScrollPane1.setViewportView(tblControlCheck);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDate)
                                    .addComponent(lblName)
                                    .addComponent(comboWarehouseFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(fileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFile, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFileUpload)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFileUpload)
                    .addComponent(fileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDate)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(comboWarehouseFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFileUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFileUploadActionPerformed
        
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
        if(evt.getStateChange() == ItemEvent.SELECTED){
            fillSpotsTable(warehousesFrom.get(comboWarehouseFrom.getSelectedIndex()).getId());
        }
    }//GEN-LAST:event_comboWarehouseFromItemStateChanged

    private void btnFileUploadMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFileUploadMousePressed
        loadFromFile(file.getAbsolutePath());
        file = null;
        btnFileUpload.setEnabled(false);
        fileTextField.setText("");
    }//GEN-LAST:event_btnFileUploadMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFile;
    private javax.swing.JButton btnFileUpload;
    private javax.swing.JComboBox comboWarehouseFrom;
    private javax.swing.JTextField fileTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblName;
    private javax.swing.JTable tblControlCheck;
    // End of variables declaration//GEN-END:variables
}
