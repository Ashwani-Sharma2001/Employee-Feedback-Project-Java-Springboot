package com.mindit.feedback.services.impl;

import com.mindit.feedback.data.repository.UserRepository;
import com.mindit.feedback.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

  private JavaMailSender javaMailSender;

  private UserRepository userRepository;

  public void sendEmail(String to, String subject, String body) {
    MimeMessage message = javaMailSender.createMimeMessage();
    // url encoder
    MimeMessageHelper helper = new MimeMessageHelper(message);

    try {
      System.out.println(to);
      helper.setTo(to);
      helper.setSubject(subject);
      String htmlBody =
          "<html><body><p>Click the following link:</p><a href='"
              + body
              + "'>Form Link</a></body></html>";
      helper.setText(htmlBody, true);

      javaMailSender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  public void sendEmailAll(String subject, String url, String recipientEmail) {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper;
    try {
      helper = new MimeMessageHelper(message, true);
      helper.setTo(recipientEmail);
      helper.setSubject(subject);
      String htmlBody =
          "<html><body><p>Click the following link:</p><a href='%s'>Form Link</a></body></html>";
      htmlBody = String.format(htmlBody, url);
      helper.setText(htmlBody, true);
      javaMailSender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}
