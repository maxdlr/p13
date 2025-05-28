import { RxStomp } from '@stomp/rx-stomp';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IMessage } from '@stomp/stompjs';

@Injectable({
  providedIn: 'root',
})
export class WsService extends RxStomp {
  constructor() {
    super();
  }

  // public to(topic: string) {
  //   this.buildEndpoints(topic);
  //   return {
  //     save: this.save,
  //     delete: this.delete,
  //     add: this.add,
  //     watch$: this.watch$(),
  //   };
  // }
  //
  // public watch$(topic: string): Observable<IMessage> {
  //   return this.watch(topic);
  // }
  //
  // private add<P extends ModelData>(payload: P): void {
  //   this.publish({
  //     destination: this.endPoints.add,
  //     body: JSON.stringify(payload),
  //   });
  // }
  //
  // private delete(id: number): void {
  //   if (!id) return;
  //   this.publish({
  //     destination: this.endPoints.delete,
  //     body: id.toString(),
  //   });
  // }
  //
  // private save<P extends ModelData>(payload: P): void {
  //   console.log('saving');
  //   this.publish({
  //     destination: this.endPoints.save,
  //     body: JSON.stringify(payload),
  //   });
  // }
  //
  // private buildEndpoints(topicName: WsTopicNameEnum) {
  //   this.endPoints = {
  //     watch: `/topic/${topicName}`,
  //     add: `/app/${topicName}.add`,
  //     delete: `/app/${topicName}.delete`,
  //     save: `/app/${topicName}.save`,
  //   };
  // }
}
