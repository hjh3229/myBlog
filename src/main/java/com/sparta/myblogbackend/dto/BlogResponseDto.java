package com.sparta.myblogbackend.dto;

import com.sparta.myblogbackend.entity.Blog;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BlogResponseDto {
    private Long id;
    private String title;
    private String username;
    private String contents;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.username = blog.getUsername();
        this.contents = blog.getContents();
        this.password = blog.getPassword();
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
