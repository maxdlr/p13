package com.maxdlr.p13.controller;

import java.util.Arrays;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxdlr.p13.entity.ConversationEntity;
import com.maxdlr.p13.entity.RoleEntity;
import com.maxdlr.p13.entity.UserEntity;
import com.maxdlr.p13.enums.ConversationStatusEnum;
import com.maxdlr.p13.enums.RoleEnum;
import com.maxdlr.p13.repository.ConversationRepository;
import com.maxdlr.p13.repository.RoleRepository;
import com.maxdlr.p13.repository.UserRepository;

@RestController
@RequestMapping("fixtures")
public class FixturesController {

  private RoleRepository roleRepository;
  private UserRepository userRepository;
  private ConversationRepository conversationRepository;

  public FixturesController(RoleRepository roleRepository, UserRepository userRepository,
      ConversationRepository conversationRepository) {
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
    this.conversationRepository = conversationRepository;
  }

  @GetMapping("/load")
  public void load() {
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
        .setEmail("admin@support.com")
        .setFirstname("client")
        .setLastname("support")
        .setPassword("password")
        .setPhoneNumber("0123456789")
        .setIsActive(true)
        .setRole(adminRole);

    ConversationEntity conversation = new ConversationEntity().setUser(user).setWsTopic("this-topic-name")
        .setStatus(ConversationStatusEnum.OPEN);

    this.userRepository.saveAll(Arrays.asList(user, admin));
    this.conversationRepository.save(conversation);
  }
}
