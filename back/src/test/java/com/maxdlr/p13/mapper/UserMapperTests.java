package com.maxdlr.p13.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import com.maxdlr.p13.TestUtils;
import com.maxdlr.p13.dto.UserRecordInfo;
import com.maxdlr.p13.entity.RoleEntity;
import com.maxdlr.p13.entity.UserEntity;

@ExtendWith(MockitoExtension.class)
public class UserMapperTests {

  UserMapper userMapper;

  @BeforeEach
  public void setUp() {
    this.userMapper = Mappers.getMapper(UserMapper.class);
  }

  @Test
  public void testToRecordInfo() {
    RoleEntity role = TestUtils.makeRoleEntityAsUser(1);
    UserEntity entity = TestUtils.makeUserEntity(1, role);
    UserRecordInfo info = this.userMapper.toRecordInfo(entity);

    assertNotNull(entity);
    assertNotNull(info);

    assertNotNull(entity.getId());
    assertNotNull(info.getId());

    assertEquals(entity.getId(), info.getId());
    assertEquals(entity.getFirstname(), info.getFirstname());
    assertEquals(entity.getLastname(), info.getLastname());
    assertEquals(entity.getPhoneNumber(), info.getPhoneNumber());
    assertEquals(entity.getIsActive(), info.getIsActive());
    assertEquals(entity.getEmail(), info.getEmail());
  }

  @Test
  public void testToRecordInfoList() {
    RoleEntity role = TestUtils.makeRoleEntityAsUser(1);
    List<UserEntity> entities = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      UserEntity entity = TestUtils.makeUserEntity(i, role);
      entities.add(entity);
    }

    List<UserRecordInfo> infoList = this.userMapper.toRecordInfo(entities);

    for (int i = 0; i < infoList.size() - 1; i++) {
      UserEntity entity = entities.get(i);
      UserRecordInfo info = infoList.get(i);

      assertNotNull(entity);
      assertNotNull(info);

      assertNotNull(entity.getId());
      assertNotNull(info.getId());

      assertEquals(entity.getId(), info.getId());
      assertEquals(entity.getFirstname(), info.getFirstname());
      assertEquals(entity.getLastname(), info.getLastname());
      assertEquals(entity.getPhoneNumber(), info.getPhoneNumber());
      assertEquals(entity.getIsActive(), info.getIsActive());
      assertEquals(entity.getEmail(), info.getEmail());
    }
  }

  @Test
  public void testToEntityFromInfo() {
    UserRecordInfo info = TestUtils.makeUserInfo(1);
    UserEntity entity = this.userMapper.toEntityFromInfo(info);

    assertNotNull(entity);
    assertNotNull(info);

    assertNotNull(entity.getId());
    assertNotNull(info.getId());

    assertEquals(entity.getId(), info.getId());
    assertEquals(entity.getFirstname(), info.getFirstname());
    assertEquals(entity.getLastname(), info.getLastname());
    assertEquals(entity.getPhoneNumber(), info.getPhoneNumber());
    assertEquals(entity.getIsActive(), info.getIsActive());
    assertEquals(entity.getEmail(), info.getEmail());
  }

  @Test
  public void testToEntityFromInfoList() {
    List<UserRecordInfo> infoList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      UserRecordInfo info = TestUtils.makeUserInfo(i);
      infoList.add(info);
    }

    List<UserEntity> entityList = this.userMapper.toEntityFromInfo(infoList);

    for (int i = 0; i < infoList.size() - 1; i++) {
      UserEntity entity = entityList.get(i);
      UserRecordInfo info = infoList.get(i);

      assertNotNull(entity);
      assertNotNull(info);

      assertNotNull(entity.getId());
      assertNotNull(info.getId());

      assertEquals(entity.getId(), info.getId());
      assertEquals(entity.getFirstname(), info.getFirstname());
      assertEquals(entity.getLastname(), info.getLastname());
      assertEquals(entity.getPhoneNumber(), info.getPhoneNumber());
      assertEquals(entity.getIsActive(), info.getIsActive());
      assertEquals(entity.getEmail(), info.getEmail());
    }
  }
}
