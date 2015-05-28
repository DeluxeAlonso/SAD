/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import application.order.OrderApplication;
import application.transportunit.TransportUnitApplication;
import entity.PedidoParcial;
import entity.UnidadTransporte;
import java.util.ArrayList;

/**
 *
 * @author robert
 */
class Problem {
    private double[][] costMatrix;
    private ArrayList<PedidoParcial> orders;
    private ArrayList<UnidadTransporte> vehicles;
    
    public Problem(){        
        OrderApplication orderApplication = new OrderApplication();
        TransportUnitApplication transportUnitApplication = new TransportUnitApplication();
        orders = orderApplication.getPendingPartialOrders();
        vehicles = transportUnitApplication.getAvailableTransportUnits();
    }
    
    public double[][] getCostMatrix() {
        return costMatrix;
    }

    public void setCostMatrix(double[][] costMatrix) {
        this.costMatrix = costMatrix;
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
