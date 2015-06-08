/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
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

    public static Session getSessionInstance() {
        Session session = null;
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        /*if(session!=null||session.isOpen() || session.isConnected()){            
         session.close();           
         }*/
        /*if(session==null)
         session=HibernateUtil.getSessionFactory().openSession();*/
        return session;
    }

    public static void closeSession() {
        Session session = null;
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        if (session != null) {
            session.close();

        }
    }

    public static String getCurrentIP() {

        InetAddress ip = null;
        try {

            ip = InetAddress.getLocalHost();

        } catch (UnknownHostException e) {

            e.printStackTrace();
            return "";

        }
        return ip.getHostAddress();
    }

    public static String getCurrentMac() {
        InetAddress ip = null;
        StringBuilder sb = null;
        try {

            ip = InetAddress.getLocalHost();

            //System.out.println("Current IP address : " + ip.getHostAddress());
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

            System.out.print("Current MAC address : ");

            sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }

        } catch (UnknownHostException | SocketException e) {

            e.printStackTrace();
            return "";

        }

        return sb.toString();
    }
}
