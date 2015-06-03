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
    public int insert(Producto object) {
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
    public int delete(Producto object) {
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
    public Producto queryById(int id) {
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        Producto producto = null;
        String hql
                = "from Producto where id=:id";
        try {
            
            session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("id", id);
            producto = (Producto) q.uniqueResult();
            if(producto!=null)
                Hibernate.initialize(producto.getNombre());
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return producto;
    }
    

    @Override
    public ArrayList<Producto> queryByType(int idType) {
        Session session = Tools.getSessionInstance();
        // WHERE p.tipoProducto.id=:idType
        String hql = "FROM Producto p WHERE p.condicion.id=:idType";
        ArrayList<Producto> products=null;
        Transaction trns = null;
        try{
            trns = session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("idType", idType);
            products = (ArrayList<Producto>) q.list();
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
    
    
    public ArrayList<Producto> queryByCondition(int idType) {
        Session session = Tools.getSessionInstance();
        String hql = "FROM Producto p WHERE p.condicion.id=:idType";
        ArrayList<Producto> products = new ArrayList<>();
        Transaction trns = null;
        try{
            trns = session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("idType", idType);
            products = (ArrayList<Producto>) q.list();
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
    
    public ArrayList<Producto> searchProduct(Producto product){
        String hql="from Producto "
                + "where (:id is null or id=:id) and (nombre like :name) and stock_total>0";
        ArrayList<Producto> products=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("id", product.getId());
            q.setParameter("name", "%" + product.getNombre() + "%");
            products = (ArrayList<Producto>) q.list();             
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } 
        return products;
    }

    @Override
    public int update(Producto object) {
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
    public Producto queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
