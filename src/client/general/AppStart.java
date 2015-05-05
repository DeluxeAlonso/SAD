package client.general;


import application.users.UserApplication;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        } catch (Exception ex) {
            Logger.getLogger(AppStart.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

