package com.maxdlr.p13.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maxdlr.p13.dto.ConversationRecordInfo;
import com.maxdlr.p13.dto.ConversationRecordInput;
import com.maxdlr.p13.entity.UserEntity;
import com.maxdlr.p13.entity.ConversationEntity;
import com.maxdlr.p13.enums.ConversationStatusEnum;
import com.maxdlr.p13.exception.ConversationNotFoundException;
import com.maxdlr.p13.exception.ConversationUserNotFoundException;
import com.maxdlr.p13.mapper.ConversationMapper;
import com.maxdlr.p13.repository.UserRepository;
import com.maxdlr.p13.value_object.TopicName;
import com.maxdlr.p13.repository.ConversationRepository;

@Service
public class ConversationService {

  ConversationRepository conversationRepository;
  UserRepository userRepository;
  ConversationMapper conversationMapper;

  public ConversationService(
      ConversationRepository conversationRepository,
      UserRepository userRepository,
      ConversationMapper conversationMapper) {
    this.conversationRepository = conversationRepository;
    this.userRepository = userRepository;
    this.conversationMapper = conversationMapper;
  }

  @Transactional
  public ConversationEntity openConversation(ConversationRecordInput conversationInput) {

    UserEntity user = this.userRepository
        .findOneById(conversationInput
            .getUserId())
        .orElseThrow(() -> new ConversationUserNotFoundException(
            "Cannot find conversation user of id : " + conversationInput.getUserId()));

    ConversationEntity conversation = new ConversationEntity()
        .setWsTopic(TopicName.generate(user))
        .setUser(user)
        .setStatus(ConversationStatusEnum.OPEN);
    conversation = this.conversationRepository.save(conversation);
    return conversation;
  }

  @Transactional(readOnly = true)
  public List<ConversationRecordInfo> getAllConversations() {
    return this.conversationMapper.toRecordInfo(this.conversationRepository.findAll());
  }

  @Transactional(readOnly = true)
  public List<ConversationRecordInfo> findByUser(Integer userId) {
    List<ConversationEntity> conversations = this.conversationRepository.findAllByUserId(userId);
    return this.conversationMapper.toRecordInfo(conversations);
  }

  @Transactional(readOnly = true)
  public ConversationRecordInfo findOneById(Integer id) {
    ConversationEntity conversation = this.conversationRepository.findOneById(id)
        .orElseThrow(() -> new ConversationNotFoundException("Cannot find conversation with id: " + id));
    return this.conversationMapper.toRecordInfo(conversation);
  }
}
