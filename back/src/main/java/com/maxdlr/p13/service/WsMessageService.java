package com.maxdlr.p13.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.maxdlr.p13.dto.WsMessageRecordInfo;
import com.maxdlr.p13.dto.WsMessageRecordInput;
import com.maxdlr.p13.entity.UserEntity;
import com.maxdlr.p13.entity.WsConversationEntity;
import com.maxdlr.p13.entity.WsMessageEntity;
import com.maxdlr.p13.enums.WsMessageStatusEnum;
import com.maxdlr.p13.exception.ConversationNotFoundException;
import com.maxdlr.p13.exception.MessageUserNotFoundException;
import com.maxdlr.p13.mapper.WsMessageMapper;
import com.maxdlr.p13.repository.UserRepository;
import com.maxdlr.p13.repository.WsConversationRepository;
import com.maxdlr.p13.repository.WsMessageRepository;

@Service
public class WsMessageService {

  WsMessageRepository wsMessageRepository;
  WsConversationRepository wsConversationRepository;
  UserRepository userRepository;
  WsConversationService conversationService;
  WsMessageMapper messageMapper;

  WsMessageService(
      WsMessageRepository messageRepository,
      WsConversationRepository conversationRepository,
      UserRepository userRepository,
      WsConversationService conversationService,
      WsMessageMapper messageMapper) {
    this.wsMessageRepository = messageRepository;
    this.wsConversationRepository = conversationRepository;
    this.userRepository = userRepository;
    this.conversationService = conversationService;
    this.messageMapper = messageMapper;
  }

  public WsMessageRecordInfo pushMessage(final WsMessageRecordInput messageInput, final WsMessageStatusEnum status) {
    final Optional<UserEntity> user = this.userRepository.findOneById(messageInput.getUserId());

    WsConversationEntity conversation = null;

    if (messageInput.getConversationId() == null) {
      conversation = this.conversationService.openConversation(user.get());
    } else {
      final Optional<WsConversationEntity> existingConversation = this.wsConversationRepository
          .findOneById(messageInput.getConversationId());

      if (existingConversation.isEmpty()) {
        throw new ConversationNotFoundException(
            "Cannot find conversation with id: " + messageInput.getConversationId());
      }

      conversation = existingConversation.get();
    }

    this.wsConversationRepository.save(conversation);

    if (user.isEmpty()) {
      throw new MessageUserNotFoundException("Cannot user associated with user with id: " + messageInput.getUserId());
    }

    final WsMessageEntity messageEntity = new WsMessageEntity()
        .setContent(messageInput.getContent())
        .setUser(user.get())
        .setWsConversation(conversation)
        .setStatus(status);

    this.wsMessageRepository.save(messageEntity);

    return this.messageMapper.toRecordInfo(messageEntity);
  }

  public List<WsMessageRecordInfo> getAllFromUser(Integer userId) {
    return this.messageMapper.toRecordInfo(this.wsMessageRepository.findAll());
  }
}
