package service.impl;

import domain.Email;
import service.ServiceMail;

import javax.faces.bean.ManagedBean;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@ManagedBean(name ="serviceMail")
public class ServiceMailImpl implements ServiceMail {

    public static final String PASSWORD = "khalidkhoya";

    public void sendMail(Email email){
        System.out.println(email.getFrom());
        final String to = email.getRecipient();//change accordingly
        String from = email.getFrom();
        String host = "localhost";//or IP address
        final String password= PASSWORD;
        //Get the session object
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.EnableSSL.enable","true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.setProperty("mail.smtp.user", to);
        props.setProperty("mail.smtp.password", "password");
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(to, password);
            }
        });

        //compose the message
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from,"farid"));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(email.getSubject());
            message.setText(email.getText());

            // Send message
            Transport.send(message);
            System.out.println("message sent successfully....");

        }catch (MessagingException mex) {mex.printStackTrace();} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
