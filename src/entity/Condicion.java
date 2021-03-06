package entity;
// Generated 15/06/2015 04:35:07 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Condicion generated by hbm2java
 */
public class Condicion  implements java.io.Serializable {


     private Integer id;
     private String nombre;
     private String descripcion;
     private Set almacens = new HashSet(0);
     private Set productos = new HashSet(0);
     private Set tipoUnidadTransportes = new HashSet(0);

    public Condicion() {
    }

    public Condicion(String nombre, String descripcion, Set almacens, Set productos, Set tipoUnidadTransportes) {
       this.nombre = nombre;
       this.descripcion = descripcion;
       this.almacens = almacens;
       this.productos = productos;
       this.tipoUnidadTransportes = tipoUnidadTransportes;
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
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Set getAlmacens() {
        return this.almacens;
    }
    
    public void setAlmacens(Set almacens) {
        this.almacens = almacens;
    }
    public Set getProductos() {
        return this.productos;
    }
    
    public void setProductos(Set productos) {
        this.productos = productos;
    }
    public Set getTipoUnidadTransportes() {
        return this.tipoUnidadTransportes;
    }
    
    public void setTipoUnidadTransportes(Set tipoUnidadTransportes) {
        this.tipoUnidadTransportes = tipoUnidadTransportes;
    }




}


