/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.session;

import base.session.ISessionRepository;
import entity.Sesion;
import infraestructure.session.SessionRepository;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Nevermade
 */
public class SessionApplication {
    
    ISessionRepository sessionRepository;
    
    public SessionApplication(){
        this.sessionRepository=new SessionRepository();
    }
    
    
    public ArrayList<Sesion> getSession(Sesion s, Date dateI, Date dateF){
        ArrayList<Sesion> sesions=null;
        try{
            sesions=sessionRepository.queryBySession(null, dateI, dateF);
        }catch(Exception e){
            e.printStackTrace();
        }
        return sesions;
    }
    
    public int insertSession(Sesion s){
        int ret=-1;
        try{
            ret=sessionRepository.insert(s);
        }catch(Exception e){
            e.printStackTrace();
        }
        return ret;
    }
    
     public ArrayList<Sesion> getAllSession(){
        ArrayList<Sesion> sesions=null;
        try{
            sesions=sessionRepository.queryAll();
        }catch(Exception e){
            e.printStackTrace();
        }
        return sesions;
    }
}
