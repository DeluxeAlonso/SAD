/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.transportunittype;

import base.transportunittype.ITransportUnitTypeRepository;
import entity.TipoUnidadTransporte;
import infraestructure.transportunittype.TransportUnitTypeRepository;
import java.util.ArrayList;

/**
 *
 * @author Alonso
 */
public class TransportUnitTypeApplication {
    
    ITransportUnitTypeRepository transportUnitTypeRepository;
    
    public TransportUnitTypeApplication(){
        this.transportUnitTypeRepository = new TransportUnitTypeRepository();
    }
    
    public ArrayList<TipoUnidadTransporte> getAllTransportUnitTypes(){
        ArrayList<TipoUnidadTransporte> transportUnitTypes = null;
        try{
            transportUnitTypes = transportUnitTypeRepository.queryAll();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return transportUnitTypes;
    }
    
}
