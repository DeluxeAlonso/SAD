package entity;
// Generated 27-May-2015 21:42:55 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Accion generated by hbm2java
 */
public class Accion  implements java.io.Serializable {


     private int id;
     private String nombre;
     private Set perfils = new HashSet(0);

    public Accion() {
    }

	
    public Accion(int id) {
        this.id = id;
    }
    public Accion(int id, String nombre, Set perfils) {
       this.id = id;
       this.nombre = nombre;
       this.perfils = perfils;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
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


