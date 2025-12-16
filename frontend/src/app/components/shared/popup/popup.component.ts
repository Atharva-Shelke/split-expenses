import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-popup',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './popup.component.html',
  styleUrls: ['./popup.component.css']
})
export class PopupComponent {
  @Input() show: boolean = false;
  @Input() title: string = 'Message';
  @Input() message: string = '';

  @Input() samePage: boolean = true;

  @Output() closed = new EventEmitter<void>();

  close() {
    this.show = false;
    this.closed.emit();
  }

  closeAndBack() {
    this.show = false;
    this.closed.emit();
    history.back();
  }
}
