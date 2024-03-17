package com.mindit.feedback.data.repository;

import com.mindit.feedback.data.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findByEmail(String email);

  Optional<User> findByEmpId(Integer empId);

  Optional<User> findByEmpIdAndName(Integer empId, String name);

  @Query("SELECT u.email FROM User u")
  List<String> findAllEmployeesEmail();
}
