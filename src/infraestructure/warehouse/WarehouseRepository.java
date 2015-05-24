/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.warehouse;


import base.warehouse.IWarehouseRepository;
import entity.Almacen;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.EntityState.Warehouses;
import util.HibernateUtil;
import util.Tools;
/**
 *
 * @author KEVIN BROWN
 */
public class WarehouseRepository implements IWarehouseRepository{

    @Override
    public void insert(Almacen object) {
        System.out.print("Hola");
        System.out.print("Hola2");
    }

    @Override
    public void delete(Almacen object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Almacen> queryAll() {
        String hql="FROM Almacen WHERE estado=:state";
        ArrayList<Almacen> warehouses=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("state", Warehouses.ACTIVO.ordinal());           
            warehouses = (ArrayList<Almacen>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return warehouses; //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public ArrayList<Almacen> queryWarehousesByType(int type) {
        String hql="FROM Almacen WHERE id_tipo_almacen=:type AND estado=:state";
        ArrayList<Almacen> warehouses=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("type", type);
            q.setParameter("state", Warehouses.ACTIVO.ordinal());
            warehouses = (ArrayList<Almacen>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return warehouses; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Almacen object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Almacen queryById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Almacen queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
