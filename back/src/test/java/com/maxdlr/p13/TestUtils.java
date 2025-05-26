package com.maxdlr.p13;

import java.util.ArrayList;
import java.util.List;

import com.maxdlr.p13.dto.RoleRecordInfo;
import com.maxdlr.p13.dto.UserRecordInfo;
import com.maxdlr.p13.dto.ConversationRecordInfo;
import com.maxdlr.p13.dto.MessageRecordInfo;
import com.maxdlr.p13.entity.RoleEntity;
import com.maxdlr.p13.entity.UserEntity;
import com.maxdlr.p13.entity.ConversationEntity;
import com.maxdlr.p13.entity.MessageEntity;
import com.maxdlr.p13.enums.RoleEnum;
import com.maxdlr.p13.enums.ConversationStatusEnum;
import com.maxdlr.p13.enums.MessageStatusEnum;
import com.sun.jna.platform.win32.Netapi32Util.UserInfo;

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

  public static ConversationEntity makeConversationEntity(Integer id, Integer userId,
      ConversationStatusEnum status) {
    return new ConversationEntity()
        .setId(id)
        .setWsTopic("topic" + id)
        .setUser(makeUserEntity(userId))
        .setStatus(status);
  }

  public static ConversationRecordInfo makeConversationInfo(Integer id, Integer userId,
      ConversationStatusEnum status) {
    return new ConversationRecordInfo(id, "topic" + id, makeUserInfo(userId), status.toString());
  }

  public static MessageEntity makeMessageEntity(Integer id, Integer userId, Integer conversationId,
      MessageStatusEnum status) {
    return new MessageEntity()
        .setId(id)
        .setContent("content" + id)
        .setUser(makeUserEntity(userId))
        .setConversation(makeConversationEntity(conversationId, userId, ConversationStatusEnum.USER_ACTIVE))
        .setStatus(MessageStatusEnum.SENT);
  }

  public static MessageRecordInfo makeMessageInfo(
      Integer id,
      Integer userId,
      Integer conversationId,
      MessageStatusEnum status) {
    return new MessageRecordInfo(
        id,
        "content" + id,
        makeUserInfo(userId),
        makeConversationInfo(
            conversationId,
            userId,
            ConversationStatusEnum.USER_ACTIVE),
        status.toString());
  }
}
