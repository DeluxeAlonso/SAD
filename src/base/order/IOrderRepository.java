/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.order;

import base.IRepository;
import entity.Cliente;
import entity.GuiaRemision;
import entity.Pedido;
import entity.PedidoParcial;
import entity.PedidoParcialXProducto;
import java.util.ArrayList;

/**
 *
 * @author Alonso
 */
public interface IOrderRepository extends IRepository<Pedido> {
    Boolean updateOrder(Pedido order);
    Cliente queryOrderClientById(Integer clientId);
    Boolean createOrder(Pedido order, PedidoParcial partialOrder, ArrayList<PedidoParcialXProducto> products);
    ArrayList<PedidoParcial> queryAllPendingPartialOrders();
    ArrayList<PedidoParcialXProducto> queryAllPartialOrderProducts(Integer partialOrderId);
    ArrayList<PedidoParcial> queryAllPendingPartialOrdersById(Integer id);
    ArrayList<Pedido> searchOrder(Pedido order);
    ArrayList<PedidoParcialXProducto> queryAllProductsByOrderId(Integer id);
    Boolean createRemissionGuides(ArrayList<PedidoParcial> acceptedOrders, ArrayList<GuiaRemision> remissionGuides);

    Boolean createPartialOrders(ArrayList<PedidoParcial> acceptedOrders, ArrayList<PedidoParcialXProducto> acceptedOrdersXProd, ArrayList<PedidoParcial> rejectedOrders, ArrayList<PedidoParcialXProducto> rejectedOrdersXProd);
}
