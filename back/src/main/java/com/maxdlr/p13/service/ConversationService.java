package com.maxdlr.p13.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.maxdlr.p13.dto.ConversationRecordInfo;
import com.maxdlr.p13.entity.UserEntity;
import com.maxdlr.p13.entity.ConversationEntity;
import com.maxdlr.p13.enums.ConversationStatusEnum;
import com.maxdlr.p13.mapper.ConversationMapper;
import com.maxdlr.p13.repository.UserRepository;
import com.maxdlr.p13.repository.ConversationRepository;

@Service
public class ConversationService {

  ConversationRepository conversationRepository;
  UserRepository userRepository;
  ConversationMapper conversationMapper;

  ConversationService(
      ConversationRepository conversationRepository,
      UserRepository userRepository,
      ConversationMapper conversationMapper) {
    this.conversationRepository = conversationRepository;
    this.userRepository = userRepository;
    this.conversationMapper = conversationMapper;
  }

  public ConversationEntity openConversation(UserEntity user) {
    ConversationEntity conversation = new ConversationEntity()
        .setWsTopic(this.generateTopicName(user))
        .setUser(user)
        .setStatus(ConversationStatusEnum.OPEN);
    conversation = this.conversationRepository.save(conversation);
    return conversation;
  }

  public List<ConversationRecordInfo> getAllConversations() {
    return this.conversationMapper.toRecordInfo(this.conversationRepository.findAll());
  }

  private String generateTopicName(UserEntity user) {
    return UUID.randomUUID().toString() + "-" + user.getEmail();
  }
}
