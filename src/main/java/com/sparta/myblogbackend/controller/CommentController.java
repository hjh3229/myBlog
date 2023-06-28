package com.sparta.myblogbackend.controller;

import com.sparta.myblogbackend.dto.CommentRequestDto;
import com.sparta.myblogbackend.dto.CommentResponseDto;
import com.sparta.myblogbackend.entity.Blog;
import com.sparta.myblogbackend.security.UserDetailsImpl;
import com.sparta.myblogbackend.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long blog_id = requestDto.getBlog_id();
        return commentService.createComment(requestDto, userDetails.getUser(), blog_id);
    }

//    @GetMapping("/comments")
//    public List<CommentResponseDto> getComments() {
//        return commentService.getComments();
//    }

    @PutMapping("/comment")
    @ResponseBody
    public CommentResponseDto updateComment(@RequestParam Long id, @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(id, requestDto);
    }

    @DeleteMapping("/comment")
    public ResponseEntity<String> deleteComment(@RequestParam Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.status(HttpStatus.OK).body("댓글 삭제 성공");
    }
}
