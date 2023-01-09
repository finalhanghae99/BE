package com.product.application.camping.repository;

import com.product.application.camping.entity.Camping;
import com.product.application.camping.entity.CampingLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CampingLikeRepository extends JpaRepository<CampingLike, Long> {
    Optional<CampingLike> findByCampingAndUsersId(Camping tempCamping, Long id);
}
