/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.internment;

import base.internment.IInternmentRepository;
import entity.Almacen;
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
    
    public void insert(Almacen object) {
        try{
            InternmentRepository w = new InternmentRepository();
            w.insert(object);
        }
        catch (Exception e){
            
        }
    }
    
    
}
