package com.sparta.myblogbackend.repository;

import com.sparta.myblogbackend.entity.Blog;
import com.sparta.myblogbackend.entity.BlogLike;
import com.sparta.myblogbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogLikeRepository extends JpaRepository<BlogLike, Long> {
    Optional<BlogLike>findByUserAndBlog(User user, Blog blog);
}
