/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.warehouse;

import base.warehouse.IWarehouseRepository;
import entity.Almacen;
import infraestructure.warehouse.WarehouseRepository;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Tools;
/**
 *
 * @author KEVIN BROWN
 */
public class WarehouseApplication {
    
    private IWarehouseRepository warehouseRepository;
    public WarehouseApplication(){
        this.warehouseRepository = new WarehouseRepository();
    }
    
    public int insert(Almacen object) {
        try{
            WarehouseRepository w = new WarehouseRepository();
            return w.insert(object);
        }
        catch (Exception e){
            
        }
        return -1;
    }
    
    public int update(Almacen object) {
        try{
            WarehouseRepository w = new WarehouseRepository();
            return w.update(object);
        }
        catch (Exception e){
            return -1;
        }
        
    }
    
    
    
    public Almacen queryById(int id) {
        Almacen a =null;
        try {            
            WarehouseRepository w = new WarehouseRepository();
            a=w.queryById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }
    
    
    
    public ArrayList<Almacen> queryAll(){
        ArrayList<Almacen> warehouses = null;
        try {
            warehouses = warehouseRepository.queryAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return warehouses;
    }
    
    public ArrayList<Almacen> queryWarehousesByType(int type){
        ArrayList<Almacen> warehouses = null;
        try {
            warehouses = warehouseRepository.queryWarehousesByType(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return warehouses;
    }
}
