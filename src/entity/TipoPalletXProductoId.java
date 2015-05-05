package entity;
// Generated 03/05/2015 07:54:51 PM by Hibernate Tools 4.3.1



/**
 * TipoPalletXProductoId generated by hbm2java
 */
public class TipoPalletXProductoId  implements java.io.Serializable {


     private int idTipoPallet;
     private int idProducto;

    public TipoPalletXProductoId() {
    }

    public TipoPalletXProductoId(int idTipoPallet, int idProducto) {
       this.idTipoPallet = idTipoPallet;
       this.idProducto = idProducto;
    }
   
    public int getIdTipoPallet() {
        return this.idTipoPallet;
    }
    
    public void setIdTipoPallet(int idTipoPallet) {
        this.idTipoPallet = idTipoPallet;
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
		 if ( !(other instanceof TipoPalletXProductoId) ) return false;
		 TipoPalletXProductoId castOther = ( TipoPalletXProductoId ) other; 
         
		 return (this.getIdTipoPallet()==castOther.getIdTipoPallet())
 && (this.getIdProducto()==castOther.getIdProducto());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdTipoPallet();
         result = 37 * result + this.getIdProducto();
         return result;
   }   


}


