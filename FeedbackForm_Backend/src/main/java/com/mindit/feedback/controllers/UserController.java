package com.mindit.feedback.controllers;

import com.mindit.feedback.contants.enums.Status;
import com.mindit.feedback.models.request.UserRequest;
import com.mindit.feedback.models.response.UserResponse;
import com.mindit.feedback.models.response.UsersResponse;
import com.mindit.feedback.services.UserService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/v1/user")
@AllArgsConstructor
@CrossOrigin
public class UserController {

  private final UserService userService;

  @GetMapping()
  @CrossOrigin
  public ResponseEntity<List<UserResponse>> getAllUsers(
      @RequestParam(name = "searchText", required = false) String searchText) {
    log.info("Getting data of all users for admin");
    List<UserResponse> usersResponse = userService.getAllUsers(searchText);
    return new ResponseEntity<>(usersResponse, HttpStatus.OK);
  }

  @PostMapping()
  @CrossOrigin
  ResponseEntity<UsersResponse> addUser(@RequestBody UserRequest userRequest) {
    UsersResponse userResponse = userService.addUser(userRequest);
    return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
  }

  @GetMapping("/getUser/{userId}")
  @CrossOrigin
  public ResponseEntity<UserResponse> getById(@PathVariable("userId") Integer userId) {
    UserResponse userResponse = userService.getById(userId);
    return new ResponseEntity<>(userResponse, HttpStatus.OK);
  }

  @PutMapping("/{userId}")
  @CrossOrigin
  public ResponseEntity<Void> updateUser(
      @PathVariable("userId") Integer empId, @RequestBody UserRequest userRequest) {
    Void object = userService.updateUser(empId, userRequest);
    return new ResponseEntity<>(object, HttpStatus.OK);
  }

  @DeleteMapping("/{userId}")
  @CrossOrigin
  ResponseEntity<Void> deleteUser(@PathVariable String userId) {
    log.info("The invoked method will set the user column to is deleted");
    Void object = userService.deleteUser(userId);
    return new ResponseEntity<>(object, HttpStatus.OK);
  }

  @PutMapping("/{userId}/{status}")
  @CrossOrigin
  public ResponseEntity<Void> updateStatus(
      @PathVariable("userId") Integer userId, @PathVariable("status") Status status) {
    Void object = userService.updateStatus(userId, status);
    return new ResponseEntity<>(object, HttpStatus.OK);
  }
}
