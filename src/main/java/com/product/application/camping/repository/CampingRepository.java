package com.product.application.camping.repository;

import com.product.application.camping.entity.Camping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampingRepository extends JpaRepository<Camping, Long> {
    List<Camping> findAllByAddress2(String address2);
    List<Camping> findAllByAddress1(String address1);
    List<Camping> findAllByCampingNameContaining(String campingname);

    /*public interface CampingRepository extends JpaRepository<Camping, Long> {
        //일반 SQL 쿼리
        //@Query(value="select address2, COUNT(*) from Camping group by Camping.address2 having count(*) > 1", nativeQuery = true)
        //public List<Camping> selectAllSQL();
    }*/
}
