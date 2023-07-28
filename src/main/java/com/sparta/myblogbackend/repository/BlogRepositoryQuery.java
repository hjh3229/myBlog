package com.sparta.myblogbackend.repository;

import com.sparta.myblogbackend.common.PageDto;
import com.sparta.myblogbackend.dto.BlogResponseDto;
import com.sparta.myblogbackend.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogRepositoryQuery {

  Page<BlogResponseDto> findBlogsByKeyword(String keyword, Pageable pageable);
}
