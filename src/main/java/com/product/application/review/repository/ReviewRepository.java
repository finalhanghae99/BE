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

    @Query(value="select * from demo.review order by like_count desc LIMIT 100", nativeQuery = true)
    List<Review> selectAllSQL();
    //List<Review> findTop100OrderByLike_COuntDesc();
    @Query(value="select * from demo.review order by lIKE_cOUNT desc LIMIT 6", nativeQuery = true)
    List<Review> selectSixSQL();

    List<Review> findAllByusersId(Long usersId);
}
