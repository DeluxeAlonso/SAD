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
    
    public ArrayList<Ubicacion> querySpotsByRack(int rackId){
        ArrayList<Ubicacion> spots = null;
        try {
            spots = spotRepository.querySpotsByRack(rackId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spots;
    }
    
    public List querySpotsByRackWithContent(int rackId){
        List<Ubicacion> spots = null;
        try {
            spots = spotRepository.querySpotsByRackWithContent(rackId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spots;
    }
    
    public ArrayList<Ubicacion> queryEmptySpotsByRack(int rackId){
        ArrayList<Ubicacion> spots = null;
        try {
            spots = spotRepository.querySpotsByRack(rackId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spots;
    }
    
}
