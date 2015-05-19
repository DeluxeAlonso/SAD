package entity;
// Generated 18/05/2015 11:45:07 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * TipoUnidadTransporte generated by hbm2java
 */
public class TipoUnidadTransporte  implements java.io.Serializable {


     private Integer id;
     private Condicion condicion;
     private String descripcion;
     private Set tipoPallets = new HashSet(0);
     private Set unidadTransportes = new HashSet(0);

    public TipoUnidadTransporte() {
    }

	
    public TipoUnidadTransporte(Condicion condicion) {
        this.condicion = condicion;
    }
    public TipoUnidadTransporte(Condicion condicion, String descripcion, Set tipoPallets, Set unidadTransportes) {
       this.condicion = condicion;
       this.descripcion = descripcion;
       this.tipoPallets = tipoPallets;
       this.unidadTransportes = unidadTransportes;
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
    public Set getTipoPallets() {
        return this.tipoPallets;
    }
    
    public void setTipoPallets(Set tipoPallets) {
        this.tipoPallets = tipoPallets;
    }
    public Set getUnidadTransportes() {
        return this.unidadTransportes;
    }
    
    public void setUnidadTransportes(Set unidadTransportes) {
        this.unidadTransportes = unidadTransportes;
    }




}


