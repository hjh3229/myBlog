package com.sparta.myblogbackend.repository;

import com.sparta.myblogbackend.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByOrderByModifiedAtDesc();
    List<Blog> findAllByContentsContainingOrderByModifiedAtDesc(String keyword);
    Blog findByPasswordEquals(String password);
}
