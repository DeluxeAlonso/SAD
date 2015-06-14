/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entity.Cliente;
import entity.Condicion;
import entity.Pedido;
import entity.Perfil;
import entity.PreguntaSecreta;
import entity.Producto;
import entity.TipoUnidadTransporte;
import entity.UnidadTransporte;
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
    
    public static ArrayList<UnidadTransporte> TRANSPORT_UNITS = new ArrayList<>();
    public static ArrayList<Pedido> ORDERS = new ArrayList<>();
    public static ArrayList<Cliente> CLIENTS = new ArrayList<>();
    public static ArrayList<Producto> PRODUCTS = new ArrayList<>();
    
    public static ArrayList<TipoUnidadTransporte> TRANSPORT_TYPES = new ArrayList<>();

    public static String[] PROFILES_NAMES;
    public static String[] CONDITIONS_NAMES;
    public static String[] TRANSPORT_TYPE_NAMES;
    
    public static String[] USER_QUESTIONS;
    
    public static void fillUserQuestions(ArrayList<PreguntaSecreta> questions){
        USER_QUESTIONS = new String[questions.size() + 1];
        for (int i = 0; i < questions.size()+1; i++) {
            if (i == 0) {
                USER_QUESTIONS[i] = "";
            } else {
                USER_QUESTIONS[i] = questions.get(i-1).getPregunta();
            }
        }
    }

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

    public static void fillUnitTransportTypesNames(){
        TRANSPORT_TYPE_NAMES =  new String[TRANSPORT_TYPES.size() + 1];
        for (int i=0; i < TRANSPORT_TYPES.size() + 1; i++){
            if (i == 0){
                TRANSPORT_TYPE_NAMES[i] = "";
            }
            else{
                TRANSPORT_TYPE_NAMES[i] = TRANSPORT_TYPES.get(i-1).getDescripcion();
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
