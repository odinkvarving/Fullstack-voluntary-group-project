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

/**
 * Service-class that was intended to use for sending mail - did not have time to implement it
 */
@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    private static final String MAIL_ADDRESS = "romreservasjon@gmail.com";
    private static final String PASSWORD = "4s1biw45uZhq3BJeWJQ5";
    private static final String BASE_URL = "http://localhost:8081/resetpassword/";

    /**
     * Method for generating and sending email to given email address, with subject and URL suffix.
     *
     * @param email: receiving account email.
     * @param subject: subject of email.
     * @param suffix: reset URL suffix.
     */
    public void sendMail(String email, String subject, String suffix) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(MAIL_ADDRESS);
            message.setTo(email);
            message.setSubject(subject);
            message.setText(getPasswordResetMessage(suffix));
            logger.info("From: " + message.getFrom() + " - To: " + message.getTo().toString() + " - Subject: " + message.getSubject() + " - Text: " + message.getText());
            mailSender.send(message);
            logger.info("Password reset mail sent to " + email);
        } catch(MailException e) {
            logger.error(e.toString());
        }
    }

    /**
     * Method which generates email with a static format.
     *
     * @param suffix: reset URL suffix.
     * @return String with email text.
     */
    private String getPasswordResetMessage(String suffix) {
        return "Bruk linken under for å tilbakestille passordet:\n" +
            BASE_URL + suffix + "\n" +
            "Mvh Supportavdelingen hos Romreservasjon";
    }
}
