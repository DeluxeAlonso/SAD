/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

/**
 *
 * @author robert
 */
public class Population {
    private Solution[] solutions;
    private Algorithm algorithm;
    private Problem problem;
    
    Population(Algorithm algorithm, Problem problem) {
        this.algorithm = algorithm;
        this.problem = problem;
        generatePopulation();        
    }

    private void generatePopulation() {
        solutions = new Solution[algorithm.getPopulationSize()];
        for (int i = 0; i < solutions.length; i++) {
            solutions[i] = new Solution(algorithm, problem);            
        }        
    }
    
    public Solution[] getSolutions() {
        return solutions;
    }

    public void setSolutions(Solution[] solutions) {
        this.solutions = solutions;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    Solution getBestSolution() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
