/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.interceptors;

import client.general.MainView;
import entity.Almacen;
import entity.Despacho;
import entity.GuiaRemision;
import entity.Kardex;
import entity.OrdenInternamiento;
import entity.Pedido;
import entity.Perfil;
import entity.Usuario;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;

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
public class HInterceptor implements PostUpdateEventListener, PostDeleteEventListener, PostInsertEventListener {

    Usuario user = MainView.user;

    @Override
    public void onPostUpdate(PostUpdateEvent pue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister ep) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onPostDelete(PostDeleteEvent pde) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onPostInsert(PostInsertEvent pie) {

        Object entity = pie.getEntity();
        if (entity instanceof Almacen) {

        } else if (entity instanceof GuiaRemision) {

        } else if (entity instanceof OrdenInternamiento) {

        } else if (entity instanceof Pedido) {

        } else if (entity instanceof Perfil) {

        } else if (entity instanceof Usuario) {
            Usuario user= (Usuario) entity;
            
        } else if (entity instanceof Despacho) {

        } else if (entity instanceof Kardex) {

        }
    }

}
