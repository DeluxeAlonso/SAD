/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.operators;

import algorithm.Algorithm;
import algorithm.Problem;
import algorithm.Solution;
import util.Randomizer;

/**
 *
 * @author robert
 */
public class Mutation {

    public static Solution mutation(Solution s, Algorithm algorithm, Problem problem) {
        if(!Randomizer.doIt(algorithm.getMutationRate())) return s;
        
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
