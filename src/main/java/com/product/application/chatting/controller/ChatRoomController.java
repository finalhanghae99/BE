package com.product.application.chatting.controller;

import com.product.application.chatting.dto.ResponseChatListDto;
import com.product.application.chatting.service.ChatService;
import com.product.application.common.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatService chatService;
    @PostMapping("/{reservationId}")
    public ResponseMessage<?> createRoom(HttpServletRequest request, @PathVariable Long reservationId) {
        chatService.createRoom(request, reservationId);
        return new ResponseMessage<>("Success", 200, null);
    }

    //채팅방에 있는 양도글 정보
    @GetMapping("/room/{reservationId}/{roomId}")
    @ResponseBody
    public ResponseMessage reservationInfo(@PathVariable String roomId, @PathVariable Long reservationId){
        ResponseMessage responseMessage = chatService.reservationInfo(roomId, reservationId);
        return  responseMessage;
    }

    @GetMapping("/{roomId}")
    public ResponseMessage getnickname(@PathVariable String roomId, HttpServletRequest request){
        ResponseMessage responseMessage = chatService.getnickname(roomId, request);
        return responseMessage;
    }

    @GetMapping("/mypage/chatting")
    public ResponseMessage<?> userChattingInfo(HttpServletRequest request){
        ResponseChatListDto responseChatListDto = chatService.userChattingInfo(request);
        return  new ResponseMessage<>("Success", 200, responseChatListDto);
    }

    // 특정 채팅방 내용 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ResponseMessage roomInfo(@PathVariable String roomId) {
        ResponseMessage responseMessage = chatService.roomInfo(roomId);
        return responseMessage;
    }



}
