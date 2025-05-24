package com.maxdlr.p13.entity;

import java.util.Date;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * AbstractEntity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AbstractEntity {

  @Column(nullable = false)
  Date createdOn;

  @Column(nullable = true)
  Date updatedOn;

  AbstractEntity() {
    this.createdOn = new Date();
  }

}
