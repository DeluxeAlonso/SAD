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
public class Algorithm {
    private int numberOfGenerations;
    private int populationSize;    
    private int tournamentSelectionKValue;            
    private double mutationRate;     
    private double overcapPenalty;
    private double overtimePenalty; 
    private double overstockPenalty; 
    private double maxPriority;
    private double basePriority;  
    private double maxTravelTime;

    public double getMaxTravelTime() {
        return maxTravelTime;
    }

    public void setMaxTravelTime(double maxTravelTime) {
        this.maxTravelTime = maxTravelTime;
    }
    
    public int getNumberOfGenerations() {
        return numberOfGenerations;
    }

    public void setNumberOfGenerations(int numberOfGenerations) {
        this.numberOfGenerations = numberOfGenerations;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public int getTournamentSelectionKValue() {
        return tournamentSelectionKValue;
    }

    public void setTournamentSelectionKValue(int tournamentSelectionKValue) {
        this.tournamentSelectionKValue = tournamentSelectionKValue;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public double getOvercapPenalty() {
        return overcapPenalty;
    }

    public void setOvercapPenalty(double overcapPenalty) {
        this.overcapPenalty = overcapPenalty;
    }

    public double getOvertimePenalty() {
        return overtimePenalty;
    }

    public void setOvertimePenalty(double overtimePenalty) {
        this.overtimePenalty = overtimePenalty;
    }

    public double getOverstockPenalty() {
        return overstockPenalty;
    }

    public void setOverstockPenalty(double overstockPenalty) {
        this.overstockPenalty = overstockPenalty;
    }

    public double getMaxPriority() {
        return maxPriority;
    }

    public void setMaxPriority(double maxPriority) {
        this.maxPriority = maxPriority;
    }

    public double getBasePriority() {
        return basePriority;
    }

    public void setBasePriority(double basePriority) {
        this.basePriority = basePriority;
    }
}
