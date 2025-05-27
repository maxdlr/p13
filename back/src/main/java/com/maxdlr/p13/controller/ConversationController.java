package com.maxdlr.p13.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.maxdlr.p13.dto.ConversationRecordInfo;
import com.maxdlr.p13.dto.ConversationRecordInput;
import com.maxdlr.p13.entity.ConversationEntity;
import com.maxdlr.p13.mapper.ConversationMapper;
import com.maxdlr.p13.service.ConversationService;

@Controller
public class ConversationController {

  ConversationService conversationService;
  ConversationMapper conversationMapper;

  public ConversationController(ConversationService conversationService, ConversationMapper conversationMapper) {
    this.conversationService = conversationService;
    this.conversationMapper = conversationMapper;
  }

  @QueryMapping
  public List<ConversationRecordInfo> GetAllConversations() {
    return this.conversationService.getAllConversations();
  }

  @QueryMapping
  public List<ConversationRecordInfo> GetAllConversationsOfUser(@Argument Integer userId) {
    return this.conversationService.findByUser(userId);
  }

  @QueryMapping
  public ConversationRecordInfo GetConversation(@Argument Integer id) {
    return this.conversationService.findOneById(id);
  }

  @MutationMapping
  public ConversationRecordInfo CreateConversation(@Argument ConversationRecordInput conversationInput) {

    assert (conversationInput != null);

    ConversationEntity conversation = this.conversationService.openConversation(conversationInput);
    return this.conversationMapper.toRecordInfo(conversation);
  }

}
