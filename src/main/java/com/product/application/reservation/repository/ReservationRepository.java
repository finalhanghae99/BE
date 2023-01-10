package com.product.application.reservation.repository;

import com.product.application.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


import java.time.LocalDate;
import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByStartDateBetween(LocalDate startDate, LocalDate endDate);

    List<Reservation> findAllByCampingAddress1(String address1);
}
