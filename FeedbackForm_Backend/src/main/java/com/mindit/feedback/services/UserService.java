package com.mindit.feedback.services;

import com.mindit.feedback.contants.enums.Status;
import com.mindit.feedback.models.request.UserRequest;
import com.mindit.feedback.models.response.TokenResponse;
import com.mindit.feedback.models.response.UserResponse;
import com.mindit.feedback.models.response.UsersResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

  List<UserResponse> getAllUsers(String searchText);

  UsersResponse addUser(UserRequest userRequest);

  Void updateUser(Integer empId, UserRequest userRequest);

  Void deleteUser(String id);

  TokenResponse loginResponse(String email, String password);

  UserResponse getById(Integer userId);

  Void updateStatus(Integer userId, Status status);
}
