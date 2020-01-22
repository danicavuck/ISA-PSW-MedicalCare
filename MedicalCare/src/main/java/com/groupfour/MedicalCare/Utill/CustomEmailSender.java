package com.groupfour.MedicalCare.Utill;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class CustomEmailSender {
    private JavaMailSender javaMailSender;
    private Logger logger = LoggerFactory.getLogger(CustomEmailSender.class);

    @Autowired
    public CustomEmailSender(JavaMailSender jmSender){
        javaMailSender = jmSender;
    }

    public void sendMail(String[] receivers, String subject, String message){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setText(message, true);
            helper.setTo(receivers);
            helper.setSubject(subject);
            helper.setFrom("medicalcarepsw@gmail.com");
        } catch (MessagingException e) {
            logger.info("Neuspesno slanje MimeMessage " + e.getMessage());
        }
        javaMailSender.send(mimeMessage);
        logger.info("Sending email from: medicalcarepsw@gmail.com");
    }
}
