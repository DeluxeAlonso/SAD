/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.transportunit;

import base.transportunit.ITransportUnitRepository;
import entity.Despacho;
import entity.GuiaRemision;
import entity.TipoUnidadTransporte;
import entity.UnidadTransporte;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Tools;

/**
 *
 * @author Alonso
 */
public class TransportUnitRepository implements ITransportUnitRepository{
    //Session session = Tools.getSessionInstance();
    
    @Override
    public Boolean createTransportUnit(UnidadTransporte transportUnit){
        Session session = Tools.getSessionInstance();
        Transaction trns = null; 
        try {            
            trns=session.beginTransaction();
            session.save(transportUnit);                      
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
    public Boolean updateTransportUnit(UnidadTransporte transportUnit){
        Session session = Tools.getSessionInstance();
        Transaction trns = null;
        try {            
            trns=session.beginTransaction();
            session.update(transportUnit);                      
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
    public Boolean loadTransportUnit(String filename){
        System.out.println(filename);
        Session session = Tools.getSessionInstance();
        String hql = "LOAD DATA LOCAL INFILE :filename INTO TABLE Unidad_Transporte FIELDS TERMINATED BY ',';";
        Transaction trns = null;
        try {            
            trns=session.beginTransaction(); 
            Query q = session.createSQLQuery(hql);
            q.setString("filename", filename);
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
    public ArrayList<UnidadTransporte> search(String plate, TipoUnidadTransporte type){
        Session session = Tools.getSessionInstance();
        String hql = "from UnidadTransporte where estado=1";
        if(type == null && !plate.equals("")){
            hql = "from UnidadTransporte where estado=1 and placa like :plate";
        }
        else if(type != null && plate.equals("")){
            hql = "from UnidadTransporte where estado=1 and id_tipo_unidad_transporte=:type";
        }
        else if(type != null && !plate.equals("")){
            hql = "from UnidadTransporte where estado=1 and placa like :plate and id_tipo_unidad_transporte=:type";
        }
        ArrayList<UnidadTransporte> unitTransports = new ArrayList<>();
        Transaction trns = null;
        try{
            trns = session.beginTransaction();
            Query q = session.createQuery(hql);
            if (!plate.equals(""))
                q.setParameter("plate", "%" + plate + "%");
            if (type != null)
                q.setParameter("type", type.getId());
            unitTransports = (ArrayList<UnidadTransporte>) q.list();
            session.getTransaction().commit();
        }
        catch (RuntimeException e){
            if(trns != null){
                trns.rollback();
            }
            e.printStackTrace();
        }
        return unitTransports;
    }
    
    
    
    @Override
    public int insert(UnidadTransporte object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(UnidadTransporte object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<UnidadTransporte> queryAll() {
        Session session = Tools.getSessionInstance();
        String hql = "from UnidadTransporte where estado=1";
        ArrayList<UnidadTransporte> unitTransports = new ArrayList<>();
        Transaction trns = null;
        try{
            trns = session.beginTransaction();
            Query q = session.createQuery(hql);
            unitTransports = (ArrayList<UnidadTransporte>) q.list();
            session.getTransaction().commit();
        }
        catch (RuntimeException e){
            if(trns != null){
                trns.rollback();
            }
            e.printStackTrace();
        }
        return unitTransports;
    }

    @Override
    public int update(UnidadTransporte object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UnidadTransporte queryById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UnidadTransporte queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<GuiaRemision> queryRemissionGuides(UnidadTransporte transportUnit, ArrayList<Despacho> deliveries) {
        String hql="from GuiaRemision g where (g.despacho.id in (select d.id from Despacho d where (d.unidadTransporte.id=:unidad) and d.id in (:delivery)))" ;
        ArrayList<GuiaRemision> remissionGuides= new ArrayList<>();
        ArrayList idList = new ArrayList();
        for(int i=0;i<deliveries.size();i++)
            idList.add(deliveries.get(i).getId());
        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameterList("delivery", idList);
            q.setParameter("unidad", transportUnit.getId());
            remissionGuides = (ArrayList<GuiaRemision>) q.list();          
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return remissionGuides; 
    }

    @Override
    public ArrayList<UnidadTransporte> queryByPlate(String plate) {
        Session session = Tools.getSessionInstance();
        String hql = "from UnidadTransporte where estado=1 and placa=:plate";
        ArrayList<UnidadTransporte> unitTransports = new ArrayList<>();
        Transaction trns = null;
        try{
            trns = session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("plate", plate);
            unitTransports = (ArrayList<UnidadTransporte>) q.list();
            session.getTransaction().commit();
        }
        catch (RuntimeException e){
            if(trns != null){
                trns.rollback();
            }
            e.printStackTrace();
        }
        return unitTransports;
    }

    
    
}
