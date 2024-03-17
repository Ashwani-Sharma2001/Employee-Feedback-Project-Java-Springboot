package com.mindit.feedback.models.response;

import com.mindit.feedback.contants.enums.Status;
import com.mindit.feedback.contants.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
  private Integer id;
  private String name;
  private String email;
  private UserRole role;
  private Status status;
}
