package com.product.application.chatting.entity;

import com.product.application.chatting.dto.ResponseChatMessageDto;
import com.product.application.common.TimeStampedChat;
import com.product.application.reservation.entity.Reservation;
import com.product.application.user.entity.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ChatMessage extends TimeStampedChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatId")
    private Long id;
    private MessageType type;
    private String roomId;
    private String sender;
    private String receiver;
    private String message;
    private boolean readMessage;

    @ManyToOne
    @JoinColumn(name="reservationId")
    private Reservation reservation;
    @ManyToOne
    @JoinColumn(name="chatRoomId")
    private ChatRoom chatRoom;

    @Builder
    public ChatMessage(MessageType type, String roomId, String sender, String receiver, String message, boolean readMessage, Reservation reservation, ChatRoom chatRoom){
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.readMessage = readMessage;
        this.reservation = reservation;
        this.chatRoom = chatRoom;
    }


    public void update(ResponseChatMessageDto responseChatMessageDto) {
        this.readMessage = responseChatMessageDto.isReadMessage();
    }
}
