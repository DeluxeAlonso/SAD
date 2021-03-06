/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.warehouse;


import application.condition.ConditionApplication;
import application.rack.RackApplication;
import application.spot.SpotApplication;
import application.warehouse.WarehouseApplication;
import client.base.BaseDialogView;
import entity.Almacen;
import entity.Rack;
import entity.Ubicacion;
import java.awt.Color;
import java.awt.Image;
import java.net.URL;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import util.Constants;
import util.EntityState;
import util.EntityType;
import util.Icons;
import util.InstanceFactory;
import util.Strings;

/**
 *
 * @author LUIS
 */
public class NewWarehouseView extends BaseDialogView {
    WarehouseApplication warehouseApplication=InstanceFactory.Instance.getInstance("warehouseApplication", WarehouseApplication.class);
    ConditionApplication conditionApplication=InstanceFactory.Instance.getInstance("conditionApplication", ConditionApplication.class);
    RackApplication rackApplication=InstanceFactory.Instance.getInstance("rackApplication", RackApplication.class);
    SpotApplication spotApplication=InstanceFactory.Instance.getInstance("spotApplication", SpotApplication.class);
    Image img;
    Image img2;
    Border errorBorder = BorderFactory.createLineBorder(Color.RED, 1);
    Border regularBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
    /**
     * Creates new form NewWarehouse
     */
    public NewWarehouseView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setTitle("Nuevo Almacen");
        initialize();
        this.condicionCombo.setModel(new javax.swing.DefaultComboBoxModel(EntityType.CONDITIONS_NAMES));
        Icons.setButton(saveTxt, Icons.ICONOS.SAVE.ordinal());
        Icons.setButton(cancelBtn, Icons.ICONOS.CANCEL.ordinal());
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
        condicionCombo = new javax.swing.JComboBox();
        saveTxt = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        descripcionTxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        AreaTxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        capacityTxt = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        racksTxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        nFilTxt = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        nColTxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nuevo Almacen");

        jLabel3.setText("*Condicion:");

        condicionCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        saveTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveTxtActionPerformed(evt);
            }
        });

        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("*Descripcion:");

        jLabel4.setText("Racks");

        jLabel5.setText("*Area (m2):");

        jLabel6.setText("*Capacidad:");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Racks"));

        jLabel7.setText("*Cantidad:");

        racksTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                racksTxtActionPerformed(evt);
            }
        });

        jLabel8.setText("Dimensiones:");

        jLabel9.setText("*Filas:");

        jLabel10.setText("*Columnas:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 32, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nFilTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nColTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(racksTxt)
                        .addGap(159, 159, 159))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(racksTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(nFilTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(nColTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(23, 23, 23)
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(descripcionTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(25, 25, 25)
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(AreaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(26, 26, 26)
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(capacityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel4)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(condicionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(saveTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(descripcionTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(AreaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(capacityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(condicionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveTxtActionPerformed
        // TODO add your handling code here:
        clearBorders();
        /*
        Almacen al = new Almacen();
        Calendar cal = Calendar.getInstance();
        al = warehouseApplication.queryById(7);
        
        Rack r = new Rack();
        r.setAlmacen(al);
        r.setEstado(1);
        r.setFechaRegistro(cal.getTime());
        r.setNumCol(1);
        r.setNumFil(1);
        r.setUbicLibres(100);
        al.getRacks().add(r);
        warehouseApplication.insert(al);
        */
        
        if (!hasErrors()){
        Almacen al = new Almacen();
        Calendar cal = Calendar.getInstance();
        int capa = Integer.parseInt(this.capacityTxt.getText());
        if (capa!=0)
        {
            al.setCapacidad(capa);
        }
        int area = Integer.parseInt(this.AreaTxt.getText());
        if (area!=0)
        {
            al.setArea(area);
        }
        int uLibres = Integer.parseInt(this.racksTxt.getText());
        int fil = Integer.parseInt(this.nFilTxt.getText());
        int col = Integer.parseInt(this.nColTxt.getText());
        
        al.setCondicion(conditionApplication.getConditionInstance(condicionCombo.getSelectedItem().toString()));
        al.setDescripcion(this.descripcionTxt.getText());
        al.setEstado(EntityState.Warehouses.ACTIVO.ordinal());
        al.setFechaRegistro(cal.getTime());
        al.setKardexes(null);
        al.setUbicLibres(fil*col*uLibres*2);
        al.setNumFilas(fil);
        al.setNumColumnas(col);
        //warehouseApplication.insert(al);
        
        for (int i=0;i<uLibres;i++){
            Rack r = new Rack();
            r.setEstado(EntityState.Racks.ACTIVO.ordinal());
            r.setFechaRegistro(cal.getTime());
            r.setAlmacen(al);
            r.setNumCol(col);
            r.setNumFil(fil);
            r.setUbicLibres(col*fil*2);
            al.getRacks().add(r);
            
            
            for (int j=0;j<col;j++){
                for (int k=0;k<fil;k++){
                    Ubicacion u1 = new Ubicacion();
                    Ubicacion u2 = new Ubicacion();
                    u1.setRack(r);
                    u1.setEstado(EntityState.Spots.LIBRE.ordinal());
                    u1.setColumna(j+1);
                    u1.setFila(k+1);
                    u1.setLado("A");
                    u2.setRack(r);
                    u2.setEstado(EntityState.Spots.LIBRE.ordinal());
                    u2.setColumna(j+1);
                    u2.setFila(k+1);
                    u2.setLado("B");
                    r.getUbicacions().add(u1);
                    r.getUbicacions().add(u2);
                }
            }
        }
        warehouseApplication.insert(al);
        JOptionPane.showMessageDialog(this, Strings.MESSAGE_WAREHOUSE_CREATED);
        clearFields();
        clearBorders();
        }
        
    }//GEN-LAST:event_saveTxtActionPerformed
    
    public void clearBorders()
    {
        AreaTxt.setBorder(regularBorder);
        capacityTxt.setBorder(regularBorder);
        descripcionTxt.setBorder(regularBorder);
        racksTxt.setBorder(regularBorder);
        condicionCombo.setBorder(regularBorder);
        nFilTxt.setBorder(regularBorder);
        nColTxt.setBorder(regularBorder);
    }
    
    
    
    public void clearFields(){
        AreaTxt.setText("");
        capacityTxt.setText("");
        descripcionTxt.setText("");
        racksTxt.setText("");      
        condicionCombo.setSelectedIndex(0);
        nFilTxt.setText("");
        nColTxt.setText("");
    }
    
        public boolean isDouble( String str ){
        try{
            Double.parseDouble( str );
            return true;
        }catch( Exception e ){
            return false;
        }
    }
    
    public boolean isInteger( String str ){
        try{
            Integer.parseInt(str);
            return true;
        }catch( Exception e ){
            return false;
        }
    }
    
    
    private boolean hasErrors(){
        boolean errorFlag=false;
        String error_message = "Errores:\n";
        if (descripcionTxt.getText().isEmpty()){
            error_message += Strings.ERROR_DESC_WAREHOUSE_REQUIRED+"\n";
            descripcionTxt.setBorder(errorBorder);
            errorFlag = true;
        }
        if (AreaTxt.getText().isEmpty()){
            error_message += Strings.ERROR_AREA_WAREHOUSE_REQUIRED+"\n";
            AreaTxt.setBorder(errorBorder);
            errorFlag = true;
        } else if (!isInteger(AreaTxt.getText())){
            error_message += "El area debe ser un numero entero."+"\n";
            AreaTxt.setBorder(errorBorder);
            errorFlag = true;
        } else if (isInteger(AreaTxt.getText())){
            int areaA = Integer.parseInt(AreaTxt.getText());
            if (areaA>10000){
                error_message += "El area de un almacen debe ser 10,000 o menor."+"\n";
                AreaTxt.setBorder(errorBorder);
                errorFlag = true;
            }else if (areaA<100){
                error_message += "El area de un almacen debe ser 100 o mayor."+"\n";
                AreaTxt.setBorder(errorBorder);
                errorFlag = true;
            }
        }
        if (capacityTxt.getText().isEmpty()){
            error_message += Strings.ERROR_CAPACITY_WAREHOUSE_REQUIRED+"\n";
            capacityTxt.setBorder(errorBorder);
            errorFlag = true;
        } else if (!isInteger(capacityTxt.getText())){
            error_message += Strings.ERROR_CAPACITY_WAREHOUSE_INT+"\n";
            capacityTxt.setBorder(errorBorder);
            errorFlag = true;
        }else {
            int  capA=Integer.parseInt(capacityTxt.getText());
            if (capA > 50){
                error_message += "La capacidad de un almacen no debe ser mayor a 50."+"\n";
                capacityTxt.setBorder(errorBorder);
                errorFlag = true;                
            }else 
                if (capA < 1){
                error_message += "La capacidad de un almacen debe ser mayor que 0 racks."+"\n";
                capacityTxt.setBorder(errorBorder);
                errorFlag = true;                
            }
        }
        if (racksTxt.getText().isEmpty()){
            error_message += Strings.ERROR_RACKS_WAREHOUSE_REQUIRED+"\n";
            racksTxt.setBorder(errorBorder);
            errorFlag = true;
        } else if (!isInteger(racksTxt.getText())){
            error_message += Strings.ERROR_RACKS_WAREHOUSE_INT+"\n";
            racksTxt.setBorder(errorBorder);
            errorFlag = true;
        } else if (isInteger(racksTxt.getText())){
            int rackA =Integer.parseInt(racksTxt.getText());
            if (rackA > 50){
                error_message += "El numero de racks debe ser menor o igual que 50."+"\n";
                racksTxt.setBorder(errorBorder);
                errorFlag = true;                
            }else if (rackA < 1){
                error_message += "El numero de racks debe ser mayor o igual a 1."+"\n";
                racksTxt.setBorder(errorBorder);
                errorFlag = true;                
            }
                
        }
        
        
        if (nFilTxt.getText().isEmpty()){
            error_message += Strings.ERROR_RACKS_FIL_REQUIRED+"\n";
            nFilTxt.setBorder(errorBorder);
            errorFlag = true;
        } else if (!isInteger(nFilTxt.getText())){
            error_message += Strings.ERROR_RACKS_FIL_INT+"\n";
            nFilTxt.setBorder(errorBorder);
            errorFlag = true;
        }else if (isInteger(nFilTxt.getText())){
            int filA =Integer.parseInt(nFilTxt.getText());
            if (filA > 15){
                error_message += "El numero de filas debe ser menor que 15."+"\n";
                nFilTxt.setBorder(errorBorder);
                errorFlag = true;                
            }else if (filA < 1){
                error_message += "El numero de filas debe ser mayor que 0."+"\n";
                nFilTxt.setBorder(errorBorder);
                errorFlag = true;                
            }
        }
        if (nColTxt.getText().isEmpty()){
            error_message += Strings.ERROR_RACKS_COL_REQUIRED+"\n";
            nColTxt.setBorder(errorBorder);
            errorFlag = true;
        } else if (!isInteger(nColTxt.getText())){
            error_message += Strings.ERROR_RACKS_COL_INT+"\n";
            nColTxt.setBorder(errorBorder);
            errorFlag = true;
        }else if (isInteger(nColTxt.getText())){
            int colA =Integer.parseInt(nColTxt.getText());
            if (colA > 20){
                error_message += "El numero de columnas debe ser menor que 20"+"\n";
                nColTxt.setBorder(errorBorder);
                errorFlag = true;                
            } else if (colA < 1){
                error_message += "El numero de columnas debe ser mayor que 0"+"\n";
                nColTxt.setBorder(errorBorder);
                errorFlag = true;                
            }
        }
        if (condicionCombo.getSelectedIndex()==0){
            error_message += Strings.ERROR_CONDICION_WAREHOUSE_REQUIRED+"\n";
            condicionCombo.setBorder(errorBorder);
            errorFlag = true;
        }
        if (errorFlag==false){
            int capa = Integer.parseInt(capacityTxt.getText());
            int rack = Integer.parseInt(racksTxt.getText());
                    
            if (rack>capa){
                error_message += "El numero de racks a crear debe ser menor o igual que la capacidad."+"\n";
                racksTxt.setBorder(errorBorder);
                errorFlag = true;        
            }
        }
        if (errorFlag==true)
        JOptionPane.showMessageDialog(this, error_message,"Mensaje de insercion de almacen",JOptionPane.WARNING_MESSAGE);
        
        return errorFlag;
    }
    
    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed



    private void racksTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_racksTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_racksTxtActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AreaTxt;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JTextField capacityTxt;
    private javax.swing.JComboBox condicionCombo;
    private javax.swing.JTextField descripcionTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField nColTxt;
    private javax.swing.JTextField nFilTxt;
    private javax.swing.JTextField racksTxt;
    private javax.swing.JButton saveTxt;
    // End of variables declaration//GEN-END:variables
}
