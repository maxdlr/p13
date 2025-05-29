import { RxStompConfig } from '@stomp/rx-stomp';
import SockJS from 'sockjs-client';
import { environment } from '../../environments/environments';
import { LoggerService } from '../logger.service';

export const rxStompConfig: RxStompConfig = {
  // Which server?
  // brokerURL: 'ws://localhost:8080/ws',

  webSocketFactory: () => {
    return new SockJS(environment.wsUrl);
  },

  // Headers
  // Typical keys: login, passcode, host
  // connectHeaders: {
  //   login: 'guest',
  //   passcode: 'guest',
  // },

  // How often to heartbeat?
  // Interval in milliseconds, set to 0 to disable
  heartbeatIncoming: 0, // Typical value 0 - disabled
  heartbeatOutgoing: 20000, // Typical value 20000 - every 20 seconds

  // Wait in milliseconds before attempting auto reconnect
  // Set to 0 to disable
  // Typical value 500 (500 milliseconds)
  reconnectDelay: 5000,

  // Will log diagnostics on console
  // It can be quite verbose, not recommended in production
  // Skip this key to stop logging to console
  debug: (msg: string): void => {
    let color: string = 'orange';

    const overrideMsgs: string[] = [
      '>>> SEND',
      '<<< CONNECTED',
      '>>> CONNECT',
      '>>> SUBSCRIBE',
      '<<< MESSAGE',
    ];

    overrideMsgs.forEach((oMsg) => {
      if (msg.includes(oMsg)) {
        msg = oMsg;
        if (oMsg.includes('<')) {
          color = '#20B2AA';
        } else {
          color = '#2F4F4F';
        }
      }
    });

    if (!overrideMsgs.includes(msg)) {
      color = '#808000';
    }

    // LoggerService.custom(msg, { bgColor: color });
  },
};
