package com.maxdlr.p13;

import java.util.ArrayList;
import java.util.List;

import com.maxdlr.p13.entity.RoleEntity;
import com.maxdlr.p13.entity.UserEntity;
import com.maxdlr.p13.enums.RoleEnum;

public class TestUtils {

  public static UserEntity makeUserEntity(Integer id) {
    List<RoleEntity> roles = new ArrayList<>();
    RoleEntity userRole = new RoleEntity().setName(RoleEnum.USER);
    roles.add(userRole);
    return new UserEntity()
        .setId(id)
        .setEmail("max@max.com" + id)
        .setPassword("password" + id)
        .setFirstname("max" + id)
        .setLastname("dlr" + id)
        .setPhoneNumber("0123456789")
        .setIsActive(true)
        .setRoles(roles);
  }
}
