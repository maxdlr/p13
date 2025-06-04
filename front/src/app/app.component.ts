import { Component } from '@angular/core';
import { ConversationComponent } from './conversation/conversation.component';

@Component({
  selector: 'app-root',
  imports: [ConversationComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.sass',
})
export class AppComponent {}
