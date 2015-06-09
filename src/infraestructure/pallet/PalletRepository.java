/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.pallet;

import base.pallet.IPalletRepository;
import entity.OrdenInternamiento;
import entity.Pallet;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.EntityState;
import util.EntityState.Pallets;
import util.Tools;

/**
 *
 * @author prote_000
 */
public class PalletRepository implements IPalletRepository{

    @Override
    public ArrayList<Pallet> queryPalletsByRack(int rackId) {
        String hql="from Pallet p where p.ubicacion.id in (select u.id from Ubicacion u where u.rack.id=:rackId)";
        ArrayList<Pallet> pallets=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("rackId", rackId);
            pallets = (ArrayList<Pallet>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return pallets; //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public ArrayList<Pallet> queryPalletsBySpot(int spotId) {
        String hql="from Pallet p where p.ubicacion.id=:spotId";
        ArrayList<Pallet> pallets=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("spotId", spotId);
            pallets = (ArrayList<Pallet>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return pallets; //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Boolean updatePalletSpot(int palletId, int spotId) {
        String hql="UPDATE Pallet p SET p.id_ubicacion=:spotId WHERE p.id=:palletId";
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createSQLQuery(hql);
            q.setParameter("spotId", spotId);
            q.setParameter("palletId", palletId);   
            q.executeUpdate();      
            session.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public Boolean deletePalletBySpot(int spotId) {
        String hql="UPDATE Pallet p SET p.estado=:state WHERE p.ubicacion.id=:spotId";
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createSQLQuery(hql);
            q.setParameter("spotId", spotId);
            q.setParameter("state", Pallets.ELIMINADO.ordinal());
            q.executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int insert(Pallet object) {
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            session.save(object);                      
            session.getTransaction().commit();            
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
            return -1;
        } 
        return object.getId();
    }

    @Override
    public int delete(Pallet object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Pallet> queryAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(Pallet object) {
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            session.saveOrUpdate(object);                      
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
            return -1;
        } 
        return 1;
    }

    @Override
    public Pallet queryById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pallet queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Boolean updateSpot(int palletId,int spotId) {
        String hql="UPDATE Pallet u SET u.ubicacion.id=:spotId WHERE u.id=:palletId";
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createSQLQuery(hql);
            q.setParameter("palletId", palletId);
            q.setParameter("spotId", spotId);  
            q.executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    
    
    @Override
    public ArrayList<Pallet> getPalletsFromOrder(int id) {
        String hql="FROM Pallet p WHERE p.ordenInternamiento.id=:id";
        ArrayList<Pallet> orden=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("id", id);
            orden = (ArrayList<Pallet>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        if (orden.size()==0)
            return null;
        else
            return orden; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Pallet> queryPalletsByProduct(Integer productId) {
        String hql="FROM Pallet WHERE id_producto=:productId and estado=1";
        ArrayList<Pallet> pallets= new ArrayList<>();
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("productId", productId);
            pallets = (ArrayList<Pallet>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return pallets;
    }

    @Override
    public ArrayList<Pallet> queryPalletsByPartialOrder(Integer partialOrderId) {
        String hql="FROM Pallet WHERE id_pedido_parcial=:partialOrderId";
        ArrayList<Pallet> pallets= new ArrayList<>();
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("partialOrderId", partialOrderId);
            pallets = (ArrayList<Pallet>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return pallets;
    }

    @Override
    public Boolean updatePallets(ArrayList<Pallet> pallets) {
                Session session = Tools.getSessionInstance();
        Transaction trns = null;
        try {            
            trns=session.beginTransaction();
            for(int i=0;i<pallets.size();i++){
                session.update(pallets.get(i));
            }                  
            session.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Pallet> queryByParameters(String ean, int almacen, int producto,int internmentOrder, Boolean selected) {
        String hql1= "FROM Pallet a where (a.ubicacion.id in (select u.id from Ubicacion u where u.rack.id in" +
                    "(select r.id from Rack r where (r.almacen.id=:almacen OR :almacen=0)))) AND "+
                    "(a.ean128 like :ean OR :ean = :aux) AND" +
                    "(a.ordenInternamiento.id = :internmentOrder OR :internmentOrder = 0) AND" +
                    "(a.producto.id = :producto OR :producto = 0)";
        
        String hql2 = "FROM Pallet a where (a.ubicacion is null) AND "+
                        "(a.ean128 like :ean OR :ean = :aux) AND" +
                        "(a.ordenInternamiento.id = :internmentOrder OR :internmentOrder = 0) AND" +
                        "(a.producto.id = :producto OR :producto = 0)";       
        String hql = null;
        ArrayList<Pallet> pallets=null;
        Transaction trns = null;
        
        
        if (selected){
            hql = hql2;
            Session session = Tools.getSessionInstance();
            try {            
                trns=session.beginTransaction();
                Query q = session.createQuery(hql);
                q.setParameter("producto", producto);
                q.setParameter("ean", ean);
                q.setParameter("aux","");
                q.setParameter("internmentOrder",internmentOrder);
                pallets = (ArrayList<Pallet>) q.list();          
                session.getTransaction().commit();
            } catch (RuntimeException e) {
                if (trns != null) {
                    trns.rollback();
                }
                e.printStackTrace();
            }      
        }
            
        else{
            hql = hql1;
            Session session = Tools.getSessionInstance();
            try {            
                trns=session.beginTransaction();
                Query q = session.createQuery(hql);
                q.setParameter("ean", ean);
                q.setParameter("almacen", almacen);
                q.setParameter("producto", producto);
                q.setParameter("aux","");
                q.setParameter("internmentOrder",internmentOrder);
                pallets = (ArrayList<Pallet>) q.list();          
                session.getTransaction().commit();
            } catch (RuntimeException e) {
                if (trns != null) {
                    trns.rollback();
                }
                e.printStackTrace();
            }
        }

        return pallets; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    public List<Object[]> queryByReport(int almacen, int condicion, int tipo, int reporte) {
        String dif="";
        if (reporte ==0){
            dif="WHERE (:wareId=0 OR p.ubicacion.rack.almacen.id = :wareId )";
        }
        else if (reporte==1){
            dif="WHERE (p.estado = :est1 OR p.estado= :est2) ";
        }else if (reporte ==2){
            
        }
                    /*
        String hql="SELECT p.producto.nombre, p.producto.tipoProducto.nombre, p.producto.condicion.nombre, "
                + "count(1) "
                + "FROM Pallet p "
                //+ "WHERE (p.ubicacion.rack.almacen.id = :wareId OR :wareId=0)" //" AND"
                //+ dif + 
                 + " GROUP BY p.producto.nombre" +
                "  ORDER BY p.ubicacion.rack.almacen.id ";
        */
        String hql="SELECT pro.nombre, pro.tipoProducto.nombre, pro.condicion.nombre, "
                + "count(1) "
                + "FROM Pallet p "
                + "JOIN p.producto pro "
                
                + dif  
                 + " GROUP BY pro.nombre, pro.tipoProducto.nombre, pro.condicion.nombre" ;
        
        
        
       
        
        
        String hql1= "SELECT p.nombre, p.tipoProducto.nombre, p.condicion.nombre, 0 "
                    + "FROM Producto p "
                    + "WHERE (p.condicion.id = :id OR :id =0) ";
        
        String hql2 = "SELECT p.nombre, p.tipoProducto.nombre, p.condicion.nombre, p.stockLogico"
                + " FROM Producto p";
        
        
        List<Object[]> listPall=null;
        List<Object[]> listPro=null;
        //gracias baldeon por enseniarme a usar DAO
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            if (reporte!=2){
            Query q = session.createQuery(hql);
            if (reporte ==0){
                q.setParameter("wareId", almacen);
            }
            else if (reporte==1){
                q.setParameter("est1", EntityState.Pallets.CREADO.ordinal());
                q.setParameter("est2",EntityState.Pallets.UBICADO.ordinal());
            }
            
            
            listPall = q.list();          
            Query q1 = session.createQuery(hql1);
            q1.setParameter("id", condicion);
            listPro = q1.list();
            
            session.getTransaction().commit();
            /////////////////////////////////////
            for (Object[] arr: listPro){
                for (Object[] arr1: listPall){
                    if (arr[0].equals(arr1[0])){
                        arr[3]=arr1[3];
                    }
                }
            }
            
            
            
            ////////////////////////////////////////////
            }else {
                
                Query q = session.createQuery(hql2);
                listPro = q.list();
            
                session.getTransaction().commit();
            }
            
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return listPro; //To change body of generated methods, choose Tools | Templates.
        
        
        
    }
    
}
