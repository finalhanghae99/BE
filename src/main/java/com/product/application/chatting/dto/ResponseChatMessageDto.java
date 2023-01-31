package com.product.application.chatting.dto;

import com.product.application.chatting.entity.ChatMessage;
import com.product.application.chatting.entity.MessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ResponseChatMessageDto {

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
    private LocalDateTime sendDate;
    private Long reservationId;
    private boolean readMessage;

    @Builder
    public ResponseChatMessageDto(MessageType type, String roomId, String sender, String message, LocalDateTime sendDate, Long reservationId, boolean readMessage) {
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
        this.sendDate = sendDate;
        this.reservationId = reservationId;
        this.readMessage = readMessage;
    }

    public void update(ChatMessage chatMessage) {
        this.readMessage = true;
    }
}
