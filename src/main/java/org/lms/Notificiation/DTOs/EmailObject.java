package org.lms.Notificiation.DTOs;

import java.util.Properties;
import javax.mail.Session;

public class EmailObject {
    private int userId;
    private String to;
    private String from = "houda.hotmail@gmail.com";
    private String header;
    private String message;
    private Properties props = System.getProperties();
    private Session session;

    public EmailObject(String to, String header, String message){
        this.to = to;
        this.message = message;
        this.header = header;

        // props.put("mail.smtp.starttls.enable", "true"); 
        // props.put("mail.smtp.ssl.protocols", "TLSv1.2,TLSv1.3");
        // props.put("mail.smtp.ssl.ciphersuites", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384");
        // props.put("mail.smtp.host", "your_smtp_host");
        // props.put("mail.smtp.port", 587); 
        // props.put("mail.smtp.auth", "true");
        // props.put("mail.smtp.username", "your_username");
        // props.put("mail.smtp.password", "your_password");


        // props.put("mail.smtp.host", "smtp.gmail.com");
        // props.put("mail.smtp.port", "587");
        // props.put("mail.smtp.auth", "true");
        // props.put("mail.smtp.starttls.enable", "true");

        // session = Session.getInstance(props, new javax.mail.Authenticator() {
        //     protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
        //         return new javax.mail.PasswordAuthentication(from, "f12345678_");
        //     }
        // });
    }

    public EmailObject(String to, String header, String message, Integer userId){
        this.to = to;
        this.message = message;
        this.header = header;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}