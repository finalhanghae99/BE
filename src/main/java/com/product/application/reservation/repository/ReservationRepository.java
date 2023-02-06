package com.product.application.reservation.repository;

import com.product.application.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByStartDateBetween(LocalDate startDate, LocalDate endDate);

    List<Reservation> findAllByCampingAddress1(String address1);

    List<Reservation> findTop6ByOrderByIdDesc();

    List<Reservation> findAllByUsersId(Long usersId);

    @Query(value="select * from demo.reservation where trade_state ='1' order by id desc LIMIT 6", nativeQuery = true)
    List<Reservation> findingsixSQL();


}
