package com.product.application.chatting.dto;

import com.product.application.chatting.entity.MessageType;
import com.product.application.reservation.entity.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class RequestMessageDto {
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
    private LocalDateTime sendDate;
    private Long reservationId;
    private boolean readMessage;




}
