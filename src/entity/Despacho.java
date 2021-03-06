package entity;
// Generated 15/06/2015 04:35:07 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Despacho generated by hbm2java
 */
public class Despacho  implements java.io.Serializable {


     private Integer id;
     private UnidadTransporte unidadTransporte;
     private Date fechaDespacho;
     private Integer estado;
     private Set guiaRemisions = new HashSet(0);

    public Despacho() {
    }

	
    public Despacho(UnidadTransporte unidadTransporte) {
        this.unidadTransporte = unidadTransporte;
    }
    public Despacho(UnidadTransporte unidadTransporte, Date fechaDespacho, Integer estado, Set guiaRemisions) {
       this.unidadTransporte = unidadTransporte;
       this.fechaDespacho = fechaDespacho;
       this.estado = estado;
       this.guiaRemisions = guiaRemisions;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public UnidadTransporte getUnidadTransporte() {
        return this.unidadTransporte;
    }
    
    public void setUnidadTransporte(UnidadTransporte unidadTransporte) {
        this.unidadTransporte = unidadTransporte;
    }
    public Date getFechaDespacho() {
        return this.fechaDespacho;
    }
    
    public void setFechaDespacho(Date fechaDespacho) {
        this.fechaDespacho = fechaDespacho;
    }
    public Integer getEstado() {
        return this.estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    public Set getGuiaRemisions() {
        return this.guiaRemisions;
    }
    
    public void setGuiaRemisions(Set guiaRemisions) {
        this.guiaRemisions = guiaRemisions;
    }




}


