/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import application.order.OrderApplication;
import application.transportunit.TransportUnitApplication;
import entity.Pedido;
import entity.PedidoParcial;
import entity.PedidoParcialXProducto;
import entity.UnidadTransporte;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author robert
 */
public class Problem {    
    private ArrayList<PedidoParcial> orders;
    private ArrayList<UnidadTransporte> vehicles;  
    private HashMap<Integer,Integer> productsStock;
    private ArrayList<Node> nodes;
    
    public Problem(){        
        OrderApplication orderApplication = new OrderApplication();
        TransportUnitApplication transportUnitApplication = new TransportUnitApplication();
        orders = orderApplication.getPendingPartialOrders();
        vehicles = transportUnitApplication.getAvailableTransportUnits(); 
        
        int vehicleCapacity = 1;
        if(vehicles!=null) vehicleCapacity = vehicles.get(0).getTipoUnidadTransporte().getCapacidadPallets();
        
        productsStock = new HashMap<>();
        nodes = new ArrayList<>();        
        Date today = new Date();
        
        for (PedidoParcial order : orders) {
            ArrayList<PedidoParcialXProducto> partialOrderProducts = 
                    orderApplication.queryAllPartialOrderProducts(order.getId());
            for (PedidoParcialXProducto partialOrderProduct : partialOrderProducts) {
                int demand = partialOrderProduct.getCantidad();
                Pedido pedido = partialOrderProduct.getPedidoParcial().getPedido();
                productsStock.put(partialOrderProduct.getProducto().getId(), demand);
                int batchNumber = demand/vehicleCapacity + 1;                
                for (int i = 0; i < batchNumber; i++) {
                    Node node = new Node();
                    
                    
                    node.setX(pedido.getLocal().getLatitud());
                    node.setY(pedido.getLocal().getLongitud());   
                    
                    long diff = pedido.getFecha().getTime() - today.getTime();
                    node.setDaysDifference(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                    
                    if(i<batchNumber) node.setDemand(batchNumber*vehicleCapacity);
                    else node.setDemand(demand);
                    demand -= batchNumber*vehicleCapacity;
                    
                    node.setPartialOrder(order);
                    node.setProduct(partialOrderProduct.getProducto());
                    nodes.add(node);                    
                }
            }            
        }
    }    

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }
    
    public HashMap<Integer, Integer> getProductsStock() {
        return productsStock;
    }

    public void setProductsStock(HashMap<Integer, Integer> productsStock) {
        this.productsStock = productsStock;
    }    
    
    public ArrayList<PedidoParcial> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<PedidoParcial> orders) {
        this.orders = orders;
    }

    public ArrayList<UnidadTransporte> getVehicles() {
        return vehicles;
    }

    public void setVehicles(ArrayList<UnidadTransporte> vehicles) {
        this.vehicles = vehicles;
    }
}
