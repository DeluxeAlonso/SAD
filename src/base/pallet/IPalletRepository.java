/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.pallet;

import base.IRepository;
import entity.Almacen;
import entity.Despacho;
import entity.OrdenInternamiento;
import entity.Pallet;
import entity.Producto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author prote_000
 */
public interface IPalletRepository extends IRepository<Pallet>{
    ArrayList<Pallet> queryPalletsByRack(int rackId);
    Boolean updatePalletSpot(int palletId, int spotId);
    public Boolean updateSpot(int palletId,int spotId);
    public ArrayList<Pallet> getPalletsFromOrder(int rackId);
    public ArrayList<Pallet> queryPalletsByProduct(Integer productId);
    
    public ArrayList<Pallet> queryPalletsByPartialOrder(Integer partialOrderId);

    public Boolean updatePallets(ArrayList<Pallet> pallets);
    public Boolean deletePalletBySpot(int spotId);
    public ArrayList<Pallet> queryPalletsBySpot(int spotId);
    public List<Object[]> queryByReport(int almacen, int condicion, int tipo, int reporte);
    public ArrayList<Pallet> queryByParameters(String ean, int almacen, int producto,int internmentOrder, Boolean selected);
    public ArrayList<Pallet> queryByDeliveryParameters(Almacen warehouse, ArrayList<Despacho>delivery, Producto product);

    public ArrayList<Pallet> queryByWarehouseParameters(Almacen warehouse, ArrayList<Despacho> delivery);
    public List<Object[]> queryByReportInter(int almacen, Date fechaD, Date fechaH, int tipo);
    public int insertNPallets(ArrayList<Pallet> pallets);
}
