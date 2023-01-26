package com.product.application.user.service;

import com.product.application.common.exception.CustomException;
import com.product.application.user.dto.*;
import com.product.application.user.entity.Users;
import com.product.application.user.jwt.JwtUtil;
import com.product.application.user.mapper.UserMapper;
import com.product.application.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.product.application.common.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class UserService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void signup(RequestSignupDto requestSignupDto) {
        Users users =userMapper.toUser(requestSignupDto);

        Optional<Users> checkuseremail = userRepository.findByUseremail(users.getUseremail());
        if(checkuseremail.isPresent()) {
            throw new CustomException(DUPLICATE_USEREMAIL);
        }
        Optional<Users> checkusernickname = userRepository.findByNickname(users.getNickname());
        if(checkusernickname.isPresent()){
            throw new CustomException(DUPLICATE_NICKNAME);
        }
        userRepository.save(users);

    }
    @Transactional
    public void login(RequestLoginDto requestLoginDto, HttpServletResponse response) {
        //사용자 확인
        Users users = userRepository.findByUseremail(requestLoginDto.getUseremail()).orElseThrow(
                () -> new CustomException(USEREMAIL_NOT_FOUND)
        );
        //비밀번호 확인
        if(!users.getPassword().equals(requestLoginDto.getPassword())){
            throw new CustomException(INCORRECT_PASSWORD);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(users.getUseremail()));
    }

    public void emailcheck(RequestEmailcheckDto requestEmailcheckDto) {
       Optional<Users> check = userRepository.findByUseremail(requestEmailcheckDto.getUseremail());
       if(check.isPresent()){
           throw new CustomException(DUPLICATE_USEREMAIL);
       }
    }

    public void nicknamecheck(RequestNicknamecheckDto requestNicknamecheckDto) {
        Optional<Users> check = userRepository.findByNickname(requestNicknamecheckDto.getNickname());
        if(check.isPresent()){
            throw new CustomException(DUPLICATE_NICKNAME);
        }
    }

    public ResponseUserNicknameDto getnickname(HttpServletRequest request) {
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

            String nickname = users.getNickname();
            ResponseUserNicknameDto responseUserNicknameDto = new ResponseUserNicknameDto(nickname);
            return responseUserNicknameDto;

        } else {
            throw new CustomException(TOKEN_ERROR);
        }
    }
}
