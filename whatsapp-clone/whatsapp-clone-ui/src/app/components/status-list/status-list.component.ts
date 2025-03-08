import { Component, OnInit, input, InputSignal, output } from '@angular/core';
import { DatePipe } from '@angular/common';
import { UserResponse } from '../../services/models';
import { UserService } from '../../services/services/user.service';

@Component({
  selector: 'app-status-list',
  // imports: [DatePipe],
  templateUrl: './status-list.component.html',
  styleUrl: './status-list.component.scss',
})
export class StatusListComponent implements OnInit {
  contacts: Array<UserResponse> = [];
  statusSelected = output<UserResponse>();

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadContacts();
  }

  loadContacts() {
    this.userService.getAllUsersExceptSelf().subscribe({
      next: (users) => {
        // In a real app, we would filter users who have status updates
        // For now, we'll just show all users as if they have status
        this.contacts = users;
      },
    });
  }

  statusClicked(contact: UserResponse) {
    this.statusSelected.emit(contact);
  }

  getStatusTimeText(lastSeen: string | undefined): string {
    if (!lastSeen) return 'Today';

    // In a real app, we would calculate the actual status time
    // For now, we'll just use the lastSeen time
    const lastSeenDate = new Date(lastSeen);
    const now = new Date();

    if (lastSeenDate.toDateString() === now.toDateString()) {
      return 'Today';
    } else {
      const yesterday = new Date();
      yesterday.setDate(yesterday.getDate() - 1);
      if (lastSeenDate.toDateString() === yesterday.toDateString()) {
        return 'Yesterday';
      } else {
        return lastSeenDate.toLocaleDateString();
      }
    }
  }
}
