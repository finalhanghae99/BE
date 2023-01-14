package com.product.application.review.repository;

import com.product.application.camping.entity.Camping;
import com.product.application.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByCamping(Camping camping);
    Long countByCamping(Camping camping);

    List<Review> findTop5ByCampingIdOrderByModifiedAtDesc(Long campingId);

    @Query(value="select * from REVIEW order by Like_Count desc LIMIT 100", nativeQuery = true)
    List<Review> selectAllSQL();
    @Query(value="select * from REVIEW order by LIKE_COUNT desc LIMIT 6", nativeQuery = true)
    List<Review> selectSixSQL();

    List<Review> findAllByusersId(Long usersId);
}
