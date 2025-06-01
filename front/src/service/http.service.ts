import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Apollo } from 'apollo-angular';
import {
  CreateConversationResponse,
  CreateMessageResponse,
  GetAllConversationsOfUserResponse,
  ConversationAndMessagesResponse,
} from '../interface/responses.interface';
import { GET_ALL_CONVERSATIONS_OF_USER } from '../gql-requests/GetAllConversationsOfUser';
import { CREATE_CONVERSATION } from '../gql-requests/CreateConversation';
import { GET_CONVERSATION_AND_MESSAGES } from '../gql-requests/GetConversationAndMessages';
import { MessageInput } from '../interface/message.interface';
import { CREATE_MESSAGE } from '../gql-requests/CreateMessage';
import { environment } from '../environments/environments';
import { UserInfo } from '../interface/user.interface';

@Injectable({
  providedIn: 'root',
})
export class HttpService {
  private httpClient: HttpClient = inject(HttpClient);
  private apollo: Apollo = inject(Apollo);

  public getConversationsOfUser(userId: number) {
    return this.apollo.query<GetAllConversationsOfUserResponse>({
      query: GET_ALL_CONVERSATIONS_OF_USER,
      variables: {
        userId,
      },
      fetchPolicy: 'no-cache',
    });
  }

  public createConversation(userId: number) {
    return this.apollo.mutate<CreateConversationResponse>({
      mutation: CREATE_CONVERSATION,
      variables: {
        conversation: {
          userId,
        },
      },
      fetchPolicy: 'no-cache',
    });
  }

  public getConversationAndMessages(conversationId: number) {
    const a = this.apollo.query<ConversationAndMessagesResponse>({
      query: GET_CONVERSATION_AND_MESSAGES,
      variables: {
        conversationId,
      },
      fetchPolicy: 'no-cache',
    });

    return a;
  }

  public createMessage(message: MessageInput) {
    return this.apollo.mutate<CreateMessageResponse>({
      mutation: CREATE_MESSAGE,
      variables: {
        message,
      },
      fetchPolicy: 'no-cache',
    });
  }

  public loadFixtures() {
    return this.httpClient.get<UserInfo[]>(
      environment.apiUrl + 'fixtures/load',
      {
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
      },
    );
  }
}
