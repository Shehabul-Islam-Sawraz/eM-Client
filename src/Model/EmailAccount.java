package Model;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Store;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class EmailAccount {
    private String emailAddress;
    private String password;
    private Properties properties;
    private Store store;

    public EmailAccount(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
        properties = new Properties();
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        sf.setTrustAllHosts(true);
        properties.put("mail.imaps.ssl.trust", "*");
        properties.put("mail.imaps.ssl.socketFactory", sf);
        properties.put("incomingHost", "imap.gmail.com");
        properties.put("mail.store.protocol", "imaps");

        properties.put("mail.transport.protocol", "smtps");
        properties.put("mail.smtps.host", "smtp.gmail.com");
        properties.put("mail.smtps.auth", "true");
        properties.put("outgoingHost", "smtp.gmail.com");
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
