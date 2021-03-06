/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.lumsoft.email.clases;

import android.os.StrictMode;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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

    List<Email> emails = new ArrayList<>();

    public RebreMails () {

        try {
            String host = "mail.m09.alumnes.inspedralbes.cat";
            String port = "143";
            String username = "grup01@m09.alumnes.inspedralbes.cat";
            String password = "grup01_M09";

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

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

            for (int i = messages.length-1; i >= 0; i--) {

                Email email = new Email();

                System.out.println("===========MSJ:" + i + "===========");
                Flags f = messages[i].getFlags();
                if (f.contains(Flags.Flag.SEEN)) {
                    email.setEstat("[Llegit]  ");
                } else {
                    email.setEstat("[No llegit]  ");
                }
                Address[] direcciones = messages[i].getFrom();
                for (int j = 0; direcciones != null && j < direcciones.length; j++) {
                    email.setFrom(direcciones[j].toString());
                }
                direcciones = messages[i].getRecipients(Message.RecipientType.TO);
                for (int j = 0; direcciones != null && j < direcciones.length; j++) {
                    email.setTo(direcciones[j].toString());
                }
                direcciones = messages[i].getRecipients(Message.RecipientType.CC);
                for (int j = 0; direcciones != null && j < direcciones.length; j++) {
                    System.out.println("Cc: " + direcciones[j].toString());
                }
                direcciones = messages[i].getRecipients(Message.RecipientType.BCC);
                for (int j = 0; direcciones != null && j < direcciones.length; j++) {
                    System.out.println("Bcc: " + direcciones[j].toString());
                }
                email.setSubject(messages[i].getSubject());
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm");
                email.setDate(sdf.format(messages[i].getSentDate()) + "");
                System.out.println("Content Type: " + messages[i].getContentType());
                try {
                    email.setContent(ExtractClearText(messages[i]));
                } catch (IOException ex) {
                    Logger.getLogger(RebreMails.class.getName()).log(Level.SEVERE, null, ex);
                }

                emails.add(email);

            }

            inbox.close(false);
            store.close();
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(RebreMails.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(RebreMails.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
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
