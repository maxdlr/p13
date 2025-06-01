import {
  Component,
  ElementRef,
  inject,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
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
  Subject,
  Subscription,
  takeUntil,
  filter,
  distinctUntilChanged,
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
export class ConversationComponent implements OnInit, OnDestroy {
  public currentConversation!: ConversationInfo;
  public currentMessages: MessageInfo[] = [];
  public conversations: ConversationInfo[] = [];
  public currentUser!: UserInfo;
  @ViewChild('scrollAnchor') private scrollAnchor!: ElementRef;

  private chatTopic: Subject<string> = new Subject<string>();
  private wsService: WsService = inject(WsService);
  private sessionService: SessionService = inject(SessionService);
  private http: HttpService = inject(HttpService);
  private componentDestroy$: Subject<void> = new Subject<void>();
  private chatTopicSubscription: Subscription | null = null;
  private conversationListSubscription: Subscription | null = null;

  ngOnInit(): void {
    this.sessionService.loadFixtures(() => this.subscribeToSessionUser());
  }

  ngOnDestroy(): void {
    this.cleanup();
  }

  private cleanup(): void {
    this.killChatTopicSubscription();
    this.killConversationListSubscription();
    this.componentDestroy$.next();
    this.componentDestroy$.complete();
  }

  private subscribeToSessionUser(): void {
    this.sessionService.currentUser$
      .pipe(
        filter((user) => !!user),
        takeUntil(this.componentDestroy$),
      )
      .subscribe((user: UserInfo) => {
        if (this.currentUser && this.currentUser.id === user.id) {
          return;
        }

        this.currentUser = user;
        this.getAllConversations();
        this.subscribeToChat();
      });
  }

  private getAllConversations(): void {
    this.http
      .getConversationsOfUser(this.currentUser.id)
      .pipe(takeUntil(this.componentDestroy$))
      .subscribe({
        next: (result: MutationResult<GetAllConversationsOfUserResponse>) => {
          const conversations: ConversationInfo[] | undefined =
            result.data?.GetAllConversationsOfUser;

          if (!conversations) {
            throw new Error('Cannot get All conversations');
          }

          this.conversations = conversations;
          this.subscribeToConversationList();
        },
        error: (error) => LoggerService.error(error.message),
      })
      .add(() => {
        if (this.conversations.length !== 0) {
          this.openConversation(this.conversations[0].id);
        }
      });
  }

  public openConversation(id: number): void {
    if (this.currentConversation && this.currentConversation.id !== id) {
      this.killChatTopicSubscription();
    }
    this.fetchMessagesOfConversation(id);
  }

  public openNewConversation(): void {
    this.http
      .createConversation(this.currentUser.id)
      .pipe(takeUntil(this.componentDestroy$))
      .subscribe({
        next: (result: MutationResult<CreateConversationResponse>) => {
          const conversation: ConversationInfo | undefined =
            result.data?.CreateConversation;

          if (!conversation) {
            throw new Error('Cannot create conversation');
          }

          this.currentConversation = conversation;
          this.openConversation(conversation.id);
        },
        error: (error) => LoggerService.error(error.message),
      });
  }

  private subscribeToChat(): void {
    this.chatTopic
      .pipe(
        filter((topic) => topic !== ''),
        distinctUntilChanged(),
        takeUntil(this.componentDestroy$),
      )
      .subscribe((topic: string) => {
        this.killChatTopicSubscription();

        this.chatTopicSubscription = this.wsService
          .watch(topic)
          .pipe(takeUntil(this.componentDestroy$))
          .subscribe({
            next: (response: IMessage) => {
              if (!response) {
                throw new Error('Cannot receive new messages');
              }

              const body = JSON.parse(response.body);
              this.currentMessages = [...this.currentMessages, body.payload];
              this.scrollToBottom();
            },
            error: (error) => {
              LoggerService.error('Chat topic subscription error: ' + error);
            },
          });
      });
  }

  private subscribeToConversationList(): void {
    this.killConversationListSubscription();

    this.conversationListSubscription = this.wsService
      .watch('/topic/all-user-conversations')
      .pipe(takeUntil(this.componentDestroy$))
      .subscribe({
        next: (response: IMessage) => {
          if (!response) {
            throw new Error('Cannot receive conversation list');
          }
          const body = JSON.parse(response.body);
          this.conversations = [...this.conversations, body.payload];
          this.scrollToBottom();
        },
        error: (error) => {
          LoggerService.error('Conversation list subscription error: ' + error);
        },
      });
  }

  private killChatTopicSubscription(): void {
    if (this.chatTopicSubscription) {
      this.chatTopicSubscription.unsubscribe();
      this.chatTopicSubscription = null;
    }
  }

  private killConversationListSubscription(): void {
    if (this.conversationListSubscription) {
      this.conversationListSubscription.unsubscribe();
      this.conversationListSubscription = null;
    }
  }

  private fetchMessagesOfConversation(id: number): void {
    this.http
      .getConversationAndMessages(id)
      .pipe(takeUntil(this.componentDestroy$))
      .subscribe({
        next: (result) => {
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
          this.scrollToBottom();
        },
        error: (error) => LoggerService.error(error.message),
      });
  }

  public switchUserRole(): void {
    this.cleanup();
    this.componentDestroy$ = new Subject<void>();

    this.conversations = [];
    this.currentMessages = [];
    this.currentConversation = undefined as any;

    this.sessionService.isAdmin
      ? this.sessionService.setCurrentUserAsUser()
      : this.sessionService.setCurrentUserAsAdmin();

    setTimeout(() => {
      this.subscribeToSessionUser();
    }, 100);
  }

  public scrollToBottom() {
    LoggerService.info('scolling to bottom');
    setTimeout(() => {
      this.scrollAnchor?.nativeElement?.scrollIntoView({ behavior: 'smooth' });
    }, 100);
  }
}
