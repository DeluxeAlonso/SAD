/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.condition;

import entity.Condicion;
import util.EntityType;

/**
 *
 * @author LUIS
 */
public class ConditionApplication {
    public Condicion getConditionInstance(String conditionName){
        for(Condicion p:EntityType.CONDITIONS){
            if(p.getNombre().equals(conditionName))
                return p;
        }
        return null;
    }
    
}
