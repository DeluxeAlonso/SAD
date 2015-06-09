/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.pallet;

import base.pallet.IPalletRepository;
import entity.Almacen;
import entity.Despacho;
import entity.OrdenInternamiento;
import entity.Pallet;
import entity.Producto;
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
        String hql="SELECT p.producto.nombre, p.producto.tipoProducto.nombre, p.producto.condicion.nombre, "
                + "count(1) "
                + "FROM Pallet p WHERE (p.ubicacion.rack.almacen.id = :wareId OR :wareId=0) AND " + 
                "(p.producto.condicion.id=:conId  OR :conId=-1) AND "+
                "(p.producto.tipoProducto.id =:tipoId OR :tipoId=-1) " +
                "  ORDER BY p.ubicacion.rack.almacen.id";
        
        
        List<Object[]> list=null;
        //gracias baldeon por enseniarme a usar DAO
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("wareId", almacen);
            q.setParameter("conId", condicion);
            q.setParameter("tipoId", tipo);
            //q.setParameter("lado",l);
            list = q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return list; //To change body of generated methods, choose Tools | Templates.
        
        
        
    }

    @Override
    public ArrayList<Pallet> queryByDeliveryParameters(Almacen warehouse, ArrayList<Despacho>delivery, Producto product) {
        String hql="from Pallet p where (p.pedidoParcial.id in (select pp.id from PedidoParcial pp where pp.guiaRemision.id in (select g.id from GuiaRemision g where g.despacho.id in (:delivery))) and (p.ubicacion.id in (select u.id from Ubicacion u where u.rack.id in (select r.id from Rack r where r.almacen.id=:warehouse))) and p.producto.id = :product)" ;
        ArrayList<Pallet> pallets=null;
        ArrayList idList = new ArrayList();
        for(int i=0;i<delivery.size();i++)
            idList.add(delivery.get(i).getId());
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameterList("delivery", idList);
            q.setParameter("product", product.getId());
            q.setParameter("warehouse", warehouse.getId());
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
    public ArrayList<Pallet> queryByWarehouseParameters(Almacen warehouse, ArrayList<Despacho> delivery) {
        String hql="from Pallet p where (p.pedidoParcial.id in (select pp.id from PedidoParcial pp where pp.guiaRemision.id in (select g.id from GuiaRemision g where g.despacho.id in (:delivery))) and (p.ubicacion.id in (select u.id from Ubicacion u where u.rack.id in (select r.id from Rack r where r.almacen.id=:warehouse))))" ;
        ArrayList<Pallet> pallets=null;
        ArrayList idList = new ArrayList();
        for(int i=0;i<delivery.size();i++)
            idList.add(delivery.get(i).getId());
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameterList("delivery", idList);
            q.setParameter("warehouse", warehouse.getId());
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
    
}
