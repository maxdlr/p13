import { gql } from 'apollo-angular';

export const CREATE_CONVERSATION = gql`
  mutation CreateConversation($conversation: ConversationInput!) {
    CreateConversation(conversation: $conversation) {
      id
      wsTopic
      user {
        # id
        email
        firstname
        lastname
        # phoneNumber
        # isActive
        # role {
        #   id
        #   name
        # }
      }
      status
    }
  }
`;
