package com.example.springsecurityex.repository;

import com.example.springsecurityex.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String userName); //userName 으로 찾아서 Optionalㅇ 저장한다
}
