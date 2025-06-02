const serverAddress = window.location.hostname;
const serverPort = window.location.port ? `:${window.location.port}` : ''; // Get the port if it exists

export const environment = {
  apiUrl: `${window.location.protocol}//${serverAddress}${serverPort}/api/`,
  wsUrl: `${window.location.protocol}//${serverAddress}${serverPort}/ws/`,
  graphqlUrl: `${window.location.protocol}//${serverAddress}${serverPort}/graphql`,
  production: true,
};
