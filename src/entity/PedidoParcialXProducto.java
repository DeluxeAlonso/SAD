package entity;
// Generated 02/05/2015 05:43:37 PM by Hibernate Tools 4.3.1



/**
 * PedidoParcialXProducto generated by hbm2java
 */
public class PedidoParcialXProducto  implements java.io.Serializable {


     private PedidoParcialXProductoId id;
     private PedidoParcial pedidoParcial;
     private Producto producto;
     private Integer cantidadPallets;

    public PedidoParcialXProducto() {
    }

	
    public PedidoParcialXProducto(PedidoParcialXProductoId id, PedidoParcial pedidoParcial, Producto producto) {
        this.id = id;
        this.pedidoParcial = pedidoParcial;
        this.producto = producto;
    }
    public PedidoParcialXProducto(PedidoParcialXProductoId id, PedidoParcial pedidoParcial, Producto producto, Integer cantidadPallets) {
       this.id = id;
       this.pedidoParcial = pedidoParcial;
       this.producto = producto;
       this.cantidadPallets = cantidadPallets;
    }
   
    public PedidoParcialXProductoId getId() {
        return this.id;
    }
    
    public void setId(PedidoParcialXProductoId id) {
        this.id = id;
    }
    public PedidoParcial getPedidoParcial() {
        return this.pedidoParcial;
    }
    
    public void setPedidoParcial(PedidoParcial pedidoParcial) {
        this.pedidoParcial = pedidoParcial;
    }
    public Producto getProducto() {
        return this.producto;
    }
    
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public Integer getCantidadPallets() {
        return this.cantidadPallets;
    }
    
    public void setCantidadPallets(Integer cantidadPallets) {
        this.cantidadPallets = cantidadPallets;
    }




}


