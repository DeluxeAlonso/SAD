/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.rack;

import base.rack.IRackRepository;
import entity.Rack;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.EntityState.Racks;
import util.HibernateUtil;
import util.Tools;

/**
 *
 * @author prote_000
 */
public class RackRepository implements IRackRepository{
    
    @Override
    public ArrayList<Rack> queryRacksByWarehouse(int warehouseId) {
        String hql="FROM Rack WHERE id_almacen=:warehouse_id AND estado=:state";
        ArrayList<Rack> warehouses=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("warehouse_id", warehouseId);
            q.setParameter("state", Racks.ACTIVO.ordinal());
            warehouses = (ArrayList<Rack>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
//            session.flush();
//            session.close();
        }
        return warehouses; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insert(Rack object) {
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
    public int delete(Rack object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Rack> queryAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(Rack object) {
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
    public Rack queryById(int id) {
        String hql="FROM Rack WHERE id=:id";
        ArrayList<Rack> racks=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("id", id);
            racks = (ArrayList<Rack>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        if (racks.size()==0)
            return null;
        else
            return racks.get(0); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rack queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Rack> queryRacksByParameters(int idWare, int idCon) {
        Session session = Tools.getSessionInstance();
        //String hql = "FROM Rack";
        String hql = "FROM Rack r WHERE (r.almacen.id=:idWare OR :idWare=0) AND (r.almacen.condicion.id=:idCon OR :idCon=0)";
        //String hql = "FROM Rack r WHERE (r.almacen.id=:idWare OR :idWare=0) AND (r.almacen.condicion.id=:idCon OR :idCon=0)";
        ArrayList<Rack> racks = new ArrayList<>();
        Transaction trns = null;
        try{
            trns = session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("idWare", idWare);
            q.setParameter("idCon", idCon);
            racks = (ArrayList<Rack>) q.list();
            session.getTransaction().commit();
        }
        catch (RuntimeException e){
            if(trns != null){
                trns.rollback();
            }
            e.printStackTrace();
        }
        return racks;
    }
}
