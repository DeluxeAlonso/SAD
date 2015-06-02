/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.kardex;

import base.kardex.IKardexRepository;
import entity.Kardex;
import entity.KardexId;
import infraestructure.kardex.KardexRepository;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author prote_000
 */
public class KardexApplication {
    IKardexRepository kardexRepository;
    
    public KardexApplication(){
        this.kardexRepository = new KardexRepository();
    }
    
    public int insert(Kardex object) {
        try{
            KardexRepository w = new KardexRepository();
            return w.insert(object);
        }
        catch (Exception e){
            
        }
        return -1;
    }
    
    public int insertKardexID(KardexId object) {
        try{
            KardexRepository w = new KardexRepository();
            return w.insertKardexID(object);
        }
        catch (Exception e){
            
        }
        return -1;
    }
    
    public ArrayList<Kardex> queryByParameters(int idWarehouse,int idProduct, Date dateIni, Date dateEnd){
        ArrayList<Kardex> kardex=null;
        try{
            kardex=kardexRepository.queryByParameters(idWarehouse,idProduct,dateIni,dateEnd);
        }catch(Exception e){
            e.printStackTrace();
        }
        return kardex;
    }  
    
    public ArrayList<Kardex> queryByParameters(int idWarehouse,int idProduct){
        ArrayList<Kardex> kardex=null;
        try{
            kardex=kardexRepository.queryByParameters(idWarehouse,idProduct);
        }catch(Exception e){
            e.printStackTrace();
        }
        return kardex;
    }
}
