package com.product.application.reservation.service;

import com.product.application.camping.entity.Camping;
import com.product.application.camping.repository.CampingRepository;
import com.product.application.chatting.repository.ChatMessageRepository;
import com.product.application.chatting.repository.ChatRoomRepository;
import com.product.application.common.ResponseMessage;
import com.product.application.common.exception.CustomException;
import com.product.application.common.exception.ErrorCode;
import com.product.application.reservation.dto.RequestReservationDto;
import com.product.application.reservation.dto.ResponseReservationDto;
import com.product.application.reservation.dto.ResponseSearchDto;
import com.product.application.reservation.dto.SearchDtoList;
import com.product.application.reservation.entity.Reservation;
import com.product.application.reservation.mapper.ReservationMapper;
import com.product.application.reservation.repository.ReservationRepository;
import com.product.application.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.product.application.common.exception.ErrorCode.AUTHORIZATION_UPDATE_FAIL;
import static com.product.application.common.exception.ErrorCode.RESERVATION_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final CampingRepository campingRepository;
    private final ReservationMapper reservationMapper;

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;


    @Transactional
    public void create(RequestReservationDto requestReservationDto, Users users, Long campingId) {
        Camping camping = campingRepository.findById(campingId).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
        Reservation reservation = reservationMapper.toRequestReservationDto(users, camping, requestReservationDto);

        reservationRepository.save(reservation);
    }

    @Transactional
    public void delete(Long reservationId, Long usersId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
                () -> new CustomException(ErrorCode.CONTENT_NOT_FOUND)
        );

        if(!reservation.getUsers().getId().equals(usersId)){
            throw new CustomException(ErrorCode.AUTHORIZATION_DELETE_FAIL);
        }
        chatMessageRepository.deleteAllByChatMessageId(reservationId);
        chatRoomRepository.deleteAllByChatRoomId(reservationId);
        reservationRepository.delete(reservation);
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

    public ResponseMessage getReservation(Long reservationId, Long usersId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
        ResponseReservationDto responseReservationDto = reservationMapper.toresponseReservationDto(reservation, reservation.getCamping(), reservation.getUsers(), usersId);

        return new ResponseMessage("Success", 200, responseReservationDto);
    }

    @Transactional
    public ResponseMessage viewListSix() {
        List<Reservation> reservationList;
        List<Reservation> responseFindListSix = new ArrayList<>();

        reservationList = reservationRepository.findingsixSQL();

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

    public void updateState(Long reservationId, Long usersId) {
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

    }

    public void updateReservation(RequestReservationDto requestReservationDto, Users users, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
                () -> new CustomException(RESERVATION_NOT_FOUND)
        );

        if(!users.getId().equals(reservation.getUsers().getId())){
            throw new CustomException(AUTHORIZATION_UPDATE_FAIL);
        }

        reservation.update(requestReservationDto);
        reservationRepository.save(reservation);
    }
}











