package com.mindit.feedback.services;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

  void sendEmail(String mail, String testSubject, String testBody);

  void sendEmailAll(String subject, String url, String recipientEmail);
}
