/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.interceptor;

import base.IRepository;
import entity.Log;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Nevermade
 */
public interface ILogRepository extends IRepository<Log> {

    ArrayList<Log> queryLog(Log object, Date dateI, Date dateF,Date dateICr ,Date dateFCr);
}
