package entity;
// Generated 18/05/2015 11:45:07 AM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Rack generated by hbm2java
 */
public class Rack  implements java.io.Serializable {


     private Integer id;
     private Almacen almacen;
     private Integer numFil;
     private Integer numCol;
     private String estado;
     private Date fechaRegistro;
     private Set ubicacions = new HashSet(0);

    public Rack() {
    }

    public Rack(Almacen almacen, Integer numFil, Integer numCol, String estado, Date fechaRegistro, Set ubicacions) {
       this.almacen = almacen;
       this.numFil = numFil;
       this.numCol = numCol;
       this.estado = estado;
       this.fechaRegistro = fechaRegistro;
       this.ubicacions = ubicacions;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Almacen getAlmacen() {
        return this.almacen;
    }
    
    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }
    public Integer getNumFil() {
        return this.numFil;
    }
    
    public void setNumFil(Integer numFil) {
        this.numFil = numFil;
    }
    public Integer getNumCol() {
        return this.numCol;
    }
    
    public void setNumCol(Integer numCol) {
        this.numCol = numCol;
    }
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Date getFechaRegistro() {
        return this.fechaRegistro;
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    public Set getUbicacions() {
        return this.ubicacions;
    }
    
    public void setUbicacions(Set ubicacions) {
        this.ubicacions = ubicacions;
    }




}


