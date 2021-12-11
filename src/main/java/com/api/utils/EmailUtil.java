package com.api.utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class EmailUtil {
    public static boolean sendEmail(String host, String port,
            final String email, final String password, String toAddress,
            String subject, String html) throws AddressException,
            MessagingException {
 
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
 
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        };
 
        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(email));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setContent(html, "text/html; charset=utf-8");
 
        try {
            // sends the e-mail
            Transport.send(msg);
        } catch (Exception e){
        	return false;
        }

        return true;
 
    }

    public static String getHtmlEmail(String path) {
        String html = "";
        try {
          File myObj = new File(path);
          Scanner myReader = new Scanner(myObj);
          while (myReader.hasNextLine()) {
            String line = myReader.nextLine();
            html += line;
          }
          myReader.close();
        } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
        };

        return html;
    }
}
