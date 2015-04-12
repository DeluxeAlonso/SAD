package client.general;


import infraestructure.user.UserRepository;
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
        InstanceFactory.Instance.register("userRepository", UserRepository.class);

    }

}

