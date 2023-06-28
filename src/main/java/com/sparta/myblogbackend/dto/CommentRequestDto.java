package com.sparta.myblogbackend.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long blog_id;
    private String comments;
}
