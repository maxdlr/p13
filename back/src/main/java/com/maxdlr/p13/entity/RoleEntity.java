package com.maxdlr.p13.entity;

import java.util.List;

import com.maxdlr.p13.enums.RoleEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class RoleEntity extends AbstractEntity {

  @Id
  @GeneratedValue
  private Integer id;

  @Column(nullable = false, unique = true)
  private RoleEnum name;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<UserEntity> users;
}
