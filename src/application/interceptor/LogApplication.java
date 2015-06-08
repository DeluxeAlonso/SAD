/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.interceptor;

import base.interceptor.ILogRepository;
import entity.Log;
import infraestructure.interceptor.HInterceptor;
import java.util.ArrayList;

/**
 *
 * @author Nevermade
 */
public class LogApplication {
    private ILogRepository logRepository;
    public LogApplication(){
        this.logRepository= new HInterceptor();
    }
    public ArrayList<Log> getAllLog(){
        ArrayList<Log> log = null;
        try {
            log = logRepository.queryAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return log;
    }
}
