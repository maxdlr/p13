package com.maxdlr.p13.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractEntity {

  @CreationTimestamp
  Date createdOn;

  @UpdateTimestamp
  Date updatedOn;
}
