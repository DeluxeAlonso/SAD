/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.session;

import base.session.ISessionRepository;
import entity.Sesion;
import java.util.ArrayList;
import java.util.Date;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Tools;

/**
 *
 * @author Nevermade
 */
public class SessionRepository implements ISessionRepository{

    @Override
    public int insert(Sesion object) {
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            session.save(object);                      
            session.getTransaction().commit();
            return object.getId();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
            return -1;
        }    
    }

    @Override
    public int delete(Sesion object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Sesion> queryAll() {
         Transaction trns = null;
        String hql="from Sesion";
        ArrayList<Sesion> sesions= null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q=session.createQuery(hql);            
            sesions=(ArrayList<Sesion>)q.list();                      
            session.getTransaction().commit();
            
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
            return null;
        }    
        return sesions;
    }

    @Override
    public int update(Sesion object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Sesion queryById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Sesion queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Sesion> queryBySession(Sesion object, Date dateI, Date dateF) {
        Transaction trns = null;
        String hql="from Sesion where (:user_id is null or usuario_id=:user_id) and "
                + "(:fechaIni is null or :fechaFin is null or inicio_sesion between :fechaIni and :fechaFin)";
        ArrayList<Sesion> sesions= null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q=session.createQuery(hql);
            if(object!=null && object.getUsuario()!=null)
                q.setParameter("user_id", object.getUsuario().getId());
            else
                q.setParameter("user_id", null);
            q.setParameter("fechaIni", dateI);
            q.setParameter("fechaFin", dateF);
            sesions=(ArrayList<Sesion>)q.list();                      
            session.getTransaction().commit();
            
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
            return null;
        }    
        return sesions;
    }
    
}
