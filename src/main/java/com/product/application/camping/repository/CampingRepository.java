package com.product.application.camping.repository;

import com.product.application.camping.entity.Camping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampingRepository extends JpaRepository<Camping, Long> {
    List<Camping> findAllByAddress1(String address1);
    List<Camping> findAllByCampingNameContaining(String campingname);
}
