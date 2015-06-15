/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.warehouse;


import base.warehouse.IWarehouseRepository;
import entity.Almacen;
import entity.Rack;
import entity.Ubicacion;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.EntityState;
import util.EntityState.Warehouses;
import util.Tools;
/**
 *
 * @author KEVIN BROWN
 */
public class WarehouseRepository implements IWarehouseRepository{

    @Override
    public int insert(Almacen object) {
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        Set racks = object.getRacks();
        Set spots=null;
        try {            
            trns=session.beginTransaction();
            session.save(object);                      
            for (Object r: racks){
                session.save((Rack)r);
                spots=((Rack)r).getUbicacions();
                for (Object u: spots){
                    session.save(u);
                }
            }
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
    public int delete(Almacen object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Almacen> queryAll() {
        String hql="FROM Almacen WHERE estado=:state";
        ArrayList<Almacen> warehouses=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("state", Warehouses.ACTIVO.ordinal());           
            warehouses = (ArrayList<Almacen>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return warehouses; //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public ArrayList<Almacen> queryWarehousesByType(int type) {
        String hql="FROM Almacen WHERE id_condicion=:type AND estado=:state";
        ArrayList<Almacen> warehouses=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("type", type);
            q.setParameter("state", Warehouses.ACTIVO.ordinal());
            warehouses = (ArrayList<Almacen>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return warehouses; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(Almacen object) {
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            session.saveOrUpdate(object);                      
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
    public Almacen queryById(int id) {
        String hql="FROM Almacen WHERE id=:id";
        ArrayList<Almacen> warehouses=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("id", id);
            warehouses = (ArrayList<Almacen>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        if (warehouses.size()==0)
            return null;
        else
            return warehouses.get(0); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Almacen queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Almacen> queryByParameters(int wareId, int conId, int state) {
        String hql="FROM Almacen a WHERE (a.id=:wareId OR :wareId=0) AND "+
                "(a.condicion.id = :conId OR :conId=0) AND "+ 
                "(a.estado =:state OR :state = -1)"+
                "  ORDER BY a.id, a.condicion, a.estado";
        ArrayList<Almacen> warehouses=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("wareId", wareId);
            q.setParameter("conId", conId);
            q.setParameter("state", state);
            warehouses = (ArrayList<Almacen>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return warehouses; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int inactive(Almacen a) {
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        String hql = "UPDATE Ubicacion u SET estado = :state WHERE u.rack.almacen.id=:wareId";
        //String hql = "UPDATE Ubicacion u SET estado = :state WHERE u.rack.id in (Select id from Rack r where r.almacen.id=:wareId)";
        String hql2 = "UPDATE Rack SET estado = :state WHERE almacen.id = :wareId";
        long nOcupados=0;
        long zero=0;
        String veri = "SELECT count(1) FROM Ubicacion u WHERE u.rack.almacen.id = :wareId AND u.estado = :state";
        try {            
            trns=session.beginTransaction();
            Query q1 = session.createQuery(veri);
            q1.setParameter("wareId", a.getId());
            q1.setParameter("state", EntityState.Spots.OCUPADO.ordinal());
            nOcupados = ((long)q1.uniqueResult());
            if (nOcupados > 0){
                session.getTransaction().commit();
                return 1;
            }
            else {
                a.setEstado(EntityState.Warehouses.INACTIVO.ordinal());
                session.saveOrUpdate(a);                      

                Query q = session.createQuery(hql);
                q.setParameter("wareId", a.getId());
                q.setParameter("state",EntityState.Spots.INACTIVO.ordinal() );
                q.executeUpdate();
                Query q2 = session.createQuery(hql2);
                q2.setParameter("wareId", a.getId());
                q2.setParameter("state",EntityState.Racks.INACTIVO.ordinal() );
                q2.executeUpdate();
                session.getTransaction().commit();
            
                return 0;
            }
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
            return -1;
        } 
    }
    
    
    @Override
    public int active(Almacen a) {
        a.setEstado(EntityState.Spots.LIBRE.ordinal());
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        String hql = "UPDATE Ubicacion u SET estado = :state WHERE u.rack.almacen.id = :wareId";
        try {            
            trns=session.beginTransaction();
            session.saveOrUpdate(a);                      
            
            Query q = session.createQuery(hql);
            q.setParameter("wareId", a.getId());
            q.setParameter("state",EntityState.Spots.LIBRE.ordinal() );
            q.executeUpdate();
            session.getTransaction().commit();
            return 0;
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
            return -1;
        } 
    }

    @Override
    public boolean isFullRack(Almacen a) {
        long nRacks=0;
        String hql = "Select count(1) FROM Rack r WHERE r.almacen.id=:wareId";
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try{
            trns=session.beginTransaction();           
            
            Query q = session.createQuery(hql);
            q.setParameter("wareId", a.getId());
            nRacks = ((long)q.uniqueResult());
            session.getTransaction().commit();
            if (nRacks>=a.getCapacidad()){
                return true;
            }else return false;
        }catch (Exception e){
            return true;
        }
            
    }
}
