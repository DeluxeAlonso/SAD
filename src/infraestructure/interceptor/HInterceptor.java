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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import util.Constants;
import util.EntityState;
import util.HibernateUtil;
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

    private Set inserts = new HashSet();
    private Set updates = new HashSet();

    @Override
    public void postFlush(Iterator entities) {
        try {
            for (Iterator it = inserts.iterator(); it.hasNext();) {
                handleAuditInsert(it.next(), 1);
            }

            for (Iterator it = updates.iterator(); it.hasNext();) {
                handleAuditInsert(it.next(), 2);
            }
        } finally {
            inserts.clear();
            updates.clear();
        }
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id,
            Object[] currentState, Object[] previousState,
            String[] propertyNames, Type[] types)
            throws CallbackException {

        if (belongsToMasters(entity)) {
            updates.add(entity);
        }
        return false;

    }

    @Override
    public boolean onSave(Object entity, Serializable id,
            Object[] state, String[] propertyNames, Type[] types)
            throws CallbackException {

        if (belongsToMasters(entity)) {
            inserts.add(entity);
        }
        return false;

    }

    private boolean belongsToMasters(Object entity) {
        if (entity instanceof Almacen) {
            return true;

        } else if (entity instanceof GuiaRemision) {
            return true;
        } else if (entity instanceof OrdenInternamiento) {
            return true;

        } else if (entity instanceof Pedido) {
            return true;

        } else if (entity instanceof Perfil) {
            return true;

        } else if (entity instanceof Usuario) {
            return true;

        } else if (entity instanceof Despacho) {
            return true;

        } else if (entity instanceof Kardex) {
            return true;
        }
        return false;
    }

    private void handleAuditInsert(Object entity, int mode) {

        Log updLog = null;
        Log log = new Log();

        if (entity instanceof Almacen) {
            Almacen a = (Almacen) entity;

            if (mode == 1) {
                log.setIdObjeto(a.getId().toString());
                log.setTipo(EntityState.Master.Almacen.ordinal());
            } else {
                updLog = queryById(a.getId().toString(), EntityState.Master.Almacen.ordinal());
            }

        } else if (entity instanceof GuiaRemision) {
            GuiaRemision g = (GuiaRemision) entity;

            if (mode == 1) {
                log.setIdObjeto(g.getId().toString());
                log.setTipo(EntityState.Master.Guia_Remision.ordinal());
            } else {
                updLog = queryById(g.getId().toString(), EntityState.Master.Almacen.ordinal());
            }
        } else if (entity instanceof OrdenInternamiento) {
            OrdenInternamiento o = (OrdenInternamiento) entity;

            if (mode == 1) {
                log.setIdObjeto(o.getId().toString());
                log.setTipo(EntityState.Master.Orden_Internamiento.ordinal());
            } else {
                updLog = queryById(o.getId().toString(), EntityState.Master.Almacen.ordinal());
            }

        } else if (entity instanceof Pedido) {
            Pedido p = (Pedido) entity;

            if (mode == 1) {
                log.setIdObjeto(p.getId().toString());
                log.setTipo(EntityState.Master.Pedido.ordinal());
            } else {
                updLog = queryById(p.getId().toString(), EntityState.Master.Almacen.ordinal());
            }

        } else if (entity instanceof Perfil) {
            Perfil p = (Perfil) entity;

            if (mode == 1) {
                log.setIdObjeto(p.getId().toString());
                log.setTipo(EntityState.Master.Perfil.ordinal());
            } else {
                updLog = queryById(p.getId().toString(), EntityState.Master.Almacen.ordinal());
            }

        } else if (entity instanceof Usuario) {
            Usuario user_modifier = (Usuario) entity;

            if (mode == 1) {
                log.setIdObjeto(user_modifier.getId());
                log.setTipo(EntityState.Master.Usuario.ordinal());
            } else {
                updLog = queryById(user_modifier.getId().toString(), EntityState.Master.Almacen.ordinal());
            }

        } else if (entity instanceof Despacho) {
            Despacho d = (Despacho) entity;

            if (mode == 1) {
                log.setIdObjeto(d.getId().toString());
                log.setTipo(EntityState.Master.Despacho.ordinal());
            } else {
                updLog = queryById(d.getId().toString(), EntityState.Master.Almacen.ordinal());
            }

        } else if (entity instanceof Kardex) {
            Kardex k = (Kardex) entity;

            if (mode == 1) {
                log.setIdObjeto(String.valueOf(k.getId().getId()));
                log.setTipo(EntityState.Master.Kardex.ordinal());
            } else {
                updLog = queryById(k.getId().toString(), EntityState.Master.Almacen.ordinal());
            }
        }
        if (mode == 1) {
            if (log.getIdObjeto() != null && !log.getIdObjeto().equals("")) {
                Calendar cal = Calendar.getInstance();
                log.setFechaCreacion(cal.getTime());
                log.setFechaActualizacion(cal.getTime());
                log.setIp(Constants.currentIP);
                log.setMac(Constants.currentMac);
                log.setUsuarioByUsuarioActualizador(MainView.user);
                log.setUsuarioByUsuarioCreador(MainView.user);
                insert(log);
            }
        } else {
            if (updLog != null) {
                Calendar cal = Calendar.getInstance();
                updLog.setFechaActualizacion(cal.getTime());
                updLog.setUsuarioByUsuarioActualizador(MainView.user);
                update(updLog);
            }
        }
    }

    @Override
    public int insert(Log object) {

        //Transaction trns = null;
        Session session = Tools.getSessionInstance();
        //try {            
        //trns=session.beginTransaction();
        session.save(object);
        /*session.getTransaction().commit();
         } catch (RuntimeException e) {
         if (trns != null) {
         trns.rollback();
         }
         e.printStackTrace();
         return -1;
         } */
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
            for (Log l : log) {
                Hibernate.initialize(l.getUsuarioByUsuarioActualizador());
                Hibernate.initialize(l.getUsuarioByUsuarioCreador());
            }
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

        /*Session session = Tools.getSessionInstance();
         String hql = "update Log set fecha_actualizacion=:fecha where id=:id";
         Query q = session.createQuery(hql);
         q.setParameter("fecha", object.getFechaActualizacion());
         q.setParameter("id", object.getId());
         /*if (object.getUsuarioByUsuarioActualizador() != null) {
         q.setParameter("user", object.getUsuarioByUsuarioActualizador().getId());
         } else {
         q.setParameter("user", null);
         }*/
        //q.executeUpdate();
        //Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {
            //trns=session.beginTransaction();
            session.update(object);
            updates.clear();
            session.flush();
            /*session.getTransaction().commit();*/
        } catch (Exception e) {
            /*if (trns != null) {
             trns.rollback();
             }*/
            e.printStackTrace();
            return -1;
        }
        return 1;

    }

    @Override
    public Log queryById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Log queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Log queryById(String id, int master) {
        //Transaction trns = null;
        Session session = Tools.getSessionInstance();
        Log log = null;
        String hql
                = "from Log where id_objeto=:id and tipo=:master";
        try {

            //session.beginTransaction();
            Query q = session.createQuery(hql);
            q.setParameter("id", id);
            q.setParameter("master", master);
            log = (Log) q.uniqueResult();
            //Hibernate.initialize(log.getUsuarioByUsuarioActualizador());
            //Hibernate.initialize(log.getUsuarioByUsuarioCreador());
            //session.getTransaction().commit();
        } catch (RuntimeException e) {
            /*if (trns != null) {
             trns.rollback();
             }*/
            e.printStackTrace();
        }
        return log;
    }

    @Override
    public ArrayList<Log> queryLog(Log object, Date dateIMod, Date dateFMod, Date dateICr, Date dateFCr) {
        String hql = "from Log "
                + "where (:usuario_creador is null or usuario_creador=:usuario_creador) and "
                + "(:usuario_actualizador is null or usuario_actualizador=:usuario_actualizador) and "
                + "(:id_objeto is null or id_objeto=:id_objeto) and "
                + "(:tipo is null or tipo=:tipo) and "
                + "(:fechaIniMod is null or :fechaFinMod is null or fecha_actualizacion between :fechaIniMod and :fechaFinMod) and "
                + "(:fechaIniCr is null or :fechaFinCr is null or fecha_creacion between :fechaIniCr and :fechaFinCr)";
        ArrayList<Log> logs = null;

        Transaction trns = null;
        Session session = Tools.getSessionInstance();
        try {
            trns = session.beginTransaction();
            Query q = session.createQuery(hql);
            if (object.getUsuarioByUsuarioActualizador() != null) {
                q.setParameter("usuario_actualizador", object.getUsuarioByUsuarioActualizador().getId());
            } else {
                q.setParameter("usuario_actualizador", null);
            }
            if (object.getUsuarioByUsuarioCreador() != null) {
                q.setParameter("usuario_creador", object.getUsuarioByUsuarioCreador().getId());
            } else {
                q.setParameter("usuario_creador", null);
            }
            if (!object.getIdObjeto().equals("")) {
                q.setParameter("id_objeto", object.getIdObjeto());
            } else {
                q.setParameter("id_objeto", null);
            }

            q.setParameter("tipo", object.getTipo());
            q.setParameter("fechaIniMod", dateIMod);
            q.setParameter("fechaFinMod", dateFMod);
            q.setParameter("fechaIniCr", dateICr);
            q.setParameter("fechaFinCr", dateFCr);
            logs = (ArrayList<Log>) q.list();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        }
        return logs;
    }
}
