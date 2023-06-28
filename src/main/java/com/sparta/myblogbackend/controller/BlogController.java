package com.sparta.myblogbackend.controller;

import com.sparta.myblogbackend.dto.BlogRequestDto;
import com.sparta.myblogbackend.dto.BlogResponseDto;
import com.sparta.myblogbackend.dto.UpdateBlogRequestDto;
import com.sparta.myblogbackend.jwt.JwtUtil;
import com.sparta.myblogbackend.security.UserDetailsImpl;
import com.sparta.myblogbackend.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class BlogController {
    private BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/blog")
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return blogService.createBlog(requestDto, userDetails.getUser());
    }

    @GetMapping("/blogs")
    public List<BlogResponseDto> getBlogs() {
        return blogService.getBlogs();
    }

    @GetMapping("/blog/find")
    public List<BlogResponseDto> getBlogsByKeyword(@RequestParam String keyword) {
        return blogService.getBlogsByKeyword(keyword);
    }

    @PutMapping("/blog")
    public BlogResponseDto updateBlog(@RequestParam Long id, @RequestBody UpdateBlogRequestDto requestDto) {
        return blogService.updateBlog(id, requestDto);
    }

    @DeleteMapping("/blog")
    public ResponseEntity<String> deleteBlog(@RequestParam Long id) {
        blogService.deleteBlog(id);
        return ResponseEntity.status(HttpStatus.OK).body("글 삭제 성공");
    }
}
