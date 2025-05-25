package com.maxdlr.p13.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.maxdlr.p13.entity.UserEntity;
import com.maxdlr.p13.entity.WsConversationEntity;
import com.maxdlr.p13.enums.WsConversationStatusEnum;
import com.maxdlr.p13.repository.UserRepository;
import com.maxdlr.p13.repository.WsConversationRepository;

@Service
public class WsConversationService {

  WsConversationRepository conversationRepository;
  UserRepository userRepository;

  WsConversationService(
      WsConversationRepository conversationRepository,
      UserRepository userRepository) {
    this.conversationRepository = conversationRepository;
    this.userRepository = userRepository;
  }

  public WsConversationEntity openConversation(UserEntity user) {

    WsConversationEntity conversation = new WsConversationEntity()
        .setWsTopic(this.generateTopicName(user))
        .setUser(user)
        .setStatus(WsConversationStatusEnum.OPEN);

    return conversation;
  }

  private String generateTopicName(UserEntity user) {
    return UUID.randomUUID().toString() + user.getEmail();
  }
}
