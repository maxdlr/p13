package com.maxdlr.p13.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class UserEntity extends AbstractEntity {

  @Id
  @GeneratedValue
  private Integer id;

  private String email;

  private String password;

  private String firstname;

  private String lastname;

  private String phoneNumber;

  private Boolean isActive;

  private List<RoleEntity> roles;
}
