/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.user;

import infraestructure.user.UserInfraestructure;

/**
 *
 * @author Nevermade
 */
public class UserController {
   private static UserInfraestructure userInf= new UserInfraestructure();
   public boolean login(String correo, String password){
       
       return (userInf.getUser(correo, password)!=null);
       
   }

}
