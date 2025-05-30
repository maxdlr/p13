import { gql } from 'apollo-angular';

export const GET_CONVERSATION_AND_MESSAGES = gql`
  query GetConversationAndMessages($conversationId: ID!) {
    GetConversation(id: $conversationId) {
      id
      wsTopic
      user {
        id
      }
      status
    }
    GetAllMessagesOfConversation(conversationId: $conversationId) {
      id
      content
      user {
        firstname
        lastname
        role {
          name
        }
      }
      conversation {
        id
        wsTopic
      }
      status
    }
  }
`;
