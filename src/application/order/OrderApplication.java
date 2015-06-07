/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.order;

import base.order.IOrderRepository;
import entity.Cliente;
import entity.Despacho;
import entity.GuiaRemision;
import entity.Pallet;
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
        ArrayList<Pedido> orders=null;
        try{
            orders=orderRepository.queryAll();
        }catch(Exception e){
            e.printStackTrace();
        }
        return orders;
    }
    
    public ArrayList<Pedido> getAllOrdersWithAllStates(){
        ArrayList<Pedido> orders=null;
        try{
            orders=orderRepository.queryAllOrders();
        }catch(Exception e){
            e.printStackTrace();
        }
        return orders;
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
    
    public Boolean CreateOrder(Pedido order, PedidoParcial p, ArrayList<PedidoParcialXProducto> pp, Boolean uploaded){
        Boolean response = false;
        try {
            response = orderRepository.createOrder(order, p, pp, uploaded);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
    public Boolean CreateRemissionGuides(ArrayList<Despacho> deliveries){
        Boolean response = false;
        try {
            response = orderRepository.createRemissionGuides(deliveries);
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
    
    public Boolean createPartialOrders(ArrayList<PedidoParcial>acceptedOrders, 
           ArrayList<PedidoParcial>rejectedOrders){
            Boolean response = false;
        try {
            response = orderRepository.createPartialOrders(acceptedOrders,rejectedOrders);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
    public void refreshOrders(){
        EntityType.ORDERS = getAllOrders();
    }

    public ArrayList<PedidoParcial> getPendingPartialOrders() {
        ArrayList<PedidoParcial> partialOrders = new ArrayList<>();
        try{
            partialOrders = orderRepository.queryAllPendingPartialOrders();
        }catch(Exception e){
            e.printStackTrace();
        }
        return partialOrders;
    }
    
    public ArrayList<PedidoParcial> getPendingPartialOrdersById(Integer id) {
        ArrayList<PedidoParcial> partialOrders = new ArrayList<>();
        try{
            partialOrders = orderRepository.queryAllPendingPartialOrdersById(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return partialOrders;
    }
    
    public ArrayList<PedidoParcialXProducto> queryAllProductsByOrderId(Integer orderId){
        ArrayList<PedidoParcialXProducto> partialProducts = new ArrayList<>();
        try{
            partialProducts = orderRepository.queryAllProductsByOrderId(orderId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return partialProducts;
    }
    
    public ArrayList<PedidoParcialXProducto> queryAllPartialOrderProducts(Integer partialOrderId){
        ArrayList<PedidoParcialXProducto> partialProducts = new ArrayList<>();
        try{
            partialProducts = orderRepository.queryAllPartialOrderProducts(partialOrderId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return partialProducts;
    }
    
    public ArrayList<Pedido> searchOrders(Pedido o){
        ArrayList<Pedido> order=null;
        try{
            order=orderRepository.searchOrder(o);
        }catch(Exception e){
            e.printStackTrace();
        }
        return order;
    }
    
    public Boolean updatePartialOrder(PedidoParcial p, ArrayList<Pallet>pallets){
        Boolean response = false;
        try {
            response = orderRepository.updatePartialOrder(p, pallets);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
        
}
