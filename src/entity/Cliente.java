package entity;
// Generated 18/05/2015 11:45:07 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Cliente generated by hbm2java
 */
public class Cliente  implements java.io.Serializable {


     private Integer id;
     private String nombre;
     private String ruc;
     private Set locals = new HashSet(0);

    public Cliente() {
    }

    public Cliente(String nombre, String ruc, Set locals) {
       this.nombre = nombre;
       this.ruc = ruc;
       this.locals = locals;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getRuc() {
        return this.ruc;
    }
    
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }
    public Set getLocals() {
        return this.locals;
    }
    
    public void setLocals(Set locals) {
        this.locals = locals;
    }




}


