package com.mindit.feedback.controllers;

import com.mindit.feedback.models.request.LoginRequest;
import com.mindit.feedback.models.response.TokenResponse;
import com.mindit.feedback.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin
public class AuthController {

  private UserService userService;

  @PostMapping()
  @CrossOrigin
  public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
    log.info("Login request for user [{}]", loginRequest.getEmail());

    this.doAuthenticate(loginRequest.getEmail(), loginRequest.getPassword());

    TokenResponse loginResponse =
        userService.loginResponse(loginRequest.getEmail(), loginRequest.getPassword());
    return new ResponseEntity<>(loginResponse, HttpStatus.OK);
  }

  private void doAuthenticate(String email, String password) {
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(email, password);
    try {
    } catch (BadCredentialsException e) {
      throw new BadCredentialsException("Invalid userName or Password");
    }
  }

  @ExceptionHandler(BadCredentialsException.class)
  public String exceptionHandler() {
    return "Credential Invalid";
  }
}
