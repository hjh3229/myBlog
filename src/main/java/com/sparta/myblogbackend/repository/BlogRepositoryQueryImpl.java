package com.sparta.myblogbackend.repository;

import static com.sparta.myblogbackend.entity.QBlog.blog;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.myblogbackend.dto.BlogResponseDto;
import com.sparta.myblogbackend.entity.Blog;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class BlogRepositoryQueryImpl implements BlogRepositoryQuery{

  private final JPAQueryFactory jpaQueryFactory;



  @Override
  public Page<BlogResponseDto> findBlogsByKeyword(String keyword, Pageable pageable) {
    QueryResults<Blog> results = getQuery(keyword)
        .orderBy(blog.createdAt.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetchResults();

    return new PageImpl<>(toBlogResponseDtoList(results.getResults()), pageable, results.getTotal());
  }

  private List<BlogResponseDto> toBlogResponseDtoList(List<Blog> blogs) {
    return blogs.stream()
        .map(BlogResponseDto::new)
        .collect(Collectors.toList());
  }

  private JPAQuery<Blog> getQuery(String keyword) {
    BooleanExpression keywordExpression = blog.title.containsIgnoreCase(keyword)
        .or(blog.contents.containsIgnoreCase(keyword));

    return jpaQueryFactory
        .selectFrom(blog)
        .where(keywordExpression);
  }
}
