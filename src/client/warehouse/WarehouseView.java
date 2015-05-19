/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.warehouse;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author LUIS
 */
public class WarehouseView extends javax.swing.JInternalFrame {

    /**
     * Creates new form WarehouseForm
     */
    public WarehouseView() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        newBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        emailTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        profileCombo = new javax.swing.JComboBox();
        searchBtn = new javax.swing.JButton();
        WarehouseGrid = new javax.swing.JScrollPane();
        usersGrid = new javax.swing.JTable();

        setClosable(true);

        newBtn.setText("Nuevo");
        newBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBtnActionPerformed(evt);
            }
        });

        editBtn.setText("Editar");

        deleteBtn.setText("Eliminar");

        jLabel1.setText("Id:");

        jLabel2.setText("Tipo:");

        profileCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        searchBtn.setText("Buscar");

        usersGrid.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Descripcion", "Capacidad", "Tipo", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        WarehouseGrid.setViewportView(usersGrid);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(profileCombo, 0, 193, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(searchBtn))
                    .addComponent(WarehouseGrid, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(newBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteBtn)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBtn)
                    .addComponent(jLabel2)
                    .addComponent(profileCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newBtn)
                    .addComponent(editBtn)
                    .addComponent(deleteBtn))
                .addGap(27, 27, 27)
                .addComponent(WarehouseGrid, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtnActionPerformed
        // TODO add your handling code here:

        NewWarehouseView newWarehouse=new NewWarehouseView((JFrame)SwingUtilities.getWindowAncestor(this),true);
        newWarehouse.setVisible(true);
    }//GEN-LAST:event_newBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane WarehouseGrid;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton editBtn;
    private javax.swing.JTextField emailTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton newBtn;
    private javax.swing.JComboBox profileCombo;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTable usersGrid;
    // End of variables declaration//GEN-END:variables
}