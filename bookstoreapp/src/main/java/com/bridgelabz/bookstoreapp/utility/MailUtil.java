package com.bridgelabz.bookstoreapp.utility;

import com.bridgelabz.bookstoreapp.entity.UserRegistration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Component
@Slf4j
public class MailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOTPEmail(UserRegistration userRegistration,String OTP) throws MessagingException, UnsupportedEncodingException {
        log.info("Inside sendOTPEmail Method");
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        System.out.println(userRegistration.getEmailId());
        helper.setTo(userRegistration.getEmailId());

        String subject = "Here's your One Time Password (OTP) - Expire in 5 minutes!";

        String content = "<p>Hello " + userRegistration.getFirstName() + "</p>"
                + "<p>For security reason, you're required to use the following "
                + "One Time Password to login:</p>"
                + "<p><b>" + OTP + "</b></p>"
                + "<br>"
                + "<p>Note: this OTP is set to expire in 5 minutes.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        javaMailSender.send(message);
        log.info("sendEmail method successfully executed.");
    }
}
