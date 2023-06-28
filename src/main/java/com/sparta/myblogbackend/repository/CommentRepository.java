package com.sparta.myblogbackend.repository;

import com.sparta.myblogbackend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByOrderByModifiedAtDesc();

    List<Comment> findAllByIdOrderByModifiedAtDesc(Long id);
}
