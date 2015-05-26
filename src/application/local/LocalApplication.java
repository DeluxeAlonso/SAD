/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.local;

import base.local.ILocalRepository;
import entity.Local;
import infraestructure.local.LocalRepository;
import java.util.ArrayList;

/**
 *
 * @author prote_000
 */
public class LocalApplication {
    private ILocalRepository localRepository;
    public LocalApplication(){
        this.localRepository = new LocalRepository();
    }
    
    public void insert(Local object){
        try {
            localRepository.insert(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Local> queryLocalsByClient(int clientId){
        ArrayList<Local> locals = null;
        try {
            locals = localRepository.queryLocalsByClient(clientId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locals;
    }
    
    public Boolean delete(int localId){
        Boolean response = false;
        try {
            response = localRepository.delete(localId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
