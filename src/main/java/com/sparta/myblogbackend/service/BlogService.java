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
        List<BlogResponseDto> responseDtos = new ArrayList<>();
        List<Blog> blogs = blogRepository.findAllByOrderByModifiedAtDesc();
        for (Blog blog : blogs) {
            List<Comment> comments = getCommentsInBlog(blog);
            responseDtos.add(new BlogResponseDto(blog));
        }
        return responseDtos;
    }

    @Transactional(readOnly = true)
    public List<BlogResponseDto> getBlogsByKeyword(String keyword) {
        List<BlogResponseDto> responseDtos = new ArrayList<>();
        List<Blog> blogs = blogRepository.findAllByContentsContainingOrderByModifiedAtDesc(keyword);
        for (Blog blog : blogs) {
            List<Comment> comments = getCommentsInBlog(blog);
            responseDtos.add(new BlogResponseDto(blog));
        }
        return responseDtos;
    }

    @Transactional
    public BlogResponseDto updateBlog(Long id, UpdateBlogRequestDto requestDto) {
        Blog blog = findBlog(id);
        blog.update(requestDto);
        List<Comment> comments = getCommentsInBlog(blog);
        return new BlogResponseDto(blog);
    }


    public void deleteBlog(Long id) {
        Blog blog = findBlog(id);

        blogRepository.delete(blog);
    }

    private List<Comment> getCommentsInBlog(Blog blog) {
        return commentRepository.findAllByIdOrderByModifiedAtDesc(blog.getId());
    }


    private Blog findBlog(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> // null 체크
                new IllegalArgumentException("선택한 글는 존재하지 않습니다.")
        );
    }
}
