import { gql } from 'apollo-angular';

export const GET_ALL_CONVERSATIONS_OF_USER = gql`
  query GetAllConversationsOfUser($userId: ID!) {
    GetAllConversationsOfUser(userId: $userId) {
      id
      # wsTopic
      user {
        #   id
        #   email
        firstname
        lastname
        #   phoneNumber
        #   isActive
        #   role {
        #     id
        #     name
        #   }
      }
      # status
    }
  }
`;
