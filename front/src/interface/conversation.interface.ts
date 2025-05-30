import { MessageInfo } from './message.interface';
import { UserInfo } from './user.interface';

export enum ConversationStatusEnum {
  ADMIN_ACTIVE = 'ADMIN_ACTIVE',
  ADMIN_INACTIVE = 'ADMIN_INACTIVE',
  USER_ACTIVE = 'USER_ACTIVE',
  USER_INACTIVE = 'USER_INACTIVE',
  OPEN = 'OPEN',
  CLOSED = 'CLOSED',
}

export interface ConversationInfo {
  id: number;
  wsTopic: string;
  user: UserInfo;
  status: ConversationStatusEnum;
}

export interface ConversationInput {
  userId: number;
}

export interface ConversationAndMessages {
  GetConversation: ConversationInfo;
  GetAllMessagesOfConversation: MessageInfo[];
}
