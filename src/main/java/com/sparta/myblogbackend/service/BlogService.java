package com.sparta.myblogbackend.service;

import com.sparta.myblogbackend.dto.BlogRequestDto;
import com.sparta.myblogbackend.dto.BlogResponseDto;
import com.sparta.myblogbackend.entity.Blog;
import com.sparta.myblogbackend.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public String updateBlog(Long id, BlogRequestDto requestDto) {
        findBlog(id);

        Blog blog = new Blog(requestDto);

        String updatedContents = blog.getContents();
        return updatedContents;
    }


    public void deleteBlog(Long id) {
        Blog blog = findBlog(id);

        blogRepository.delete(blog);
    }


    private Blog findBlog(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> // null 체크
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }
}
