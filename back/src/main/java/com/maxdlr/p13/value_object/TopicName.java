package com.maxdlr.p13.value_object;

import java.util.UUID;

import com.maxdlr.p13.entity.UserEntity;

public class TopicName {

  private String name;

  public TopicName(UserEntity user) {
    this.name = UUID.randomUUID().toString() + "-" + user.getEmail();
  }

  public String get() {
    return this.name;
  }
}
