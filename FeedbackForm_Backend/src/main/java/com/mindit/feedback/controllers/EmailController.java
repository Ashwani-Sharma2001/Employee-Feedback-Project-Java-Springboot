package com.mindit.feedback.controllers;

// EmailController.java
import com.mindit.feedback.data.repository.UserRepository;
import com.mindit.feedback.models.request.EmailRequest;
import com.mindit.feedback.services.EmailService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@CrossOrigin
@AllArgsConstructor
public class EmailController {

  private EmailService emailService;
  private UserRepository userRepository;

  @PostMapping("/send")
  @CrossOrigin
  public String sendEmail(@RequestBody EmailRequest emailRequest) {
    emailService.sendEmail(
        emailRequest.getEmail(), "Employee Quaterly FeedBack Form", emailRequest.getUrl());
    return "Email sent successfully....";
  }

  // Currently Email Broadcast is working from FrontEnd
  @PostMapping("/sendAll")
  @CrossOrigin
  public String sendEmailToAll(@RequestBody EmailRequest emailRequest) {
    List<String> recipients = userRepository.findAllEmployeesEmail();
    for (String user : recipients) {
      String email = user;
      System.out.print(email);
      emailService.sendEmailAll("Employee Quaterly FeedBack Form", emailRequest.getUrl(), email);
    }
    return "Emails sent successfully....";
  }
}
