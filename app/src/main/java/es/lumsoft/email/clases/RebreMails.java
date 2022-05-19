/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.lumsoft.email.clases;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author santi
 */
public class RebreMails {

    public static void main(String[] args) {

        try {
            String host = "mail.m09.alumnes.inspedralbes.cat";
            String port = "143";
            String username = "grup01@m09.alumnes.inspedralbes.cat";
            String password = "grup01_M09";


            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imap");
            props.setProperty("mail.imap.port", port);
            props.setProperty("mail.imap.host", host);
            props.setProperty("mail.imap.user", username);
            props.setProperty("mail.imap.password", password);

            Session session = Session.getInstance(props);
            Store store = session.getStore("imap");
            store.connect(host, username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message[] messages = inbox.getMessages();

            for (int i = 0; i < messages.length; i++) {
                System.out.println("===========MSJ:" + i + "===========");
                Flags f = messages[i].getFlags();
                if (f.contains(Flags.Flag.SEEN)) {
                    System.out.println("Ya leído");
                } else {
                    System.out.println("Aún no leído");
                }
                Address[] direcciones = messages[i].getFrom();
                for (int j = 0; direcciones != null && j < direcciones.length; j++) {
                    System.out.println("From: " + direcciones[j].toString());
                }
                direcciones = messages[i].getRecipients(Message.RecipientType.TO);
                for (int j = 0; direcciones != null && j < direcciones.length; j++) {
                    System.out.println("To: " + direcciones[j].toString());
                }
                direcciones = messages[i].getRecipients(Message.RecipientType.CC);
                for (int j = 0; direcciones != null && j < direcciones.length; j++) {
                    System.out.println("Cc: " + direcciones[j].toString());
                }
                direcciones = messages[i].getRecipients(Message.RecipientType.BCC);
                for (int j = 0; direcciones != null && j < direcciones.length; j++) {
                    System.out.println("Bcc: " + direcciones[j].toString());
                }
                System.out.println("Subject: " + messages[i].getSubject());
                System.out.println("Date: " + messages[i].getSentDate());
                System.out.println("Content Type: " + messages[i].getContentType());
                try {
                    System.out.println("Content: " + ExtractClearText(messages[i]));
                } catch (IOException ex) {
                    Logger.getLogger(RebreMails.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            inbox.close(false);
            store.close();
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(RebreMails.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(RebreMails.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String ExtractClearText(Message message) throws IOException, MessagingException {
        String result = "";

        if (message instanceof MimeMessage) {
            MimeMessage m = (MimeMessage) message;
            Object contentObject = m.getContent();
            if (contentObject instanceof Multipart) {
                BodyPart clearTextPart = null;
                BodyPart htmlTextPart = null;
                Multipart content = (Multipart) contentObject;
                int count = content.getCount();
                for (int i = 0; i < count; i++) {
                    BodyPart part = content.getBodyPart(i);
                    if (part.isMimeType("text/plain")) {
                        clearTextPart = part;
                        break;
                    } else if (part.isMimeType("text/html")) {
                        htmlTextPart = part;
                    }
                }

                if (clearTextPart != null) {
                    result = (String) clearTextPart.getContent();
                } else if (htmlTextPart != null) {
                    result = (String) htmlTextPart.getContent();
                }

            } else if (contentObject instanceof String) // a simple text message
            {
                result = (String) contentObject;
            } else // not a mime message
            {
                Logger.getLogger(RebreMails.class.getName()).log(Level.SEVERE, null, "not a mime part or multipart " + message.toString());
                result = null;
            }
        }
        return result;
    }
}
