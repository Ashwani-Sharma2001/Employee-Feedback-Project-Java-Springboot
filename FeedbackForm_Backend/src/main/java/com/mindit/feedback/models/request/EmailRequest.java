package com.mindit.feedback.models.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EmailRequest {
  private String email;
  private String url;
}
