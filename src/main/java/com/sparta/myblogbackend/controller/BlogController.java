package com.sparta.myblogbackend.controller;

import com.sparta.myblogbackend.dto.BlogRequestDto;
import com.sparta.myblogbackend.dto.BlogResponseDto;
import com.sparta.myblogbackend.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BlogController {
    private BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/blog")
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto) {
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
    public String updateBlog(@RequestParam String password, @RequestParam Long id, @RequestBody BlogRequestDto requestDto) {
        return blogService.updateBlog(password, id, requestDto);
    }

    @DeleteMapping("/blog")
    public ResponseEntity<String> deleteBlog(@RequestParam String password, @RequestParam Long id) {
        blogService.deleteBlog(password, id);
        return ResponseEntity.status(HttpStatus.OK).body("삭제 성공");
    }
}
