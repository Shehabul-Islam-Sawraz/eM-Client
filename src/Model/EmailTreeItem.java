package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class EmailTreeItem<String> extends TreeItem<String> {
    private String name;
    private ObservableList<EmailMessage> emailMessages;
    private int unreadMessagesCount;

    public EmailTreeItem(String name) {
        super(name);
        this.name = name;
        this.emailMessages = FXCollections.observableArrayList();
    }

    public ObservableList<EmailMessage> getEmailMessages(){
        return  emailMessages;
    }

    public void addEmail(Message message) {
        EmailMessage emailMessage = fetchEmail(message);
        emailMessages.add(emailMessage);
    }

    public void addEmailToTop(Message message) {
        EmailMessage emailMessage = fetchEmail(message);
        emailMessages.add(0, emailMessage);
    }

    private EmailMessage fetchEmail(Message message) {
        boolean messageIsRead = false;
        try {
            messageIsRead = message.getFlags().contains(Flags.Flag.SEEN);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        EmailMessage emailMessage = null;
        try {
            emailMessage = new EmailMessage(
                    message.getSubject(),
                    message.getFrom()[0].toString(),
                    message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(),
                    message.getSize(),
                    message.getSentDate(),
                    messageIsRead,
                    message
            );
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        //emailMessages.add(emailMessage);
        if(!messageIsRead){
            incrementMessagesCount();
        }
        /*try {
            System.out.println("Added to " + name + " " + message.getSubject());
        } catch (MessagingException e) {
            e.printStackTrace();
        }*/
        return emailMessage;
    }

    public void incrementMessagesCount(){
        unreadMessagesCount++;
        updateName();
    }

    public void decrementMessagesCount(){
        unreadMessagesCount--;
        updateName();
    }

    private void updateName(){
        if(unreadMessagesCount > 0){
            this.setValue((String)(name + "(" + unreadMessagesCount + ")"));
        } else {
            this.setValue(name);
        }
    }

}
