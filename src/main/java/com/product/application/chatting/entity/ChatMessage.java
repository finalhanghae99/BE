package com.product.application.chatting.entity;

import com.product.application.reservation.entity.Reservation;
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
    @Column(length = 5000)
    private String message;
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
    public ChatMessage(MessageType type, String roomId, String sender, String receiver, String message,Reservation reservation, ChatRoom chatRoom, LocalDateTime sendDate){
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.reservation = reservation;
        this.chatRoom = chatRoom;
        this.sendDate = sendDate;
    }


}
