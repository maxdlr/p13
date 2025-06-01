import {
  Component,
  EventEmitter,
  inject,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import { ConversationInfo } from '../../interface/conversation.interface';
import { BubbleIconComponent } from '../bubble-icon/bubble-icon.component';
import { SpinnerIconComponent } from '../spinner-icon/spinner-icon.component';
import { UserInfo } from '../../interface/user.interface';
import { UtilService } from '../../service/util.service';
import { SessionService } from '../../service/session.service';

@Component({
  selector: 'app-sidebar',
  imports: [BubbleIconComponent, SpinnerIconComponent],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.sass',
})
export class SidebarComponent implements OnInit {
  @Input() conversations: ConversationInfo[] = [];
  @Output() selectedConversationId = new EventEmitter<number>();
  @Output() newConversation = new EventEmitter<void>();
  @Output() switchUserRole = new EventEmitter<void>();
  public welcomeMsg!: string;
  public sessionService: SessionService = inject(SessionService);

  ngOnInit(): void {
    this.sessionService.currentUser$.subscribe((user: UserInfo) => {
      this.welcomeMsg = 'Bonjour ' + UtilService.upperFirst(user.firstname);
    });
  }

  openConversation(id: number): void {
    this.selectedConversationId.emit(id);
  }

  createConversation() {
    this.newConversation.emit();
  }

  switch() {
    this.switchUserRole.emit();
  }

  get roleButton() {
    if (!this.sessionService.currentUserSubject.value) {
      return 'Loading...';
    }

    return this.sessionService.isAdmin ? 'As User' : 'As Admin';
  }
}
