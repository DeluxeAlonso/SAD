/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.product;

import base.product.IProductRepository;
import entity.Producto;
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
public class ProductRepository implements IProductRepository {

    @Override
    public void insert(Producto object) {
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
        }
    }

    @Override
    public void delete(Producto object) {
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
        }
    }

    @Override
    public ArrayList<Producto> queryAll() {
        Session session = Tools.getSessionInstance();
        String hql = "from Producto";
        ArrayList<Producto> products = new ArrayList<>();
        Transaction trns = null;
        try{
            trns = session.beginTransaction();
            Query q = session.createQuery(hql);
            products = (ArrayList<Producto>) q.list();
            for (int i = 0; i < products.size(); i++) {
                if(products.get(i)!=null)
                    Hibernate.initialize(products.get(i).getCondicion());                
            }
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
    public void update(Producto object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Producto queryById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Producto queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
