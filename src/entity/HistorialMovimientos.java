package entity;
// Generated May 27, 2015 11:57:07 PM by Hibernate Tools 4.3.1



/**
 * HistorialMovimientos generated by hbm2java
 */
public class HistorialMovimientos  implements java.io.Serializable {


     private HistorialMovimientosId id;
     private Almacen almacenByIdAlmacenOut;
     private Almacen almacenByIdAlmacenIn;
     private Pallet pallet;

    public HistorialMovimientos() {
    }

    public HistorialMovimientos(HistorialMovimientosId id, Almacen almacenByIdAlmacenOut, Almacen almacenByIdAlmacenIn, Pallet pallet) {
       this.id = id;
       this.almacenByIdAlmacenOut = almacenByIdAlmacenOut;
       this.almacenByIdAlmacenIn = almacenByIdAlmacenIn;
       this.pallet = pallet;
    }
   
    public HistorialMovimientosId getId() {
        return this.id;
    }
    
    public void setId(HistorialMovimientosId id) {
        this.id = id;
    }
    public Almacen getAlmacenByIdAlmacenOut() {
        return this.almacenByIdAlmacenOut;
    }
    
    public void setAlmacenByIdAlmacenOut(Almacen almacenByIdAlmacenOut) {
        this.almacenByIdAlmacenOut = almacenByIdAlmacenOut;
    }
    public Almacen getAlmacenByIdAlmacenIn() {
        return this.almacenByIdAlmacenIn;
    }
    
    public void setAlmacenByIdAlmacenIn(Almacen almacenByIdAlmacenIn) {
        this.almacenByIdAlmacenIn = almacenByIdAlmacenIn;
    }
    public Pallet getPallet() {
        return this.pallet;
    }
    
    public void setPallet(Pallet pallet) {
        this.pallet = pallet;
    }




}


