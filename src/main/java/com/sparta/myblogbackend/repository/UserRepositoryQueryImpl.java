package com.sparta.myblogbackend.repository;

import static com.sparta.myblogbackend.entity.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.myblogbackend.entity.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryQueryImpl implements UserRepositoryQuery{

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Optional<User> findByUsernameByQuery(String username) {
    User foundUser = jpaQueryFactory.selectFrom(user)
        .where(user.username.eq(username))
        .fetchOne();

    return Optional.ofNullable(foundUser);
  }
}
