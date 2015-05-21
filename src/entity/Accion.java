package entity;
// Generated 21-May-2015 00:05:52 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Accion generated by hbm2java
 */
public class Accion  implements java.io.Serializable {


     private int idAccion;
     private String nombre;
     private Set perfils = new HashSet(0);

    public Accion() {
    }

	
    public Accion(int idAccion) {
        this.idAccion = idAccion;
    }
    public Accion(int idAccion, String nombre, Set perfils) {
       this.idAccion = idAccion;
       this.nombre = nombre;
       this.perfils = perfils;
    }
   
    public int getIdAccion() {
        return this.idAccion;
    }
    
    public void setIdAccion(int idAccion) {
        this.idAccion = idAccion;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Set getPerfils() {
        return this.perfils;
    }
    
    public void setPerfils(Set perfils) {
        this.perfils = perfils;
    }




}


