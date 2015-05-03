/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 *
 * @author Nevermade
 */
public class Tools {
    
    public static String generatePassword(int length){
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32).substring(0,length);
    }
    public static Object setDefault(Object in, Object def){
        if(in==null)
            return def;
        return in;
    }
}
