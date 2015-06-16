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
import client.reports.RemissionGuideReport;
import entity.Almacen;
import entity.Despacho;
import entity.GuiaRemision;
import entity.Kardex;
import entity.KardexId;
import entity.Local;
import entity.Pallet;
import entity.Pedido;
import entity.PedidoParcial;
import entity.PedidoParcialXProducto;
import entity.Producto;
import entity.Ubicacion;
import entity.UnidadTransporte;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CancellationException;
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
import util.Constants;
import util.EntityState;
import util.Icons;
import util.Strings;

/**
 *
 * @author alulab14
 */
public class DeliveryView extends BaseView {
    //private Solution solution = null;
    private AlgorithmExecution algorithmExecution = null;
    ArrayList<Pedido> currentOrders = new ArrayList<>();
    OrderApplication orderApplication = new OrderApplication();
    PalletApplication palletApplication = new PalletApplication();
    ProductApplication productApplication = new ProductApplication();
    WarehouseApplication warehouseApplication = new WarehouseApplication();
    KardexApplication kardexApplication = new KardexApplication();
    TransportUnitApplication transportUnitApplication = new TransportUnitApplication();
    ArrayList<Despacho> solutionDeliveries = new ArrayList<Despacho>();
    ArrayList<UnidadTransporte> vehicles = new ArrayList<UnidadTransporte>();
    ArrayList<Solution> solutions = new ArrayList<Solution>();
    HashMap<Integer, ArrayList<Pallet>> map = new HashMap<Integer, ArrayList<Pallet>>();
    JFileChooser fc = new JFileChooser();
    File file = null;
    Boolean firstRun = true;
    Informable informable;
    boolean running = false;
    
