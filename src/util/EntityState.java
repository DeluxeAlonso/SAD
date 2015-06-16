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

    public EntityState() {
    }

    public static enum Users {

        ACTIVO, INACTIVO
    };

    public static String[] getUsersState() {
        
        return userState;
    }
    
    public static enum Spots {

        INACTIVO, LIBRE, OCUPADO
    };

    public static String[] getSpotsState() {
        
        return spotState;
    }
    
    public static String getSpotStateLiteral(int state) {
        
        return spotStateLiteral[state];
    }
    
    public static enum Warehouses {

        INACTIVO, ACTIVO
    };

    public static String[] getWarehousesState() {
        
        return warehousesState;
    }
    
    public static enum Racks {

        INACTIVO, ACTIVO, EN_REVISION, LLENO
    };

    public static String[] getRacksState() {
        
        return racksState;
    }
    
    public static enum Clients {

        INACTIVO, ACTIVO
    };

    public static String[] getClientsState() {
        
        return clientsState;
    }
    
    public static enum Locals {

        INACTIVO, ACTIVO
    };

    public static String[] getLocalsState() {
        
        return localsState;
    }
   
    public static enum TransportUnits {

        INACTIVO, ACTIVO
    };

    public static String[] getTransportUnitsState() {
        
        return transportUnitsState;
    }
    
    public static enum InternmentOrders {

        REGISTRADA, INTERNADA, PENDIENTE
    };

    public static String[] getInternmentOrdersState() {
        
        return internmentOrdersState;
    }
    
    public static enum Pallets {

        CREADO, UBICADO, DESPACHADO, ELIMINADO, ROTO, VENCIDO
    }

    public static String[] getPalletsState() {
        
        return palletsState;
    }
    
    public static enum Orders {

        ANULADO, REGISTRADO, EN_CURSO, FINALIZADO
    };

    public static String[] getOrdersState() {
        
        return ordersState;
    }
    
    public static enum PartialOrders {

        ATENDIDO, NO_ATENDIDO, ANULADO
    };

    public static String[] getPartialOrdersState() {
        
        return partialOrdersState;
    }
    
    public static enum RemissionGuides{
        INACTIVO,ACTIVO
    };
    
    public static String[] getRemissionGuidesState() {
        
        return remissionGuideState;
    }
    
    /*
     almacen
     guia remision
     orden internamiento
     pedido
     perfil
     usuario
     despacho
     kardex
     */

    public static enum Master {

        Almacen, Guia_Remision, Orden_Internamiento, Pedido, Perfil, Usuario, Despacho, Kardex
    };

    public static String[] getMasters() {
        
        return masters;
    }
    
    public static enum Operation{
      
        Insert,Update,Delete
        
    };
    
    public static String[] getOperations(){
         
         return operation;
    }
    private static String[] operation={"","Creación","Actualización", "Eliminación"};
    private static String[] masters = {"","Almacén", "Guía de Remisión", "Orden de Internamiento", "Pedido", "Perfil", "Usuario", "Despacho", "Kardex"};
    private static String[] partialOrdersState = {"Atendido", "No Atendido", "Anulado"};
    private static String[] ordersState = {"Anulado", "Registrado", "En Curso", "Finalizado"};
    private static String[] palletsState = {"Creado", "Ubicado", "Despachado", "Eliminado","Roto", "Vencido"};
    private static String[] internmentOrdersState = {"Registrada", "Internada", "Pendiente"};
    private static String[] transportUnitsState = {"Inactivo", "Activo"};
    private static String[] localsState = {"Inactivo", "Activo"};
    private static String[] racksState = {"Inactivo", "Activo", "En_revision", "Lleno"};
    private static String[] clientsState = {"Inactivo", "Activo"};
    private static String[] warehousesState = {"Inactivo", "Activo"};
    private static String[] spotState = {"Inactivo", "Libre", "Ocupado"};
    private static String[] userState = {" ", "Activo", "Inactivo"};
    private static String[] spotStateLiteral={"Inactivo","Libre","Ocupado"};
    private static String[] remissionGuideState={"Inactivo","Activo"};    
}
