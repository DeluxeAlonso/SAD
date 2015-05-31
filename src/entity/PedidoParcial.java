package entity;
// Generated 30/05/2015 08:07:22 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * PedidoParcial generated by hbm2java
 */
public class PedidoParcial  implements java.io.Serializable {


     private Integer id;
     private GuiaRemision guiaRemision;
     private Pedido pedido;
     private Integer estado;
     private Set pedidoParcialXProductos = new HashSet(0);

    public PedidoParcial() {
    }

	
    public PedidoParcial(Pedido pedido) {
        this.pedido = pedido;
    }
    public PedidoParcial(GuiaRemision guiaRemision, Pedido pedido, Integer estado, Set pedidoParcialXProductos) {
       this.guiaRemision = guiaRemision;
       this.pedido = pedido;
       this.estado = estado;
       this.pedidoParcialXProductos = pedidoParcialXProductos;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public GuiaRemision getGuiaRemision() {
        return this.guiaRemision;
    }
    
    public void setGuiaRemision(GuiaRemision guiaRemision) {
        this.guiaRemision = guiaRemision;
    }
    public Pedido getPedido() {
        return this.pedido;
    }
    
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    public Integer getEstado() {
        return this.estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    public Set getPedidoParcialXProductos() {
        return this.pedidoParcialXProductos;
    }
    
    public void setPedidoParcialXProductos(Set pedidoParcialXProductos) {
        this.pedidoParcialXProductos = pedidoParcialXProductos;
    }




}


