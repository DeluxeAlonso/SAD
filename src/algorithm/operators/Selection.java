/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.operators;

import algorithm.Population;
import algorithm.Solution;
import java.util.Arrays;
import java.util.Collections;
import util.Randomizer;

/**
 *
 * @author robert
 */
public class Selection {
    public static enum Options {BEST, WORST};
    
    public static int tournamentSelection(int k, Population population, 
            Options option) {
        int idx = -1;
        Solution[] solutions = population.getSolutions();
        for (int i = 0; i < k; i++) {
            int chosen = Randomizer.randomInt(k-i);
            int last = k-i-1;
            if(option==Options.BEST){
                if(idx==-1 || solutions[chosen].getCost() < solutions[idx].getCost()){
                    idx = chosen;
                }
            }
            if(option==Options.WORST){
                if(idx==-1 || solutions[chosen].getCost() > solutions[idx].getCost()){
                    idx = chosen;
                }
            }
            if(idx==chosen) idx = last;
            Solution aux = solutions[chosen];
            solutions[chosen] = solutions[last];
            solutions[last] = aux;
            
        }        
        return idx;
    }
    
}
