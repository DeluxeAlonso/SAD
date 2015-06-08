package entity;
// Generated 07/06/2015 09:17:33 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * TipoUnidadTransporte generated by hbm2java
 */
public class TipoUnidadTransporte  implements java.io.Serializable {


     private Integer id;
     private Condicion condicion;
     private String descripcion;
     private Integer capacidadPallets;
     private Double velocidadPromedio;
     private Set unidadTransportes = new HashSet(0);

    public TipoUnidadTransporte() {
    }

	
    public TipoUnidadTransporte(Condicion condicion) {
        this.condicion = condicion;
    }
    public TipoUnidadTransporte(Condicion condicion, String descripcion, Integer capacidadPallets, Double velocidadPromedio, Set unidadTransportes) {
       this.condicion = condicion;
       this.descripcion = descripcion;
       this.capacidadPallets = capacidadPallets;
       this.velocidadPromedio = velocidadPromedio;
       this.unidadTransportes = unidadTransportes;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Condicion getCondicion() {
        return this.condicion;
    }
    
    public void setCondicion(Condicion condicion) {
        this.condicion = condicion;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Integer getCapacidadPallets() {
        return this.capacidadPallets;
    }
    
    public void setCapacidadPallets(Integer capacidadPallets) {
        this.capacidadPallets = capacidadPallets;
    }
    public Double getVelocidadPromedio() {
        return this.velocidadPromedio;
    }
    
    public void setVelocidadPromedio(Double velocidadPromedio) {
        this.velocidadPromedio = velocidadPromedio;
    }
    public Set getUnidadTransportes() {
        return this.unidadTransportes;
    }
    
    public void setUnidadTransportes(Set unidadTransportes) {
        this.unidadTransportes = unidadTransportes;
    }




}


