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
import { WsService } from '../../service/WebSockets/ws.service';
import { IMessage } from '@stomp/stompjs';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

@Component({
  selector: 'app-conversation',
  standalone: true,
  imports: [MessageComponent, InputComponent, SidebarComponent],
  templateUrl: './conversation.component.html',
  styleUrl: './conversation.component.sass',
})
export class ConversationComponent {
  public currentConversation: ConversationInfo | null = null;
  public currentMessages: MessageInfo[] = [];
  public conversations: ConversationInfo[] = [];
  public apollo: Apollo = inject(Apollo);

  private topic: BehaviorSubject<string> = new BehaviorSubject<string>('');
  private currentTopic$: Observable<string> = this.topic.asObservable();

  private wsService: WsService = inject(WsService);

  ngOnInit(): void {
    this.getAllConversations();
    this.listen();
  }

  private getAllConversations() {
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
        this.startConversationSession(
          this.currentConversation?.wsTopic as string,
        );
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
      });
  }

  private listen() {
    this.currentTopic$.subscribe((topic: string) => {
      LoggerService.info('listening to ' + topic);
      this.wsService.watch(topic).subscribe((response: IMessage) => {
        LoggerService.info('subscribed');
      });
    });
  }

  private startConversationSession(topic: string) {
    this.topic.next('/topic/' + topic);
  }
}
