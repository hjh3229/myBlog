package com.sparta.myblogbackend.service.impl;

import com.sparta.myblogbackend.common.PageDto;
import com.sparta.myblogbackend.controller.S3Uploader;
import com.sparta.myblogbackend.dto.BlogRequestDto;
import com.sparta.myblogbackend.dto.BlogResponseDto;
import com.sparta.myblogbackend.dto.UpdateBlogRequestDto;
import com.sparta.myblogbackend.entity.Blog;
import com.sparta.myblogbackend.entity.BlogLike;
import com.sparta.myblogbackend.entity.User;
import com.sparta.myblogbackend.exception.customexception.UnauthorizedException;
import com.sparta.myblogbackend.repository.BlogLikeRepository;
import com.sparta.myblogbackend.repository.BlogRepository;
import com.sparta.myblogbackend.repository.UserRepository;
import com.sparta.myblogbackend.service.BlogService;
import java.io.IOException;
import java.util.concurrent.RejectedExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final BlogLikeRepository blogLikeRepository;
    private final UserRepository userRepository;

    @Autowired
    private S3Uploader s3Uploader;

    @Override
    public BlogResponseDto createBlog(BlogRequestDto requestDto, User user, MultipartFile multipartFile) throws IOException {
        log.info("서비스에서 글 생성 시도");
        try {
            String storedFileName = s3Uploader.upload(multipartFile, "blog");
            Blog blog = blogRepository.save(new Blog(requestDto, user, storedFileName));
            log.info("서비스에서 글 생성 성공");
            return new BlogResponseDto(blog);
        } catch (RejectedExecutionException e) {
            throw new RuntimeException("Fail ! 게시글 생성 실패", e);
        }
    }

    @Override
    public List<BlogResponseDto> getBlogs() {
        return blogRepository.findAllByOrderByModifiedAtDesc().stream().map(BlogResponseDto::new).toList();
    }

    @Override
    public List<BlogResponseDto> getBlogsByKeyword(String keyword) {
        if (keyword == null) {
            throw new RuntimeException("키워드를 입력해주세요");
        }
        return blogRepository.findAllByContentsContainingOrderByModifiedAtDesc(keyword).stream().map(BlogResponseDto::new).toList();
    }

    @Override
    public Page<BlogResponseDto> getBlogByKeyword(String keyword, PageDto pageDto) {
        if (keyword == null) {
            throw new RuntimeException("키워드를 입력해주세요");
        }
        return blogRepository.findBlogsByKeyword(keyword, pageDto.toPageable(keyword));
    }

    @Override
    public BlogResponseDto updateBlog(Long id, UpdateBlogRequestDto requestDto, User user) {
        Blog blog = findBlog(id);
        if (blog.getUsername().equals(user.getUsername())) {
            blog.update(requestDto);
        } else {
            throw new UnauthorizedException("글은 작성자만 수정 할 수 있습니다.");
        }
        return new BlogResponseDto(blog);
    }

    @Override
    public void deleteBlog(Long id, User user) {
        Blog blog = findBlog(id);
        if (blog.getUsername().equals(user.getUsername())) {
            blogRepository.delete(blog);
        } else {
            throw new UnauthorizedException("글은 작성자만 삭제 할 수 있습니다.");
        }
    }

    @Override
    public String like(Long blogId, Long userId) {
        final String[] msg = {""};

        Blog blog = findBlog(blogId);
        User user = findUser(userId);

        Optional<BlogLike> isLike = blogLikeRepository.findByUserAndBlog(user, blog);

        isLike.ifPresentOrElse(
                like -> {
                    blogLikeRepository.delete(like);
                    blog.subLikeCount(like);
                    blog.updateLikeCount();
                    msg[0] = "좋아요 취소";
                },
                () -> {
                    BlogLike blogLike = new BlogLike(user, blog);

                    blogLike.mappingBlog(blog);
                    blogLike.mappingUser(user);
                    blog.updateLikeCount();

                    blogLikeRepository.save(blogLike);
                    msg[0] = "좋아요";
                }
        );
        return msg[0];
    }

    @Override
    public boolean isLiked(Long blogId, Long userId) {
        Blog blog = findBlog(blogId);
        User user = userRepository.findById(userId).orElse(new User());
        Optional<BlogLike> isLike = blogLikeRepository.findByUserAndBlog(user, blog);
        boolean isLiked = BlogLike.isLikedBlog(isLike);
        return isLiked;
    }

    public Blog findBlog(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> // null 체크
                new IllegalArgumentException("선택한 글는 존재하지 않습니다.")
        );
    }

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
    }
}
