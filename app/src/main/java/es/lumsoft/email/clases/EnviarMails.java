package es.lumsoft.email.clases;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EnviarMails {

    final static String username = "grup01@m09.alumnes.inspedralbes.cat";
    final static String to = "grup01@m09.alumnes.inspedralbes.cat";
    final static String password = "grup01_M09";


    public static void enviarCorreu (String titol, String missatge) {

        // Get system properties
        Properties properties = System.getProperties();
        final String host = "mail.m09.alumnes.inspedralbes.cat";
        final String port = "587";
        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // SSL Port
        properties.put("mail.smtp.port", port);

        // enable authentication
        properties.put("mail.smtp.auth", "true");

        // SSL Factory
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");

        // creating Session instance referenced to
        // Authenticator object to pass in
        // Session.getInstance argument
        Session session = Session.getDefaultInstance(properties,
                new Authenticator() {

                    // override the getPasswordAuthentication
                    // method
                    protected PasswordAuthentication
                    getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        //compose the message
        try {
            // javax.mail.internet.MimeMessage class is mostly
            // used for abstraction.
            MimeMessage message = new MimeMessage(session);

            // header field of the header.
            message.setFrom(new InternetAddress(username));

            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            message.setSubject(titol);
            message.setText(missatge);

            // Send message
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

}
