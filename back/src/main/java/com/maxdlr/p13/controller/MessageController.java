package com.maxdlr.p13.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.maxdlr.p13.dto.MessageRecordInfo;
import com.maxdlr.p13.dto.MessageRecordInput;
import com.maxdlr.p13.enums.MessageStatusEnum;
import com.maxdlr.p13.service.MessageService;

@Controller
public class MessageController {

  MessageService messageService;

  MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  @QueryMapping
  public List<MessageRecordInfo> GetAllMessagesOfUser(@Argument Integer userId) {
    return this.messageService.findAllByUser(userId);
  }

  @QueryMapping
  public MessageRecordInfo GetMessage(@Argument Integer id) {
    return this.messageService.findById(id);
  }

  @QueryMapping
  public List<MessageRecordInfo> GetAllMessagesOfConversation(@Argument Integer conversationId) {
    return this.messageService.findAllByConversation(conversationId);
  }

  @MutationMapping
  public MessageRecordInfo CreateMessage(
      @Argument MessageRecordInput message) {
    return this.messageService.push(message, MessageStatusEnum.SENT);
  }
}
