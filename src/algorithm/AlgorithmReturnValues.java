/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import entity.Despacho;
import entity.PedidoParcial;
import java.util.ArrayList;

/**
 *
 * @author alulab14
 */
public class AlgorithmReturnValues {
    private ArrayList<PedidoParcial> rejectedOrders;
    private ArrayList<PedidoParcial> acceptedOrders;
    private ArrayList<Despacho> despachos;
    
    public AlgorithmReturnValues(ArrayList<Despacho> despachos,  ArrayList<PedidoParcial> acceptedOrders, ArrayList<PedidoParcial> rejectedOrders) {
        this.rejectedOrders = rejectedOrders;
        this.acceptedOrders = acceptedOrders;
        this.despachos = despachos;
    }
    
        public ArrayList<PedidoParcial> getRejectedOrders() {
        return rejectedOrders;
    }

    public void setRejectedOrders(ArrayList<PedidoParcial> rejectedOrders) {
        this.rejectedOrders = rejectedOrders;
    }

    public ArrayList<PedidoParcial> getAcceptedOrders() {
        return acceptedOrders;
    }

    public void setAcceptedOrders(ArrayList<PedidoParcial> acceptedOrders) {
        this.acceptedOrders = acceptedOrders;
    }

    public ArrayList<Despacho> getDespachos() {
        return despachos;
    }

    public void setDespachos(ArrayList<Despacho> despachos) {
        this.despachos = despachos;
    }

}
