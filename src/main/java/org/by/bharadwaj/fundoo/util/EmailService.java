package org.by.bharadwaj.fundoo.util;

import org.by.bharadwaj.fundoo.exception.FundooException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String subject, String text, String from, String to) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(from);
            mail.setTo(to);
            mail.setText(text);
            mail.setSubject(subject);

            javaMailSender.send(mail);
        }catch (Exception e) {
            throw new FundooException(HttpStatus.BAD_GATEWAY.value(),e.getMessage());
        }
    }
}
