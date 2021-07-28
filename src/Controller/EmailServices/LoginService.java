package Controller.EmailServices;

import Model.EmailAccount;
import sample.EmailManager;

import javax.mail.*;

public class LoginService {
    EmailAccount emailAccount;
    EmailManager emailManager;

    public LoginService(EmailAccount emailAccount, EmailManager emailManager) {
        this.emailAccount = emailAccount;
        this.emailManager = emailManager;
    }

    public EmailLoginServiceResult login(){
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAccount.getEmailAddress(), emailAccount.getPassword());
            }
        };
        try {
            Session session = Session.getInstance(emailAccount.getProperties(), authenticator);
            Store store = session.getStore("imaps");
            store.connect(emailAccount.getProperties().getProperty("incomingHost"),
                    emailAccount.getEmailAddress(),
                    emailAccount.getPassword());
            emailAccount.setStore(store);
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
}
