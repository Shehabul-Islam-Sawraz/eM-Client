package Controller.EmailServices;

import Model.EmailAccount;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import sample.EmailManager;

import javax.mail.*;

public class LoginService extends Service<EmailLoginServiceResult> {
    EmailAccount emailAccount;
    EmailManager emailManager;

    public LoginService(EmailAccount emailAccount, EmailManager emailManager) {
        this.emailAccount = emailAccount;
        this.emailManager = emailManager;
    }

    private EmailLoginServiceResult login(){
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication  getPasswordAuthentication() {
                return new PasswordAuthentication(emailAccount.getEmailAddress(), emailAccount.getPassword());
            }
        };
        try {
            Session session = Session.getInstance(emailAccount.getProperties(), authenticator);
            emailAccount.setSession(session);
            Store store = session.getStore("imaps");
            store.connect(emailAccount.getProperties().getProperty("incomingHost"),
                    emailAccount.getEmailAddress(),
                    emailAccount.getPassword());
            emailAccount.setStore(store);
            emailManager.addEmailAccount(emailAccount);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            return EmailLoginServiceResult.FAILED_BY_NETWORK;
        } catch (AuthenticationFailedException e) {
            e.printStackTrace();
            return  EmailLoginServiceResult.FAILED_BY_CREDENTIALS;
        } catch (MessagingException e) {
            e.printStackTrace();
            return EmailLoginServiceResult.FAILED_BY_UNEXPECTED_ERROR;
        } catch (Exception e) {
            e.printStackTrace();
            return  EmailLoginServiceResult.FAILED_BY_UNEXPECTED_ERROR;
        }
        return EmailLoginServiceResult.SUCCESS;
    }

    @Override
    protected Task<EmailLoginServiceResult> createTask() {
        return new Task<EmailLoginServiceResult>() {
            @Override
            protected EmailLoginServiceResult call() throws Exception {
                return login();
            }
        };
    }
}
