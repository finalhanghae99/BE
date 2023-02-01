package com.product.application.chatting.controller;

import com.product.application.chatting.dto.RequestMessageDto;
import com.product.application.chatting.service.ChatService;
import com.product.application.common.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ChatMessageController {
    private final SimpMessageSendingOperations sendingOperations;
    private final ChatService chatService;

    @MessageMapping("/chat/message/{roomId}")
    public ResponseMessage<?> saveMessage(@RequestBody RequestMessageDto requestMessageDto) {
        sendingOperations.convertAndSend("/topic/chat/room/"+requestMessageDto.getRoomId(),requestMessageDto);
        chatService.saveMessage(requestMessageDto, requestMessageDto.getRoomId());
        return new ResponseMessage<>("Success", 200, null);
    }

}
