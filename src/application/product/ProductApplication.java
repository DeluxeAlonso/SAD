/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application.product;

import base.product.IProductRepository;
import entity.Producto;
import infraestructure.product.ProductRepository;
import java.util.ArrayList;
import util.EntityType;

/**
 *
 * @author robert
 */
public class ProductApplication {

    IProductRepository productRepository;
    
    public ProductApplication(){
        this.productRepository = new ProductRepository();
    }
    
    public void createProduct(Producto product) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ArrayList<Producto> getAllProducts(){
        ArrayList<Producto> actions=null;
        try{
            actions=productRepository.queryAll();
        }catch(Exception e){
            e.printStackTrace();
        }
        return actions;
    }
    
    public void refreshProducts(){
        EntityType.PRODUCTS = getAllProducts();
    }
    
}
