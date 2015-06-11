/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.user;

import base.user.IUserRepository;
import entity.Usuario;
import infraestructure.user.UserRepository;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Nevermade
 */
public class UserApplication {

    private IUserRepository userRepository;
    private sun.misc.BASE64Encoder base64encoder = new BASE64Encoder();
    private sun.misc.BASE64Decoder base64decoder = new BASE64Decoder();
    SecretKey key=null;
    public UserApplication() {
        this.userRepository = new UserRepository();
        DESKeySpec keySpec;
        try {
            keySpec = new DESKeySpec("daekef is the best".getBytes("UTF8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            key = keyFactory.generateSecret(keySpec);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UserApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(UserApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(UserApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public Usuario login(String correo, String password) {
        Usuario user=null;
        try {
            user= userRepository.getUser(correo);
            
            if(!decrypt(user.getPassword()).equals(password))
                return null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }
    
    public boolean doesUserExist(String correo){
        Usuario user=null;
        try {
            user= userRepository.getUser(correo);            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user!=null?true:false;
    }

    public void createUser(Usuario user) {
        user.setPassword(encrypt(user.getPassword()));
        try{
            userRepository.insert(user);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Usuario> getAllUsers() {
        ArrayList<Usuario> users = null;
        try {
            users = userRepository.queryAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    

    public String encrypt(String x) {
        
        String encryptedpassword=null;
        try {
            // ENCODE plainTextPassword String
            byte[] cleartext = x.getBytes("UTF8");
            Cipher cipher = Cipher.getInstance("DES"); // cipher is not thread safe
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encryptedpassword= base64encoder.encode(cipher.doFinal(cleartext));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UserApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(UserApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(UserApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(UserApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(UserApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encryptedpassword;
    }

    public String decrypt(String x) {
        byte[] plainTextPwdBytes =null;
        String dpw=null;
        try {
            // DECODE encryptedPwd String
            byte[] encrypedPwdBytes = base64decoder.decodeBuffer(x);
            Cipher cipher = Cipher.getInstance("DES");// cipher is not thread safe
            cipher.init(Cipher.DECRYPT_MODE, key);            
            plainTextPwdBytes = cipher.doFinal(encrypedPwdBytes);
            dpw= new String(plainTextPwdBytes,"UTF8");
        } catch (IOException ex) {
            Logger.getLogger(UserApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(UserApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(UserApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(UserApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(UserApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dpw;
    }
    public Usuario getUserById(String id){
        Usuario user=null;
        try{
            user=userRepository.queryById(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }
    public void updateUser(Usuario user){
        
        try{
            userRepository.update(user);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public ArrayList<Usuario> searchUser(Usuario user) {
        ArrayList<Usuario> users = null;
        try {
            users = userRepository.searchUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    
}
