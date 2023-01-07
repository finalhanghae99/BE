package com.product.application.reservation.repository;

import com.product.application.reservation.entity.Reservation;
import com.product.application.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {


}
