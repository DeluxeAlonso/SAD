package entity;
// Generated 21-May-2015 00:05:52 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * TipoPallet generated by hbm2java
 */
public class TipoPallet  implements java.io.Serializable {


     private Integer id;
     private String descripcion;
     private Set tipoPalletXProductos = new HashSet(0);
     private Set pallets = new HashSet(0);
     private Set tipoUnidadTransportes = new HashSet(0);

    public TipoPallet() {
    }

    public TipoPallet(String descripcion, Set tipoPalletXProductos, Set pallets, Set tipoUnidadTransportes) {
       this.descripcion = descripcion;
       this.tipoPalletXProductos = tipoPalletXProductos;
       this.pallets = pallets;
       this.tipoUnidadTransportes = tipoUnidadTransportes;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Set getTipoPalletXProductos() {
        return this.tipoPalletXProductos;
    }
    
    public void setTipoPalletXProductos(Set tipoPalletXProductos) {
        this.tipoPalletXProductos = tipoPalletXProductos;
    }
    public Set getPallets() {
        return this.pallets;
    }
    
    public void setPallets(Set pallets) {
        this.pallets = pallets;
    }
    public Set getTipoUnidadTransportes() {
        return this.tipoUnidadTransportes;
    }
    
    public void setTipoUnidadTransportes(Set tipoUnidadTransportes) {
        this.tipoUnidadTransportes = tipoUnidadTransportes;
    }




}


