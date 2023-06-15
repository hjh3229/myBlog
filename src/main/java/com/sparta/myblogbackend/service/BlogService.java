package com.sparta.myblogbackend.service;

import com.sparta.myblogbackend.dto.BlogRequestDto;
import com.sparta.myblogbackend.dto.BlogResponseDto;
import com.sparta.myblogbackend.entity.Blog;
import com.sparta.myblogbackend.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public BlogResponseDto createBlog(BlogRequestDto requestDto) {
        Blog blog =new Blog(requestDto);

        Blog saveBlog = blogRepository.save(blog);

        BlogResponseDto blogResponseDto = new BlogResponseDto(saveBlog);

        return blogResponseDto;
    }

    public List<BlogResponseDto> getBlogs() {
        return blogRepository.findAllByOrderByModifiedAtDesc().stream().map(BlogResponseDto::new).toList();
    }

    public List<BlogResponseDto> getBlogsByKeyword(String keyword) {
        return blogRepository.findAllByContentsContainingOrderByModifiedAtDesc(keyword).stream().map(BlogResponseDto::new).toList();
    }

    public Long updateBlog(String password, Long id, BlogRequestDto requestDto) {
        matchPassword(password);
        Blog blog = findBlog(id);

        blog.update(requestDto);
        return id;
    }

    private Blog findBlog(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> // null 체크
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }

    private void matchPassword(String password) {
        Blog blog = blogRepository.findByPasswordEquals(password);
        if (blog == null) {
            throw new NullPointerException("비밀번호가 일치하지 않습니다.");
        }
    }

    public Long deleteBlog(String password, Long id) {
        matchPassword(password);
        Blog blog = findBlog(id);

        blogRepository.delete(blog);
        return id;
    }
}
