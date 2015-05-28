package entity;
// Generated 27-May-2015 21:42:55 by Hibernate Tools 4.3.1



/**
 * HistorialMovimientosId generated by hbm2java
 */
public class HistorialMovimientosId  implements java.io.Serializable {


     private int idPallet;
     private int idAlmacenIn;
     private int idAlmacenOut;

    public HistorialMovimientosId() {
    }

    public HistorialMovimientosId(int idPallet, int idAlmacenIn, int idAlmacenOut) {
       this.idPallet = idPallet;
       this.idAlmacenIn = idAlmacenIn;
       this.idAlmacenOut = idAlmacenOut;
    }
   
    public int getIdPallet() {
        return this.idPallet;
    }
    
    public void setIdPallet(int idPallet) {
        this.idPallet = idPallet;
    }
    public int getIdAlmacenIn() {
        return this.idAlmacenIn;
    }
    
    public void setIdAlmacenIn(int idAlmacenIn) {
        this.idAlmacenIn = idAlmacenIn;
    }
    public int getIdAlmacenOut() {
        return this.idAlmacenOut;
    }
    
    public void setIdAlmacenOut(int idAlmacenOut) {
        this.idAlmacenOut = idAlmacenOut;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof HistorialMovimientosId) ) return false;
		 HistorialMovimientosId castOther = ( HistorialMovimientosId ) other; 
         
		 return (this.getIdPallet()==castOther.getIdPallet())
 && (this.getIdAlmacenIn()==castOther.getIdAlmacenIn())
 && (this.getIdAlmacenOut()==castOther.getIdAlmacenOut());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdPallet();
         result = 37 * result + this.getIdAlmacenIn();
         result = 37 * result + this.getIdAlmacenOut();
         return result;
   }   


}


