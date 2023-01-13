package com.product.application.s3;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImgRepository extends JpaRepository<Img, Long> {

    List<Img> findByReviewId(Long reviewId);
}
