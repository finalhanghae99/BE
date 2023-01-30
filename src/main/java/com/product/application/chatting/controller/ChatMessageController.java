package com.product.application.chatting.controller;

import com.product.application.chatting.dto.RequestMessageDto;
import com.product.application.chatting.service.ChatService;
import com.product.application.common.ResponseMessage;
import com.product.application.user.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class ChatMessageController {
    private final SimpMessageSendingOperations sendingOperations;
    private final ChatService chatService;
    @CrossOrigin(origins = {"http://campingzipbeta.s3-website.ap-northeast-2.amazonaws.com", "http://localhost:3000"}, exposedHeaders = JwtUtil.AUTHORIZATION_HEADER)
    @MessageMapping("/chat/message/{reservationId}/{roomId}/{nickname}")
    public ResponseMessage<?> saveMessage(@RequestBody RequestMessageDto requestMessageDto,
                                          @PathVariable Long reservationId,
                                          @PathVariable String roomId,
                                          @PathVariable String nickname) {
        sendingOperations.convertAndSend("/topic/chat/room/"+roomId,requestMessageDto);
        chatService.saveMessage(requestMessageDto, reservationId, roomId, nickname);
        return new ResponseMessage<>("Success", 200, null);
    }


}
