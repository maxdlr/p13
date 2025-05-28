package com.maxdlr.p13.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.maxdlr.p13.dto.MessageRecordInfo;
import com.maxdlr.p13.dto.MessageRecordInput;
import com.maxdlr.p13.enums.MessageStatusEnum;
import com.maxdlr.p13.service.MessageService;
import com.maxdlr.p13.service.WsService;

@Controller
public class MessageController {

  MessageService messageService;
  WsService wsService;

  public MessageController(MessageService messageService, WsService wsService) {
    this.messageService = messageService;
    this.wsService = wsService;
  }

  @QueryMapping
  public List<MessageRecordInfo> GetAllMessagesOfUser(@Argument Integer userId) {
    return this.messageService.findAllByUser(userId);
  }

  @QueryMapping
  public MessageRecordInfo GetMessage(@Argument Integer id) {
    return this.messageService.findOneById(id);
  }

  @QueryMapping
  public List<MessageRecordInfo> GetAllMessagesOfConversation(@Argument Integer conversationId) {
    return this.messageService.findAllByConversation(conversationId);
  }

  @MutationMapping
  public MessageRecordInfo CreateMessage(
      @Argument MessageRecordInput message) {

    MessageRecordInfo createdMessage = this.messageService.push(message, MessageStatusEnum.SENT);
    Map<String, Object> headers = new HashMap<>();

    this.wsService.<MessageRecordInfo>send(
        headers,
        createdMessage.getConversation().getWsTopic(),
        createdMessage);

    return createdMessage;
  }
}
