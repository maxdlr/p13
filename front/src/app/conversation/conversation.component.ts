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
import { CREATE_CONVERSATION } from '../../gql-requests/CreateConversation';
import { GET_ALL_CONVERSATIONS_OF_USER } from '../../gql-requests/GetAllConversationsOfUser';
import { GET_CONVERSATION_AND_MESSAGES } from '../../gql-requests/GetConversationAndMessages';
import { MessageInfo } from '../../interface/message.interface';
import { WsService } from '../../service/WebSockets/ws.service';
import { IMessage } from '@stomp/stompjs';
import { BehaviorSubject, Observable, Subscription, takeUntil } from 'rxjs';
import { ConversationPoolService } from '../../service/conversationPool.service';
import { SessionService } from '../../service/session.service';
import { LoggerService } from '../../service/logger.service';
import { UserInfo } from '../../interface/user.interface';

@Component({
  selector: 'app-conversation',
  imports: [MessageComponent, InputComponent, SidebarComponent],
  templateUrl: './conversation.component.html',
  styleUrl: './conversation.component.sass',
})
export class ConversationComponent {
  public currentConversation!: ConversationInfo;
  public currentMessages: MessageInfo[] = [];
  public conversations: ConversationInfo[] = [];
  public currentUser!: UserInfo;

  private currentConversationSubscription!: Subscription;

  private messageListTopic: BehaviorSubject<string> =
    new BehaviorSubject<string>('');
  private messageListTopic$: Observable<string> =
    this.messageListTopic.asObservable();

  private currentConversationMessagesSubject: BehaviorSubject<MessageInfo[]> =
    new BehaviorSubject<MessageInfo[]>([]);

  private wsService: WsService = inject(WsService);
  private conversationPoolService = inject(ConversationPoolService);
  private apollo: Apollo = inject(Apollo);
  private sessionService: SessionService = inject(SessionService);

  ngOnInit(): void {
    this.sessionService.loadUserFixtures(() => this.subscribeToSessionUser());
  }

  private subscribeToSessionUser() {
    LoggerService.info('Sub session user');
    this.sessionService.currentUser$.subscribe((user: UserInfo) => {
      this.currentUser = user;
      this.getAllConversations();
    });
  }

  private subscribeCurrentConversationToPoolState() {
    LoggerService.info('Sub to pool state');
    this.currentConversationMessagesSubject
      .asObservable()
      .subscribe((messages: MessageInfo[]) => {
        this.conversationPoolService.register(
          this.currentConversation?.id,
          messages,
        );
      });
  }

  private getAllConversations() {
    LoggerService.info('getting all convs');
    this.apollo
      .query<GetAllConversationsOfUserResponse>({
        query: GET_ALL_CONVERSATIONS_OF_USER,
        variables: {
          userId: this.currentUser.id,
        },
      })
      .subscribe(
        (result: MutationResult<GetAllConversationsOfUserResponse>) => {
          const conversations: ConversationInfo[] | undefined =
            result.data?.GetAllConversationsOfUser;

          if (!conversations) {
            throw new Error('Cannot get All conversations');
          }

          this.conversations = conversations;

          if (this.conversations.length !== 0) {
            this.currentConversation = this.conversations[0];
          }
          this.subscribeToConversationList();
        },
      );
  }

  openConversation(id: number) {
    LoggerService.info('opening conv' + id);
    this.unsubscribeFromCurrentConversation();

    const storedMessages: MessageInfo[] | undefined =
      this.conversationPoolService.retrieve(id);

    if (!storedMessages) {
      this.fetchMessagesOfConversation(id);
    } else {
      this.currentMessages = storedMessages;
    }
  }

  openNewConversation() {
    LoggerService.info('creating conv');
    this.apollo
      .mutate<CreateConversationResponse>({
        mutation: CREATE_CONVERSATION,
        variables: {
          conversation: {
            userId: this.currentUser.id,
          } as ConversationInput,
        },
      })
      .subscribe((result: MutationResult<CreateConversationResponse>) => {
        const conversation: ConversationInfo | undefined =
          result.data?.CreateConversation;

        if (!conversation) {
          throw new Error('Cannot create conversation');
        }

        this.currentConversation = conversation;
      });
  }

  private subscribeToCurrentConversationMessages() {
    LoggerService.info('Sub current conv and its messages');
    this.messageListTopic$.subscribe((topic: string) => {
      this.wsService
        .watch(topic)
        .pipe(takeUntil(this.wsService.destroy$))
        .subscribe((response: IMessage) => {
          if (!response) {
            throw new Error('Cannot receive new messages');
          }

          const body = JSON.parse(response.body);
          const newMessageList = [...this.currentMessages, body.payload];
          this.currentMessages = newMessageList;
          this.currentConversationMessagesSubject.next(newMessageList);
          this.subscribeCurrentConversationToPoolState();
        });
    });
  }

  private subscribeToConversationList() {
    LoggerService.info('Sub conv list');
    this.wsService
      .watch('/topic/all-user-conversations')
      .subscribe((response: IMessage) => {
        if (!response) {
          throw new Error('Cannot receive conversation list');
        }
        const body = JSON.parse(response.body);
        this.conversations = [...this.conversations, body.payload];
        this.subscribeToCurrentConversationMessages();
      });
  }

  private unsubscribeFromCurrentConversation() {
    LoggerService.info('unsub from conv ' + this.currentConversation.id);

    if (this.currentConversation) {
      this.wsService.destroy$.next();
      this.currentConversationSubscription.unsubscribe();
    }
  }

  private fetchMessagesOfConversation(id: number) {
    LoggerService.info('getting all message of conv');
    this.currentConversationSubscription = this.apollo
      .query<ConversationAndMessages>({
        query: GET_CONVERSATION_AND_MESSAGES,
        variables: { conversationId: id },
        fetchPolicy: 'no-cache',
      })
      .subscribe((result) => {
        const messages: MessageInfo[] | undefined =
          result.data?.GetAllMessagesOfConversation;
        const conversation: ConversationInfo | undefined =
          result.data?.GetConversation;

        if (!conversation) {
          throw new Error('Cannot get messages of conversation');
        }

        this.currentConversation = conversation;
        this.currentMessages = messages;
        this.currentConversationMessagesSubject.next(messages);
        this.messageListTopic.next(
          '/topic/' + this.currentConversation?.wsTopic,
        );
      });
  }
}
