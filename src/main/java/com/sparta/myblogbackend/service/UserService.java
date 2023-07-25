package com.sparta.myblogbackend.service;

import com.sparta.myblogbackend.dto.LoginRequestDto;
import com.sparta.myblogbackend.dto.SignupRequestDto;
import com.sparta.myblogbackend.entity.User;
import com.sparta.myblogbackend.entity.UserRoleEnum;
import com.sparta.myblogbackend.jwt.JwtUtil;
import com.sparta.myblogbackend.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UserService {
    void signup(SignupRequestDto requestDto);
}
