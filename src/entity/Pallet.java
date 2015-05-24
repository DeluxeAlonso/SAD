package entity;
// Generated 24-May-2015 16:40:14 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Pallet generated by hbm2java
 */
public class Pallet  implements java.io.Serializable {


     private Integer id;
     private Despacho despacho;
     private TipoPallet tipoPallet;
     private Ubicacion ubicacion;
     private String ean128;
     private Date fechaRegistro;
     private Set historialMovimientoses = new HashSet(0);
     private Set itemProductos = new HashSet(0);

    public Pallet() {
    }

	
    public Pallet(Despacho despacho, TipoPallet tipoPallet, Ubicacion ubicacion) {
        this.despacho = despacho;
        this.tipoPallet = tipoPallet;
        this.ubicacion = ubicacion;
    }
    public Pallet(Despacho despacho, TipoPallet tipoPallet, Ubicacion ubicacion, String ean128, Date fechaRegistro, Set historialMovimientoses, Set itemProductos) {
       this.despacho = despacho;
       this.tipoPallet = tipoPallet;
       this.ubicacion = ubicacion;
       this.ean128 = ean128;
       this.fechaRegistro = fechaRegistro;
       this.historialMovimientoses = historialMovimientoses;
       this.itemProductos = itemProductos;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Despacho getDespacho() {
        return this.despacho;
    }
    
    public void setDespacho(Despacho despacho) {
        this.despacho = despacho;
    }
    public TipoPallet getTipoPallet() {
        return this.tipoPallet;
    }
    
    public void setTipoPallet(TipoPallet tipoPallet) {
        this.tipoPallet = tipoPallet;
    }
    public Ubicacion getUbicacion() {
        return this.ubicacion;
    }
    
    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
    public String getEan128() {
        return this.ean128;
    }
    
    public void setEan128(String ean128) {
        this.ean128 = ean128;
    }
    public Date getFechaRegistro() {
        return this.fechaRegistro;
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    public Set getHistorialMovimientoses() {
        return this.historialMovimientoses;
    }
    
    public void setHistorialMovimientoses(Set historialMovimientoses) {
        this.historialMovimientoses = historialMovimientoses;
    }
    public Set getItemProductos() {
        return this.itemProductos;
    }
    
    public void setItemProductos(Set itemProductos) {
        this.itemProductos = itemProductos;
    }




}


