package com.product.application.user.repository;

import com.product.application.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUseremail(String useremail);

    Optional<Users> findByNickname(String nickname);
}
