package com.maxdlr.p13.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.ActiveProfiles;

import com.maxdlr.p13.TestUtils;
import com.maxdlr.p13.dto.MessageRecordInfo;
import com.maxdlr.p13.entity.ConversationEntity;
import com.maxdlr.p13.entity.MessageEntity;
import com.maxdlr.p13.entity.RoleEntity;
import com.maxdlr.p13.entity.UserEntity;
import com.maxdlr.p13.enums.ConversationStatusEnum;
import com.maxdlr.p13.enums.MessageStatusEnum;
import com.maxdlr.p13.mapper.MessageMapper;
import com.maxdlr.p13.repository.ConversationRepository;
import com.maxdlr.p13.repository.MessageRepository;
import com.maxdlr.p13.repository.UserRepository;
import com.maxdlr.p13.service.ConversationService;

@SpringBootTest
@AutoConfigureGraphQlTester
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class MessageControllerIntegrationTests {

  @Autowired
  GraphQlTester tester;

  @Mock
  ConversationService conversationService;

  @Autowired
  MessageRepository messageRepository;

  @Autowired
  MessageMapper messageMapper;

  @Autowired
  UserRepository userRepository;

  @Autowired
  ConversationRepository conversationRepository;

  private UserEntity testUser;
  private ConversationEntity testConversation;
  private List<MessageRecordInfo> expectedMessagesDto;
  private List<MessageEntity> savedMessagesEntities;

  @BeforeEach
  public void setUp() {
    this.messageRepository.deleteAll();
    this.conversationRepository.deleteAll();
    this.userRepository.deleteAll();

    RoleEntity testRole = TestUtils.makeRoleEntityAsUser();
    this.testUser = TestUtils.makeUserEntity(testRole);
    this.testUser = this.userRepository.save(testUser);

    this.testConversation = TestUtils.makeConversationEntity(this.testUser, ConversationStatusEnum.USER_ACTIVE);
    this.testConversation = this.conversationRepository.save(this.testConversation);

    MessageEntity msg1 = TestUtils.makeMessageEntity(this.testUser, this.testConversation,
        MessageStatusEnum.READ);
    MessageEntity msg2 = TestUtils.makeMessageEntity(this.testUser, this.testConversation,
        MessageStatusEnum.READ);

    this.savedMessagesEntities = this.messageRepository.saveAll(Arrays.asList(msg1, msg2));

    this.expectedMessagesDto = this.messageMapper.toRecordInfo(this.savedMessagesEntities);
  }

  @AfterEach
  public void tearDown() {

    this.messageRepository.deleteAll();
    this.conversationRepository.deleteAll();
    this.userRepository.deleteAll();
  }

  @Test
  public void testGetMessagesOfUser() {
    tester
        .document(
            """
                query {
                  GetAllMessagesOfUser(userId: 1) {
                    id
                    content
                    user {
                      id
                      email
                      firstname
                      lastname
                      phoneNumber
                      isActive
                      role {
                        id
                        name
                      }
                    }
                    conversation {
                      id
                      wsTopic
                      user {
                        id
                        email
                        firstname
                        lastname
                        phoneNumber
                        isActive
                        role {
                          id
                          name
                        }
                      }
                      status
                    }
                    status
                  }
                }
                """)
        .execute()
        .path("GetAllMessagesOfUser")
        .entityList(MessageRecordInfo.class)
        .containsExactly(this.expectedMessagesDto.toArray(new MessageRecordInfo[0]));
  }

  @Test
  public void testGetMessage() {
    tester
        .document("""
            query GetMessage($id: ID!) { # Declare the variable in the query
              GetMessage(id: $id) { # Use the variable
                id
                content
                user {
                  id
                  email
                  firstname
                  lastname
                  phoneNumber
                  isActive
                  role {
                    id
                    name
                  }
                }
                conversation {
                  id
                  wsTopic
                  user {
                    id
                    email
                    firstname
                    lastname
                    phoneNumber
                    isActive
                    role {
                      id
                      name
                    }
                  }
                  status
                }
                status
              }
            }
            """)
        .variable("id", this.savedMessagesEntities.get(0).getId())
        .execute()
        .path("GetMessage")
        .entity(MessageRecordInfo.class)
        .isEqualTo(this.expectedMessagesDto.get(0));

  }
}
