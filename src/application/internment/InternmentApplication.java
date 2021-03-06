/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.internment;

import base.internment.IInternmentRepository;
import entity.Almacen;
import entity.OrdenInternamiento;
import entity.OrdenInternamientoXProductoId;
import entity.OrdenInternamientoXProducto;
import entity.Producto;
import infraestructure.internment.InternmentRepository;
import java.util.ArrayList;

/**
 *
 * @author KEVIN BROWN
 */
public class InternmentApplication {
    
    
     private IInternmentRepository internmentRepository;
     
    public InternmentApplication(){
        this.internmentRepository = new InternmentRepository();
    }
    
    public int insert(OrdenInternamiento object) {
        try{
            InternmentRepository w = new InternmentRepository();
            w.insert(object);
            return object.getId();
        }
        catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    
    public ArrayList<OrdenInternamiento> queryAll(){
        ArrayList<OrdenInternamiento> internmentOrder = null;
        try {
            internmentOrder = internmentRepository.queryAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return internmentOrder;
    }
    
    public OrdenInternamiento queryById(int id){
        OrdenInternamiento orden = null;
        try {
            orden = internmentRepository.queryById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orden;
    }
    
    public ArrayList<OrdenInternamiento> queryByType(int idType){
        ArrayList<OrdenInternamiento> products=null;
        try{
            products = internmentRepository.queryByType(idType);
        }catch(Exception e){
            e.printStackTrace();
        }
        return products;
    }
    
        public Boolean incCantOrderXProd(OrdenInternamientoXProducto object){
        Boolean response = false;
        try {
            response = internmentRepository.incCantOrderXProd(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
    
    public OrdenInternamientoXProducto getProdOrder(OrdenInternamiento idType){
        OrdenInternamientoXProducto products=null;
        try{
            products = internmentRepository.getProdOrder(idType);
        }catch(Exception e){
            e.printStackTrace();
        }
        return products;
    }
    
    public OrdenInternamientoXProducto getOrderProduct(Producto product, Integer quantity){
        OrdenInternamientoXProducto products=null;
        try{
            products = internmentRepository.getOrderProd(product, quantity);
        }catch(Exception e){
            e.printStackTrace();
        }
        return products;
    }
    
    
    public void insertOrdenXProducto(OrdenInternamientoXProducto object){
            try{
            InternmentRepository w = new InternmentRepository();
            w.insertOrdenXProducto(object);
            //return object.getId();
        }
        catch (Exception e){
            e.printStackTrace();
            //return -1;
        }
            
        }
    
    public void updateOrdenXProducto(OrdenInternamientoXProducto object){
            try{
            InternmentRepository w = new InternmentRepository();
            w.updateOrdenXProducto(object);
            //return object.getId();
        }
        catch (Exception e){
            e.printStackTrace();
            //return -1;
        }
            
    }
    
    public int update(OrdenInternamiento object) {
        try{
            InternmentRepository i = new InternmentRepository();
            return i.update(object);
        }
        catch (Exception e){
            return -1;
        }
        
    } 
    
        
}
