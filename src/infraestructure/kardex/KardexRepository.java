/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.kardex;

import base.kardex.IKardexRepository;
import entity.Kardex;
import entity.KardexId;
import java.util.ArrayList;
import java.util.Date;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Tools;

/**
 *
 * @author prote_000
 */
public class KardexRepository implements IKardexRepository{

    @Override
    public ArrayList<Kardex> queryByParameters(int idWarehouse,int idProduct, Date dateIni, Date dateEnd) {
        Session session = Tools.getSessionInstance();
        String hql = "FROM Kardex k WHERE k.almacen.id=:idWarehouse AND k.producto.id=:idProduct AND k.fecha BETWEEN :dateIni AND :dateEnd";
        ArrayList<Kardex> products = new ArrayList<>();
        Transaction trns = null;
        try{
            trns = session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("idWarehouse", idWarehouse);
            q.setParameter("idProduct", idProduct);
            q.setParameter("dateIni", dateIni);
            q.setParameter("dateEnd", dateEnd);
            products = (ArrayList<Kardex>) q.list();
            session.getTransaction().commit();
        }
        catch (RuntimeException e){
            if(trns != null){
                trns.rollback();
            }
            e.printStackTrace();
        }
        return products;
    }
    
    @Override
    public ArrayList<Kardex> queryByParameters(int idWarehouse,int idProduct) {
        Session session = Tools.getSessionInstance();
        String hql = "FROM Kardex k WHERE k.almacen.id=:idWarehouse AND k.producto.id=:idProduct ORDER BY k.fecha desc";
        ArrayList<Kardex> products = new ArrayList<>();
        Transaction trns = null;
        try{
            trns = session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("idWarehouse", idWarehouse);
            q.setParameter("idProduct", idProduct);
            products = (ArrayList<Kardex>) q.list();
            session.getTransaction().commit();
        }
        catch (RuntimeException e){
            if(trns != null){
                trns.rollback();
            }
            e.printStackTrace();
        }
        return products;
    }
    
    @Override
    public int insert(Kardex object) {
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            session.save(object);                      
            session.getTransaction().commit();
            return object.getId().getId();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
            return -1;
        }}

        @Override
        public int insertKardexID(KardexId object) {
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
        }}
    
    @Override
    public int delete(Kardex object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Kardex> queryAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(Kardex object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Kardex queryById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Kardex queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
