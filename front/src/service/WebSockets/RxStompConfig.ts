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

  heartbeatIncoming: 0,
  heartbeatOutgoing: 20000,
  reconnectDelay: 5000,
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

    LoggerService.custom(msg, { bgColor: color });
  },
};
