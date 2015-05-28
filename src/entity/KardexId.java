package entity;
// Generated 27-May-2015 21:42:55 by Hibernate Tools 4.3.1



/**
 * KardexId generated by hbm2java
 */
public class KardexId  implements java.io.Serializable {


     private int idProducto;
     private int idAlmacen;

    public KardexId() {
    }

    public KardexId(int idProducto, int idAlmacen) {
       this.idProducto = idProducto;
       this.idAlmacen = idAlmacen;
    }
   
    public int getIdProducto() {
        return this.idProducto;
    }
    
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
    public int getIdAlmacen() {
        return this.idAlmacen;
    }
    
    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof KardexId) ) return false;
		 KardexId castOther = ( KardexId ) other; 
         
		 return (this.getIdProducto()==castOther.getIdProducto())
 && (this.getIdAlmacen()==castOther.getIdAlmacen());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdProducto();
         result = 37 * result + this.getIdAlmacen();
         return result;
   }   


}


