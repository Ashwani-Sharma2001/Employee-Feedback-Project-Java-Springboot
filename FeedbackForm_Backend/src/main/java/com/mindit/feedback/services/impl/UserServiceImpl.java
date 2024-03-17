package com.mindit.feedback.services.impl;

import static com.mindit.feedback.contants.enums.Status.ISACTIVE;
import static com.mindit.feedback.contants.enums.UserRole.USER;
import static com.mindit.feedback.utils.EncryptionService.encrypt;

import com.mindit.feedback.contants.enums.Status;
import com.mindit.feedback.data.entity.User;
import com.mindit.feedback.data.repository.UserRepository;
import com.mindit.feedback.exception.CommonErrorCode;
import com.mindit.feedback.exception.MMException;
import com.mindit.feedback.models.request.UserRequest;
import com.mindit.feedback.models.response.TokenResponse;
import com.mindit.feedback.models.response.UserResponse;
import com.mindit.feedback.models.response.UsersResponse;
import com.mindit.feedback.services.UserService;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private static final String str = "Provided UserId ";

  @Override
  public List<UserResponse> getAllUsers(String searchText) {
    try {
      List<User> users;
      if (searchText != null && !searchText.isEmpty()) {
        log.info("Getting data from matching fields for " + searchText + " search .");
      }
      users = userRepository.findAll();
      List<UserResponse> userResponses = userMapping(users);
      return userResponses;
    } catch (MMException e) {
      throw new MMException(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }
  }

  private List<UserResponse> userMapping(List<User> users) {
    List<UserResponse> userLists = new ArrayList<>();
    if (!users.isEmpty()) {
      log.info("Your result is being mapped to response");
      for (User userdata : users) {
        UserResponse userList = new UserResponse();
        userList.setId(userdata.getEmpId());
        userList.setName(userdata.getName());
        userList.setEmail(userdata.getEmail());
        userList.setRole(userdata.getRole());
        userList.setStatus(userdata.getStatus());
        userLists.add(userList);
      }
    }
    return userLists;
  }

  @Override
  public UsersResponse addUser(UserRequest userRequest) {
    UsersResponse userResponse = new UsersResponse();
    try {
      Optional<User> existingUserByEmpId = userRepository.findByEmpId(userRequest.getEmpId());
      if (existingUserByEmpId.isPresent()) {
        userResponse.setMessage("EmpId is already present");
        return userResponse;
      }

      Optional<User> existingUserByEmail = userRepository.findByEmail(userRequest.getEmail());
      if (existingUserByEmail.isPresent()) {
        userResponse.setMessage("EmailId is already Present");
        return userResponse;
      }
      User user = userFromRequest(userRequest);
      userRepository.save(user);
      Optional<User> userOptional = userRepository.findByEmail(userRequest.getEmail());
      if (userOptional.isPresent()) {
        userResponse = userFromResponse(userOptional.get());
        userResponse.setMessage("User added successfully"); // Success message
      }

      return userResponse;
    } catch (MMException e) {
      throw e;
    } catch (Exception ex) {
      log.error("Error occurred while adding a user: [{}]", ex.getMessage());
      throw new MMException(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }
  }

  private User userFromRequest(UserRequest userRequest)
      throws NoSuchAlgorithmException, InvalidKeySpecException, MessagingException, IOException {
    User user = new User();
    UUID uuid = UUID.randomUUID();
    user.setId(uuid.toString());

    if (!userRequest.getName().isEmpty()) {
      user.setName(userRequest.getName());
    } else {
      throw new MMException(CommonErrorCode.USERNAME_NOT_VALID);
    }

    if (isValidEmail(userRequest.getEmail())) {
      user.setEmail(userRequest.getEmail());
      user.setRole(USER);
      user.setEmpId(userRequest.getEmpId());
      user.setPassword("NA");
      user.setStatus(ISACTIVE);

    } else {
      throw new MMException(CommonErrorCode.BAD_REQUEST);
    }
    return user;
  }

  private boolean isValidEmail(String email) {
    return email != null && email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^. -]+@[a-zA-Z0-9. -]+$");
  }

  private UsersResponse userFromResponse(User user) {
    UsersResponse userResponse = new UsersResponse();
    userResponse.setId(user.getId());
    userResponse.setEmail(user.getEmail());
    userResponse.setName(user.getName());

    return userResponse;
  }

  @Override
  public Void updateUser(Integer empId, UserRequest userRequest) {
    try {
      userRepository
          .findByEmpId(empId)
          .ifPresent(
              user -> {
                user.setName(userRequest.getName());
                user.setEmail(userRequest.getEmail());
                userRepository.save(user);
              });
    } catch (MMException e) {
      throw e;
    }
    return null;
  }

  @Override
  public Void deleteUser(String id) {
    try {
      Optional<User> optionalUser = userRepository.findById(id);
      if (optionalUser.isPresent()) {
        User user = optionalUser.get();
        userRepository.save(user);
        log.info(str + id + " is set to deleted");
      } else {
        log.error(str + id + " does not exist");
        throw new MMException(CommonErrorCode.DATA_NOT_FOUND);
      }
      return null;
    } catch (MMException e) {
      throw e;
    }
  }

  @Override
  public TokenResponse loginResponse(String email, String password) {
    try {

      Optional<User> userData = userRepository.findByEmail(email);
      TokenResponse tokenResponse = new TokenResponse();
      if (userData.isEmpty()) {
        throw new MMException(CommonErrorCode.USERNAME_NOT_VALID);
      }
      String encryptedPassword = encrypt(password);
      if (String.valueOf(userData.get().getRole()).equals("ADMIN")
          && encryptedPassword.equals(userData.get().getPassword())) {
        tokenResponse.setMessage("Login Successfully");
        tokenResponse.setEmail(userData.get().getEmail());
        tokenResponse.setRole("ADMIN");
      } else if (String.valueOf(userData.get().getRole()).equals("USER")
          && encryptedPassword.equals(userData.get().getPassword())) {
        tokenResponse.setMessage("Login Successfully");
        tokenResponse.setEmail(userData.get().getEmail());
        tokenResponse.setRole("USER");
      } else {
        throw new MMException(CommonErrorCode.PASSWORD_NOT_VALID);
      }

      return tokenResponse;
    } catch (MMException e) {
      throw e;
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    } catch (InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public UserResponse getById(Integer userId) {
    try {
      Optional<User> user = userRepository.findByEmpId(userId);
      UserResponse userResponse = new UserResponse();
      if (user.isPresent()) {
        userResponse.setId(user.get().getEmpId());
        userResponse.setName(user.get().getName());
        userResponse.setEmail(user.get().getEmail());
        userResponse.setRole(user.get().getRole());
        return userResponse;
      }
    } catch (Exception e) {
      log.error("Wrong credentials");
      throw new MMException(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }
    return null;
  }

  @Override
  public Void updateStatus(Integer userId, Status status) {
    try {
      Optional<User> optionalUser = userRepository.findByEmpId(userId);
      if (optionalUser.isPresent()) {
        User user = optionalUser.get();
        user.setStatus(status);
        userRepository.save(user);
      } else {
        log.error(str + userId + " does not exist");
        throw new MMException(CommonErrorCode.DATA_NOT_FOUND);
      }
    } catch (MMException e) {
      throw e;
    }
    return null;
  }
}
