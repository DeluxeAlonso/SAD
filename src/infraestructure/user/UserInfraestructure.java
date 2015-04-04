/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.user;

import entity.Usuario;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author Nevermade
 */
public class UserInfraestructure {
     public Usuario getUser(String email, String password) {
        Usuario user=null;
        String hql
                = "from Usuario where correo='" + email + "' and password='" + password + "'";
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setMaxResults(1);
            user=(Usuario)q.uniqueResult();            
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return user;
    }
}
