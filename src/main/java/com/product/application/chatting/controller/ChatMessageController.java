package com.product.application.chatting.controller;

import com.product.application.chatting.dto.RequestMessageDto;
import com.product.application.chatting.service.ChatService;
import com.product.application.common.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ChatMessageController {
    private final SimpMessageSendingOperations sendingOperations;
    private final ChatService chatService;
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
