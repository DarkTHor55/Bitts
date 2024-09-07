package com.darkthor.Service.Impl;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Transactional
    public class MailService {

    @Autowired
    private JavaMailSender mailSender;
    private static final int otp = UserServiceImpl.otp;

    public void sendEmail(final String email) {
        final String subject= "Email Varification from Bitts" ;
        final String body="We have received a request to verify your email address associated with your Bitts account. Please use the following One-Time Password (OTP) to complete your verification process:\n" +"\n" +"Your OTP is: "+otp+"\n" +"\n" +"Please note that this OTP is valid for the next 10 minutes. Do not share this code with anyone for security reasons.\n" +"\n" +"If you did not request this verification, please ignore this email or contact our support team immediately.\n" +"\n" +"Thank you for choosing Bitts!\n" +"\n" +"Best regards,\n" +"Bitts Support Team";
        final int randomNumber;
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("dark55thor@gmail.com");
            message.setTo(email);
            message.setText(body);
            message.setSubject(subject);
            mailSender.send(message);
            System.out.println("Mail sent successfully." + otp);
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}