/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.rack;

import base.IRepository;
import entity.Rack;
import java.util.ArrayList;

/**
 *
 * @author prote_000
 */
public interface IRackRepository extends IRepository<Rack>{
    ArrayList<Rack> queryRacksByWarehouse(int warehouseId);
}
