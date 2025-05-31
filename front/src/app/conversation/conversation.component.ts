import { Component, inject, OnDestroy } from '@angular/core';
import { ConversationInfo } from '../../interface/conversation.interface';
import {
  CreateConversationResponse,
  GetAllConversationsOfUserResponse,
} from '../../interface/responses.interface';
import { MessageComponent } from '../message/message.component';
import { InputComponent } from '../input/input.component';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { MessageInfo } from '../../interface/message.interface';
import { WsService } from '../../service/WebSockets/ws.service';
import { IMessage } from '@stomp/stompjs';
import {
  BehaviorSubject,
  Observable,
  Subject,
  Subscription,
  takeUntil,
} from 'rxjs';
import { SessionService } from '../../service/session.service';
import { LoggerService } from '../../service/logger.service';
import { UserInfo } from '../../interface/user.interface';
import { HttpService } from '../../service/http.service';
import { MutationResult } from 'apollo-angular';

@Component({
  selector: 'app-conversation',
  imports: [MessageComponent, InputComponent, SidebarComponent],
  templateUrl: './conversation.component.html',
  styleUrl: './conversation.component.sass',
})
export class ConversationComponent implements OnDestroy {
  public currentConversation!: ConversationInfo;
  public currentMessages: MessageInfo[] = [];
  public conversations: ConversationInfo[] = [];
  public currentUser!: UserInfo;

  private chatTopic: BehaviorSubject<string> = new BehaviorSubject<string>('');
  private chatTopic$: Observable<string> = this.chatTopic.asObservable();

  private wsService: WsService = inject(WsService);
  private sessionService: SessionService = inject(SessionService);
  private http: HttpService = inject(HttpService);

  private componentDestroy$: Subject<void> = new Subject<void>();
  private chatTopicSubscription: Subscription | null = null;

  ngOnInit(): void {
    this.sessionService.loadFixtures(() => this.subscribeToSessionUser());
  }

  ngOnDestroy(): void {
    LoggerService.info(
      'ConversationComponent ngOnDestroy called. Emitting on componentDestroy$.',
    );
    this.componentDestroy$.next();
    this.componentDestroy$.complete();
  }

  private subscribeToSessionUser() {
    this.sessionService.currentUser$
      .pipe(takeUntil(this.componentDestroy$))
      .subscribe((user: UserInfo) => {
        this.currentUser = user;
        LoggerService.info('Sub session user, user is: ' + this.currentUser.id);
        this.getAllConversations();
        this.subscribeToChat();
      });
  }

  private getAllConversations() {
    this.http
      .getConversationsOfUser(this.currentUser.id)
      .pipe(takeUntil(this.componentDestroy$))
      .subscribe(
        (result: MutationResult<GetAllConversationsOfUserResponse>) => {
          const conversations: ConversationInfo[] | undefined =
            result.data?.GetAllConversationsOfUser;

          if (!conversations) {
            throw new Error('Cannot get All conversations');
          }

          this.conversations = conversations;

          LoggerService.info(
            'getting all convs for user: ' + this.currentUser.id,
          );

          this.subscribeToConversationList();
        },
      )
      .add(() => {
        if (this.conversations.length !== 0) {
          LoggerService.info(this.conversations[0]);
          this.openConversation(this.conversations[0].id);
        }
      });
  }

  public openConversation(id: number) {
    LoggerService.info('opening conv' + id);
    if (this.currentConversation && this.currentConversation.id !== id) {
      this.killChatTopicSubscription();
    }
    this.fetchMessagesOfConversation(id);
  }

  public openNewConversation() {
    LoggerService.info('creating conv');
    this.http
      .createConversation(this.currentUser.id)
      .pipe(takeUntil(this.componentDestroy$))
      .subscribe((result: MutationResult<CreateConversationResponse>) => {
        const conversation: ConversationInfo | undefined =
          result.data?.CreateConversation;

        if (!conversation) {
          throw new Error('Cannot create conversation');
        }

        this.currentConversation = conversation;
        this.openConversation(conversation.id);
      });
  }

  private subscribeToChat() {
    LoggerService.info('Sub current conv and its messages');
    this.killChatTopicSubscription();

    this.chatTopic$
      .pipe(takeUntil(this.componentDestroy$))
      .subscribe((topic: string) => {
        LoggerService.info(`Subscribing to chat topic: ${topic}`);
        this.chatTopicSubscription = this.wsService
          .watch(topic)
          .pipe(takeUntil(this.componentDestroy$))
          .subscribe(
            (response: IMessage) => {
              if (!response) {
                throw new Error('Cannot receive new messages');
              }

              const body = JSON.parse(response.body);
              const newMessageList = [...this.currentMessages, body.payload];
              this.currentMessages = newMessageList;
              LoggerService.info(
                'Received new message, currentMessages length: ' +
                  this.currentMessages.length,
              );
            },
            (error) => {
              LoggerService.error('Chat topic subscription error: ' + error);
            },
          );
      });
  }

  private subscribeToConversationList() {
    LoggerService.info('Sub conv list');
    this.wsService
      .watch('/topic/all-user-conversations')
      .pipe(takeUntil(this.componentDestroy$))
      .subscribe(
        (response: IMessage) => {
          if (!response) {
            throw new Error('Cannot receive conversation list');
          }
          const body = JSON.parse(response.body);
          this.conversations = [...this.conversations, body.payload];
          LoggerService.info(
            'Received new conversation, convs length: ' +
              this.conversations.length,
          );
        },
        (error) => {
          LoggerService.error('Conversation list subscription error: ' + error);
        },
      );
  }

  private killChatTopicSubscription(): void {
    if (this.chatTopicSubscription) {
      LoggerService.info('Unsubscribing from previous chat topic.');
      this.chatTopicSubscription.unsubscribe();
      this.chatTopicSubscription = null;
    }
  }

  private fetchMessagesOfConversation(id: number) {
    LoggerService.info('getting all message of conv');
    this.http
      .getConversationAndMessages(id)
      .pipe(takeUntil(this.componentDestroy$))
      .subscribe((result) => {
        const messages: MessageInfo[] | undefined =
          result.data?.GetAllMessagesOfConversation;
        const conversation: ConversationInfo | undefined =
          result.data?.GetConversation;

        if (!conversation) {
          throw new Error('Cannot get messages of conversation');
        }

        this.currentConversation = conversation;
        this.currentMessages = messages || [];
        this.chatTopic.next('/topic/' + this.currentConversation?.wsTopic);
      });
  }

  public switchUserRole() {
    LoggerService.info('Switching user role...');
    this.componentDestroy$.next();
    this.componentDestroy$ = new Subject<void>();

    this.sessionService.isAdmin
      ? this.sessionService.setCurrentUserAsUser()
      : this.sessionService.setCurrentUserAsAdmin();

    this.subscribeToSessionUser();
  }
}
