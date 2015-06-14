/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.spot;

import base.spot.ISpotRepository;
import entity.Ubicacion;
import infraestructure.spot.SpotRepository;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prote_000
 */
public class SpotApplication {
    private ISpotRepository spotRepository;
    public SpotApplication(){
        this.spotRepository = new SpotRepository();
    }
    /*
    public ArrayList<Ubicacion> querySpotsByRack(int rackId){
        ArrayList<Ubicacion> spots = null;
        try {
            spots = spotRepository.querySpotsByRack(rackId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spots;
    }
    */
    
    public int insert(Ubicacion object) {
        try{
            SpotRepository w = new SpotRepository();
            w.insert(object);
            return object.getId();
        }
        catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    
    public Ubicacion queryById(int id) {
        Ubicacion u=null;
        try{
            SpotRepository w = new SpotRepository();
            u = w.queryById(id);
            return u;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<Ubicacion> queryEmptySpotsByRack(int rackId){
        ArrayList<Ubicacion> spots = null;
        try {
            spots = spotRepository.queryEmptySpotsByRack(rackId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spots;
    }
    
    public ArrayList<Ubicacion> querySpotsByWarehouse2(int warehouseId){
        ArrayList<Ubicacion> spots = null;
        try {
            spots = spotRepository.querySpotsByWarehouse2(warehouseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spots;
    }
    
    public ArrayList<Ubicacion> querySpotsByWarehouse(int warehouseId){
        ArrayList<Ubicacion> spots = null;
        try {
            spots = spotRepository.querySpotsByWarehouse(warehouseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spots;
    }
    
    public ArrayList<Ubicacion> queryByParameters(int idWare,int idCon){
        ArrayList<Ubicacion> spots = null;
        try {
            spots = spotRepository.querySpotsByParameters(idWare, idCon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spots;
    }

    public ArrayList<Ubicacion> querySpotsByRack(int rackId){
        ArrayList<Ubicacion> spots = null;
        try {
            spots = spotRepository.querySpotsByRack(rackId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spots;
    }    
    
    public ArrayList<Ubicacion> queryByPosition(int rackId, int fil, int col, int lado){
        ArrayList<Ubicacion> spots = null;
        try {
            spots = spotRepository.querySpotsByPosition(rackId,fil,col,lado);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spots;
    }    
    public Boolean updateSpotOccupancy(int spotId,int occupancyState){
        Boolean response = false;
        try {
            response = spotRepository.updateSpotOccupancy(spotId,occupancyState);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    public long verifySpots(int idRack){
        long a=0;
        try {
            a = spotRepository.verifySpots(idRack);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
        return a;
    }
}
