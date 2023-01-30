package com.product.application.reservation.service;

import com.product.application.camping.entity.Camping;
import com.product.application.camping.repository.CampingRepository;
import com.product.application.common.ResponseMessage;
import com.product.application.common.exception.CustomException;
import com.product.application.common.exception.ErrorCode;
import com.product.application.reservation.dto.*;
import com.product.application.reservation.entity.Reservation;
import com.product.application.reservation.mapper.ReservationMapper;
import com.product.application.reservation.repository.ReservationRepository;
import com.product.application.review.dto.ReviewLikeResponseDto;
import com.product.application.review.entity.Review;
import com.product.application.review.entity.ReviewLike;
import com.product.application.user.entity.Users;
import com.product.application.user.jwt.JwtUtil;
import com.product.application.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.product.application.common.exception.ErrorCode.*;


@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final CampingRepository campingRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper;
    private final JwtUtil jwtUtil;


    @Transactional
    public void create(RequestReservationDto requestReservationDto, HttpServletRequest request, Long campingId) {
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

            Camping camping = campingRepository.findById(campingId).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
            Reservation reservation = reservationMapper.toRequestReservationDto(users, camping, requestReservationDto);

            reservationRepository.save(reservation);

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

    @Transactional
    public ResponseMessage getReservationList(LocalDate startDate, LocalDate endDate, String address1, String address2) {

        List<Reservation> reservationList;
        List<Reservation> returnReservationList = new ArrayList<>();

        if (startDate != null && endDate != null) {
            reservationList = reservationRepository.findAllByStartDateBetween(startDate, endDate);
            if (address1 != null) {
                if (address2 != null) {
                    for (Reservation reservation : reservationList) {
                        if (reservation.getCamping().getAddress1().equals(address1) && reservation.getCamping().getAddress2().equals(address2)) {
                            returnReservationList.add(reservation);
                        }
                    }
                } else {
                    for (Reservation reservation : reservationList) {
                        if (reservation.getCamping().getAddress1().equals(address1)) {
                            returnReservationList.add(reservation);
                        }
                    }
                }
            } else {
                for (Reservation reservation : reservationList) {
                    returnReservationList.add(reservation);
                }
            }
        } else if (address1 != null && address2 != null) {
            reservationList = reservationRepository.findAllByCampingAddress1(address1);
            for (Reservation reservation : reservationList) {
                if (reservation.getCamping().getAddress1().equals(address1) && reservation.getCamping().getAddress2().equals(address2)) {
                    returnReservationList.add(reservation);
                }
            }
        } else if (address1 != null) {
            reservationList = reservationRepository.findAllByCampingAddress1(address1);
            for (Reservation reservation : reservationList) {
                if (reservation.getCamping().getAddress1().equals(address1)) {
                    returnReservationList.add(reservation);
                }
            }
        } else {
            throw new CustomException(ErrorCode.SEARCH_REQUIREMENT_ERROR);
        }

        List<ResponseSearchDto> responseSearchDtoList = new ArrayList<>();
        for (Reservation reservation : returnReservationList) {
            ResponseSearchDto responseSearchDto = reservationMapper.toResponseSearchDto(reservation, reservation.getCamping());
            responseSearchDtoList.add(responseSearchDto);
        }
        return new ResponseMessage("Success", 200, new SearchDtoList(responseSearchDtoList));
    }

    public ResponseMessage getReservation(Long reservationId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        Long usersId;

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new CustomException(TOKEN_ERROR);
            }

            Users users = userRepository.findByUseremail(claims.getSubject()).orElseThrow(
                    () -> new CustomException(USER_NOT_FOUND)
            );
            usersId = users.getId();

        } else {
            usersId = 0L;
        }

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
        ResponseReservationDto responseReservationDto = reservationMapper.toresponseReservationDto(reservation, reservation.getCamping(), reservation.getUsers(), usersId);

        return new ResponseMessage("Success", 200, responseReservationDto);
    }

    @Transactional
    public ResponseMessage viewListSix() {
        List<Reservation> reservationList;
        List<Reservation> responseFindListSix = new ArrayList<>();

        reservationList = reservationRepository.findTop6ByOrderByIdDesc();

        for (Reservation reservation : reservationList) {
            responseFindListSix.add(reservation);
        }

        List<ResponseSearchDto> responseFindListSixDtoList = new ArrayList<>();
        for (Reservation reservation : responseFindListSix) {
            ResponseSearchDto responseSearchDto = reservationMapper.toResponseSearchDto(reservation, reservation.getCamping());
            responseFindListSixDtoList.add(responseSearchDto);
        }

        return new ResponseMessage<>("Success", 200, responseFindListSixDtoList);
    }

    public void updateState(Long reservationId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new CustomException(TOKEN_ERROR);
            }

            Users users = userRepository.findByUseremail(claims.getSubject()).orElseThrow(
                    () -> new CustomException(USER_NOT_FOUND)
            );
            Long usersId = users.getId();

            //reservation글을 찾아서 상태값을 변경한다./
            Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
                    () -> new CustomException(RESERVATION_NOT_FOUND)
            );

            if(!reservation.getUsers().getId().equals(usersId)){
                throw new CustomException(AUTHORIZATION_UPDATE_FAIL);
            }

            if(reservation.isTradeState()){
                reservation.updateState(false);
                reservationRepository.save(reservation);
            } else {
                reservation.updateState(true);
                reservationRepository.save(reservation);
            }


        } else {
            throw new CustomException(TOKEN_ERROR);
        }
    }

    public void updateReservation(RequestReservationDto requestReservationDto, HttpServletRequest request, Long reservationId) {
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
                    () -> new CustomException(RESERVATION_NOT_FOUND)
            );

            if(!users.getId().equals(reservation.getUsers().getId())){
                throw new CustomException(AUTHORIZATION_UPDATE_FAIL);
            }

            reservation.update(requestReservationDto);
            reservationRepository.save(reservation);
        } else {
            throw new CustomException(ErrorCode.TOKEN_ERROR);
        }
    }
}











