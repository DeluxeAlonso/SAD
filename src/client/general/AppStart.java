package client.general;


import application.action.ActionApplication;
import application.internment.InternmentApplication;
import application.profile.ProfileApplication;
import application.user.UserApplication;
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
            InstanceFactory.Instance.register("warehouseApplication", UserApplication.class);
            InstanceFactory.Instance.register("profileApplication", ProfileApplication.class);
            InstanceFactory.Instance.register("accionApplication", ActionApplication.class);
            InstanceFactory.Instance.register("internmentApplication", InternmentApplication.class);
        } catch (Exception ex) {
            Logger.getLogger(AppStart.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadEntityType();
    }
    
    private void loadEntityType(){      
        ProfileApplication profileApplication = InstanceFactory.Instance.getInstance("profileApplication", ProfileApplication.class);
        ActionApplication actionApplication = InstanceFactory.Instance.getInstance("accionApplication", ActionApplication.class);
        //al final se tiene que cargar el arreglo desde la base de datos        
        Condicion c1 = new Condicion(); c1.setId(1); c1.setNombre("Normal");
        Condicion c2 = new Condicion(); c2.setId(2); c2.setNombre("Refrigerado");
        EntityType.CONDITIONS.add(c1); 
        EntityType.CONDITIONS.add(c2);
        
        //Se llama un metodo para que actualice los perfiles en la variable global PROFILES y ACTIONS
        profileApplication.refreshProfiles();
        actionApplication.refreshActions();       

    }

}

