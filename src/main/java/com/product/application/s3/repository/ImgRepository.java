package com.product.application.s3.repository;

import com.product.application.s3.entity.Img;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImgRepository extends JpaRepository<Img, Long> {

    List<Img> findByReviewId(Long reviewId);
}
