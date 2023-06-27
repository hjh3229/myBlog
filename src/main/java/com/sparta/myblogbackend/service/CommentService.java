package com.sparta.myblogbackend.service;

import com.sparta.myblogbackend.dto.CommentRequestDto;
import com.sparta.myblogbackend.dto.CommentResponseDto;
import com.sparta.myblogbackend.entity.Blog;
import com.sparta.myblogbackend.entity.Comment;
import com.sparta.myblogbackend.entity.User;
import com.sparta.myblogbackend.repository.CommentRepository;

import java.util.List;

public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    public CommentResponseDto createComment(CommentRequestDto requestDto, User user) {
            Comment comment = commentRepository.save(new Comment(requestDto, user));

            return new CommentResponseDto(comment);
    }

    public List<CommentResponseDto> getComments() {
        return commentRepository.findAllByOrderByModifiedAtDesc().stream().map(CommentResponseDto::new).toList();
    }

    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto) {
        Comment comment = findComment(id);
        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long id) {
        Comment comment = findComment(id);

        commentRepository.delete(comment);
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> // null 체크
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }
}
