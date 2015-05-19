/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.action;

import base.action.IActionRepository;
import entity.Accion;
import infraestructure.action.ActionRepository;
import java.util.ArrayList;

/**
 *
 * @author dabarca
 */
public class ActionApplication {
    IActionRepository actionRepository;
    
    public ActionApplication(){
        this.actionRepository=new ActionRepository();
    }
    
    public ArrayList<Accion> getAllActions(){
        ArrayList<Accion> actions=null;
        try{
            actions=actionRepository.queryAll();
        }catch(Exception e){
            e.printStackTrace();
        }
        return actions;
    }
}
