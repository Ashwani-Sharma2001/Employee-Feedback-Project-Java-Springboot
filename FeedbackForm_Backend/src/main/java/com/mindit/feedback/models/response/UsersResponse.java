package com.mindit.feedback.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersResponse {
  private String id;
  private String name;
  private String email;
  private String message;
}
