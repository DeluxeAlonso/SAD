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
import entity.UnidadTransporte;
import util.Randomizer;

/**
 *
 * @author robert
 */
public class Mutation {

    public static Solution mutation(Solution s, Algorithm algorithm, Problem problem) {
        if(!Randomizer.doIt(algorithm.getMutationRate())) return s;
        Node[][] nodes = s.getNodes();
        int idxStart = Randomizer.randomInt(nodes.length);
        int idxI, idxJ;
        for (idxI = idxJ = idxStart; idxI >= 0 || idxJ < nodes.length; idxI--, idxJ++) { //find a non_empty route
            if (idxI >= 0 && nodes[idxI].length>0) {
                idxJ = -1;
                break;
            }
            if (idxJ < nodes.length && nodes[idxJ].length>0) {
                idxI = -1;
                break;
            }
        }
        //if(idxI<0 && idxJ==nodes.length)
            
        idxI = Math.max(idxI,idxJ);
        if(idxI>=nodes.length)
            return s;
        idxJ = Randomizer.randomInt(nodes[idxI].length);
        
        changePosition(nodes, algorithm, problem, idxI, idxJ);
        
        s.setNodes(nodes);
        return s;
    }

    private static void changePosition(Node[][] nodes, Algorithm algorithm, Problem problem, int idxI, int idxJ) {
        UnidadTransporte vehicle = problem.getVehicles().get(0);
        double speed = vehicle.getTipoUnidadTransporte().getVelocidadPromedio();
        Node node = nodes[idxI][idxJ];
        double bestPayoff = -Double.MAX_VALUE;        
        int iRoute = -1, jRoute = -1;
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].length; j++) {
                double travelCost, toNode, fromNode;
                if (j > 0) {
                    travelCost = distance(nodes[i][j - 1].getIdx(), nodes[i][j].getIdx()) / speed;
                    toNode = distance(nodes[i][j - 1].getIdx(), node.getIdx()) / speed;
                } else {
                    travelCost = distance(Problem.getLastNode(), nodes[i][j].getIdx()) / speed;
                    toNode = distance(Problem.getLastNode(), node.getIdx()) / speed;
                }                
                fromNode = distance(node.getIdx(), nodes[i][j].getIdx()) / speed;
                double payoff = ObjectiveFunction.objectiveFunction(vehicle, nodes[i][j],
                        algorithm, 0, 0, 0, travelCost, 0)
                        - ObjectiveFunction.objectiveFunction(vehicle, node,
                        algorithm, 0, 0, 0, toNode, 0)
                        - ObjectiveFunction.objectiveFunction(vehicle, nodes[i][j],
                        algorithm, 0, 0, 0, fromNode, 0);
                if(payoff > bestPayoff && i!=idxI){
                    bestPayoff = payoff;
                    iRoute = i;
                    jRoute = j;
                }
            }            
        }
        
//        System.out.println("node1: " + node.getProduct().getId());
        //System.out.println("moving " + idxI + "/" + idxJ + " to " +iRoute + "/" + jRoute);
        
        if(iRoute==-1 || jRoute==-1) return;
        
        Node[] fromRoute = nodes[idxI];
        Node[] toRoute = nodes[iRoute];
        Node[] newFromRoute = new Node[fromRoute.length-1];
        Node[] newToRoute = new Node[toRoute.length+1];        
        System.arraycopy(fromRoute, 0, newFromRoute, 0, idxJ);
//        if(node==null) throw new AssertionError("there is a null node");
//        System.out.println("node2: " + node.getProduct().getId());
        for (int i = idxJ+1; i < fromRoute.length; i++) {
            newFromRoute[i-1] = fromRoute[i];            
        }
        System.arraycopy(toRoute, 0, newToRoute, 0, jRoute);
        newToRoute[jRoute] = node;
        for (int i = jRoute; i < toRoute.length; i++) {
            newToRoute[i+1] = toRoute[i];
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
