import { Component, Input, OnInit } from '@angular/core';
import { MessageInfo } from '../../interface/message.interface';
import { UtilService } from '../../service/util.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-message',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './message.component.html',
  styleUrl: './message.component.sass',
})
export class MessageComponent implements OnInit {
  @Input() message!: MessageInfo;
  public initials!: string;

  ngOnInit(): void {
    this.initials = UtilService.getInitials(this.message.user);
  }
}
