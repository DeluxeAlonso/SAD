/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.operators;

import algorithm.Algorithm;
import algorithm.Node;
import algorithm.Solution;
import entity.PedidoParcial;
import entity.UnidadTransporte;
import java.awt.geom.Point2D;
import java.util.HashMap;

/**
 *
 * @author robert
 */
public class ObjectiveFunction {
    public static double objectiveFunction(UnidadTransporte t, Node b, 
            Algorithm algorithm, int currentCap, double currentTime, 
            int extraCap, double travelCost, int remainingStock){
        
        double customerPriority = b.getDaysDifference()<=0 ? 
                algorithm.getMaxPriority() :
                Math.exp(-b.getDaysDifference()*Math.log(algorithm.getBasePriority()));
                
        int overCap = Math.max(0, currentCap + extraCap - 
                t.getTipoUnidadTransporte().getCapacidadPallets());
        
        double overTime = Math.max(0, currentTime + travelCost -
                algorithm.getMaxTravelTime());
        
        double overStock = Math.max(0, -remainingStock);
        
        return travelCost/customerPriority + 
                algorithm.getOvercapPenalty()*overCap +
                algorithm.getOvertimePenalty()*overTime + 
                algorithm.getOverstockPenalty()*overStock;
    }
    
    public static double getRouteCost(UnidadTransporte vehicle, Node[] route, 
            Algorithm algorithm, HashMap<Integer,Integer> currentStock){
        double routeCost = 0; int currentCap = 0; double currentTime = 0;
        for (int i = 1; i < route.length; i++) {
            double travelCost = Point2D.distance(route[i-1].getX(), route[i-1].getY(), 
                    route[i].getX(), route[i].getY())/
                    vehicle.getTipoUnidadTransporte().getVelocidadPromedio();
            int extraCap = route[i].getDemand();            
            int productId = route[i].getProduct().getId();
            int newStock = currentStock.get(productId)
                    - route[i].getDemand();      
            
            routeCost += objectiveFunction(vehicle, route[i], algorithm,
                    currentCap, currentTime, extraCap, travelCost, newStock);    
            
            currentCap += extraCap;            
            currentTime += travelCost;
            currentStock.put(productId, newStock);
        }
        //add cost between warehouse-node, node-warehouse
        return routeCost;
    }
    
    public static double getSolutionCost(Solution s, Algorithm algorithm, 
            HashMap<Integer,Integer> productsStock){
        HashMap<Integer,Integer> currentStock = new HashMap<> (productsStock);        
        Node[][] routes = s.getNodes();
        UnidadTransporte[] vehicles = s.getVehicles();
        double solutionCost = 0;
        for (int i = 0; i < routes.length; i++) {
            double routeCost = getRouteCost(vehicles[i], routes[i], algorithm,
                    currentStock);
            solutionCost += routeCost;            
        }        
        return solutionCost;
    }   
}
