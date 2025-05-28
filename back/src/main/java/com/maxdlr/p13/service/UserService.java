package com.maxdlr.p13.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maxdlr.p13.dto.UserRecordInfo;
import com.maxdlr.p13.entity.ConversationEntity;
import com.maxdlr.p13.entity.UserEntity;
import com.maxdlr.p13.exception.UserConversationNotFoundException;
import com.maxdlr.p13.exception.UserNotFoundException;
import com.maxdlr.p13.mapper.UserMapper;
import com.maxdlr.p13.repository.ConversationRepository;
import com.maxdlr.p13.repository.UserRepository;

@Service
public class UserService {

  UserRepository userRepository;
  UserMapper userMapper;
  ConversationRepository conversationRepository;

  public UserService(UserRepository userRepository,
      UserMapper userMapper,
      ConversationRepository conversationRepository) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.conversationRepository = conversationRepository;
  }

  @Transactional(readOnly = true)
  public UserRecordInfo findOneById(Integer id) {
    UserEntity user = this.userRepository.findOneById(id)
        .orElseThrow(() -> new UserNotFoundException("Cannot find user with id: " + id));
    return this.userMapper.toRecordInfo(user);
  }

  @Transactional(readOnly = true)
  public List<UserRecordInfo> findAll() {
    return this.userMapper.toRecordInfo(this.userRepository.findAll());
  }

  @Transactional(readOnly = true)
  public UserRecordInfo findByConversation(Integer conversationId) {
    ConversationEntity conversation = this.conversationRepository.findOneById(conversationId)
        .orElseThrow(
            () -> new UserConversationNotFoundException(("Cannot find user conversation with id: " + conversationId)));

    return this.userMapper.toRecordInfo(conversation.getUser());
  }
}
