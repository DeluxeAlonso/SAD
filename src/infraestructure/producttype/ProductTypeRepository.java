/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.producttype;

import infraestructure.product.*;
import base.product.IProductRepository;
import base.producttype.IProductTypeRepository;
import entity.Producto;
import entity.TipoProducto;
import java.util.ArrayList;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Tools;

/**
 *
 * @author Alonso
 */
public class ProductTypeRepository implements IProductTypeRepository {

    @Override
    public ArrayList<TipoProducto> queryByType(int idType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insert(TipoProducto object) {
        Transaction trns = null;        
        Session session = Tools.getSessionInstance();
        
        try {
            trns = session.beginTransaction();                      
            session.save(object);            
            trns.commit();
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
    public int delete(TipoProducto object) {
        Transaction trns = null;
        Session session = Tools.getSessionInstance();

        try {
            trns = session.beginTransaction();
            session.delete(object);
            trns.commit();            
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
    public ArrayList<TipoProducto> queryAll() {
        Session session = Tools.getSessionInstance();
        String hql = "from TipoProducto";
        ArrayList<TipoProducto> products = new ArrayList<TipoProducto>();
        Transaction trns = null;
        try{
            trns = session.beginTransaction();
            Query q = session.createQuery(hql);
            products = (ArrayList<TipoProducto>) q.list();
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
    public int update(TipoProducto object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoProducto queryById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoProducto queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
