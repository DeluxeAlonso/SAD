/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.pallet;

import base.pallet.IPalletRepository;
import entity.OrdenInternamiento;
import entity.Pallet;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Tools;

/**
 *
 * @author prote_000
 */
public class PalletRepository implements IPalletRepository{

    @Override
    public ArrayList<Pallet> queryPalletsByRack(int rackId) {
        String hql="from Pallet p where p.ubicacion.id in (select u.id from Ubicacion u where u.rack.id=:rackId)";
        ArrayList<Pallet> pallets=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("rackId", rackId);
            pallets = (ArrayList<Pallet>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return pallets; //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Boolean updatePalletSpot(int palletId, int spotId) {
        String hql="UPDATE Pallet p SET p.id_ubicacion=:spotId WHERE p.id=:palletId";
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createSQLQuery(hql);
            q.setParameter("spotId", spotId);
            q.setParameter("palletId", palletId);   
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
    public int insert(Pallet object) {
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
    public int delete(Pallet object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Pallet> queryAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(Pallet object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pallet queryById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pallet queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Pallet getPalletsFromOrder(OrdenInternamiento id) {
        String hql="FROM Pallet WHERE ordenInternamiento=:id";
        ArrayList<Pallet> orden=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("id", id);
            orden = (ArrayList<Pallet>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        if (orden.size()==0)
            return null;
        else
            return orden.get(0); //To change body of generated methods, choose Tools | Templates.
    }
    
}
