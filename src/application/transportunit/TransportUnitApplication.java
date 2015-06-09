/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.transportunit;

import base.transportunit.ITransportUnitRepository;
import entity.Despacho;
import entity.GuiaRemision;
import entity.TipoUnidadTransporte;
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
    
    public ArrayList<UnidadTransporte> searchTransportUnits(String plate, TipoUnidadTransporte type){
        ArrayList<UnidadTransporte> actions=null;
        try{
            actions=transportUnitRepository.search(plate, type);
        }catch(Exception e){
            e.printStackTrace();
        }
        return actions;
    }
    
    public void refreshTransportUnits(){
        EntityType.TRANSPORT_UNITS = getAllTransportUnits();
    }
    
    public Boolean loadTransportUnit(String filename){
        Boolean response = false;
        try {
            response = transportUnitRepository.loadTransportUnit(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
    public ArrayList<GuiaRemision> getRemissionGuides(UnidadTransporte transportUnit, ArrayList<Despacho> deliveries){
        ArrayList<GuiaRemision> remissionGuides= new ArrayList<>();
        try{
            remissionGuides=transportUnitRepository.queryRemissionGuides(transportUnit,deliveries);
        }catch(Exception e){
            e.printStackTrace();
        }
        return remissionGuides;
    }

    public ArrayList<UnidadTransporte> getAvailableTransportUnits() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
