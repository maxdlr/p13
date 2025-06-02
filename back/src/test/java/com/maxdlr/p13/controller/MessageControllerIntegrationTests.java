package com.maxdlr.p13.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.maxdlr.p13.dto.MessageRecordInput;
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
                query GetAllMessagesOfUser($userId: ID!) {
                      GetAllMessagesOfUser(userId: $userId) {
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
        .variable("userId", this.testUser.getId())
        .execute()
        .path("GetAllMessagesOfUser")
        .entityList(MessageRecordInfo.class)
        .containsExactly(this.expectedMessagesDto.toArray(new MessageRecordInfo[0]));
  }

  @Test
  public void testGetMessage() {
    tester
        .document("""
            query GetMessage($id: ID!) {
              GetMessage(id: $id) {
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
        .variable("id", this.savedMessagesEntities.getFirst().getId())
        .execute()
        .path("GetMessage")
        .entity(MessageRecordInfo.class)
        .isEqualTo(this.expectedMessagesDto.getFirst());
  }

  @Test
  public void testGetAllMessagesOfConversation() {
    tester
        .document("""
            query GetAllMessagesOfConversation($conversationId: ID!) {
              GetAllMessagesOfConversation(conversationId: $conversationId) {
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
        .variable("conversationId", this.testConversation.getId())
        .execute()
        .path("GetAllMessagesOfConversation")
        .entityList(MessageRecordInfo.class)
        .containsExactly(this.expectedMessagesDto.toArray(new MessageRecordInfo[0]));
  }

  @Test
  public void testCreateMessage() {

    Map<String, String> testMessage = new HashMap<String, String>();
    testMessage.put("content", "my test content");
    testMessage.put("userId", this.testUser.getId().toString());
    testMessage.put("conversationId", this.testConversation.getId().toString());

    tester.document("""
        mutation CreateMessage($message: MessageInput!) {
          CreateMessage(message: $message) {
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
        .variable("message", testMessage)
        .execute()
        .path("CreateMessage")
        .entity(MessageRecordInfo.class)
        .satisfies(createdMessage -> {
          assertNotNull(createdMessage);
          assertEquals("my test content", createdMessage.content());
          assertEquals(createdMessage.user().id(), this.testUser.getId());
          assertEquals(createdMessage.conversation().id(), this.testConversation.getId());
        });
  }

  @Test
  public void testCreateMessage_MessageUserNotFoundException() {
    Map<String, String> testMessage = new HashMap<String, String>();
    testMessage.put("content", "my test content");
    testMessage.put("userId", "999");
    testMessage.put("conversationId", this.testConversation.getId().toString());

    tester.document("""
        mutation CreateMessage($message: MessageInput!) {
          CreateMessage(message: $message) {
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
        .variable("message", testMessage)
        .execute()
        .errors()
        .expect(error -> error.getMessage().contains("Cannot find message user with id: 999"));
  }

  @Test
  public void testCreateMessage_MessageConversationNotFoundException() {
    Map<String, String> testMessage = new HashMap<String, String>();
    testMessage.put("content", "my test content");
    testMessage.put("userId", this.testUser.getId().toString());
    testMessage.put("conversationId", "999");

    tester.document("""
        mutation CreateMessage($message: MessageInput!) {
          CreateMessage(message: $message) {
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
        .variable("message", testMessage)
        .execute()
        .errors()
        .expect(error -> error.getMessage().contains("Cannot find message conversation with id: 999"));
  }

  @Test
  public void testCreateMessage_WithNullConversationId() {
    Map<String, String> testMessage = new HashMap<String, String>();
    testMessage.put("content", "my test content");
    testMessage.put("userId", this.testUser.getId().toString());
    // testMessage.put("conversationId", "caca");

    tester.document("""
        mutation CreateMessage($message: MessageInput!) {
          CreateMessage(message: $message) {
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
        .variable("message", testMessage)
        .execute()
        .path("CreateMessage")
        .entity(MessageRecordInfo.class)
        .satisfies(createdMessage -> {
          assertNotNull(createdMessage);
          assertEquals("my test content", createdMessage.content());
          assertEquals(createdMessage.user().id(), this.testUser.getId());
        });
  }

  @Test
  public void testGetMessage_MessageNotFoundException() {
    tester
        .document("""
            query GetMessage($id: ID!) {
              GetMessage(id: $id) {
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
        .variable("id", "999")
        .execute()
        .errors()
        .expect(error -> error.getMessage().contains("Cannot find message with id: 999"));
  }
}
