/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.internment;
import base.internment.IInternmentRepository;
import entity.Cliente;
import entity.OrdenInternamiento;
import entity.OrdenInternamientoXProducto;
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

        //@Override
        public void insertOrdenXProducto(OrdenInternamientoXProducto object) {
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            session.save(object);                      
            session.getTransaction().commit();
            //return object.getId();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
           // return -1;
        } 
    }
        
    public Boolean incCantOrderXProd(OrdenInternamientoXProducto object) {
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            session.saveOrUpdate(object);                      
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
    

    @Override
    public int update(OrdenInternamiento object) {
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
    public ArrayList<OrdenInternamiento> queryByType(int idType) {
        Session session = Tools.getSessionInstance();
        String hql = "FROM OrdenInternamiento p WHERE p.estado=:idType";
        ArrayList<OrdenInternamiento> orders = new ArrayList<>();
        Transaction trns = null;
        try{
            trns = session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("idType", idType);
            orders = (ArrayList<OrdenInternamiento>) q.list();
            session.getTransaction().commit();
        }
        catch (RuntimeException e){
            if(trns != null){
                trns.rollback();
            }
            e.printStackTrace();
        }
        return orders;
    }
    
    
    
    @Override
    public OrdenInternamiento queryById(int id) {
        String hql="FROM OrdenInternamiento WHERE id=:id";
        ArrayList<OrdenInternamiento> orden=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("id", id);
            orden = (ArrayList<OrdenInternamiento>) q.list();          
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

    @Override
    public OrdenInternamientoXProducto getProdOrder(OrdenInternamiento id) {
        String hql="FROM OrdenInternamientoXProducto WHERE ordenInternamiento=:id";
        ArrayList<OrdenInternamientoXProducto> orden=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("id", id);
            orden = (ArrayList<OrdenInternamientoXProducto>) q.list();          
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
    
    
    
    @Override
    public OrdenInternamiento queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
