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
    
    public void insert(OrdenInternamiento object) {
        try{
            InternmentRepository w = new InternmentRepository();
            w.insert(object);
        }
        catch (Exception e){
            
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
    
    
}
