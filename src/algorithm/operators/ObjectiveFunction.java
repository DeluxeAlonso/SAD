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
import entity.UnidadTransporte;
import java.util.ArrayList;
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
        
        /**if(overCap>0) System.out.println("overcap " + overCap);
        if(overTime>0) System.out.println("overtime " + overTime);
        if(overStock>0) System.out.println("overstock " + overStock);*/
        
        customerPriority = 1;
        
        return travelCost/customerPriority + 
                algorithm.getOvercapPenalty()*overCap +
                algorithm.getOvertimePenalty()*overTime + 
                algorithm.getOverstockPenalty()*overStock;
    }
    
    public static double getRouteCost(UnidadTransporte vehicle, Node[] route, 
            Algorithm algorithm, HashMap<Integer,Integer> currentStock){
        double routeCost = 0; int currentCap = 0; double currentTime = 0;
        if(route==null) return routeCost;  
        if(route.length==0) return routeCost;
        double speed = vehicle.getTipoUnidadTransporte().getVelocidadPromedio();
        for (int i = 0; i < route.length+1; i++) {
            double travelCost = 0; int extraCap = 0, productId = 0, newStock = 0;
            
            if(i>0 && i==route.length)
                travelCost = distance(route[i-1].getX(), route[i-1].getY(), 
                    Constants.WAREHOUSE_LONGITUDE, Constants.WAREHOUSE_LATITUDE)/
                    speed;
            else if(i>0)
                travelCost = distance(route[i-1].getX(), route[i-1].getY(), 
                    route[i].getX(), route[i].getY())/
                    speed;
            else if(i<route.length)
                travelCost = distance(Constants.WAREHOUSE_LONGITUDE, Constants.WAREHOUSE_LATITUDE,
                    route[i].getX(), route[i].getY())/
                    speed;            
            
            System.out.println("travelCost: " + travelCost + 
                    "  distance: " + travelCost*speed + " speed: " + speed);
            
            if(i < route.length && currentStock!=null) {                
                extraCap = route[i].getDemand();
                productId = route[i].getProduct().getId();
                newStock = currentStock.get(productId)
                        - route[i].getDemand();
                
                //System.out.println("productId: " + productId + "  currentStock: " + 
                //        currentStock.get(productId) + "  demand: " + route[i].getDemand());
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
    
    public static double getSolutionCost(Solution s, Algorithm algorithm, Problem problem,
            HashMap<Integer,Integer> productsStock){
        HashMap<Integer,Integer> currentStock = new HashMap<> (productsStock);        
        Node[][] routes = s.getNodes();
        ArrayList<UnidadTransporte> vehicles = problem.getVehicles();
        double solutionCost = 0;
        for (int i = 0; i < routes.length; i++) {
            double routeCost = getRouteCost(vehicles.get(i), routes[i], algorithm,
                    currentStock);
            solutionCost += routeCost; 
            System.out.println("costo de ruta: " + routeCost);
        }        
        return solutionCost;
    }   
    
    
    public static double distance(double long1, double lat1, double long2, double lat2){
        long1 = Math.PI/180*long1;
        long2 = Math.PI/180*long2;
        lat1 = Math.PI/180*lat1;
        lat2 = Math.PI/180*lat2;
        
        double diflong = (long2 - long1);
        double diflat = (lat2 - lat1);
        double sinlat = Math.sin(diflat/2);
        double sinlong = Math.sin(diflong/2);
        double a = sinlat*sinlat + Math.cos(lat1)*Math.cos(lat2)*
                   sinlong*sinlong;
        double b = 2*6378.0*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        //double b = 2*6378*Math.asin(Math.min(1,Math.sqrt(a)));    
        
        //if(b > 30)
        //    System.out.println(long1 + "/" + lat1 + " " + long2 + "/" + lat2 + " " + b);
        
        return b;
    }

}
