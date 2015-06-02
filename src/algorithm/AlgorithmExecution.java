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
import entity.Cliente;
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
import java.util.Iterator;
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
    
    public AlgorithmReturnValues processOrders(Solution solution, Problem problem){
        ArrayList<UnidadTransporte> vehicles = problem.getVehicles();               
        
        ArrayList<PedidoParcial> orders = problem.getOrders();
        ArrayList<ArrayList<PedidoParcialXProducto>> partialOrdersXProducts = problem.getPartialOrdersXProducts();        
        
        Node[][] nodes = solution.getNodes();
        ArrayList<PedidoParcial> rejectedOrders = new ArrayList<>();
        ArrayList<PedidoParcial> acceptedOrders = new ArrayList<>();
        ArrayList<Despacho> despachos = new ArrayList<>();
        for (int i = 0; i < nodes.length; i++) {
            Despacho despacho = new Despacho();
            despacho.setEstado(1); // <-- set estado
            despacho.setFechaDespacho(new Date());
            despacho.setUnidadTransporte(vehicles.get(i));
            //HashSet<Cliente> customers = new HashSet<>();
            HashSet<GuiaRemision> guiasRemision = new HashSet<>();
            HashMap<Cliente,GuiaRemision> customers = new HashMap<>();
            HashMap<PedidoParcial,PedidoParcial> toNewOrder = new HashMap<>();
            HashMap<PedidoParcialXProducto,PedidoParcialXProducto> toNewOrderXProduct = new HashMap<>();
            for (int j = 0; j < nodes[i].length; j++) {
                PedidoParcialXProducto orderXProduct = nodes[i][j].getOrderXProduct();
                PedidoParcial partialOrder = nodes[i][j].getPartialOrder();
                Cliente customer = partialOrder.getPedido().getCliente();
                if(!customers.containsKey(customer)){                     
                    GuiaRemision guia = new GuiaRemision();
                    guia.setCliente(customer);
                    guia.setDespacho(despacho);
                    guia.setEstado(1); // <-- set estado
                    guia.setPedidoParcials(new HashSet<>());
                    guiasRemision.add(guia);
                    customers.put(customer, guia); //para no perder la referencia a la guia
                }
                HashSet<PedidoParcial> partialOrders = (HashSet<PedidoParcial>) 
                        customers.get(customer).getPedidoParcials();
                if(!toNewOrder.containsKey(partialOrder)){
                    PedidoParcial newPartialOrder = new PedidoParcial();
                    newPartialOrder.setEstado(EntityState.PartialOrders.ATENDIDO.ordinal());
                    newPartialOrder.setGuiaRemision(customers.get(customer));
                    newPartialOrder.setPedido(partialOrder.getPedido());
                    newPartialOrder.setPedidoParcialXProductos(new HashSet<>());
                    acceptedOrders.add(newPartialOrder);
                    partialOrders.add(newPartialOrder);                    
                    toNewOrder.put(partialOrder,newPartialOrder); //para no perder la referencia al nuevo pedido parcial
                }
                PedidoParcial newPartialOrder = toNewOrder.get(partialOrder); //aqui se anhadiran los productos
                HashSet<PedidoParcialXProducto> orderXProducts = (HashSet<PedidoParcialXProducto>) 
                        newPartialOrder.getPedidoParcialXProductos();
                if(!toNewOrderXProduct.containsKey(orderXProduct)){
                    PedidoParcialXProducto newOrderXProduct = new PedidoParcialXProducto();
                    newOrderXProduct.setPedidoParcial(newPartialOrder);
                    newOrderXProduct.setProducto(nodes[i][j].getProduct());
                    newOrderXProduct.setCantidad(0);
                    orderXProducts.add(newOrderXProduct);
                    toNewOrderXProduct.put(orderXProduct, newOrderXProduct);
                }
                PedidoParcialXProducto newOrderXProduct = toNewOrderXProduct.get(orderXProduct);
                int newDemand = newOrderXProduct.getCantidad() +
                        nodes[i][j].getDemand();
                newOrderXProduct.setCantidad(newDemand);
            }
            despacho.setGuiaRemisions(guiasRemision);
            despachos.add(despacho);
        }
        
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
        /*for (int i = 0; i < orders.size(); i++) {
            PedidoParcial pedido = new PedidoParcial();
            pedido.setPedido(orders.get(i).getPedido());
            pedido.setEstado(EntityState.PartialOrders.ATENDIDO.ordinal());              
            acceptedOrders.add(pedido);
        }*/
        
        HashMap<PedidoParcial, PedidoParcial> toPedidoParcial = new HashMap<>();
        for (int i = 0; i < orders.size(); i++) {
            PedidoParcial pedido = new PedidoParcial();
            pedido.setPedido(orders.get(i).getPedido());
            pedido.setEstado(EntityState.PartialOrders.NO_ATENDIDO.ordinal());              
            pedido.setPedidoParcialXProductos(new HashSet<>());
            rejectedOrders.add(pedido);
            toPedidoParcial.put(orders.get(i), pedido);
        }        
        
        //ahora se crea un par de estructuras mas que almacenaran
        //el detalle de los pedidos parciales que se crearon arriba
        //ArrayList<ArrayList<PedidoParcialXProducto>> acceptedOrdersXProd = new ArrayList<>();
        //ArrayList<ArrayList<PedidoParcialXProducto>> rejectedOrdersXProd = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            //ArrayList<PedidoParcialXProducto> acceptedOrdersXProdOfOrder = new ArrayList<>();
            HashSet<PedidoParcialXProducto> rejectedOrdersXProdOfOrder = 
                    (HashSet<PedidoParcialXProducto>) toPedidoParcial.get(orders.get(i)).getPedidoParcialXProductos();
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
                    /*row.setCantidad(totalDemand);
                    row.setPedidoParcial(acceptedOrders.get(i));
                    acceptedOrdersXProdOfOrder.add(row);*/
                }
                else{ //atendida parcialmente
                    /*PedidoParcialXProducto row2 = new PedidoParcialXProducto();//atendida
                    row2.setProducto(entry.getKey());
                    row2.setCantidad(servedDemand);
                    row2.setPedidoParcial(acceptedOrders.get(i));
                    acceptedOrdersXProdOfOrder.add(row2);*/
                    
                    row.setCantidad(totalDemand-servedDemand);//no atendida
                    row.setPedidoParcial(rejectedOrders.get(i));
                    rejectedOrdersXProdOfOrder.add(row);
                }
            }                       
            //acceptedOrdersXProd.add(acceptedOrdersXProdOfOrder);
            //rejectedOrdersXProd.add(rejectedOrdersXProdOfOrder);
        }
        
        Iterator<PedidoParcial> it = rejectedOrders.iterator();
        while(it.hasNext()){
            PedidoParcial rejectedOrder = it.next();
            HashSet<PedidoParcialXProducto> rejectedOrdersXProdOfOrder
                    = (HashSet<PedidoParcialXProducto>) rejectedOrder.getPedidoParcialXProductos();
            if (rejectedOrdersXProdOfOrder.isEmpty()) {
                it.remove();
            }
        }
        
        
        return new AlgorithmReturnValues(despachos, acceptedOrders, rejectedOrders);
    }
    
    public void assignRemissionGuides(ArrayList<Despacho> deliveries){
        ArrayList<PedidoParcial> acceptedOrders = new ArrayList<>();
        ArrayList<GuiaRemision> remissionGuides = new ArrayList<>();
        for(int i=0;i<deliveries.size();i++)
            for (Iterator<GuiaRemision> remissionGuide = deliveries.get(i).getGuiaRemisions().iterator(); remissionGuide.hasNext(); ) {
                GuiaRemision g = remissionGuide.next();
                for(Iterator<PedidoParcial> partialOrder = g.getPedidoParcials().iterator(); partialOrder.hasNext();){
                    PedidoParcial p = partialOrder.next();
                    acceptedOrders.add(p);
                }
                remissionGuides.add(g);
            }
        orderApplication.CreateRemissionGuides(acceptedOrders, remissionGuides); 
    }
    
    public void createPartialOrders(ArrayList<PedidoParcial>acceptedOrders, ArrayList<PedidoParcial>rejectedOrders){
        ArrayList<PedidoParcialXProducto> acceptedOrdersXProd = getPartialOrderDetail(acceptedOrders);
        ArrayList<PedidoParcialXProducto> rejectedOrdersXProd = getPartialOrderDetail(rejectedOrders);
        
        orderApplication.createPartialOrders(acceptedOrders, acceptedOrdersXProd, rejectedOrders, rejectedOrdersXProd);
    }
    
    public ArrayList<PedidoParcialXProducto> getPartialOrderDetail(ArrayList<PedidoParcial> orders){
        ArrayList<PedidoParcialXProducto> orderDetails = new ArrayList<>();
        for(int i=0;i<orders.size();i++)
            for(Iterator<PedidoParcialXProducto> partialOrderDetail = orders.get(i).getPedidoParcialXProductos().iterator(); partialOrderDetail.hasNext();){
                    PedidoParcialXProducto p = partialOrderDetail.next();
                    orderDetails.add(p);
            }
        return orderDetails;
    }
    
}
