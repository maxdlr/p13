package com.maxdlr.p13.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.maxdlr.p13.dto.ConversationRecordInfo;
import com.maxdlr.p13.dto.ConversationRecordInput;
import com.maxdlr.p13.entity.ConversationEntity;
import com.maxdlr.p13.mapper.ConversationMapper;
import com.maxdlr.p13.service.ConversationService;
import com.maxdlr.p13.service.WsService;

@Controller
public class ConversationController {

  ConversationService conversationService;
  ConversationMapper conversationMapper;
  WsService wsService;

  public ConversationController(ConversationService conversationService, ConversationMapper conversationMapper,
      WsService wsService) {
    this.conversationService = conversationService;
    this.conversationMapper = conversationMapper;
    this.wsService = wsService;
  }

  @QueryMapping
  public List<ConversationRecordInfo> GetAllConversations() {
    return this.conversationService.getAllConversations();
  }

  @QueryMapping
  public List<ConversationRecordInfo> GetAllConversationsOfUser(@Argument Integer userId) {
    List<ConversationRecordInfo> conversations = this.conversationService.findByUser(userId);
    return conversations;
  }

  @QueryMapping
  public ConversationRecordInfo GetConversation(@Argument Integer id) {
    ConversationRecordInfo conversation = this.conversationService.findOneById(id);
    return conversation;
  }

  @MutationMapping
  public ConversationRecordInfo CreateConversation(@Argument ConversationRecordInput conversation) {
    ConversationEntity conversationEntity = this.conversationService.openConversation(conversation);
    ConversationRecordInfo conversationInfo = this.conversationMapper.toRecordInfo(conversationEntity);

    Map<String, Object> headers = new HashMap<>();

    this.wsService.<ConversationRecordInfo>send(
        headers,
        "all-user-conversations",
        conversationInfo);
    return conversationInfo;
  }
}
