import { Component, inject } from '@angular/core';
import {
  ConversationInfo,
  ConversationInput,
} from '../../interface/conversation.interface';
import { Apollo, gql } from 'apollo-angular';
import { CreateConversationResponse } from '../../interface/responses.interface';

@Component({
  selector: 'app-conversation',
  standalone: true,
  imports: [],
  templateUrl: './conversation.component.html',
  styleUrl: './conversation.component.sass',
})
export class ConversationComponent {
  loading = true;
  error: any;
  conversation: ConversationInfo | null = null;
  apollo: Apollo = inject(Apollo);

  CREATE_CONVERSATION_MUTATION = gql`
    mutation CreateConversation($conversation: ConversationInput!) {
      CreateConversation(conversation: $conversation) {
        id
        wsTopic
        user {
          id
          email
          firstname
          lastname
          phoneNumber
          isActive
          role {
            id
            name
          }
        }
        status
      }
    }
  `;

  ngOnInit(): void {
    this.apollo
      .mutate<CreateConversationResponse>({
        mutation: this.CREATE_CONVERSATION_MUTATION,
        variables: {
          conversation: {
            userId: 1,
          } as ConversationInput,
        },
      })
      .subscribe((result) => {
        this.conversation = result.data?.CreateConversation || null;
        // this.loading = result.loading;
        // this.error = result.error;
      });
  }
}
