package com.maxdlr.p13.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.maxdlr.p13.TestUtils;
import com.maxdlr.p13.dto.WsMessageRecordInfo;
import com.maxdlr.p13.entity.WsMessageEntity;
import com.maxdlr.p13.enums.WsMessageStatusEnum;

public class WsMessageMapperTests {

  WsMessageMapperImpl wsMessageMapper;

  @BeforeEach
  public void setUp() {
    this.wsMessageMapper = new WsMessageMapperImpl();
  }

  @Test
  public void toEntityFromRecordInfo() {
    WsMessageRecordInfo info = TestUtils.makeWsMessageInfo(1, 1, 1,
        WsMessageStatusEnum.SENT);

    WsMessageEntity entity = this.wsMessageMapper.toEntityFromInfo(info);

    assertNotNull(info);
    assertNotNull(entity);

    assertEquals(info.getId(), entity.getId());
    assertEquals(info.getUser().getId(), entity.getUser().getId());
    assertEquals(info.getStatus(), entity.getStatus().toString());
    assertEquals(info.getContent(), entity.getContent());
    assertEquals(info.getWsConversation().getId(), entity.getWsConversation().getId());
  }

  @Test
  public void toEntityFromRecordInfoList() {
    List<WsMessageRecordInfo> infoList = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      infoList.add(TestUtils.makeWsMessageInfo(i, 1, 1, WsMessageStatusEnum.SENT));
    }

    List<WsMessageEntity> entityList = this.wsMessageMapper.toEntityFromInfo(infoList);

    for (int i = 0; i < infoList.size() - 1; i++) {
      WsMessageRecordInfo info = infoList.get(i);
      WsMessageEntity entity = entityList.get(i);
      assertNotNull(info);
      assertNotNull(entity);

      assertEquals(info.getId(), entity.getId());
      assertEquals(info.getUser().getId(), entity.getUser().getId());
      assertEquals(info.getStatus(), entity.getStatus().toString());
      assertEquals(info.getContent(), entity.getContent());
      assertEquals(info.getWsConversation().getId(), entity.getWsConversation().getId());
    }
  }

  @Test
  public void toRecordInfoFromEntity() {
    WsMessageEntity entity = TestUtils.makeWsMessageEntity(1, 1, 1, WsMessageStatusEnum.SENT);

    WsMessageRecordInfo info = this.wsMessageMapper.toRecordInfo(entity);

    assertNotNull(info);
    assertNotNull(entity);

    assertEquals(info.getId(), entity.getId());
    assertEquals(info.getUser().getId(), entity.getUser().getId());
    assertEquals(info.getStatus(), entity.getStatus().toString());
    assertEquals(info.getContent(), entity.getContent());
    assertEquals(info.getWsConversation().getId(), entity.getWsConversation().getId());
  }

  @Test
  public void toRecordInfoFromEntityList() {
    List<WsMessageEntity> entityList = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      entityList.add(TestUtils.makeWsMessageEntity(i, 1, 1, WsMessageStatusEnum.SENT));
    }

    List<WsMessageRecordInfo> infoList = this.wsMessageMapper.toRecordInfo(entityList);

    for (int i = 0; i < entityList.size() - 1; i++) {
      WsMessageRecordInfo info = infoList.get(i);
      WsMessageEntity entity = entityList.get(i);
      assertNotNull(info);
      assertNotNull(entity);

      assertEquals(info.getId(), entity.getId());
      assertEquals(info.getUser().getId(), entity.getUser().getId());
      assertEquals(info.getStatus(), entity.getStatus().toString());
      assertEquals(info.getContent(), entity.getContent());
      assertEquals(info.getWsConversation().getId(), entity.getWsConversation().getId());
    }
  }
}
