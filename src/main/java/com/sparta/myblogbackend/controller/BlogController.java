package com.sparta.myblogbackend.controller;

import com.sparta.myblogbackend.dto.BlogRequestDto;
import com.sparta.myblogbackend.dto.BlogResponseDto;
import com.sparta.myblogbackend.service.BlogService;
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

    @PutMapping("/blog/update")
    public Long updateBlog(@PathVariable String password, @PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        return blogService.updateBlog(password, id, requestDto);
    }

    @DeleteMapping("/blog")
    public Long deleteBlog(@PathVariable String password, @PathVariable Long id) {
        return blogService.deleteBlog(password, id);
    }
}
