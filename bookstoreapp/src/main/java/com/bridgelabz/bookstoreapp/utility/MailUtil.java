package com.bridgelabz.bookstoreapp.utility;

import com.bridgelabz.bookstoreapp.entity.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Component
@Slf4j
public class MailUtil {

    /**
     * Purpose : To send OTP on the user mail
     *
     * @param userModel object of UserRegistration
     * @param OTP random generated String
     */
    public void sendOTPEmail(UserModel userModel, String OTP)
    {
        String subject = "Here's your One Time Password (OTP) - Expire in 5 minutes!";
        String content = "<p>Hello " + userModel.getFirstName() + "</p>"
                + "<p>For security reason, you're required to use the following "
                + "One Time Password to login:</p>"
                + "<p><b>" + OTP + "</b></p>"
                + "<br>"
                + "<p>Note: this OTP is set to expire in 5 minutes.</p>";
        setEmailConfiguration(subject,content, userModel);

    }

    /**
     * Purpose : To send link on the mail to reset the password
     *
     * @param userModel
     * @param generatedToken
     * @param displayMessage
     */
    public void sendResetPasswordMail(UserModel userModel, String generatedToken, String displayMessage){
        String subject = "Here's your Reset Password Link - Please Verify Yourself Before link expires ";
        String content = "<p>Hello " + userModel.getFirstName() + "</p>"
                + "<p>Please click here : "
                + " Password Reset Link :</p>"
                + "<p><b>" + "<a href=\"http://localhost:8080/book-store-app/user/verify-by-token?token=" + generatedToken + "\">" +displayMessage +"</a>" + "</b></p>"
                + "<br>"
                + "<p>Note: this link is set to expire in 5 minutes.</p>";
        setEmailConfiguration(subject,content, userModel);
    }

    public void setEmailConfiguration(String subject, String content, UserModel userModel){
        final String fromEmail = "bookstore123654@gmail.com";// requires valid gmail id
        final String password = "Asdfghjkl1$";// correct password for gmail id
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");// SMTP Host
        props.put("mail.smtp.port", "587");// TLS Port
        props.put("mail.smtp.auth", "true");// enable authentication
        props.put("mail.smtp.starttls.enable", "true");// enable STARTTLS
        // to check email sender credentials are valid or not
        Authenticator auth = new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(fromEmail, password);

            }
        };
        javax.mail.Session session = Session.getInstance(props, auth);
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress("no_reply@gmail.com", "NoReply"));
            msg.setReplyTo(InternetAddress.parse("bookstore123654@gmail.com", false));
            msg.setSubject(subject, "UTF-8");
            msg.setText(content,"UTF-8","html");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userModel.getEmailId(), false));
            Transport.send(msg);
            System.out.println("Email Sent Successfully........."); }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
