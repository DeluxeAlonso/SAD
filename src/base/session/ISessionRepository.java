/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.session;

import base.IRepository;
import entity.Sesion;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Nevermade
 */
public interface ISessionRepository extends IRepository<Sesion>{
    ArrayList<Sesion>queryBySession(Sesion session,Date dateI, Date dateF);
}
