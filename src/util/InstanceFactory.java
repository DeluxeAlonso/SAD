/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author dabarca
 */
public class InstanceFactory<T> {

    public static InstanceFactory Instance = new InstanceFactory();

    private Map<String, T> typeMap = new HashMap<String, T>();

    private InstanceFactory() {
    }

    public void register(String s, Class<T> type) throws InstantiationException,
        IllegalAccessException{       
        T object = type.newInstance();        
        if (object != null) {
            typeMap.put(s, object);
        }
    }

    public T getInstance(String s, Class<T> type) throws InstantiationException,
        IllegalAccessException{
        if(typeMap.containsKey(s)){
            return typeMap.get(s);
        }else{
             T instance = type.newInstance();
             typeMap.put(s, instance);
             return instance;
        }     
    }


}
