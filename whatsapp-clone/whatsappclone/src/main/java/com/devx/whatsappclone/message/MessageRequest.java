package com.devx.whatsappclone.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class MessageRequest {

    private String Content;
    private String SenderId;
    private String RecipientId;
    private MessageType type;
    private String ChatId;
}
