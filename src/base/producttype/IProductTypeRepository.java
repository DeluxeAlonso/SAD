/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.producttype;

import base.product.*;
import base.IRepository;
import entity.Producto;
import entity.TipoProducto;
import java.util.ArrayList;

/**
 *
 * @author Alonso
 */
public interface IProductTypeRepository extends IRepository<TipoProducto>{
    public ArrayList<TipoProducto> queryByType(int idType);
}
