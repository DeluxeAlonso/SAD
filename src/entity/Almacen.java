package entity;
// Generated May 29, 2015 5:58:43 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Almacen generated by hbm2java
 */
public class Almacen  implements java.io.Serializable {


     private Integer id;
     private Condicion condicion;
     private String descripcion;
     private Integer capacidad;
     private Integer area;
     private Date fechaRegistro;
     private Integer estado;
     private Integer ubicLibres;
     private Set racks = new HashSet(0);
     private Set historialMovimientosesForIdAlmacenOut = new HashSet(0);
     private Set historialMovimientosesForIdAlmacenIn = new HashSet(0);
     private Set kardexes = new HashSet(0);

    public Almacen() {
    }

	
    public Almacen(Condicion condicion) {
        this.condicion = condicion;
    }
    public Almacen(Condicion condicion, String descripcion, Integer capacidad, Integer area, Date fechaRegistro, Integer estado, Integer ubicLibres, Set racks, Set historialMovimientosesForIdAlmacenOut, Set historialMovimientosesForIdAlmacenIn, Set kardexes) {
       this.condicion = condicion;
       this.descripcion = descripcion;
       this.capacidad = capacidad;
       this.area = area;
       this.fechaRegistro = fechaRegistro;
       this.estado = estado;
       this.ubicLibres = ubicLibres;
       this.racks = racks;
       this.historialMovimientosesForIdAlmacenOut = historialMovimientosesForIdAlmacenOut;
       this.historialMovimientosesForIdAlmacenIn = historialMovimientosesForIdAlmacenIn;
       this.kardexes = kardexes;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Condicion getCondicion() {
        return this.condicion;
    }
    
    public void setCondicion(Condicion condicion) {
        this.condicion = condicion;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Integer getCapacidad() {
        return this.capacidad;
    }
    
    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }
    public Integer getArea() {
        return this.area;
    }
    
    public void setArea(Integer area) {
        this.area = area;
    }
    public Date getFechaRegistro() {
        return this.fechaRegistro;
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    public Integer getEstado() {
        return this.estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    public Integer getUbicLibres() {
        return this.ubicLibres;
    }
    
    public void setUbicLibres(Integer ubicLibres) {
        this.ubicLibres = ubicLibres;
    }
    public Set getRacks() {
        return this.racks;
    }
    
    public void setRacks(Set racks) {
        this.racks = racks;
    }
    public Set getHistorialMovimientosesForIdAlmacenOut() {
        return this.historialMovimientosesForIdAlmacenOut;
    }
    
    public void setHistorialMovimientosesForIdAlmacenOut(Set historialMovimientosesForIdAlmacenOut) {
        this.historialMovimientosesForIdAlmacenOut = historialMovimientosesForIdAlmacenOut;
    }
    public Set getHistorialMovimientosesForIdAlmacenIn() {
        return this.historialMovimientosesForIdAlmacenIn;
    }
    
    public void setHistorialMovimientosesForIdAlmacenIn(Set historialMovimientosesForIdAlmacenIn) {
        this.historialMovimientosesForIdAlmacenIn = historialMovimientosesForIdAlmacenIn;
    }
    public Set getKardexes() {
        return this.kardexes;
    }
    
    public void setKardexes(Set kardexes) {
        this.kardexes = kardexes;
    }




}


