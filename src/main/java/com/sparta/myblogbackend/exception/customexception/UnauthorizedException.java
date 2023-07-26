package com.sparta.myblogbackend.exception.customexception;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException{
  private static final String message = "접근 권한이 없습니다.";
  public UnauthorizedException() {

  }

  public UnauthorizedException(String message) {
    super(message);
  }
}
