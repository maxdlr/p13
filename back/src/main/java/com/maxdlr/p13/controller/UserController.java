package com.maxdlr.p13.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.maxdlr.p13.dto.UserRecordInfo;
import com.maxdlr.p13.service.UserService;

@Controller
public class UserController {

  UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @QueryMapping
  public UserRecordInfo GetUser(@Argument Integer id) {
    return this.userService.findOneById(id);
  }

  @QueryMapping
  public List<UserRecordInfo> GetAllUsers() {
    return this.userService.findAll();
  }

  @QueryMapping
  public UserRecordInfo GetUserOfConversation(@Argument Integer conversationId) {
    return this.userService.findByConversation(conversationId);
  }
}
