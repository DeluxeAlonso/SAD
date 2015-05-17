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
/**
 *
 * @author KEVIN BROWN
 */
public class WarehouseApplication {
    
    private IWarehouseRepository warehouseRepository;
    public WarehouseApplication(){
        this.warehouseRepository = new WarehouseRepository();
    }
    
    public void insert(Almacen object) {
        try{
            WarehouseRepository w = new WarehouseRepository();
            w.insert(object);
        }
        catch (Exception e){
            
        }
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
