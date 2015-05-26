package client.general;


import application.action.ActionApplication;
import application.client.ClientApplication;
import application.condition.ConditionApplication;
import application.internment.InternmentApplication;
import application.order.OrderApplication;
import application.product.ProductApplication;
import application.profile.ProfileApplication;
import application.transportunit.TransportUnitApplication;
import application.rack.RackApplication;
import application.spot.SpotApplication;
import application.user.UserApplication;
import application.warehouse.WarehouseApplication;
import entity.Condicion;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.EntityType;
import util.HibernateUtil;
import util.InstanceFactory;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dabarca
 */
public class AppStart {

    public static AppStart initConfig = new AppStart();

    public AppStart() {
    }

    public void start() {
        try {
                  
            InstanceFactory.Instance.register("userApplication", UserApplication.class);
            InstanceFactory.Instance.register("warehouseApplication", WarehouseApplication.class);
            InstanceFactory.Instance.register("profileApplication", ProfileApplication.class);
            InstanceFactory.Instance.register("actionApplication", ActionApplication.class);
            InstanceFactory.Instance.register("internmentApplication", InternmentApplication.class);
            InstanceFactory.Instance.register("transportUnitApplication",TransportUnitApplication.class);
            InstanceFactory.Instance.register("conditionApplication", ConditionApplication.class);
            InstanceFactory.Instance.register("rackApplication", RackApplication.class);
            InstanceFactory.Instance.register("spotApplication", SpotApplication.class);
            InstanceFactory.Instance.register("orderApplication", OrderApplication.class);
            InstanceFactory.Instance.register("clientApplication", ClientApplication.class);
            InstanceFactory.Instance.register("productApplication", ProductApplication.class);  
        } catch (Exception ex) {
            Logger.getLogger(AppStart.class.getName()).log(Level.SEVERE, null, ex);
        }

        loadEntityType();
        
    }
    
    private void loadEntityType(){      
        ProfileApplication profileApplication = InstanceFactory.Instance.getInstance("profileApplication", ProfileApplication.class);
        TransportUnitApplication transportUnitApplication = InstanceFactory.Instance.getInstance("transportUnitApplication",TransportUnitApplication.class);
        ActionApplication actionApplication = InstanceFactory.Instance.getInstance("actionApplication", ActionApplication.class);
        WarehouseApplication warehouseApplication = InstanceFactory.Instance.getInstance("warehouseApplication", WarehouseApplication.class);
        ConditionApplication conditionApplication = InstanceFactory.Instance.getInstance("conditionApplication", ConditionApplication.class);
        RackApplication rackApplication = InstanceFactory.Instance.getInstance("rackApplication", RackApplication.class);
        SpotApplication spotApplication = InstanceFactory.Instance.getInstance("spotApplication", SpotApplication.class);
        OrderApplication orderApplication = InstanceFactory.Instance.getInstance("orderApplication", OrderApplication.class);
        ClientApplication clientApplication = InstanceFactory.Instance.getInstance("clientApplication", ClientApplication.class);
        ProductApplication productApplication = InstanceFactory.Instance.getInstance("productApplication", ProductApplication.class);
        //al final se tiene que cargar el arreglo desde la base de datos        
        EntityType.CONDITIONS = conditionApplication.queryAll();
        EntityType.fillConditionNames();
        //Se llama un metodo para que actualice los perfiles en la variable global PROFILES y ACTIONS
        profileApplication.refreshProfiles();
        actionApplication.refreshActions();       
        transportUnitApplication.refreshTransportUnits();
        orderApplication.refreshOrders();
        clientApplication.refreshClients();
        productApplication.refreshProducts();
    }

}

