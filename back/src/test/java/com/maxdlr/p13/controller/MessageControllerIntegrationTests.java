package com.maxdlr.p13.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.maxdlr.p13.TestUtils;
import com.maxdlr.p13.dto.ConversationRecordInfo;
import com.maxdlr.p13.dto.MessageRecordInfo;
import com.maxdlr.p13.dto.UserRecordInfo;
import com.maxdlr.p13.enums.ConversationStatusEnum;
import com.maxdlr.p13.enums.MessageStatusEnum;
import com.maxdlr.p13.service.MessageService;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@GraphQlTest(controllers = MessageController.class, excludeAutoConfiguration = DataSourceAutoConfiguration.class)
@ExtendWith(MockitoExtension.class)
public class MessageControllerIntegrationTests {

  @Autowired
  GraphQlTester graphQlTester;

  @Mock
  MessageService messageService;

  @Test
  public void testGetMessagesOfUser() {
    UserRecordInfo user = TestUtils.makeUserInfo(1);
    ConversationRecordInfo conversation = TestUtils.makeConversationInfo(1, user.getId(),
        ConversationStatusEnum.USER_ACTIVE);
    String status = MessageStatusEnum.READ.toString();

    List<MessageRecordInfo> messages = Arrays.asList(
        new MessageRecordInfo(1, "message 1", user, conversation, status),
        new MessageRecordInfo(1, "message 1", user, conversation, status));

    when(this.messageService.findAllByUser(user.getId())).thenReturn(messages);

    graphQlTester
        .documentName("getAllMessagesOfUser")
        .variable("userId", user.getId())
        .execute()
        .path("GetAllMessagesOfUser")
        .entityList(MessageRecordInfo.class)
        .hasSize(2)
        .contains(messages.get(0), messages.get(1));

    verify(messageService).findAllByUser(user.getId());
  }

}
