package com.maxdlr.p13.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxdlr.p13.dto.RoleRecordInfo;
import com.maxdlr.p13.dto.UserRecordInfo;
import com.maxdlr.p13.entity.RoleEntity;
import com.maxdlr.p13.entity.UserEntity;
import com.maxdlr.p13.enums.RoleEnum;
import com.maxdlr.p13.repository.RoleRepository;
import com.maxdlr.p13.repository.UserRepository;

@RestController
@RequestMapping("fixtures")
public class FixturesController {

  private RoleRepository roleRepository;
  private UserRepository userRepository;

  FixturesController(RoleRepository roleRepository, UserRepository userRepository) {
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
  }

  @GetMapping("/load")
  public void load() {
    RoleEntity userRole = new RoleEntity().setName(RoleEnum.USER);
    RoleEntity adminRole = new RoleEntity().setName(RoleEnum.ADMIN);

    userRole = this.roleRepository.save(userRole);
    adminRole = this.roleRepository.save(adminRole);

    UserEntity user = new UserEntity()
        .setEmail("max@max.com")
        .setFirstname("max")
        .setLastname("dlr")
        .setPassword("password")
        .setPhoneNumber("0123456789")
        .setIsActive(true)
        .setRole(userRole);

    this.userRepository.save(user);
  }
}
