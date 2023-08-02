package com.sparta.myblogbackend.controller;

import com.sparta.myblogbackend.common.PageDto;
import com.sparta.myblogbackend.dto.BlogRequestDto;
import com.sparta.myblogbackend.dto.BlogResponseDto;
import com.sparta.myblogbackend.dto.UpdateBlogRequestDto;
import com.sparta.myblogbackend.entity.User;
import com.sparta.myblogbackend.jwt.JwtUtil;
import com.sparta.myblogbackend.security.UserDetailsImpl;
import com.sparta.myblogbackend.service.BlogService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BlogController {
    private final BlogService blogService;

    @PostMapping("/blog")
    public BlogResponseDto createBlog(@RequestPart("requestDto") BlogRequestDto requestDto, @RequestPart("multipartFile") MultipartFile multipartFile, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        log.info("게시글 생성 시도");
        try {
            log.info("게시글 생성 성공");
            return blogService.createBlog(requestDto, userDetails.getUser(), multipartFile);
        } catch (Exception e) {
            throw new RuntimeException("게시글 작성 실패");
        }
    }

    @GetMapping("/blogs")
    public List<BlogResponseDto> getBlogs() {
        return blogService.getBlogs();
    }

    @GetMapping("/blogs/find")
    public List<BlogResponseDto> getBlogsByKeyword(@RequestParam String keyword) {
        return blogService.getBlogsByKeyword(keyword);
    }

    @GetMapping("/blogs/get")
    public Page<BlogResponseDto> getBlogByKeyword(@RequestParam String keyword, @RequestBody PageDto pageDto) {
        return blogService.getBlogByKeyword(keyword, pageDto);
    }

    @PutMapping("/blog")
    public BlogResponseDto updateBlog(@RequestParam Long id, @RequestBody UpdateBlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return blogService.updateBlog(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/blog")
    public ResponseEntity<String> deleteBlog(@RequestParam Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        blogService.deleteBlog(id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body("글 삭제 성공");
    }

    @PostMapping("/blog/like")
    public String like(@RequestParam Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return blogService.like(id, userDetails.getUser().getId());
    }

    @GetMapping("/blog/like")
    public boolean isLiked(@RequestParam Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return blogService.isLiked(id, userDetails.getUser().getId());
    }
}
