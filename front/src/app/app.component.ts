import { Component, inject, OnInit } from '@angular/core';
import { ConversationComponent } from './conversation/conversation.component';
import { SessionService } from '../service/session.service';

@Component({
  selector: 'app-root',
  imports: [ConversationComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.sass',
})
export class AppComponent implements OnInit {
  private sessionService: SessionService = inject(SessionService);
  ngOnInit(): void {}
}
