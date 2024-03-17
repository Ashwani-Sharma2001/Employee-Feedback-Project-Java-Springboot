package com.mindit.feedback.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllFeedbackResponse {
  private Integer empId;
  private String achievements;
  private String improvements;
  private String plans;
}
