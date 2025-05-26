package com.maxdlr.p13.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.maxdlr.p13.TestUtils;
import com.maxdlr.p13.dto.ConversationRecordInfo;
import com.maxdlr.p13.entity.ConversationEntity;
import com.maxdlr.p13.enums.ConversationStatusEnum;

public class ConversationMapperTests {

  ConversationMapper conversationMapper;

  @BeforeEach
  public void setUp() {
    this.conversationMapper = new ConversationMapperImpl();
  }

  @Test
  public void toEntityFromRecordInfo() {
    ConversationRecordInfo info = TestUtils.makeConversationInfo(1, 1,
        ConversationStatusEnum.USER_ACTIVE);

    ConversationEntity entity = this.conversationMapper.toEntityFromInfo(info);

    assertNotNull(info);
    assertNotNull(entity);

    assertEquals(info.getId(), entity.getId());
    assertEquals(info.getUser().getId(), entity.getUser().getId());
    assertEquals(info.getStatus(), entity.getStatus().toString());
    assertEquals(info.getWsTopic(), entity.getWsTopic());
  }

  @Test
  public void toEntityFromRecordInfoList() {
    List<ConversationRecordInfo> infoList = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      infoList.add(TestUtils.makeConversationInfo(i, 1, ConversationStatusEnum.USER_ACTIVE));
    }

    List<ConversationEntity> entityList = this.conversationMapper.toEntityFromInfo(infoList);

    for (int i = 0; i < infoList.size() - 1; i++) {
      ConversationRecordInfo info = infoList.get(i);
      ConversationEntity entity = entityList.get(i);
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
    ConversationEntity entity = TestUtils.makeConversationEntity(1, 1, ConversationStatusEnum.ADMIN_ACTIVE);

    ConversationRecordInfo info = this.conversationMapper.toRecordInfo(entity);

    assertNotNull(info);
    assertNotNull(entity);

    assertEquals(info.getId(), entity.getId());
    assertEquals(info.getUser().getId(), entity.getUser().getId());
    assertEquals(info.getStatus(), entity.getStatus().toString());
    assertEquals(info.getWsTopic(), entity.getWsTopic());
  }

  @Test
  public void toRecordInfoFromEntityList() {
    List<ConversationEntity> entityList = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      entityList.add(TestUtils.makeConversationEntity(i, 1, ConversationStatusEnum.USER_ACTIVE));
    }

    List<ConversationRecordInfo> infoList = this.conversationMapper.toRecordInfo(entityList);

    for (int i = 0; i < entityList.size() - 1; i++) {
      ConversationRecordInfo info = infoList.get(i);
      ConversationEntity entity = entityList.get(i);
      assertNotNull(info);
      assertNotNull(entity);

      assertEquals(info.getId(), entity.getId());
      assertEquals(info.getUser().getId(), entity.getUser().getId());
      assertEquals(info.getStatus(), entity.getStatus().toString());
      assertEquals(info.getWsTopic(), entity.getWsTopic());
    }
  }
}
