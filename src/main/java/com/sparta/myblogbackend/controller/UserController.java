package com.sparta.myblogbackend.controller;

import com.sparta.myblogbackend.dto.SignupRequestDto;
import com.sparta.myblogbackend.dto.UserInfoDto;
import com.sparta.myblogbackend.entity.UserRoleEnum;
import com.sparta.myblogbackend.security.UserDetailsImpl;
import com.sparta.myblogbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/login-page")
    @ResponseBody
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user/sign-up")
    @ResponseBody
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/user/sign-up")
    public String signup(@RequestBody @Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return "redirect:/api/user/sign-up";
        }

        userService.signup(requestDto);

        return "redirect:/api/user/login-page";
    }

    @GetMapping("/user-info")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        UserRoleEnum role = userDetails.getUser().getRole();
        boolean isAdmin = (role == UserRoleEnum.ADMIN);

        return new UserInfoDto(username, isAdmin);
    }
}