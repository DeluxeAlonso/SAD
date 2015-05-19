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
        Session session=HibernateUtil.getSessionFactory().getCurrentSession();
        if(session.isOpen()){            
            session.close();            
        }
        return HibernateUtil.getSessionFactory().openSession();        
    }
    
    public static void closeSession(){
        Session session=HibernateUtil.getSessionFactory().getCurrentSession();
        if(session.isOpen()){            
            session.close();            
        }
    }
}
