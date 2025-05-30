import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ConversationInfo } from '../../interface/conversation.interface';
import { BubbleIconComponent } from '../bubble-icon/bubble-icon.component';
import { SpinnerIconComponent } from '../spinner-icon/spinner-icon.component';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [BubbleIconComponent, SpinnerIconComponent],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.sass',
})
export class SidebarComponent {
  @Input() conversations: ConversationInfo[] = [];
  @Output() selectedConversationId = new EventEmitter<number>();
  @Output() newConversation = new EventEmitter<void>();

  openConversation(id: number): void {
    this.selectedConversationId.emit(id);
  }

  createConversation() {
    this.newConversation.emit();
  }
}
