package com.sparta.myblogbackend.controller;

import com.sparta.myblogbackend.dto.ApiResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.concurrent.RejectedExecutionException;

@RestControllerAdvice
@Slf4j
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalAccessException.class) // 허가되지 않은 접근
    public ResponseEntity<ApiResponseDto> handleIllegalAccess(IllegalAccessException e) {
        String ErrorMsg = e.getMessage();
        return ResponseEntity.badRequest().body(new ApiResponseDto(ErrorMsg, HttpStatus.FORBIDDEN.value()));
    }

    @ExceptionHandler(IllegalArgumentException.class) // 잘못된 값 전달
    public ResponseEntity<ApiResponseDto> handleIllegalArgument(IllegalArgumentException e) {
        String ErrorMsg = e.getMessage();
        return ResponseEntity.badRequest().body(new ApiResponseDto(ErrorMsg, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(RejectedExecutionException.class) // 접근 권한 없음
    public ResponseEntity<ApiResponseDto> handleRejectedExecution(RejectedExecutionException e) {
        String ErrorMsg = e.getMessage();
        return ResponseEntity.badRequest().body(new ApiResponseDto(ErrorMsg, HttpStatus.FORBIDDEN.value()));
    }

}
