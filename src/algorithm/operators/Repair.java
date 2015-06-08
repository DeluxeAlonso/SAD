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
import static algorithm.operators.ObjectiveFunction.distance;
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
            int totalDemand = 0;
            //double routeCost = getRouteCost(vehicles.get(i), routes[i], algorithm,
            //        null);
            for (int j = 0; j < routes[i].length; j++) {
                totalDemand += routes[i][j].getDemand();                
            }            
            if(totalDemand < lowestNodes){
                lowestNodes = totalDemand;
                lowestCostIdx = i;
            }
            if(totalDemand > highestNodes){
                highestNodes = totalDemand;
                highestCostIdx = i;
            }
        }      
        
        Node[] highest = routes[highestCostIdx];
        Node[] lowest = routes[lowestCostIdx];
        
        if(highest.length==0){
            System.out.println("bad highest");
            return null;
        }
        
        //System.out.println("moving " + highestCostIdx + "/" + " to " + lowestCostIdx + "/" );
                
        
        int idxJOut = 0, maxDem = 0;
        for (int i = 0; i < highest.length; i++) {
            if(highest[i].getDemand()>maxDem){
                maxDem = highest[i].getDemand();
                idxJOut = i;
            }            
        }
        
        changePosition(routes, algorithm, problem, highestCostIdx, idxJOut, lowestCostIdx);
        
        
        /*
        Node out = highest[idxJOut];
        
        Node[] newHighest = new Node[highest.length-1];
        Node[] newLowest = new Node[lowest.length+1];
        
        System.arraycopy(highest, 0, newHighest, 0, highest.length-1);
        System.arraycopy(lowest, 0, newLowest, 0, lowest.length);
        
        //System.arraycopy(highest, 0, newHighest, 0, newHighest.length-1);
        //System.arraycopy(lowest, 0, newLowest, 0, newLowest.length);
        newLowest[lowest.length] = out;
        routes[highestCostIdx] = newHighest;
        routes[lowestCostIdx] = newLowest;*/
        
        s.setNodes(routes);
        return s;
    } 

    private static void changePosition(Node[][] nodes, Algorithm algorithm, Problem problem, int idxI, int idxJ, int iRoute) {
        UnidadTransporte vehicle = problem.getVehicles().get(0);
        double speed = vehicle.getTipoUnidadTransporte().getVelocidadPromedio();
        Node node = nodes[idxI][idxJ];
        double bestPayoff = -Double.MAX_VALUE;
        int jRoute = 0;
        //for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[iRoute].length; j++) {
                double travelCost, toNode, fromNode;
                if (j > 0) {
                    travelCost = distance(nodes[iRoute][j - 1].getIdx(), nodes[iRoute][j].getIdx()) / speed;
                    toNode = distance(nodes[iRoute][j - 1].getIdx(), node.getIdx()) / speed;
                } else {
                    travelCost = distance(Problem.getLastNode(), nodes[iRoute][j].getIdx()) / speed;
                    toNode = distance(Problem.getLastNode(), node.getIdx()) / speed;
                }
                fromNode = distance(node.getIdx(), nodes[iRoute][j].getIdx()) / speed;
                double payoff = ObjectiveFunction.objectiveFunction(vehicle, nodes[iRoute][j],
                        algorithm, 0, 0, 0, travelCost, 0)
                        - ObjectiveFunction.objectiveFunction(vehicle, node,
                                algorithm, 0, 0, 0, toNode, 0)
                        - ObjectiveFunction.objectiveFunction(vehicle, nodes[iRoute][j],
                                algorithm, 0, 0, 0, fromNode, 0);
                if (payoff > bestPayoff) {
                    bestPayoff = payoff;
                    //iRoute = iRoute;
                    jRoute = j;
                }
            }
        //}

//        System.out.println("node1: " + node.getProduct().getId());
        //System.out.println("moving " + idxI + "/" + idxJ + " to " + iRoute + "/" + jRoute);

        Node[] fromRoute = nodes[idxI];
        Node[] toRoute = nodes[iRoute];
        Node[] newFromRoute = new Node[fromRoute.length - 1];
        Node[] newToRoute = new Node[toRoute.length + 1];
        System.arraycopy(fromRoute, 0, newFromRoute, 0, idxJ);
//        if(node==null) throw new AssertionError("there is a null node");
//        System.out.println("node2: " + node.getProduct().getId());
        for (int i = idxJ + 1; i < fromRoute.length; i++) {
            newFromRoute[i - 1] = fromRoute[i];
        }
        System.arraycopy(toRoute, 0, newToRoute, 0, jRoute);
        newToRoute[jRoute] = node;
        for (int i = jRoute; i < toRoute.length; i++) {
            newToRoute[i + 1] = toRoute[i];
        }
        //System.arraycopy(toRoute, jRoute, newToRoute, jRoute + 1, toRoute.length - jRoute); //here could be the error
        nodes[idxI] = newFromRoute;
        nodes[iRoute] = newToRoute;

//        System.out.println("");
//        System.out.println("newFromRoute");
        /*for (int i = 0; i < newFromRoute.length; i++) {
         try{
         System.out.print(newFromRoute[i].getProduct().getId() + "/" + newFromRoute[i].getDemand() + 
         "/" + newFromRoute[i].getPartialOrder().getPedido().getId() +"  ");            
         }catch(Exception ex){
         System.out.println("exception from : " + i + " " + newFromRoute.length);
         }
         }
        
         System.out.println("newToRoute");
         for (int i = 0; i < newToRoute.length; i++) {
         try{
         System.out.print(newToRoute[i].getProduct().getId() + "/" + newToRoute[i].getDemand() + 
         "/" + newToRoute[i].getPartialOrder().getPedido().getId() +"  ");            
         }catch(Exception ex){
         System.out.println("exception to : " + i + " " + newToRoute.length);
         }
         }        
         */
    }
    
}
