/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.pallet;

import base.pallet.IPalletRepository;
import entity.Almacen;
import entity.Despacho;
import entity.Kardex;
import entity.OrdenInternamiento;
import entity.OrdenInternamientoXProducto;
import entity.Pallet;
import entity.Producto;
import entity.Ubicacion;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
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
        String hql="from Pallet p where p.ubicacion.id=:spotId and p.estado=:state";
        ArrayList<Pallet> pallets=null;
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("spotId", spotId);
            q.setParameter("state", Pallets.UBICADO.ordinal());
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
        String hql="UPDATE Pallet SET estado=:state WHERE id_ubicacion=:spotId";
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createSQLQuery(hql);
            q.setParameter("spotId", spotId);
            q.setParameter("state", Pallets.ROTO.ordinal());
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
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        Pallet pallet = null;
        String hql
                = "from Pallet where id=:id";
        try {
            
            session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("id", id);
            pallet = (Pallet) q.uniqueResult();
            if(pallet!=null)
                Hibernate.initialize(pallet.getId());
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return pallet;
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
        String hql="FROM Pallet p WHERE p.ordenInternamiento.id=:id and p.estado=0";
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
    public ArrayList<Pallet> getPendPalletsFromOrder(int id) {
        String hql="FROM Pallet p WHERE p.ordenInternamiento.id=:id AND p.estado= :estado";
        ArrayList<Pallet> orden= new ArrayList<>();
        
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("id", id);
            q.setParameter("estado", 0);
            orden = (ArrayList<Pallet>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
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
        String hql="FROM Pallet WHERE id_pedido_parcial=:partialOrderId and estado=2";
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
    public ArrayList<Pallet> queryByParameters(String ean, int almacen, int producto,int internmentOrder, int estado) {
        String hql1= "FROM Pallet a where (a.ubicacion.id in (select u.id from Ubicacion u where u.rack.id in" +
                    "(select r.id from Rack r where (r.almacen.id=:almacen OR :almacen=0)))) AND "+
                    "(a.ean128 like :ean OR :ean = :aux) AND" +
                    "(a.ordenInternamiento.id = :internmentOrder OR :internmentOrder = 0) AND" +
                    "(a.estado = :estado OR :estado = -1) AND" +
                    "(a.producto.id = :producto OR :producto = 0) ORDER BY a.ubicacion.rack.almacen.id, a.ubicacion.rack.id, a.ubicacion.lado, a.ubicacion.fila, a.ubicacion.columna";
        
        String hql2 = "FROM Pallet a where (a.ubicacion is null) AND "+
                        "(a.ean128 like :ean OR :ean = :aux) AND" +
                        "(a.ordenInternamiento.id = :internmentOrder OR :internmentOrder = 0) AND" +
                        "(a.estado = :estado  OR :estado = -1) AND" +
                        "(a.producto.id = :producto OR :producto = 0)";
 
        
        String hql = null;
        ArrayList<Pallet> pallets=null;
        Transaction trns = null;
        
            Session session = Tools.getSessionInstance();
            try {            
                trns=session.beginTransaction();
                Query q = session.createQuery(hql1);
                Query q2 = session.createQuery(hql2);
                q.setParameter("almacen", almacen);
                q.setParameter("producto", producto);
                q.setParameter("ean", "%"+ean+"%");
                q.setParameter("estado", estado);
                q.setParameter("aux","");
                q.setParameter("internmentOrder",internmentOrder);
                
                q2.setParameter("producto", producto);
                q2.setParameter("ean", "%"+ean+"%");
                q2.setParameter("aux","");
                q2.setParameter("estado", estado);
                q2.setParameter("internmentOrder",internmentOrder);

                pallets = (ArrayList<Pallet>) q.list();          
                pallets.addAll(q2.list());
                
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

    @Override
    public ArrayList<Pallet> queryByDeliveryParameters(Almacen warehouse, ArrayList<Despacho>delivery, Producto product) {
        String hql="from Pallet p where (p.pedidoParcial.id in (select pp.id from PedidoParcial pp where pp.guiaRemision.id in (select g.id from GuiaRemision g where g.despacho.id in (:delivery))) and (p.ubicacion.id in (select u.id from Ubicacion u where u.rack.id in (select r.id from Rack r where r.almacen.id=:warehouse))) and p.producto.id = :product)" ;
        ArrayList<Pallet> pallets = new ArrayList<>();
        ArrayList idList = new ArrayList();
        for(int i=0;i<delivery.size();i++){
            idList.add(delivery.get(i).getId());
        }
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
        ArrayList<Pallet> pallets=new ArrayList<>();
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

    @Override
    public List<Object[]> queryByReportInter(int almacen, Date fechaD, Date fechaH, int tipo) {
        String hql="SELECT p.ordenInternamiento.id, p.ubicacion.rack.almacen.descripcion, pro.nombre, pro.tipoProducto.nombre, pro.condicion.nombre, "
                + "count(1) , p.fechaRegistro "
                + "FROM Pallet p "
                + "JOIN p.producto pro "
                + "WHERE (p.ubicacion.rack.almacen.id = :wareId OR :wareId=0) AND (p.fechaRegistro BETWEEN :dateIni AND :dateEnd)"
                 + " GROUP BY p.ordenInternamiento.id,p.ubicacion.rack.almacen.descripcion, "
                + "pro.nombre, pro.tipoProducto.nombre, pro.condicion.nombre, "
                + "p.fechaRegistro" ;
        List<Object[]> list=null;
        //gracias baldeon por enseniarme a usar DAO
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            
            Query q = session.createQuery(hql);
            q.setParameter("wareId", almacen);
            q.setParameter("dateIni", fechaD);
            q.setParameter("dateEnd", fechaH);
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
    public int insertNPallets(ArrayList<Pallet> pallets) {
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Pallet pIni=pallets.get(0);
            session.save(pIni);
            session.flush();
            int startId=pIni.getId();
            String ean128=pIni.getEan128();            
            pIni.setEan128(ean128+startId);
            session.update(pIni);
            session.flush();
            int cant=pallets.size();
            
            for(int i=0;i<cant;i++){
                if(i==0) continue;
                Pallet p = pallets.get(i);
                int num=startId+i;
                p.setEan128(ean128+num);
                p.setId(num);
                session.save(p);
                if(i%20==0) session.flush();
            }
                                 
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
    
    public int internNPallets(ArrayList<Pallet> pallets, OrdenInternamientoXProducto orXProd, Kardex kardex ) {
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Almacen alm = pallets.get(0).getUbicacion().getRack().getAlmacen();
            Producto prod = pallets.get(0).getProducto();
            for (Pallet p : pallets){
                if (p.getUbicacion() != null){
                    //Actualizar ubicacion de pallets
                    session.update(p);
                    //actualizar estado en ubicaciones
                    Ubicacion ub = p.getUbicacion();
                    ub.setEstado(EntityState.Spots.OCUPADO.ordinal());
                    session.update(ub);                    
                }
            }
            session.flush(); 

            
            //actualizar orden intermientoXproducto. cant ingresada
            orXProd.setCantidadIngresada(orXProd.getCantidadIngresada()+pallets.size());
            session.update(orXProd);
            session.flush(); 

            //cambiar estado si se interna toda la orden
            int x = orXProd.getCantidad();
            int y = orXProd.getCantidadIngresada();
            if (x == y){
                orXProd.getOrdenInternamiento().setEstado(EntityState.InternmentOrders.INTERNADA.ordinal());
                session.update(orXProd.getOrdenInternamiento());
            }
            session.flush();
            
            // actualizar pallets registrados y ubicados del producto
            prod.setPalletsRegistrados(prod.getPalletsRegistrados()-pallets.size());
            prod.setPalletsUbicados(prod.getPalletsUbicados()+ pallets.size());
            session.update(prod);
            session.flush(); 
            //disminuir ubicaciones libres en almacen
            alm.setUbicLibres(alm.getUbicLibres()-pallets.size());
            session.update(alm);
            session.flush(); 
            //ingresar entrada en kardex
            session.save(kardex);
            session.flush();     
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

    public int internNPalletsNoOrder(ArrayList<Pallet> pallets, Kardex kardex ) {
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Almacen alm = pallets.get(0).getUbicacion().getRack().getAlmacen();
            Producto prod = pallets.get(0).getProducto();
            for (Pallet p : pallets){
                if (p.getUbicacion() != null){
                    //Actualizar ubicacion de pallets
                    session.update(p);
                    //actualizar estado en ubicaciones
                    Ubicacion ub = p.getUbicacion();
                    ub.setEstado(EntityState.Spots.OCUPADO.ordinal());
                    session.update(ub);                    
                }
            }
            session.flush(); 
            // actualizar pallets registrados y ubicados del producto
            prod.setPalletsRegistrados(prod.getPalletsRegistrados()-pallets.size());
            prod.setPalletsUbicados(prod.getPalletsUbicados()+ pallets.size());
            session.update(prod);
            session.flush(); 
            //disminuir ubicaciones libres en almacen
            alm.setUbicLibres(alm.getUbicLibres()-pallets.size());
            session.update(alm);
            session.flush(); 
            //ingresar entrada en kardex
            session.save(kardex);
            session.flush();     
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
    
    public int liberarPorCreado(ArrayList<Pallet> pallets, OrdenInternamientoXProducto orXProd, Kardex kardex ) {
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        Almacen alm = null;
        try {            
            trns=session.beginTransaction();
            if (pallets.get(0).getUbicacion()!=null)
                alm = pallets.get(0).getUbicacion().getRack().getAlmacen();
            Producto prod = pallets.get(0).getProducto();
            for (Pallet p : pallets){
                if (p.getUbicacion() != null){
                    //Actualizar ubicacion de pallets a null
                    //actualizar estado en ubicaciones liberado
                    Ubicacion ub = p.getUbicacion();
                    ub.setEstado(EntityState.Spots.LIBRE.ordinal());
                    p.setUbicacion(null);
                    session.update(p);
                    session.update(ub);                    
                }
            }
            session.flush(); 
            
            //actualizar orden intermientoXproducto. cant ingresada
            if (orXProd != null){
                for (Pallet p : pallets){
                        orXProd.setCantidadIngresada(orXProd.getCantidadIngresada()-pallets.size());
                        session.update(orXProd);
                        session.flush(); 
                }                
            }

            //cambiar estado si la orden internada disminuyo
            if (orXProd != null){
                int x = orXProd.getCantidad();
                int y = orXProd.getCantidadIngresada();
                if ((y < x) && (orXProd.getOrdenInternamiento().getEstado() ==EntityState.InternmentOrders.INTERNADA.ordinal())){
                    orXProd.getOrdenInternamiento().setEstado(EntityState.InternmentOrders.REGISTRADA.ordinal());
                    session.update(orXProd.getOrdenInternamiento());
                }
                session.flush();                
            }
            
            // actualizar pallets registrados y ubicados del producto
            prod.setPalletsRegistrados(prod.getPalletsRegistrados()+pallets.size());
            prod.setPalletsUbicados(prod.getPalletsUbicados()- pallets.size());
            session.update(prod);
            session.flush(); 
            //aumentar ubicaciones libres en almacen
            if (alm != null){
                alm.setUbicLibres(alm.getUbicLibres()+pallets.size());
                session.update(alm);
                session.flush();                
            }
            //ingresar entrada en kardex
            session.save(kardex);
            session.flush();     
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
    
    public int liberarPorRotoOVencido(ArrayList<Pallet> pallets, OrdenInternamientoXProducto orXProd, Kardex kardex ) {
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        Almacen alm = null;
        try {            
            trns=session.beginTransaction();
            if (pallets.get(0).getUbicacion()!= null)
                alm = pallets.get(0).getUbicacion().getRack().getAlmacen();
            Producto prod = pallets.get(0).getProducto();
            for (Pallet p : pallets){
                if (p.getUbicacion() != null){
                    //Actualizar ubicacion de pallets a null
                    //actualizar estado en ubicaciones liberado
                    Ubicacion ub = p.getUbicacion();
                    ub.setEstado(EntityState.Spots.LIBRE.ordinal());
                    p.setUbicacion(null);
                    session.update(p);
                    session.update(ub);                    
                }
            }
            session.flush(); 

            
            //actualizar orden intermientoXproducto. cant ingresada
            //si estuvo ubicado no debe reducir su cantidad ingresada
            /* 
            if (orXProd != null){
                for (Pallet p : pallets){
                        orXProd.setCantidadIngresada(orXProd.getCantidadIngresada()-pallets.size());
                        session.update(orXProd);
                        session.flush(); 
                }                
            }*/


            //cambiar estado si la orden internada disminuyo
            /*if (orXProd != null){
                int x = orXProd.getCantidad();
                int y = orXProd.getCantidadIngresada();
                if ((y < x) && (orXProd.getOrdenInternamiento().getEstado() ==EntityState.InternmentOrders.INTERNADA.ordinal())){
                    orXProd.getOrdenInternamiento().setEstado(EntityState.InternmentOrders.REGISTRADA.ordinal());
                    session.update(orXProd.getOrdenInternamiento());
                }
                session.flush();                
            }
            */
            
            // disminuir pallets ubicados y stock logico 
            //prod.setPalletsRegistrados(prod.getPalletsRegistrados()+pallets.size());
            prod.setPalletsUbicados(prod.getPalletsUbicados()- pallets.size());
            prod.setStockLogico(prod.getStockLogico()-pallets.size());
            session.update(prod);
            session.flush(); 
            //aumentar ubicaciones libres en almacen
            if (alm != null){
                alm.setUbicLibres(alm.getUbicLibres()+pallets.size());
                session.update(alm);
                session.flush();                
            }
            //ingresar entrada en kardex
            session.save(kardex);
            session.flush();     
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
    
    
    
}
