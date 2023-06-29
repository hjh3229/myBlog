package com.sparta.myblogbackend.service;

import com.sparta.myblogbackend.dto.BlogRequestDto;
import com.sparta.myblogbackend.dto.BlogResponseDto;
import com.sparta.myblogbackend.dto.UpdateBlogRequestDto;
import com.sparta.myblogbackend.entity.Blog;
import com.sparta.myblogbackend.entity.Comment;
import com.sparta.myblogbackend.entity.User;
import com.sparta.myblogbackend.repository.BlogRepository;
import com.sparta.myblogbackend.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;

    public BlogService(BlogRepository blogRepository, CommentRepository commentRepository) {
        this.blogRepository = blogRepository;
        this.commentRepository = commentRepository;
    }

    public BlogResponseDto createBlog(BlogRequestDto requestDto, User user) {
        Blog blog= blogRepository.save(new Blog(requestDto, user));

        return new BlogResponseDto(blog);
    }

    @Transactional(readOnly = true)
    public List<BlogResponseDto> getBlogs() {
        return blogRepository.findAllByOrderByModifiedAtDesc().stream().map(BlogResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public List<BlogResponseDto> getBlogsByKeyword(String keyword) {
        if (keyword == null) {
            throw new RuntimeException("키워드를 입력해주세요");
        }
        return blogRepository.findAllByContentsContainingOrderByModifiedAtDesc(keyword).stream().map(BlogResponseDto::new).toList();
    }

    @Transactional
    public BlogResponseDto updateBlog(Long id, UpdateBlogRequestDto requestDto) {
        Blog blog = findBlog(id);
        blog.update(requestDto);
        return new BlogResponseDto(blog);
    }


    public void deleteBlog(Long id) {
        Blog blog = findBlog(id);

        blogRepository.delete(blog);
    }

    private Blog findBlog(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> // null 체크
                new IllegalArgumentException("선택한 글는 존재하지 않습니다.")
        );
    }
}
