package com.product.application.chatting.repository;

import com.product.application.chatting.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    


    List<ChatMessage> findAllBySender(String nickname);

    List<ChatMessage> findAllByRoomId(String roomId);


   ChatMessage findTopByRoomIdOrderBySendDateDesc(String roomId);

}
