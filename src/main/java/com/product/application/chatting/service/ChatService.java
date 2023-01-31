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
import com.product.application.user.jwt.JwtUtil;
import com.product.application.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

import static com.product.application.common.exception.ErrorCode.TOKEN_ERROR;
import static com.product.application.common.exception.ErrorCode.USER_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class ChatService {

    private final JwtUtil jwtUtil;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final ChattingMapper chattingMapper;

    public void createRoom(HttpServletRequest request, Long reservationId) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            Users users = userRepository.findByUseremail(claims.getSubject()).orElseThrow(
                    () -> new CustomException(ErrorCode.USER_NOT_FOUND)
            );
            Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
            ChatRoom chatRoom = ChatRoom.create(users, reservation);
            chatRoomRepository.save(chatRoom);
        } else {
            throw new CustomException(ErrorCode.TOKEN_ERROR);
        }
    }

    @Transactional
    public void saveMessage(RequestMessageDto requestMessageDto,Long reservationId, String roomId, String nickname) {
        Users user = userRepository.findByNickname(nickname).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
        ChatMessage chatMessage = chattingMapper.toRequestMessageDto(requestMessageDto, user.getNickname(), chatRoom, reservation);
        chatMessageRepository.save(chatMessage);
    }

    public ResponseMessage reservationInfo(String roomId, Long reservationId) {
        List<ChatMessage> chatMessageList = chatMessageRepository.findAllByRoomId(roomId);
        List<ResponseChatReservationDto> responseChatReservationDtoList = new ArrayList<>();

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));

        for (ChatMessage chatMessage : chatMessageList) {
            ResponseChatReservationDto responseChatReservationDto = chattingMapper.toResponseChatMessageDto(chatMessage, reservation);
            responseChatReservationDtoList.add(responseChatReservationDto);
        }

        return new ResponseMessage("Success", 200, responseChatReservationDtoList);
    }

    public ResponseMessage getnickname(String roomId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new CustomException(ErrorCode.TOKEN_ERROR);
            }
            Users users = userRepository.findByUseremail(claims.getSubject()).orElseThrow(
                    () -> new CustomException(ErrorCode.USER_NOT_FOUND)
            );

            ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);

            ResponseChatNicknameDto responseChatNicknameDto = chattingMapper.toresponseChatNicknameDto(chatRoom);

            return new ResponseMessage<>("Success", 200, responseChatNicknameDto);

        } else {
            throw new CustomException(ErrorCode.TOKEN_ERROR);
        }
    }

    public ResponseChatListDto userChattingInfo(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new CustomException(TOKEN_ERROR);
            }
            Users users = userRepository.findByUseremail(claims.getSubject()).orElseThrow(
                    () -> new CustomException(USER_NOT_FOUND)
            );

            List<ChatMessage> nicknameList = chatMessageRepository.findAllBySender(users.getNickname());
            Set<String> roomList = new HashSet<>();
            for (ChatMessage chatMessage : nicknameList) {
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
        } else {
            throw new CustomException(TOKEN_ERROR);
        }

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



