/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application.producttype;

import application.product.*;
import base.product.IProductRepository;
import base.producttype.IProductTypeRepository;
import entity.Producto;
import entity.TipoProducto;
import infraestructure.product.ProductRepository;
import infraestructure.producttype.ProductTypeRepository;
import java.util.ArrayList;
import util.EntityType;

/**
 *
 * @author robert
 */
public class ProductTypeApplication {

    IProductTypeRepository productTypeRepository;
    
    public ProductTypeApplication(){
        this.productTypeRepository = new ProductTypeRepository();
    }
    
    public int insert(TipoProducto product) {
        int i=-1;
        try{
            i = productTypeRepository.insert(product);
        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
        return i;
    }
    
    public ArrayList<TipoProducto> queryAll(){
        ArrayList<TipoProducto> actions=null;
        try{
            actions=productTypeRepository.queryAll();
        }catch(Exception e){
            e.printStackTrace();
        }
        return actions;
    }  
    

    
    public int delete(TipoProducto product){
        int i = product.getId();
        try{
            productTypeRepository.delete(product);
        }catch(Exception e){
            
            e.printStackTrace();
            return -1;
        }
        return i;
    }
    /*
    public void refreshProducts(){
        EntityType.PRODUCTS = getAllProducts();
    }*/
    
}
