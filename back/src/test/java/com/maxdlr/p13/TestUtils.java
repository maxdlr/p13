package com.maxdlr.p13;

import java.util.ArrayList;
import java.util.List;

import com.maxdlr.p13.dto.RoleRecordInfo;
import com.maxdlr.p13.dto.UserRecordInfo;
import com.maxdlr.p13.dto.WsConversationRecordInfo;
import com.maxdlr.p13.entity.RoleEntity;
import com.maxdlr.p13.entity.UserEntity;
import com.maxdlr.p13.entity.WsConversationEntity;
import com.maxdlr.p13.enums.RoleEnum;
import com.maxdlr.p13.enums.WsConversationStatusEnum;

public class TestUtils {

  public static UserEntity makeUserEntity(Integer id) {
    RoleEntity userRole = makeRoleEntityAsUser(1);
    return new UserEntity()
        .setId(id)
        .setEmail("max@max.com" + id)
        .setPassword("password" + id)
        .setFirstname("max" + id)
        .setLastname("dlr" + id)
        .setPhoneNumber("0123456789")
        .setIsActive(true)
        .setRole(userRole);
  }

  public static UserRecordInfo makeUserInfo(Integer id) {
    RoleRecordInfo userRoleInfo = makeRoleInfoAsUser(id);
    return new UserRecordInfo(
        id,
        "max@max.com" + id,
        "max" + id,
        "dlr" + id,
        "0123456789",
        true,
        userRoleInfo);
  }

  public static RoleEntity makeRoleEntityAsUser(Integer id) {
    return new RoleEntity().setId(id).setName(RoleEnum.USER);
  }

  public static RoleEntity makeRoleEntityAsAdmin(Integer id) {
    return new RoleEntity().setId(id).setName(RoleEnum.ADMIN);
  }

  public static RoleRecordInfo makeRoleInfoAsUser(Integer id) {
    return new RoleRecordInfo(id, RoleEnum.USER.toString());
  }

  public static RoleRecordInfo makeRoleInfoAsAdmin(Integer id) {
    return new RoleRecordInfo(id, RoleEnum.ADMIN.toString());
  }

  public static WsConversationEntity makeWsConversationEntity(Integer id, Integer userId,
      WsConversationStatusEnum status) {
    return new WsConversationEntity()
        .setId(id)
        .setWsTopic("topic" + id)
        .setUser(makeUserEntity(userId))
        .setStatus(status);
  }

  public static WsConversationRecordInfo makeWsConversationInfo(Integer id, Integer userId, String status) {
    return new WsConversationRecordInfo(id, "topic" + id, makeUserInfo(userId), status);
  }
}
