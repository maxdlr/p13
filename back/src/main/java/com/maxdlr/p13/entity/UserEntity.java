package com.maxdlr.p13.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@EqualsAndHashCode
@Data
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserEntity extends AbstractEntity {

  @Id
  @GeneratedValue()
  private Integer id;

  @Column(nullable = false)
  @Size(max = 60)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String firstname;

  @Column(nullable = false)
  private String lastname;

  @Column(nullable = false)
  private String phoneNumber;

  @Column(nullable = false)
  private Boolean isActive;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private RoleEntity role;
}
