/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.users;

import base.users.IUserRepository;

/**
 *
 * @author Nevermade
 */
public class UserApplication {
   private IUserRepository userRepository;
   
   public UserApplication(IUserRepository userRepository){
       this.userRepository=userRepository;
   }
   
   public boolean login(String correo, String password){
       
       return (userRepository.getUser(correo, password)!=null);
       
   }

}
