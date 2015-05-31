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
import java.util.Iterator;
import util.Constants;
import util.Randomizer;

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
        int routesNo = problem.getVehicles().size();
        Node[][] routes = new Node[routesNo][];
        nodes = problem.getNodes();
        visited = new boolean[nodes.size()];
        ArrayList<Node> RCL;
        HashMap<Integer,Integer> currentStock = new HashMap<> (problem.getProductsStock());        
        Node node;
        boolean alreadyInit;
        for (int i = 0; i < routesNo; i++) {
            UnidadTransporte vehicle = problem.getVehicles().get(i);
            ArrayList<Node> route = new ArrayList<>();
            int currentCapacity = vehicle.getTipoUnidadTransporte().getCapacidadPallets();
            double currentTime = 3;
            alreadyInit = false; node = null;
            while(currentCapacity >=0){                
                initializeBetaAndTau(node, vehicle, algorithm);
                if(!alreadyInit){
                    RCL = generateRCL(node, beta, tau, 1, vehicle, algorithm);
                    alreadyInit = true;
                }
                else
                    RCL = generateRCL(node, beta, tau, algorithm.getGraspAlpha(), vehicle, algorithm);
                int chosen = Randomizer.randomInt(RCL.size());
                boolean canBeAdded = true;
                if(currentCapacity < RCL.get(chosen).getDemand())
                    canBeAdded = false;
                if(currentStock.get(RCL.get(chosen).getProduct().getId()) < 
                        RCL.get(chosen).getDemand())
                    canBeAdded = false;
                double returnTravelTime = Point2D.distance(RCL.get(chosen).getX(), 
                        RCL.get(chosen).getY(), 
                    Constants.WAREHOUSE_LATITUDE, Constants.WAREHOUSE_LONGITUDE)/
                    vehicle.getTipoUnidadTransporte().getVelocidadPromedio();
                if(currentTime < returnTravelTime)
                    canBeAdded = false;
                if(canBeAdded){
                    visited[chosen] = true;
                    node = RCL.get(chosen);
                    route.add(node);        
                    currentCapacity -= node.getDemand();
                }
                else break;
            }
            routes[i] = new Node[route.size()];
            routes[i] = route.toArray(routes[i]);
        }
        return routes;
    }

    private static void initializeBetaAndTau(Node baseNode, UnidadTransporte vehicle,
            Algorithm algorithm) {
        boolean alreadyInit = false;        
        double speed = vehicle.getTipoUnidadTransporte().getVelocidadPromedio();
        for (int i = 0; i < nodes.size(); i++) {
            if(!visited[i]){
                double travelCost;
                if (baseNode == null) {
                    travelCost = Point2D.distance(Constants.WAREHOUSE_LATITUDE, 
                            Constants.WAREHOUSE_LONGITUDE,
                        nodes.get(i).getX(), nodes.get(i).getY())/speed;                    
                } else {
                    travelCost = Point2D.distance(baseNode.getX(), baseNode.getY(),
                        nodes.get(i).getX(), nodes.get(i).getY())/speed;
                }
                double cost = ObjectiveFunction.objectiveFunction(vehicle, 
                        nodes.get(i), algorithm, 0, 0, 0, travelCost, 0);
                if(!alreadyInit){
                    tau = cost;
                    beta = cost;
                    alreadyInit = true;
                }
                else{
                    beta = Math.min(cost, beta);
                    tau = Math.max(cost, tau);
                }
            }
        }        
    }    

    private static ArrayList<Node> generateRCL(Node baseNode, double beta, double tau, 
            double alpha, UnidadTransporte vehicle, Algorithm algorithm) {
        ArrayList<Node> RCL = new ArrayList<>();
        double speed = vehicle.getTipoUnidadTransporte().getVelocidadPromedio();
        for (int i = 0; i < nodes.size(); i++) {
            if(!visited[i]){
                double travelCost;
                if (baseNode == null) {
                    travelCost = Point2D.distance(Constants.WAREHOUSE_LATITUDE,
                            Constants.WAREHOUSE_LONGITUDE,
                            nodes.get(i).getX(), nodes.get(i).getY()) / speed;
                } else {
                    travelCost = Point2D.distance(baseNode.getX(), baseNode.getY(),
                            nodes.get(i).getX(), nodes.get(i).getY()) / speed;
                }
                double cost = ObjectiveFunction.objectiveFunction(vehicle,
                        nodes.get(i), algorithm, 0, 0, 0, travelCost, 0);
                if(cost <= beta + alpha*(tau-beta))
                    RCL.add(nodes.get(i));
            }            
        }        
        return RCL;
    }
}
