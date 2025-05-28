import { gql } from 'apollo-angular';

export const GET_CONVERSATION_AND_MESSAGES = gql`
  query GetConversationAndMessages($conversationId: ID!) {
    GetConversation(id: $conversationId) {
      wsTopic
      user {
        email
        firstname
        lastname
      }
      status
    }
    GetAllMessagesOfConversation(conversationId: $conversationId) {
      id
      content
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
      conversation {
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
      status
    }
  }
`;
