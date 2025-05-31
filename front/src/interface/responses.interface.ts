import { ConversationInfo } from './conversation.interface';
import { UserInfo } from './user.interface';
import { MessageInfo } from './message.interface';

export interface GetUserResponse {
  GetUser: UserInfo;
}

export interface GetAllUsersResponse {
  GetAllUsers: UserInfo[];
}

export interface GetUserOfConversationResponse {
  GetUserOfConversation: UserInfo[];
}

export interface GetConversationResponse {
  GetConversation: ConversationInfo;
}

export interface CreateConversationResponse {
  CreateConversation: ConversationInfo;
}

export interface GetAllConversationsOfUserResponse {
  GetAllConversationsOfUser: ConversationInfo[];
}

export interface CreateMessageResponse {
  CreateMessage: MessageInfo;
}

export interface FixturesResponse {
  CreateUser: UserInfo;
  CreateAdmin: UserInfo;
  CreateConversation: ConversationInfo;
}

export interface ConversationAndMessagesResponse {
  GetConversation: ConversationInfo;
  GetAllMessagesOfConversation: MessageInfo[];
}
