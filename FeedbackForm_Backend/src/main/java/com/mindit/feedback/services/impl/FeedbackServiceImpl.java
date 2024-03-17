package com.mindit.feedback.services.impl;

import com.mindit.feedback.data.entity.Feedback;
import com.mindit.feedback.data.entity.User;
import com.mindit.feedback.data.repository.FeedbackRepo;
import com.mindit.feedback.data.repository.UserRepository;
import com.mindit.feedback.exception.CommonErrorCode;
import com.mindit.feedback.exception.MMException;
import com.mindit.feedback.models.request.FeedbackRequest;
import com.mindit.feedback.models.request.ReviewRequest;
import com.mindit.feedback.models.response.FeedbackResponse;
import com.mindit.feedback.models.response.FeedbacksResponse;
import com.mindit.feedback.services.FeedbackService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

  private UserRepository userRepository;
  private FeedbackRepo feedbackRepo;
  private static final Set<String> submittedUrls = new HashSet<>();

  @Override
  public ResponseEntity<FeedbackResponse> addFeedback(
      @RequestBody FeedbackRequest feedbackRequest) {
    Integer empId = feedbackRequest.getEmpId();
    String name = feedbackRequest.getName();
    Optional<User> userOptional = userRepository.findByEmpIdAndName(empId, name);

    if (userOptional.isEmpty()) {
      return ResponseEntity.badRequest()
          .body(new FeedbackResponse(false, "Invalid empId ...No matching user found."));
    }

    LocalDate twoMonthsAgo = LocalDate.now().minusMonths(2);
    List<Feedback> previousFeedbacks =
        feedbackRepo.findByempIdAndSubmittedDate(empId, twoMonthsAgo);

    if (!previousFeedbacks.isEmpty()) {
      // Feedback already submitted in the last two months
      return ResponseEntity.badRequest()
          .body(
              new FeedbackResponse(
                  false, "Form Already Submitted For This Quarter Try in Next Quarter.."));
    }

    Feedback feedback = new Feedback();
    feedback.setEmpId(empId);
    feedback.setAchievements(feedbackRequest.getAchievements());
    feedback.setImprovements(feedbackRequest.getImprovements());
    feedback.setPlans(feedbackRequest.getPlans());

    // Set submittedDate to the current date, month, and year
    LocalDate currentDate = LocalDate.now();
    String currentMonthYearDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    feedback.setSubmittedDate(currentMonthYearDate);

    feedbackRepo.save(feedback);

    return ResponseEntity.ok(new FeedbackResponse(true, "Feedback submitted successfully."));
  }

  @Override
  public List<FeedbacksResponse> getFeedbackById(Integer empId) {
    try {
      List<Feedback> feedbackList = feedbackRepo.findAllByEmpId(empId);
      List<FeedbacksResponse> feedbacksResponseList = new ArrayList<>();

      for (Feedback feedback : feedbackList) {
        FeedbacksResponse feedbacksResponse = new FeedbacksResponse();
        feedbacksResponse.setSno(feedback.getSno());
        feedbacksResponse.setEmpId(empId);
        feedbacksResponse.setAchievements(feedback.getAchievements());
        feedbacksResponse.setImprovements(feedback.getImprovements());
        feedbacksResponse.setPlans(feedback.getPlans());
        feedbacksResponse.setAchievementComments(feedback.getCommentsOnAchievements());
        feedbacksResponse.setImprovementComments(feedback.getCommentsOnImprovements());
        feedbacksResponse.setPlanComments(feedback.getCommentsOnPlans());
        feedbacksResponse.setSubmitDate(feedback.getSubmittedDate());
        feedbacksResponseList.add(feedbacksResponse);
      }

      return feedbacksResponseList;
    } catch (Exception e) {
      log.error("Error fetching feedback data", e);
      throw new MMException(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public Void addComments(ReviewRequest reviewRequest, Integer id) {
    try {
      Optional<Feedback> feedbackOptional = feedbackRepo.findById(id);

      feedbackOptional.ifPresent(
          feedback -> {
            feedback.setCommentsOnAchievements(reviewRequest.getAchievementComments());
            feedback.setCommentsOnImprovements(reviewRequest.getImprovementComments());
            feedback.setCommentsOnPlans(reviewRequest.getPlanComments());
            feedbackRepo.save(feedback);
          });
    } catch (MMException e) {
      throw e;
    }
    return null;
  }
}
