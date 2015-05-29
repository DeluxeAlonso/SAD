/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import entity.PedidoParcial;
import entity.UnidadTransporte;

/**
 *
 * @author robert
 */

public class Solution implements Comparable<Solution>{
    private PedidoParcial[][] acceptedOrders;
    private UnidadTransporte[] vehicles;
    private Algorithm algorithm;
    private Problem problem;
    private float cost;

    Solution(Algorithm algorithm, Problem problem) {
        this.algorithm = algorithm;
        this.problem = problem;
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public int compareTo(Solution o) {
        return Double.compare(this.getCost(), o.getCost());
    }

    public PedidoParcial[][] getAcceptedOrders() {
        return acceptedOrders;
    }

    public void setAcceptedOrders(PedidoParcial[][] acceptedOrders) {
        this.acceptedOrders = acceptedOrders;
    }

    public UnidadTransporte[] getVehicles() {
        return vehicles;
    }

    public void setVehicles(UnidadTransporte[] vehicles) {
        this.vehicles = vehicles;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
    
}
