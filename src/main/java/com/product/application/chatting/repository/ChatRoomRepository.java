package com.product.application.chatting.repository;

import com.product.application.chatting.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    ChatRoom findByRoomId(String roomId);

    List<ChatRoom> findAllByBuyerAndSellerAndReservationId(String buyer, String seller, Long reservationId);

}
