package com.sparta.myblogbackend.service.impl;

import com.sparta.myblogbackend.dto.CommentRequestDto;
import com.sparta.myblogbackend.dto.CommentResponseDto;
import com.sparta.myblogbackend.entity.Blog;
import com.sparta.myblogbackend.entity.Comment;
import com.sparta.myblogbackend.entity.CommentLike;
import com.sparta.myblogbackend.entity.User;
import com.sparta.myblogbackend.exception.customexception.UnauthorizedException;
import com.sparta.myblogbackend.repository.BlogRepository;
import com.sparta.myblogbackend.repository.CommentLikeRepository;
import com.sparta.myblogbackend.repository.CommentRepository;
import com.sparta.myblogbackend.repository.UserRepository;
import com.sparta.myblogbackend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;

    @Override
    public CommentResponseDto createComment(CommentRequestDto requestDto, User user, Long blog_id) {
        Blog blog = blogRepository.findById(blog_id).orElseThrow(() ->
                new IllegalArgumentException("해당 글을 찾을 수 없습니다.")
        );
        Comment comment = commentRepository.save(new Comment(requestDto, user, blog));

        return new CommentResponseDto(comment);
    }

    @Override
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, User user) {
        Comment comment = findComment(id);
        if (comment.getUsername().equals(user.getUsername())) {
            comment.update(requestDto);
        } else {
            throw new UnauthorizedException("댓글은 작성자만 수정 할 수 있습니다.");
        }

        return new CommentResponseDto(comment);
    }

    @Override
    public void deleteComment(Long id, User user) {
        Comment comment = findComment(id);
        if (comment.getUsername().equals(user.getUsername())) {
            commentRepository.delete(comment);
        } else {
            throw new UnauthorizedException("작성자만 수정 할 수 있습니다.");
        }
    }

    @Override
    public void like(Long commentId, Long userId) {
        Comment comment = findComment(commentId);
        User user = findUser(userId);
        Optional<CommentLike> isLike = commentLikeRepository.findByUserAndComment(user, comment);

        isLike.ifPresentOrElse(
                like -> {
                    commentLikeRepository.delete(like);
                    comment.subLikeCount(like);
                    comment.updateLikeCount();
                },
                () -> {
                    CommentLike commentLike = new CommentLike(user, comment);

                    commentLike.mappingComment(comment);
                    commentLike.mappingUser(user);
                    comment.updateLikeCount();

                    commentLikeRepository.save(commentLike);
                }
        );
    }

    @Override
    public boolean isLiked(Long commentId, Long userId) {
        Comment comment = findComment(commentId);
        User user = userRepository.findById(userId).orElse(new User());
        Optional<CommentLike> isLike = commentLikeRepository.findByUserAndComment(user, comment);
        boolean isLiked = CommentLike.isLikedComment(isLike);
        return isLiked;
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> // null 체크
                new IllegalArgumentException("선택한 글은 존재하지 않습니다.")
        );
    }

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
    }
}
