package com.mindit.feedback.services;

import com.mindit.feedback.models.request.FeedbackRequest;
import com.mindit.feedback.models.request.ReviewRequest;
import com.mindit.feedback.models.response.FeedbacksResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface FeedbackService {

  public ResponseEntity<?> addFeedback(FeedbackRequest feedbackRequest);

  List<FeedbacksResponse> getFeedbackById(Integer empId);

  Void addComments(ReviewRequest reviewRequest, Integer id);
}
