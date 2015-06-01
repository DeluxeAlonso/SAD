/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import algorithm.operators.Selection;
import algorithm.operators.Crossover;
import algorithm.operators.LocalSearch;
import algorithm.operators.Mutation;
import algorithm.operators.ObjectiveFunction;
import algorithm.operators.Repair;
import application.order.OrderApplication;
import entity.Despacho;
import entity.GuiaRemision;
import entity.PedidoParcial;
import entity.PedidoParcialXProducto;
import entity.Producto;
import entity.UnidadTransporte;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import util.EntityState;

/**
 *
 * @author robert
 */
public class AlgorithmExecution {
    OrderApplication orderApplication = new OrderApplication();
    
    public void start(){
        long ini = System.currentTimeMillis();
        
        Algorithm algorithm = new Algorithm();
        algorithm.setPopulationSize(50000);
        algorithm.setTournamentSelectionKValue(50);
        algorithm.setOvercapPenalty(10000);
        algorithm.setOvertimePenalty(10000);
        algorithm.setOverstockPenalty(10000);
        algorithm.setMutationRate(0.5f);
        algorithm.setMaxPriority(10000);
        algorithm.setBasePriority(1.2);
        algorithm.setMaxTravelTime(3);
        algorithm.setGraspAlpha(0.3);
        
        Problem problem = new Problem();
        
        Population population = new Population(algorithm, problem);
        
        for (int i = 0; i < algorithm.getNumberOfGenerations(); i++) {
            Solution[] parents = new Solution[2];
            parents[0] = population.getSolutions()[Selection.tournamentSelection(
                        algorithm.getTournamentSelectionKValue(), population, 
                        Selection.Options.BEST)];
            parents[1] = population.getSolutions()[Selection.tournamentSelection(
                        algorithm.getTournamentSelectionKValue(), population, 
                        Selection.Options.BEST)];
            Arrays.sort(parents);
            
            Solution child = Crossover.uniformCrossover(parents, algorithm, problem);
            
            child = Mutation.mutation(child, algorithm, problem);            
            child = LocalSearch.opt2Improvement(child, algorithm, problem);  
            
            double cost = ObjectiveFunction.getSolutionCost(child, algorithm,
                problem.getProductsStock());
            if(cost>algorithm.getOvercapPenalty() || cost>algorithm.getOvertimePenalty() ||
                    cost>algorithm.getOverstockPenalty()){
                child = Repair.repair(child, algorithm);
                child.setCost(ObjectiveFunction.getSolutionCost(child, algorithm,
                        problem.getProductsStock()));
            }
            else
                child.setCost(cost);
            
            int replacedSolution = Selection.tournamentSelection(
                    algorithm.getTournamentSelectionKValue(), population, 
                    Selection.Options.WORST);
            
            population.getSolutions()[replacedSolution] = child;
        }
        long end = System.currentTimeMillis();
        System.out.println("Execution time: " + (end-ini) + "ms");
        
        Solution bestSolution = population.getBestSolution();
                
        //Here we can show the solution and the user can decide to run again the algorithm
        AlgorithmView window = new AlgorithmView(bestSolution);
        window.setBounds(0, 0, 700, 700);
        window.setVisible(true);
        
        processOrders(bestSolution, problem);
        
    }
    
