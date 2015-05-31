/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.action;

import base.IRepository;
import entity.Accion;
import java.util.ArrayList;

/**
 *
 * @author dabarca
 */
public interface IActionRepository extends IRepository<Accion>{
    Accion queryByName(String name);
    ArrayList<Accion> queryAllParents();
    ArrayList<Accion> queryChildByParent(int idPadre);
}
