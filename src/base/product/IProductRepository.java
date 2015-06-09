/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.product;

import base.IRepository;
import entity.Producto;
import java.util.ArrayList;

/**
 *
 * @author Alonso
 */
public interface IProductRepository extends IRepository<Producto>{
    public ArrayList<Producto> queryByType(int idType);
    public ArrayList<Producto> queryByCondition(int idType);
    ArrayList<Producto> searchProduct(Producto product);
}
