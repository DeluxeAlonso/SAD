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
import application.kardex.KardexApplication;
import application.order.OrderApplication;
import application.pallet.PalletApplication;
import application.product.ProductApplication;
import application.transportunit.TransportUnitApplication;
import application.warehouse.WarehouseApplication;
import client.base.BaseView;
import client.order.OrderView;
import client.reports.AvailabilityReport;
import entity.Almacen;
import entity.Despacho;
import entity.GuiaRemision;
import entity.Kardex;
import entity.KardexId;
import entity.Pallet;
import entity.Pedido;
import entity.PedidoParcial;
import entity.PedidoParcialXProducto;
import entity.Producto;
import entity.UnidadTransporte;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Number;
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
    ProductApplication productApplication = new ProductApplication();
    WarehouseApplication warehouseApplication = new WarehouseApplication();
    KardexApplication kardexApplication = new KardexApplication();
    TransportUnitApplication transportUnitApplication = new TransportUnitApplication();
    ArrayList<Despacho> solutionDeliveries = new ArrayList<Despacho>();
    JFileChooser fc = new JFileChooser();
    File file = null;
    
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
        verifyOrders();
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
         
    public void verifyOrders(){
        currentOrders = orderApplication.getAllOrders();
        for(int i=0;i<currentOrders.size();i++){
            if(currentOrders.get(i).getEstado() != EntityState.Orders.ANULADO.ordinal()){
                int attendedCount = 0;
                ArrayList<PedidoParcial> partialOrders = orderApplication.getPendingPartialOrdersById(currentOrders.get(i).getId());        
                for(int j=0;j<partialOrders.size();j++){
                    if(partialOrders.get(j).getEstado() == EntityState.PartialOrders.ATENDIDO.ordinal())
                        attendedCount++;
                }
                if(attendedCount == partialOrders.size()){
                    currentOrders.get(i).setEstado(EntityState.Orders.FINALIZADO.ordinal());
                    orderApplication.updateOrder(currentOrders.get(i));
                }
            }
        }
        refreshOrders();
    }
    
    public void insertKardex(ArrayList<Despacho> deliveries){
        ArrayList<Almacen> warehouses = warehouseApplication.queryAll();
        ArrayList<Producto> products = productApplication.getAllProducts();
        for(int i=0;i<warehouses.size();i++){
            for(int j=0;j<products.size();j++){
                ArrayList<Pallet> pallets = palletApplication.queryByDeliveryParameters(warehouses.get(i), deliveries, products.get(j));
                if(!pallets.isEmpty()){
                    ArrayList<Kardex> kardex = kardexApplication.queryByParameters(warehouses.get(i).getId(), products.get(j).getId());
                    Kardex internmentKardex = new Kardex();
                    internmentKardex.setAlmacen(warehouses.get(i));
                    internmentKardex.setProducto(products.get(j));
                    internmentKardex.setTipoMovimiento("Salida");
                    internmentKardex.setCantidad(pallets.size());

                    internmentKardex.setFecha(Calendar.getInstance().getTime());
                    if(kardex.size()==0){
                        internmentKardex.setStockInicial(0);
                    }else{
                        internmentKardex.setStockInicial(kardex.get(0).getStockFinal());
                    }
                    internmentKardex.setStockFinal(internmentKardex.getStockInicial() - pallets.size());

                    KardexId kId = new KardexId();
                    kId.setIdAlmacen(warehouses.get(i).getId());
                    kId.setIdProducto(products.get(j).getId());

                    internmentKardex.setId(kId);

                    kardexApplication.insert(internmentKardex);
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
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

        jButton1.setText("Exportar Guia de Almacen");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Exportar Guia de Remision");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap())
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
            solutionDeliveries = returnValues.getDespachos();
            assignRemissionGuides(returnValues.getDespachos());
            if(createPartialOrders(returnValues.getAcceptedOrders(), returnValues.getRejectedOrders())){
                if(OrderView.orderView != null)
                    OrderView.orderView.verifyOrders();
                verifyOrders();
                allCheckbox.setSelected(false);
                jButton1.setEnabled(true);
                jButton2.setEnabled(true);
                insertKardex(returnValues.getDespachos());
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        fc.setDialogTitle("Seleccione un archivo");
        fc.showOpenDialog(this);
        FileFilter excelType = new FileNameExtensionFilter("Excel spreadsheet (.xls)", "xls");
        fc.addChoosableFileFilter(excelType);
        file = fc.getSelectedFile();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try{
            File exlFile = file;
            WritableWorkbook writableWorkbook = Workbook.createWorkbook(exlFile);
            ArrayList<Almacen> warehouse = warehouseApplication.queryAll();
            for(int i=0;i<warehouse.size();i++){
                ArrayList<Pallet> pallets = palletApplication.queryByWarehouseParameters(warehouse.get(i), solutionDeliveries);
                if(pallets.size()>0){
                    WritableSheet writableSheet = writableWorkbook.createSheet(
                       "Almacen " + warehouse.get(i).getDescripcion(), i);
                    URL url = getClass().getResource("../../images/warehouse-512-000000.png");
                    java.io.File imageFile = new java.io.File(url.toURI());
                    BufferedImage input = ImageIO.read(imageFile);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(input, "PNG", baos);

                    writableSheet.addImage(new WritableImage(1,1,0.4,1,baos.toByteArray()));

                    writableSheet.setColumnView(1, 10);
                    writableSheet.setColumnView(2, 35);
                    writableSheet.setColumnView(3, 25);
                    writableSheet.setColumnView(4, 20);
                    writableSheet.setColumnView(5, 20);
                    writableSheet.setColumnView(6, 20);
                    writableSheet.setColumnView(7, 15);
                    writableSheet.setColumnView(8, 15);
                    writableSheet.setColumnView(9, 15);
                    writableSheet.setColumnView(10, 15);

                    createWarehouseHeader(writableSheet,date,dateFormat,warehouse.get(i));

                   fillWarehouseReport(writableSheet,pallets);     
                }
            }
            writableWorkbook.write();
            writableWorkbook.close();

        }catch (Exception ex) {
            Logger.getLogger(AvailabilityReport.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(this, "Ocurrió un error al abrir el archivo",Strings.ERROR_KARDEX_TITLE,JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

     public void createWarehouseHeader(WritableSheet writableSheet, Date date, DateFormat dateFormat, Almacen warehouse){
        
        try{
            Label label0 = new Label(1, 1, "");
            Label label1 = new Label(4, 1, "Guía de Almacen");
            Label label2 = new Label(7, 1, "Fecha: "+ dateFormat.format(date));
            Label label3 = new Label(1, 2, "Código: "+ warehouse.getId() );
            Label label4 = new Label(1, 3, "Condición: "+warehouse.getCondicion().getNombre());
            Label label5 = new Label(1, 4, "Área: "+ warehouse.getArea());
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
             
            Label t1 = new Label(1, 6, "Pallet"); 
            Label t2 = new Label(2, 6, "EAN128"); 
            Label t3 = new Label(3, 6, "Producto"); 
            Label t4 = new Label(4, 6, "Rack"); 
            Label t5 = new Label(5, 6, "Fila"); 
            Label t6 = new Label(6, 6, "Lado");  
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
     
    private void fillWarehouseReport(WritableSheet writableSheet, ArrayList<Pallet>pallets){
        try{
            //Definicion de formatos
            WritableFont rowFont = new WritableFont(WritableFont.createFont("Calibri"),
             11,
             WritableFont.NO_BOLD,  false,
             UnderlineStyle.NO_UNDERLINE);
             rowFont.setColour(jxl.format.Colour.BLACK);
            
            WritableCellFormat parFormat = new WritableCellFormat(rowFont);
             parFormat.setWrap(false);
             parFormat.setAlignment(Alignment.CENTRE);
             parFormat.setBackground(Colour.GREY_25_PERCENT);
             
             
             WritableCellFormat imparFormat = new WritableCellFormat(rowFont);
             imparFormat.setAlignment(Alignment.CENTRE);
             imparFormat.setWrap(false);
             int col=1;
             int fil=7;
             int i=0;
             Object[] row;
             for(Pallet pallet : pallets){
                /*
                row = new Object[]{
                    arr[0].toString(),
                    arr[1].toString(),
                    arr[2].toString(),
                    arr[3].toString()
                };
                model.addRow(row);
                        */
                Number l1 = new Number(1, fil+i,pallet.getId());
                Label l2 = new Label(2, fil+i, pallet.getEan128());
                Label l3 = new Label(3, fil+i, pallet.getProducto().getNombre());
                Number l4 = new Number(4,fil+i,pallet.getUbicacion().getRack().getId());
                Number l5 = new Number(5,fil+i,pallet.getUbicacion().getFila());
                Label l6 = new Label(6,fil+i,pallet.getUbicacion().getLado());      
                
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
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        fc.setDialogTitle("Seleccione un archivo");
        fc.showOpenDialog(this);
        file = fc.getSelectedFile();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try{
            File exlFile = file;
            WorkbookSettings ws = new WorkbookSettings();
            ws.setEncoding("UTF8");
            WritableWorkbook writableWorkbook = Workbook.createWorkbook(exlFile,ws);
            
            ArrayList<UnidadTransporte> transportUnits = transportUnitApplication.getAllTransportUnits();
            for(int i=0;i<transportUnits.size();i++){
                ArrayList<GuiaRemision> remissionGuides = transportUnitApplication.getRemissionGuides(transportUnits.get(i), solutionDeliveries);
                if(remissionGuides.size()>0){
                    WritableSheet writableSheet = writableWorkbook.createSheet(
                       
                       transportUnits.get(i).getTransportista(), i);
                    
                    URL url = getClass().getResource("../../images/warehouse-512-000000.png");
                    java.io.File imageFile = new java.io.File(url.toURI());
                    BufferedImage input = ImageIO.read(imageFile);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(input, "PNG", baos);

                    writableSheet.addImage(new WritableImage(1,1,0.4,1,baos.toByteArray()));

                    writableSheet.setColumnView(1, 10);
                    writableSheet.setColumnView(2, 15);
                    writableSheet.setColumnView(3, 15);
                    writableSheet.setColumnView(4, 20);
                    writableSheet.setColumnView(5, 20);
                    writableSheet.setColumnView(6, 35);
                    writableSheet.setColumnView(7, 15);
                    writableSheet.setColumnView(8, 15);
                    writableSheet.setColumnView(9, 15);
                    writableSheet.setColumnView(10, 15);

                    createHeader(writableSheet,date,dateFormat,transportUnits.get(i));

                   fillReport(writableSheet,remissionGuides);     
                }
            }
            writableWorkbook.write();
            writableWorkbook.close();

        }catch (Exception ex) {
            Logger.getLogger(AvailabilityReport.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(this, "Ocurrió un error al abrir el archivo",Strings.ERROR_KARDEX_TITLE,JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    public void createHeader(WritableSheet writableSheet, Date date, DateFormat dateFormat, UnidadTransporte transportUnit){
        
        try{
            Label label0 = new Label(1, 1, "");
            Label label1 = new Label(4, 1, "Guías de Remision");
            Label label2 = new Label(7, 1, "Fecha: "+ dateFormat.format(date));
            Label label3 = new Label(1, 2, "Código: "+ transportUnit.getId() );
            Label label4 = new Label(1, 3, "Transportista: "+transportUnit.getTransportista());
            Label label5 = new Label(1, 4, "Placa: "+ transportUnit.getPlaca());
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
             
            Label t1 = new Label(1, 6, "Guía de Remisión"); 
            Label t2 = new Label(2, 6, "Cliente"); 
            Label t3 = new Label(3, 6, "Ruc"); 
            Label t4 = new Label(4, 6, "Local"); 
            Label t5 = new Label(5, 6, "Descripción"); 
            Label t6 = new Label(6, 6, "Dirección");  
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
    
    private void fillReport(WritableSheet writableSheet, ArrayList<GuiaRemision>remissionGuides){
        try{
            //Definicion de formatos
            WritableFont rowFont = new WritableFont(WritableFont.createFont("Calibri"),
             11,
             WritableFont.NO_BOLD,  false,
             UnderlineStyle.NO_UNDERLINE);
             rowFont.setColour(jxl.format.Colour.BLACK);
            
            WritableCellFormat parFormat = new WritableCellFormat(rowFont);
             parFormat.setWrap(false);
             parFormat.setAlignment(Alignment.CENTRE);
             parFormat.setBackground(Colour.GREY_25_PERCENT);
             
             
             WritableCellFormat imparFormat = new WritableCellFormat(rowFont);
             imparFormat.setAlignment(Alignment.CENTRE);
             imparFormat.setWrap(false);
             int col=1;
             int fil=7;
             int i=0;
             Object[] row;
             for(GuiaRemision guide : remissionGuides){
                /*
                row = new Object[]{
                    arr[0].toString(),
                    arr[1].toString(),
                    arr[2].toString(),
                    arr[3].toString()
                };
                model.addRow(row);
                        */
                Number l1 = new Number(1, fil+i,guide.getId());
                Label l2 = new Label(2, fil+i, guide.getCliente().getNombre());
                Label l3 = new Label(3, fil+i, guide.getCliente().getRuc());
                Label l4 = new Label(4,fil+i,"");
                Label l5 = new Label(5,fil+i,"");;
                Label l6 = new Label(6,fil+i,"");;
                for (Iterator<PedidoParcial> partials =guide.getPedidoParcials().iterator(); partials.hasNext(); ) {
                    PedidoParcial p = partials.next();
                    l4 = new Label(4, fil+i, p.getPedido().getLocal().getNombre());
                    l5 = new Label(5, fil+i, p.getPedido().getLocal().getDescripcion());
                    l6 = new Label(6, fil+i, p.getPedido().getLocal().getDireccion());
                    break;
                }
                
                
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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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
