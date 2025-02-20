package com.devx.whatsappclone.message;

import com.devx.whatsappclone.chat.Chat;
import com.devx.whatsappclone.chat.ChatRepository;
import com.devx.whatsappclone.file.FileService;
import com.devx.whatsappclone.file.FileUtils;
import com.devx.whatsappclone.notification.Notification;
import com.devx.whatsappclone.notification.NotificationService;
import com.devx.whatsappclone.notification.NotificationType;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper mapper;
    private final FileService fileService;
    private final NotificationService notificationService;

    public void saveMessage(MessageRequest messageRequest) {
        Chat chat = chatRepository.findById(messageRequest.getChatId()).orElseThrow(() -> new EntityNotFoundException("Chat with id " + messageRequest.getChatId() + " not found"));

        Message message = new Message();
        message.setContent(messageRequest.getContent());
        message.setChat(chat);
        message.setSenderId(messageRequest.getSenderId());
        message.setReceiverId(messageRequest.getRecipientId());
        message.setState(MessageState.SENT);
        message.setType(messageRequest.getType());

        messageRepository.save(message);

        Notification notification = Notification.builder()
                .chatId(chat.getId())
                .messageType(messageRequest.getType())
                .content(messageRequest.getContent())
                .senderId(messageRequest.getSenderId())
                .recipientId(messageRequest.getRecipientId())
                .type(NotificationType.MESSAGE)
                .chatName(chat.getChatName(message.getSenderId()))
                .build();

        notificationService.sendNotification(message.getReceiverId(), notification);

    }


    public List<MessageResponse> findMessagesByChatId(String chatId) {
        return messageRepository.findMessagesByChatId(chatId)
                .stream()
                .map(mapper::toMessageResponse)
                .toList();
    } //todo>


    @Transactional
    public void setMessagesToSeen(String chatId, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new EntityNotFoundException("Chat with id " + chatId + " not found"));
        final String recipientId = getRecipientId(chat, authentication);
        messageRepository.setMessagesToSeenByChatId(chatId, MessageState.SEEN);

        Notification notification = Notification.builder()
                .chatId(chat.getId())
                .senderId(getSenderId(chat, authentication))
                .recipientId(recipientId)
                .type(NotificationType.SEEN)
                .build();

        notificationService.sendNotification(recipientId, notification);
    }

    public void uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new EntityNotFoundException("Chat with id " + chatId + " not found"));

        final String recipientId = getRecipientId(chat, authentication);
        final String senderId = getSenderId(chat, authentication);

        final String filePath = fileService.saveFile(file, senderId);

        Message message = new Message();
        message.setChat(chat);
        message.setSenderId(senderId);
        message.setReceiverId(recipientId);
        message.setState(MessageState.SENT);
        message.setType(MessageType.IMAGE);
        message.setMediaFilePath(filePath);

        messageRepository.save(message);

        Notification notification = Notification.builder()
                .chatId(chat.getId())
                .type(NotificationType.IMAGE)
                .messageType(MessageType.IMAGE)
                .senderId(senderId)
                .recipientId(recipientId)
                .media(FileUtils.readFileFromLocation(filePath))
                .build();

        notificationService.sendNotification(recipientId, notification);

    }

    private String getSenderId(Chat chat, Authentication authentication) {
        return chat.getSender().getId().equals(authentication.getName()) ? chat.getSender().getId() : chat.getRecipient().getId();
    }

    private String getRecipientId(Chat chat, Authentication authentication) {
        return chat.getSender().getId().equals(authentication.getName()) ? chat.getRecipient().getId() : chat.getSender().getId();
    }


}
