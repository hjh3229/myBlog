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
public class BlogLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;

    public BlogLike(User user, Blog blog) {
        this.user = user;
        this.blog = blog;
    }

    public static boolean isLikedBlog(Optional<BlogLike> optionalLike) { // 좋아요 여부 반환
        return optionalLike.isPresent();
    }

    public void mappingUser(User user) { // user mapping
        this.user = user;
        user.mappingBlogLike(this);
    }

    public void mappingBlog(Blog blog) { // feed mapping
        this.blog = blog;
        blog.mappingBlogLike(this);
    }
}
