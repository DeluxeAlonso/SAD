/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.internment;
import base.internment.IInternmentRepository;
import entity.Cliente;
import entity.OrdenInternamiento;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.EntityState;
import util.Tools;


/**
 *
 * @author KEVIN BROWN
 */
public class InternmentRepository implements IInternmentRepository {

    @Override
    public int insert(OrdenInternamiento object) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return -1;
    }

    @Override
    public int delete(OrdenInternamiento object) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return -1;
    }

    @Override
    public ArrayList<OrdenInternamiento> queryAll() {
         String hql="FROM OrdenInternamiento c WHERE c.estado=:state";
        ArrayList<OrdenInternamiento> internmentOrders=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);    
            q.setParameter("state", EntityState.InternmentOrders.REGISTRADA.ordinal());          
            internmentOrders = (ArrayList<OrdenInternamiento>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } 
        return internmentOrders;
    }
    
    public ArrayList<OrdenInternamiento> queryAllItem() {
         String hql="FROM OrdenInternamientoXItemProducto c WHERE c.estado=:state";
        ArrayList<OrdenInternamiento> internmentOrders=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);    
            q.setParameter("state", EntityState.InternmentOrders.REGISTRADA.ordinal());          
            internmentOrders = (ArrayList<OrdenInternamiento>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } 
        return internmentOrders;
    }

    @Override
    public int update(OrdenInternamiento object) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return -1;
    }

    @Override
    public OrdenInternamiento queryById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OrdenInternamiento queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