    public void processOrders(Solution solution, Problem problem){
        ArrayList<UnidadTransporte> vehicles = problem.getVehicles();        
        Node[][] nodes = solution.getNodes();
        
        ArrayList<PedidoParcial> orders = problem.getOrders();
        ArrayList<ArrayList<PedidoParcialXProducto>> partialOrdersXProducts = problem.getPartialOrdersXProducts();
        
        //Primero se crea un par de hashmaps para almacenar la demanda de cada pedido parcial x producto
        //demand y demand2 tendra la demanda total y original del pedido parcial x producto        
        HashMap<PedidoParcial,HashMap<Producto,Integer>> demand = new HashMap<>();
        HashMap<PedidoParcial,HashMap<Producto,Integer>> demand2 = new HashMap<>();
        for (int i = 0; i < orders.size(); i++) {                        
            for (int j = 0; j < partialOrdersXProducts.get(i).size(); j++) {
                PedidoParcialXProducto pedParXProd = partialOrdersXProducts.get(i).get(j);
                Producto product = pedParXProd.getProducto();
                HashMap<Producto,Integer> hm = demand.get(orders.get(i));
                if(hm==null)
                    hm = new HashMap<>();
                hm.put(product, pedParXProd.getCantidad());
                demand.put(orders.get(i),hm);
                demand2.put(orders.get(i),hm);
            }
        }
        
        //ahora, a demand se le va disminuyendo la demanda debido a la solucion del algoritmo:
        //si hay locales que son atendidos por el algoritmo, se reduce el valor de la demanda original
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].length; j++) {
                HashMap<Producto,Integer> hm = demand.get(nodes[i][j].getPartialOrder());
                int curDemand = hm.get(nodes[i][j].getProduct());
                hm.put(nodes[i][j].getProduct(), curDemand-nodes[i][j].getDemand());
                demand.put(nodes[i][j].getPartialOrder(), hm);
            }
        }
        
        //se crea un par de estructuras que representaran los pedidos parciales que se aceptan 
        //por el algoritmo y las ordenes que se rechazan
        //aqui es donde se dividen los pedidos parciales en mas pedidos parciales
        ArrayList<PedidoParcial> acceptedOrders = new ArrayList<>();
        ArrayList<PedidoParcial> rejectedOrders = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            PedidoParcial pedido = new PedidoParcial();
            pedido.setPedido(orders.get(i).getPedido());
            pedido.setEstado(EntityState.PartialOrders.ATENDIDO.ordinal());              
            acceptedOrders.add(pedido);
        }
        for (int i = 0; i < orders.size(); i++) {
            PedidoParcial pedido = new PedidoParcial();
            pedido.setPedido(orders.get(i).getPedido());
            pedido.setEstado(EntityState.PartialOrders.NO_ATENDIDO.ordinal());              
            rejectedOrders.add(pedido);
        }        
        
        //ahora se crea un par de estructuras mas que almacenaran
        //el detalle de los pedidos parciales que se crearon arriba
        ArrayList<ArrayList<PedidoParcialXProducto>> acceptedOrdersXProd = new ArrayList<>();
        ArrayList<ArrayList<PedidoParcialXProducto>> rejectedOrdersXProd = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            ArrayList<PedidoParcialXProducto> acceptedOrdersXProdOfOrder = new ArrayList<>();
            ArrayList<PedidoParcialXProducto> rejectedOrdersXProdOfOrder = new ArrayList<>();
            HashMap<Producto,Integer> hm = demand.get(orders.get(i));
            HashMap<Producto,Integer> hm2 = demand2.get(orders.get(i));
            for (Map.Entry<Producto, Integer> entry : hm.entrySet()) {
                PedidoParcialXProducto row = new PedidoParcialXProducto();
                row.setProducto(entry.getKey());
                int totalDemand = hm2.get(entry.getKey());
                int servedDemand = totalDemand - entry.getValue();
                if(servedDemand==0){ //no atendida
                    row.setCantidad(totalDemand);
                    row.setPedidoParcial(rejectedOrders.get(i));
                    rejectedOrdersXProdOfOrder.add(row);
                }
                else if(servedDemand==totalDemand){ //atendida totalmente
                    row.setCantidad(totalDemand);
                    row.setPedidoParcial(acceptedOrders.get(i));
                    acceptedOrdersXProdOfOrder.add(row);
                }
                else{ //atendida parcialmente
                    PedidoParcialXProducto row2 = new PedidoParcialXProducto();//atendida
                    row2.setProducto(entry.getKey());
                    row2.setCantidad(servedDemand);
                    row2.setPedidoParcial(acceptedOrders.get(i));
                    acceptedOrdersXProdOfOrder.add(row2);
                    
                    row.setCantidad(totalDemand-servedDemand);//no atendida
                    row.setPedidoParcial(rejectedOrders.get(i));
                    rejectedOrdersXProdOfOrder.add(row);
                }
            }
            acceptedOrdersXProd.add(acceptedOrdersXProdOfOrder);
            rejectedOrdersXProd.add(rejectedOrdersXProdOfOrder);
        }
        
        
        //asignar guias de remision a las ordenes atendidas
        assignRemissionGuides(acceptedOrders);
        createPartialOrders(acceptedOrders, acceptedOrdersXProd,rejectedOrders,rejectedOrdersXProd);
    }
    
    public void assignRemissionGuides(ArrayList<PedidoParcial> acceptedOrders){
        ArrayList<GuiaRemision> remissionGuides = new ArrayList<>();
        for(int i=0;i<acceptedOrders.size();i++){
            GuiaRemision remissionGuide = new GuiaRemision();
            remissionGuide.setCliente(acceptedOrders.get(i).getPedido().getCliente());
            remissionGuide.setDespacho(null);
            remissionGuide.setEstado(0);
            acceptedOrders.get(i).setGuiaRemision(remissionGuide);
            remissionGuides.add(remissionGuide);
        }
        orderApplication.CreateRemissionGuides(acceptedOrders, remissionGuides);  
    }
    
    public void createPartialOrders(ArrayList<PedidoParcial>acceptedOrders, 
            ArrayList<ArrayList<PedidoParcialXProducto>>acceptedOrdersXProd,
            ArrayList<PedidoParcial>rejectedOrders,ArrayList<ArrayList<PedidoParcialXProducto>>rejectedOrdersXProd){
        orderApplication.createPartialOrders(acceptedOrders, acceptedOrdersXProd, rejectedOrders, rejectedOrdersXProd);
    }
    
}
