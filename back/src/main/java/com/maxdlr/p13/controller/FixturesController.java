package com.maxdlr.p13.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxdlr.p13.dto.ConversationRecordInfo;
import com.maxdlr.p13.dto.ConversationRecordInput;
import com.maxdlr.p13.dto.UserRecordInfo;
import com.maxdlr.p13.dto.UserRecordInput;
import com.maxdlr.p13.entity.ConversationEntity;
import com.maxdlr.p13.entity.RoleEntity;
import com.maxdlr.p13.entity.UserEntity;
import com.maxdlr.p13.enums.ConversationStatusEnum;
import com.maxdlr.p13.enums.RoleEnum;
import com.maxdlr.p13.mapper.ConversationMapper;
import com.maxdlr.p13.mapper.UserMapper;
import com.maxdlr.p13.repository.ConversationRepository;
import com.maxdlr.p13.repository.RoleRepository;
import com.maxdlr.p13.repository.UserRepository;
import com.maxdlr.p13.service.ConversationService;
import com.maxdlr.p13.service.WsService;

@RestController
@RequestMapping("api/fixtures")
public class FixturesController {

  private RoleRepository roleRepository;
  private UserRepository userRepository;
  private ConversationRepository conversationRepository;
  private ConversationService conversationService;
  private UserMapper userMapper;
  private ConversationMapper conversationMapper;
  private WsService wsService;

  public FixturesController(RoleRepository roleRepository, UserRepository userRepository,
      ConversationRepository conversationRepository, ConversationService conversationService, UserMapper userMapper,
      ConversationMapper conversationMapper, WsService wsService) {
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
    this.conversationRepository = conversationRepository;
    this.conversationService = conversationService;
    this.userMapper = userMapper;
    this.conversationMapper = conversationMapper;
    this.wsService = wsService;
  }

  @GetMapping("/load")
  public ResponseEntity<List<UserRecordInfo>> load() {
    List<UserEntity> existingUsers = this.userRepository.findAll();

    if (existingUsers.isEmpty()) {
      RoleEntity userRole = new RoleEntity().setName(RoleEnum.USER);
      RoleEntity adminRole = new RoleEntity().setName(RoleEnum.ADMIN);

      this.roleRepository.saveAll(Arrays.asList(userRole, adminRole));

      UserEntity user = new UserEntity()
          .setEmail("max@max.com")
          .setFirstname("max")
          .setLastname("dlr")
          .setPassword("password")
          .setPhoneNumber("0123456789")
          .setIsActive(true)
          .setRole(userRole);

      UserEntity admin = new UserEntity()
          .setEmail("admin@yourcaryourway.com")
          .setFirstname("admin")
          .setLastname("yourcaryourway")
          .setPassword("password")
          .setPhoneNumber("0123456789")
          .setIsActive(true)
          .setRole(adminRole);

      List<UserEntity> createdUsers = this.userRepository.saveAll(Arrays.asList(user, admin));
      List<UserRecordInfo> createdUserInfos = this.userMapper.toRecordInfo(createdUsers);

      ConversationEntity conversation = this.conversationService
          .openConversation(new ConversationRecordInput(user.getId()));
      this.conversationRepository.save(conversation);
      return ResponseEntity.ok(createdUserInfos);
    }

    return ResponseEntity.ok(this.userMapper.toRecordInfo(existingUsers));
  }
}
