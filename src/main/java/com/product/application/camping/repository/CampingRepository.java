package com.product.application.camping.repository;

import com.product.application.camping.entity.Camping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CampingRepository extends JpaRepository<Camping, Long> {
    List<Camping> findAllByAddress1(String address1);
    List<Camping> findAllByCampingNameContaining(String campingname);

    List<Camping> findAllByCampingNameContainingIgnoreCase(String campingname);
    @Query(value="select * from demo.camping order by camping_like_count desc LIMIT 5", nativeQuery = true)
    List<Camping> selectFiveSQL();
    @Transactional
    @Modifying
    @Query("select c from Camping c where Function('replace', c.campingName, ' ', '') like %:campingName%")
    List<Camping> selectCampingNameSQL(String campingName);

}
