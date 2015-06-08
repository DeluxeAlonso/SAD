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
import java.util.Date;

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
    
    public ArrayList<Log> getLog(Log log, Date dateIniMod, Date dateFinMod,Date dateIniCr, Date dateFinCr){
       ArrayList<Log> logs = null;
        try {
            logs = logRepository.queryLog(log,dateIniMod,dateFinMod, dateIniCr, dateFinCr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs;
    }
}
