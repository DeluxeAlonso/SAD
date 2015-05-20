/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.pallet;

import base.pallet.IPalletRepository;
import entity.Pallet;
import java.util.ArrayList;
import java.util.List;
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
    public void insert(Pallet object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Pallet object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Pallet> queryAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Pallet object) {
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
    
}
