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
    
    public ConditionApplication() {
        this.conditionRepository = new ConditionRepository();
    }
    
    public ArrayList<Condicion> getAllConditions(){
        ArrayList<Condicion> conditions=null;
        try{
            conditions=conditionRepository.queryAll();
        }catch(Exception e){
            e.printStackTrace();
        }
        return conditions;
    }
    
    public void refreshConditions() {        
        EntityType.CONDITIONS = getAllConditions();
        EntityType.fillConditionNames();        
    }
    
    public Condicion getConditionInstance(String conditionName){
        for(Condicion p:EntityType.CONDITIONS){
            if(p.getNombre().equals(conditionName))
                return p;
        }
        return null;
    }
    
}
