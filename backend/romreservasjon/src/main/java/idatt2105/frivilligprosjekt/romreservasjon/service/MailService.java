package idatt2105.frivilligprosjekt.romreservasjon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    private static final String MAIL_ADDRESS = "romreservasjon@gmail.com";
    private static final String PASSWORD = "4s1biw45uZhq3BJeWJQ5";
    private static final String BASE_URL = "http://localhost:8081/resetpassword/";

    /*@Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.gmail.com");
        sender.setPort(587);
        sender.setUsername(MAIL_ADDRESS);
        sender.setPassword(PASSWORD);

        Properties props = sender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return sender;
    }*/

    /*public void sendMail(String email, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(MAIL_ADDRESS);
            message.setTo(email);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            logger.info("Password reset mail sent to " + email);
        } catch(MailException e) {
            logger.error(e.toString());
        }
    }*/

    public void sendMail(String email, String subject, String suffix) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(MAIL_ADDRESS);
            message.setTo(email);
            message.setSubject(subject);
            message.setText(getPasswordResetMessage(suffix));
            logger.info("From: " + message.getFrom() + " - To: " + message.getTo() + " - Subject: " + message.getSubject() + " - Text: " + message.getText());
            mailSender.send(message);
            logger.info("Password reset mail sent to " + email);
        } catch(MailException e) {
            logger.error(e.toString());
        }
    }

    private String getPasswordResetMessage(String suffix) {
        return "Bruk linken under for å tilbakestille passordet:\n" +
                BASE_URL + suffix + "\n" +
                "Mvh Supportavdelingen hos Romreservasjon";

        /*return "<H2>Glemt passord Romreservasjon</H2> </br>" +
            "<p>Hei, vi har mottatt en forespørsel angående tilbakestilling av glemt passord</p> </br>" +
            "<p>Vennligst bruk linken under for å tilbakestille passordet: </br>" +
            "<a href=" + BASE_URL + urlSuffix + "><h3>Tilbakestill passord</h3></a> </br>" +
            "<p></p> </br>" +
            "<p>Hvis du ikke har sendt denne forespørselen, kan du ignorere denne mailen</p> </br>" +
            "<h3>Mvh Supportavdelingen hos Romreservasjon</h3>";*/
    }




    /**
     * Sends a password reset message to a given recipient with a provided reset-url.
     *
     * @param recipient the recipient to send the message to.
     * @param urlSuffix the url to send the message to. can be any url.
     * @return true if the message was successfully sent.
     */
    /*public boolean sendPasswordResetMail(String recipient, String urlSuffix) {
        try {
            Transport.send(getPasswordResetMessage(getGmailSession(), recipient, urlSuffix));
            logger.info("Sent password reset message to: " + recipient);

            return true;
        } catch (Exception e) {
            logger.error("Error during transporting of password reset message to: " + recipient);
            return false;
        }
    }*/

    /*private Message getPasswordResetMessage(Session session, String recipient, String urlSuffix) {
        logger.info("Preparing a new password reset message to: " + recipient);
        Message message = new MimeMessage(session);
        String textMessage =
            "<H2>Glemt passord Romreservasjon</H2> </br>" +
                "<p>Hei, vi har mottatt en forespørsel angående tilbakestilling av glemt passord</p> </br>" +
                "<p>Vennligst bruk linken under for å tilbakestille passordet: </br>" +
                "<a href=" + BASE_URL + urlSuffix + "><h3>Tilbakestill passord</h3></a> </br>" +
                "<p></p> </br>" +
                "<p>Hvis du ikke har sendt denne forespørselen, kan du ignorere denne mailen</p> </br>" +
                "<h3>Mvh Supportavdelingen hos Romreservasjon</h3>";
        try {
            message.setFrom(new InternetAddress(MAIL_ADDRESS));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Password reset");
            message.setContent(textMessage, "text/html");
        } catch (Exception e) {
            logger.error("Something went wrong during password reset message generation", e);
        }
        return message;
    }*/

    /*private Session getGmailSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        return Session.getInstance(properties,
            new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(MAIL_ADDRESS, PASSWORD);
                }
            });
    }*/

    /**
     * Generates and sends a test message to the specified recipient.
     *
     * @param recipient the recipient to send the message to.
     */
    /*private void sendTestMail(String recipient) {
        try {
            Transport.send(getTestMessage(getGmailSession(), recipient));
            logger.info("Sent test message to: " + recipient);
        } catch (Exception e) {
            logger.error("Error during transporting of test message", e);
        }
    }*/

    /*private Message getTestMessage(Session session, String recipient) {
        logger.info("Preparing test message to: " + recipient);
        Message message = new MimeMessage(session);
        String testText = "This is a test paragraph.";
        try {
            message.setFrom(new InternetAddress(MAIL_ADDRESS));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Testing Mail API");
            message.setText(testText);
        } catch (Exception e) {
            logger.error("something went wrong during test message generation", e);
        }
        return message;
    }*/
}
