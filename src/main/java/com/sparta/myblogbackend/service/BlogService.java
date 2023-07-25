package com.sparta.myblogbackend.service;

import com.sparta.myblogbackend.dto.BlogRequestDto;
import com.sparta.myblogbackend.dto.BlogResponseDto;
import com.sparta.myblogbackend.dto.UpdateBlogRequestDto;
import com.sparta.myblogbackend.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface BlogService {

    BlogResponseDto createBlog(BlogRequestDto requestDto, User user);

    @Transactional(readOnly = true)
    List<BlogResponseDto> getBlogs();

    @Transactional(readOnly = true)
    List<BlogResponseDto> getBlogsByKeyword(String keyword);

    @Transactional
    BlogResponseDto updateBlog(Long id, UpdateBlogRequestDto requestDto, User user);

    void deleteBlog(Long id, User user);


    @Transactional
    String like(Long blogId, Long userId);

    boolean isLiked(Long blogId, Long userId);
}
