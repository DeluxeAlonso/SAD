/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.pallet;

import base.pallet.IPalletRepository;
import entity.Almacen;
import entity.Despacho;
import entity.OrdenInternamiento;
import entity.Pallet;
import entity.Producto;
import infraestructure.pallet.PalletRepository;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prote_000
 */
public class PalletApplication {
    private IPalletRepository palletRepository;
    
    public PalletApplication(){
        this.palletRepository = new PalletRepository();
    }
    
    public ArrayList<Pallet> queryPalletsByRack(int rackId){
        ArrayList<Pallet> pallets = null;
        try {
            pallets = palletRepository.queryPalletsByRack(rackId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pallets;
    }
    
    public ArrayList<Pallet> queryPalletsBySpot(int spotId){
        ArrayList<Pallet> pallets = null;
        try {
            pallets = palletRepository.queryPalletsBySpot(spotId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pallets;
    }
    
    public Boolean deletePalletBySpot(int spotId){
        Boolean response = false;
        try {
            response = palletRepository.deletePalletBySpot(spotId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
    public Boolean updatePalletSpot(int palletId, int spotId){
        Boolean response = false;
        try {
            response = palletRepository.updatePalletSpot(palletId, spotId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
        public Boolean updateSpot(int palletId, int spotId){
        Boolean response = false;
        try {
            response = palletRepository.updatePalletSpot(palletId, spotId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
        public int insert(Pallet object) {
        try{
            PalletRepository w = new PalletRepository();
            w.insert(object);
            return object.getId();
        }
        catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    
        public ArrayList<Pallet> getPalletsFromOrder(int rackId){
        ArrayList<Pallet> pallets = new ArrayList<>();
        try {
            pallets = palletRepository.getPalletsFromOrder(rackId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pallets;
    }
        
    public ArrayList<Pallet> getAvailablePalletsByProductId(Integer productId){
        ArrayList<Pallet> pallets = null;
        try {
            pallets = palletRepository.queryPalletsByProduct(productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pallets;
    }

    public ArrayList<Pallet> getPalletsByPartialOrder(Integer partialOrderId){
        ArrayList<Pallet> pallets = null;
        try {
            pallets = palletRepository.queryPalletsByPartialOrder(partialOrderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pallets;
    }

        public void update(Pallet pallet){
        
        try{
            palletRepository.update(pallet);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
        
    public Boolean updatePallets(ArrayList<Pallet>pallets){
        Boolean response = false;
        try {
            response = palletRepository.updatePallets(pallets);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
    public List<Object[]> queryByReport(int almacen, int condicion, int tipo, int reporte){
        List<Object[]> pallets = null;
        try {
            pallets = palletRepository.queryByReport(almacen,condicion,tipo,reporte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pallets;
    }

    public ArrayList<Pallet> queryByParameters(String ean, int almacen, int producto, int internmentOrder, Boolean selected){
        ArrayList<Pallet> pallets = new ArrayList<>();
        try {
            pallets = palletRepository.queryByParameters(ean, almacen, producto,internmentOrder, selected);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pallets;
    }
    
    public ArrayList<Pallet> queryByDeliveryParameters(Almacen warehouse, ArrayList<Despacho> delivery,Producto product){
        ArrayList<Pallet> pallets = new ArrayList<>();
        try {
            pallets = palletRepository.queryByDeliveryParameters(warehouse, delivery,product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pallets;
    }
    
    public ArrayList<Pallet> queryByWarehouseParameters(Almacen warehouse, ArrayList<Despacho> delivery){
        ArrayList<Pallet> pallets = new ArrayList<>();
        try {
            pallets = palletRepository.queryByWarehouseParameters(warehouse, delivery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pallets;
    }
    
}
