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
import algorithm.operators.Repair;
import algorithm.operators.Replacement;
import java.util.Arrays;

/**
 *
 * @author robert
 */
public class AlgorithmExecution {
    
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
        
        Problem problem = new Problem();
        
        Population population = new Population(algorithm, problem);
        
        for (int i = 0; i < algorithm.getNumberOfGenerations(); i++) {
            Solution[] parents = new Solution[2];
            parents[0] = Selection.tournamentSelection(
                        algorithm.getTournamentSelectionKValue(), population, 
                        Selection.options.BEST.ordinal());
            parents[1] = Selection.tournamentSelection(
                        algorithm.getTournamentSelectionKValue(), population, 
                        Selection.options.BEST.ordinal());
            Arrays.sort(parents);
            
            Solution child = Crossover.uniformCrossover(parents, algorithm, problem);
            
            child = Mutation.mutation(child, algorithm, problem);            
            child = LocalSearch.opt2Improvement(child);            
            child = Repair.repair(child);
            
            Solution replacedSolution = Selection.tournamentSelection(
                    algorithm.getTournamentSelectionKValue(), population, 
                    Selection.options.WORST.ordinal());
            
            Replacement.solutionReplacement(population, child, replacedSolution);
        }
        long end = System.currentTimeMillis();
        System.out.println("Execution time: " + (end-ini) + "ms");
        
        Solution bestSolution = population.getBestSolution();
    }
}
