const serverAddress = window.location.hostname;
export const environment = {
  apiUrl: `http://${serverAddress}:8080/api/`,
  wsUrl: `http://${serverAddress}:8080/ws/`,
  graphqlUrl: `http://${serverAddress}:8080/graphql`,
  production: false,
};
