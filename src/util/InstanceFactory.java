/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author dabarca
 */
public class InstanceFactory {

    public static InstanceFactory Instance = new InstanceFactory();

    private Map<String, Object> typeMap = new HashMap<String, Object>();

    private InstanceFactory() {
    }

    public <T> void  register(String s, Class<T> type) {       
        T object=null;        
        try {
            object = type.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(InstanceFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(InstanceFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (object != null) {
            typeMap.put(s, object);
        }
    }

    public <T> T getInstance(String s, Class<T> type) {
        if(typeMap.containsKey(s)){
            return (T)typeMap.get(s);
        }else{
             T instance=null;
            try {
                instance = type.newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(InstanceFactory.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(InstanceFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(instance!=null)
                typeMap.put(s, instance);
            return instance;
        }     
    }


}
