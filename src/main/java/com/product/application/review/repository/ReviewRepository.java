package com.product.application.review.repository;

import com.product.application.camping.entity.Camping;
import com.product.application.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByCamping(Camping camping);
    Long countByCamping(Camping camping);
}
