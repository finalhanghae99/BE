package com.product.application.chatting.repository;

import com.product.application.chatting.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    ChatRoom findByRoomId(String roomId);

    List<ChatRoom> findAllByBuyerAndSellerAndReservationId(String buyer, String seller, Long reservationId);

    @Modifying
    @Query("delete from ChatRoom cr where cr.reservation.id = :id")
    void deleteAllByChatRoomId(@Param("id") Long reservationId);


}
