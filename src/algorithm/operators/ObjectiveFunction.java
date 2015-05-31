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
import util.Constants;

/**
 *
 * @author robert
 */
public class ObjectiveFunction {
    public static double objectiveFunction(UnidadTransporte t, Node b, 
            Algorithm algorithm, int currentCap, double currentTime, 
            int extraCap, double travelCost, int remainingStock){
        
        double customerPriority;        
        if(b!=null)
            customerPriority = b.getDaysDifference()<=0 ? 
                algorithm.getMaxPriority() :
                Math.exp(-b.getDaysDifference()*Math.log(algorithm.getBasePriority()));
        else customerPriority = 1;
                
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
        if(route==null) return routeCost;  
        double speed = vehicle.getTipoUnidadTransporte().getVelocidadPromedio();
        for (int i = 0; i < route.length+1; i++) {
            double travelCost; int extraCap = 0, productId = 0, newStock = 0;
            
            if(i==route.length)
                travelCost = Point2D.distance(route[i-1].getX(), route[i-1].getY(), 
                    Constants.WAREHOUSE_LATITUDE, Constants.WAREHOUSE_LONGITUDE)/
                    speed;
            else if(i>0)
                travelCost = Point2D.distance(route[i-1].getX(), route[i-1].getY(), 
                    route[i].getX(), route[i].getY())/
                    speed;
            else
                travelCost = Point2D.distance(Constants.WAREHOUSE_LATITUDE, Constants.WAREHOUSE_LONGITUDE,
                    route[i].getX(), route[i].getY())/
                    speed;            
            
            if(i < route.length) {                
                extraCap = route[i].getDemand();
                productId = route[i].getProduct().getId();
                newStock = currentStock.get(productId)
                        - route[i].getDemand();
                routeCost += objectiveFunction(vehicle, route[i], algorithm,
                        currentCap, currentTime, extraCap, travelCost, newStock);
                currentCap += extraCap;                
                currentStock.put(productId, newStock);
            } 
            else{
                routeCost += objectiveFunction(vehicle, null, algorithm,
                        currentCap, currentTime, extraCap, travelCost, newStock);
            }
            currentTime += travelCost;
        }
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
