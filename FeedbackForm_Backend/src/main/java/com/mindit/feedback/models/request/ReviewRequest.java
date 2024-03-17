package com.mindit.feedback.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
  private String achievementComments;
  private String improvementComments;
  private String planComments;
}
