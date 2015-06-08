package entity;
// Generated 08/06/2015 05:30:00 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Cliente generated by hbm2java
 */
public class Cliente  implements java.io.Serializable {


     private Integer id;
     private String nombre;
     private String ruc;
     private Integer estado;
     private Set locals = new HashSet(0);
     private Set guiaRemisions = new HashSet(0);
     private Set pedidos = new HashSet(0);

    public Cliente() {
    }

    public Cliente(String nombre, String ruc, Integer estado, Set locals, Set guiaRemisions, Set pedidos) {
       this.nombre = nombre;
       this.ruc = ruc;
       this.estado = estado;
       this.locals = locals;
       this.guiaRemisions = guiaRemisions;
       this.pedidos = pedidos;
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
    public Integer getEstado() {
        return this.estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    public Set getLocals() {
        return this.locals;
    }
    
    public void setLocals(Set locals) {
        this.locals = locals;
    }
    public Set getGuiaRemisions() {
        return this.guiaRemisions;
    }
    
    public void setGuiaRemisions(Set guiaRemisions) {
        this.guiaRemisions = guiaRemisions;
    }
    public Set getPedidos() {
        return this.pedidos;
    }
    
    public void setPedidos(Set pedidos) {
        this.pedidos = pedidos;
    }




}


