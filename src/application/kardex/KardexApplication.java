/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.kardex;

import base.kardex.IKardexRepository;
import entity.Kardex;
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
    
    public ArrayList<Kardex> queryByParameters(int idWarehouse,int idProduct, Date dateIni, Date dateEnd){
        ArrayList<Kardex> kardex=null;
        try{
            kardex=kardexRepository.queryByParameters(idWarehouse,idProduct,dateIni,dateEnd);
        }catch(Exception e){
            e.printStackTrace();
        }
        return kardex;
    }  
}
