/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.transportunittype;

import base.transportunittype.ITransportUnitTypeRepository;
import entity.TipoUnidadTransporte;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Tools;

/**
 *
 * @author Alonso
 */
public class TransportUnitTypeRepository implements ITransportUnitTypeRepository{

    @Override
    public int insert(TipoUnidadTransporte object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(TipoUnidadTransporte object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<TipoUnidadTransporte> queryAll() {
        String hql = "from TipoUnidadTransporte";
        ArrayList<TipoUnidadTransporte> unitTransportTypes = null;
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try{
            trns = session.beginTransaction();
            Query q = session.createQuery(hql);
            unitTransportTypes = (ArrayList<TipoUnidadTransporte>) q.list();
            session.getTransaction().commit();
        }
        catch (RuntimeException e){
            if(trns != null){
                trns.rollback();
            }
            e.printStackTrace();
        }
        return unitTransportTypes;
    }

    @Override
    public int update(TipoUnidadTransporte object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoUnidadTransporte queryById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoUnidadTransporte queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
