package com.maxdlr.p13.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.maxdlr.p13.dto.MessageRecordInfo;
import com.maxdlr.p13.dto.MessageRecordInput;
import com.maxdlr.p13.entity.UserEntity;
import com.maxdlr.p13.entity.ConversationEntity;
import com.maxdlr.p13.entity.MessageEntity;
import com.maxdlr.p13.enums.MessageStatusEnum;
import com.maxdlr.p13.exception.ConversationNotFoundException;
import com.maxdlr.p13.exception.MessageUserNotFoundException;
import com.maxdlr.p13.mapper.MessageMapper;
import com.maxdlr.p13.repository.UserRepository;
import com.maxdlr.p13.repository.ConversationRepository;
import com.maxdlr.p13.repository.MessageRepository;

@Service
public class MessageService {

  MessageRepository messageRepository;
  ConversationRepository conversationRepository;
  UserRepository userRepository;
  ConversationService conversationService;
  MessageMapper messageMapper;

  MessageService(
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

  public MessageRecordInfo pushMessage(final MessageRecordInput messageInput, final MessageStatusEnum status) {
    UserEntity user = this.userRepository.findOneById(messageInput.getUserId())
        .orElseThrow(() -> new MessageUserNotFoundException(
            "Cannot user associated with user with id: " + messageInput.getUserId()));

    ConversationEntity conversation = null;

    if (messageInput.getConversationId() == null) {
      conversation = this.conversationService.openConversation(user);
    } else {
      conversation = this.conversationRepository
          .findOneById(messageInput.getConversationId())
          .orElseThrow(() -> new ConversationNotFoundException(
              "Cannot find conversation with id: " + messageInput.getConversationId()));
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

  public List<MessageRecordInfo> getAllFromUser(Integer userId) {
    return this.messageMapper.toRecordInfo(this.messageRepository.findByUserId(userId));
  }
}
