package io.netstacker.latte.domain.services;

import javax.mail.*;

import org.springframework.stereotype.Service;

import io.netstacker.latte.domain.utils.EmailUtil;

@Service
public class MailService {
    public void send(String to, String subject, String text) {
        // Set up the SMTP server.
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.host", "sandbox.smtp.mailtrap.io"); // SMTP Host
        props.put("mail.smtp.port", "587"); // TLS Port
        props.put("mail.smtp.auth", "true"); // enable authentication
        props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

        // create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            // override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        System.getenv("MAILTRAP_SANDBOX_USER"),
                        System.getenv("MAILTRAP_SANDBOX_PASSWORD"));
            }
        };
        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, to, subject, text);
    }
}
