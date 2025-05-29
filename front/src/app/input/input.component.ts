import { Component, inject, Input } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Apollo } from 'apollo-angular';
import { CREATE_MESSAGE } from '../../gql-requests/CreateMessage';
import { MessageInput } from '../../interface/message.interface';
import { CreateMessageResponse } from '../../interface/responses.interface';
import { ConversationInfo } from '../../interface/conversation.interface';
import { LoggerService } from '../../service/logger.service';

@Component({
  selector: 'app-input',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './input.component.html',
  styleUrl: './input.component.sass',
})
export class InputComponent {
  public messageControl: FormControl = new FormControl('');
  @Input() conversation!: ConversationInfo;

  private apollo: Apollo = inject(Apollo);

  public send() {
    const message: MessageInput = {
      content: this.messageControl.value,
      userId: this.conversation.user.id,
      conversationId: this.conversation.id,
    };

    this.apollo
      .mutate<CreateMessageResponse>({
        mutation: CREATE_MESSAGE,
        variables: {
          message,
        },
      })
      .subscribe(() => {
        LoggerService.info('sending');
        this.messageControl.setValue('');
      });
  }
}
