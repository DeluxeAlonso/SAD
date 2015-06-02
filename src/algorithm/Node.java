/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import entity.PedidoParcial;
import entity.PedidoParcialXProducto;
import entity.Producto;

/**
 *
 * @author robert
 */
public class Node {
    private double x;//Longitud
    private double y;//Latitud
    private double daysDifference;
    private Producto product;
    private int demand;    
    private PedidoParcial partialOrder;
    private int idx;
    private PedidoParcialXProducto orderXProduct;
    
    public PedidoParcialXProducto getOrderXProduct() {
        return orderXProduct;
    }

    public void setOrderXProduct(PedidoParcialXProducto orderXProduct) {
        this.orderXProduct = orderXProduct;
    }
    
    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }
    
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDaysDifference() {
        return daysDifference;
    }

    public void setDaysDifference(double daysDifference) {
        this.daysDifference = daysDifference;
    }

    public Producto getProduct() {
        return product;
    }

    public void setProduct(Producto product) {
        this.product = product;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    public PedidoParcial getPartialOrder() {
        return partialOrder;
    }

    public void setPartialOrder(PedidoParcial partialOrder) {
        this.partialOrder = partialOrder;
    }
}
