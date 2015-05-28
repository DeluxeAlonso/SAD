/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.condition;

import base.condition.IConditionRepository;
import entity.Condicion;
import infraestructure.condition.ConditionRepository;
import java.util.ArrayList;
import util.EntityType;

/**
 *
 * @author LUIS
 */
public class ConditionApplication {
    
    private IConditionRepository conditionRepository;
    public ConditionApplication(){
        this.conditionRepository = new ConditionRepository();
    }
    
    
    
    
    public Condicion getConditionInstance(String conditionName){
        for(Condicion p:EntityType.CONDITIONS){
            if(p.getNombre().equals(conditionName))
                return p;
        }
        return null;
    }
    
    public ArrayList<Condicion> queryAll(){
        ArrayList<Condicion> condiciones = null;
        try {
            condiciones = conditionRepository.queryAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return condiciones;
    }
    
    public void refreshConditions(){
        EntityType.CONDITIONS = queryAll();
    }
    
}
