/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author LUIS
 */
public class Icons {
    
    public static Image img= null;
    public static ArrayList<Image> images = new ArrayList<Image>();
    public static enum ICONOS { 
        SAVE , 
        CANCEL, 
        SEARCH, 
        CREATE, 
        MODIFY, 
        DELETE,
        RESET
    };
    
    public static String[] getIconNames(){
         String[] iconNames={"Guardar","Cancelar","Buscar", "Nuevo", "Editar", "Eliminar","Resetear"};
         return iconNames;
    }
    
    public Icons(){
        loadIcons();
    }
    
    
    public static void setButton(JButton b, int a){
        
        try{
            img = images.get(a);
            img = img.getScaledInstance(16, 16,0);
            b.setIcon(new ImageIcon(img));
            b.setToolTipText(getIconNames()[a]);
            
        }catch(Exception e){
            System.out.println("Error al asignar icono");
            System.out.println(e.toString());
        }
    }
    public static void setButton(JButton b, int a, int width, int height){
        try{
            img = images.get(a);
            //ImageIcon img = new ImageIcon("../../images/01_new_16.ico");
            img = img.getScaledInstance(width, height,0);
            //cancelBtn.setIcon(img);
            b.setIcon(new ImageIcon(img));
            
        }catch(Exception e){
            System.out.println("Error al asignar icono");
            System.out.println(e.toString());
        }
    }
    
    public static void loadIcons(){
        try{
            img = ImageIO.read(URL.class.getResource("../../images/Save.png"));
            images.add(img);
            img = ImageIO.read(URL.class.getResource("../../images/Close.png"));
            images.add(img);
            img = ImageIO.read(URL.class.getResource("../../images/Zoom.png"));
            images.add(img);
            img = ImageIO.read(URL.class.getResource("../../images/Create.png"));
            images.add(img);
            img = ImageIO.read(URL.class.getResource("../../images/Modify.png"));
            images.add(img);
            img = ImageIO.read(URL.class.getResource("../../images/Delete.png"));
            images.add(img);
            img = ImageIO.read(URL.class.getResource("../../images/Unlock.png"));
            images.add(img);
            
        }catch(Exception e){
            System.out.println("Error en carga masiva de iconos");
            System.out.println(e.toString());
        }
    }
}
