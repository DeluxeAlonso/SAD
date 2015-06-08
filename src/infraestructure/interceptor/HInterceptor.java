/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.interceptor;

import base.interceptor.ILogRepository;
import client.general.MainView;
import entity.Almacen;
import entity.Despacho;
import entity.GuiaRemision;
import entity.Kardex;
import entity.Log;
import entity.OrdenInternamiento;
import entity.Pedido;
import entity.Perfil;
import entity.Usuario;
import java.io.Serializable;
import org.hibernate.type.Type;

import java.util.ArrayList;
import java.util.Calendar;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.EntityState;
import util.Tools;

/**
 *
 * @author Nevermade
 */
/*
 almacen
 guia remision
 orden internamiento
 pedido
 perfil
 usuario
 despacho
 kardex
 */
public class HInterceptor extends EmptyInterceptor implements ILogRepository {

    Usuario user = MainView.user;

    @Override
    public boolean onSave(Object entity,
            Serializable id,
            Object[] state,
            String[] propertyNames,
            Type[] types) {
        handleAuditInsert(entity,1);

        return false;
    }

    @Override
    public boolean onFlushDirty(Object entity,
            Serializable id,
            Object[] currentState,
            Object[] previousState,
            String[] propertyNames,
            Type[] types) {

        handleAuditInsert(entity,2);
        return false;
    }

    @Override
    public void onDelete(Object entity,
            Serializable id,
            Object[] state,
            String[] propertyNames,
            Type[] types) {

        handleAuditInsert(entity,3);
    }

    private void handleAuditInsert(Object entity,int mode) {
        Log log = new Log();        
        Calendar cal=Calendar.getInstance();
        log.setFecha(cal.getTime());
        if (entity instanceof Almacen) {

        } else if (entity instanceof GuiaRemision) {

        } else if (entity instanceof OrdenInternamiento) {
            OrdenInternamiento o = (OrdenInternamiento) entity;
            log.setIdObjeto(o.getId().toString());
            log.setTipo(EntityState.Master.Orden_Internamiento.ordinal());

        } else if (entity instanceof Pedido) {
            Pedido p = (Pedido) entity;
            //log.setIdObjeto(p.getId().toString());
            //log.setTipo(EntityState.Master.Pedido.ordinal());

        } else if (entity instanceof Perfil) {
            Perfil p = (Perfil) entity;
            log.setIdObjeto(p.getId().toString());
            log.setTipo(EntityState.Master.Perfil.ordinal());

        } else if (entity instanceof Usuario) {
            Usuario user_modifier = (Usuario) entity;
            log.setIdObjeto(user_modifier.getId());
            log.setTipo(EntityState.Master.Usuario.ordinal());

        } else if (entity instanceof Despacho) {
            Despacho d = (Despacho) entity;
            //log.setIdObjeto(d.getId().toString());
            //log.setTipo(EntityState.Master.Despacho.ordinal());

        } else if (entity instanceof Kardex) {
            Kardex k = (Kardex) entity;
            log.setIdObjeto(String.valueOf(k.getId().getId()));

        }
        if (log.getIdObjeto() != null) {
            switch (mode){
                case 1:
                    log.setOperacion(EntityState.Operation.Insert.ordinal());
                    break;
                case 2:
                    log.setOperacion(EntityState.Operation.Update.ordinal());
                    break;
                case 3:
                    log.setOperacion(EntityState.Operation.Delete.ordinal());
                    break;
            }      
            log.setUsuario(user);
            insert(log);
        }
    }

    @Override
    public int insert(Log object) {
        Session session = Tools.getSessionInstance();
        session.save(object);
        return 1;

    }

    @Override
    public int delete(Log object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Log> queryAll() {
        String hql = "from Log";
        ArrayList<Log> log = null;

        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {
            trns = session.beginTransaction();
            Query q = session.createQuery(hql);
            log = (ArrayList<Log>) q.list();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return log;
    }

    @Override
    public int update(Log object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Log queryById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Log queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Log queryLog(Log object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
