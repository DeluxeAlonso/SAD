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
    public ArrayList<Ubicacion> querySpotsByWarehouse(int warehouseId) {
        String hql="FROM Ubicacion u WHERE u.rack.id IN (SELECT id FROM Rack r WHERE r.almacen.id=:warehouseId) AND u.estado!=:state ORDER BY u.rack.id, u.lado, u.fila, u.columna";
        ArrayList<Ubicacion> spots=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("state", Spots.INACTIVO.ordinal());
            q.setParameter("warehouseId", warehouseId);
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
    
    public ArrayList<Ubicacion> querySpotsByParameters(int wareId, int rackId) {
        String hql="FROM Ubicacion u WHERE (u.rack.almacen.id=:wareId) AND "+
                "(u.rack.id = :rackId OR :rackId=0) AND u.rack.estado!=:estado"+
                "  ORDER BY u.rack.id, u.fila, u.columna";
        ArrayList<Ubicacion> spots=null;
        //gracias baldeon por enseniarme a usar DAO
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("rackId", rackId);
            q.setParameter("wareId", wareId);
            q.setParameter("estado", EntityState.Racks.INACTIVO.ordinal());
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
    
    public ArrayList<Ubicacion> querySpotsByPosition(int rackId, 
                                int fil, int col, int lado) {
        String hql="FROM Ubicacion u WHERE (u.rack.id = :rackId OR :rackId=0) AND " + 
                "(u.fila=:fil  OR :fil=0) AND (u.columna=:col  OR :col=0) AND "+
                "((u.lado like :lado)  OR (:lado like 'todos')) " +
                "AND u.rack.estado!=:estado"+
                "  ORDER BY u.rack.id, u.fila, u.columna";
        String l = null;
        if (lado ==0) l="todos";
        else if (lado==1) l="A";
        else l ="B";
        
        ArrayList<Ubicacion> spots=null;
        //gracias baldeon por enseniarme a usar DAO
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("rackId", rackId);
            q.setParameter("fil", fil);
            q.setParameter("col", col);
            q.setParameter("lado",l);
            q.setParameter("estado", EntityState.Racks.INACTIVO.ordinal());
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
    public long verifySpots(int idRack) {
        
        
        String hql="Select count(1) FROM Ubicacion u WHERE u.rack.id=:rackId "
                + "AND (u.estado=:state1 OR u.estado=:state2)";
        String hql2 ="UPDATE Rack r SET estado=:stateNew WHERE r.id=:idRack";

        long number=0;
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q= session.createQuery(hql);
            q.setParameter("rackId", idRack);
            q.setParameter("state1", EntityState.Spots.OCUPADO.ordinal());
            q.setParameter("state2", EntityState.Spots.LIBRE.ordinal());
            number=((long)q.uniqueResult());
            
            if (number==0){
                Query q1= session.createQuery(hql2);
                q1.setParameter("stateNew", EntityState.Spots.INACTIVO.ordinal());
                q1.setParameter("idRack", idRack);
            }
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return number; //To change body of generated methods, choose Tools | Templates.
                
                
                
                
                
                
                
                
    }
    
    
}
