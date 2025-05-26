package com.maxdlr.p13.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.maxdlr.p13.dto.ConversationRecordInfo;
import com.maxdlr.p13.service.ConversationService;

@Controller
public class ConversationController {

  ConversationService conversationService;

  ConversationController(ConversationService conversationService) {
    this.conversationService = conversationService;
  }

  @QueryMapping
  public List<ConversationRecordInfo> GetAllConversations() {
    return this.conversationService.getAllConversations();
  }

}
