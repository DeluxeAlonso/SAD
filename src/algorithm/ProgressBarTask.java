/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import client.delivery.DeliveryView;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 *
 * @author robert
 */
public class ProgressBarTask extends SwingWorker<Void, Void>{
    private DeliveryView view;
    
    public ProgressBarTask(DeliveryView view){
        this.view = view;    
    }
    
    @Override
    public Void doInBackground() throws Exception {
        String cad = "";
        boolean first = false, second = false, third = false;
        int progress = 0;
        setProgress(0);
        while (progress < 100) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProgressBarTask.class.getName()).log(Level.SEVERE, null, ex);
            }
            progress += 5;
            setProgress(Math.min(progress, 100));
            
            /*int nGen = AlgorithmExecution.nGen;
            int nPop = AlgorithmExecution.nPop;
            int iGen = AlgorithmExecution.iGen;
            int iPop = AlgorithmExecution.iPop;
            System.out.println(iPop + " " + nPop + " " + iGen + " " + nGen);
            if (nPop == 0 || iPop == 0) {
                if (!first) {
                    cad += "Inicializando parámetros...";
                    view.getTxtResult().setText(cad);
                    first = true;
                }
                progress = 0;
                setProgress(0);
            } else if (iPop < nPop) {
                if (iPop < 0.1 * nPop && !second) {
                    cad += "\nIniciando primera fase del algoritmo...";
                    view.getTxtResult().setText(cad);
                    second = true;
                }
                progress = iPop * 50 / nPop;
                setProgress(progress);
            } else if (iPop == nPop && iGen < 0.1 * nGen) {
                if (!third) {
                    cad += "\nIniciando segunda fase del algoritmo...";
                    view.getTxtResult().setText(cad);
                    third = true;
                }
                progress = 50;
                setProgress(50);
            } else if (iGen < nGen) {
                progress = 50 + iGen * 50 / nGen;
                setProgress(progress);                
            } else if (iGen == nGen) {
                progress = 100;
                setProgress(progress);
            }    */            
        }
        return null;
    }
    
    
}
