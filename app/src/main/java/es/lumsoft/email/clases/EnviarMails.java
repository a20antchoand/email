

package es.lumsoft.email.clases;
import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EnviarMails {

    public EnviarMails (Context context, String from, String to, String titol, String missatge) {
        // Canviar els paràmetres
        String[] textDividit = from.split("@");

        final String password = textDividit[0] + "_M09";
        final String host = "mail.m09.alumnes.inspedralbes.cat";
        final String port = "587";
        final String username = from; // correct password for gmail id

        System.out.println("Comencem!!!");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        // SSL Port
        properties.put("mail.smtp.port", port);
        // enable authentication
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        // SSL Factory
        //properties.put("mail.smtp.socketFactory.class",
        //        "javax.net.ssl.SSLSocketFactory");
        // creating Session instance referenced to
        // Authenticator object to pass in
        // Session.getInstance argument
        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {

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
            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            message.setSubject(titol);
            message.setText(missatge);

            // Send message
            System.out.println("Inici de l'enviament... (paciència que pot trigar uns 10 segons.... )");
            Transport.send(message);
            System.out.println("Enviat!!!");
            Toast.makeText(context, "S'ha enviat el correu correctament!", Toast.LENGTH_SHORT).show();
        } catch (MessagingException mex) {
            mex.printStackTrace();
            Toast.makeText(context, "Error al enviar el correu!", Toast.LENGTH_SHORT).show();

        }
    }
}