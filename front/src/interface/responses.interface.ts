import {
  ConversationAndMessages,
  ConversationInfo,
} from './conversation.interface';
import { UserInfo } from './user.interface';

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

export interface GetConversationAndMessages {
  GetConversationAndMessages: ConversationAndMessages;
}
