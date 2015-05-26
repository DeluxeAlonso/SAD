/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entity.Accion;
import entity.Condicion;
import entity.Perfil;
import java.util.ArrayList;

/**
 *
 * @author A20112449
 */
public class EntityType {

    public EntityType() {
    }

    public static ArrayList<Condicion> CONDITIONS = new ArrayList<>();
    public static ArrayList<Perfil> PROFILES = new ArrayList<>();
    public static ArrayList<Accion> ACTIONS = new ArrayList<>();

    public static String[] PROFILES_NAMES;
    public static String[] CONDITIONS_NAMES;

    public static void fillProfileNames() {
        PROFILES_NAMES = new String[PROFILES.size() + 1];
        for (int i = 0; i < PROFILES.size()+1; i++) {
            if (i == 0) {
                PROFILES_NAMES[i] = "";
            } else {
                PROFILES_NAMES[i] = PROFILES.get(i-1).getNombrePerfil();
            }
        }
    } 
    
    public static void fillConditionNames() {
        CONDITIONS_NAMES = new String[CONDITIONS.size() + 1];
        for (int i = 0; i < CONDITIONS.size()+1; i++) {
            if (i == 0) {
                CONDITIONS_NAMES[i] = "";
            } else {
                CONDITIONS_NAMES[i] = CONDITIONS.get(i-1).getNombre();
            }
        }
    } 
    
    public static Condicion getCondition(int codigo) {
        int n = CONDITIONS.size();
        for (int i = 0; i < n; i++) {
            if (codigo == CONDITIONS.get(i).getId())
            {
                return CONDITIONS.get(i);
            }
        }
        return null;
    }
    
    public static Condicion getCondition(String nombre) {
        int n = CONDITIONS.size();
        for (int i = 0; i < n; i++) {
            if (CONDITIONS.get(i).getNombre().equals(nombre))
            {
                return CONDITIONS.get(i);
            }
        }
        return null;
    }
    
    

}
