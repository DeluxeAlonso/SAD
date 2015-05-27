package entity;
// Generated May 26, 2015 4:12:25 AM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Kardex generated by hbm2java
 */
public class Kardex  implements java.io.Serializable {


     private KardexId id;
     private Almacen almacen;
     private Producto producto;
     private Integer cantidad;
     private Date fecha;
     private String tipoMovimiento;

    public Kardex() {
    }

	
    public Kardex(KardexId id, Almacen almacen, Producto producto) {
        this.id = id;
        this.almacen = almacen;
        this.producto = producto;
    }
    public Kardex(KardexId id, Almacen almacen, Producto producto, Integer cantidad, Date fecha, String tipoMovimiento) {
       this.id = id;
       this.almacen = almacen;
       this.producto = producto;
       this.cantidad = cantidad;
       this.fecha = fecha;
       this.tipoMovimiento = tipoMovimiento;
    }
   
    public KardexId getId() {
        return this.id;
    }
    
    public void setId(KardexId id) {
        this.id = id;
    }
    public Almacen getAlmacen() {
        return this.almacen;
    }
    
    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }
    public Producto getProducto() {
        return this.producto;
    }
    
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public Integer getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public String getTipoMovimiento() {
        return this.tipoMovimiento;
    }
    
    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }




}


