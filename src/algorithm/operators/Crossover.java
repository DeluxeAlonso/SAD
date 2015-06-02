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
import algorithm.grasp.Grasp;
import entity.UnidadTransporte;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author robert
 */
public class Crossover {

    public static Solution uniformCrossover(Solution[] parents, Algorithm algorithm, Problem problem) {
        Node[][] parentRoutes1 = parents[0].getNodes();
        Node[][] parentRoutes2 = parents[1].getNodes();
        int nRoutes = parentRoutes1.length;
        
        boolean[][] conflicts = new boolean[nRoutes][nRoutes];
        for (int i = 0; i < nRoutes; i++) {
            for (int j = 0; j < nRoutes; j++) {
                if(thereIsConflict(parentRoutes1[i], parentRoutes2[j]))
                    conflicts[i][j] = true;                
            }            
        }
        
        double[] R1 = getR(parentRoutes1, algorithm, problem);
        double[] R2 = getR(parentRoutes2, algorithm, problem);
        Integer[] order1 = new Integer[nRoutes];
        Integer[] order2 = new Integer[nRoutes];
        for (int i = 0; i < nRoutes; i++) {
            order1[i] = order2[i] = i;            
        }
        Arrays.sort(order1, (Integer t1, Integer t2) -> Double.compare(R1[t1], R1[t2]));
        Arrays.sort(order2, (Integer t1, Integer t2) -> Double.compare(R2[t1], R2[t2]));
        
        boolean[] posCand1 = new boolean[nRoutes];
        boolean[] posCand2 = new boolean[nRoutes];
        for (int i = 0; i < nRoutes; i++) {
            posCand1[i] = posCand2[i] = true;            
        }
        
        int idx1 = 0, idx2 = 0, idxChild = 0;
        Node[][] childRoutes = new Node[nRoutes][0];
        
        while(idx1<parentRoutes1.length && idx2<parentRoutes2.length){
            if(idxChild==nRoutes) break;
            boolean found1 = false, found2 = false;
            while(!found1 && idx1 < parentRoutes1.length){
                if(posCand1[order1[idx1]] && parentRoutes1[order1[idx1]].length>0){
                    childRoutes[idxChild] = parentRoutes1[order1[idx1]];
                    for (int i = 0; i < nRoutes; i++) {
                        if(conflicts[order1[idx1]][i])
                            posCand2[i] = false;                        
                    }
                    found1 = true;
                    idxChild++;
                }
                idx1++;
            }
            if(idxChild==nRoutes) break;
            while(!found2 && idx2 < parentRoutes2.length){
                if(posCand2[order2[idx2]] && parentRoutes2[order2[idx2]].length>0){
                    childRoutes[idxChild] = parentRoutes2[order2[idx2]];
                    for (int i = 0; i < nRoutes; i++) {
                        if(conflicts[i][order2[idx2]])
                            posCand1[i] = false;                        
                    }
                    found2 = true;
                    idxChild++;
                }
                idx2++;
            }
        }
        
        HashMap<Integer,Integer> currentStock = new HashMap<> (problem.getProductsStock());        
        boolean[] visited = new boolean[problem.getNodes().size()];
        
        for (int i = 0; i < idxChild; i++) {
            for (Node node : childRoutes[i]) {
                visited[node.getIdx()] = true;
                int productId = node.getProduct().getId();
                int newStock = currentStock.get(productId) - node.getDemand();
                currentStock.put(productId, newStock);                
            }
        }        
        
        for (int i = idxChild; i < nRoutes; i++) {
            UnidadTransporte vehicle = problem.getVehicles().get(i);
            ArrayList<Node> route = Grasp.createRoute(vehicle, algorithm, currentStock, visited);
            childRoutes[i] = new Node[route.size()];
            childRoutes[i] = route.toArray(childRoutes[i]);            
        }
        
        return new Solution(childRoutes, algorithm, problem);                
    }

    private static boolean thereIsConflict(Node[] rutas1, Node[] rutas2) {
        for (int i = 1; i < rutas1.length; i++) {
            for (int j = 1; j < rutas2.length; j++) {
                if (rutas1[i].getIdx() == rutas2[j].getIdx()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static double[] getR(Node[][] routes, Algorithm algorithm, Problem problem) {
        double[] R = new double[routes.length];        
        UnidadTransporte vehicle = problem.getVehicles().get(0);
        for (int i = 0; i < routes.length; i++) {
            double routeCost = ObjectiveFunction.getRouteCost(vehicle, routes[i], algorithm, null);
            double nodes = routes[i].length;
            R[i] = routeCost/nodes;
        }
        return R;
    }
    
}
