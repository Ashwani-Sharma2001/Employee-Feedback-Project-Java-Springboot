package com.mindit.feedback.controllers;

import com.mindit.feedback.models.request.FeedbackRequest;
import com.mindit.feedback.models.request.ReviewRequest;
import com.mindit.feedback.models.response.FeedbacksResponse;
import com.mindit.feedback.services.FeedbackService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/user/feedback")
@CrossOrigin
public class FeedbackController {

  private FeedbackService feedbackService;

  @PostMapping("/add")
  @CrossOrigin
  public ResponseEntity<FeedbacksResponse> addFeedback(
      @RequestBody FeedbackRequest feedbackRequest) {
    return (ResponseEntity<FeedbacksResponse>) feedbackService.addFeedback(feedbackRequest);
  }

  @GetMapping("/{empId}")
  @CrossOrigin
  public ResponseEntity<List<FeedbacksResponse>> getFeedbackById(@PathVariable Integer empId) {
    List<FeedbacksResponse> feedbacksResponses = feedbackService.getFeedbackById(empId);
    return new ResponseEntity<>(feedbacksResponses, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  @CrossOrigin
  public ResponseEntity<Void> updateFeedbacks(
      @RequestBody ReviewRequest reviewRequest, @PathVariable("id") Integer id) {
    Void object = feedbackService.addComments(reviewRequest, id);
    return new ResponseEntity<>(object, HttpStatus.OK);
  }
}
