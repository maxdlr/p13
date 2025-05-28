import { ConversationInfo } from './conversation.interface';
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

export interface CreateConversationResponse {
  CreateConversation: ConversationInfo;
}
