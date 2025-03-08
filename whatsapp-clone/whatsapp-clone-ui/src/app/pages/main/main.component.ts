import { MessageService } from './../../services/services/message.service';
import { ChatResponse } from './../../services/models/chat-response';
import { KeycloakService } from './../../utils/keycloak/keycloak.service';
import { Component, OnInit } from '@angular/core';
import { ChatListComponent } from '../../components/chat-list/chat-list.component';
import { ChatService } from '../../services/services/chat.service';
import { MessageResponse } from '../../services/models';
import { DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
// import { PickerComponent } from '@ctrl/ngx-emoji-mart';

@Component({
  selector: 'app-main',
  imports: [ChatListComponent, DatePipe, FormsModule],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss',
})
export class MainComponent implements OnInit {
  chats: Array<ChatResponse> = [];
  selectedChat: ChatResponse = {};
  chatMessages: MessageResponse[] = [];
  constructor(
    private chatService: ChatService,
    private keycloakService: KeycloakService,
    private messageService: MessageService
  ) {}
  ngOnInit(): void {
    this.getAllChats();
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
