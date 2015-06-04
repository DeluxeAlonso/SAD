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
import util.Constants;

/**
 *
 * @author robert
 */
public class LocalSearch {
    public static Solution opt2Improvement(Solution child, Algorithm algorithm,
            Problem problem) {
        Node[][] nodes = child.getNodes();
        for (Node[] node : nodes) {
            Node[] route = new Node[node.length + 1];
            Node warehouse = new Node();
            warehouse.setX(Constants.WAREHOUSE_LONGITUDE);
            warehouse.setY(Constants.WAREHOUSE_LATITUDE);
            warehouse.setDaysDifference(0);
            route[0] = warehouse;
            for (int j = 1; j < route.length; j++) {
                route[j] = node[j-1];                
            }
            steepestImprovement(route, algorithm, problem);
            for (int j = 1; j < route.length; j++) {
                node[j-1] = route[j];                
            }
        }
        child.setNodes(nodes);
        return child;
    }
    
    private static void steepestImprovement(Node[] ruta, Algorithm algorithm,
            Problem problem) {
        UnidadTransporte vehicle = problem.getVehicles().get(0);
        double mejorAhorro = 1;
        int len = ruta.length;
        int bestIdx1 = 0, bestIdx4 = 2;
        int numMaxIt = len * len, k = 0;
        while (mejorAhorro > 0 && k < numMaxIt) {
            mejorAhorro = 0;
            k++;
            for (int idx1 = 0; idx1 < len; idx1++) {
                for (int idx4 = idx1 + 1; idx4 < len; idx4++) { //importante: el costo de ida = costo de vuelta
                    int idx2 = (idx1 + 1) % len, idx3 = (idx4 + 1) % len;
                    if (idx4 != idx1 && idx4 != idx2 && idx3 != idx1) {
                        Node t1 = ruta[idx1];
                        Node t2 = ruta[idx2];
                        Node t3 = ruta[idx3];
                        Node t4 = ruta[idx4];
                        //Calcular el ahorro si es que se intercambiaran las aristas
                        double ahorro = getCost(t1, t2, algorithm) + getCost(t4, t3, algorithm) 
                                - getCost(t2, t3, algorithm) - getCost(t1, t4, algorithm);
                        if (ahorro > mejorAhorro) {
                            mejorAhorro = ahorro;
                            bestIdx1 = idx1;
                            bestIdx4 = idx4;
                        }
                    }
                }
            }
            if (mejorAhorro > 0) {
                reorder(ruta, bestIdx1, bestIdx4);
            }
        }
        //System.out.println("k " + k);
    }

    private static double getCost(Node a, Node b, Algorithm algorithm){
        double customerPriority = b.getDaysDifference()<=0 ? 
                algorithm.getMaxPriority() :
                Math.exp(-b.getDaysDifference()*Math.log(algorithm.getBasePriority()));
        double travelCost = distance(a.getX(), a.getY(),
                            b.getX(), b.getY());
        return travelCost/customerPriority;
    }
    
    private static void reorder(Node[] ruta, int idx1, int idx4) {        
        Node[] rutaAux = new Node[ruta.length];
        //Copiado auxiliar
        System.arraycopy(ruta, 0, rutaAux, 0, ruta.length);
        //Reordenamiento
        rutaAux[idx1 + 1] = ruta[idx4];
        rutaAux[idx4] = ruta[idx1 + 1];
        for (int i = idx1 + 2, j = idx4 - 1; i < idx4; i++, j--) {
            rutaAux[i] = ruta[j];
        }
        //Copiado final
        System.arraycopy(rutaAux, 0, ruta, 0, ruta.length);
    }
}
