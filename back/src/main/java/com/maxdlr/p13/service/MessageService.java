package com.maxdlr.p13.service;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maxdlr.p13.dto.ConversationRecordInput;
import com.maxdlr.p13.dto.MessageRecordInfo;
import com.maxdlr.p13.dto.MessageRecordInput;
import com.maxdlr.p13.entity.ConversationEntity;
import com.maxdlr.p13.entity.MessageEntity;
import com.maxdlr.p13.entity.UserEntity;
import com.maxdlr.p13.enums.MessageStatusEnum;
import com.maxdlr.p13.exception.ConversationNotFoundException;
import com.maxdlr.p13.exception.MessageNotFoundException;
import com.maxdlr.p13.exception.MessageUserNotFoundException;
import com.maxdlr.p13.mapper.MessageMapper;
import com.maxdlr.p13.repository.ConversationRepository;
import com.maxdlr.p13.repository.MessageRepository;
import com.maxdlr.p13.repository.UserRepository;

@Service
public class MessageService {

  MessageRepository messageRepository;
  ConversationRepository conversationRepository;
  UserRepository userRepository;
  ConversationService conversationService;
  MessageMapper messageMapper;

  public MessageService(
      MessageRepository messageRepository,
      ConversationRepository conversationRepository,
      UserRepository userRepository,
      ConversationService conversationService,
      MessageMapper messageMapper) {
    this.messageRepository = messageRepository;
    this.conversationRepository = conversationRepository;
    this.userRepository = userRepository;
    this.conversationService = conversationService;
    this.messageMapper = messageMapper;
  }

  @Transactional(readOnly = true)
  public MessageRecordInfo push(final MessageRecordInput messageInput, final MessageStatusEnum status) {
    UserEntity user = this.userRepository.findOneById(messageInput.getUserId())
        .orElseThrow(() -> new MessageUserNotFoundException(
            "Cannot find message user with id: " + messageInput.getUserId()));

    ConversationEntity conversation = null;

    if (messageInput.getConversationId() == null) {
      conversation = this.conversationService.openConversation(new ConversationRecordInput(user.getId()));
    } else {
      conversation = this.conversationRepository
          .findOneById(messageInput.getConversationId())
          .orElseThrow(() -> new ConversationNotFoundException(
              "Cannot find message conversation with id: " + messageInput.getConversationId()));
    }

    conversation = this.conversationRepository.save(conversation);

    MessageEntity messageEntity = new MessageEntity()
        .setContent(messageInput.getContent())
        .setUser(user)
        .setConversation(conversation)
        .setStatus(status);

    messageEntity = this.messageRepository.save(messageEntity);

    return this.messageMapper.toRecordInfo(messageEntity);
  }

  @Transactional
  public List<MessageRecordInfo> findAllByUser(Integer userId) {
    return this.messageMapper.toRecordInfo(this.messageRepository.findByUserId(userId));
  }

  @Transactional
  public MessageRecordInfo findOneById(Integer id) {
    MessageEntity message = this.messageRepository.findOneById(id)
        .orElseThrow(() -> new MessageNotFoundException("Cannot find message with id: " + id));
    return this.messageMapper.toRecordInfo(message);
  }

  @Transactional
  public List<MessageRecordInfo> findAllByConversation(Integer conversationId) {
    List<MessageEntity> messages = this.messageRepository.findByConversationId(conversationId);
    return this.messageMapper.toRecordInfo(messages);
  }
}
