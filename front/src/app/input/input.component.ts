import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MessageInput } from '../../interface/message.interface';
import { ConversationInfo } from '../../interface/conversation.interface';
import { HttpService } from '../../service/http.service';
import { SessionService } from '../../service/session.service';

@Component({
  selector: 'app-input',
  imports: [ReactiveFormsModule],
  templateUrl: './input.component.html',
  styleUrl: './input.component.sass',
})
export class InputComponent {
  public messageControl: FormControl = new FormControl('');
  @Input() conversation!: ConversationInfo;
  @Output() messageSent: EventEmitter<void> = new EventEmitter();

  private http: HttpService = inject(HttpService);
  private sessionService: SessionService = inject(SessionService);

  public send() {
    const message: MessageInput = {
      content: this.messageControl.value,
      userId: this.sessionService.currentUserSubject.value.id,
      conversationId: this.conversation.id,
    };

    this.http.createMessage(message).subscribe(() => {
      this.messageControl.setValue('');
    });
    this.messageSent.emit();
  }
}
