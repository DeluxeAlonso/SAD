/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author dabarca
 */
public  class EntityState {
    
    public EntityState(){}
    
    
    public static enum Users { ACTIVO , INACTIVO };
    public static String[] getUsersState(){
         String[] userState={"Activo","Inactivo"};
         return userState;
    }
    
    
    
    
    
}
