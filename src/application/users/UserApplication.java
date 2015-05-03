/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.users;

import base.users.IUserRepository;
import entity.Usuario;
import infraestructure.user.UserRepository;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nevermade
 */
public class UserApplication {

    private IUserRepository userRepository;
    

    public UserApplication() {
        this.userRepository = new UserRepository();
    }

    public boolean login(String correo, String password) {
        
        Usuario user=userRepository.getUser(correo);
        if(user!=null)
            return checkPassword(password,user.getPassword());
        return false;
    }
    
    public ArrayList<Usuario> getAllUsers(){
        return userRepository.queryAll();
    }
    public static byte[] computeHash(String x) {
        java.security.MessageDigest d = null;
        try {
            d = java.security.MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        d.reset();
        d.update(x.getBytes());
        return d.digest();
    }

    public static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    public boolean checkPassword(String password, String stored) {
        String hashedPass = null;
        try {
            hashedPass = byteArrayToHexString(computeHash(password));
        } catch (Exception ex) {
            Logger.getLogger(UserApplication.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (hashedPass.equals(stored)) {
            return true;
        } else {
            return false;
        }
    }

}
