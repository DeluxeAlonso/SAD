package entity;
// Generated 13/06/2015 10:34:15 PM by Hibernate Tools 4.3.1


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
     private Integer estado;
     private Date fechaRegistro;
     private Integer ubicLibres;
     private Set ubicacions = new HashSet(0);

    public Rack() {
    }

	
    public Rack(Almacen almacen) {
        this.almacen = almacen;
    }
    public Rack(Almacen almacen, Integer numFil, Integer numCol, Integer estado, Date fechaRegistro, Integer ubicLibres, Set ubicacions) {
       this.almacen = almacen;
       this.numFil = numFil;
       this.numCol = numCol;
       this.estado = estado;
       this.fechaRegistro = fechaRegistro;
       this.ubicLibres = ubicLibres;
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
    public Integer getEstado() {
        return this.estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    public Date getFechaRegistro() {
        return this.fechaRegistro;
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    public Integer getUbicLibres() {
        return this.ubicLibres;
    }
    
    public void setUbicLibres(Integer ubicLibres) {
        this.ubicLibres = ubicLibres;
    }
    public Set getUbicacions() {
        return this.ubicacions;
    }
    
    public void setUbicacions(Set ubicacions) {
        this.ubicacions = ubicacions;
    }




}


