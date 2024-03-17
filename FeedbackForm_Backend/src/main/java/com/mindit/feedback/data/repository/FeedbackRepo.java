package com.mindit.feedback.data.repository;

import com.mindit.feedback.data.entity.Feedback;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepo extends JpaRepository<Feedback, Integer> {

  Optional<Feedback> findByEmpId(Integer empId);

  List<Feedback> findAllByEmpId(Integer empId);

  @Query(
      value = "SELECT * FROM feedbacks WHERE empId = :empId AND submissionDate >= :twoMonthsAgo",
      nativeQuery = true)
  List<Feedback> findByempIdAndSubmittedDate(Integer empId, LocalDate twoMonthsAgo);
}
