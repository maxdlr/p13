import { Component, inject } from '@angular/core';
import {
  ConversationAndMessages,
  ConversationInfo,
  ConversationInput,
} from '../../interface/conversation.interface';
import { Apollo, MutationResult } from 'apollo-angular';
import {
  CreateConversationResponse,
  GetAllConversationsOfUserResponse,
} from '../../interface/responses.interface';
import { MessageComponent } from '../message/message.component';
import { InputComponent } from '../input/input.component';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { LoggerService } from '../../service/logger.service';
import { CREATE_CONVERSATION } from '../../gql-requests/CreateConversation';
import { GET_ALL_CONVERSATIONS_OF_USER } from '../../gql-requests/GetAllConversationsOfUser';
import { GET_CONVERSATION_AND_MESSAGES } from '../../gql-requests/GetConversationAndMessages';
import { MessageInfo } from '../../interface/message.interface';

@Component({
  selector: 'app-conversation',
  standalone: true,
  imports: [MessageComponent, InputComponent, SidebarComponent],
  templateUrl: './conversation.component.html',
  styleUrl: './conversation.component.sass',
})
export class ConversationComponent {
  loading = true;
  error: any;
  currentConversation: ConversationInfo | null = null;
  currentMessages: MessageInfo[] = [];
  conversations: ConversationInfo[] = [];
  apollo: Apollo = inject(Apollo);

  ngOnInit(): void {
    LoggerService.info('getting all conversations of user 1');
    this.apollo
      .query<GetAllConversationsOfUserResponse>({
        query: GET_ALL_CONVERSATIONS_OF_USER,
        variables: {
          userId: 1,
        },
      })
      .subscribe(
        (result: MutationResult<GetAllConversationsOfUserResponse>) => {
          this.conversations = result.data?.GetAllConversationsOfUser || [];
          // this.loading = result.loading;
          // this.error = result.error;
        },
      );
  }

  openConversation(id: number) {
    LoggerService.info('opening conversation ' + id + ' and its messages');
    this.apollo
      .query<ConversationAndMessages>({
        query: GET_CONVERSATION_AND_MESSAGES,
        variables: { conversationId: id },
      })
      .subscribe((result) => {
        this.currentConversation = result.data?.GetConversation || null;
        this.currentMessages = result.data?.GetAllMessagesOfConversation || [];
        // this.loading = result.loading;
        // this.error = result.error;
      });
  }

  openNewConversation() {
    LoggerService.info('Creating new conversation');
    this.apollo
      .mutate<CreateConversationResponse>({
        mutation: CREATE_CONVERSATION,
        variables: {
          conversation: {
            userId: 1,
          } as ConversationInput,
        },
      })
      .subscribe((result: MutationResult<CreateConversationResponse>) => {
        this.currentConversation = result.data?.CreateConversation || null;
        // this.loading = result.loading;
        // this.error = result.error;
      });
  }
}
