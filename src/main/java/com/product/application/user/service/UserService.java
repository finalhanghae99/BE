package com.product.application.user.service;

import com.product.application.common.exception.CustomException;
import com.product.application.user.dto.EmailcheckRequestDto;
import com.product.application.user.dto.LoginRequestDto;
import com.product.application.user.dto.NicknamecheckRequestDto;
import com.product.application.user.dto.SignupRequestDto;
import com.product.application.user.entity.Users;
import com.product.application.user.jwt.JwtUtil;
import com.product.application.user.mapper.UserMapper;
import com.product.application.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.product.application.common.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class UserService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void signup(SignupRequestDto signupRequestDto) {
        Users users =userMapper.toUser(signupRequestDto);

        Optional<Users> check = userRepository.findByUseremail(users.getUseremail());
        if(check.isPresent()) {
            throw new CustomException(DUPLICATE_USEREMAIL);
        }
        userRepository.save(users);

    }
    @Transactional
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        //사용자 확인
        Users users = userRepository.findByUseremail(loginRequestDto.getUseremail()).orElseThrow(
                () -> new CustomException(USEREMAIL_NOT_FOUND)
        );
        //비밀번호 확인
        if(!users.getPassword().equals(loginRequestDto.getPassword())){
            throw new CustomException(INCORRECT_PASSWORD);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(users.getUseremail()));
    }

    public void emailcheck(EmailcheckRequestDto emailcheckRequestDto) {
       Optional<Users> check = userRepository.findByUseremail(emailcheckRequestDto.getUseremail());
       if(check.isPresent()){
           throw new CustomException(DUPLICATE_USEREMAIL);
       }
    }

    public void nicknamecheck(NicknamecheckRequestDto nicknamecheckRequestDto) {
        Optional<Users> check = userRepository.findByNickname(nicknamecheckRequestDto.getNickname());
        if(check.isPresent()){
            throw new CustomException(DUPLICATE_NICKNAME);
        }
    }
}
