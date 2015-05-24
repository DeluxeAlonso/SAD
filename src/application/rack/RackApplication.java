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
}
