package com.sparta.myblogbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    public CommentLike(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }

    public static boolean isLikedComment(Optional<CommentLike> optionalLike) { // 좋아요 여부 반환
        return optionalLike.isPresent();
    }

    public void mappingUser(User user) { // user mapping
        this.user = user;
        user.mappingCommentLike(this);
    }

    public void mappingComment(Comment comment) { // feed mapping
        this.comment = comment;
        comment.mappingCommentLike(this);
    }
}
