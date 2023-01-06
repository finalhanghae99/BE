package com.product.application.reservation.service;

import com.product.application.camping.entity.Camping;
import com.product.application.common.exception.CustomException;
import com.product.application.common.exception.ErrorCode;
import com.product.application.reservation.dto.ReservationRequestDto;
import com.product.application.reservation.entity.Reservation;
import com.product.application.reservation.repository.ReservationRepository;
import com.product.application.user.entity.Users;
import com.product.application.user.jwt.JwtUtil;
import com.product.application.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void create(ReservationRequestDto reservationRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            Users users = userRepository.findByUseremail(claims.getSubject()).orElseThrow(
                    () -> new CustomException(ErrorCode.USER_NOT_FOUND)
            );

            reservationRepository.save(new Reservation(reservationRequestDto, users));

        } else {
            throw new CustomException(ErrorCode.TOKEN_ERROR);
        }
    }

    @Transactional
    public void delete(Long reservationId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            Users users = userRepository.findByUseremail(claims.getSubject()).orElseThrow(
                    () -> new CustomException(ErrorCode.USER_NOT_FOUND)
            );

            Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
                    () -> new CustomException(ErrorCode.CONTENT_NOT_FOUND)
            );

            reservationRepository.deleteById(reservationId);

        } else {
            throw new CustomException(ErrorCode.TOKEN_ERROR);
        }
    }
}
