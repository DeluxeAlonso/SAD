/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.client;

import base.client.IClientRepository;
import entity.Cliente;
import entity.Local;
import infraestructure.client.ClientRepository;
import java.util.ArrayList;
import util.EntityType;

/**
 *
 * @author prote_000
 */
public class ClientApplication {
    
    private IClientRepository clientRepository;
    public ClientApplication(){
        this.clientRepository = new ClientRepository();
    }
    
    public int insert(Cliente object){
        try {
            clientRepository.insert(object);
            return object.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public ArrayList<Cliente> queryAll(){
        ArrayList<Cliente> clients = null;
        try {
            clients = clientRepository.queryAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clients;
    }
    
    public Boolean delete(int clientId){
        Boolean response = false;
        try {
            response = clientRepository.delete(clientId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
    public void refreshClients(){
        EntityType.CLIENTS = queryAll();
    }
    
    public Local queryLocalByClientId(Cliente client, Local local){
        Local selectedLocal = null;
        try {
            selectedLocal = clientRepository.queryLocalById(client, local);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return selectedLocal;
    }
    
}
