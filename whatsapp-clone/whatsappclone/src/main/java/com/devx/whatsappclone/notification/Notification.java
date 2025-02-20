package com.devx.whatsappclone.notification;

import com.devx.whatsappclone.message.MessageType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor@NoArgsConstructor
@Builder
public class Notification {

    private String chatId;
    private String content;
    private String senderId;
    private String recipientId;
    private MessageType messageType;
    private String chatName;
    private NotificationType type;
    private byte[] media;
}
