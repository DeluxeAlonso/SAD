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
           /* System.out.println("");
            System.out.println("vehicle " + i);
            System.out.println("");*/
            
            UnidadTransporte vehicle = problem.getVehicles().get(i);
            
            ArrayList<Node> route = createRoute(vehicle, algorithm, currentStock, visited);
            
            routes[i] = new Node[route.size()];
            routes[i] = route.toArray(routes[i]);
            
            
        }
        
        /*currentStock = new HashMap<> (problem.getProductsStock());   
        for (int i = 0; i < routesNo; i++) {
            UnidadTransporte vehicle = problem.getVehicles().get(i);
            System.out.println("costo ruta " + i + " " + ObjectiveFunction.getRouteCost(vehicle, routes[i], algorithm, currentStock));            
            
        }*/
        
        /*int vis = 0;
        for (int i = 0; i < visited.length; i++) {
            if(visited[i]) vis++;            
        }*/
        //System.out.println("visited nodes: " + vis);
        
        return routes;
    }
    
    
    public static Node[][] construction2(Algorithm algorithm, Problem problem) {
        int routesNo = problem.getVehicles().size();
        Node[][] routes = new Node[routesNo][];
        nodes = problem.getNodes();
        boolean[] visited = new boolean[nodes.size()];

        HashMap<Integer, Integer> currentStock = new HashMap<>(problem.getProductsStock());

        for (int i = routesNo-1; i >= 0; i--) {
            UnidadTransporte vehicle = problem.getVehicles().get(i);

            ArrayList<Node> route = createRoute(vehicle, algorithm, currentStock, visited);

            routes[i] = new Node[route.size()];
            routes[i] = route.toArray(routes[i]);

        }
        return routes;
    }
    
    
    public static Node[][] construction3(Algorithm algorithm, Problem problem) {
        int routesNo = problem.getVehicles().size();
        
        nodes = problem.getNodes();
        boolean[] visited = new boolean[nodes.size()];

        HashMap<Integer, Integer> currentStock = new HashMap<>(problem.getProductsStock());   
        
        UnidadTransporte vehicle = problem.getVehicles().get(0);
        int[] currentCapacity = new int[routesNo];
        int vehicleCapacity = vehicle.getTipoUnidadTransporte().getCapacidadPallets();
        double[] currentTime = new double[routesNo];
        Node[] node = new Node[routesNo];
        boolean[] alreadyInit = new boolean[routesNo];
        
        ArrayList<ArrayList<Node>>arrRoutes = new ArrayList<>();
        for (int i = 0; i < routesNo; i++) {
            ArrayList<Node> arrRoutes2 = new ArrayList<>();
            arrRoutes.add(arrRoutes2); 
        }
               
        int idx = 0, noInsertion = 0;
        
        while(true){
            noInsertion++;
            GraspParameters param = initializeBetaAndTau(node[idx], vehicle, algorithm, currentTime[idx], currentStock,
                    visited);
            ArrayList<Node> RCL;
            if (!alreadyInit[idx]) {
                RCL = generateRCL(node[idx], param.getBeta(), param.getTau(), 1, vehicle, algorithm, currentTime[idx],
                        currentStock, visited);
                alreadyInit[idx] = true;
            } else {
                RCL = generateRCL(node[idx], param.getBeta(), param.getTau(), algorithm.getGraspAlpha(), vehicle,
                        algorithm, currentTime[idx], currentStock, visited);
            }
            if (!RCL.isEmpty()) {
                int chosen = Randomizer.randomInt(RCL.size());
                boolean canBeAdded = true;
                if (currentCapacity[idx] + RCL.get(chosen).getDemand() > vehicleCapacity) {
                    canBeAdded = false;
                }
                if (canBeAdded) {
                    Node nextNode = RCL.get(chosen);
                    visited[nextNode.getIdx()] = true;
                    arrRoutes.get(idx).add(nextNode);
                    noInsertion = 0;
                    //System.out.println("added " + idx + " node");
                    currentCapacity[idx] += nextNode.getDemand();

                    double travelCost;
                    if (node[idx] == null) {
                        travelCost = distance(Problem.getLastNode(), nextNode.getIdx())
                                / vehicle.getTipoUnidadTransporte().getVelocidadPromedio();
                    } else {
                        travelCost = distance(node[idx].getIdx(), nextNode.getIdx())
                                / vehicle.getTipoUnidadTransporte().getVelocidadPromedio();
                    }

                    int productId = nextNode.getProduct().getId();
                    int newStock = currentStock.get(productId) - nextNode.getDemand();

                    currentTime[idx] += travelCost;
                    currentStock.put(productId, newStock);

                    /*double returnCost = distance(nextNode.getIdx(), Problem.getLastNode())
                            / vehicle.getTipoUnidadTransporte().getVelocidadPromedio();*/
                    /*System.out.println("RCL added: " + travelCost + " giving a total of: " + 
                     currentTime + " and to return, we need " + returnCost + " so we need " + 
                     (currentTime + returnCost));*/
                    node[idx] = nextNode;
                }
            }
            
            if(noInsertion==routesNo) break;
            idx = (idx+1)%routesNo;
        }
        
        Node[][] routes = new Node[routesNo][];
        
        for (int i = 0; i<routesNo; i++) {            
            routes[i] = new Node[arrRoutes.get(i).size()];
            routes[i] = arrRoutes.get(i).toArray(routes[i]);
        }
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
                double travelCost = distance(nodes.get(i).getIdx(), Problem.getLastNode())/speed;
                //System.out.println("first toWarehouse: " + travelCost);
                //Time to go to baseNode
                if (baseNode == null) {
                    travelCost += distance(Problem.getLastNode(), nodes.get(i).getIdx())/speed;                    
                } else {
                    travelCost += distance(baseNode.getIdx(), nodes.get(i).getIdx())/speed;
                }
                int productId = nodes.get(i).getProduct().getId();
                int newStock = currentStock.get(productId) - nodes.get(i).getDemand();
                
                double cost = ObjectiveFunction.objectiveFunction(vehicle, 
                        nodes.get(i), algorithm, 0, currentTime, 0, travelCost, newStock);
                
                //System.out.println(i + "currentTime: " + currentTime + "   travelCost: " + travelCost + "   cost: " + cost);
                
                if(cost>2*travelCost/* || cost>algorithm.getOverstockPenalty()*/)
                    continue;
                //System.out.println("chosen");
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
                double travelCost = distance(nodes.get(i).getIdx(), Problem.getLastNode())/speed;
                //Time to go to baseNode
                if (baseNode == null) {
                    travelCost += distance(Problem.getLastNode(), nodes.get(i).getIdx()) / speed;
                } else {
                    travelCost += distance(baseNode.getIdx(), nodes.get(i).getIdx()) / speed;
                }
                
                int productId = nodes.get(i).getProduct().getId();
                int newStock = currentStock.get(productId) - nodes.get(i).getDemand();
                
                double cost = ObjectiveFunction.objectiveFunction(vehicle, 
                        nodes.get(i), algorithm, 0, currentTime, 0, travelCost, newStock);
                
                if(cost <= beta + alpha*(tau-beta)){
                   // System.out.println("RCL added: " + travelCost);
                    RCL.add(nodes.get(i));
                }
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
            
            /*System.out.println("");
            System.out.println("Beta and Tau: " + param.getBeta() + " " + param.getTau());*/
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
                //System.out.println("added " + idx + " node");
                currentCapacity += nextNode.getDemand();

                double travelCost;
                if (node == null) {
                    travelCost = distance(Problem.getLastNode(), nextNode.getIdx())
                            / vehicle.getTipoUnidadTransporte().getVelocidadPromedio();
                } else {
                    travelCost = distance(node.getIdx(), nextNode.getIdx())
                            / vehicle.getTipoUnidadTransporte().getVelocidadPromedio();
                }

                int productId = nextNode.getProduct().getId();
                int newStock = currentStock.get(productId) - nextNode.getDemand();

                currentTime += travelCost;
                currentStock.put(productId, newStock);
                
                double returnCost = distance(nextNode.getIdx(), Problem.getLastNode())/                        
                         vehicle.getTipoUnidadTransporte().getVelocidadPromedio();
                /*System.out.println("RCL added: " + travelCost + " giving a total of: " + 
                        currentTime + " and to return, we need " + returnCost + " so we need " + 
                        (currentTime + returnCost));*/
                node = nextNode;
            } else {
                break; //if the vehicle is full
            }
            
            idx++;
        }
        return route;
    }
}
