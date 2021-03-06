/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.warehouse;

import base.IRepository;
import entity.Usuario;
import entity.Almacen;
import entity.Rack;
import java.util.ArrayList;

/**
 *
 * @author KEVIN BROWN
 */
public interface IWarehouseRepository extends IRepository<Almacen>{
    ArrayList<Almacen> queryWarehousesByType(int type);
    ArrayList<Almacen> queryByParameters(int wareId,int conId, int state );
    boolean isFullRack(Almacen a);
    int inactive(Almacen a);
    int active(Almacen a);
    long getNumberRacks(Almacen a);
    boolean isOcupy(Almacen a);
}