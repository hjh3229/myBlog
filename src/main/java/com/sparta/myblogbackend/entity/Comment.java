package com.sparta.myblogbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.myblogbackend.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comments;
    @JoinColumn(nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;

    public Comment(CommentRequestDto requestDto, User user, Blog blog) {
        this.comments = requestDto.getComments();
        this.username = user.getUsername();
        this.user = user;
        this.blog = blog;
    }

    public void update(CommentRequestDto requestDto) {
        this.comments = requestDto.getComments();
    }
}
