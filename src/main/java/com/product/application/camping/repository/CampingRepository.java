package com.product.application.camping.repository;

import com.product.application.camping.entity.Camping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CampingRepository extends JpaRepository<Camping, Long> {
    List<Camping> findAllByAddress1(String address1);
    List<Camping> findAllByCampingNameContaining(String campingname);

    @Query(value="select * from demo.camping order by camping_like_count desc LIMIT 5", nativeQuery = true)
    List<Camping> selectFiveSQL();
}
