package com.product.application.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String useremail;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column
    private String profileImageUrl;

    @Builder
    public Users(String useremail, String nickname, String password){
        this.useremail = useremail;
        this.nickname = nickname;
        this.password = password;
    }
}
