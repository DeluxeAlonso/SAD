/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.client;

import base.client.IClientRepository;
import entity.Cliente;
import entity.Local;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.EntityState.Clients;
import util.Tools;

/**
 *
 * @author prote_000
 */
public class ClientRepository implements IClientRepository{

    @Override
    public int insert(Cliente object) {
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
    public ArrayList<Cliente> queryAll() {
        String hql="FROM Cliente c WHERE c.estado=:state";
        ArrayList<Cliente> clients=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);    
            q.setParameter("state", Clients.ACTIVO.ordinal());          
            clients = (ArrayList<Cliente>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } 
        return clients;
    }
    
    @Override
    public Boolean delete(int clientId) {
        String hql="UPDATE Cliente c SET c.estado=:state WHERE c.id=:clientId";
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createSQLQuery(hql);
            q.setParameter("state", Clients.INACTIVO.ordinal());
            q.setParameter("clientId", clientId); 
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
    /*
    @Override
    public void delete(Cliente object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    */
    @Override
    public int update(Cliente object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Cliente queryById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Cliente queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(Cliente object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Local queryLocalById(Cliente client, Local local) {
        String hql="FROM Local l WHERE l.estado=1 and l.cliente=:cliente and l.cliente.estado=1 and l.id=:local";
        Local selectedLocal =null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);    
            q.setParameter("cliente", client);  
            q.setParameter("local", local.getId());
            selectedLocal = (Local)q.uniqueResult();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } 
        return selectedLocal;
    }
    
}
