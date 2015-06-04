/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.local;

import base.local.ILocalRepository;
import entity.Local;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.EntityState.Locals;
import util.Tools;

/**
 *
 * @author prote_000
 */
public class LocalRepository implements ILocalRepository{
    
    @Override
    public ArrayList<Local> queryLocalsByClient(int clientId) {
        String hql="FROM Local l WHERE l.cliente.id=:clienteId AND l.estado=:state";
        ArrayList<Local> locals=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("state", Locals.ACTIVO.ordinal());
            q.setParameter("clienteId", clientId);
            locals = (ArrayList<Local>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return locals; //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public ArrayList<Local> queryAll() {
        String hql="FROM Local l WHERE l.estado=:state";
        ArrayList<Local> locals=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("state", Locals.ACTIVO.ordinal());
            locals = (ArrayList<Local>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return locals; //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int insert(Local object) {
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            session.save(object);                      
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
            return -1;
        }
        return object.getId();
    }
    
    @Override
    public Boolean delete(int localId) {
        String hql="UPDATE Local l SET l.estado=:state WHERE l.id=:localId";
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createSQLQuery(hql);
            q.setParameter("state", Locals.INACTIVO.ordinal());
            q.setParameter("localId", localId); 
            q.executeUpdate();      
            session.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
            return false;
        } 
    }

    @Override
    public int delete(Local object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(Local object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Local queryById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Local queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
