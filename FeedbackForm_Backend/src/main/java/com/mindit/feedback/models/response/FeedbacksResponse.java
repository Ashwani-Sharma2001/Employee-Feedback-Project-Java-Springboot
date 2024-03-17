package com.mindit.feedback.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbacksResponse {
  private Integer sno;
  private Integer empId;
  private String achievements;
  private String improvements;
  private String plans;
  private String achievementComments;
  private String improvementComments;
  private String planComments;
  private String SubmitDate;

  public FeedbacksResponse(String s) {}
}
