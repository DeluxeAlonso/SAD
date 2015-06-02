/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.delivery;

import algorithm.AlgorithmExecution;
import algorithm.AlgorithmReturnValues;
import algorithm.Solution;
import application.order.OrderApplication;
import application.pallet.PalletApplication;
import client.delivery.GoogleMaps;
import client.base.BaseView;
import entity.Despacho;
import entity.GuiaRemision;
import entity.Pallet;
import entity.PedidoParcial;
import entity.PedidoParcialXProducto;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import util.EntityState;
import util.Icons;
import util.Strings;

/**
 *
 * @author alulab14
 */
public class DeliveryView extends BaseView {
    private Solution solution = null;
    private AlgorithmExecution algorithmExecution = null;
    OrderApplication orderApplication = new OrderApplication();
    PalletApplication palletApplication = new PalletApplication();
    
    /**
     * Creates new form DispatchView
     */
    public DeliveryView() {        
        initComponents();
        super.initialize();
        Icons.setButton(btnProcess, Icons.ICONOS.APPLY.ordinal());
        Icons.setButton(btnExecuteAlgorithm, Icons.ICONOS.PLAY.ordinal());
        Icons.setButton(btnViewSolution, Icons.ICONOS.DELIVERY.ordinal());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnProcess = new javax.swing.JButton();
        btnExecuteAlgorithm = new javax.swing.JButton();
        btnViewSolution = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtHours = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtMinutes = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtResult = new javax.swing.JTextArea();

        setClosable(true);
        setTitle(Strings.BAD_PARAMETERS_TITLE);

        btnProcess.setText("Escoger Solución");
        btnProcess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcessActionPerformed(evt);
            }
        });

        btnExecuteAlgorithm.setText("Ejecutar Algoritmo");
        btnExecuteAlgorithm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExecuteAlgorithmActionPerformed(evt);
            }
        });

        btnViewSolution.setText("Ver Solución");
        btnViewSolution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewSolutionActionPerformed(evt);
            }
        });

        jLabel1.setText("Tiempo máximo por ruta:");

        jLabel2.setText("horas");

        jLabel3.setText("minutos");

        txtResult.setColumns(20);
        txtResult.setRows(5);
        jScrollPane1.setViewportView(txtResult);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnProcess)
                        .addGap(18, 18, 18)
                        .addComponent(btnExecuteAlgorithm)
                        .addGap(18, 18, 18)
                        .addComponent(btnViewSolution))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtHours, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtMinutes, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtHours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtMinutes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProcess)
                    .addComponent(btnExecuteAlgorithm)
                    .addComponent(btnViewSolution))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnProcessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcessActionPerformed
        if(solution!=null && algorithmExecution!=null){
            AlgorithmReturnValues returnValues = algorithmExecution.processOrders(solution);
            assignRemissionGuides(returnValues.getDespachos());
            if(createPartialOrders(returnValues.getAcceptedOrders(), returnValues.getRejectedOrders()))
                JOptionPane.showMessageDialog(this, Strings.MESSAGE_DELETE_ORDER,
                    Strings.MESSAGE_DELETE_ORDER_TITLE,JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, Strings.MESSAGE_DELETE_ORDER,
                    Strings.MESSAGE_DELETE_ORDER_TITLE,JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnProcessActionPerformed

    private void btnViewSolutionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewSolutionActionPerformed
        if(solution!=null){
            GoogleMaps googleMaps = new GoogleMaps(solution);
        }
    }//GEN-LAST:event_btnViewSolutionActionPerformed

    private void btnExecuteAlgorithmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExecuteAlgorithmActionPerformed
        algorithmExecution = new AlgorithmExecution();
        //solution = algorithmExecution.start(60);
        
        try{
            double hours, minutes;
            if(txtHours.getText().isEmpty()) hours = 0;
            if(txtMinutes.getText().isEmpty()) minutes = 0;
            hours = Double.parseDouble(txtHours.getText());
            minutes = Double.parseDouble(txtMinutes.getText());
            solution = algorithmExecution.start(60);
            StringBuffer buf = algorithmExecution.displayRoutes(solution);
            txtResult.setText(buf.toString());
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, Strings.BAD_PARAMETERS,
                    Strings.BAD_PARAMETERS_TITLE,JOptionPane.INFORMATION_MESSAGE);
        }        
    }//GEN-LAST:event_btnExecuteAlgorithmActionPerformed

    public void assignRemissionGuides(ArrayList<Despacho> deliveries){
        ArrayList<PedidoParcial> acceptedOrders = new ArrayList<>();
        ArrayList<GuiaRemision> remissionGuides = new ArrayList<>();
        for(int i=0;i<deliveries.size();i++)
            for (Iterator<GuiaRemision> remissionGuide = deliveries.get(i).getGuiaRemisions().iterator(); remissionGuide.hasNext(); ) {
                GuiaRemision g = remissionGuide.next();
                for(Iterator<PedidoParcial> partialOrder = g.getPedidoParcials().iterator(); partialOrder.hasNext();){
                    
                    PedidoParcial p = partialOrder.next();
                    acceptedOrders.add(p);
                }
                remissionGuides.add(g);
            }
        orderApplication.CreateRemissionGuides(acceptedOrders, remissionGuides); 
    }
    
    public Boolean createPartialOrders(ArrayList<PedidoParcial>acceptedOrders, ArrayList<PedidoParcial>rejectedOrders){
        ArrayList<PedidoParcialXProducto> acceptedOrdersXProd = getAcceptedPartialOrderDetail(acceptedOrders);
        ArrayList<PedidoParcialXProducto> rejectedOrdersXProd = getRejectedPartialOrderDetail(rejectedOrders);
        
        return orderApplication.createPartialOrders(acceptedOrders, acceptedOrdersXProd, rejectedOrders, rejectedOrdersXProd);
    }
    
    public ArrayList<PedidoParcialXProducto> getAcceptedPartialOrderDetail(ArrayList<PedidoParcial> orders){
        ArrayList<PedidoParcialXProducto> orderDetails = new ArrayList<>();
        for(int i=0;i<orders.size();i++)
            for(Iterator<PedidoParcialXProducto> partialOrderDetail = orders.get(i).getPedidoParcialXProductos().iterator(); partialOrderDetail.hasNext();){
                    PedidoParcialXProducto p = partialOrderDetail.next();
                    ArrayList<Pallet> pallets = palletApplication.getAvailablePalletsByProductId(p.getProducto().getId());
                    ArrayList<Pallet> selectedPallets = new ArrayList<>();
                    for(int j=0;j<p.getCantidad();j++){
                        Pallet selectedPallet;
                        selectedPallet = pallets.get(j);
                        selectedPallet.setEstado(EntityState.Pallets.DESPACHADO.ordinal());                  
                        selectedPallet.setPedidoParcial(p.getPedidoParcial());
                        selectedPallets.add(selectedPallet);
                    }
                    palletApplication.updatePallets(selectedPallets);
                    orderDetails.add(p);
            }
        return orderDetails;
    }
    
    public ArrayList<PedidoParcialXProducto> getRejectedPartialOrderDetail(ArrayList<PedidoParcial> orders){
        ArrayList<PedidoParcialXProducto> orderDetails = new ArrayList<>();
        for(int i=0;i<orders.size();i++)
            for(Iterator<PedidoParcialXProducto> partialOrderDetail = orders.get(i).getPedidoParcialXProductos().iterator(); partialOrderDetail.hasNext();){
                    PedidoParcialXProducto p = partialOrderDetail.next();
                    orderDetails.add(p);
            }
        return orderDetails;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExecuteAlgorithm;
    private javax.swing.JButton btnProcess;
    private javax.swing.JButton btnViewSolution;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtHours;
    private javax.swing.JTextField txtMinutes;
    private javax.swing.JTextArea txtResult;
    // End of variables declaration//GEN-END:variables
}
