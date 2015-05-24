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
public class EntityState {
    
    public EntityState(){}
    
    
    public static enum Users { ACTIVO , INACTIVO };
    public static String[] getUsersState(){
         String[] userState={"Activo","Inactivo"};
         return userState;
    }
    
    public static enum Spots { INACTIVO, LIBRE, OCUPADO};
    public static String[] getSpotsState(){
         String[] spotState={"Inactivo","Libre","Ocupado"};
         return spotState;
    }
    
    public static enum Warehouses { INACTIVO, ACTIVO, EN_REVISION};
    public static String[] getWarehousesState(){
         String[] warehousesState={"Inactivo","Activo","En_revision"};
         return warehousesState;
    }
    
    public static enum Racks { INACTIVO, ACTIVO, EN_REVISION, LLENO};
    public static String[] getRacksState(){
         String[] racksState={"Inactivo","Activo","En_revision","Lleno"};
         return racksState;
    }
    
    public static enum Clients { INACTIVO, ACTIVO};
    public static String[] getClientsState(){
         String[] clientsState={"Inactivo","Activo"};
         return clientsState;
    }
    
    public static enum Locals { INACTIVO, ACTIVO};
    public static String[] getLocalsState(){
         String[] localsState={"Inactivo","Activo"};
         return localsState;
    }
    
}
