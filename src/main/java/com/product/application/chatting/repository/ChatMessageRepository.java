package com.product.application.chatting.repository;

import com.product.application.chatting.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    


    List<ChatMessage> findAllBySender(String nickname);

    List<ChatMessage> findAllByRoomId(String roomId);


   ChatMessage findTopByRoomIdOrderBySendDateDesc(String roomId);


    List<ChatMessage> findAllByReceiver(String nickname);

    @Modifying
    @Query("delete from ChatMessage cm where cm.reservation.id = :id")
    void deleteAllByChatMessageId(@Param("id") Long reservationId);


}
