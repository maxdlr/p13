import { Injectable } from '@angular/core';
import { MessageInfo } from '../interface/message.interface';
import { LoggerService } from './logger.service';

export type ConversationPool = Map<number, MessageInfo[]>;

@Injectable({
  providedIn: 'root',
})
export class ConversationPoolService {
  private activeConversationsSubject: ConversationPool = new Map<
    number,
    MessageInfo[]
  >();

  public register(id: number | undefined, messages: MessageInfo[]) {
    if (!id) {
      throw new Error(
        'Cannot register messages to conversation, no conversation id found',
      );
    }

    this.activeConversationsSubject.set(id, messages);

    LoggerService.info(this.activeConversationsSubject);
  }

  public retrieve(id: number): MessageInfo[] | undefined {
    return this.activeConversationsSubject.get(id);
  }

  public searchAndfind(id: number):
    | {
        id: number;
        messages: MessageInfo[];
      }
    | undefined {
    let searchResult: { id: number; messages: MessageInfo[] } | undefined =
      undefined;
    this.activeConversationsSubject.forEach(
      (value: MessageInfo[], key: number) => {
        if (key === id) {
          searchResult = {
            id: key,
            messages: value,
          };
        }
      },
    );
    return searchResult;
  }
}
