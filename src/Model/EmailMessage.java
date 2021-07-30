package Model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.mail.Message;
import javax.mail.internet.MimeBodyPart;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmailMessage {
    private SimpleStringProperty subject;
    private SimpleStringProperty sender;
    private SimpleStringProperty recipient;
    private SimpleObjectProperty<EmailSizeModifier> size;
    private SimpleObjectProperty<Date> date;
    private boolean isRead;
    private Message message;

    private List<MimeBodyPart> attachmentList = new ArrayList<MimeBodyPart>();
    private boolean hasAttachments = false;

    public EmailMessage(String subject, String sender, String recipient, int size, Date date, boolean isRead, Message message){
        this.subject = new SimpleStringProperty(subject);
        this.sender = new SimpleStringProperty(sender);
        this.recipient = new SimpleStringProperty(recipient);
        this.size = new SimpleObjectProperty<EmailSizeModifier>(new EmailSizeModifier(size));
        this.date = new SimpleObjectProperty<Date>(date);
        this.isRead = isRead;
        this.message = message;
    }

    public String getSubject(){
        return this.subject.get();
    }
    public  String getSender(){
        return this.sender.get();
    }
    public String getRecipient(){
        return this.recipient.get();
    }
    public EmailSizeModifier getSize(){ return this.size.get(); }
    public Date getDate(){
        return this.date.get();
    }

    public boolean isRead() {
        return isRead;
    }
    public void setRead(boolean read) {
        this.isRead = read;
    }
    public Message getMessage(){
        return this.message;
    }
    public boolean getHasAttachments() {
        return hasAttachments;
    }
    public List<MimeBodyPart> getAttachmentList(){
        return attachmentList;
    }

    public void addAttachment(MimeBodyPart mimeBodyPart) {
        hasAttachments = true;
        attachmentList.add(mimeBodyPart);
        /*try {
            System.out.println("Added attach: " + mimeBodyPart.getFileName());
        } catch ( Exception e) {
            e.printStackTrace();
        }*/
    }
}
