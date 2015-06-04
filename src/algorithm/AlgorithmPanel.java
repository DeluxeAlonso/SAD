/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import java.awt.Color;
import java.awt.Graphics;
import util.Constants;

/**
 *
 * @author robert
 */
public class AlgorithmPanel extends javax.swing.JPanel {
    private Solution solution;
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;
    private double xAl;
    private double yAl;

    /**
     * Creates new form PanelResultado
     */
    public AlgorithmPanel() {
    }

    public AlgorithmPanel(Solution solution) {
        double minXa = Double.MAX_VALUE, maxXa = -Double.MAX_VALUE;
        double minYa = Double.MAX_VALUE, maxYa = -Double.MAX_VALUE;
        double xAla, yAla;
        minXa = Math.min(minXa, xAla = Constants.WAREHOUSE_LONGITUDE);
        maxXa = Math.max(maxXa, Constants.WAREHOUSE_LONGITUDE);
        minYa = Math.min(minYa, yAla = Constants.WAREHOUSE_LATITUDE);
        maxYa = Math.max(maxYa, Constants.WAREHOUSE_LATITUDE);

        this.xAl = xAla;
        this.yAl = yAla;
        Node[][] nodes = solution.getNodes();
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].length; j++) {
                minXa = Math.min(minXa, nodes[i][j].getX());
                maxXa = Math.max(maxXa, nodes[i][j].getX());
                minYa = Math.min(minYa, nodes[i][j].getY());
                maxYa = Math.max(maxYa, nodes[i][j].getY());                
            }            
        }
        
        minX = minXa - 0.01;// - 10;
        maxX = maxXa + 0.01;// + 10;
        minY = minYa - 0.01;// - 10;
        maxY = maxYa + 0.01;// + 10;

        //System.out.println("min/max " + minX + " " + maxX + " " + minY + " " + maxY);
        
        this.solution = solution;

        setBackground(new java.awt.Color(255, 255, 255));
        initComponents();
        
        
        //System.out.println("constructor");
    }

    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.setColor(Color.white);
//        g.fillRect(0, 0, getWidth(), getHeight());
//        g.setColor(Color.black);
        int totX = this.getWidth();
        int totY = this.getHeight();

        Node[][] nodes = solution.getNodes();
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 1; j < nodes[i].length; j++) { //dibujar la ruta
                double posX2 = nodes[i][j].getX();
                double posX1 = nodes[i][j - 1].getX();
                double posY2 = nodes[i][j].getY();
                double posY1 = nodes[i][j - 1].getY();
                int x1 = (int) (totX * (posX1 - minX) / (maxX - minX));
                int y1 = (int) (totY * (posY1 - minY) / (maxY - minY));
                int x2 = (int) (totX * (posX2 - minX) / (maxX - minX));
                int y2 = (int) (totY * (posY2 - minY) / (maxY - minY));
                g.drawLine(x1, y1, x2, y2);
                
                //System.out.println(x1 + " " + y1 + " / " + x2 + " " + y2);

                g.setColor(Color.blue);
                g.drawString(((Integer) nodes[i][j].getIdx()).toString(), x2, y2);
                g.drawString(((Integer) nodes[i][j - 1].getIdx()).toString(), x1, y1);
                g.setColor(Color.black);
            }
            if (nodes[i].length > 0) { //si la ruta contiene al menos un cliente
                //dibujar camino desde el centro de distribucion
                double posX2 = nodes[i][0].getX();
                double posX1 = xAl;
                double posY2 = nodes[i][0].getY();
                double posY1 = yAl;
                int x1 = (int) (totX * (posX1 - minX) / (maxX - minX));
                int y1 = (int) (totY * (posY1 - minY) / (maxY - minY));
                int x2 = (int) (totX * (posX2 - minX) / (maxX - minX));
                int y2 = (int) (totY * (posY2 - minY) / (maxY - minY));
                g.drawLine(x1, y1, x2, y2);

                //System.out.println(x1 + " " + y1 + " / " + x2 + " " + y2);
                
                g.setColor(Color.blue);
                g.drawString(((Integer) nodes[i][1].getIdx()).toString(), x2, y2);
                g.drawString("0", x1, y1);
                g.setColor(Color.black);

                //dibujar camino hacia el centro de distribucion
                posX1 = nodes[i][nodes[i].length - 1].getX();
                posX2 = xAl;
                posY1 = nodes[i][nodes[i].length - 1].getY();
                posY2 = yAl;
                x1 = (int) (totX * (posX1 - minX) / (maxX - minX));
                y1 = (int) (totY * (posY1 - minY) / (maxY - minY));
                x2 = (int) (totX * (posX2 - minX) / (maxX - minX));
                y2 = (int) (totY * (posY2 - minY) / (maxY - minY));
                g.drawLine(x1, y1, x2, y2);

                //System.out.println(x1 + " " + y1 + " / " + x2 + " " + y2);
                
                g.setColor(Color.blue);
                g.drawString(((Integer) nodes[i][nodes[i].length - 1].getIdx()).toString(), x1, y1);
                g.setColor(Color.black);
                //g2.drawString("0", x2, y2);
            }
        }
        
        System.out.println("painted");
    }    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
