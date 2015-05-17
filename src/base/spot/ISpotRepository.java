/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.spot;

import base.IRepository;
import entity.Ubicacion;
import java.util.ArrayList;

/**
 *
 * @author prote_000
 */
public interface ISpotRepository extends IRepository<Ubicacion>{
    ArrayList<Ubicacion> querySpotsByRack(int rackId);
}
