package org.lms.Notificiation.Services;

import javax.mail.*;
import java.util.Date;
import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.lms.Notificiation.DTOs.EmailObject;
import org.springframework.stereotype.Service;

// this is made public for future scalabillity -> will be isolated from the notification module and used globally
@Service
public class EmailService{
    
    public void sendEmail(EmailObject email) throws MessagingException{
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
           Properties props = System.getProperties();
           props.setProperty("mail.smtp.host", "smtp.gmail.com");
           props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
           props.setProperty("mail.smtp.socketFactory.fallback", "false");
           props.setProperty("mail.smtp.port", "465");
           props.setProperty("mail.smtp.socketFactory.port", "465");
           props.put("mail.smtp.starttls.required", "true");
           props.put("mail.smtp.ssl.protocols", "TLSv1.2");
           props.put("mail.smtp.auth", "true");
           props.put("mail.debug", "true");
           props.put("mail.store.protocol", "pop3");
           props.put("mail.transport.protocol", "smtp");

           final String username = "lmsmailproject@gmail.com";
           final String password = "zqkdstiwgwiscsvp";

           try{
             Session session = Session.getDefaultInstance(props, 
                                 new Authenticator(){
                                    protected PasswordAuthentication getPasswordAuthentication() {
                                       return new PasswordAuthentication(username, password);
                                    }});
             Message msg = new MimeMessage(session);
             msg.setFrom(new InternetAddress("lmsmailproject@gmail.com"));
             msg.setRecipients(Message.RecipientType.TO, 
                               InternetAddress.parse(email.getTo(),false));
             msg.setSubject(email.getHeader());
             msg.setText(email.getMessage());
             msg.setSentDate(new Date());
             Transport.send(msg);
           }catch (MessagingException e){ 
             System.out.println(e);
           }
    }
}
