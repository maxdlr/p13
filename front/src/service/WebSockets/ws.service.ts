import { RxStomp } from '@stomp/rx-stomp';
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WsService extends RxStomp {
  public destroy$ = new Subject<void>();
  constructor() {
    super();
  }
}
