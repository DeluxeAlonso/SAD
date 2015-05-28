package entity;
// Generated May 27, 2015 11:57:07 PM by Hibernate Tools 4.3.1



/**
 * KardexId generated by hbm2java
 */
public class KardexId  implements java.io.Serializable {


     private int id;
     private int idProducto;
     private int idAlmacen;

    public KardexId() {
    }

    public KardexId(int id, int idProducto, int idAlmacen) {
       this.id = id;
       this.idProducto = idProducto;
       this.idAlmacen = idAlmacen;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
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
         
		 return (this.getId()==castOther.getId())
 && (this.getIdProducto()==castOther.getIdProducto())
 && (this.getIdAlmacen()==castOther.getIdAlmacen());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getId();
         result = 37 * result + this.getIdProducto();
         result = 37 * result + this.getIdAlmacen();
         return result;
   }   


}


