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
import entity.Ubicacion;
import infraestructure.order.OrderRepository;
import java.util.ArrayList;
import java.util.Date;
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
    
    public ArrayList<Pedido> getOrdersByClientId(Integer clientId){
        ArrayList<Pedido>orders = new ArrayList<>();
        try{
            orders = orderRepository.queryOrdersByClientId(clientId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return orders;
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
    
    public ArrayList<PedidoParcial> getNonAttendedPartialOrdersById(Integer id) {
        ArrayList<PedidoParcial> partialOrders = new ArrayList<>();
        try{
            partialOrders = orderRepository.queryAllNonAttendedPartialOrdersById(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return partialOrders;
    }
    
    public ArrayList<Pedido> getPartialOrdersByDeliveryId(Despacho delivery){
        ArrayList<Pedido> partialOrders = new ArrayList<>();
        try{
            partialOrders = orderRepository.queryAllPartialOrdersByDeliveryId(delivery);
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
    
    public ArrayList<GuiaRemision> searchRemissionGuides(Integer idDelivery, Integer idRemissionGuide, Date startDate, Date endDate){
        ArrayList<GuiaRemision> remissionGuides = new ArrayList<>();
        try{
            remissionGuides = orderRepository.queryAllProductsByOrderId(idDelivery, idRemissionGuide, startDate, endDate);
        }catch(Exception e){
            e.printStackTrace();
        }
        return remissionGuides;
    }
    
    public ArrayList<Despacho> searchDeliveries(Integer idDelivery, Integer IdTransportist, Date startDate, Date endDate){
        ArrayList<Despacho> deliveries = new ArrayList<>();
        try{
            deliveries = orderRepository.searchDeliveries(idDelivery, IdTransportist, startDate, endDate);
        }catch(Exception e){
            e.printStackTrace();
        }
        return deliveries;
    }
    
    public Boolean updateSpots(ArrayList<Ubicacion>spots, ArrayList<Pallet>pallets){
        Boolean response = false;
        try {
            response = orderRepository.updateSpots(spots, pallets);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
        
}
