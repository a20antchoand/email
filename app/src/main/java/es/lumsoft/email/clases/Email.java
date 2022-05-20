package es.lumsoft.email.clases;

public class Email {

    String estat;
    String from;
    String to;
    String subject;
    String date;
    String content;

    public Email() {
    }

    public Email(String estat, String from, String to, String subject, String date, String content) {
        this.estat = estat;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.date = date;
        this.content = content;
    }

    public String getEstat() {
        return estat;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
