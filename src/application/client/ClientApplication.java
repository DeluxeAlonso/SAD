/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.client;

import base.client.IClientRepository;
import entity.Cliente;
import infraestructure.client.ClientRepository;

/**
 *
 * @author prote_000
 */
public class ClientApplication {
    
    private IClientRepository clientRepository;
    public ClientApplication(){
        this.clientRepository = new ClientRepository();
    }
    
    public void insert(Cliente object){
        try {
            clientRepository.insert(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
