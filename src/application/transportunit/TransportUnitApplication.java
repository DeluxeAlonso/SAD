/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.transportunit;

import base.transportunit.ITransportUnitRepository;
import entity.UnidadTransporte;
import infraestructure.transportunit.TransportUnitRepository;
import java.util.ArrayList;
import util.EntityType;

/**
 *
 * @author Alonso
 */
public class TransportUnitApplication {
    
    ITransportUnitRepository transportUnitRepository;
    
    public TransportUnitApplication(){
        this.transportUnitRepository = new TransportUnitRepository();
    }
    
    public ArrayList<UnidadTransporte> getAllTransportUnits(){
        ArrayList<UnidadTransporte> actions=null;
        try{
            actions=transportUnitRepository.queryAll();
        }catch(Exception e){
            e.printStackTrace();
        }
        return actions;
    }
    
    public Boolean createTransportUnit(UnidadTransporte transportUnit){
        Boolean response = false;
        try {
            response = transportUnitRepository.createTransportUnit(transportUnit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
    public Boolean updateTransportUnit(UnidadTransporte transportUnit){
        Boolean response = false;
        try {
            response = transportUnitRepository.updateTransportUnit(transportUnit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
    public void refreshTransportUnits(){
        EntityType.TRANSPORT_UNITS = getAllTransportUnits();
    }
    
}
