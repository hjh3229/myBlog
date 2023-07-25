package com.sparta.myblogbackend.service;

import com.sparta.myblogbackend.dto.CommentRequestDto;
import com.sparta.myblogbackend.dto.CommentResponseDto;
import com.sparta.myblogbackend.entity.User;
import org.springframework.transaction.annotation.Transactional;



public interface CommentService {

    CommentResponseDto createComment(CommentRequestDto requestDto, User user, Long blog_id);

    @Transactional
    CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, User user);

    void deleteComment(Long id, User user);

    void like(Long commentId, Long userId);

    boolean isLiked(Long commentId, Long userId);
}
