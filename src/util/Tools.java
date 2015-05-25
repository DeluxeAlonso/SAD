/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.hibernate.Session;

/**
 *
 * @author Nevermade
 */
public class Tools {

    public static String generatePassword(int length) {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32).substring(0, length);
    }
    
    public static Session getSessionInstance(){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        /*if(session!=null||session.isOpen() || session.isConnected()){            
            session.close();           
        }*/
        /*if(session==null)
            session=HibernateUtil.getSessionFactory().openSession();*/
        return session;        
    }
    
    public static void closeSessione(){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        if(session!=null){            
            session.close();
            
        }
    }
    
}
