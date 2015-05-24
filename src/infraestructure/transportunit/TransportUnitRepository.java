/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.transportunit;

import base.transportunit.ITransportUnitRepository;
import entity.TipoUnidadTransporte;
import entity.UnidadTransporte;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Tools;

/**
 *
 * @author Alonso
 */
public class TransportUnitRepository implements ITransportUnitRepository{

    @Override
    public Boolean createTransportUnit(UnidadTransporte transportUnit){
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            session.save(transportUnit);                      
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
    public void insert(UnidadTransporte object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(UnidadTransporte object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<UnidadTransporte> queryAll() {
        String hql = "from UnidadTransporte";
        ArrayList<UnidadTransporte> unitTransport = null;
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try{
            trns = session.beginTransaction();
            Query q = session.createQuery(hql);
            unitTransport = (ArrayList<UnidadTransporte>) q.list();
            session.getTransaction().commit();
        }
        catch (RuntimeException e){
            if(trns != null){
                trns.rollback();
            }
            e.printStackTrace();
        }
        return unitTransport;
    }
    
    public Boolean updateTransportUnit(UnidadTransporte transportUnit){
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            session.update(transportUnit);                      
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
    public void update(UnidadTransporte object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UnidadTransporte queryById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UnidadTransporte queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
