import { gql } from 'apollo-angular';

export const FIXTURES = gql`
  mutation CreateUser($user: UserInfo!, $admin: UserInfo!, $userId: ID!) {
    CreateUser(user: $user) {
      id
      firstname
      lastname
      role {
        name
      }
    }
    CreateAdmin(user: $admin) {
      id
      firstname
      lastname
      role {
        name
      }
    }
  }
`;
