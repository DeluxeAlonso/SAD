/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.transportunit;

import application.transportunit.TransportUnitApplication;
import application.transportunittype.TransportUnitTypeApplication;
import entity.TipoUnidadTransporte;
import entity.UnidadTransporte;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import util.EntityState;
import util.EntityType;
import util.InstanceFactory;
import util.Strings;

/**
 *
 * @author LUIS
 */
public class TransportUnitView extends javax.swing.JInternalFrame implements MouseListener{
    
    TransportUnitApplication transportUnitApplication = InstanceFactory.Instance.getInstance("transportUnitApplication", TransportUnitApplication.class);
    TransportUnitTypeApplication transportUnitTypeApplication = InstanceFactory.Instance.getInstance("transportUnitTypeApplication", TransportUnitTypeApplication.class);
    Border errorBorder = BorderFactory.createLineBorder(Color.RED, 1);
    Border regularBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
    public static TransportUnitView transportUnitView;
    String error_message;
    /**
     * Creates new form TUForm
     */
    public TransportUnitView() {
        initComponents();
        transportUnitView = this;
        setupListeners();
        setupElements();
    }
    
    public void setupElements(){
        transportUnitApplication.refreshTransportUnits();
        fillCombos();
        refreshTable();
    }
    
    public void setupListeners() {
        addMouseListener(this);
        transportScrollPanel.addMouseListener(this);
        transportTable.addMouseListener(this);
    }
    
