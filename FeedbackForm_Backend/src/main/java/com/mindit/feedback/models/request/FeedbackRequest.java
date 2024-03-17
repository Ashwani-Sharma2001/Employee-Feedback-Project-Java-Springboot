package com.mindit.feedback.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequest {

  private Integer empId;
  private String name;
  private String achievements;
  private String improvements;
  private String plans;
}
