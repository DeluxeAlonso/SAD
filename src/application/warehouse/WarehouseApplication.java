/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.warehouse;

import entity.Almacen;
import infraestructure.warehouse.WarehouseRepository;
/**
 *
 * @author KEVIN BROWN
 */
public class WarehouseApplication {
    public void insert(Almacen object) {
        try{
            WarehouseRepository w = new WarehouseRepository();
            w.insert(object);
        }
        catch (Exception e){
            
        }
    }
}
