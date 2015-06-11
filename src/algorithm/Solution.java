/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import algorithm.grasp.Grasp;
import entity.UnidadTransporte;

/**
 *
 * @author robert
 */

public class Solution implements Comparable<Solution>{
    private Node[][] nodes;    
    private Algorithm algorithm;
    private Problem problem;
    private double cost;
    
    public Solution(){}

    public Solution(Node[][] nodes, Algorithm algorithm, Problem problem) {
        this.nodes = nodes;
        this.algorithm = algorithm;
        this.problem = problem;
    }
    
    public Solution(Algorithm algorithm, Problem problem, int idx) {
        this.algorithm = algorithm;
        this.problem = problem;        
        if(idx%6==0)
            this.nodes = Grasp.construction(algorithm, problem);
        else if(idx%6==1)
            this.nodes = Grasp.construction2(algorithm, problem);
        else if(idx%6==2)
            this.nodes = Grasp.construction3(algorithm, problem);              
        else
            this.nodes = Grasp.construction4(algorithm, problem);
    }
    
    @Override
    public int compareTo(Solution o) {
        return Double.compare(this.getCost(), o.getCost());
    }

    public Node[][] getNodes() {
        return nodes;
    }

    public void setNodes(Node[][] nodes) {
        this.nodes = nodes;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isEmpty() {
        for(int i=0; i<nodes.length; i++)
            if(nodes[i].length>0) return false;
        return true;        
    }
}
