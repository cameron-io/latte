package io.netstacker.latte.infra.services;

import java.util.Date;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class MailService {
    public int send(String to, String subject, String text) {
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

        return sendEmail(session, to, subject, text);
    }

    /**
     * Utility method to send simple HTML email
     * 
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    private static int sendEmail(Session session, String toEmail, String subject, String body) {
        try {
            MimeMessage msg = new MimeMessage(session);
            // set message headers
            msg.addHeader("Content-Type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("no_reply@latte.com", "NoReply-JD"));
            msg.setReplyTo(InternetAddress.parse("no_reply@latte.com", false));
            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            System.out.println("Message is ready");
            Transport.send(msg);

            return 0;
        } catch (Exception e) {
            e.printStackTrace();

            return 1;
        }
    }
}
