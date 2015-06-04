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
import static algorithm.operators.ObjectiveFunction.distance;
import entity.UnidadTransporte;
import java.util.ArrayList;
import java.util.HashMap;
import util.Constants;
import util.Randomizer;

/**
 *
 * @author robert
 */
public class Grasp {    
    private static ArrayList<Node> nodes;    
    
    public static Node[][] construction(Algorithm algorithm, Problem problem) {
        int routesNo = problem.getVehicles().size();
        Node[][] routes = new Node[routesNo][];
        nodes = problem.getNodes();
        boolean[] visited = new boolean[nodes.size()];
        
        HashMap<Integer,Integer> currentStock = new HashMap<> (problem.getProductsStock());        
        
        for (int i = 0; i < routesNo; i++) {
            System.out.println("vehicle " + i);
            
            UnidadTransporte vehicle = problem.getVehicles().get(i);
            
            ArrayList<Node> route = createRoute(vehicle, algorithm, currentStock, visited);
            
            routes[i] = new Node[route.size()];
            routes[i] = route.toArray(routes[i]);
            //if(routes[i].length == 0) throw new AssertionError(i + " is a bad route");
        }
        
        
        int vis = 0;
        for (int i = 0; i < visited.length; i++) {
            if(visited[i]) vis++;            
        }
        System.out.println("visited nodes: " + vis);
        
        return routes;
    }

    private static GraspParameters initializeBetaAndTau(Node baseNode, UnidadTransporte vehicle,
            Algorithm algorithm, double currentTime, HashMap<Integer,Integer> currentStock,
            boolean[] visited) {
        double beta=0, tau=0;
        boolean alreadyInit = false;        
        double speed = vehicle.getTipoUnidadTransporte().getVelocidadPromedio();
        for (int i = 0; i < nodes.size(); i++) {
            if(!visited[i]){
                //Time to return to warehouse
                double travelCost = distance(nodes.get(i).getX(), nodes.get(i).getY(),
                        Constants.WAREHOUSE_LONGITUDE, Constants.WAREHOUSE_LATITUDE)/speed;
                //Time to go to baseNode
                if (baseNode == null) {
                    travelCost += distance(Constants.WAREHOUSE_LONGITUDE, 
                            Constants.WAREHOUSE_LATITUDE,
                        nodes.get(i).getX(), nodes.get(i).getY())/speed;                    
                } else {
                    travelCost += distance(baseNode.getX(), baseNode.getY(),
                        nodes.get(i).getX(), nodes.get(i).getY())/speed;
                }
                int productId = nodes.get(i).getProduct().getId();
                int newStock = currentStock.get(productId)
                        - nodes.get(i).getDemand();
                
                double cost = ObjectiveFunction.objectiveFunction(vehicle, 
                        nodes.get(i), algorithm, 0, currentTime, 0, travelCost, newStock);
                
                //System.out.println(i + " travelCost: " + travelCost + "   cost: " + cost);
                
                if(cost>algorithm.getOvertimePenalty() || cost>algorithm.getOverstockPenalty())
                    continue;
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
        return new GraspParameters(beta, tau);
    }    

    private static ArrayList<Node> generateRCL(Node baseNode, double beta, double tau, 
            double alpha, UnidadTransporte vehicle, Algorithm algorithm, 
            double currentTime, HashMap<Integer,Integer> currentStock,
            boolean[] visited) {
        ArrayList<Node> RCL = new ArrayList<>();
        double speed = vehicle.getTipoUnidadTransporte().getVelocidadPromedio();
        for (int i = 0; i < nodes.size(); i++) {
            if(!visited[i]){
                //Time to return to warehouse
                double travelCost = distance(nodes.get(i).getX(), nodes.get(i).getY(),
                        Constants.WAREHOUSE_LONGITUDE, Constants.WAREHOUSE_LATITUDE)/speed;
                //Time to go to baseNode
                if (baseNode == null) {
                    travelCost += distance(Constants.WAREHOUSE_LONGITUDE,
                            Constants.WAREHOUSE_LATITUDE,
                            nodes.get(i).getX(), nodes.get(i).getY()) / speed;
                } else {
                    travelCost += distance(baseNode.getX(), baseNode.getY(),
                            nodes.get(i).getX(), nodes.get(i).getY()) / speed;
                }
                
                int productId = nodes.get(i).getProduct().getId();
                int newStock = currentStock.get(productId)
                        - nodes.get(i).getDemand();
                
                double cost = ObjectiveFunction.objectiveFunction(vehicle, 
                        nodes.get(i), algorithm, 0, currentTime, 0, travelCost, newStock);
                
                if(cost <= beta + alpha*(tau-beta))
                    RCL.add(nodes.get(i));
            }            
        }        
        return RCL;
    }

    public static ArrayList<Node> createRoute(UnidadTransporte vehicle, Algorithm algorithm,
            HashMap<Integer,Integer> currentStock, boolean[] visited) {
        ArrayList<Node> route = new ArrayList<>();

        ArrayList<Node> RCL;
        
        int currentCapacity = 0, vehicleCapacity = vehicle.getTipoUnidadTransporte().getCapacidadPallets();
        double currentTime = 0; 
        int idx = 0;

        boolean alreadyInit = false;
        Node node = null, nextNode;
        while (true) {
            GraspParameters param = initializeBetaAndTau(node, vehicle, algorithm, currentTime, currentStock, 
                    visited);
            if (!alreadyInit) {
                RCL = generateRCL(node, param.getBeta(), param.getTau(), 1, vehicle, algorithm, currentTime, 
                        currentStock, visited);
                alreadyInit = true;
            } else {
                RCL = generateRCL(node, param.getBeta(), param.getTau(), algorithm.getGraspAlpha(), vehicle, 
                        algorithm, currentTime, currentStock, visited);
            }
            
            
            //System.out.println(param.getBeta() + " " + param.getTau());
            //if(idx==0 && RCL.isEmpty()) throw new AssertionError("bad RCL");            
            
            if (RCL.isEmpty()) {
                break; //if there are no more nodes to add
            }
            int chosen = Randomizer.randomInt(RCL.size());
            boolean canBeAdded = true;
            if (currentCapacity + RCL.get(chosen).getDemand() > vehicleCapacity) {
                canBeAdded = false;
            }
            
            //System.out.println(currentCapacity + " " + RCL.get(chosen).getDemand() + " " + vehicleCapacity );
            //if(idx==0 && !canBeAdded) throw new AssertionError("bad demand");
            
            if (canBeAdded) {
                nextNode = RCL.get(chosen);
                visited[nextNode.getIdx()] = true;                
                route.add(nextNode);
                currentCapacity += nextNode.getDemand();

                double travelCost;
                if (node == null) {
                    travelCost = distance(Constants.WAREHOUSE_LONGITUDE,
                            Constants.WAREHOUSE_LATITUDE,
                            nextNode.getX(), nextNode.getY())
                            / vehicle.getTipoUnidadTransporte().getVelocidadPromedio();
                } else {
                    travelCost = distance(node.getX(), node.getY(),
                            nextNode.getX(), nextNode.getY())
                            / vehicle.getTipoUnidadTransporte().getVelocidadPromedio();
                }

                int productId = nextNode.getProduct().getId();
                int newStock = currentStock.get(productId) - nextNode.getDemand();

                currentTime += travelCost;
                currentStock.put(productId, newStock);
            } else {
                break; //if the vehicle is full
            }
            
            idx++;
        }
        return route;
    }
}
