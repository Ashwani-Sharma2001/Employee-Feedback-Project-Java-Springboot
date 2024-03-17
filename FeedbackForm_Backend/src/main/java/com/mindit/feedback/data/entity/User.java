package com.mindit.feedback.data.entity;

import com.mindit.feedback.contants.enums.Status;
import com.mindit.feedback.contants.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class User {

  @Id private String id;

  @Column(name = "empid")
  private Integer empId;

  @Column(name = "name")
  private String name;

  @Column(name = "emailId")
  private String email;

  @Column(name = "password")
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private UserRole role;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;
}