    public void fillCombos(){
        EntityType.TRANSPORT_TYPES = transportUnitTypeApplication.getAllTransportUnitTypes();
        EntityType.fillUnitTransportTypesNames();
        transportTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(EntityType.TRANSPORT_TYPE_NAMES));
        typeCreateCombo.setModel(new javax.swing.DefaultComboBoxModel(EntityType.TRANSPORT_TYPE_NAMES));
    }
    
    /*
     * Transport Unit Methods
     */  
    public Boolean saveTransportUnit(){
        UnidadTransporte transportUnit = new UnidadTransporte();
        transportUnit.setPlaca(plateCreateTxt.getText());
        transportUnit.setTransportista(transportistCreateTxt.getText());
        transportUnit.setEstado(1);
        transportUnit.setTipoUnidadTransporte(EntityType.TRANSPORT_TYPES.get(typeCreateCombo.getSelectedIndex()-1));
        return transportUnitApplication.createTransportUnit(transportUnit);
    }
    
    public Boolean editTransportUnit(UnidadTransporte updatedTransportUnit) {
        updatedTransportUnit.setPlaca(plateCreateTxt.getText());
        updatedTransportUnit.setTransportista(transportistCreateTxt.getText());
        updatedTransportUnit.setEstado(1);
        updatedTransportUnit.setTipoUnidadTransporte(EntityType.TRANSPORT_TYPES.get(typeCreateCombo.getSelectedIndex()-1));
        return transportUnitApplication.updateTransportUnit(updatedTransportUnit);
    }
    
    public void deleteTransportUnit(){
        EntityType.TRANSPORT_UNITS.get(transportTable.getSelectedRow()).setEstado(0);
        if(transportUnitApplication.updateTransportUnit(EntityType.TRANSPORT_UNITS.get(transportTable.getSelectedRow()))){
            transportUnitApplication.refreshTransportUnits();
            refreshTable();
            JOptionPane.showMessageDialog(this, Strings.MESSAGE_DELETE_TRANSPORT_UNIT,
                    Strings.MESSAGE_DELETE_TRANSPORT_UNIT_TITLE,JOptionPane.INFORMATION_MESSAGE);
        }
    }
   
    /*
     * Buttons Handling Methods
     */ 
    public void enableButtons(){
        editBtn.setEnabled(true);
        deleteBtn.setEnabled(true);  
    }
    
    public void disableButtons(){
        editBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
    }
    
    /*
     * Input Fields Methods
     */ 
    public void fillCreateFields(UnidadTransporte transportUnit){
        plateCreateTxt.setText(transportUnit.getPlaca());
        transportistCreateTxt.setText(transportUnit.getTransportista());
        typeCreateCombo.setSelectedItem(transportUnit.getTipoUnidadTransporte().getDescripcion());
    }
    
    public void clearFields(){
        plateCreateTxt.setText("");
        transportistCreateTxt.setText("");
        typeCreateCombo.setSelectedIndex(0);
    }
    
    public void clearNewTransportUnitFielBorders(){
        plateCreateTxt.setBorder(regularBorder);
        transportistCreateTxt.setBorder(regularBorder);
    }
    
    public Boolean validFields(){                                         
        clearNewTransportUnitFielBorders();
        error_message = "Errores:\n";
        JOptionPane.setDefaultLocale(new Locale("es", "ES"));
        boolean valid = true;
        if(plateCreateTxt.getText().isEmpty()){
            error_message += Strings.ERROR_PLATE_REQUIRED+"\n";
            plateCreateTxt.setBorder(errorBorder);
            valid = false;
        }
        else if(plateCreateTxt.getText().length() != 6){
            error_message += Strings.ERROR_PLATE_NOT_6+"\n";
            plateCreateTxt.setBorder(errorBorder);
            valid = false;
        }
        if(transportistCreateTxt.getText().isEmpty()){
            error_message += Strings.ERROR_TRANSPORTIST_REQUIRED+"\n";
            transportistCreateTxt.setBorder(errorBorder);
            valid = false;
        }
        else if(transportistCreateTxt.getText().length() > 60){
            error_message += Strings.ERROR_TRANSPORTIST_MORE_60+"\n";
            transportistCreateTxt.setBorder(errorBorder);
            valid = false; 
        }
        if(typeCreateCombo.getSelectedIndex() == 0){
            error_message += Strings.ERROR_TRANSPORT_TYPE_REQUIRED+"\n";
            valid = false; 
        }
        return valid;
    }
    
    /*
     * File Methods
     */ 
    public void loadFile(String filename){
        Boolean response = transportUnitApplication.loadTransportUnit(filename);
        if(response){
            JOptionPane.showMessageDialog(this, Strings.MESSAGE_FILE_TRANSPORT_UNIT,
                    Strings.MESSAGE_FILE_TRANSPORT_UNIT_TITLE,JOptionPane.INFORMATION_MESSAGE);
            transportUnitApplication.refreshTransportUnits();
            refreshTable();
        }
        else
            JOptionPane.showMessageDialog(this, Strings.MESSAGE_FILE_ERROR_TRANSPORT_UNIT,
                    Strings.MESSAGE_FILE_TRANSPORT_UNIT_TITLE,JOptionPane.INFORMATION_MESSAGE);
    }
    
    /*
     * Table Methods
     */ 
    public void refreshTable(){
        ArrayList<String> cols = new ArrayList<>();
        for (int i = 0; i<transportTable.getColumnCount(); i++)
            cols.add(transportTable.getColumnName(i));
        DefaultTableModel tableModel = new DefaultTableModel(cols.toArray(), 0);
        transportTable.setModel(tableModel);
        EntityType.TRANSPORT_UNITS.stream().forEach((_transportUnit) -> {
            Object[] row = {_transportUnit.getId(), _transportUnit.getPlaca(),
                _transportUnit.getTransportista(), EntityState.getTransportUnitsState()[_transportUnit.getEstado()],
                _transportUnit.getTipoUnidadTransporte().getDescripcion(),""};
            tableModel.addRow(row);
        });
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        transportScrollPanel = new javax.swing.JScrollPane();
        transportTable = new javax.swing.JTable();
        deleteBtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        fileTxt = new javax.swing.JTextField();
        loadBtn = new javax.swing.JButton();
        fileBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        plateTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        transportTypeCombo = new javax.swing.JComboBox();
        searchBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        saveBtn = new javax.swing.JButton();
        plateCreateTxt = new javax.swing.JTextField();
        transportistCreateTxt = new javax.swing.JTextField();
        typeCreateCombo = new javax.swing.JComboBox();
        editBtn = new javax.swing.JButton();

        setClosable(true);
        setTitle("Unidad de Tranporte");

        transportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Placa", "Conductor", "Tipo", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        transportScrollPanel.setViewportView(transportTable);
        if (transportTable.getColumnModel().getColumnCount() > 0) {
            transportTable.getColumnModel().getColumn(0).setResizable(false);
            transportTable.getColumnModel().getColumn(1).setResizable(false);
            transportTable.getColumnModel().getColumn(2).setResizable(false);
            transportTable.getColumnModel().getColumn(3).setResizable(false);
            transportTable.getColumnModel().getColumn(4).setResizable(false);
        }

        deleteBtn.setText("Eliminar");
        deleteBtn.setEnabled(false);
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Carga Masiva"));

        jLabel3.setText("Ingresar Unidades de Transporte desde un archivo:");

        fileTxt.setEditable(false);
        fileTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileTxtActionPerformed(evt);
            }
        });

        loadBtn.setText("Cargar");
        loadBtn.setEnabled(false);
        loadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadBtnActionPerformed(evt);
            }
        });

        fileBtn.setText("...");
        fileBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        fileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(fileTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fileBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loadBtn)
                    .addComponent(fileBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros"));

        jLabel1.setText("Placa:");

        jLabel2.setText("Tipo:");

        transportTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        searchBtn.setText("Buscar");
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(searchBtn)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(plateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(transportTypeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(plateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(transportTypeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchBtn))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Nueva Unidad de Transporte"));

        jLabel4.setText("*Placa:");

        jLabel5.setText("*Transportista:");

        jLabel6.setText("*Tipo:");

        saveBtn.setText("Guardar");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        typeCreateCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        typeCreateCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeCreateComboActionPerformed(evt);
            }
        });

        editBtn.setText("Editar");
        editBtn.setEnabled(false);
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(typeCreateCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(plateCreateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(transportistCreateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(plateCreateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(transportistCreateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(typeCreateCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveBtn)
                    .addComponent(editBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(deleteBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(transportScrollPanel))))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(transportScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteBtn)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        // TODO add your handling code here:
        int response = JOptionPane.showConfirmDialog(this, Strings.MESSAGE_CONFIRM_DELETE_TRANSPORT_UNIT,Strings.MESSAGE_DELETE_TRANSPORT_UNIT_TITLE,JOptionPane.WARNING_MESSAGE);
        if(JOptionPane.OK_OPTION == response){
            deleteTransportUnit();
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void fileTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileTxtActionPerformed

    private void loadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadBtnActionPerformed
        loadFile(fileTxt.getText());
    }//GEN-LAST:event_loadBtnActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        // TODO add your handling code here:
        String plate = plateTxt.getText();
        TipoUnidadTransporte type = null;
        Integer index = transportTypeCombo.getSelectedIndex() - 1;
        if (index >= 0)
            type = EntityType.TRANSPORT_TYPES.get(index);
        EntityType.TRANSPORT_UNITS = transportUnitApplication.searchTransportUnits(plate, type);
        refreshTable();
    }//GEN-LAST:event_searchBtnActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        if(validFields()){   
            if(saveTransportUnit()){
                transportUnitApplication.refreshTransportUnits();
                JOptionPane.showMessageDialog(this, Strings.MESSAGE_NEW_TRANSPORT_UNIT_CREATED,Strings.MESSAGE_TRANSPORT_UNIT_TITLE,JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                refreshTable();
            }
            else{
                JOptionPane.showMessageDialog(this, Strings.ERROR_MESSAGE_TRANSPORT_UNIT,Strings.MESSAGE_TRANSPORT_UNIT_TITLE,JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else{
            JOptionPane.showMessageDialog(this, error_message,Strings.ERROR_NEW_LOCAL_TITLE,JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_saveBtnActionPerformed

    private void typeCreateComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeCreateComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_typeCreateComboActionPerformed
    
    private void fileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileBtnActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Seleccione un archivo");
        fc.showOpenDialog(this);
        File file = fc.getSelectedFile();
        if (!fc.getSelectedFile().getName().endsWith(".csv")) {
          JOptionPane.showMessageDialog(this, "El archivo seleccionado no es un archivo CSV.");
        }
        else{
            fileTxt.setText(file.getAbsolutePath());
            loadBtn.setEnabled(true);
        }
    }//GEN-LAST:event_fileBtnActionPerformed

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        if(validFields()){
            if(editTransportUnit(EntityType.TRANSPORT_UNITS.get(transportTable.getSelectedRow()))){
                transportUnitApplication.refreshTransportUnits();
                JOptionPane.showMessageDialog(this, Strings.MESSAGE_NEW_TRANSPORT_UNIT_CREATED,Strings.MESSAGE_TRANSPORT_UNIT_TITLE,JOptionPane.INFORMATION_MESSAGE);
                disableButtons();
                refreshTable();
            }
            else
                JOptionPane.showMessageDialog(this, Strings.ERROR_MESSAGE_TRANSPORT_UNIT,Strings.MESSAGE_TRANSPORT_UNIT_TITLE,JOptionPane.INFORMATION_MESSAGE);
        }
        else
            JOptionPane.showMessageDialog(this, error_message,Strings.ERROR_NEW_LOCAL_TITLE,JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_editBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton editBtn;
    private javax.swing.JButton fileBtn;
    private javax.swing.JTextField fileTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton loadBtn;
    private javax.swing.JTextField plateCreateTxt;
    private javax.swing.JTextField plateTxt;
    private javax.swing.JButton saveBtn;
    private javax.swing.JButton searchBtn;
    private javax.swing.JScrollPane transportScrollPanel;
    private javax.swing.JTable transportTable;
    private javax.swing.JComboBox transportTypeCombo;
    private javax.swing.JTextField transportistCreateTxt;
    private javax.swing.JComboBox typeCreateCombo;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(transportTable.getSelectedRow() != -1){
            clearNewTransportUnitFielBorders();
            enableButtons();
            fillCreateFields(EntityType.TRANSPORT_UNITS.get(transportTable.getSelectedRow()));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
