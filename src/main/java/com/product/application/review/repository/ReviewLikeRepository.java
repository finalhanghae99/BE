package com.product.application.review.repository;

import com.product.application.review.entity.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    Optional<ReviewLike> findByReviewIdAndUserId(Long reviewId, Long usersId);

    List<ReviewLike> findByReviewId(Long id);

    @Modifying
    @Query("delete from ReviewLike rl where rl.review.id = :id")
    void deleteAllByReviewId(@Param("id") Long reviewId);
}
