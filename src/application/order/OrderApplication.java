/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.order;

import base.order.IOrderRepository;
import entity.Cliente;
import entity.Pedido;
import entity.PedidoParcial;
import entity.PedidoParcialXProducto;
import infraestructure.order.OrderRepository;
import java.util.ArrayList;
import util.EntityType;

/**
 *
 * @author Alonso
 */
public class OrderApplication {
    
    IOrderRepository orderRepository;
    
    public OrderApplication(){
        this.orderRepository = new OrderRepository();
    }
    
    public ArrayList<Pedido> getAllOrders(){
        ArrayList<Pedido> actions=null;
        try{
            actions=orderRepository.queryAll();
        }catch(Exception e){
            e.printStackTrace();
        }
        return actions;
    }
    
    public Cliente getOrderClient(Integer clientId){
        Cliente client = null;
        try{
            client = orderRepository.queryOrderClientById(clientId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return client;
    }
    
    public Boolean CreateOrder(Pedido order, PedidoParcial p, PedidoParcialXProducto pp){
        Boolean response = false;
        try {
            response = orderRepository.createOrder(order, p, pp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
    public Boolean updateOrder(Pedido order){
        Boolean response = false;
        try {
            response = orderRepository.updateOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
    public void refreshOrders(){
        EntityType.ORDERS = getAllOrders();
    }
        
}
