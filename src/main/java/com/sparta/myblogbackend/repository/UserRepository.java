package com.sparta.myblogbackend.repository;

import com.sparta.myblogbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryQuery {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
