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
import client.delivery.DeliveryView;
import client.delivery.Informable;
import entity.Cliente;
import entity.Despacho;
import entity.GuiaRemision;
import entity.PedidoParcial;
import entity.PedidoParcialXProducto;
import entity.Producto;
import entity.UnidadTransporte;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.SwingWorker;
import util.EntityState;

/**
 *
 * @author robert
 */
public class AlgorithmExecution extends SwingWorker<Solution, String>{
    public static Problem problem;
    public static boolean bad = false;
    public static boolean overCap = false;
    public static boolean overTime = false;
    public static boolean overStock = false;    
    private double maxTravelTime;
    private ArrayList<PedidoParcial> partialOrders;
    private Informable informable;
    
    static boolean checkSolution(Solution solution) {
        Node[][] routes = solution.getNodes();
        boolean[] visited = new boolean[problem.getNodes().size()];        
        for (int i = 0; i < routes.length; i++) {
            for (int j = 0; j < routes[i].length; j++) {
                if(visited[routes[i][j].getIdx()])
                    return false;
                visited[routes[i][j].getIdx()] = true; 
            }            
        }
        return true;
    }
    public DeliveryView view = null;
    
    public AlgorithmExecution() {        
    }
    
    public AlgorithmExecution(DeliveryView view, double maxTravelTime, 
            ArrayList<PedidoParcial>partialOrders, Informable informable) {
        this.maxTravelTime = maxTravelTime;
        this.partialOrders = partialOrders;
        this.informable = informable;
        this.view = view;
    }
    
    public Solution start(double maxTravelTime, ArrayList<PedidoParcial>partialOrders){        
        
        long ini = System.currentTimeMillis();
        
        String cad = "Inicializando parámetros...";
        view.getTxtResult().setText(cad);
        
        Algorithm algorithm = new Algorithm();
        algorithm.setNumberOfGenerations(50000);
        algorithm.setPopulationSize(50000);
        algorithm.setTournamentSelectionKValue(50);
        algorithm.setOvercapPenalty(100000);
        algorithm.setOvertimePenalty(100000);
        algorithm.setOverstockPenalty(100000);
        algorithm.setMutationRate(0.5f);
        algorithm.setMaxPriority(100);
        algorithm.setBasePriority(1.09f);
        algorithm.setMaxTravelTime(maxTravelTime); 
        algorithm.setGraspAlpha(0.3f);
        
        String cad2 = "\nIniciando primera fase del algoritmo...";
        view.getTxtResult().setText(cad+cad2);
        
        problem = new Problem(partialOrders);
        
        Population population = new Population(algorithm, problem);
        System.out.println("Se creo la población");
        
        Solution bestSolution;
        
        long popTime = System.currentTimeMillis() - ini;
        ini = System.currentTimeMillis();
        
        overCap = overTime = overStock = bad = false;
        
        String cad3 = "\nIniciando segunda fase del algoritmo...";
        view.getTxtResult().setText(cad+cad2+cad3);
        
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

            double cost = ObjectiveFunction.getSolutionCost(child, algorithm, problem,
                problem.getProductsStock());
            if(bad){
                Solution sol = Repair.repair(child, algorithm, problem);
                if(sol!=null){
                    child = sol;
                    child.setCost(ObjectiveFunction.getSolutionCost(child, algorithm, problem, 
                        problem.getProductsStock()));
                }
            }
            else
                child.setCost(cost);  
            
            overCap = overTime = overStock = bad = false;
            
            int replacedSolution = Selection.tournamentSelection(
                    algorithm.getTournamentSelectionKValue(), population, 
                    Selection.Options.WORST);
            
            /*bestSolution = population.getBestSolution();
            System.out.println("Generación: " + i + " child: " + child.getCost() + 
                    " best: " + bestSolution.getCost() + " worst " + population.getSolutions()[replacedSolution].getCost());
            */
            population.getSolutions()[replacedSolution] = child;
        }
        long end = System.currentTimeMillis();
        System.out.println("Grasp algorithm time: " + popTime + "ms");
        System.out.println("Genetic algorithm time: " + (end-ini) + "ms");
        
        bestSolution = population.getBestSolution();
        
        System.out.println("bestSolution cost: " + bestSolution.getCost());
        System.out.println("");
        //System.out.println(displayRoutes(bestSolution));
        //System.out.println("");
        System.out.println(displayDemand(bestSolution));

        double theTime = (popTime+(end-ini));
        theTime /= 1000;
         
        String cad4 = "\nEjecución finalizada...";
        String cad5 = "\nTiempo de ejecución: " + String.format( "%.2f", theTime ) + " s";
        view.getTxtResult().setText(cad+cad2+cad3+cad4+cad5+"\n");
        
        bestSolution = orderByNodes(bestSolution);
        
