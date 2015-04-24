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
    
    public static enum Users { ACTIVO , INACTIVO };
   
    
    public static enum Profile {JEFE_ALMACEN,SUPERVISOR,ADMIN};
    
    public static String[] getProfiles(){
         String[] profiles={"Jefe Almacen","Supervisor","Administrador"};
         return profiles;
    }
    
    
    
}
