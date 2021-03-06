/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.profile;

import base.profile.IProfileRepository;
import entity.Perfil;
import infraestructure.profile.ProfileRepository;
import java.util.ArrayList;
import util.EntityType;

/**
 *
 * @author dabarca
 */
public class ProfileApplication {
    private IProfileRepository profileRepository;
    
    public ProfileApplication(){
        this.profileRepository=new ProfileRepository();
    }
    
    public ArrayList<Perfil> getAllProfiles(){
        ArrayList<Perfil> profiles=null;
        try{
            profiles=profileRepository.queryAll();
        }catch(Exception e){
            e.printStackTrace();
        }
        return profiles;
    }
    
    public void updateProfile(Perfil profile){        
        try{
            profileRepository.update(profile);
        }catch(Exception e){
            e.printStackTrace();
        }       
    }
    
    public void refreshProfiles() {
        
        EntityType.PROFILES = getAllProfiles();
        EntityType.fillProfileNames();
        
    }
    
    public Perfil getProfileByName(String profileName){
        Perfil profile=null;
        try{
            profile=profileRepository.queryByName(profileName);
        }catch(Exception e){
            e.printStackTrace();
        }
        return profile;
    }
    
    public void insertProfile(Perfil profile){
        try{
            profileRepository.insert(profile);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void deleteProfile(Perfil profile){
        try{
            profileRepository.delete(profile);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
