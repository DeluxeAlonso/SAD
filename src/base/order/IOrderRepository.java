/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.order;

import base.IRepository;
import entity.Cliente;
import entity.Pedido;
import entity.PedidoParcial;
import entity.PedidoParcialXProducto;

/**
 *
 * @author Alonso
 */
public interface IOrderRepository extends IRepository<Pedido> {
    Boolean updateOrder(Pedido order);
    Cliente queryOrderClientById(Integer clientId);
    Boolean createOrder(Pedido order, PedidoParcial p, PedidoParcialXProducto pp);
}