    /**
     * Creates new form DispatchView
     */
    public DeliveryView() {        
        initComponents();
        super.initialize();
        Icons.setButton(btnProcess, Icons.ICONOS.APPLY.ordinal());
        Icons.setButton(btnExecuteAlgorithm, Icons.ICONOS.PLAY.ordinal());
        Icons.setButton(btnViewSolution, Icons.ICONOS.DELIVERY.ordinal());
        vehicles = transportUnitApplication.getAllTransportUnits();
        setupElements();
        tblRoutes.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                fillRouteDetailTable();                
            }    
        });
        informable = new Informable() {
            @Override
            public void messageChanged(String message) {                
                txtResult.append(message + "\n");
            }
        };
    }
    
    private void setupElements(){
        verifyOrders();
    }
    
    private void fillOrderTable(){
        DefaultTableModel tableModel = (DefaultTableModel) orderTable.getModel();
        tableModel.setRowCount(0);
        currentOrders.stream().forEach((_order) -> {
            
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");            
            double time = ObjectiveFunction.geographicalDist(Constants.WAREHOUSE_LONGITUDE, 
                    Constants.WAREHOUSE_LATITUDE, _order.getLocal().getLongitud(), 
                    _order.getLocal().getLatitud());     
            
            double speed;
            if(vehicles.size()>0) speed = vehicles.get(0).getTipoUnidadTransporte().getVelocidadPromedio();
            else speed = 30;
            time = time/speed; //<-- velocidad
            int seconds = (int) (time * 3600);
            int hours = seconds / 3600;
            int minutes = (seconds - hours * 3600) / 60;
            String totalTime = "" + hours + "h " + minutes + "m";
            
            Object[] row = {_order.getId(), _order.getCliente().getNombre()
                    , _order.getLocal().getNombre(),EntityState.getOrdersState()[_order.getEstado()]
                    , df.format(_order.getFechaVencimiento()), totalTime,
                    firstRun};
            tableModel.addRow(row);
        });
        if(firstRun)
            firstRun = false;
    }
    
    private void refreshOrders(){
        currentOrders = orderApplication.getAllOrders();
        fillOrderTable();
    }

    private void refreshCombo(){
        String[] cads = new String[solutions.size()];
        for (int i = 0; i < cads.length; i++) {
            cads[i] = "Solución " + (i+1);            
        }
        cmbSolutions.setModel(new javax.swing.DefaultComboBoxModel(cads));
        if(solutions.size()>0)
            cmbSolutions.setSelectedIndex(solutions.size()-1);
        fillRoutesTable();
        fillRouteDetailTable();
    }
    
    private void fillRouteDetailTable() {
        clearRouteDetailTable();
        DefaultTableModel model = (DefaultTableModel) tblRouteDetail.getModel();
        int idx = tblRoutes.getSelectedRow();
        if(idx==-1) return;
        int cmbIdx = cmbSolutions.getSelectedIndex();
        if(cmbIdx==-1) return;
        Node[] route = solutions.get(cmbIdx).getNodes()[idx];
        for (int j = 0; j < route.length; j++) {
            model.addRow(new Object[]{
                j+1,
                route[j].getPartialOrder().getPedido().getCliente().getNombre(),
                route[j].getPartialOrder().getPedido().getLocal().getDescripcion(),
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

        int cmbIdx = cmbSolutions.getSelectedIndex();
        if(cmbIdx==-1) return;
        Node[][] nodes = solutions.get(cmbIdx).getNodes();
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
                i+1,
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
            if((Boolean)tableModel.getValueAt(i, 6)){
                ArrayList<PedidoParcial> tempPartials = 
                        orderApplication.getNonAttendedPartialOrdersById(currentOrders.get(i).getId());
                for(int j=0;j<tempPartials.size();j++)
                    partials.add(tempPartials.get(j));
            }
        }
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
                if(attendedCount == 0){
                    currentOrders.get(i).setEstado(EntityState.Orders.REGISTRADO.ordinal());
                    orderApplication.updateOrder(currentOrders.get(i));
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
                ArrayList<Ubicacion>spots = new ArrayList<>();
                for(int k=0;k<pallets.size();k++){
                    Ubicacion spot = pallets.get(k).getUbicacion();
                    spot.setEstado(EntityState.Spots.LIBRE.ordinal());
                    pallets.get(k).setUbicacion(null);
                    spots.add(spot);
                }
                if(!spots.isEmpty())
                    orderApplication.updateSpots(spots, pallets);
                if(!pallets.isEmpty()){
                    ArrayList<Kardex> kardex = kardexApplication.queryByParameters(warehouses.get(i).getId(), products.get(j).getId());
                    Kardex internmentKardex = new Kardex();
                    internmentKardex.setAlmacen(warehouses.get(i));
                    internmentKardex.setProducto(products.get(j));
                    internmentKardex.setTipoMovimiento(Strings.MESSAGE_KARDEX_OUT_DELIVERY);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jProgressBar = new javax.swing.JProgressBar();
        cmbSolutions = new javax.swing.JComboBox();
        spnHours = new javax.swing.JSpinner();
        spnMinutes = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRoutes = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRouteDetail = new javax.swing.JTable();
        warehouseBtn = new javax.swing.JButton();
        deliveryBtn = new javax.swing.JButton();
        remissionBtn = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        allCheckbox = new javax.swing.JCheckBox();
        btnViewLocals = new javax.swing.JButton();
        btnDisplay = new javax.swing.JButton();

        setClosable(true);
        setTitle(Strings.ALGORITHM_TITLE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Ejecución"));

        txtResult.setColumns(20);
        txtResult.setRows(5);
        jScrollPane1.setViewportView(txtResult);

        btnViewSolution.setText("Ver Rutas");
        btnViewSolution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewSolutionActionPerformed(evt);
            }
        });

        btnExecuteAlgorithm.setText("Generar Rutas de Despacho");
        btnExecuteAlgorithm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExecuteAlgorithmActionPerformed(evt);
            }
        });

        btnProcess.setText("Escoger Rutas");
        btnProcess.setEnabled(false);
        btnProcess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcessActionPerformed(evt);
            }
        });

        jLabel1.setText("Tiempo máximo por ruta:");

        jLabel2.setText("horas");

        jLabel3.setText("minutos");

        jProgressBar.setMinimum(0);
        jProgressBar.setMaximum(100);

        cmbSolutions.setModel(new javax.swing.DefaultComboBoxModel(new String[] {""}));
        cmbSolutions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSolutionsActionPerformed(evt);
            }
        });

        spnHours.setModel(new javax.swing.SpinnerNumberModel(0, 0, 24, 1));

        spnMinutes.setModel(new javax.swing.SpinnerNumberModel(0, 0, 60, 1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(cmbSolutions, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnProcess)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnExecuteAlgorithm))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(spnHours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel2)
                                    .addGap(6, 6, 6)
                                    .addComponent(spnMinutes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnViewSolution))))
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(spnHours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnMinutes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnViewSolution))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProcess)
                    .addComponent(btnExecuteAlgorithm)
                    .addComponent(cmbSolutions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        if (tblRoutes.getColumnModel().getColumnCount() > 0) {
            tblRoutes.getColumnModel().getColumn(0).setPreferredWidth(30);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
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
                "Orden de visita", "Cliente", "Local", "Dirección", "Producto", "Demanda"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblRouteDetail);
        if (tblRouteDetail.getColumnModel().getColumnCount() > 0) {
            tblRouteDetail.getColumnModel().getColumn(0).setPreferredWidth(30);
        }

        warehouseBtn.setText("Guía de Almacen");
        warehouseBtn.setEnabled(false);
        warehouseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                warehouseBtnActionPerformed(evt);
            }
        });

        deliveryBtn.setText("Guía de Despacho");
        deliveryBtn.setEnabled(false);
        deliveryBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deliveryBtnActionPerformed(evt);
            }
        });

        remissionBtn.setText("Guías de Remisión");
        remissionBtn.setEnabled(false);
        remissionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remissionBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(warehouseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(deliveryBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(remissionBtn)))
                .addGap(9, 10, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deliveryBtn)
                    .addComponent(warehouseBtn)
                    .addComponent(remissionBtn))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de Pedidos"));

        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Cliente", "Local", "Estado", "Fecha de vencimiento", "Tiempo de llegada", "Seleccione"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(orderTable);
        if (orderTable.getColumnModel().getColumnCount() > 0) {
            orderTable.getColumnModel().getColumn(0).setPreferredWidth(30);
            orderTable.getColumnModel().getColumn(6).setPreferredWidth(30);
        }

        allCheckbox.setSelected(true);
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

        btnViewLocals.setText("Ver Locales");
        btnViewLocals.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewLocalsActionPerformed(evt);
            }
        });

        btnDisplay.setText("Ver...");
        btnDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnViewLocals)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(allCheckbox))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(allCheckbox, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnViewLocals)
                    .addComponent(btnDisplay))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnProcessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcessActionPerformed
        int cmbIdx = cmbSolutions.getSelectedIndex();
        if(cmbIdx==-1) return;
        if(solutions.isEmpty()) 
            JOptionPane.showMessageDialog(this, Strings.ALGORITHM_RUN_MESSAGE,
                    Strings.ALGORITHM_RUN_MESSAGE_TITLE,JOptionPane.INFORMATION_MESSAGE);
        if(solutions.get(cmbIdx)!=null && algorithmExecution!=null){
            AlgorithmReturnValues returnValues = algorithmExecution.processOrders(solutions.get(cmbIdx));
            solutionDeliveries = returnValues.getDespachos();
            assignRemissionGuides(returnValues.getDespachos());
            if(createPartialOrders(returnValues.getAcceptedOrders(), returnValues.getRejectedOrders())){
                try {
                    startLoader();
                    verifyOrders();
                    if(OrderView.orderView != null)
                        OrderView.orderView.verifyOrders();
                    allCheckbox.setSelected(false);
                    warehouseBtn.setEnabled(true);
                    deliveryBtn.setEnabled(true);
                    remissionBtn.setEnabled(true);
                     ArrayList<Almacen> warehouse = warehouseApplication.queryAll();
                    for(int i=0;i<warehouse.size();i++){
                        ArrayList<Pallet> pallets = palletApplication.queryByWarehouseParameters(warehouse.get(i), solutionDeliveries);
                        map.put(warehouse.get(i).getId(), pallets);
                        System.out.println("SIZE DEL GET " + map.get(warehouse.get(i).getId()).size());
                    }   
                    insertKardex(returnValues.getDespachos());
                    JOptionPane.showMessageDialog(this, Strings.DELIVERY_SUCCESS,
                        Strings.DELIVERY_TITLE,JOptionPane.INFORMATION_MESSAGE);
                    btnProcess.setEnabled(false);
                }finally{
                stopLoader();
                }
            }
            else
                JOptionPane.showMessageDialog(this, Strings.DELIVERY_ERROR,
                    Strings.DELIVERY_TITLE,JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnProcessActionPerformed

    private void btnViewSolutionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewSolutionActionPerformed
        int cmbIdx = cmbSolutions.getSelectedIndex();
        if(cmbIdx==-1) return;
        if(solutions.get(cmbIdx)!=null){
            GoogleMaps googleMaps = new GoogleMaps(solutions.get(cmbIdx));
        }
    }//GEN-LAST:event_btnViewSolutionActionPerformed

    private void btnExecuteAlgorithmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExecuteAlgorithmActionPerformed
        if(!running){            
            try{
                double hours = 3, minutes = 0;
                hours = (Integer)spnHours.getValue();
                minutes = (Integer)spnMinutes.getValue();
                /*if(hours<0 || hours>24 || minutes<0 || minutes>1000){
                    JOptionPane.showMessageDialog(DeliveryView.this, Strings.PARAMETERS_OUT_OF_BOUNDS,
                                        Strings.PARAMETERS_OUT_OF_BOUNDS_TITLE, JOptionPane.INFORMATION_MESSAGE);
                    return;
                }*/
                if(hours==0 && minutes==0){
                    JOptionPane.showMessageDialog(DeliveryView.this, Strings.ALGORITHM_ZERO_TIME,
                                            Strings.ALGORITHM_ZERO_TIME_TITLE, JOptionPane.INFORMATION_MESSAGE); 
                    return;
                }
                ArrayList<PedidoParcial> selectedPartialOrders = getCheckedOrders();
                if(!selectedPartialOrders.isEmpty()){
                    txtResult.setText("");
                    algorithmExecution = new AlgorithmExecution(this, hours + minutes/60, 
                            selectedPartialOrders, informable){
                        @Override
                        protected void done() {
                            try {
                                final Solution solution = get();
                                if (solution.isEmpty()) {
                                    JOptionPane.showMessageDialog(DeliveryView.this, Strings.ALGORITHM_NO_ROUTES_FOUND,
                                            Strings.ALGORITHM_NO_ROUTES_FOUND_TITLE, JOptionPane.INFORMATION_MESSAGE);
                                    return;
                                }
                                solutions.add(solution);
                                refreshCombo();
                                JOptionPane.showMessageDialog(DeliveryView.this, Strings.ALGORITHM_SUCCESS,
                                        Strings.ALGORITHM_SUCCESS_TITLE, JOptionPane.INFORMATION_MESSAGE);
                                btnProcess.setEnabled(true);
                                running = false;
                                btnExecuteAlgorithm.setText("Generar Rutas de Despacho");

                            } catch (CancellationException ex){
                                JOptionPane.showMessageDialog(DeliveryView.this, "Se ha cancelado la ejecución.",
                                        Strings.ALGORITHM_SUCCESS_TITLE, JOptionPane.INFORMATION_MESSAGE);
                                algorithmExecution.cancel(true);
                                jProgressBar.setValue(0);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(DeliveryView.this, Strings.ALGORITHM_ERROR,
                                        Strings.ALGORITHM_ERROR_TITLE, JOptionPane.INFORMATION_MESSAGE);
                            } 
                        }
                    };        
                    PropertyChangeListener listener
                            = new PropertyChangeListener() {
                                public void propertyChange(PropertyChangeEvent event) {
                                    if ("progress".equals(event.getPropertyName())) {
                                        jProgressBar.setValue((Integer) event.getNewValue());
                                    }
                                }
                            };
                    algorithmExecution.addPropertyChangeListener(listener);
                    algorithmExecution.execute(); 
                    running = true;
                    btnExecuteAlgorithm.setText("Cancelar");
                }else
                JOptionPane.showMessageDialog(this, "Debe seleccionar al menos un pedido.",
                        "Despacho",JOptionPane.WARNING_MESSAGE);
            }catch(Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, Strings.BAD_PARAMETERS,
                        Strings.BAD_PARAMETERS_TITLE,JOptionPane.INFORMATION_MESSAGE);
            }   
        }
        else{
            algorithmExecution.cancel(true);
            running = false;
            btnExecuteAlgorithm.setText("Generar Rutas de Despacho");
        }
    }//GEN-LAST:event_btnExecuteAlgorithmActionPerformed

    private void btnDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayActionPerformed
        int cmbIdx = cmbSolutions.getSelectedIndex();
        if(cmbIdx==-1) return;
        if(solutions.get(cmbIdx)!=null){
            //System.out.println("got here");
            JDesktopPane desktopPane = getDesktopPane();
            AlgorithmView view = new AlgorithmView(solutions.get(cmbIdx));            
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
               tableModel.setValueAt(true, i, 6); 
        else
           for(int i=0;i<currentOrders.size();i++)
               tableModel.setValueAt(false, i, 6);
    }//GEN-LAST:event_allCheckboxItemStateChanged

    private void warehouseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_warehouseBtnActionPerformed
        file = getReportSelectedFile();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try{
            File exlFile = file;
            WritableWorkbook writableWorkbook = Workbook.createWorkbook(exlFile);
            ArrayList<Almacen> warehouse = warehouseApplication.queryAll();
            for(int i=0;i<warehouse.size();i++){
                ArrayList<Pallet> pallets = map.get(warehouse.get(i).getId());
                if(pallets.size()>0){
                    WritableSheet writableSheet = writableWorkbook.createSheet(
                       "Almacen " + warehouse.get(i).getDescripcion(), i);
                    URL url = getClass().getResource("../../images/warehouse-512-000000.png");
                    java.io.File imageFile = new java.io.File(url.toURI());
                    BufferedImage input = ImageIO.read(imageFile);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(input, "PNG", baos);

                    writableSheet.addImage(new WritableImage(1,1,0.4,1,baos.toByteArray()));
                    writableSheet.getSettings().setShowGridLines(false);
                    writableSheet.getSettings().setPrintGridLines(false);
                    writableSheet.setColumnView(1, 10);
                    writableSheet.setColumnView(2, 50);
                    writableSheet.setColumnView(3, 35);
                    writableSheet.setColumnView(4, 10);
                    writableSheet.setColumnView(5, 10);
                    writableSheet.setColumnView(6, 10);
                    writableSheet.setColumnView(7, 10);
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
        
    }//GEN-LAST:event_warehouseBtnActionPerformed

     public void createWarehouseHeader(WritableSheet writableSheet, Date date, DateFormat dateFormat, Almacen warehouse){
        
        try{
            Label label0 = new Label(1, 1, "");
            Label label1 = new Label(3, 1, "Guía de Almacen");
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
             tittleFormat.setAlignment(jxl.format.Alignment.LEFT);
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
            Label t6 = new Label(6, 6, "Columna");  
            Label t7 = new Label(7, 6, "Lado");
            
             t1.setCellFormat(headerTFormat);
             t2.setCellFormat(headerTFormat);
             t3.setCellFormat(headerTFormat);
             t4.setCellFormat(headerTFormat);
             t5.setCellFormat(headerTFormat);
             t6.setCellFormat(headerTFormat);
             t7.setCellFormat(headerTFormat);
             
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
            writableSheet.addCell(t7);
        }
            catch(Exception e){
                e.printStackTrace();
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
             //parFormat.setAlignment(Alignment.CENTRE);
             parFormat.setBackground(Colour.GREY_25_PERCENT);
             
             
             WritableCellFormat imparFormat = new WritableCellFormat(rowFont);
             //imparFormat.setAlignment(Alignment.CENTRE);
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
                Number l6 = new Number(6,fil+i,pallet.getUbicacion().getColumna()); 
                Label l7 = new Label(7,fil+i,pallet.getUbicacion().getLado()); 
                
                
                if (i%2==0){
                    l1.setCellFormat(imparFormat);
                    l2.setCellFormat(imparFormat);
                    l3.setCellFormat(imparFormat);
                    l4.setCellFormat(imparFormat);
                    l5.setCellFormat(imparFormat);
                    l6.setCellFormat(imparFormat);
                    l7.setCellFormat(imparFormat);
                }else{
                    l1.setCellFormat(parFormat);
                    l2.setCellFormat(parFormat);
                    l3.setCellFormat(parFormat);
                    l4.setCellFormat(parFormat);
                    l5.setCellFormat(parFormat);
                    l6.setCellFormat(parFormat);
                    l7.setCellFormat(parFormat);
                }
                writableSheet.addCell(l1);
                writableSheet.addCell(l2);
                writableSheet.addCell(l3);
                writableSheet.addCell(l4);
                writableSheet.addCell(l5);
                writableSheet.addCell(l6);
                writableSheet.addCell(l7);
                i++;
            }
   
        }catch (Exception e){
            
        }
    }
    
    private void deliveryBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deliveryBtnActionPerformed
        file = getReportSelectedFile();
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
                    writableSheet.getSettings().setShowGridLines(false);
                    writableSheet.getSettings().setPrintGridLines(false);
                    writableSheet.setColumnView(1, 10);
                    writableSheet.setColumnView(2, 20);
                    writableSheet.setColumnView(3, 20);
                    writableSheet.setColumnView(4, 35);
                    writableSheet.setColumnView(5, 35);
                    writableSheet.setColumnView(6, 35);
                    writableSheet.setColumnView(7, 15);
                    writableSheet.setColumnView(8, 15);
                    writableSheet.setColumnView(9, 15);
                    writableSheet.setColumnView(10, 15);

                    createHeader(writableSheet,date,dateFormat,transportUnits.get(i));

                   fillReport(writableSheet,remissionGuides,i);     
                }
            }
            writableWorkbook.write();
            writableWorkbook.close();

        }catch (Exception ex) {
            Logger.getLogger(DeliveryView.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(this, "Ocurrió un error al abrir el archivo",Strings.ERROR_KARDEX_TITLE,JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_deliveryBtnActionPerformed

    private void btnViewLocalsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewLocalsActionPerformed
        HashSet<Local> hsLocals = new HashSet<>();
        ArrayList<PedidoParcial> selectedPartialOrders = getCheckedOrders();
        for (PedidoParcial currentOrder : selectedPartialOrders) {
            hsLocals.add(currentOrder.getPedido().getLocal());            
        }
        ArrayList<Local> locals = new ArrayList<>(hsLocals);
        GoogleMapsLocals map = new GoogleMapsLocals(locals);
    }//GEN-LAST:event_btnViewLocalsActionPerformed

    private void cmbSolutionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSolutionsActionPerformed
        fillRoutesTable();
    }//GEN-LAST:event_cmbSolutionsActionPerformed

    private void remissionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remissionBtnActionPerformed
        file = getReportSelectedFile();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try{
            File exlFile = file;
            WorkbookSettings ws = new WorkbookSettings();
            ws.setEncoding("UTF8");
            WritableWorkbook writableWorkbook = Workbook.createWorkbook(exlFile,ws);
      
            ArrayList<GuiaRemision> remissionGuides = transportUnitApplication.getRemissionGuides(null, solutionDeliveries);
            if(remissionGuides.size()>0){
                for(int i=0;i<remissionGuides.size();i++){
                    WritableSheet writableSheet = writableWorkbook.createSheet(
                       
                      "Guía " + remissionGuides.get(i).getId().toString(), i);
                    
                    URL url = getClass().getResource("../../images/warehouse-512-000000.png");
                    java.io.File imageFile = new java.io.File(url.toURI());
                    BufferedImage input = ImageIO.read(imageFile);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(input, "PNG", baos);

                    writableSheet.addImage(new WritableImage(1,1,0.4,1,baos.toByteArray()));
                    writableSheet.getSettings().setShowGridLines(false);
                    writableSheet.getSettings().setPrintGridLines(false);
                    writableSheet.setColumnView(1, 10);
                    writableSheet.setColumnView(2, 31);
                    writableSheet.setColumnView(3, 35);
                    writableSheet.setColumnView(4, 10);
                    writableSheet.setColumnView(5, 10);
                    writableSheet.setColumnView(6, 10);
                    writableSheet.setColumnView(7, 10);
                    writableSheet.setColumnView(8, 15);
                    writableSheet.setColumnView(9, 15);
                    writableSheet.setColumnView(10, 15);

                   createRemissionHeader(writableSheet,date,dateFormat,remissionGuides.get(i));

                   fillRemissionReport(writableSheet,remissionGuides.get(i),i);     
                }
            }
            writableWorkbook.write();
            writableWorkbook.close();

        }catch (Exception ex) {
            Logger.getLogger(DeliveryView.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(this, "Ocurrió un error al abrir el archivo",Strings.ERROR_KARDEX_TITLE,JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_remissionBtnActionPerformed

    public void createHeader(WritableSheet writableSheet, Date date, DateFormat dateFormat, UnidadTransporte transportUnit){
        
        try{
            Label label0 = new Label(1, 1, "");
            Label label1 = new Label(4, 1, "Guía de Despacho");
            Label label2 = new Label(6, 1, "Fecha: "+ dateFormat.format(date));
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
    
    private void fillReport(WritableSheet writableSheet, ArrayList<GuiaRemision>revRemissionGuides, int idx){
        try{
            //Definicion de formatos
            WritableFont rowFont = new WritableFont(WritableFont.createFont("Calibri"),
             11,
             WritableFont.NO_BOLD,  false,
             UnderlineStyle.NO_UNDERLINE);
             rowFont.setColour(jxl.format.Colour.BLACK);
            
            WritableCellFormat parFormat = new WritableCellFormat(rowFont);
             parFormat.setWrap(false);
             //parFormat.setAlignment(Alignment.CENTRE);
             parFormat.setBackground(Colour.GREY_25_PERCENT);
             
             
             WritableCellFormat imparFormat = new WritableCellFormat(rowFont);
             //imparFormat.setAlignment(Alignment.CENTRE);
             imparFormat.setWrap(false);
             int col=1;
             int fil=7;
             int i=0;
             Object[] row;
             
             int[] orders = new int[revRemissionGuides.size() + 1];
             for(int j=0;j<orders.length;j++)
                 orders[j]=j;
             
             Arrays.sort(orders);
             GuiaRemision[]tempGuides = new GuiaRemision[revRemissionGuides.size() + 1];
             ArrayList<GuiaRemision> remissionGuides = new ArrayList<>();
             for(int j=0;j<revRemissionGuides.size();j++){
                 for(int k=0;k<orders.length;k++)
                     if(revRemissionGuides.get(j).getOrdenVisita()==orders[k])
                         tempGuides[k]=revRemissionGuides.get(j);
            }
            
             for(int j=0;j<tempGuides.length;j++)
                 if(tempGuides[j]!=null)
                    remissionGuides.add(tempGuides[j]);
 
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
                Label l5 = new Label(5,fil+i,"");
                Label l6 = new Label(6,fil+i,"");
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
            e.printStackTrace();
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
    private javax.swing.JButton btnViewLocals;
    private javax.swing.JButton btnViewSolution;
    private javax.swing.JComboBox cmbSolutions;
    private javax.swing.JButton deliveryBtn;
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
    private javax.swing.JButton remissionBtn;
    private javax.swing.JSpinner spnHours;
    private javax.swing.JSpinner spnMinutes;
    private javax.swing.JTable tblRouteDetail;
    private javax.swing.JTable tblRoutes;
    private javax.swing.JTextArea txtResult;
    private javax.swing.JButton warehouseBtn;
    // End of variables declaration//GEN-END:variables

    public JProgressBar getjProgressBar() {
        return jProgressBar;
    }

    public JTextArea getTxtResult() {
        return txtResult;
    }

    private void fillRemissionReport(WritableSheet writableSheet, GuiaRemision remissionGuide, int idx) {
         try{
            //Definicion de formatos
            WritableFont rowFont = new WritableFont(WritableFont.createFont("Calibri"),
             11,
             WritableFont.NO_BOLD,  false,
             UnderlineStyle.NO_UNDERLINE);
             rowFont.setColour(jxl.format.Colour.BLACK);
            
            WritableCellFormat parFormat = new WritableCellFormat(rowFont);
             parFormat.setWrap(false);
             //parFormat.setAlignment(Alignment.CENTRE);
             parFormat.setBackground(Colour.GREY_25_PERCENT);
             
             
             WritableCellFormat imparFormat = new WritableCellFormat(rowFont);
             //imparFormat.setAlignment(Alignment.CENTRE);
             imparFormat.setWrap(false);
             int col=1;
             int fil=8;
             int i=0;
             Object[] row;
             ArrayList<PedidoParcialXProducto> pp = new ArrayList<>();
             for (Iterator<PedidoParcial> partials =remissionGuide.getPedidoParcials().iterator(); partials.hasNext(); ) {
                    PedidoParcial p = partials.next();
                    ArrayList<PedidoParcialXProducto> pxp = orderApplication.queryAllPartialOrderProducts(p.getId());
                    for(int j=0;j<pxp.size();j++)
                        pp.add(pxp.get(j));
             }
             
             for(PedidoParcialXProducto product : pp){
                /*
                row = new Object[]{
                    arr[0].toString(),
                    arr[1].toString(),
                    arr[2].toString(),
                    arr[3].toString()
                };
                model.addRow(row);
                        */
                Number l1 = new Number(1, fil+i,product.getProducto().getId());
                Label l2 = new Label(2, fil+i, product.getProducto().getNombre());
                Label l3 = new Label(3, fil+i, product.getProducto().getDescripcion());
                Number l4 = new Number(4,fil+i,product.getCantidad());
                Number l5 = new Number(5,fil+i,product.getCantidad() * product.getProducto().getPeso());
                
                
                if (i%2==0){
                    l1.setCellFormat(imparFormat);
                    l2.setCellFormat(imparFormat);
                    l3.setCellFormat(imparFormat);
                    l4.setCellFormat(imparFormat);
                    l5.setCellFormat(imparFormat);
                }else{
                    l1.setCellFormat(parFormat);
                    l2.setCellFormat(parFormat);
                    l3.setCellFormat(parFormat);
                    l4.setCellFormat(parFormat);
                    l5.setCellFormat(parFormat);
                }
                writableSheet.addCell(l1);
                writableSheet.addCell(l2);
                writableSheet.addCell(l3);
                writableSheet.addCell(l4);
                writableSheet.addCell(l5);
                i++;
            }
   
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createRemissionHeader(WritableSheet writableSheet, Date date, DateFormat dateFormat, GuiaRemision get) {
         try{
            Label label0 = new Label(1, 1, "");
            Label label1 = new Label(3, 1, "Guía de Remisión");
            Label label2 = new Label(4, 1, "Fecha: "+ dateFormat.format(date));
            Label label3 = new Label(1, 3, "Código: "+ get.getId() );
            Label label4 = new Label(1, 4, "Transportista: "+get.getDespacho().getUnidadTransporte().getTransportista());
            Label label5 = new Label(1, 5, "Placa: "+ get.getDespacho().getUnidadTransporte().getPlaca());
            Label label6 = new Label(4, 3, "Cliente: "+ get.getCliente().getNombre() );
            Label label7 = new Label(4, 4, "Ruc Cliente: "+get.getCliente().getRuc());
            WritableFont tittleFont = new WritableFont(WritableFont.createFont("Calibri"),
             16,
             WritableFont.BOLD,  false,
             UnderlineStyle.NO_UNDERLINE);
             tittleFont.setColour(jxl.format.Colour.RED);
            
            WritableCellFormat tittleFormat = new WritableCellFormat(tittleFont);
             tittleFormat.setWrap(false);
             tittleFormat.setAlignment(jxl.format.Alignment.LEFT);
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
             
            Label t1 = new Label(1, 7, "Cod. Producto"); 
            Label t2 = new Label(2, 7, "Nombre"); 
            Label t3 = new Label(3, 7, "Descripción"); 
            Label t4 = new Label(4, 7, "Cantidad"); 
            Label t5 = new Label(5, 7, "Peso"); 
             t1.setCellFormat(headerTFormat);
             t2.setCellFormat(headerTFormat);
             t3.setCellFormat(headerTFormat);
             t4.setCellFormat(headerTFormat);
             t5.setCellFormat(headerTFormat);
             
             label0.setCellFormat(headerLFormat);
             label1.setCellFormat(tittleFormat);
             label2.setCellFormat(headerLFormat);
             label3.setCellFormat(headerLFormat);
             label4.setCellFormat(headerLFormat);
             label5.setCellFormat(headerLFormat);
             label6.setCellFormat(headerLFormat);
             label7.setCellFormat(headerLFormat);
            writableSheet.addCell(label0);
            writableSheet.addCell(label1);
            writableSheet.addCell(label2);
            writableSheet.addCell(label3);
            writableSheet.addCell(label4);
            writableSheet.addCell(label5);
            writableSheet.addCell(label6);
            writableSheet.addCell(label7);
            writableSheet.addCell(t1);
            writableSheet.addCell(t2);
            writableSheet.addCell(t3);
            writableSheet.addCell(t4);
            writableSheet.addCell(t5);
        }
            catch(Exception e){
                
            }
    }
}
