package com.maxdlr.p13.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.maxdlr.p13.TestUtils;
import com.maxdlr.p13.dto.WsConversationRecordInfo;
import com.maxdlr.p13.entity.WsConversationEntity;
import com.maxdlr.p13.enums.WsConversationStatusEnum;

public class WsConversationMapperTests {

  WsConversationMapperImpl wsConversationMapper;

  @BeforeEach
  public void setUp() {
    wsConversationMapper = new WsConversationMapperImpl();
  }

  @Test
  public void toEntityFromRecordInfo() {
    WsConversationRecordInfo info = TestUtils.makeWsConversationInfo(1, 1,
        WsConversationStatusEnum.USER_ACTIVE.toString());

    WsConversationEntity entity = this.wsConversationMapper.toEntityFromInfo(info);

    assertNotNull(info);
    assertNotNull(entity);

    assertEquals(info.getId(), entity.getId());
    assertEquals(info.getUser().getId(), entity.getUser().getId());
    assertEquals(info.getStatus(), entity.getStatus().toString());
    assertEquals(info.getWsTopic(), entity.getWsTopic());
  }

  @Test
  public void toEntityFromRecordInfoList() {
    List<WsConversationRecordInfo> infoList = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      infoList.add(TestUtils.makeWsConversationInfo(i, 1, WsConversationStatusEnum.USER_ACTIVE.toString()));
    }

    List<WsConversationEntity> entityList = this.wsConversationMapper.toEntityFromInfo(infoList);

    for (int i = 0; i < infoList.size() - 1; i++) {
      WsConversationRecordInfo info = infoList.get(i);
      WsConversationEntity entity = entityList.get(i);
      assertNotNull(info);
      assertNotNull(entity);

      assertEquals(info.getId(), entity.getId());
      assertEquals(info.getUser().getId(), entity.getUser().getId());
      assertEquals(info.getStatus(), entity.getStatus().toString());
      assertEquals(info.getWsTopic(), entity.getWsTopic());
    }
  }

  @Test
  public void toRecordInfoFromEntity() {
    WsConversationEntity entity = TestUtils.makeWsConversationEntity(1, 1, WsConversationStatusEnum.ADMIN_ACTIVE);

    WsConversationRecordInfo info = this.wsConversationMapper.toRecordInfo(entity);

    assertNotNull(info);
    assertNotNull(entity);

    assertEquals(info.getId(), entity.getId());
    assertEquals(info.getUser().getId(), entity.getUser().getId());
    assertEquals(info.getStatus(), entity.getStatus().toString());
    assertEquals(info.getWsTopic(), entity.getWsTopic());
  }

  @Test
  public void toRecordInfoFromEntityList() {
    List<WsConversationEntity> entityList = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      entityList.add(TestUtils.makeWsConversationEntity(i, 1, WsConversationStatusEnum.USER_ACTIVE));
    }

    List<WsConversationRecordInfo> infoList = this.wsConversationMapper.toRecordInfo(entityList);

    for (int i = 0; i < entityList.size() - 1; i++) {
      WsConversationRecordInfo info = infoList.get(i);
      WsConversationEntity entity = entityList.get(i);
      assertNotNull(info);
      assertNotNull(entity);

      assertEquals(info.getId(), entity.getId());
      assertEquals(info.getUser().getId(), entity.getUser().getId());
      assertEquals(info.getStatus(), entity.getStatus().toString());
      assertEquals(info.getWsTopic(), entity.getWsTopic());
    }
  }
}
