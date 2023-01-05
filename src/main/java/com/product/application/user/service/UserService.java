package com.product.application.user.service;

import com.product.application.common.exception.CustomException;
import com.product.application.user.dto.SignupRequestDto;
import com.product.application.user.entity.Users;
import com.product.application.user.mapper.UserMapper;
import com.product.application.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.product.application.common.exception.ErrorCode.DUPLICATE_USEREMAIL;

@RequiredArgsConstructor
@Service
public class UserService {

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
}
