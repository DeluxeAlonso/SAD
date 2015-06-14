/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infraestructure.question;

import base.question.IQuestionRepository;
import entity.PreguntaSecreta;
import entity.Sesion;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Tools;

/**
 *
 * @author Nevermade
 */
public class QuestionRepository implements IQuestionRepository{

    @Override
    public int insert(PreguntaSecreta object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(PreguntaSecreta object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<PreguntaSecreta> queryAll() {
        Transaction trns = null;
        String hql="from PreguntaSecreta";
        ArrayList<PreguntaSecreta> questions= null;
        Session session = Tools.getSessionInstance();
        try {            
            trns=session.beginTransaction();
            Query q=session.createQuery(hql);            
            questions=(ArrayList<PreguntaSecreta>)q.list();                      
            session.getTransaction().commit();
            
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
            return null;
        }    
        return questions;
    }

    @Override
    public int update(PreguntaSecreta object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PreguntaSecreta queryById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PreguntaSecreta queryById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
