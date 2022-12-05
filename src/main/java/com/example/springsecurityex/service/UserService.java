package com.example.springsecurityex.service;

import com.example.springsecurityex.domain.User;
import com.example.springsecurityex.exception.AppException;
import com.example.springsecurityex.exception.ErrorCode;
import com.example.springsecurityex.repository.UserRepository;
import com.example.springsecurityex.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private Long expireTimeMs = 1000*60*60l;

    @Value("${jwt.token.secret}")
    private String key;
    public String join(String userName, String password){
        //userName 중복  check
        userRepository.findByUserName(userName)
                .ifPresent(user-> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED, userName + "는 이미 있습니다.");
                });

        //저장
        User user = User.builder()
                .userName(userName)
                .password(encoder.encode(password))
                .build();
        userRepository.save(user);

        return "SUCCESS";
    }

    public String login(String userName, String password) {
        //userName 없음
        User selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(()->new AppException(ErrorCode.USERNAME_NOT_FOUND,userName + "이 없습니다."));


        //password 틀림
        if(!encoder.matches(password,selectedUser.getPassword())){
            throw new AppException(ErrorCode.INVALID_PASSWORD,"패스워드가 잘못 되었습니다.");
        }
        String token = JwtTokenUtil.createToken(selectedUser.getUserName(),key,expireTimeMs);
        return token;
    }
}
