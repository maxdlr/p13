import { WsService } from './ws.service';
import { rxStompConfig } from './RxStompConfig';

export function rxStompServiceFactory() {
  const rxStomp = new WsService();
  rxStomp.configure(rxStompConfig);
  rxStomp.activate();
  return rxStomp;
}
