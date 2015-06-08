/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.interceptor;

import base.IRepository;
import entity.Log;

/**
 *
 * @author Nevermade
 */
public interface ILogRepository extends IRepository<Log>{
    Log queryLog(Log object);
}
