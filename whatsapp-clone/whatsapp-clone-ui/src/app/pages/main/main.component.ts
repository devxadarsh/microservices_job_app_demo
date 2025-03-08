import { MessageService } from './../../services/services/message.service';
import { ChatResponse } from './../../services/models/chat-response';
import { KeycloakService } from './../../utils/keycloak/keycloak.service';
import { Component, OnInit } from '@angular/core';
import { ChatListComponent } from '../../components/chat-list/chat-list.component';
import { StatusListComponent } from '../../components/status-list/status-list.component';
import { ChatService } from '../../services/services/chat.service';
import { MessageResponse, UserResponse } from '../../services/models';
import { DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
// import { PickerComponent } from '@ctrl/ngx-emoji-mart';

@Component({
  selector: 'app-main',
  imports: [ChatListComponent, StatusListComponent, DatePipe, FormsModule],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss',
})
export class MainComponent implements OnInit {
  chats: Array<ChatResponse> = [];
  selectedChat: ChatResponse = {};
  chatMessages: MessageResponse[] = [];
  activeTab: 'chat' | 'status' = 'chat';
  selectedStatus: UserResponse | null = null;
  constructor(
    private chatService: ChatService,
    private keycloakService: KeycloakService,
    private messageService: MessageService
  ) {}
  ngOnInit(): void {
    this.getAllChats();
  }

  setActiveTab(tab: 'chat' | 'status') {
    this.activeTab = tab;
    // Reset selected items when switching tabs
    if (tab === 'chat') {
      this.selectedStatus = null;
    } else {
      // this.selectedChat = {};
      // this.chatMessages = [];
    }
  }

  private getAllChats() {
    this.chatService.getChatsByReceiverId().subscribe({
      next: (response) => {
        this.chats = response;
      },
    });
  }

  userProfile() {
    this.keycloakService.accountManagement();
  }

  logout() {
    this.keycloakService.logOut();
  }

  chatSelected(chatResponse: ChatResponse) {
    this.selectedChat = chatResponse;
    this.getAllChatMessages(chatResponse.id as string);
    this.setMessagesToSeen();
    // this.selectedChat.unreadCount = 0;
  }

  statusSelected(userResponse: UserResponse) {
    this.selectedStatus = userResponse;
  }

  getAllChatMessages(chatId: string) {
    this.messageService.findMessagesByChatId({ chatId }).subscribe({
      next: (messages) => {
        this.chatMessages = messages;
      },
    });
  }

  setMessagesToSeen() {
    throw new Error('Method not implemented.');
  }

  isSelfMessage(message: MessageResponse) {
    return message.senderId === this.keycloakService.userId;
  }
}
