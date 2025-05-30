package com.maxdlr.p13.value_object;

import java.util.UUID;

import com.maxdlr.p13.entity.UserEntity;

public class TopicName {

  public static String generate(UserEntity user) {
    return UUID.randomUUID().toString() + "-" + user.getEmail();
  }
}
