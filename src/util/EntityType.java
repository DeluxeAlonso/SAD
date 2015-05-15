/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entity.Accion;
import entity.Condicion;
import entity.Perfil;
import java.util.ArrayList;

/**
 *
 * @author A20112449
 */
public class EntityType {
     public EntityType(){}
    
    
    public static ArrayList<Condicion> CONDITIONS = new ArrayList<>();
    public static ArrayList<Perfil> PROFILES = new ArrayList<>();
    public static ArrayList<Accion> ACTIONS = new ArrayList<>();
    
    public static String[] PROFILES_NAMES;
   
    
    
    
    public static void fillProfileNames(){
        PROFILES_NAMES= new String[PROFILES.size()];
        for(int i=0;i<PROFILES.size();i++){
            PROFILES_NAMES[i]=PROFILES.get(i).getNombrePerfil();
        }        
    }
    
    
    
   
}
