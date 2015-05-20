/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.spot;

import base.spot.ISpotRepository;
import entity.Ubicacion;
import java.util.ArrayList;
import javafx.scene.effect.Light.Spot;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


import org.hibernate.criterion.Restrictions;

import util.HibernateUtil;

import util.Tools;

/**
 *
 * @author prote_000
 */
public class SpotRepository implements ISpotRepository{

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
    /*
    @Override
    public List querySpotsByRackWithContent(int rackId) {
        String hql="SELECT u.id as u_id, u.fila, u.columna, u.lado, p.id as p_id, p.ean128,p.fecha_registro "
                + "FROM Ubicacion u,Pallet p "
                + "WHERE u.id_rack=:rackId and p.id_ubicacion=u.id";
        
        List spots=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            
            //Criteria cr = session.createCriteria(Spot.class);
            //cr.add(Restrictions.eq("id_rack", rackId));
            q.setParameter("rackId", rackId);
            spots = (ArrayList<Ubicacion>) q.list();      
            //spots = cr.list(); 
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
    public List querySpotsByRackWithContent(int rackId) {
        String hql="SELECT u.id as u_id, u.fila, u.columna, u.lado, p.id as p_id, p.ean128,p.fecha_registro "
                + "FROM Ubicacion u,Pallet p "
                + "WHERE u.id_rack=:rackId and p.id_ubicacion=u.id";
        
        List spots=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            
            //Criteria cr = session.createCriteria(Spot.class);
            //cr.add(Restrictions.eq("id_rack", rackId));
            q.setParameter("rackId", rackId);
            spots = (ArrayList<Ubicacion>) q.list();      
            //spots = cr.list(); 
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
    public void insert(Ubicacion object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Ubicacion object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Ubicacion> queryAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Ubicacion object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Ubicacion queryById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Ubicacion queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
