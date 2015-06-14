/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.internment;

import base.IRepository;
import entity.Usuario;
import entity.Almacen;
import entity.OrdenInternamiento;
import entity.OrdenInternamientoXProducto;
import entity.Producto;
import java.util.ArrayList;

/**
 *
 * @author KEVIN BROWN
 */
public interface IInternmentRepository extends IRepository<OrdenInternamiento>{

    public ArrayList<OrdenInternamiento> queryByType(int idType);

    public OrdenInternamientoXProducto getProdOrder(OrdenInternamiento idType);

    public Boolean incCantOrderXProd(OrdenInternamientoXProducto object);

    public OrdenInternamientoXProducto getOrderProd(Producto id, Integer quantity);
    
}
