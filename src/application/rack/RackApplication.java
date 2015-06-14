/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.rack;

import base.rack.IRackRepository;
import entity.Rack;
import infraestructure.rack.RackRepository;
import java.util.ArrayList;

/**
 *
 * @author prote_000
 */
public class RackApplication {
    private IRackRepository rackRepository;
    public RackApplication(){
        this.rackRepository = new RackRepository();
    }
    
    public ArrayList<Rack> queryRacksByWarehouse(int warehouse_id){
        ArrayList<Rack> racks = null;
        try {
            racks = rackRepository.queryRacksByWarehouse(warehouse_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return racks;
    }
    
    public ArrayList<Rack> queryAllByWarehouse(int warehouse_id){
        ArrayList<Rack> racks = null;
        try {
            racks = rackRepository.queryAllByWarehouse(warehouse_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return racks;
    }
    
    
    public ArrayList<Rack> queryByParameters(int idWare,int idCon){
        ArrayList<Rack> racks = null;
        try {
            racks = rackRepository.queryRacksByParameters(idWare, idCon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return racks;
    }
    
    
    
    public Rack queryById(int id){
        Rack rack = null;
        try {
            rack = rackRepository.queryById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rack;
    }
    
    public int insert(Rack object) {
        try{
            RackRepository w = new RackRepository();
            w.insert(object);
            return object.getId();
        }
        catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public int update(Rack object) {
        try{
            RackRepository w = new RackRepository();
            w.update(object);
            return object.getId();
        }
        catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }    
    
    
    public int active(Rack r){
        try{
            RackRepository w = new RackRepository();
            return w.active(r);
        }
        catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    
    public int inactive(Rack r){
        try{
            RackRepository w = new RackRepository();
            return w.inactive(r);
        }
        catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
}
