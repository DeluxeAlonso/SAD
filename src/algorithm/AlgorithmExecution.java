/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import java.util.Arrays;

/**
 *
 * @author robert
 */
public class AlgorithmExecution {
    
    public void start(){
        Algorithm algorithm = new Algorithm();
        algorithm.setPopulationSize(50000);
        algorithm.setTournamentSelectionKValue(50);
        algorithm.setOvercapPenalty(10000);
        algorithm.setOvertimePenalty(10000);
        algorithm.setOverstockPenalty(10000);
        algorithm.setMutationRate(0.5f);
        algorithm.setMaxPriority(10000);
        algorithm.setBasePriority(1.2);
        
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
            
        }
    }
    
}
