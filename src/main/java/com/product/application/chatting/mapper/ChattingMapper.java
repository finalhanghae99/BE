package com.product.application.chatting.mapper;

import com.product.application.chatting.dto.*;
import com.product.application.chatting.entity.ChatMessage;
import com.product.application.chatting.entity.ChatRoom;
import com.product.application.reservation.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChattingMapper {

    public ChatMessage toRequestMessageDto(RequestMessageDto requestMessageDto, ChatRoom chatRoom, Reservation reservation, String roomId, String nickname) {
        return ChatMessage.builder()
                .sender(nickname)
                .receiver(reservation.getUsers().getNickname())
                .message(requestMessageDto.getMessage())
                .type(requestMessageDto.getType())
                .roomId(roomId)
                .chatRoom(chatRoom)
                .readMessage(requestMessageDto.isReadMessage())
                .reservation(reservation)
                .build();
    }
    public ResponseChatReservationDto toResponseChatMessageDto(ChatRoom chatRoom, Reservation reservation) {
        return ResponseChatReservationDto.builder()
                .campingName(reservation.getCamping().getCampingName())
                .imageUrl(reservation.getCamping().getImageUrl())
                .price(reservation.getPrice())
                .build();
    }
    public ResponseChatNicknameDto toresponseChatNicknameDto(ChatRoom chatRoom){
        return ResponseChatNicknameDto.builder()
                .seller(chatRoom.getSeller())
                .buyer(chatRoom.getBuyer())
                .build();
    }
    public ResponseChattingDto toResponseChattingDto(ChatMessage chatMessage){
        return ResponseChattingDto.builder()
                .lastChatMessage(chatMessage.getMessage())
                .lastSendDate(chatMessage.getSendDate())
                .buyerProfileImageUrl(chatMessage.getChatRoom().getBuyerProfileImageUrl())
                .sellerProfileImageUrl(chatMessage.getChatRoom().getSellerProfileImageUrl())
                .seller(chatMessage.getChatRoom().getSeller())
                .buyer(chatMessage.getChatRoom().getBuyer())
                .campingName(chatMessage.getReservation().getCamping().getCampingName())
                .build();
    }
    public ResponseChatMessageDto toresponseChatMessageDto(ChatMessage chatMessage){
        return ResponseChatMessageDto.builder()
                .sendDate(chatMessage.getSendDate())
                .sender(chatMessage.getSender())
                .type(chatMessage.getType())
                .roomId(chatMessage.getRoomId())
                .message(chatMessage.getMessage())
                .readMessage(chatMessage.isReadMessage())
                .build();
    }


}
