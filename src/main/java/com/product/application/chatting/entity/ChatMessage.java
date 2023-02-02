package com.product.application.chatting.entity;

import com.product.application.chatting.dto.ResponseChatMessageDto;
import com.product.application.common.TimeStampedChat;
import com.product.application.reservation.entity.Reservation;
import com.product.application.user.entity.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatId")
    private Long id;
    private MessageType type;
    private String roomId;
    private String sender;
    private String receiver;
    private String message;
    private Boolean readMessage;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime sendDate;

    @ManyToOne
    @JoinColumn(name="reservationId")
    private Reservation reservation;
    @ManyToOne
    @JoinColumn(name="chatRoomId")
    private ChatRoom chatRoom;

    @Builder
    public ChatMessage(MessageType type, String roomId, String sender, String receiver, String message, boolean readMessage, Reservation reservation, ChatRoom chatRoom, LocalDateTime sendDate){
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.readMessage = readMessage;
        this.reservation = reservation;
        this.chatRoom = chatRoom;
        this.sendDate = sendDate;
    }


    public void update(ResponseChatMessageDto responseChatMessageDto) {
        this.readMessage = responseChatMessageDto.isReadMessage();
    }
}
