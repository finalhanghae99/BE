package com.product.application.chatting.mapper;

import com.product.application.chatting.dto.*;
import com.product.application.chatting.entity.ChatMessage;
import com.product.application.chatting.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ChattingMapper {

    public ChatMessage toRequestMessageDto(RequestMessageDto requestMessageDto, ChatRoom chatRoom, String roomId, String nickname) {
        LocalDateTime now = LocalDateTime.parse(requestMessageDto.getSendDate());
        System.out.println("now = " + now);
        return ChatMessage.builder()
                .sender(nickname)
                .receiver(chatRoom.getReservation().getUsers().getNickname())
                .message(requestMessageDto.getMessage())
                .type(requestMessageDto.getType())
                .roomId(roomId)
                .chatRoom(chatRoom)
                .reservation(chatRoom.getReservation())
                .sendDate(now)
                .build();
    }
    public ResponseChatReservationDto toResponseChatMessageDto(ChatRoom chatRoom) {
        return ResponseChatReservationDto.builder()
                .campingName(chatRoom.getReservation().getCamping().getCampingName())
                .imageUrl(chatRoom.getReservation().getCamping().getImageUrl())
                .price(chatRoom.getReservation().getPrice())
                .reservationId(chatRoom.getReservation().getId())
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
                .roomId(chatMessage.getRoomId())
                .build();
    }
    public ResponseChatMessageDto toresponseChatMessageDto(ChatMessage chatMessage){
        return ResponseChatMessageDto.builder()
                .sendDate(chatMessage.getSendDate())
                .sender(chatMessage.getSender())
                .type(chatMessage.getType())
                .roomId(chatMessage.getRoomId())
                .message(chatMessage.getMessage())
                .build();
    }
    public ResponseChatRoomDto toResponseChatRoomDto(ChatRoom chatRoom){
        return ResponseChatRoomDto.builder()
                .roomId(chatRoom.getRoomId())
                .build();
    }


}
