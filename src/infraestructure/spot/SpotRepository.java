/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.spot;

import base.spot.ISpotRepository;
import entity.Ubicacion;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.effect.Light.Spot;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


import org.hibernate.criterion.Restrictions;
import util.EntityState;
import util.EntityState.Spots;

import util.HibernateUtil;

import util.Tools;

/**
 *
 * @author prote_000
 */
public class SpotRepository implements ISpotRepository{
    /*
    @Override
    public ArrayList<Ubicacion> querySpotsByRack(int rackId) {
        String hql="from Ubicacion where id_rack=:rackId";
        ArrayList<Ubicacion> spots=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("rackId", rackId);
            spots = (ArrayList<Ubicacion>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } 
        return spots; //To change body of generated methods, choose Tools | Templates.
    }
    */
    @Override
    public ArrayList<Ubicacion> queryEmptySpotsByRack(int rackId) {
        String hql="FROM Ubicacion u WHERE u.rack.id=:rackId AND u.estado=:state ORDER BY u.lado, u.fila";
        ArrayList<Ubicacion> spots=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("state", Spots.LIBRE.ordinal());
            q.setParameter("rackId", rackId);
            spots = (ArrayList<Ubicacion>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return spots; //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Boolean updateSpotOccupancy(int spotId,int occupancyState) {
        String hql="UPDATE Ubicacion u SET u.estado=:occupancyState WHERE u.id=:spotId";
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createSQLQuery(hql);
            q.setParameter("occupancyState", occupancyState);
            q.setParameter("spotId", spotId);  
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
    public int insert(Ubicacion object) {
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
    public int delete(Ubicacion object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Ubicacion> queryAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(Ubicacion object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Ubicacion queryById(int id) {
        String hql="FROM Ubicacion u WHERE u.id=:id";
        ArrayList<Ubicacion> spots=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("id", id);
            spots = (ArrayList<Ubicacion>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        if (spots==null) return null;
        else return spots.get(0);
    }

    @Override
    public Ubicacion queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Ubicacion> querySpotsByRack(int rackId) {
        String hql="FROM Ubicacion u WHERE u.rack.id=:rackId ORDER BY u.fila, u.columna";
        ArrayList<Ubicacion> spots=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("rackId", rackId);
            spots = (ArrayList<Ubicacion>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return spots; //To change body of generated methods, choose Tools | Templates.
    }
    
}
