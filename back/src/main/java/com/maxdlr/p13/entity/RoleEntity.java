package com.maxdlr.p13.entity;

import com.maxdlr.p13.enums.RoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = false)
@Data
@Table(name = "role", uniqueConstraints = { @UniqueConstraint(columnNames = "name") })
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RoleEntity extends AbstractEntity {

  @Id
  @GeneratedValue
  private Integer id;

  @Column(nullable = false, unique = true)
  private RoleEnum name;
}
