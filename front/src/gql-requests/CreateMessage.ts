import { gql } from 'apollo-angular';

export const CREATE_MESSAGE = gql`
  mutation CreateMessage($message: MessageInput!) {
    CreateMessage(message: $message) {
      id
    }
  }
`;
