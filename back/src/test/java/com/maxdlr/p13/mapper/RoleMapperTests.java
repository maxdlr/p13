package com.maxdlr.p13.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.maxdlr.p13.TestUtils;
import com.maxdlr.p13.dto.RoleRecordInfo;
import com.maxdlr.p13.entity.RoleEntity;

public class RoleMapperTests {

  RoleMapper roleMapper;

  @BeforeEach
  public void setUp() {
    roleMapper = new RoleMapperImpl();
  }

  @Test
  public void toEntityFromInfo() {
    RoleRecordInfo info = TestUtils.makeRoleInfoAsUser(1);
    RoleEntity entity = this.roleMapper.toEntityFromInfo(info);

    assertNotNull(info);
    assertNotNull(entity);

    assertEquals(info.getId(), entity.getId());
    assertEquals(info.getName(), entity.getName().toString());
  }

  @Test
  public void toEntityFromInfoList() {
    List<RoleRecordInfo> infoList = new ArrayList<>();

    RoleRecordInfo userInfo = TestUtils.makeRoleInfoAsUser(1);
    RoleRecordInfo adminInfo = TestUtils.makeRoleInfoAsAdmin(2);
    infoList.add(userInfo);
    infoList.add(adminInfo);

    List<RoleEntity> entityList = this.roleMapper.toEntityFromInfo(infoList);

    for (int i = 0; i < infoList.size() - 1; i++) {
      RoleRecordInfo info = infoList.get(i);
      RoleEntity entity = entityList.get(i);
      assertNotNull(info);
      assertNotNull(entity);

      assertEquals(info.getId(), entity.getId());
      assertEquals(info.getName(), entity.getName().toString());
    }
  }

  @Test
  public void toInfoFromEntity() {
    RoleEntity entity = TestUtils.makeRoleEntityAsUser(1);
    RoleRecordInfo info = this.roleMapper.toRecordInfo(entity);

    assertNotNull(info);
    assertNotNull(entity);

    assertEquals(info.getId(), entity.getId());
    assertEquals(info.getName(), entity.getName().toString());
  }

  @Test
  public void toInfoFromEntityList() {
    List<RoleEntity> entityList = new ArrayList<>();

    RoleEntity userRoleEntity = TestUtils.makeRoleEntityAsUser(1);
    RoleEntity adminRoleEntity = TestUtils.makeRoleEntityAsAdmin(2);

    entityList.add(userRoleEntity);
    entityList.add(adminRoleEntity);

    List<RoleRecordInfo> infoList = this.roleMapper.toRecordInfo(entityList);

    for (int i = 0; i < infoList.size() - 1; i++) {
      RoleRecordInfo info = infoList.get(i);
      RoleEntity entity = entityList.get(i);
      assertNotNull(info);
      assertNotNull(entity);

      assertEquals(info.getId(), entity.getId());
      assertEquals(info.getName(), entity.getName().toString());
    }
  }
}
