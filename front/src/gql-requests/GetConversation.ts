import { gql } from 'apollo-angular';

export const GET_CONVERSATION = gql`
  query GetConversation($id: ID!) {
    GetConversation(id: $id) {
      # id
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
