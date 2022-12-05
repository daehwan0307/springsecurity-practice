package com.example.springsecurityex.controller;

import com.example.springsecurityex.domain.dto.UserJoinRequest;
import com.example.springsecurityex.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserJoinRequest dto){
        userService.join(dto.getUserName(),dto.getPassword());
        return ResponseEntity.ok().body("회원가입이 성공 했습니다.");
    }
}
