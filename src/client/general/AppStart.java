package client.general;


import application.users.UserApplication;
import entity.Accion;
import entity.Condicion;
import entity.Perfil;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.EntityType;
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
        loadEntityType();
    }
    
    private void loadEntityType(){
        //al final se tiene que cargar el arreglo desde la base de datos
        Condicion c1 = new Condicion(); c1.setId(1); c1.setNombre("Normal");
        Condicion c2 = new Condicion(); c2.setId(2); c2.setNombre("Refrigerado");
        EntityType.CONDITIONS.add(c1); 
        EntityType.CONDITIONS.add(c2);
        
        Perfil p1 = new Perfil();p1.setIdPerfil(1);p1.setNombrePerfil("Jefe de Almacen");
        Perfil p2 = new Perfil();p2.setIdPerfil(2);p2.setNombrePerfil("Supervisor");
        Perfil p3 = new Perfil();p3.setIdPerfil(3);p3.setNombrePerfil("Administrador");
        EntityType.PROFILES.add(p1);
        EntityType.PROFILES.add(p2);
        EntityType.PROFILES.add(p3);
        EntityType.fillProfileNames();
        
        Accion a1 = new Accion();a1.setIdAccion(1);a1.setNombre("Movimientos");
        Accion a2 = new Accion();a2.setIdAccion(2);a2.setNombre("Operaciones");
        Accion a3 = new Accion();a3.setIdAccion(3);a3.setNombre("Mantenimientos");
        Accion a4 = new Accion();a4.setIdAccion(4);a4.setNombre("Reportes");
        Accion a5 = new Accion();a5.setIdAccion(5);a5.setNombre("Usuarios");
        Accion a6 = new Accion();a6.setIdAccion(6);a6.setNombre("Interfaces");
        Accion a7 = new Accion();a7.setIdAccion(7);a7.setNombre("Sesion");
        EntityType.ACTIONS.add(a1);
        EntityType.ACTIONS.add(a2);
        EntityType.ACTIONS.add(a3);
        EntityType.ACTIONS.add(a4);
        EntityType.ACTIONS.add(a5);
        EntityType.ACTIONS.add(a6);
        EntityType.ACTIONS.add(a7);

    }

}

