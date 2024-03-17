package com.mindit.feedback.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
  private String name;
//
//  @Pattern(
//      regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^. -]+@[a-zA-Z0-9. -]+$",
//      message = "Invalid Email Name")
  private String email;

  private Integer empId;
}
