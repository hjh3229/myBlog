package com.sparta.myblogbackend.service;

import com.sparta.myblogbackend.dto.CommentRequestDto;
import com.sparta.myblogbackend.dto.CommentResponseDto;
import com.sparta.myblogbackend.entity.Blog;
import com.sparta.myblogbackend.entity.Comment;
import com.sparta.myblogbackend.entity.User;
import com.sparta.myblogbackend.repository.BlogRepository;
import com.sparta.myblogbackend.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;

    public CommentService(CommentRepository commentRepository, BlogRepository blogRepository) {
        this.commentRepository = commentRepository;
        this.blogRepository = blogRepository;
    }


    public CommentResponseDto createComment(CommentRequestDto requestDto, User user, Long blog_id) {
            Blog blog = blogRepository.findById(blog_id).orElseThrow(() ->
                    new RuntimeException("해당 글을 찾을 수 없습니다.")
            );
            Comment comment = commentRepository.save(new Comment(requestDto, user, blog));

            return new CommentResponseDto(comment);
    }

    public List<CommentResponseDto> getComments() {
        return commentRepository.findAllByOrderByModifiedAtDesc().stream().map(CommentResponseDto::new).toList();
    }

    @Transactional
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
                new IllegalArgumentException("선택한 글은 존재하지 않습니다.")
        );
    }
}
