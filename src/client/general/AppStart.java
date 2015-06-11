package client.general;

import application.action.ActionApplication;
import application.client.ClientApplication;
import application.condition.ConditionApplication;
import application.interceptor.LogApplication;
import application.internment.InternmentApplication;
import application.order.OrderApplication;
import application.product.ProductApplication;
import application.producttype.ProductTypeApplication;
import application.profile.ProfileApplication;
import application.transportunit.TransportUnitApplication;
import application.rack.RackApplication;
import application.session.SessionApplication;
import application.spot.SpotApplication;
import application.user.UserApplication;
import application.warehouse.WarehouseApplication;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import util.Constants;
import util.EntityType;
import util.Icons;
import util.InstanceFactory;
import util.Tools;


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
        JOptionPane.setDefaultLocale(new Locale("es", "ES"));
        initializeParam();
        try {

            InstanceFactory.Instance.register("userApplication", UserApplication.class);
            InstanceFactory.Instance.register("warehouseApplication", WarehouseApplication.class);
            InstanceFactory.Instance.register("profileApplication", ProfileApplication.class);
            InstanceFactory.Instance.register("actionApplication", ActionApplication.class);
            InstanceFactory.Instance.register("internmentApplication", InternmentApplication.class);
            InstanceFactory.Instance.register("transportUnitApplication", TransportUnitApplication.class);
            InstanceFactory.Instance.register("conditionApplication", ConditionApplication.class);
            InstanceFactory.Instance.register("rackApplication", RackApplication.class);
            InstanceFactory.Instance.register("spotApplication", SpotApplication.class);
            InstanceFactory.Instance.register("orderApplication", OrderApplication.class);
            InstanceFactory.Instance.register("clientApplication", ClientApplication.class);
            InstanceFactory.Instance.register("productApplication", ProductApplication.class);
            InstanceFactory.Instance.register("productTypeApplication", ProductTypeApplication.class);
            InstanceFactory.Instance.register("logApplication",LogApplication.class);
            InstanceFactory.Instance.register("sessionApplication",SessionApplication.class);
        } catch (Exception ex) {
            Logger.getLogger(AppStart.class.getName()).log(Level.SEVERE, null, ex);
        }

        loadEntityType();

    }

    private void loadEntityType() {
        ProfileApplication profileApplication = InstanceFactory.Instance.getInstance("profileApplication", ProfileApplication.class);
        TransportUnitApplication transportUnitApplication = InstanceFactory.Instance.getInstance("transportUnitApplication", TransportUnitApplication.class);
        ActionApplication actionApplication = InstanceFactory.Instance.getInstance("actionApplication", ActionApplication.class);
        WarehouseApplication warehouseApplication = InstanceFactory.Instance.getInstance("warehouseApplication", WarehouseApplication.class);
        ConditionApplication conditionApplication = InstanceFactory.Instance.getInstance("conditionApplication", ConditionApplication.class);
        RackApplication rackApplication = InstanceFactory.Instance.getInstance("rackApplication", RackApplication.class);
        SpotApplication spotApplication = InstanceFactory.Instance.getInstance("spotApplication", SpotApplication.class);
        OrderApplication orderApplication = InstanceFactory.Instance.getInstance("orderApplication", OrderApplication.class);
        ClientApplication clientApplication = InstanceFactory.Instance.getInstance("clientApplication", ClientApplication.class);
        ProductApplication productApplication = InstanceFactory.Instance.getInstance("productApplication", ProductApplication.class);
        ProductTypeApplication productTypeApplication = InstanceFactory.Instance.getInstance("productTypeApplication", ProductTypeApplication.class);
        conditionApplication.refreshConditions();
        EntityType.fillConditionNames();
        Icons.loadIcons();
        //Se llama un metodo para que actualice los perfiles en la variable global PROFILES y ACTIONS
        profileApplication.refreshProfiles();
        transportUnitApplication.refreshTransportUnits();
        orderApplication.refreshOrders();
        clientApplication.refreshClients();
        productApplication.refreshProducts();

    }
    
    private void initializeParam(){
        Constants.currentIP=Tools.getCurrentIP();
        Constants.currentMac=Tools.getCurrentMac();
    }

}
