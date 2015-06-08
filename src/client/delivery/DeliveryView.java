/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.delivery;

import algorithm.AlgorithmExecution;
import algorithm.AlgorithmReturnValues;
import algorithm.AlgorithmView;
import algorithm.Node;
import algorithm.Problem;
import algorithm.Solution;
import algorithm.operators.ObjectiveFunction;
import application.order.OrderApplication;
import application.pallet.PalletApplication;
import client.base.BaseView;
import client.order.OrderView;
import entity.Despacho;
import entity.GuiaRemision;
import entity.Pallet;
import entity.Pedido;
import entity.PedidoParcial;
import entity.PedidoParcialXProducto;
import entity.UnidadTransporte;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
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
    ArrayList<Pedido> currentOrders = new ArrayList<>();
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
        setupElements();
        tblRoutes.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                fillRouteDetailTable();                
            }    
        });
    }
    
    private void setupElements(){
        refreshOrders();
    }
    
    private void fillOrderTable(){
        DefaultTableModel tableModel = (DefaultTableModel) orderTable.getModel();
        tableModel.setRowCount(0);
        currentOrders.stream().forEach((_order) -> {
            Object[] row = {_order.getId(), _order.getCliente().getNombre()
                    , _order.getLocal().getNombre(),EntityState.getOrdersState()[_order.getEstado()], false};
            tableModel.addRow(row);
        });
    }
    
    private void refreshOrders(){
        currentOrders = orderApplication.getAllOrders();
        fillOrderTable();
    }

    private void fillRouteDetailTable() {
        clearRouteDetailTable();
        DefaultTableModel model = (DefaultTableModel) tblRouteDetail.getModel();
        int idx = tblRoutes.getSelectedRow();
        if(idx==-1) return;
        Node[] route = solution.getNodes()[idx];
        for (int j = 0; j < route.length; j++) {
            model.addRow(new Object[]{
                j,
                route[j].getPartialOrder().getPedido().getCliente().getNombre(),
                route[j].getPartialOrder().getPedido().getLocal().getDireccion(),
                route[j].getProduct().getNombre(),
                route[j].getDemand()                
            });            
        }        
    }

    private void fillRoutesTable() {
        clearRoutesTable();
        DefaultTableModel model = (DefaultTableModel) tblRoutes.getModel();
        ArrayList<UnidadTransporte> vehiculos = AlgorithmExecution.problem.getVehicles();

        Node[][] nodes = solution.getNodes();
        for (int i = 0; i < nodes.length; i++) {
            int cap = 0;
            double time = 0;
            for (int j = 0; j < nodes[i].length; j++) {
                cap += nodes[i][j].getDemand();
                if (j > 0) {
                    time += ObjectiveFunction.distance(nodes[i][j - 1].getIdx(), nodes[i][j].getIdx());
                }
            }
            if (nodes[i].length > 0) {
                time += ObjectiveFunction.distance(Problem.getLastNode(), nodes[i][0].getIdx());
                time += ObjectiveFunction.distance(nodes[i][nodes[i].length - 1].getIdx(), Problem.getLastNode());
            }
            time /= vehiculos.get(0).getTipoUnidadTransporte().getVelocidadPromedio();
            int seconds = (int) (time * 3600);
            int hours = seconds / 3600;
            int minutes = (seconds - hours * 3600) / 60;
            String totalTime = "" + hours + "h " + minutes + "m";
            model.addRow(new Object[]{
                i,
                vehiculos.get(i).getPlaca(),
                vehiculos.get(i).getTransportista(),
                totalTime,
                cap
            });
        }

    }

    private void clearRoutesTable() {
        DefaultTableModel model = (DefaultTableModel) tblRoutes.getModel();
        model.setRowCount(0);
    }
    
    private void clearRouteDetailTable() {
        DefaultTableModel model = (DefaultTableModel) tblRouteDetail.getModel();
        model.setRowCount(0);
    }
    
    private ArrayList<PedidoParcial> getCheckedOrders(){
        DefaultTableModel tableModel = (DefaultTableModel) orderTable.getModel();
        ArrayList<PedidoParcial> partials = new ArrayList<>();
        for(int i=0;i<currentOrders.size();i++){
            if((Boolean)tableModel.getValueAt(i, 4)){
                ArrayList<PedidoParcial> tempPartials = 
                        orderApplication.getPendingPartialOrdersById(currentOrders.get(i).getId());
                for(int j=0;j<tempPartials.size();j++)
                    partials.add(tempPartials.get(j));
            }
        }
        if(partials.isEmpty())
            return orderApplication.getPendingPartialOrders();
        else
            return partials;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtResult = new javax.swing.JTextArea();
        btnViewSolution = new javax.swing.JButton();
        btnExecuteAlgorithm = new javax.swing.JButton();
        btnProcess = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtHours = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtMinutes = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnDisplay = new javax.swing.JButton();
        jProgressBar = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRoutes = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRouteDetail = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        allCheckbox = new javax.swing.JCheckBox();

        setClosable(true);
        setTitle(Strings.ALGORITHM_TITLE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Ejecución"));

        txtResult.setColumns(20);
        txtResult.setRows(5);
        jScrollPane1.setViewportView(txtResult);

        btnViewSolution.setText("Ver Solución");
        btnViewSolution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewSolutionActionPerformed(evt);
            }
        });

        btnExecuteAlgorithm.setText("Ejecutar Algoritmo");
        btnExecuteAlgorithm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExecuteAlgorithmActionPerformed(evt);
            }
        });

        btnProcess.setText("Escoger Solución");
        btnProcess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcessActionPerformed(evt);
            }
        });

        jLabel1.setText("Tiempo máximo por ruta:");

        jLabel2.setText("horas");

        jLabel3.setText("minutos");

        btnDisplay.setText("Ver...");
        btnDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayActionPerformed(evt);
            }
        });

        jProgressBar.setMinimum(0);
        jProgressBar.setMaximum(100);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnProcess)
                        .addGap(18, 18, 18)
                        .addComponent(btnExecuteAlgorithm)
                        .addGap(18, 18, 18)
                        .addComponent(btnViewSolution))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtHours, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtMinutes, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtHours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtMinutes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btnDisplay))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProcess)
                    .addComponent(btnExecuteAlgorithm)
                    .addComponent(btnViewSolution))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Rutas asignadas"));

        tblRoutes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ruta No.", "Vehículo", "Conductor", "Tiempo estimado", "Capacidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
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
        jScrollPane2.setViewportView(tblRoutes);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle de la ruta"));

        tblRouteDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nodo No.", "Cliente", "Dirección", "Producto", "Demanda"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
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
        jScrollPane3.setViewportView(tblRouteDetail);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de Pedidos"));

        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Cliente", "Local", "Estado", "Seleccione"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(orderTable);

        allCheckbox.setText("Marcar Todos");
        allCheckbox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                allCheckboxItemStateChanged(evt);
            }
        });
        allCheckbox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                allCheckboxStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(allCheckbox)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(allCheckbox, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnProcessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcessActionPerformed
        if(solution!=null && algorithmExecution!=null){
            AlgorithmReturnValues returnValues = algorithmExecution.processOrders(solution);
            assignRemissionGuides(returnValues.getDespachos());
            if(createPartialOrders(returnValues.getAcceptedOrders(), returnValues.getRejectedOrders())){
                if(OrderView.orderView != null)
                    OrderView.orderView.verifyOrders();
                refreshOrders();
                allCheckbox.setSelected(false);
                JOptionPane.showMessageDialog(this, Strings.DELIVERY_SUCCESS,
                    Strings.DELIVERY_TITLE,JOptionPane.INFORMATION_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(this, Strings.DELIVERY_ERROR,
                    Strings.DELIVERY_TITLE,JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnProcessActionPerformed

    private void btnViewSolutionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewSolutionActionPerformed
        if(solution!=null){
            GoogleMaps googleMaps = new GoogleMaps(solution);
        }
    }//GEN-LAST:event_btnViewSolutionActionPerformed

    private void btnExecuteAlgorithmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExecuteAlgorithmActionPerformed
        algorithmExecution = new AlgorithmExecution(this);
        /*solution = algorithmExecution.start(3);
        StringBuffer buf = algorithmExecution.displayDemand(solution);
        txtResult.setText(buf.toString());*/
        ArrayList<PedidoParcial> selectedPartialOrders = getCheckedOrders();
        double hours = 3, minutes = 0;
        try{
            hours = Double.parseDouble(txtHours.getText());
            minutes = Double.parseDouble(txtMinutes.getText());
            
            try {
                solution = algorithmExecution.start(hours + minutes/60, selectedPartialOrders);
                //StringBuffer buf = algorithmExecution.displayDemand(solution);
                //txtResult.setText(buf.toString());
                fillRoutesTable();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, Strings.ALGORITHM_ERROR,
                        Strings.ALGORITHM_ERROR_TITLE, JOptionPane.INFORMATION_MESSAGE);
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, Strings.BAD_PARAMETERS,
                    Strings.BAD_PARAMETERS_TITLE,JOptionPane.INFORMATION_MESSAGE);
        }      
        
        
    }//GEN-LAST:event_btnExecuteAlgorithmActionPerformed

    private void btnDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayActionPerformed
        if(solution!=null){
            //System.out.println("got here");
            JDesktopPane desktopPane = getDesktopPane();
            AlgorithmView view = new AlgorithmView(solution);            
            desktopPane.add(view);
            view.setSize(700, 700);
            view.setBounds(0, 0, 700, 700);
            view.setVisible(true);
            //System.out.println(algorithmExecution.displayRoutes(view.algorithmPanel.solution));
        }
    }//GEN-LAST:event_btnDisplayActionPerformed

    private void allCheckboxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_allCheckboxStateChanged

    }//GEN-LAST:event_allCheckboxStateChanged

    private void allCheckboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_allCheckboxItemStateChanged
        DefaultTableModel tableModel = (DefaultTableModel) orderTable.getModel();
        if(allCheckbox.isSelected())
            for(int i=0;i<currentOrders.size();i++)
               tableModel.setValueAt(true, i, 4); 
        else
           for(int i=0;i<currentOrders.size();i++)
               tableModel.setValueAt(false, i, 4);
    }//GEN-LAST:event_allCheckboxItemStateChanged

    public void assignRemissionGuides(ArrayList<Despacho> deliveries){
        ArrayList<PedidoParcial> acceptedOrders = new ArrayList<>();
        ArrayList<GuiaRemision> remissionGuides = new ArrayList<>();
        
        orderApplication.CreateRemissionGuides(deliveries); 
    }
    
    public Boolean createPartialOrders(ArrayList<PedidoParcial>acceptedOrders, ArrayList<PedidoParcial>rejectedOrders){
        return orderApplication.createPartialOrders(acceptedOrders, rejectedOrders);
    }
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox allCheckbox;
    private javax.swing.JButton btnDisplay;
    private javax.swing.JButton btnExecuteAlgorithm;
    private javax.swing.JButton btnProcess;
    private javax.swing.JButton btnViewSolution;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JProgressBar jProgressBar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable orderTable;
    private javax.swing.JTable tblRouteDetail;
    private javax.swing.JTable tblRoutes;
    private javax.swing.JTextField txtHours;
    private javax.swing.JTextField txtMinutes;
    private javax.swing.JTextArea txtResult;
    // End of variables declaration//GEN-END:variables

    public JProgressBar getjProgressBar() {
        return jProgressBar;
    }

    public JTextArea getTxtResult() {
        return txtResult;
    }
}
