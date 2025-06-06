package com.maxdlr.p13.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.ActiveProfiles;

import com.maxdlr.p13.TestUtils;
import com.maxdlr.p13.dto.UserRecordInfo;
import com.maxdlr.p13.entity.ConversationEntity;
import com.maxdlr.p13.entity.UserEntity;
import com.maxdlr.p13.enums.ConversationStatusEnum;
import com.maxdlr.p13.mapper.UserMapper;
import com.maxdlr.p13.repository.ConversationRepository;
import com.maxdlr.p13.repository.UserRepository;

@SpringBootTest
@AutoConfigureGraphQlTester
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UserControllerIntegrationTests {

  @Autowired
  GraphQlTester tester;

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserMapper userMapper;

  @Autowired
  ConversationRepository conversationRepository;

  private ConversationEntity testConversation;

  private List<UserRecordInfo> expectedUsers;
  private List<UserEntity> savedUsers;

  @BeforeEach
  public void setUp() {
    this.conversationRepository.deleteAll();
    this.userRepository.deleteAll();

    UserEntity user1 = TestUtils.makeUserEntity(TestUtils.makeRoleEntityAsUser());
    UserEntity user2 = TestUtils.makeUserEntity(TestUtils.makeRoleEntityAsAdmin());

    this.savedUsers = this.userRepository.saveAll(Arrays.asList(user1, user2));

    this.testConversation = TestUtils.makeConversationEntity(this.savedUsers.getFirst(),
        ConversationStatusEnum.USER_ACTIVE);
    this.conversationRepository.save(this.testConversation);

    this.expectedUsers = this.userMapper.toRecordInfo(this.savedUsers);
  }

  @Test
  public void testGetUser() {
    tester
        .document(
            """
                query GetUser($id: ID!) {
                      GetUser(id: $id) {
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
                    }
                    """)
        .variable("id", this.savedUsers.getFirst().getId())
        .execute()
        .path("GetUser")
        .entity(UserRecordInfo.class)
        .isEqualTo(this.expectedUsers.getFirst());
  }

  @Test
  public void testGetAllUsers() {
    tester
        .document(
            """
                query GetAllUsers {
                      GetAllUsers {
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
                    }
                    """)
        .execute()
        .path("GetAllUsers")
        .entityList(UserRecordInfo.class)
        .containsExactly(this.expectedUsers.toArray(new UserRecordInfo[0]));
  }

  @Test
  public void testGetUserOfConversation() {
    tester
        .document(
            """
                query GetUserOfConversation($conversationId: ID!) {
                      GetUserOfConversation(conversationId: $conversationId) {
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
                    }
                    """)
        .variable("conversationId", this.testConversation.getId())
        .execute()
        .path("GetUserOfConversation")
        .entity(UserRecordInfo.class)
        .isEqualTo(this.expectedUsers.getFirst());
  }

  @Test
  public void testGetUser_UserNotFoundException() {
    tester
        .document(
            """
                query GetUser($id: ID!) {
                      GetUser(id: $id) {
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
                    }
                    """)
        .variable("id", "999")
        .execute()
        .errors().expect(error -> error.getMessage().contains("Cannot find user with id: 999"));
  }

  @Test
  public void testGetUser_UserConversationNotFoundException() {
    tester
        .document(
            """
                query GetUserOfConversation($conversationId: ID!) {
                      GetUserOfConversation(conversationId: $conversationId) {
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
                    }
                    """)
        .variable("conversationId", "999")
        .execute()
        .errors().expect(error -> error.getMessage().contains("Cannot find user conversation with id: 999"));
  }
}
