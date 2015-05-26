/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.profile;

import base.IRepository;
import entity.Accion;
import entity.Perfil;
import java.util.ArrayList;

/**
 *
 * @author dabarca
 */
public interface IProfileRepository extends IRepository<Perfil>{
    Perfil queryByName(String name);
}
