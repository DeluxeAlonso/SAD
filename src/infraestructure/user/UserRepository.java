/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.user;

import base.user.IUserRepository;
import entity.Usuario;
import java.util.ArrayList;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Tools;

/**
 *
 * @author Nevermade
 */
public class UserRepository implements IUserRepository {

    @Override
    public Usuario getUser(String email) {
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        Usuario user = null;
        String hql
                = "from Usuario where correo=:email";
        try {
            
            session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("email", email);
            user = (Usuario) q.uniqueResult();
            if(user!=null){
                Hibernate.initialize(user.getPerfil());                
            }
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int insert(Usuario object) {
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
        return 1;
        
    }

    @Override
    public int delete(Usuario object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Usuario> queryAll() {
        String hql="from Usuario";
        ArrayList<Usuario> users=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);              
            users = (ArrayList<Usuario>) q.list();             
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } 
        return users;
    }

    @Override
    public int update(Usuario object) {
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            session.update(object);                      
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
            return -1;
        } 
        return 1;
    }

    @Override
    public Usuario queryById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario queryById(String id) {
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        Usuario user = null;
        String hql
                = "from Usuario where id=:id";
        try {
            
            session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("id", id);
            user = (Usuario) q.uniqueResult();
            if(user!=null)
                Hibernate.initialize(user.getPerfil());
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }
    public ArrayList<Usuario> searchUser(Usuario user){
        String hql="from Usuario "
                + "where (:idPerfil is null or id_perfil=:idPerfil) and (correo like :correo) and "
                + "(:nombre is null or (concat(nombre,' ',apellido_paterno,' ',apellido_materno) like :nombreL)) and "
                + "(:estado is null or estado=:estado)";
        ArrayList<Usuario> users=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);            
            q.setParameter("correo", "%"+user.getCorreo()+"%");
            q.setParameter("estado", user.getEstado());
            if(user.getPerfil()!=null)
                q.setParameter("idPerfil", user.getPerfil().getId());
            else
                q.setParameter("idPerfil", null);
            if(user.getNombre()!=null){
                q.setParameter("nombre", user.getNombre());
                q.setParameter("nombreL", "%"+user.getNombre()+"%");
            }
            else{
                q.setParameter("nombre", null);
                q.setParameter("nombreL", null);
            }
            users = (ArrayList<Usuario>) q.list();             
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } 
        return users;
    }

}
