package entity;
// Generated 24-May-2015 16:40:14 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * ItemProducto generated by hbm2java
 */
public class ItemProducto  implements java.io.Serializable {


     private Integer id;
     private Pallet pallet;
     private Producto producto;
     private String ean13;
     private Date fechaVencimiento;
     private Date fechaRegistro;

    public ItemProducto() {
    }

	
    public ItemProducto(Pallet pallet, Producto producto) {
        this.pallet = pallet;
        this.producto = producto;
    }
    public ItemProducto(Pallet pallet, Producto producto, String ean13, Date fechaVencimiento, Date fechaRegistro) {
       this.pallet = pallet;
       this.producto = producto;
       this.ean13 = ean13;
       this.fechaVencimiento = fechaVencimiento;
       this.fechaRegistro = fechaRegistro;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Pallet getPallet() {
        return this.pallet;
    }
    
    public void setPallet(Pallet pallet) {
        this.pallet = pallet;
    }
    public Producto getProducto() {
        return this.producto;
    }
    
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public String getEan13() {
        return this.ean13;
    }
    
    public void setEan13(String ean13) {
        this.ean13 = ean13;
    }
    public Date getFechaVencimiento() {
        return this.fechaVencimiento;
    }
    
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    public Date getFechaRegistro() {
        return this.fechaRegistro;
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }




}


