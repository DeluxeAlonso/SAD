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
         String[] userState={" ","Activo","Inactivo"};
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
    
    public static enum TransportUnits { INACTIVO, ACTIVO};
    public static String[] getTransportUnitsState(){
         String[] transportUnitsState={"Inactivo","Activo"};
         return transportUnitsState;
    }
    
    public static enum InternmentOrders { REGISTRADA, INTERNADA, PENDIENTE};
    public static String[] getInternmentOrdersState(){
        String[] internmentOrdersState={"Registrada","Internada", "Pendiente"};
        return internmentOrdersState;
    }
    
    public static enum Pallets {CREADO, UBICADO, DESPACHADO, ELIMINADO}
    public static String[] getPalletsState(){
        String[] palletsState={"Creado","Ubicado","Despachado","Eliminado"};
        return palletsState;
    }
    
    public static enum Orders { ANULADO, REGISTRADO, EN_CURSO, FINALIZADO};
    public static String[] getOrdersState(){
         String[] ordersState={"Anulado","Registrado", "En Curso", "Finalizado"};
         return ordersState;
    }
    
    public static enum PartialOrders { ATENDIDO, NO_ATENDIDO, ANULADO};
    public static String[] getPartialOrdersState(){
         String[] partialOrdersState={"Atendido","No Atendido", "Anulado"};
         return partialOrdersState;
    }

}
