package com.devx.whatsappclone.message;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {

    private Long id;
    private String content;
    private MessageState state;
    private MessageType type;
    private LocalDateTime createdAt;
    private String senderId;
    private String recipientId;
    private byte[] media;
}
