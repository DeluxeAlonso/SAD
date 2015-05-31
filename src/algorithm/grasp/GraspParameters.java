/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.grasp;

/**
 *
 * @author robert
 */
class GraspParameters {
    private double beta;
    private double tau;

    public GraspParameters(double beta, double tau) {
        this.beta = beta;
        this.tau = tau;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getTau() {
        return tau;
    }

    public void setTau(double tau) {
        this.tau = tau;
    }
}
