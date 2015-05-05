package entity;
// Generated 03/05/2015 07:54:51 PM by Hibernate Tools 4.3.1



/**
 * OrdenInternamientoXProductoId generated by hbm2java
 */
public class OrdenInternamientoXProductoId  implements java.io.Serializable {


     private int idOrdenInternamiento;
     private int idProducto;

    public OrdenInternamientoXProductoId() {
    }

    public OrdenInternamientoXProductoId(int idOrdenInternamiento, int idProducto) {
       this.idOrdenInternamiento = idOrdenInternamiento;
       this.idProducto = idProducto;
    }
   
    public int getIdOrdenInternamiento() {
        return this.idOrdenInternamiento;
    }
    
    public void setIdOrdenInternamiento(int idOrdenInternamiento) {
        this.idOrdenInternamiento = idOrdenInternamiento;
    }
    public int getIdProducto() {
        return this.idProducto;
    }
    
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof OrdenInternamientoXProductoId) ) return false;
		 OrdenInternamientoXProductoId castOther = ( OrdenInternamientoXProductoId ) other; 
         
		 return (this.getIdOrdenInternamiento()==castOther.getIdOrdenInternamiento())
 && (this.getIdProducto()==castOther.getIdProducto());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdOrdenInternamiento();
         result = 37 * result + this.getIdProducto();
         return result;
   }   


}


