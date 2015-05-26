package client.general;


import application.action.ActionApplication;
import application.condition.ConditionApplication;
import application.internment.InternmentApplication;
import application.profile.ProfileApplication;
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
            InstanceFactory.Instance.register("conditionApplication", ConditionApplication.class);
            InstanceFactory.Instance.register("rackApplication", RackApplication.class);
            InstanceFactory.Instance.register("spotApplication", SpotApplication.class);
        } catch (Exception ex) {
            Logger.getLogger(AppStart.class.getName()).log(Level.SEVERE, null, ex);
        }

        loadEntityType();
        
    }
    
    private void loadEntityType(){      
        ProfileApplication profileApplication = InstanceFactory.Instance.getInstance("profileApplication", ProfileApplication.class);
        ActionApplication actionApplication = InstanceFactory.Instance.getInstance("actionApplication", ActionApplication.class);
        WarehouseApplication warehouseApplication = InstanceFactory.Instance.getInstance("warehouseApplication", WarehouseApplication.class);
        ConditionApplication conditionApplication = InstanceFactory.Instance.getInstance("conditionApplication", ConditionApplication.class);
        RackApplication rackApplication = InstanceFactory.Instance.getInstance("rackApplication", RackApplication.class);
        SpotApplication spotApplication = InstanceFactory.Instance.getInstance("spotApplication", SpotApplication.class);
        //al final se tiene que cargar el arreglo desde la base de datos        
        Condicion c1 = new Condicion(); c1.setId(1); c1.setNombre("Normal");
        c1.setDescripcion("Esta condicion sirve para productos que necesiten una temperatura entre 15 y 30 grados");
        
        Condicion c2 = new Condicion(); c2.setId(2); c2.setNombre("Refrigerados");
        c2.setDescripcion("Esta condicion sirve para productos que necesiten una refreigeracion");
        
        EntityType.CONDITIONS.add(c1); 
        EntityType.CONDITIONS.add(c2);
        EntityType.fillConditionNames();
        //Se llama un metodo para que actualice los perfiles en la variable global PROFILES y ACTIONS
        profileApplication.refreshProfiles();
        actionApplication.refreshActions();       

    }

}

