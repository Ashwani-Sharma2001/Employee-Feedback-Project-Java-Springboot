package com.mindit.feedback.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedbacks")
public class Feedback {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer sno;

  @Column(name = "empId")
  private Integer empId;

  @Column(name = "achievements")
  private String achievements;

  @Column(name = "improvements")
  private String improvements;

  @Column(name = "plans")
  private String plans;

  @Column(name = "acheivements_reviews")
  private String commentsOnAchievements;

  @Column(name = "improvements_reviews")
  private String commentsOnImprovements;

  @Column(name = "plans_reviews")
  private String commentsOnPlans;

  @Column(name = "submissionDate")
  private String SubmittedDate;
}
