/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.kardex;

import base.IRepository;
import entity.Kardex;
import entity.KardexId;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author prote_000
 */
public interface IKardexRepository extends IRepository<Kardex>{
    public ArrayList<Kardex> queryByParameters(int idWarehouse,int idProduct, Date dateIni, Date dateEnd);
    public ArrayList<Kardex> queryByParameters(int idWarehouse,int idProduct);
    public int insertKardexID(KardexId object);
}
