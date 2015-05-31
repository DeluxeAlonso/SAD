/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.grasp;

import algorithm.Algorithm;
import algorithm.Node;
import algorithm.Problem;
import algorithm.operators.ObjectiveFunction;
import entity.UnidadTransporte;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import util.Constants;

/**
 *
 * @author robert
 */
public class Grasp {
    private static double beta;
    private static double tau;
    private static ArrayList<Node> nodes;
    private static boolean[] visited;
    
    
    public static Node[][] construction(Algorithm algorithm, Problem problem) {
        nodes = problem.getNodes();
        visited = new boolean[nodes.size()];
        HashMap<Integer,Integer> currentStock = new HashMap<> (problem.getProductsStock());        
        Node node;
        for (UnidadTransporte vehicle : problem.getVehicles()) {
            int currentCapacity = 0;
            while(currentCapacity <= vehicle.getTipoUnidadTransporte().getCapacidadPallets()){
                node = null;
                initializeBetaAndTau(node, vehicle, algorithm);
                
                //break if currentStock goes beyond limit
            }
        }

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void initializeBetaAndTau(Node baseNode, UnidadTransporte vehicle,
            Algorithm algorithm) {
        beta = 0;
        tau = 0;
        double speed = vehicle.getTipoUnidadTransporte().getVelocidadPromedio();
        for (int i = 0; i < nodes.size(); i++) {
            if(visited[i]){
                double travelCost;
                if (baseNode == null) {
                    travelCost = Point2D.distance(Constants.WAREHOUSE_LATITUDE, 
                            Constants.WAREHOUSE_LONGITUDE,
                        nodes.get(i).getX(), nodes.get(i).getY())/speed;                    
                } else {
                    travelCost = Point2D.distance(baseNode.getX(), baseNode.getY(),
                        nodes.get(i).getX(), nodes.get(i).getY())/speed;
                }
                /*
                int extraCap = nodes.get(i).getDemand();
                productId = route[i].getProduct().getId();
                newStock = currentStock.get(productId)
                        - route[i].getDemand();
                routeCost += objectiveFunction(vehicle, route[i], algorithm,
                        currentCap, currentTime, extraCap, travelCost, newStock);
                currentCap += extraCap;
                currentStock.put(productId, newStock);*/
                
                double cost = ObjectiveFunction.objectiveFunction(vehicle, 
                        nodes.get(i), algorithm, 0, 0, 0, travelCost, 0);
            }
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
