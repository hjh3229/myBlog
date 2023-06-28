package com.sparta.myblogbackend.dto;

import com.sparta.myblogbackend.entity.Blog;
import com.sparta.myblogbackend.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BlogResponseDto {
    private Long id;
    private String username;
    private String title;
    private String contents;
    private List<Comment> comments;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.username = blog.getUsername();
        this.title = blog.getTitle();
        this.contents = blog.getContents();
        this.comments = blog.getComments();
        this.createdAt = blog.getCreatedAt();
        this.modifiedAt = blog.getModifiedAt();
    }
}
