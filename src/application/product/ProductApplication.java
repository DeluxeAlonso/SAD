
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
    
    public int insert(Producto product) {
        int i =1;
        try{
            i = productRepository.insert(product);
        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
        return i;
    }
    public int update(Producto product) {
        int i =1;
        try{
            return productRepository.update(product);
        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    
       
    public void createProduct(Producto product) {
        try{
            int i = productRepository.insert(product);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
           public Producto queryById(int id){
        Producto p = null;
        try{
            p = productRepository.queryById(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return p;
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
    
    public ArrayList<Producto> queryByType(int idType){
        ArrayList<Producto> products=null;
        try{
            products = productRepository.queryByType(idType);
        }catch(Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return products;
    }
    
    public ArrayList<Producto> queryByCondition(int idType){
        ArrayList<Producto> products=null;
        try{
            products = productRepository.queryByCondition(idType);
        }catch(Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return products;
    }
    
    public void delete(Producto product){
        try{
            productRepository.delete(product);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void refreshProducts(){
        EntityType.PRODUCTS = getAllProducts();
    }
    
    public ArrayList<Producto> searchProduct(Producto product){
        ArrayList<Producto> products=null;
        try{
            products = productRepository.searchProduct(product);
        }catch(Exception e){
            e.printStackTrace();
        }
        return products;
    }
    
}
