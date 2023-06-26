package com.sparta.myblogbackend.controller;

import com.sparta.myblogbackend.dto.BlogRequestDto;
import com.sparta.myblogbackend.dto.BlogResponseDto;
import com.sparta.myblogbackend.jwt.JwtUtil;
import com.sparta.myblogbackend.service.BlogService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BlogController {
    private BlogService blogService;

    private final JwtUtil jwtUtil;

    @PostMapping("/blog")
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto, HttpServletResponse res) {
        String token = jwtUtil.createToken(requestDto.getUsername());
        jwtUtil.addJwtToCookie(token, res);
        return blogService.createBlog(requestDto);
    }

    @GetMapping("/blog")
    public List<BlogResponseDto> getBlogs() {
        return blogService.getBlogs();
    }

    @GetMapping("/blog/find")
    public List<BlogResponseDto> getBlogsByKeyword(@RequestParam String keyword) {
        return blogService.getBlogsByKeyword(keyword);
    }

    @PutMapping("/blog")
    @ResponseBody
    public String updateBlog(@RequestParam Long id, @RequestBody BlogRequestDto requestDto, @RequestHeader String tokenValue) {
        String token = jwtUtil.substringToken(tokenValue);

        if(!jwtUtil.validateToken(token)){
            throw new IllegalArgumentException("Token Error");
        }
        return blogService.updateBlog(id, requestDto);
    }

    @DeleteMapping("/blog")
    public ResponseEntity<String> deleteBlog(@RequestParam Long id, @RequestHeader String tokenValue) {
        String token = jwtUtil.substringToken(tokenValue);

        if(!jwtUtil.validateToken(token)){
            throw new IllegalArgumentException("Token Error");
        }
        blogService.deleteBlog(id);
        return ResponseEntity.status(HttpStatus.OK).body("삭제 성공");
    }
}
