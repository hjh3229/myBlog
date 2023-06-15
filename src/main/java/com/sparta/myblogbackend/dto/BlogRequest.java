package com.sparta.myblogbackend.dto;

import lombok.Getter;

@Getter
public class BlogRequest {
    private String title;
    private String username;
    private String contents;
    private String password;
}
