import { ConversationInfo } from './conversation.interface';
import { UserInfo } from './user.interface';

export interface MessageInfo {
  id: number;
  content: string;
  user: UserInfo;
  conversation: ConversationInfo;
  status: string;
}

export interface MessageInput {
  content: string;
  userId: number;
  conversationId: number;
}
