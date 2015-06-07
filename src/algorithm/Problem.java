/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import algorithm.operators.ObjectiveFunction;
import application.order.OrderApplication;
import application.product.ProductApplication;
import application.transportunit.TransportUnitApplication;
import entity.Pedido;
import entity.PedidoParcial;
import entity.PedidoParcialXProducto;
import entity.Producto;
import entity.UnidadTransporte;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import util.Constants;

/**
 *
 * @author robert
 */
public class Problem {    
    private ArrayList<PedidoParcial> orders;
    private ArrayList<ArrayList<PedidoParcialXProducto>> partialOrdersXProducts;
    private ArrayList<UnidadTransporte> vehicles;  
    private HashMap<Integer,Integer> productsStock;
    private ArrayList<Node> nodes;
    private static int lastNode;

    public static int getLastNode() {
        return lastNode;
    }

    public static void setLastNode(int lastNode) {
        Problem.lastNode = lastNode;
    }
    
    public Problem(){        
        OrderApplication orderApplication = new OrderApplication();
        TransportUnitApplication transportUnitApplication = new TransportUnitApplication();
        ProductApplication productApplication = new ProductApplication();        
        orders = orderApplication.getPendingPartialOrders();
        vehicles = transportUnitApplication.getAllTransportUnits(); 
        ArrayList<Producto> products = productApplication.getAllProducts();
        
        int vehicleCapacity = 1;
        if(vehicles!=null) vehicleCapacity = vehicles.get(0).getTipoUnidadTransporte().getCapacidadPallets();
        
        productsStock = new HashMap<>();
        
        for (int i = 0; i < products.size(); i++) {
            productsStock.put(products.get(i).getId(), products.get(i).getStockTotal());
        }
        
        if(vehicles.isEmpty()) throw new AssertionError("There are no inserted vehicles");
        
        
        nodes = new ArrayList<>();        
        Date today = new Date();
        int nNodes = 0;
        
        partialOrdersXProducts = new ArrayList<>();
        
        for (PedidoParcial order : orders) {
            ArrayList<PedidoParcialXProducto> partialOrderProducts = 
                    orderApplication.queryAllPartialOrderProducts(order.getId());
            partialOrdersXProducts.add(partialOrderProducts);
            for (PedidoParcialXProducto partialOrderProduct : partialOrderProducts) {
                int demand = partialOrderProduct.getCantidad();
                Pedido pedido = partialOrderProduct.getPedidoParcial().getPedido();
                //productsStock.put(partialOrderProduct.getProducto().getId(), demand);
                int batchNumber;
                if(demand%vehicleCapacity==0) batchNumber = demand/vehicleCapacity;                
                else batchNumber = demand/vehicleCapacity +1;
                for (int i = 0; i < batchNumber; i++) {
                    Node node = new Node();
                    
                    
                    node.setX(pedido.getLocal().getLongitud());
                    node.setY(pedido.getLocal().getLatitud());   
                    
                    long diff = pedido.getFecha().getTime() - today.getTime();
                    node.setDaysDifference(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                    
                    if(i<batchNumber-1) node.setDemand(vehicleCapacity);
                    else node.setDemand(demand);
                    demand -= vehicleCapacity;
                    
                    node.setPartialOrder(order);
                    node.setProduct(partialOrderProduct.getProducto());
                    node.setIdx(nNodes++);
                    node.setOrderXProduct(partialOrderProduct);
                    
                    nodes.add(node);                    
                }
            }            
        }
        
        for (HashMap.Entry<Integer, Integer> entry : productsStock.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println("Product id: " + key + "  Stock: " + value);

        }
        
        System.out.println("nodes size: " + nodes.size());
        
        for (int i = 0; i < nodes.size(); i++) {
            System.out.println("Product id: " + nodes.get(i).getProduct().getId() + 
                    "  Demand: " + nodes.get(i).getDemand() + 
                    "  Order id: " + nodes.get(i).getPartialOrder().getPedido().getId());
            
        }
        
        double[][] distMatrix = new double[nodes.size()+1][nodes.size()+1];
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                distMatrix[i][j] = ObjectiveFunction.geographicalDist(nodes.get(i).getX(), 
                        nodes.get(i).getY(), nodes.get(j).getX(), nodes.get(j).getY());
                
            }            
        }
        for (int i = 0; i < nodes.size(); i++) {
            distMatrix[i][nodes.size()] = ObjectiveFunction.geographicalDist(nodes.get(i).getX(), 
                    nodes.get(i).getY(), Constants.WAREHOUSE_LONGITUDE, Constants.WAREHOUSE_LATITUDE);            
        }
        for (int i = 0; i < nodes.size(); i++) {
            distMatrix[nodes.size()][i] = ObjectiveFunction.geographicalDist(
                    Constants.WAREHOUSE_LONGITUDE, Constants.WAREHOUSE_LATITUDE, 
                    nodes.get(i).getX(), nodes.get(i).getY());            
        }
        distMatrix[nodes.size()][nodes.size()] = 0;
        ObjectiveFunction.setDistMatrix(distMatrix);
        lastNode = nodes.size();
    }    

    public ArrayList<ArrayList<PedidoParcialXProducto>> getPartialOrdersXProducts() {
        return partialOrdersXProducts;
    }

    public void setPartialOrdersXProducts(ArrayList<ArrayList<PedidoParcialXProducto>> partialOrdersXProducts) {
        this.partialOrdersXProducts = partialOrdersXProducts;
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
