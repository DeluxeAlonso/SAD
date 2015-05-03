/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.user;

import base.users.IUserRepository;
import entity.PreguntaSecreta;
import entity.Usuario;
import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;
import util.Tools;

/**
 *
 * @author Nevermade
 */
public class UserRepository implements IUserRepository {

    @Override
    public Usuario getUser(String email) {
        Usuario user = null;
        String hql
                = "from Usuario where correo=:email";
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("email", email);
            user = (Usuario) q.uniqueResult();
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return user;
    }

    @Override
    public void insert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Usuario> queryAll() {
        String hql="from Usuario";
        ArrayList<Usuario> users=null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);              
            users = (ArrayList<Usuario>) q.list();
            for(Usuario user : users){
                user.setIdUsuario((String)Tools.setDefault(user.getIdUsuario(),""));
                user.setNombre((String)Tools.setDefault(user.getNombre(),""));
                user.setApellidoPaterno((String)Tools.setDefault(user.getApellidoPaterno(),""));
                user.setApellidoMaterno((String)Tools.setDefault(user.getApellidoMaterno(),""));
                user.setPassword((String)Tools.setDefault(user.getPassword(), ""));
                user.setRespuesta((String)Tools.setDefault(user.getRespuesta(), ""));
                //user.setEstado((Byte)Tools.setDefault(user.getEstado(),0));
                user.setPreguntaSecreta((PreguntaSecreta)Tools.setDefault(user.getPreguntaSecreta(), null));
            }
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return users;
    }

    @Override
    public void update(Usuario object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario queryById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
