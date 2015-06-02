/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.spot;

import base.IRepository;
import entity.Ubicacion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prote_000
 */
public interface ISpotRepository extends IRepository<Ubicacion>{
    //ArrayList<Ubicacion> querySpotsByRack(int rackId);
    ArrayList<Ubicacion> queryEmptySpotsByRack(int rackId);
    Boolean updateSpotOccupancy(int spotId,int occupancyState);
    ArrayList<Ubicacion> querySpotsByRack(int rackId);
    ArrayList<Ubicacion> querySpotsByParameters(int wareId, int rackId);
}
