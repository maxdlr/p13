package com.maxdlr.p13.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.maxdlr.p13.dto.ConversationRecordInfo;
import com.maxdlr.p13.dto.ConversationRecordInput;
import com.maxdlr.p13.entity.ConversationEntity;
import com.maxdlr.p13.entity.RoleEntity;
import com.maxdlr.p13.entity.UserEntity;
import com.maxdlr.p13.enums.ConversationStatusEnum;
import com.maxdlr.p13.mapper.ConversationMapper;
import com.maxdlr.p13.mapper.MessageMapper;
import com.maxdlr.p13.mapper.UserMapper;
import com.maxdlr.p13.repository.ConversationRepository;
import com.maxdlr.p13.repository.RoleRepository;
import com.maxdlr.p13.repository.UserRepository;
import com.maxdlr.p13.service.ConversationService;

@SpringBootTest
@AutoConfigureGraphQlTester
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ConversationControllerIntegrationTests {

  @Autowired
  GraphQlTester tester;

  @Autowired
  ConversationService conversationService;

  @Autowired
  ConversationMapper conversationMapper;

  // @Autowired
  // MessageMapper messageMapper;

  @Autowired
  UserMapper userMapper;

  @Autowired
  UserRepository userRepository;

  @Autowired
  ConversationRepository conversationRepository;

  @Autowired
  RoleRepository roleRepository;

  List<ConversationEntity> savedConversations;
  List<ConversationRecordInfo> expectedConversations;

  UserEntity testUser;

  @BeforeEach
  public void setUp() {
    this.conversationRepository.deleteAll();
    this.userRepository.deleteAll();
    this.roleRepository.deleteAll();

    RoleEntity role = TestUtils.makeRoleEntityAsUser();

    this.testUser = TestUtils.makeUserEntity(role);
    this.userRepository.save(this.testUser);

    ConversationEntity conv1 = TestUtils.makeConversationEntity(this.testUser, ConversationStatusEnum.OPEN);
    ConversationEntity conv2 = TestUtils.makeConversationEntity(this.testUser, ConversationStatusEnum.OPEN);

    this.savedConversations = this.conversationRepository.saveAll(Arrays.asList(conv1, conv2));

    this.expectedConversations = this.conversationMapper.toRecordInfo(this.savedConversations);
  }

  @Test
  public void testGetConversation() {
    tester.document("""
        query GetConversation($id: ID!) {
          GetConversation(id: $id) {
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
        }
        """)
        .variable("id", this.savedConversations.getFirst().getId())
        .execute()
        .path("GetConversation")
        .entity(ConversationRecordInfo.class)
        .isEqualTo(this.expectedConversations.getFirst());
  }

  @Test
  public void testGetAllConversations() {
    tester.document("""
        query GetAllConversations {
          GetAllConversations {
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
        }
        """)
        .execute()
        .path("GetAllConversations")
        .entityList(ConversationRecordInfo.class)
        .containsExactly(this.expectedConversations.toArray(new ConversationRecordInfo[0]));
  }

  @Test
  public void testGetAllConversationsOfUser() {
    tester.document("""
        query GetAllConversationsOfUser($userId: ID!) {
          GetAllConversationsOfUser(userId: $userId) {
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
        }
        """)
        .variable("userId", this.testUser.getId())
        .execute()
        .path("GetAllConversationsOfUser")
        .entityList(ConversationRecordInfo.class)
        .containsExactly(this.expectedConversations.toArray(new ConversationRecordInfo[0]));
  }

  // @Test
  // public void testCreateConversation() {
  // Map<String, String> testConversation = new HashMap<>();
  // testConversation.put("userId", this.testUser.getId().toString());
  //
  // System.out.println(testConversation + "monmarkercaca");
  //
  // tester.document("""
  // mutation CreateConversation($conversation: ConversationInput!) {
  // CreateConversation(conversation: $conversation) {
  // id
  // wsTopic
  // user {
  // id
  // email
  // firstname
  // lastname
  // phoneNumber
  // isActive
  // role {
  // id
  // name
  // }
  // }
  // status
  // }
  // }
  // """)
  // .variable("conversation", testConversation)
  // .execute()
  // .path("CreateConversation")
  // .entity(ConversationRecordInfo.class);
  // // .satisfies(createdConv -> {
  // // assertNotNull(createdConv);
  // // assertEquals(createdConv.getUser().getId(), this.testUser.getId());
  // // assertEquals(createdConv.getStatus(),
  // // ConversationStatusEnum.OPEN.toString());
  // // });
  // }
}
