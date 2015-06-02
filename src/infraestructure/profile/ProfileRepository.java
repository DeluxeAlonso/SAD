/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.profile;

import base.profile.IProfileRepository;
import entity.Perfil;
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
public class ProfileRepository implements IProfileRepository {

    @Override
    public int insert(Perfil object) {
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
    public int delete(Perfil object) {
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
    public ArrayList<Perfil> queryAll() {
        String hql = "from Perfil";
        ArrayList<Perfil> profiles = null;

        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {
            trns = session.beginTransaction();
            Query q = session.createQuery(hql);
            profiles = (ArrayList<Perfil>) q.list();            
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return profiles;
    }

    @Override
    public int update(Perfil object) {
        Transaction trns = null;        
        Session session = Tools.getSessionInstance();
        
        try {
            trns = session.beginTransaction();                      
            session.update(object);            
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
    public Perfil queryById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Perfil queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Perfil queryByName(String name){
        String hql = "from Perfil where nombre_perfil=:name";
        Perfil profile = null;

        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {
            trns = session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("name", name);
            profile=(Perfil)q.uniqueResult();
            Hibernate.initialize(profile.getUsuarios());
            if(profile!=null)
                Hibernate.initialize(profile.getAccions());
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return profile;
    }

}
