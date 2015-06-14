/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.question;

import base.question.IQuestionRepository;
import entity.PreguntaSecreta;
import infraestructure.question.QuestionRepository;
import java.util.ArrayList;

/**
 *
 * @author Nevermade
 */
public class QuestionApplication {
    private IQuestionRepository questionRepository;
    public QuestionApplication(){
        this.questionRepository=new QuestionRepository();
    }
    public ArrayList<PreguntaSecreta>getAllQuestions(){
        ArrayList<PreguntaSecreta> questions=null;
        try{
            questions=questionRepository.queryAll();
        }catch(Exception e){
            e.printStackTrace();
        }
        return questions;
    }
}
