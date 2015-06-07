/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.operators;

import algorithm.Algorithm;
import algorithm.Node;
import algorithm.Problem;
import algorithm.Solution;
import static algorithm.operators.ObjectiveFunction.getRouteCost;
import entity.UnidadTransporte;
import java.util.ArrayList;

/**
 *
 * @author robert
 */
public class Repair {

    public static Solution repair(Solution s, Algorithm algorithm, Problem problem) {
        Node[][] routes = s.getNodes();
        ArrayList<UnidadTransporte> vehicles = problem.getVehicles();
        int highestCostIdx = 0, lowestCostIdx = 0;
        //double highestCost = -Double.MAX_VALUE, lowestCost = Double.MAX_VALUE;
        int highestNodes = -Integer.MAX_VALUE, lowestNodes = Integer.MAX_VALUE;
        for (int i = 0; i < routes.length; i++) {
            //double routeCost = getRouteCost(vehicles.get(i), routes[i], algorithm,
            //        null);
            int nodesNo = routes[i].length;
            if(nodesNo < lowestNodes){
                lowestNodes = nodesNo;
                lowestCostIdx = i;
            }
            if(nodesNo < highestNodes){
                highestNodes = nodesNo;
                highestCostIdx = i;
            }
        }      
        
        Node[] highest = routes[highestCostIdx];
        Node[] lowest = routes[lowestCostIdx];
        
        if(highest.length==0){
            System.out.println("bad highest");
            return null;
        }
        
        Node out = highest[highest.length-1];
        Node[] newHighest = new Node[highest.length-1];
        Node[] newLowest = new Node[lowest.length+1];
        System.arraycopy(highest, 0, newHighest, 0, highest.length-1);
        System.arraycopy(lowest, 0, newLowest, 0, lowest.length);
        
        //System.arraycopy(highest, 0, newHighest, 0, newHighest.length-1);
        //System.arraycopy(lowest, 0, newLowest, 0, newLowest.length);
        newLowest[lowest.length] = out;
        routes[highestCostIdx] = newHighest;
        routes[lowestCostIdx] = newLowest;
        s.setNodes(routes);
        return s;
    } 
    
}
