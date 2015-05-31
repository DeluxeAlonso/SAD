/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.action;

import base.action.IActionRepository;
import entity.Accion;
import java.util.ArrayList;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Tools;

/**
 *
 * @author dabarca
 */
public class ActionRepository implements IActionRepository{

    @Override
    public int insert(Accion object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(Accion object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Accion> queryAll() {
        String hql="from Accion";
        ArrayList<Accion> actions=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);              
            actions = (ArrayList<Accion>) q.list();            
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return actions;
    }

    @Override
    public int update(Accion object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Accion queryById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Accion queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public Accion queryByName(String name){
        String hql="from Accion where nombre=:name";
        Accion action=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("name", name);            
            action =(Accion) q.uniqueResult();
            //Hibernate.initialize(action.getPerfils());
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return action;
    }

    @Override
    public ArrayList<Accion> queryAllParents() {
        String hql="from Accion where idPadre=null";
        ArrayList<Accion> actions=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);                       
            actions =(ArrayList<Accion>)q.list();
            //Hibernate.initialize(action.getPerfils());
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return actions;
    }

    @Override
    public ArrayList<Accion> queryChildByParent(int idPadre) {
        String hql="from Accion where idPadre=:idPadre";
        ArrayList<Accion> actions=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);   
            q.setParameter("idPadre", idPadre);
            actions =(ArrayList<Accion>)q.list();
            //Hibernate.initialize(action.getPerfils());
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return actions;
    }
}
