package com.product.application.chatting.entity;

import com.product.application.reservation.entity.Reservation;
import com.product.application.review.entity.ReviewLike;
import com.product.application.user.entity.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatRoomId")
    private Long id;
    private String roomId;
    private String buyer;
    private String seller;
    private String sellerProfileImageUrl;
    private String buyerProfileImageUrl;
    @ManyToOne
    @JoinColumn(name="reservationId")
    private Reservation reservation;


    public static ChatRoom create(Users users, Reservation reservation) {
        ChatRoom room = new ChatRoom();
        room.roomId = UUID.randomUUID().toString();
        room.buyer = users.getNickname();
        room.seller = reservation.getUsers().getNickname();
        room.sellerProfileImageUrl = reservation.getUsers().getProfileImageUrl();
        room.buyerProfileImageUrl = users.getProfileImageUrl();
        room.reservation = reservation;
        return room;
    }



}
