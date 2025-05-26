package com.maxdlr.p13.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.maxdlr.p13.TestUtils;
import com.maxdlr.p13.dto.MessageRecordInfo;
import com.maxdlr.p13.entity.MessageEntity;
import com.maxdlr.p13.enums.MessageStatusEnum;

public class MessageMapperTests {

  MessageMapperImpl messageMapper;

  @BeforeEach
  public void setUp() {
    this.messageMapper = new MessageMapperImpl();
  }

  @Test
  public void toEntityFromRecordInfo() {
    MessageRecordInfo info = TestUtils.makeMessageInfo(1, 1, 1,
        MessageStatusEnum.SENT);

    MessageEntity entity = this.messageMapper.toEntityFromInfo(info);

    assertNotNull(info);
    assertNotNull(entity);

    assertEquals(info.getId(), entity.getId());
    assertEquals(info.getUser().getId(), entity.getUser().getId());
    assertEquals(info.getStatus(), entity.getStatus().toString());
    assertEquals(info.getContent(), entity.getContent());
    assertEquals(info.getConversation().getId(), entity.getConversation().getId());
  }

  @Test
  public void toEntityFromRecordInfoList() {
    List<MessageRecordInfo> infoList = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      infoList.add(TestUtils.makeMessageInfo(i, 1, 1, MessageStatusEnum.SENT));
    }

    List<MessageEntity> entityList = this.messageMapper.toEntityFromInfo(infoList);

    for (int i = 0; i < infoList.size() - 1; i++) {
      MessageRecordInfo info = infoList.get(i);
      MessageEntity entity = entityList.get(i);
      assertNotNull(info);
      assertNotNull(entity);

      assertEquals(info.getId(), entity.getId());
      assertEquals(info.getUser().getId(), entity.getUser().getId());
      assertEquals(info.getStatus(), entity.getStatus().toString());
      assertEquals(info.getContent(), entity.getContent());
      assertEquals(info.getConversation().getId(), entity.getConversation().getId());
    }
  }

  @Test
  public void toRecordInfoFromEntity() {
    MessageEntity entity = TestUtils.makeMessageEntity(1, 1, 1, MessageStatusEnum.SENT);

    MessageRecordInfo info = this.messageMapper.toRecordInfo(entity);

    assertNotNull(info);
    assertNotNull(entity);

    assertEquals(info.getId(), entity.getId());
    assertEquals(info.getUser().getId(), entity.getUser().getId());
    assertEquals(info.getStatus(), entity.getStatus().toString());
    assertEquals(info.getContent(), entity.getContent());
    assertEquals(info.getConversation().getId(), entity.getConversation().getId());
  }

  @Test
  public void toRecordInfoFromEntityList() {
    List<MessageEntity> entityList = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      entityList.add(TestUtils.makeMessageEntity(i, 1, 1, MessageStatusEnum.SENT));
    }

    List<MessageRecordInfo> infoList = this.messageMapper.toRecordInfo(entityList);

    for (int i = 0; i < entityList.size() - 1; i++) {
      MessageRecordInfo info = infoList.get(i);
      MessageEntity entity = entityList.get(i);
      assertNotNull(info);
      assertNotNull(entity);

      assertEquals(info.getId(), entity.getId());
      assertEquals(info.getUser().getId(), entity.getUser().getId());
      assertEquals(info.getStatus(), entity.getStatus().toString());
      assertEquals(info.getContent(), entity.getContent());
      assertEquals(info.getConversation().getId(), entity.getConversation().getId());
    }
  }
}
