package com.maxdlr.p13.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.maxdlr.p13.dto.WsMessageRecordInfo;
import com.maxdlr.p13.dto.WsMessageRecordInput;
import com.maxdlr.p13.enums.WsMessageStatusEnum;
import com.maxdlr.p13.service.WsMessageService;

@Controller
public class MessagingController {

  WsMessageService messageService;

  MessagingController(WsMessageService messageService) {
    this.messageService = messageService;
  }

  @QueryMapping
  public List<WsMessageRecordInfo> GetAllMessagesOfUser(@Argument Integer userId) {
    return this.messageService.getAllFromUser(userId);
  }

  @MutationMapping()
  public WsMessageRecordInfo CreateMessage(
      @Argument WsMessageRecordInput message) {
    return this.messageService.pushMessage(message, WsMessageStatusEnum.SENT);
  }
}
