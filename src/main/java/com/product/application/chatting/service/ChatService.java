package com.product.application.chatting.service;

import com.product.application.chatting.dto.*;
import com.product.application.chatting.entity.ChatMessage;
import com.product.application.chatting.entity.ChatRoom;
import com.product.application.chatting.mapper.ChattingMapper;
import com.product.application.chatting.repository.ChatMessageRepository;
import com.product.application.chatting.repository.ChatRoomRepository;
import com.product.application.common.ResponseMessage;
import com.product.application.common.exception.CustomException;
import com.product.application.common.exception.ErrorCode;
import com.product.application.reservation.entity.Reservation;
import com.product.application.reservation.repository.ReservationRepository;
import com.product.application.user.entity.Users;
import com.product.application.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;




@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final ChattingMapper chattingMapper;

    public void createRoom(Users users, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
                () -> new CustomException(ErrorCode.CONTENT_NOT_FOUND)
        );
        ChatRoom chatRoom = ChatRoom.create(users, reservation);
        chatRoomRepository.save(chatRoom);
    }


    @Transactional
    public void saveMessage(RequestMessageDto requestMessageDto, Long reservationId, String roomId, Users user) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
        ChatMessage chatMessage = chattingMapper.toRequestMessageDto(requestMessageDto, chatRoom, reservation, roomId, user.getNickname());
        chatMessageRepository.save(chatMessage);
    }


    public ResponseMessage reservationInfo(String roomId, Long reservationId) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
        ResponseChatReservationDto responseChatReservationDto = chattingMapper.toResponseChatMessageDto(chatRoom, reservation);
        return new ResponseMessage("Success", 200, responseChatReservationDto);
    }

    public ResponseMessage getnickname(String roomId, Users user) {
            ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
            ResponseChatNicknameDto responseChatNicknameDto = chattingMapper.toresponseChatNicknameDto(chatRoom);
            return new ResponseMessage<>("Success", 200, responseChatNicknameDto);
        }


    public ResponseChatListDto userChattingInfo(Users users) {
        List<ChatMessage> senderList = chatMessageRepository.findAllBySender(users.getNickname());
        List<ChatMessage> receiverList = chatMessageRepository.findAllByReceiver(users.getNickname());
        Set<String> roomList = new HashSet<>();
        for (ChatMessage chatMessage : senderList) {
            roomList.add(chatMessage.getRoomId());
        }
        for (ChatMessage chatMessage : receiverList) {
            roomList.add(chatMessage.getRoomId());
        }
        List<ResponseChattingDto> responseChattingDtoList = new ArrayList<>();
        for (String roomId : roomList) {
            ChatMessage chatMessageList = chatMessageRepository.findTopByRoomIdOrderBySendDateDesc(roomId);
            ResponseChattingDto responseChattingDto = chattingMapper.toResponseChattingDto(chatMessageList);
            responseChattingDtoList.add(responseChattingDto);
        }
        ResponseChatListDto responseChatListDto = new ResponseChatListDto(responseChattingDtoList);
        return responseChatListDto;
    }


    public ResponseMessage roomInfo(String roomId) {
        List<ChatMessage> chatMessageList = chatMessageRepository.findAllByRoomId(roomId);
        List<ResponseChatMessageDto> responseChatMessageDtoList = new ArrayList<>();

        for (ChatMessage chatMessage : chatMessageList) {
            ResponseChatMessageDto responseChatMessageDto = chattingMapper.toresponseChatMessageDto(chatMessage);
            responseChatMessageDtoList.add(responseChatMessageDto);
        }

        return new ResponseMessage("Success", 200, responseChatMessageDtoList);
    }

}



