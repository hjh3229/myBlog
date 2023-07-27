package com.sparta.myblogbackend.repository;

import com.sparta.myblogbackend.entity.User;
import java.util.Optional;

public interface UserRepositoryQuery {

  Optional<User> findByUsernameByQuery(String username);

}