        return bestSolution;  
    }
    
    public AlgorithmReturnValues processOrders(Solution solution){
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
            
            if (rejectedOrdersXProdOfOrder.isEmpty()) it.remove();
            else{
                Iterator<PedidoParcialXProducto> it2 = rejectedOrdersXProdOfOrder.iterator();
                while(it2.hasNext()){
                    PedidoParcialXProducto pedXProd = it2.next();
                    if(pedXProd.getCantidad()==0) it2.remove();                    
                }
                if (rejectedOrdersXProdOfOrder.isEmpty()) it.remove();
            }
        }
        
        displayStructures(despachos, acceptedOrders, rejectedOrders);
        
        return new AlgorithmReturnValues(despachos, acceptedOrders, rejectedOrders);
    }

    public StringBuffer displayRoutes(Solution bestSolution) {
        StringBuffer buf = new StringBuffer();
        Node[][]nodes = bestSolution.getNodes();
        for (int i = 0; i < nodes.length; i++) {            
            buf.append("Ruta ").append(i).append("\n") ;           
            for (int j = 0; j < nodes[i].length; j++) {
                buf.append(nodes[i][j].getX()).append("/").append(nodes[i][j].getY()).
                        append("  ");                
            }
            buf.append("\n");
        }
        return buf;
    }
    
    public StringBuffer displayDemand(Solution bestSolution) {
        StringBuffer buf = new StringBuffer();
        Node[][]nodes = bestSolution.getNodes();
        for (int i = 0; i < nodes.length; i++) {  
            buf.append("Ruta ").append(i).append("\n") ;           
            int cap = 0; double time = 0;
            for (int j = 0; j < nodes[i].length; j++) {
                try{
                buf.append(nodes[i][j].getProduct().getId()).append("/").append(nodes[i][j].getDemand()).
                        append("/").append(nodes[i][j].getPartialOrder().getPedido().getId()).
                        append("/").append(nodes[i][j].getIdx()).append("  ");
                cap += nodes[i][j].getDemand();
                if(j>0) time += ObjectiveFunction.distance(nodes[i][j-1].getIdx(), nodes[i][j].getIdx());
                }catch(Exception ex){
                    System.out.println("exception: " + i + " " + j);
                }
            }
            if(nodes[i].length>0) {
                time += ObjectiveFunction.distance(Problem.getLastNode(), nodes[i][0].getIdx());
                time += ObjectiveFunction.distance(nodes[i][nodes[i].length-1].getIdx(), Problem.getLastNode());
            }
            time /= problem.getVehicles().get(0).getTipoUnidadTransporte().getVelocidadPromedio();
            buf.append(cap + " " + time + "\n");
        }
        return buf;
    }
    
    public StringBuffer displayOrders(Solution bestSolution) {
        StringBuffer buf = new StringBuffer();
        Node[][]nodes = bestSolution.getNodes();
        for (int i = 0; i < nodes.length; i++) {            
            buf.append("Ruta ").append(i).append("\n") ;           
            for (int j = 0; j < nodes[i].length; j++) {
                buf.append(nodes[i][j].getPartialOrder().getPedido().getId()).
                        append("  ");                
            }
            buf.append("\n");
        }
        return buf;
    }

    public void displayStructures(ArrayList<Despacho> despachos, ArrayList<PedidoParcial> acceptedOrders, ArrayList<PedidoParcial> rejectedOrders) {
        
        System.out.println("Despachos");
        for(Despacho despacho: despachos){
            System.out.println("  Despacho No: " + despacho.getId() + " vehiculo: " + despacho.getUnidadTransporte().getId());
            for (Iterator it = despacho.getGuiaRemisions().iterator(); it.hasNext();) {
                GuiaRemision guia = (GuiaRemision) it.next();
                System.out.println("    Guia No: " + guia.getId() + " cliente: " + guia.getCliente().getId());
                for (Iterator it2 = guia.getPedidoParcials().iterator(); it2.hasNext();) {
                    PedidoParcial pedidoParcial = (PedidoParcial) it2.next();
                    System.out.println("      Pedido No: " + pedidoParcial.getPedido().getId());
                    for (Iterator it3 = pedidoParcial.getPedidoParcialXProductos().iterator(); it3.hasNext();) {
                        PedidoParcialXProducto pedXProd = (PedidoParcialXProducto) it3.next();
                        System.out.println("        Producto No: " + pedXProd.getProducto().getId() + " cantidad: " + pedXProd.getCantidad());
                    }
                }
            }
        }
        
        System.out.println("");
        System.out.println("Ordenes aceptadas");
        for(PedidoParcial pedidoParcial: acceptedOrders){
            System.out.println("      Pedido No: " + pedidoParcial.getPedido().getId());
            for (Iterator it3 = pedidoParcial.getPedidoParcialXProductos().iterator(); it3.hasNext();) {
                PedidoParcialXProducto pedXProd = (PedidoParcialXProducto) it3.next();
                System.out.println("        Producto No: " + pedXProd.getProducto().getId() + " cantidad: " + pedXProd.getCantidad());
            }
        }
              
        System.out.println("");
        System.out.println("Ordenes rechazadas");
        for(PedidoParcial pedidoParcial: rejectedOrders){
            System.out.println("      Pedido No: " + pedidoParcial.getPedido().getId());
            for (Iterator it3 = pedidoParcial.getPedidoParcialXProductos().iterator(); it3.hasNext();) {
                PedidoParcialXProducto pedXProd = (PedidoParcialXProducto) it3.next();
                System.out.println("        Producto No: " + pedXProd.getProducto().getId() + " cantidad: " + pedXProd.getCantidad());
            }
        }
        
        
    }

    private Solution orderByNodes(Solution solution) {
        Node[][] nodes = solution.getNodes();
        
        Arrays.sort(nodes, new Comparator<Node[]>(){
            public int compare(Node[] a, Node[] b){
                return (b.length - a.length);
            }
        });
        
        solution.setNodes(nodes);
        return solution;
    }

    @Override
    protected Solution doInBackground() throws Exception {
        long ini = System.currentTimeMillis();

        publish("Inicializando parámetros...");       

        Algorithm algorithm = new Algorithm();
        algorithm.setNumberOfGenerations(50000);
        algorithm.setPopulationSize(50000);
        algorithm.setTournamentSelectionKValue(50);
        algorithm.setOvercapPenalty(100000);
        algorithm.setOvertimePenalty(100000);
        algorithm.setOverstockPenalty(100000);
        algorithm.setMutationRate(0.5f);
        algorithm.setMaxPriority(100);
        algorithm.setBasePriority(1.09f);
        algorithm.setMaxTravelTime(maxTravelTime);
        algorithm.setGraspAlpha(0.3f);

        publish("Iniciando primera fase de la generación de rutas...");

        problem = new Problem(partialOrders);

        Population population = new Population();
        population.setProblem(problem);
        population.setAlgorithm(algorithm);
        Solution[] solutions = new Solution[algorithm.getPopulationSize()];
        int n1 = solutions.length;
        for (int i = 0; i < n1; i++) {            
            solutions[i] = new Solution(algorithm, problem, i); 
            solutions[i] = LocalSearch.opt2Improvement(solutions[i], algorithm, problem);
            solutions[i].setCost(ObjectiveFunction.getSolutionCost(solutions[i], 
                    algorithm, problem, problem.getProductsStock()));  
            if(n1%100==0) setProgress((i+1) * 50 / n1);
        }        
        population.setSolutions(solutions);
        
        System.out.println("Se creo la población");

        Solution bestSolution;

        long popTime = System.currentTimeMillis() - ini;
        ini = System.currentTimeMillis();

        overCap = overTime = overStock = bad = false;

        publish("Iniciando segunda fase de la generación de rutas...");
        
        int n = algorithm.getNumberOfGenerations();
        for (int i = 0; i < n; i++) {
            
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

            double cost = ObjectiveFunction.getSolutionCost(child, algorithm, problem,
                    problem.getProductsStock());
            if (bad) {
                Solution sol = Repair.repair(child, algorithm, problem);
                if (sol != null) {
                    child = sol;
                    child.setCost(ObjectiveFunction.getSolutionCost(child, algorithm, problem,
                            problem.getProductsStock()));
                }
            } else {
                child.setCost(cost);
            }

            overCap = overTime = overStock = bad = false;

            int replacedSolution = Selection.tournamentSelection(
                    algorithm.getTournamentSelectionKValue(), population,
                    Selection.Options.WORST);

            /*bestSolution = population.getBestSolution();
             System.out.println("Generación: " + i + " child: " + child.getCost() + 
             " best: " + bestSolution.getCost() + " worst " + population.getSolutions()[replacedSolution].getCost());
             */
            population.getSolutions()[replacedSolution] = child;
            
            if(n%100==0) setProgress(50 + (i+1) * 50 / n);
            
        }
        long end = System.currentTimeMillis();
        System.out.println("Grasp algorithm time: " + popTime + "ms");
        System.out.println("Genetic algorithm time: " + (end - ini) + "ms");

        bestSolution = population.getBestSolution();

        System.out.println("bestSolution cost: " + bestSolution.getCost());
        System.out.println("");
        //System.out.println(displayRoutes(bestSolution));
        //System.out.println("");
        System.out.println(displayDemand(bestSolution));

        double theTime = (popTime + (end - ini));
        theTime /= 1000;

        publish("Ejecución finalizada...");
        publish("Tiempo de ejecución: " + String.format("%.2f", theTime) + " s");

        bestSolution = orderByNodes(bestSolution);

        return bestSolution;
    }
    
    @Override
    protected void process(List<String> chunks) {
        for (String message : chunks) {
            informable.messageChanged(message);
        }
  }
    
}
