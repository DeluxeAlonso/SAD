/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.local;

import base.IRepository;
import entity.Local;
import java.util.ArrayList;

/**
 *
 * @author prote_000
 */
public interface ILocalRepository extends IRepository<Local>{
    public ArrayList<Local> queryLocalsByClient(int clientId);
    public Boolean delete(int localId);
}
