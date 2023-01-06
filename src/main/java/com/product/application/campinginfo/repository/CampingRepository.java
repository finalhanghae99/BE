package com.product.application.campinginfo.repository;

import com.product.application.campinginfo.entity.Camping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampingRepository extends JpaRepository<Camping, Long> {
}
