import { Component, Input } from '@angular/core';
import { MessageInfo } from '../../interface/message.interface';

@Component({
  selector: 'app-message',
  standalone: true,
  imports: [],
  templateUrl: './message.component.html',
  styleUrl: './message.component.sass',
})
export class MessageComponent {
  @Input() message!: MessageInfo;
}
