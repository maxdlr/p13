import { Component, OnInit, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Apollo, gql } from 'apollo-angular';
import { UserInfo } from '../interface/user.interface';
import { GetUserResponse } from '../interface/responses.interface';
import { ConversationComponent } from './conversation/conversation.component';
import { loadErrorMessages, loadDevMessages } from '@apollo/client/dev';
import { __DEV__ } from '@apollo/client/utilities/globals';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ConversationComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.sass',
})
export class AppComponent implements OnInit {
  loading = true;
  error: any;
  user: UserInfo | null = null;
  apollo: Apollo = inject(Apollo);

  GET_USER_QUERY = gql`
    query GetUser($id: ID!) {
      GetUser(id: $id) {
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
    }
  `;

  ngOnInit(): void {
    if (__DEV__) {
      // Adds messages only in a dev environment
      loadDevMessages();
      loadErrorMessages();
    }
    const userId = '1';

    this.apollo
      .watchQuery<GetUserResponse>({
        query: this.GET_USER_QUERY,
        variables: {
          id: userId,
        },
      })
      .valueChanges.subscribe((result) => {
        this.user = result.data?.GetUser || null;
        this.loading = result.loading;
        this.error = result.error;
      });
  }
}
