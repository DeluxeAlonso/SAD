/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entity.Usuario;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
/**
 *
 * @author Nevermade
 */
public class MailUtil {

    private static String USER_NAME = "sad.mail.dp1";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "sad.grupo1"; // GMail password
    private static String RECIPIENT = "";

    public static boolean sendPasswordRecovery(Usuario user, String newPass) {
        String from = USER_NAME+"@gmail.com";        
        RECIPIENT = user.getCorreo();
        String to = RECIPIENT; // list of recipient email addresses
        String subject = "Recuperación de Contraseña.";
        String body = "";

        body = setBody(user, newPass);

        return sendFromGMail(from,to, subject, body);
    }

    private static String setBody(Usuario user, String newPass) {
        String body = "";

        body += "<div class=\"content\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; max-width: 600px; display: block; margin: 0 auto; padding: 20px;\">";
        body += "<table class=\"main\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; border-radius: 3px; background-color: #fff; margin: 0; border: 1px solid #e9e9e9;\" bgcolor=\"#fff\"><tbody><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"alert alert-warning\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 16px; vertical-align: top; color: #fff; font-weight: 500; text-align: center; border-radius: 3px 3px 0 0; background-color: #FF9F00; margin: 0; padding: 20px;\" align=\"center\" bgcolor=\"#FF9F00\" valign=\"top\">";
        body += "Recuperación de Contraseña";
        body += "</td>";
        body += "</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-wrap\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 20px;\" valign=\"top\">";
        body += "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><tbody><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">";
        body += "Estimado(a) " + user.getNombre() + " " + user.getApellidoPaterno() + ":";
        body += "</td>";
        body += "</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">";
        body += "Su nueva contraseña es: <strong style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">" + newPass + "</strong>. Por favor, vuelva a iniciar sesión.";
        body += "</td>";
        body += "</tr>";
        body += "<tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">";
        body += "Gracias.";
        body += "</td>";
        body += "</tr></tbody></table></td>";
        body += "</tr></tbody></table><div class=\"footer\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; width: 100%; clear: both; color: #999; margin: 0; padding: 20px;\">";
        body += "<table width=\"100%\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><tbody><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"aligncenter content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 12px; vertical-align: top; color: #999; text-align: center; margin: 0; padding: 0 0 20px;\" align=\"center\" valign=\"top\"><a href=\"#\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 12px; color: #999; text-decoration: underline; margin: 0;\">Sistema de Almacenes y Despachos (SAD)</a></td>";
        body += "</tr></tbody></table></div></div>";

        return body;
    }

    private static boolean sendFromGMail(String from, String to, String subject, String body) {
        Properties props = new Properties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");
        /*props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");*/
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        //props.put("mail.smtp.connectiontimeout", "15000");
        //props.put("mail.smtp.timeout", "15000");
        props.put("mail.smtp.ssl.trust", host);

        //Session session = Session.getInstance(props,new GMailAuthenticator(from, pass));
        Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(USER_NAME,PASSWORD);
				}
			});
        //Session session = Session.getDefaultInstance(props, null);

        try {
            MimeMessage  message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body, "utf-8", "html");
           /* Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);*/
            Transport.send(message);
            
            /*Transport transport=session.getTransport("smtp");
            transport.connect(host,from,pass);*/
            /*transport.sendMessage(message, message.getAllRecipients());
            transport.close();*/
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } 
        return true;
    }
}

